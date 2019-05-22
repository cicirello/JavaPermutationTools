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

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.SplittableRandom;

/**
 * RandomIndexer is a class of utility methods related to 
 * efficiently generating random indexes, and combination of indexes, into arrays.
 * The methods of this class neither directly operate, nor depend, on arrays, and can thus
 * be used wherever you need random integer values.  The name of the class is derived
 * from the motivating case, the case of efficiently generating random indexes into an array.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 1.19.5.22
 * @since 1.4
 *
 */
public final class RandomIndexer {
	
	/**
	 * Class of static utility methods, so instantiation is not allowed.
	 */
	private RandomIndexer() {}
	
	/**
	 * <p>Generates a random integer uniformly distributed in the interval: [0, bound).</p>
	 * <p>This method uses ThreadLocalRandom as the pseudorandom number generator, and is thus
	 * safe for use with threads.  However, it does not use ThreadLocalRandom.nextInt(int bound)
	 * method.  Instead, our nextInt(int bound) method is an implementation of 
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.</p>
	 * <p>This method is significantly faster than ThreadLocalRandom.nextInt(int bound).</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		//Commented out lines handle bound that is a power of 2 as a special case
		//Seems to only marginally speed computation in special case while adding to cost of
		//general case.
		//int b1 = bound - 1;
		//if ((bound & b1) == 0) return ThreadLocalRandom.current().nextInt() & b1;
		long product = (long)(ThreadLocalRandom.current().nextInt() & 0x7fffffff) * (long)bound;
		int low31 = (int)product & 0x7fffffff;
		if (low31 < bound) {
			int threshold = (0x80000000-bound) % bound;
			while (low31 < threshold) {
				product = (long)(ThreadLocalRandom.current().nextInt() & 0x7fffffff) * (long)bound;
				low31 = (int)product & 0x7fffffff;
			}
		}
		return (int)(product >> 31);
	}
	
	/**
	 * <p>Generates a random integer uniformly distributed in the interval: [0, bound).</p>
	 * <p>This method uses a SplittableRandom object passed as a parameter 
	 * as the pseudorandom number generator.  
	 * However, it does not use SplittableRandom.nextInt(int bound)
	 * method.  Instead, our nextInt(int bound) method is an implementation of 
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.</p>
	 * <p>This method is significantly faster than SplittableRandom.nextInt(int bound).</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound, SplittableRandom gen) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		//Commented out lines handle bound that is a power of 2 as a special case
		//Seems to only marginally speed computation in special case while adding to cost of
		//general case.
		//int b1 = bound - 1;
		//if ((bound & b1) == 0) return gen.nextInt() & b1;
		long product = (long)(gen.nextInt() & 0x7fffffff) * (long)bound;
		int low31 = (int)product & 0x7fffffff;
		if (low31 < bound) {
			int threshold = (0x80000000-bound) % bound;
			while (low31 < threshold) {
				product = (long)(gen.nextInt() & 0x7fffffff) * (long)bound;
				low31 = (int)product & 0x7fffffff;
			}
		}
		return (int)(product >> 31);
	}
	
	/**
	 * <p>Generates a random integer uniformly distributed in the interval: [0, bound).</p>
	 * <p>This method uses a Random object passed as a parameter 
	 * as the pseudorandom number generator.  
	 * However, it does not use Random.nextInt(int bound)
	 * method.  Instead, our nextInt(int bound) method is an implementation of 
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.</p>
	 * <p>This method is significantly faster than Random.nextInt(int bound).</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound, Random gen) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		//Commented out lines handle bound that is a power of 2 as a special case
		//Seems to only marginally speed computation in special case while adding to cost of
		//general case.
		//int b1 = bound - 1;
		//if ((bound & b1) == 0) return gen.nextInt() & b1;
		long product = (long)(gen.nextInt() & 0x7fffffff) * (long)bound;
		int low31 = (int)product & 0x7fffffff;
		if (low31 < bound) {
			int threshold = (0x80000000-bound) % bound;
			while (low31 < threshold) {
				product = (long)(gen.nextInt() & 0x7fffffff) * (long)bound;
				low31 = (int)product & 0x7fffffff;
			}
		}
		return (int)(product >> 31);
	}
	
	/**
	 * <p>Generates a random integer in the interval: [0, bound).</p>
	 * <p>This method uses ThreadLocalRandom as the pseudorandom number generator, and is thus
	 * safe for use with threads.  However, it does not use ThreadLocalRandom.nextInt(int bound)
	 * method.  Instead, our nextBiasedInt(int bound) method computes a random int in the target interval
	 * via a multiplication and a shift, rather than the more common mod.  This method does not
	 * correct for bias via rejection sampling, and thus some values in the interval [0, bound)
	 * may be more likely than others.  There is no bias for bound values that are powers of 2.
	 * Otherwise, the lower the value of bound, the less bias; and the higher
	 * the value of bound, the more bias.  If your bound is relatively low, and if your application
	 * does not require strict uniformity, then this method is significantly faster than any
	 * approach that corrects for bias.  We started with  
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.
	 * But we removed from it the rejection sampling portion.</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextBiasedInt(int bound) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		return (int)((long)(ThreadLocalRandom.current().nextInt() & 0x7fffffff) * (long)bound >> 31);
	}
	
	/**
	 * <p>Generates a random integer in the interval: [0, bound).</p>
	 * <p>This method uses SplittableRandom as the pseudorandom number generator, and is thus
	 * safe for use with threads.  However, it does not use SplittableRandom.nextInt(int bound)
	 * method.  Instead, our nextBiasedInt(int bound) method computes a random int in the target interval
	 * via a multiplication and a shift, rather than the more common mod.  This method does not
	 * correct for bias via rejection sampling, and thus some values in the interval [0, bound)
	 * may be more likely than others.  There is no bias for bound values that are powers of 2.
	 * Otherwise, the lower the value of bound, the less bias; and the higher
	 * the value of bound, the more bias.  If your bound is relatively low, and if your application
	 * does not require strict uniformity, then this method is significantly faster than any
	 * approach that corrects for bias.  We started with  
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.
	 * But we removed from it the rejection sampling portion.</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextBiasedInt(int bound, SplittableRandom gen) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		return (int)((long)(gen.nextInt() & 0x7fffffff) * (long)bound >> 31);
	}
	
	/**
	 * <p>Generates a random integer in the interval: [0, bound).</p>
	 * <p>This method uses Random as the pseudorandom number generator, and is thus
	 * safe for use with threads.  However, it does not use Random.nextInt(int bound)
	 * method.  Instead, our nextBiasedInt(int bound) method computes a random int in the target interval
	 * via a multiplication and a shift, rather than the more common mod.  This method does not
	 * correct for bias via rejection sampling, and thus some values in the interval [0, bound)
	 * may be more likely than others.  There is no bias for bound values that are powers of 2.
	 * Otherwise, the lower the value of bound, the less bias; and the higher
	 * the value of bound, the more bias.  If your bound is relatively low, and if your application
	 * does not require strict uniformity, then this method is significantly faster than any
	 * approach that corrects for bias.  We started with  
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions on Modeling and Computer Simulation, 29(1), 2019.
	 * But we removed from it the rejection sampling portion.</p>
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextBiasedInt(int bound, Random gen) {
		if (bound < 1) throw new IllegalArgumentException("bound must be positive");
		return (int)((long)(gen.nextInt() & 0x7fffffff) * (long)bound >> 31);
	}
	
}