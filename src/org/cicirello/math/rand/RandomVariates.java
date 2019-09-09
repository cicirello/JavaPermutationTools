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
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return median + scale * StrictMath.tan(StrictMath.PI * (ThreadLocalRandom.current().nextDouble() - 0.5));
	}
	
	/**
	 * Generates a pseudorandom number from a Cauchy distribution with median 0
	 * and chosen scale parameter.
	 * @param scale The scale parameter of the Cauchy.
	 * @return a pseudorandom number from a Cauchy distribution
	 */
	public static double nextCauchy(double scale) {
		// Inverse Method:
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return scale * StrictMath.tan(StrictMath.PI * (ThreadLocalRandom.current().nextDouble() - 0.5));
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
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return median + scale * StrictMath.tan(StrictMath.PI * (r.nextDouble() - 0.5));
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
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return scale * StrictMath.tan(StrictMath.PI * (r.nextDouble() - 0.5));
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
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return median + scale * StrictMath.tan(StrictMath.PI * (r.nextDouble() - 0.5));
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
		// I'm tempted to map u==0 to Double.NEGATIVE_INFINITY, and u==1
		// to Double.POSTIVE_INFINITY (where u=r.nextDouble()).  
		// However, u will never equal 1.0 since nextDouble's
		// return is exclusive of 1.0, and it doesn't seem right to consider the special case
		// on the one side but not the other.  The maximum absolute value that the call to tan
		// below gives appears to be on the order of 1.6ish * 10 to the power 16, which is
		// well off from the min/max double values.
		return scale * StrictMath.tan(StrictMath.PI * (r.nextDouble() - 0.5));
	}
	
}