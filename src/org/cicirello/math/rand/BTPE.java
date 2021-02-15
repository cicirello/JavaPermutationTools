/*
 * Copyright 2019-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of JavaPermutationTools (https://jpt.cicirello.org/).
 *
 * JavaPermutationTools is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * JavaPermutationTools is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaPermutationTools.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.cicirello.math.rand;

import org.cicirello.math.MathFunctions;
import java.util.SplittableRandom;
import java.util.Random;

/**
 * <p>This class enables generating random variates from a binomial distribution.
 * This is a mostly direct implementation of the BTPE algorithm described in the
 * article: Voratas Kachitvichyanukul and Bruce W. Schmeiser. 1988. 
 * Binomial random variate generation. Commun. ACM 31, 2 (February 1988), 
 * 216-222. DOI=http://dx.doi.org/10.1145/42372.42381 </p>
 *
 * <p>There are a few, mostly minor, differences in my Java implementation of
 * the algorithm described in that paper.  First, there are several minor
 * optimizations, such as replacing division by constants with multiplication of constants
 * wherever practical.  As mentioned in the above article, BTPE is only valid
 * when n*min(p,1-p) &ge; 10.  The authors of that article include a survey of
 * various alternative approaches one might use when n*min(p,1-p) &lt; 10.  We use
 * a simple approach based on the inverse transform in that case.</p>
 *
 * <p>We cache the various constants that depend only on n and p.  We only recompute
 * these if a different n or p is given as a parameter.  To enable a thread safe approach,
 * we maintain one constant cache for each thread using ThreadLocal.  Specifically, we maintain
 * one instance of BTPE per thread using ThreadLocal.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 2.12.2021
 */
final class BTPE {
	
	/**
	 * Generates a pseudorandom integer from a binomial distribution.
	 * @param n Number of trials for the binomial distribution.
	 * @param p The probability of a successful trial.
	 * @param generator The source of randomness.
	 * @return A pseudorandom integer from a binomial distribution.
	 */
	public static int nextBinomial(int n, double p, Random generator) {
		return threadLocalInstance(n, p).next(generator);
	}
	
	/**
	 * Generates a pseudorandom integer from a binomial distribution.
	 * @param n Number of trials for the binomial distribution.
	 * @param p The probability of a successful trial.
	 * @param generator The source of randomness.
	 * @return A pseudorandom integer from a binomial distribution.
	 */
	public static int nextBinomial(int n, double p, SplittableRandom generator) {
		return threadLocalInstance(n, p).next(generator);
	}
	
	// We cache constants until n or p changes.  We use ThreadLocal for
	// cache so that we are thread safe.  Each thread has its own cache.
	private static final ThreadLocal<BTPE> tl = new ThreadLocal<BTPE>();
	
	/*
	 * Private helper to handle getting the thread local instance of BTPE,
	 * initializing if necessary, and generating if it doesn't already exist.
	 */
	private static BTPE threadLocalInstance(int n, double p) {
		BTPE btpe = tl.get();
		if (btpe == null) {
			btpe = new BTPE(n, p);
			tl.set(btpe);
		} else if (btpe.n != n || btpe.p != p) {
			btpe.init(n, p);
		}
		return btpe;
	}
	
	// Variables for caching constants that only change when n or p changes.
	// Constant cache for BTPE and simple inverse based approach
	private int n;
	private double p;
	private double r;
	private double q;
	
	// Constant cache for BTPE
	private double f_m;
	private int m;
	private double nr;
	private double nrq;
	private double p1;
	private double x_m;
	private double x_l;
	private double x_r;
	private double c;
	private double lambda_l;
	private double lambda_r;
	private double p2;
	private double p3;
	private double p4;
	private double nrqInv;
	
	// Constant cache for simple inverse based approach
	private double s;
	private double a;
	private double pow0;
	
	// BTPE is not valid if n*min(p,1-p) < 10
	private static final int BTPE_CUTOFF = 10;
	
	// Private constructor
	private BTPE(int n, double p) {
		init(n, p);
	}
	
	// call this if n or p changes
	private void init(int n, double p) {
		// Step 0: Set-up constants, etc.
		this.n = n;
		this.p = p;
		if (p <= 0.5) {
			r = p; 
			q = 1 - p; 
		} else {
			q = p;
			r = 1 - p;
		}
		nr = n*r; 
		s = r / q;
		if (nr < BTPE_CUTOFF) {
			a = (n+1)*s;
			pow0 = MathFunctions.pow(q, n);		
		} else {			
			f_m = nr + r; 
			m = (int)f_m;  
			nrq = nr*q; 
			p1 = Math.floor(2.195 * Math.sqrt(nrq) - 4.6*q) + 0.5; 
			x_m = m + 0.5; 
			x_l = x_m - p1; 
			x_r = x_m + p1; 
			c = 0.134 + 20.5/(15.3 + m); 
			a = (f_m - x_l) / (f_m - x_l * r); 
			lambda_l = a * (1 + 0.5 * a); 
			a = (x_r - f_m) / (x_r * q); 
			lambda_r = a * (1 + 0.5 * a); 
			p2 = p1 * (1 + c + c); 
			p3 = p2 + c / lambda_l; 
			p4 = p3 + c / lambda_r; 
			nrqInv = 1.0 / nrq;
		}
	}
	
	private int next(Random generator) {
				
		if (nr < BTPE_CUTOFF) {
			// Use simple algorithm based on inverse transform since BTPE is only valid
			// when n*min(p, 1-p) >= 10.
			double u = generator.nextDouble();
			int y = 0;
			double pow = pow0;
			while (u > pow) {
				u -= pow;
				y++;
				pow = pow * (a/y-s);
			}
			return p > 0.5 ? n - y : y;
		}
		
		int y;
		while (true) {
			
			// Step 1.
			double u = p4 * generator.nextDouble(); 
			double v = generator.nextDouble(); 
			if (u <= p1) { 
				y = (int)(x_m - p1*v + u); 
				break;
			}
			
			// Step 2: parallelograms
			if (u <= p2) {
				double x = x_l + (u - p1) / c;
				v = v*c + 1 - Math.abs(m - x + 0.5) / p1;
				if (v > 1) continue;
				y = (int)x;
			} else {
				// Step 3: left exponential tail
				if (u <= p3) {
					y = (int)Math.floor(x_l + Math.log(v)/lambda_l);
					if (y < 0) continue;
					v = v * (u - p2) * lambda_l;
				} else {
					// Step 4: right exponential tail
					y = (int)Math.floor(x_r - Math.log(v)/lambda_r);
					if (y > n) continue;
					v = v * (u - p3) * lambda_r;
				}
			}
			
			// Step 5: Acceptance/rejection comparisons.
			
			// Step 5.0
			int k = m > y ? m - y : y - m;
			if (k <= 20 || k >= nrq * 0.5 - 1) {
				// Step 5.1
				a = s * (n+1);
				double f = 1.0;
				if (m < y) {
					for (int i = m + 1; i <= y; i++) {
						f = f*(a/i-s);
					}
				} else if (m > y) {
					for (int i = y + 1; i <= m; i++) {
						f = f/(a/i-s);
					}
				}
				if (v > f) continue;
				break;
			} else {
				// Step 5.2: Squeezing
				double rho = (k * nrqInv) * ((k * (k*0.3333333333333333 + 0.625) + 0.16666666666666666) * nrqInv + 0.5);
				double t = -k*k*(0.5*nrqInv);
				a = Math.log(v);
				if (a < t - rho) {
					break;
				}
				if (a > t + rho) continue;
			}
			
			// Step 5.3: Final acceptance/rejection test
			int x1 = y + 1;
			int f1 = m + 1;
			int z = n + 1 - m;
			int w = n - y + 1;
			if (a > x_m * Math.log(((double)f1)/x1) + (n - m + 0.5)*Math.log(((double)z)/w) + (y-m)*Math.log(w*r/(x1*q)) + stirlingApproximation(f1) + stirlingApproximation(z) + stirlingApproximation(x1) + stirlingApproximation(w)) continue;
			break;
		}
		
		return p > 0.5 ? n - y : y;
	}
	
	private int next(SplittableRandom generator) {
				
		if (nr < BTPE_CUTOFF) {
			// Use simple algorithm based on inverse transform since BTPE is only valid
			// when n*min(p, 1-p) >= 10.
			double u = generator.nextDouble();
			int y = 0;
			double pow = pow0;
			while (u > pow) {
				u -= pow;
				y++;
				pow = pow * (a/y-s);
			}
			return p > 0.5 ? n - y : y;
		}
		
		int y;
		while (true) {
			
			// Step 1.
			double u = p4 * generator.nextDouble(); 
			double v = generator.nextDouble(); 
			if (u <= p1) { 
				y = (int)(x_m - p1*v + u); 
				break;
			}
			
			// Step 2: parallelograms
			if (u <= p2) {
				double x = x_l + (u - p1) / c;
				v = v*c + 1 - Math.abs(m - x + 0.5) / p1;
				if (v > 1) continue;
				y = (int)x;
			} else {
				// Step 3: left exponential tail
				if (u <= p3) {
					y = (int)Math.floor(x_l + Math.log(v)/lambda_l);
					if (y < 0) continue;
					v = v * (u - p2) * lambda_l;
				} else {
					// Step 4: right exponential tail
					y = (int)Math.floor(x_r - Math.log(v)/lambda_r);
					if (y > n) continue;
					v = v * (u - p3) * lambda_r;
				}
			}
			
			// Step 5: Acceptance/rejection comparisons.
			
			// Step 5.0
			int k = m > y ? m - y : y - m;
			if (k <= 20 || k >= nrq * 0.5 - 1) {
				// Step 5.1
				a = s * (n+1);
				double f = 1.0;
				if (m < y) {
					for (int i = m + 1; i <= y; i++) {
						f = f*(a/i-s);
					}
				} else if (m > y) {
					for (int i = y + 1; i <= m; i++) {
						f = f/(a/i-s);
					}
				}
				if (v > f) continue;
				break;
			} else {
				// Step 5.2: Squeezing
				double rho = (k * nrqInv) * ((k * (k*0.3333333333333333 + 0.625) + 0.16666666666666666) * nrqInv + 0.5);
				double t = -k*k*(0.5*nrqInv);
				a = Math.log(v);
				if (a < t - rho) {
					break;
				}
				if (a > t + rho) continue;
			}
			
			// Step 5.3: Final acceptance/rejection test
			int x1 = y + 1;
			int f1 = m + 1;
			int z = n + 1 - m;
			int w = n - y + 1;
			if (a > x_m * Math.log(((double)f1)/x1) + (n - m + 0.5)*Math.log(((double)z)/w) + (y-m)*Math.log(w*r/(x1*q)) + stirlingApproximation(f1) + stirlingApproximation(z) + stirlingApproximation(x1) + stirlingApproximation(w)) continue;
			break;
		}
		
		return p > 0.5 ? n - y : y;
	}
	
	private double stirlingApproximation(int x) {
		int x2 = x * x;
		return (13860.0 - (462.0 - (132.0 - (99.0 - 140.0 / x2) / x2) / x2) / x2) / x / 166320.0;
	}
	
}