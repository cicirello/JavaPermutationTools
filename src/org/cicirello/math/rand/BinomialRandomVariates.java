/*
 * BinomialRandomVariates.java: Java port and optimization of the C language
 * randist/binomial_tpe.c from the GNU Scientific Library 2.5.
 * 
 * Copyright (C) 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * The original C language randist/binomial_tpe.c from the GSL
 * is copyright as follows:
 *
 * Copyright (C) 1996, 2003, 2007 James Theiler, Brian Gough
 *
 * BinomialRandomVariates.java is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * BinomialRandomVariates.java is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BinomialRandomVariates.java.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.cicirello.math.rand;

import org.cicirello.math.MathFunctions;
import java.util.SplittableRandom;
import java.util.Random;

/*
 * This Java class provides methods for generating random variates from a
 * binomial distribution.  It is deliberately package private to isolate
 * code that was derived from another open source project, the GNU Scientific Library,
 * from the rest of this project.
 * That original code was in the C language.  In this Java class, I have ported that
 * code to Java, refactored it quite a bit to make it easier to read, and also optimized 
 * in a couple places.
 *
 * The original C version can be found at the bottom of this file in comments, to enable
 * you to compare and see what was kept as is, and what I changed.  Parts I kept
 * unchanged, or with very minor changes, are subject to the original copyright
 * (see copyright block at top), and the Java specific code and any substantive changes
 * I've made are subject to the new copyright.
 *
 * I am using the same license agreement as the original C version (see above).
 *
 * I have isolated the code in this package-private class in this way to enable
 * easily swapping this out for a different implementation in the event that I decide
 * to release a version of this project under a different incompatible license agreement.
 * Although I don't anticipate doing so as I like the GPL 3.
 */
final class BinomialRandomVariates {
	
	/*
	 * Utility class of static methods, so constructor is private to prevent
	 * instantiation.
	 */
	private BinomialRandomVariates() {}
	
	public static int nextBinomial(int n, double p, Random r) {
		// Case of n = 0 is 0.  Undefined for n < 0, simply returning 0.
		if (n <= 0) return 0;
		                       
		boolean flipped = false;
		if (p > 0.5) {
			/* work with small p */
			p = 1.0 - p;              
			flipped = true;
		}

		double np = n * p;
		int ix = (np < SMALL_MEAN) ? nextBinomialSmallMean(n, p, r): nextBinomialLargeMean(n, p, np, r);
		return (flipped) ? (n - ix) : ix;
	}
	
	public static int nextBinomial(int n, double p, SplittableRandom r) {
		// Case of n = 0 is 0.  Undefined for n < 0, simply returning 0.
		if (n <= 0) return 0;
		                       
		boolean flipped = false;
		if (p > 0.5) {
			/* work with small p */
			p = 1.0 - p;              
			flipped = true;
		}

		double np = n * p;
		int ix = (np < SMALL_MEAN) ? nextBinomialSmallMean(n, p, r): nextBinomialLargeMean(n, p, np, r);
		return (flipped) ? (n - ix) : ix;
	}
	
	private static int nextBinomialSmallMean(int n, double p, SplittableRandom r) {
		int ix = 0;
		double q = 1 - p;
		double s = p / q;
		double f0 = MathFunctions.pow(q, n);   /* f(x), starting with x=0 */
		boolean finished = false;
		do {
			/* 
			 * Original C code on which this is based used
			 * an infinite loop here, along with a goto in a
			 * nested loop with a long jump to bottom.
			 * In this Java version, I replaced infinite while loop and the old goto 
			 * with a do-while loop and the finished flag declared above.
			 */

			double f = f0;
			double u = r.nextDouble();

			for (ix = 0; ix <= BINV_CUTOFF; ix++) {
				if (u < f) {
					finished = true;
					break;
				}
				u -= f;
				f *= s * (n - ix) / (ix + 1);
			}

			/* 
			 * It should be the case that finished==true
			 * before this point is reached.  If not,
			 * then roundoff has prevented u from decreasing
			 * all the way to zero.  This can happen only if the initial u
			 * was very nearly equal to 1, which is a rare situation.  In
			 * that rare situation, we just try again.
			 */
		} while (!finished);
		return ix;
	}
	
	private static int nextBinomialSmallMean(int n, double p, Random r) {
		int ix = 0;
		double q = 1 - p;
		double s = p / q;
		double f0 = MathFunctions.pow(q, n);   /* f(x), starting with x=0 */
		boolean finished = false;
		do {
			/* 
			 * Original C code on which this is based used
			 * an infinite loop here, along with a goto in a
			 * nested loop with a long jump to bottom.
			 * In this Java version, I replaced infinite while loop and the old goto 
			 * with a do-while loop and the finished flag declared above.
			 */

			double f = f0;
			double u = r.nextDouble();

			for (ix = 0; ix <= BINV_CUTOFF; ix++) {
				if (u < f) {
					finished = true;
					break;
				}
				u -= f;
				f *= s * (n - ix) / (ix + 1);
			}

			/* 
			 * It should be the case that finished==true
			 * before this point is reached.  If not,
			 * then roundoff has prevented u from decreasing
			 * all the way to zero.  This can happen only if the initial u
			 * was very nearly equal to 1, which is a rare situation.  In
			 * that rare situation, we just try again.
			 */
		} while (!finished);
		return ix;
	}
	
	private static int nextBinomialLargeMean(int n, double p, double np, SplittableRandom r) {
		int ix = 0;
		double q = 1 - p;
		double s = p / q;

		double ffm = np + p;      /* ffm = n*p+p             */
		int m = (int)ffm;         /* m = int floor[n*p+p]    */
		double fm = m;            /* fm = double m;          */
		double xm = fm + 0.5;     /* xm = half integer mean (tip of triangle)  */
		double npq = np * q;      /* npq = n*p*q            */

		/* Compute cumulative area of tri, para, exp tails */
		/* Note: magic numbers 2.195, 4.6, 0.134, 20.5, 15.3 */
		/* These magic numbers are not adjustable...at least not easily! */

		/* p1: radius of triangle region; since height=1, also: area of region */
		double p1 = Math.floor(2.195 * Math.sqrt(npq) - 4.6 * q) + 0.5;

		/* xl, xr: left and right edges of triangle */
		double xl = xm - p1;
		double xr = xm + p1;

		/* Parameter of exponential tails */
		/* Left tail:  t(x) = c*exp(-lambda_l*[xl - (x+0.5)]) */
		/* Right tail: t(x) = c*exp(-lambda_r*[(x+0.5) - xr]) */

		/* p2: p1 + area of parallelogram region */
		double c = 0.134 + 20.5 / (15.3 + fm);
		double p2 = p1 * (1.0 + c + c);

		double al = (ffm - xl) / (ffm - xl * p);
		double lambda_l = al * (1.0 + 0.5 * al);
		double ar = (xr - ffm) / (xr * q);
		double lambda_r = ar * (1.0 + 0.5 * ar);
		/* p3: p2 + area of left tail */
		double p3 = p2 + c / lambda_l;
		/* p4: p3 + area of right tail */
		/* pi/p4: probability of i'th area (i=1,2,3,4) */
		double p4 = p3 + c / lambda_r;

		boolean tryAgain;
		do {
			/*
			 * The original C version didn't use a loop.  Instead, it used
			 * a rather complex combination of goto statements with multiple labels
			 * and rather long jumps.
			 * My version uses an explicit loop rather than one implied by gotos.
			 * Still complex (i.e., multiple ways of exiting via breaks, and still has
			 * some long jumps, but with continue statements rather than gotos).
			 * NOTE TO SELF: Try to refactor this further to make easier to read.
			 */
			tryAgain = false;

			/* generate random variates, u specifies which region: Tri, Par, Tail */
			double u = r.nextDouble() * p4;
			double v = r.nextDouble();

			if (u <= p1) {
				/* Triangular region */
				ix = (int)(xm - p1 * v + u);
				break;
			} else if (u <= p2) {
				/* Parallelogram region */
				double x = xl + (u - p1) / c;
				v = v * c + 1.0 - Math.abs(x - xm) / p1;
				if (v > 1.0 || v <= 0.0) {
					tryAgain = true;
					continue;
				} 
				ix = (int)x;
			} else if (u <= p3) {
				/* Left tail */
				ix = (int)(xl + Math.log(v) / lambda_l);
				if (ix < 0) {
					tryAgain = true;
					continue;
				}
				v *= ((u - p2) * lambda_l);
			} else {
				/* Right tail */
				ix = (int)(xr - Math.log(v) / lambda_r);
				if (ix > n) {
					tryAgain = true;
					continue;
				}
				v *= ((u - p3) * lambda_r);
			}

			/* 
			 * At this point, the goal is to test whether v <= f(x)/f(m) 
			 * v <= f(x)/f(m) = (m!(n-m)! / (x!(n-x)!)) * (p/q)^{x-m}
			 */

			/* SQUEEZE METHOD: More efficient determination of whether v < f(x)/f(M) */

			int k = Math.abs(ix - m);
			double var, accept;

			if (k <= FAR_FROM_MEAN) {
				/* 
				 * If ix near m (ie, |ix-m|<FAR_FROM_MEAN), then do
				 * explicit evaluation using recursive relation for f(x)
				 */
				double g = (n + 1) * s;
				double f = 1.0;

				var = v;

				if (m < ix) {
					for (int i = m + 1; i <= ix; i++) {
						f *= (g / i - s);
					}
				} else if (m > ix) {
					for (int i = ix + 1; i <= m; i++) {
						f /= (g / i - s);
					}
				}

				accept = f;
			} else {
				/* If ix is far from the mean m: k=ABS(ix-m) large */

				var = Math.log(v);

				if (k < npq / 2 - 1) {
					/* "Squeeze" using upper and lower bounds on
					* log(f(x)) The squeeze condition was derived
					* under the condition k < npq/2-1 */
					double amaxp = k / npq * ((k * (k / 3.0 + 0.625) + (1.0 / 6.0)) / npq + 0.5);
					double ynorm = -(k * k / (2.0 * npq));
					if (var < ynorm - amaxp) break;
					if (var > ynorm + amaxp) {
						tryAgain = true;
						continue;
					}
				}

				/* Now, again: do the test log(v) vs. log f(x)/f(M) */

				double x1 = ix + 1.0;
				double w1 = n - ix + 1.0;
				double f1 = fm + 1.0;
				double z1 = n + 1.0 - fm;

				accept = xm * Math.log(f1 / x1) + (n - m + 0.5) * Math.log(z1 / w1)
					+ (ix - m) * Math.log(w1 * p / (x1 * q))
					+ stirling(f1) + stirling(z1) - stirling(x1) - stirling(w1);
			}

			if (var > accept) {
				tryAgain = true;
			}
			
		} while (tryAgain);
		return ix;
	}
	
	private static int nextBinomialLargeMean(int n, double p, double np, Random r) {
		int ix = 0;
		double q = 1 - p;
		double s = p / q;

		double ffm = np + p;      /* ffm = n*p+p             */
		int m = (int)ffm;         /* m = int floor[n*p+p]    */
		double fm = m;            /* fm = double m;          */
		double xm = fm + 0.5;     /* xm = half integer mean (tip of triangle)  */
		double npq = np * q;      /* npq = n*p*q            */

		/* Compute cumulative area of tri, para, exp tails */
		/* Note: magic numbers 2.195, 4.6, 0.134, 20.5, 15.3 */
		/* These magic numbers are not adjustable...at least not easily! */

		/* p1: radius of triangle region; since height=1, also: area of region */
		double p1 = Math.floor(2.195 * Math.sqrt(npq) - 4.6 * q) + 0.5;

		/* xl, xr: left and right edges of triangle */
		double xl = xm - p1;
		double xr = xm + p1;

		/* Parameter of exponential tails */
		/* Left tail:  t(x) = c*exp(-lambda_l*[xl - (x+0.5)]) */
		/* Right tail: t(x) = c*exp(-lambda_r*[(x+0.5) - xr]) */

		/* p2: p1 + area of parallelogram region */
		double c = 0.134 + 20.5 / (15.3 + fm);
		double p2 = p1 * (1.0 + c + c);

		double al = (ffm - xl) / (ffm - xl * p);
		double lambda_l = al * (1.0 + 0.5 * al);
		double ar = (xr - ffm) / (xr * q);
		double lambda_r = ar * (1.0 + 0.5 * ar);
		/* p3: p2 + area of left tail */
		double p3 = p2 + c / lambda_l;
		/* p4: p3 + area of right tail */
		/* pi/p4: probability of i'th area (i=1,2,3,4) */
		double p4 = p3 + c / lambda_r;

		boolean tryAgain;
		do {
			/*
			 * The original C version didn't use a loop.  Instead, it used
			 * a rather complex combination of goto statements with multiple labels
			 * and rather long jumps.
			 * My version uses an explicit loop rather than one implied by gotos.
			 * Still complex (i.e., multiple ways of exiting via breaks, and still has
			 * some long jumps, but with continue statements rather than gotos).
			 * NOTE TO SELF: Try to refactor this further to make easier to read.
			 */
			tryAgain = false;

			/* generate random variates, u specifies which region: Tri, Par, Tail */
			double u = r.nextDouble() * p4;
			double v = r.nextDouble();

			if (u <= p1) {
				/* Triangular region */
				ix = (int)(xm - p1 * v + u);
				break;
			} else if (u <= p2) {
				/* Parallelogram region */
				double x = xl + (u - p1) / c;
				v = v * c + 1.0 - Math.abs(x - xm) / p1;
				if (v > 1.0 || v <= 0.0) {
					tryAgain = true;
					continue;
				} 
				ix = (int)x;
			} else if (u <= p3) {
				/* Left tail */
				ix = (int)(xl + Math.log(v) / lambda_l);
				if (ix < 0) {
					tryAgain = true;
					continue;
				}
				v *= ((u - p2) * lambda_l);
			} else {
				/* Right tail */
				ix = (int)(xr - Math.log(v) / lambda_r);
				if (ix > n) {
					tryAgain = true;
					continue;
				}
				v *= ((u - p3) * lambda_r);
			}

			/* 
			 * At this point, the goal is to test whether v <= f(x)/f(m) 
			 * v <= f(x)/f(m) = (m!(n-m)! / (x!(n-x)!)) * (p/q)^{x-m}
			 */

			/* SQUEEZE METHOD: More efficient determination of whether v < f(x)/f(M) */

			int k = Math.abs(ix - m);
			double var, accept;

			if (k <= FAR_FROM_MEAN) {
				/* 
				 * If ix near m (ie, |ix-m|<FAR_FROM_MEAN), then do
				 * explicit evaluation using recursive relation for f(x)
				 */
				double g = (n + 1) * s;
				double f = 1.0;

				var = v;

				if (m < ix) {
					for (int i = m + 1; i <= ix; i++) {
						f *= (g / i - s);
					}
				} else if (m > ix) {
					for (int i = ix + 1; i <= m; i++) {
						f /= (g / i - s);
					}
				}

				accept = f;
			} else {
				/* If ix is far from the mean m: k=ABS(ix-m) large */

				var = Math.log(v);

				if (k < npq / 2 - 1) {
					/* "Squeeze" using upper and lower bounds on
					* log(f(x)) The squeeze condition was derived
					* under the condition k < npq/2-1 */
					double amaxp = k / npq * ((k * (k / 3.0 + 0.625) + (1.0 / 6.0)) / npq + 0.5);
					double ynorm = -(k * k / (2.0 * npq));
					if (var < ynorm - amaxp) break;
					if (var > ynorm + amaxp) {
						tryAgain = true;
						continue;
					}
				}

				/* Now, again: do the test log(v) vs. log f(x)/f(M) */

				double x1 = ix + 1.0;
				double w1 = n - ix + 1.0;
				double f1 = fm + 1.0;
				double z1 = n + 1.0 - fm;

				accept = xm * Math.log(f1 / x1) + (n - m + 0.5) * Math.log(z1 / w1)
					+ (ix - m) * Math.log(w1 * p / (x1 * q))
					+ stirling(f1) + stirling(z1) - stirling(x1) - stirling(w1);
			}

			if (var > accept) {
				tryAgain = true;
			}
			
		} while (tryAgain);
		return ix;
	}
	
	/* 
	 * Corresponds to the first five
	 * terms in asymptoic formula for
	 * log Gamma (y) - (y-0.5)log(y) + y - 0.5 log(2*pi);
	 * See Abramowitz and Stegun, eq 6.1.40
	 */
	private static double stirling(double y) {
		double y2 = y * y;
		return (13860.0 - (462.0 - (132.0 - (99.0 - 140.0 / y2) / y2) / y2) / y2) / y / 166320.0;
	}
	
	private static final int SMALL_MEAN = 14;           
	private static final int BINV_CUTOFF = 110;
	private static final int FAR_FROM_MEAN = 20;
}


// ORIGINAL C VERSION BEGINS HERE IN THE COMMENTS BELOW.

/* randist/binomial_tpe.c
 * 
 * Copyright (C) 1996, 2003, 2007 James Theiler, Brian Gough
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

// #include <config.h>
// #include <math.h>
// #include <gsl/gsl_rng.h>
// #include <gsl/gsl_randist.h>
// #include <gsl/gsl_pow_int.h>
// #include <gsl/gsl_sf_gamma.h>

// /* The binomial distribution has the form,

   // f(x) =  n!/(x!(n-x)!) * p^x (1-p)^(n-x) for integer 0 <= x <= n
        // =  0                               otherwise

   // This implementation follows the public domain ranlib function
   // "ignbin", the bulk of which is the BTPE (Binomial Triangle
   // Parallelogram Exponential) algorithm introduced in
   // Kachitvichyanukul and Schmeiser[1].  It has been translated to use
   // modern C coding standards.

   // If n is small and/or p is near 0 or near 1 (specifically, if
   // n*min(p,1-p) < SMALL_MEAN), then a different algorithm, called
   // BINV, is used which has an average runtime that scales linearly
   // with n*min(p,1-p).

   // But for larger problems, the BTPE algorithm takes the form of two
   // functions b(x) and t(x) -- "bottom" and "top" -- for which b(x) <
   // f(x)/f(M) < t(x), with M = floor(n*p+p).  b(x) defines a triangular
   // region, and t(x) includes a parallelogram and two tails.  Details
   // (including a nice drawing) are in the paper.

   // [1] Kachitvichyanukul, V. and Schmeiser, B. W.  Binomial Random
   // Variate Generation.  Communications of the ACM, 31, 2 (February,
   // 1988) 216.

   // Note, Bruce Schmeiser (personal communication) points out that if
   // you want very fast binomial deviates, and you are happy with
   // approximate results, and/or n and n*p are both large, then you can
   // just use gaussian estimates: mean=n*p, variance=n*p*(1-p).

   // This implementation by James Theiler, April 2003, after obtaining
   // permission -- and some good advice -- from Drs. Kachitvichyanukul
   // and Schmeiser to use their code as a starting point, and then doing
   // a little bit of tweaking.

   // Additional polishing for GSL coding standards by Brian Gough.  */

// #define SMALL_MEAN 14           /* If n*p < SMALL_MEAN then use BINV
                                   // algorithm. The ranlib
                                   // implementation used cutoff=30; but
                                   // on my computer 14 works better */

// #define BINV_CUTOFF 110         /* In BINV, do not permit ix too large */

// #define FAR_FROM_MEAN 20        /* If ix-n*p is larger than this, then
                                   // use the "squeeze" algorithm.
                                   // Ranlib used 20, and this seems to
                                   // be the best choice on my machine as
                                   // well */

// #define LNFACT(x) gsl_sf_lnfact(x)

// inline static double
// Stirling (double y1)
// {
  // double y2 = y1 * y1;
  // double s =
    // (13860.0 -
     // (462.0 - (132.0 - (99.0 - 140.0 / y2) / y2) / y2) / y2) / y1 / 166320.0;
  // return s;
// }

// unsigned int
// gsl_ran_binomial_tpe (const gsl_rng * rng, double p, unsigned int n)
// {
  // return gsl_ran_binomial (rng, p, n);
// }

// unsigned int
// gsl_ran_binomial (const gsl_rng * rng, double p, unsigned int n)
// {
  // int ix;                       /* return value */
  // int flipped = 0;
  // double q, s, np;

  // if (n == 0)
    // return 0;

  // if (p > 0.5)
    // {
      // p = 1.0 - p;              /* work with small p */
      // flipped = 1;
    // }

  // q = 1 - p;
  // s = p / q;
  // np = n * p;

  // /* Inverse cdf logic for small mean (BINV in K+S) */

  // if (np < SMALL_MEAN)
    // {
      // double f0 = gsl_pow_uint (q, n);   /* f(x), starting with x=0 */

      // while (1)
        // {
          // /* This while(1) loop will almost certainly only loop once; but
           // * if u=1 to within a few epsilons of machine precision, then it
           // * is possible for roundoff to prevent the main loop over ix to
           // * achieve its proper value.  following the ranlib implementation,
           // * we introduce a check for that situation, and when it occurs,
           // * we just try again.
           // */

          // double f = f0;
          // double u = gsl_rng_uniform (rng);

          // for (ix = 0; ix <= BINV_CUTOFF; ++ix)
            // {
              // if (u < f)
                // goto Finish;
              // u -= f;
              // /* Use recursion f(x+1) = f(x)*[(n-x)/(x+1)]*[p/(1-p)] */
              // f *= s * (n - ix) / (ix + 1);
            // }

          // /* It should be the case that the 'goto Finish' was encountered
           // * before this point was ever reached.  But if we have reached
           // * this point, then roundoff has prevented u from decreasing
           // * all the way to zero.  This can happen only if the initial u
           // * was very nearly equal to 1, which is a rare situation.  In
           // * that rare situation, we just try again.
           // *
           // * Note, following the ranlib implementation, we loop ix only to
           // * a hardcoded value of SMALL_MEAN_LARGE_N=110; we could have
           // * looped to n, and 99.99...% of the time it won't matter.  This
           // * choice, I think is a little more robust against the rare
           // * roundoff error.  If n>LARGE_N, then it is technically
           // * possible for ix>LARGE_N, but it is astronomically rare, and
           // * if ix is that large, it is more likely due to roundoff than
           // * probability, so better to nip it at LARGE_N than to take a
           // * chance that roundoff will somehow conspire to produce an even
           // * larger (and more improbable) ix.  If n<LARGE_N, then once
           // * ix=n, f=0, and the loop will continue until ix=LARGE_N.
           // */
        // }
    // }
  // else
    // {
      // /* For n >= SMALL_MEAN, we invoke the BTPE algorithm */

      // int k;

      // double ffm = np + p;      /* ffm = n*p+p             */
      // int m = (int) ffm;        /* m = int floor[n*p+p]    */
      // double fm = m;            /* fm = double m;          */
      // double xm = fm + 0.5;     /* xm = half integer mean (tip of triangle)  */
      // double npq = np * q;      /* npq = n*p*q            */

      // /* Compute cumulative area of tri, para, exp tails */

      // /* p1: radius of triangle region; since height=1, also: area of region */
      // /* p2: p1 + area of parallelogram region */
      // /* p3: p2 + area of left tail */
      // /* p4: p3 + area of right tail */
      // /* pi/p4: probability of i'th area (i=1,2,3,4) */

      // /* Note: magic numbers 2.195, 4.6, 0.134, 20.5, 15.3 */
      // /* These magic numbers are not adjustable...at least not easily! */

      // double p1 = floor (2.195 * sqrt (npq) - 4.6 * q) + 0.5;

      // /* xl, xr: left and right edges of triangle */
      // double xl = xm - p1;
      // double xr = xm + p1;

      // /* Parameter of exponential tails */
      // /* Left tail:  t(x) = c*exp(-lambda_l*[xl - (x+0.5)]) */
      // /* Right tail: t(x) = c*exp(-lambda_r*[(x+0.5) - xr]) */

      // double c = 0.134 + 20.5 / (15.3 + fm);
      // double p2 = p1 * (1.0 + c + c);

      // double al = (ffm - xl) / (ffm - xl * p);
      // double lambda_l = al * (1.0 + 0.5 * al);
      // double ar = (xr - ffm) / (xr * q);
      // double lambda_r = ar * (1.0 + 0.5 * ar);
      // double p3 = p2 + c / lambda_l;
      // double p4 = p3 + c / lambda_r;

      // double var, accept;
      // double u, v;              /* random variates */

    // TryAgain:

      // /* generate random variates, u specifies which region: Tri, Par, Tail */
      // u = gsl_rng_uniform (rng) * p4;
      // v = gsl_rng_uniform (rng);

      // if (u <= p1)
        // {
          // /* Triangular region */
          // ix = (int) (xm - p1 * v + u);
          // goto Finish;
        // }
      // else if (u <= p2)
        // {
          // /* Parallelogram region */
          // double x = xl + (u - p1) / c;
          // v = v * c + 1.0 - fabs (x - xm) / p1;
          // if (v > 1.0 || v <= 0.0)
            // goto TryAgain;
          // ix = (int) x;
        // }
      // else if (u <= p3)
        // {
          // /* Left tail */
          // ix = (int) (xl + log (v) / lambda_l);
          // if (ix < 0)
            // goto TryAgain;
          // v *= ((u - p2) * lambda_l);
        // }
      // else
        // {
          // /* Right tail */
          // ix = (int) (xr - log (v) / lambda_r);
          // if (ix > (double) n)
            // goto TryAgain;
          // v *= ((u - p3) * lambda_r);
        // }

      // /* At this point, the goal is to test whether v <= f(x)/f(m) 
       // *
       // *  v <= f(x)/f(m) = (m!(n-m)! / (x!(n-x)!)) * (p/q)^{x-m}
       // *
       // */

      // /* Here is a direct test using logarithms.  It is a little
       // * slower than the various "squeezing" computations below, but
       // * if things are working, it should give exactly the same answer
       // * (given the same random number seed).  */

// #ifdef DIRECT
      // var = log (v);

      // accept =
        // LNFACT (m) + LNFACT (n - m) - LNFACT (ix) - LNFACT (n - ix)
        // + (ix - m) * log (p / q);

// #else /* SQUEEZE METHOD */

      // /* More efficient determination of whether v < f(x)/f(M) */

      // k = abs (ix - m);

      // if (k <= FAR_FROM_MEAN)
        // {
          // /* 
           // * If ix near m (ie, |ix-m|<FAR_FROM_MEAN), then do
           // * explicit evaluation using recursion relation for f(x)
           // */
          // double g = (n + 1) * s;
          // double f = 1.0;

          // var = v;

          // if (m < ix)
            // {
              // int i;
              // for (i = m + 1; i <= ix; i++)
                // {
                  // f *= (g / i - s);
                // }
            // }
          // else if (m > ix)
            // {
              // int i;
              // for (i = ix + 1; i <= m; i++)
                // {
                  // f /= (g / i - s);
                // }
            // }

          // accept = f;
        // }
      // else
        // {
          // /* If ix is far from the mean m: k=ABS(ix-m) large */

          // var = log (v);

          // if (k < npq / 2 - 1)
            // {
              // /* "Squeeze" using upper and lower bounds on
               // * log(f(x)) The squeeze condition was derived
               // * under the condition k < npq/2-1 */
              // double amaxp =
                // k / npq * ((k * (k / 3.0 + 0.625) + (1.0 / 6.0)) / npq + 0.5);
              // double ynorm = -(k * k / (2.0 * npq));
              // if (var < ynorm - amaxp)
                // goto Finish;
              // if (var > ynorm + amaxp)
                // goto TryAgain;
            // }

          // /* Now, again: do the test log(v) vs. log f(x)/f(M) */

// #if USE_EXACT
          // /* This is equivalent to the above, but is a little (~20%) slower */
          // /* There are five log's vs three above, maybe that's it? */

          // accept = LNFACT (m) + LNFACT (n - m)
            // - LNFACT (ix) - LNFACT (n - ix) + (ix - m) * log (p / q);

// #else /* USE STIRLING */
          // /* The "#define Stirling" above corresponds to the first five
           // * terms in asymptoic formula for
           // * log Gamma (y) - (y-0.5)log(y) + y - 0.5 log(2*pi);
           // * See Abramowitz and Stegun, eq 6.1.40
           // */

          // /* Note below: two Stirling's are added, and two are
           // * subtracted.  In both K+S, and in the ranlib
           // * implementation, all four are added.  I (jt) believe that
           // * is a mistake -- this has been confirmed by personal
           // * correspondence w/ Dr. Kachitvichyanukul.  Note, however,
           // * the corrections are so small, that I couldn't find an
           // * example where it made a difference that could be
           // * observed, let alone tested.  In fact, define'ing Stirling
           // * to be zero gave identical results!!  In practice, alv is
           // * O(1), ranging 0 to -10 or so, while the Stirling
           // * correction is typically O(10^{-5}) ...setting the
           // * correction to zero gives about a 2% performance boost;
           // * might as well keep it just to be pendantic.  */

          // {
            // double x1 = ix + 1.0;
            // double w1 = n - ix + 1.0;
            // double f1 = fm + 1.0;
            // double z1 = n + 1.0 - fm;

            // accept = xm * log (f1 / x1) + (n - m + 0.5) * log (z1 / w1)
              // + (ix - m) * log (w1 * p / (x1 * q))
              // + Stirling (f1) + Stirling (z1) - Stirling (x1) - Stirling (w1);
          // }
// #endif
// #endif
        // }


      // if (var <= accept)
        // {
          // goto Finish;
        // }
      // else
        // {
          // goto TryAgain;
        // }
    // }

// Finish:

  // return (flipped) ? (n - ix) : (unsigned int)ix;
// }




