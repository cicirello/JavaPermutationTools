/*
 * Copyright 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
import java.util.concurrent.ThreadLocalRandom;

/**
 * This utility class provides methods for generating random variates from
 * different distributions.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 8.28.2019
 * @since 2.0
 *
 */
public final class RandomVariates {
	
	/*
	 * Utility class of static methods, so constructor is private to prevent
	 * instantiation.
	 */
	private RandomVariates() {}
	
	/**
	 * Generates a pseudorandom integer from a binomial distribution.
	 * The source of randomness is via the {@link ThreadLocalRandom}
	 * class, and thus this method is both safe and efficient for use with threads.
	 * @param n Number of trials for the binomial distribution.
	 * @param p The probability of a successful trial.
	 * @return A pseudorandom integer from a binomial distribution.
	 */
	public static int nextBinomial(int n, double p) {
		return BTPE.nextBinomial(n, p, ThreadLocalRandom.current());
	}
	
	/**
	 * Generates a pseudorandom integer from a binomial distribution.
	 * @param n Number of trials for the binomial distribution.
	 * @param p The probability of a successful trial.
	 * @param r The source of randomness.
	 * @return A pseudorandom integer from a binomial distribution.
	 */
	public static int nextBinomial(int n, double p, Random r) {
		return BTPE.nextBinomial(n, p, r);
	}
	
	/**
	 * Generates a pseudorandom integer from a binomial distribution.
	 * @param n Number of trials for the binomial distribution.
	 * @param p The probability of a successful trial.
	 * @param r The source of randomness.
	 * @return A pseudorandom integer from a binomial distribution.
	 */
	public static int nextBinomial(int n, double p, SplittableRandom r) {
		return BTPE.nextBinomial(n, p, r);
	}	
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution.
	 * @param median The median of the Cauchy.
	 * @param scale The scale parameter of the Cauchy.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double median, double scale) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = ThreadLocalRandom.current().nextDouble();
		if (u == 0.5 && ThreadLocalRandom.current().nextBoolean()) {
			u = -0.5;
		}
		return median + scale * StrictMath.tan(StrictMath.PI * u);
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution with median 0
	 * and chosen scale parameter.
	 * @param scale The scale parameter of the Cauchy.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double scale) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = ThreadLocalRandom.current().nextDouble();
		if (u == 0.5 && ThreadLocalRandom.current().nextBoolean()) {
			u = -0.5;
		}
		return scale * StrictMath.tan(StrictMath.PI * u);
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution.
	 * @param median The median of the Cauchy.
	 * @param scale The scale parameter of the Cauchy.
	 * @param r The source of randomness.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double median, double scale, Random r) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = r.nextDouble();
		if (u == 0.5 && r.nextBoolean()) {
			u = -0.5;
		}
		return median + scale * StrictMath.tan(StrictMath.PI * u);
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution with median 0
	 * and chosen scale parameter.
	 * @param scale The scale parameter of the Cauchy.
	 * @param r The source of randomness.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double scale, Random r) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = r.nextDouble();
		if (u == 0.5 && r.nextBoolean()) {
			u = -0.5;
		}
		return scale * StrictMath.tan(StrictMath.PI * u);
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution.
	 * @param median The median of the Cauchy.
	 * @param scale The scale parameter of the Cauchy.
	 * @param r The source of randomness.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double median, double scale, SplittableRandom r) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = r.nextDouble();
		if (u == 0.5 && r.nextBoolean()) {
			u = -0.5;
		}
		return median + scale * StrictMath.tan(StrictMath.PI * u);
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution with median 0
	 * and chosen scale parameter.
	 * @param scale The scale parameter of the Cauchy.
	 * @param r The source of randomness.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double scale, SplittableRandom r) {
		// Inverse Method:
		//      Mathematically, it should be: median + scale * tan(PI * (u - 0.5)),
		// where u is uniformly random from the interval [0, 1].
		// However, since tan goes through one complete cycle every PI,
		// we can replace it with: median + scale * tan(PI * u), going from
		// 0 to PI, rather than from -PI/2 to PI/2.  This is equivalent
		// as far as generating a random Cauchy variate is concerned, but saves
		// one arithmetic operation.  
		//     At first glance, it may appear as if we might be
		// doubly sampling u == 0 since tan(0)==tan(PI), however, our uniform
		// random numbers are generated from [0, 1), so that right endpoint will never
		// be sampled.
		//     We have one special case to consider.  When u==0.5, we have tan(PI/2),
		// which is undefined.  In the limit, however, tan(PI/2) is infinity.
		// We could map this to the constant for infinity.  However, this would introduce
		// a very slight bias in favor of positive results since our interval considers
		// from tan(0) through tan(PI-epsilon), which doesn't include tan(-PI/2), though it 
		// comes close since tan(PI/2+epsilon)==tan(-PI/2+epsilon).  In the limit, tan(-PI/2)
		// is -infinity.  So mapping tan(PI/2) to infinity would result in one extra value that
		// leads to a positive result relative to the number of values that lead to negative results.
		//     We handle this in the following way.  First, when u==0.5, we generate a 
		// random boolean to control whether u==0.5 means PI/2 or -PI/2.  Second, rather than
		// map to the constants for positive and negative infinity from the Double class, we
		// pass these along to the tan method and let it do its thing numerically, which is
		// a value around 1.6ish * 10 to the power 16 (and negative of that in the case of -PI/2).
		double u = r.nextDouble();
		if (u == 0.5 && r.nextBoolean()) {
			u = -0.5;
		}
		return scale * StrictMath.tan(StrictMath.PI * u);
	}
	
}