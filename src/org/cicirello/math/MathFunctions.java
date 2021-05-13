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

package org.cicirello.math;

/**
 * MathFunctions is a class of utility methods that implement various mathematical functions.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 5.13.2021
 *
 */
public final class MathFunctions {
	
	/*
	 * Utility class of static methods, so constructor is private to prevent
	 * instantiation.
	 */
	private MathFunctions() {}
	
	/**
	 * Computes a<sup>b</sup>, where the exponent b is an integer.
	 * This is more efficient than using {@link Math#pow} since
	 * it exploits the integer type of the exponent.
	 * @param a The base.
	 * @param b The exponent.
	 * @return a<sup>b</sup>
	 */
	public static double pow(double a, int b) {
		if (b >= 0) {
			double c = 1;
			while (b > 0) {
				if ((b & 1) == 1) c *= a;
				b = b >> 1;
				a = a * a;
			}
			return c;
		} else {
			return 1.0 / pow(a, -b);
		} 
	}
	
	/**
	 * Implementation of the natural log of the absolute value of
	 * the gamma function.
	 * @param n input parameter to the function
	 * @return ln(abs(gamma(n)))
	 */
	public static double logGamma(double n) {
		if (!Double.isFinite(n)) return n;
		if (n >= 2.556348e305) {
			// input parameter is too large to calculate function
			return Double.POSITIVE_INFINITY;
		} else if (n <= -2.556348e305) {
			// input parameter is too large to calculate function
			return Double.NEGATIVE_INFINITY;
		} else if (n < -34.0) {
			if (n < -4.5035996273704955e15) {
				// inputs lower than this are essentially negative integers
				// due to precision of floating-point numbers.
				// This is the smallest negative double with an ulp less than 1.
				return Double.POSITIVE_INFINITY;
			}
			n = -n; 
			double p = ((long)n);
			if (p == n) return Double.POSITIVE_INFINITY;
			double z = n - p;
			if (z > 0.5) {
				p = p + 1.0;
				z = p - n;
			}
			z = n * Math.sin(z * Math.PI);
			// This check doesn't seem to be necessary given addition of first check in block.
			// if (z == 0.0) return Double.POSITIVE_INFINITY;
			// ln(PI)
			final double LOG_PI = 1.1447298858494002;
			return LOG_PI - Math.log(z) - logGamma(n);
		} else if (n < 13.0) {
			double z = 1.0;
			int p = 0;
			double u = n;
			while (u >= 3.0) {
				p--;
				u = n + p;
				z = z * u;
			}
			while (u < 2.0) {
				if (u == 0.0) return Double.POSITIVE_INFINITY;
				z = z / u;
				p++;
				u = n + p;
			}
			if (z < 0.0) z = -z;
			if (u == 2.0) return Math.log(z);
			p -= 2;
			n = n + p; 
			return Math.log(z) + 
				n * evaluatePolynomial(n, POLY_APPROX_2) / evaluatePolynomial(n, POLY_APPROX_3);
		} else {
			// ln(sqrt(2pi))
			final double LOG_SQRT_PI2 = 0.9189385332046727;
			double q = (n - 0.5) * Math.log(n) - n + LOG_SQRT_PI2; 
			if (n > 1.0e8) return q;
			double p = 1.0 / (n * n);
			if (n >= 1000.0) {
				q = q + ((7.936507936507937E-4 * p - 0.002777777777777778) 
					* p + 0.08333333333333333) / n;
			} else {
				q += evaluatePolynomial(p, STIRLING_EXPANSION_LN_GAMMA) / n;
			}
			return q;
		}
	}
	
	// HELPERS FOR logGamma BEGIN HERE
	
	private static double evaluatePolynomial(double x, double polynomial[]) {
		double result = polynomial[0];
		for (int i = 1; i < polynomial.length; i++) {
			result = result * x + polynomial[i];
		}
		return result;
	}
	
	private static final double[] STIRLING_EXPANSION_LN_GAMMA = {
		8.116141674705085E-4, -5.950619042843014E-4,
		7.936503404577169E-4, -0.002777777777300997,
		0.08333333333333319
	};

	private static final double[] POLY_APPROX_2 = {
		-1378.2515256912086, -38801.631513463784,
		-331612.9927388712, -1162370.974927623,
		-1721737.0082083966, -853555.6642457654
	};

	private static final double[] POLY_APPROX_3 = {
		1.0, 
		-351.81570143652345, -17064.210665188115,
		-220528.59055385445, -1139334.4436798252,
		-2532523.0717758294, -2018891.4143353277,
	};
	
	// HELPERS FOR logGamma END HERE

}