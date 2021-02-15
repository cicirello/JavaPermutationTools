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
 * @version 2.13.2021
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
	 * safe, and efficient (i.e., non-blocking), for use with threads.  However, 
	 * it does not use ThreadLocalRandom.nextInt(int bound)
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
		return nextInt(bound, ThreadLocalRandom.current());
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
	 * safe, and efficient (i.e., non-blocking), for use with threads.  
	 * However, it does not use ThreadLocalRandom.nextInt(int bound)
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
	 * method.  Instead, our nextBiasedInt(int bound) 
	 * method computes a random int in the target interval
	 * via a multiplication and a shift, 
	 * rather than the more common mod.  This method does not
	 * correct for bias via rejection sampling, 
	 * and thus some values in the interval [0, bound)
	 * may be more likely than others.  There is no 
	 * bias for bound values that are powers of 2.
	 * Otherwise, the lower the value of bound, the less bias; and the higher
	 * the value of bound, the more bias.  
	 * If your bound is relatively low, and if your application
	 * does not require strict uniformity, 
	 * then this method is significantly faster than any
	 * approach that corrects for bias.  We started with  
	 * the algorithm proposed in the article: Daniel Lemire, "Fast Random Integer 
	 * Generation in an Interval," ACM Transactions 
	 * on Modeling and Computer Simulation, 29(1), 2019.
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
	
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleReservoir(int n, int k, int[] result) {
		return sampleReservoir(n, k, result, ThreadLocalRandom.current());
	}
	
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions 
	 * on Mathematical Software.  The runtime is O(n)
	 * and it generates O(n-k) random numbers.  
	 * Thus, it is an especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleReservoir(int n, int k, int[] result, Random gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		for (int i = 0; i < k; i++) {
			result[i] = i;
		}
		for (int i = k; i < n; i++) {
			int j = nextInt(i+1, gen);
			if (j < k) {
				result[j] = i;
			}
		}
		return result;
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions 
	 * on Mathematical Software.  The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleReservoir(int n, int k, int[] result, SplittableRandom gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		for (int i = 0; i < k; i++) {
			result[i] = i;
		}
		for (int i = k; i < n; i++) {
			int j = nextInt(i+1, gen);
			if (j < k) {
				result[j] = i;
			}
		}
		return result;
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>This implements the algorithm SELECT of S. Goodman and S. Hedetniemi, as described in:
	 * J Ernvall, O Nevalainen, "An Algorithm for Unbiased Random Sampling," The 
	 * Computer Journal, 25(1):45-47, 1982.</p>
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] samplePool(int n, int k, int[] result) {
		return samplePool(n, k, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>
	 * <p>This implements the algorithm SELECT of S. Goodman and S. Hedetniemi, as described in:
	 * J Ernvall, O Nevalainen, "An Algorithm for Unbiased Random Sampling," The 
	 * Computer Journal, 25(1):45-47, 1982.</p>
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] samplePool(int n, int k, int[] result, SplittableRandom gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		int[] pool = new int[n];
		for (int i = 0; i < n; i++) pool[i] = i;
		int remaining = n;
		for (int i = 0; i < k; i++) {
			int temp = nextInt(remaining, gen);
			result[i] = pool[temp];
			remaining--;
			pool[temp] = pool[remaining];
		}
		return result;
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>
	 * <p>This implements the algorithm SELECT of S. Goodman and S. Hedetniemi, as described in:
	 * J Ernvall, O Nevalainen, "An Algorithm for Unbiased Random Sampling," The 
	 * Computer Journal, 25(1):45-47, 1982.</p>
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] samplePool(int n, int k, int[] result, Random gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		int[] pool = new int[n];
		for (int i = 0; i < n; i++) pool[i] = i;
		int remaining = n;
		for (int i = 0; i < k; i++) {
			int temp = nextInt(remaining, gen);
			result[i] = pool[temp];
			remaining--;
			pool[temp] = pool[remaining];
		}
		return result;
	}
	
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * Just like sampleReservoir, the sampleInsertion method only requires O(1) extra space,
	 * while samplePool requires O(n) extra space.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleInsertion(int n, int k, int[] result) {
		return sampleInsertion(n, k, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * Just like sampleReservoir, the sampleInsertion method only requires O(1) extra space,
	 * while samplePool requires O(n) extra space.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen The source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleInsertion(int n, int k, int[] result, SplittableRandom gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		for (int i = 0; i < k; i++) {
			int temp = nextInt(n-i, gen);
			int j = k-i; 
			for ( ; j < k; j++) {
				if (temp >= result[j]) {
					temp++;
					result[j-1] = result[j];
				} else break;
			}
			result[j-1] = temp;
		}
		return result;
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * Just like sampleReservoir, the sampleInsertion method only requires O(1) extra space,
	 * while samplePool requires O(n) extra space.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen The source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sampleInsertion(int n, int k, int[] result, Random gen) {
		if (k > n) throw new IllegalArgumentException("k must be no greater than n");
		if (result == null || result.length < k) result = new int[k];
		for (int i = 0; i < k; i++) {
			int temp = nextInt(n-i, gen);
			int j = k-i; 
			for ( ; j < k; j++) {
				if (temp >= result[j]) {
					temp++;
					result[j-1] = result[j];
				} else break;
			}
			result[j-1] = temp;
		}
		return result;
	}
	
	/**
	 * <p>Generates a random sample, without replacement, from the
	 * set of integers in the interval [0, n).  Each of the n integers 
	 * has a probability p of inclusion in the sample.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * source of randomness, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param p The probability that each of the n integers is included in the sample.
	 * @return An array containing the sample.
	 * @since 2.0
	 */
	public static int[] sample(int n, double p) {
		return sample(n, p, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample, without replacement, from the
	 * set of integers in the interval [0, n).  Each of the n integers 
	 * has a probability p of inclusion in the sample.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * source of randomness, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param p The probability that each of the n integers is included in the sample.
	 * @param r The source of randomness.
	 * @return An array containing the sample.
	 * @since 2.0
	 */
	public static int[] sample(int n, double p, SplittableRandom r) {
		if (p <= 0) {
			return new int[0];
		} else if (p >= 1) {
			int[] result = new int[n];
			for (int i = 0; i < n; i++) result[i] = i;
			return result;
		} else {
			return sample(n, RandomVariates.nextBinomial(n, p, r), null, r);
		}
	}
	
	/**
	 * <p>Generates a random sample, without replacement, from the
	 * set of integers in the interval [0, n).  Each of the n integers 
	 * has a probability p of inclusion in the sample.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * source of randomness, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param p The probability that each of the n integers is included in the sample.
	 * @param r The source of randomness.
	 * @return An array containing the sample.
	 * @since 2.0
	 */
	public static int[] sample(int n, double p, Random r) {
		if (p <= 0) {
			return new int[0];
		} else if (p >= 1) {
			int[] result = new int[n];
			for (int i = 0; i < n; i++) result[i] = i;
			return result;
		} else {
			return sample(n, RandomVariates.nextBinomial(n, p, r), null, r);
		}
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>
	 * <p>This method chooses among the RandomIndexer.samplePool, 
	 * RandomIndexer.sampleReservoir, and RandomIndexer.sampleInsertion 
	 * methods based on the values of n and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sample(int n, int k, int[] result) {
		if (k + k < n) {
			if (k * k < n) return sampleInsertion(n, k, result, ThreadLocalRandom.current());
			else return samplePool(n, k, result, ThreadLocalRandom.current());
		} else return sampleReservoir(n, k, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>
	 * <p>This method chooses among the RandomIndexer.samplePool, 
	 * RandomIndexer.sampleReservoir, and RandomIndexer.sampleInsertion 
	 * methods based on the values of n and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sample(int n, int k, int[] result, SplittableRandom gen) {
		if (k + k < n) {
			if (k * k < n) return sampleInsertion(n, k, result, gen);
			else return samplePool(n, k, result, gen);
		} else return sampleReservoir(n, k, result, gen);
	}
	
	/**
	 * <p>Generates a random sample of k integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose k combinations are equally
	 * likely.</p>
	 * <p>This method chooses among the RandomIndexer.samplePool, 
	 * RandomIndexer.sampleReservoir, and RandomIndexer.sampleInsertion 
	 * methods based on the values of n and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param k The size of the desired sample.
	 * @param result An array to hold the sample that is generated.  If result is null
	 * or if result.length is less than k, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the sample of k randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if k &gt; n.
	 * @throws NegativeArraySizeException if k &lt; 0.
	 */
	public static int[] sample(int n, int k, int[] result, Random gen) {
		if (k + k < n) {
			if (k * k < n) return sampleInsertion(n, k, result, gen);
			else return samplePool(n, k, result, gen);
		} else return sampleReservoir(n, k, result, gen);
	}
	
	/**
	 * <p>Generates a random sample of 2 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 2 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, and efficient (i.e., non-blocking), 
	 * for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).
	 * @throws IllegalArgumentException if n &lt; 2.
	 */
	public static int[] nextIntPair(int n, int[] result) {
		return nextIntPair(n, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 2 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 2 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 2.
	 */
	public static int[] nextIntPair(int n, int[] result, SplittableRandom gen) {
		if (result == null || result.length < 2) result = new int[2];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		if (result[1] >= result[0]) {
			result[1]++;
		} 
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 2 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 2 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 2.
	 */
	public static int[] nextIntPair(int n, int[] result, Random gen) {
		if (result == null || result.length < 2) result = new int[2];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		if (result[1] >= result[0]) {
			result[1]++;
		} 
		return result;
	}
	
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result) {
		return nextIntTriple(n, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param sort If true, the result is sorted in increasing order; otherwise it is in arbitrary order.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result, boolean sort) {
		return nextIntTriple(n, result, sort, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param sort If true, the result is sorted in increasing order; otherwise it is in arbitrary order.
	 * @param gen The source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result, boolean sort, SplittableRandom gen) {
		if (result == null || result.length < 3) result = new int[3];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		result[2] = nextInt(n-2, gen);
		if (sort) adjustSortTriple(result);
		else adjustTriple(result);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param sort If true, the result is sorted in increasing order; otherwise it is in arbitrary order.
	 * @param gen The source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result, boolean sort, Random gen) {
		if (result == null || result.length < 3) result = new int[3];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		result[2] = nextInt(n-2, gen);
		if (sort) adjustSortTriple(result);
		else adjustTriple(result);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result, SplittableRandom gen) {
		if (result == null || result.length < 3) result = new int[3];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		result[2] = nextInt(n-2, gen);
		adjustTriple(result);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, without replacement, from the
	 * set of integers in the interval [0, n).  All n choose 3 combinations are equally
	 * likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers from the interval [0, n).  
	 * @throws IllegalArgumentException if n &lt; 3.
	 */
	public static int[] nextIntTriple(int n, int[] result, Random gen) {
		if (result == null || result.length < 3) result = new int[3];
		result[0] = nextInt(n, gen);
		result[1] = nextInt(n-1, gen);
		result[2] = nextInt(n-2, gen);
		adjustTriple(result);
		return result;
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.
	 * Each position in the result is equally likely true or false.</p>
	 * <p>Runtime: O(n).</p>
	 * <p>This method uses ThreadLocalRandom as the source of randomness,
	 * and is thus safe, and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The length of the array mask.
	 * @return An array of n randomly generated boolean values.
	 */
	public static boolean[] arrayMask(int n) {
		return arrayMask(n, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.
	 * Each position in the result is equally likely true or false.</p>
	 * <p>Runtime: O(n).</p>
	 *
	 * @param n The length of the array mask.
	 * @param gen The source of randomness.
	 * @return An array of n randomly generated boolean values.
	 */
	public static boolean[] arrayMask(int n, SplittableRandom gen) {
		boolean[] result = new boolean[n];
		for (int i = 0; i < n; i++) {
			result[i] = gen.nextBoolean();
		}
		return result;
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.
	 * Each position in the result is equally likely true or false.</p>
	 * <p>Runtime: O(n).</p>
	 *
	 * @param n The length of the array mask.
	 * @param gen The source of randomness.
	 * @return An array of n randomly generated boolean values.
	 */
	public static boolean[] arrayMask(int n, Random gen) {
		boolean[] result = new boolean[n];
		for (int i = 0; i < n; i++) {
			result[i] = gen.nextBoolean();
		}
		return result;
	}
	
	
	/**
	 * <p>Generates an "array mask" of a specified length and specified number of true values,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(min(n, k<sup>2</sup>)), and it uses O(min(k, n-k)) random numbers.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The length of the array mask.
	 * @param k The desired number of true values, which must be no greater than n.
	 * @return An array of n boolean values, exactly k of which are equal to true.
	 */
	public static boolean[] arrayMask(int n, int k) {
		return arrayMask(n, k, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length and specified number of true values,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(min(n, k<sup>2</sup>)), and it uses O(min(k, n-k)) random numbers.</p>
	 *
	 * @param n The length of the array mask.
	 * @param k The desired number of true values, which must be no greater than n.
	 * @param gen The source of randomness.
	 * @return An array of n boolean values, exactly k of which are equal to true.
	 */
	public static boolean[] arrayMask(int n, int k, SplittableRandom gen) {
		boolean[] result = new boolean[n];
		if (k >= n) {
			for (int i = 0; i < n; i++) result[i] = true;
		} else if (k > 0) {
			int[] indexes = sample(n, k, null, gen);
			for (int i = 0; i < k; i++) {
				result[indexes[i]] = true;
			}
		}
		return result;
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length and specified number of true values,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(min(n, k<sup>2</sup>)), and it uses O(min(k, n-k)) random numbers.</p>
	 *
	 * @param n The length of the array mask.
	 * @param k The desired number of true values, which must be no greater than n.
	 * @param gen The source of randomness.
	 * @return An array of n boolean values, exactly k of which are equal to true.
	 */
	public static boolean[] arrayMask(int n, int k, Random gen) {
		boolean[] result = new boolean[n];
		if (k >= n) {
			for (int i = 0; i < n; i++) result[i] = true;
		} else if (k > 0) {
			int[] indexes = sample(n, k, null, gen);
			for (int i = 0; i < k; i++) {
				result[indexes[i]] = true;
			}
		}
		return result;
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(n), and it uses O(n) random doubles.</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The length of the array mask.
	 * @param p The probability that an element of the result is true.
	 * @return An array of n boolean values, such that each element is true with probability p.
	 * @since 1.5
	 */
	public static boolean[] arrayMask(int n, double p) {
		return arrayMask(n, p, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(n), and it uses O(n) random doubles.</p>
	 *
	 * @param n The length of the array mask.
	 * @param p The probability that an element of the result is true.
	 * @param gen The source of randomness.
	 * @return An array of n boolean values, such that each element is true with probability p.
	 * @since 1.5
	 */
	public static boolean[] arrayMask(int n, double p, SplittableRandom gen) {
		boolean[] result = new boolean[n];
		if (p >= 1) {
			for (int i = 0; i < n; i++) result[i] = true;
		} else if (p > 0) {
			int[] s = sample(n, p, gen);
			for (int i = 0; i < s.length; i++) {
				result[s[i]] = true;
			}
		}
		return result;
	}
	
	/**
	 * <p>Generates an "array mask" of a specified length,
	 * where an "array mask" is an array of boolean values of the same length as another array.</p>
	 * <p>Runtime: O(n), and it uses O(n) random doubles.</p>
	 *
	 * @param n The length of the array mask.
	 * @param p The probability that an element of the result is true.
	 * @param gen The source of randomness.
	 * @return An array of n boolean values, such that each element is true with probability p.
	 * @since 1.5
	 */
	public static boolean[] arrayMask(int n, double p, Random gen) {
		boolean[] result = new boolean[n];
		if (p >= 1) {
			for (int i = 0; i < n; i++) result[i] = true;
		} else if (p > 0) {
			int[] s = sample(n, p, gen);
			for (int i = 0; i < s.length; i++) {
				result[s[i]] = true;
			}
		}
		return result;
	}
	
	
	
	/**
	 * <p>Generates a random sample of 2 integers, i, j, without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window.  
	 * All pairs that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the pair.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @return An array containing the pair of 
	 * randomly chosen integers, i, j, 
	 * from the interval [0, n), such that |i-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 1 or n &lt; 2.
	 */
	public static int[] nextWindowedIntPair(int n, int window, int[] result) {
		return nextWindowedIntPair(n, window, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 2 integers, i, j, without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window.  
	 * All pairs that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the pair.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers, i, j, 
	 * from the interval [0, n), such that |i-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 1 or n &lt; 2.
	 */
	public static int[] nextWindowedIntPair(int n, int window, int[] result, SplittableRandom gen) {
		if (window >= n - 1) return nextIntPair(n, result, gen);
		if (result == null || result.length < 2) result = new int[2];
		final int z1 = n - window;
		final int z2 = z1 + z1;
		int i = nextInt(z2 + window - 1, gen);
		int j = nextInt(window, gen);
		setAndAdjustWindowedPair(result, i, j, z1, z2);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 2 integers, i, j, without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window.  
	 * All pairs that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the pair.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 2, then this method will construct an array for the result. 
	 * @param gen Source of randomness.
	 * @return An array containing the pair of 
	 * randomly chosen integers, i, j, 
	 * from the interval [0, n), such that |i-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 1 or n &lt; 2.
	 */
	public static int[] nextWindowedIntPair(int n, int window, int[] result, Random gen) {
		if (window >= n - 1) return nextIntPair(n, result, gen);
		if (result == null || result.length < 2) result = new int[2];
		final int z1 = n - window;
		final int z2 = z1 + z1;
		int i = nextInt(z2 + window - 1, gen);
		int j = nextInt(window, gen);
		setAndAdjustWindowedPair(result, i, j, z1, z2);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result) {
		return nextWindowedIntTriple(n, window, result, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 * <p>This method uses ThreadLocalRandom as the 
	 * pseudorandom number generator, and is thus safe, 
	 * and efficient (i.e., non-blocking), for use with threads.</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result.
	 * @param sort If true, the result is sorted in increasing order, otherwise it is in random order.
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 * @since 2.0
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result, boolean sort) {
		return nextWindowedIntTriple(n, window, result, sort, ThreadLocalRandom.current());
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param gen The source of randomness.
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result, SplittableRandom gen) {
		if (window >= n - 1) return nextIntTriple(n, result, gen);
		if (result == null || result.length < 3) result = new int[3];
		final int z1 = n - window;
		final int z3 = 3*z1;
		int i = nextInt(z3 + window - 2, gen);
		int j = nextInt(window, gen);
		int k = nextInt(window - 1, gen);
		setAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result.
	 * @param sort If true, the result is sorted in increasing order, otherwise it is in random order.
	 * @param gen The source of randomness.
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 * @since 2.0
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result, boolean sort, SplittableRandom gen) {
		if (window >= n - 1) return nextIntTriple(n, result, sort, gen);
		if (result == null || result.length < 3) result = new int[3];
		final int z1 = n - window;
		final int z3 = 3*z1;
		int i = nextInt(z3 + window - 2, gen);
		int j = nextInt(window, gen);
		int k = nextInt(window - 1, gen);
		if (sort) sortSetAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		else setAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param gen The source of randomness.
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result, Random gen) {
		if (window >= n - 1) return nextIntTriple(n, result, gen);
		if (result == null || result.length < 3) result = new int[3];
		final int z1 = n - window;
		final int z3 = 3*z1;
		int i = nextInt(z3 + window - 2, gen);
		int j = nextInt(window, gen);
		int k = nextInt(window - 1, gen);
		setAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		return result;
	}
	
	/**
	 * <p>Generates a random sample of 3 integers, i, j, k without replacement, from the
	 * set of integers in the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * All triples that satisfy the window constraint are equally likely.</p>
	 * <p>The runtime is O(1).</p>
	 *
	 * @param n The number of integers to choose from.
	 * @param window The maximum difference between the integers of the triple.
	 * @param result An array to hold the pair that is generated.  If result is null
	 * or if result.length is less than 3, then this method will construct an array for the result. 
	 * @param sort If true, the result is sorted in increasing order, otherwise it is in random order.
	 * @param gen The source of randomness.
	 * @return An array containing the triple of 
	 * randomly chosen integers, i, j, k 
	 * from the interval [0, n), such that |i-j| &le; window, and 
	 * |i-k| &le; window, and |k-j| &le; window.  
	 * @throws IllegalArgumentException if window &lt; 2 or n &lt; 3.
	 * @since 2.0
	 */
	public static int[] nextWindowedIntTriple(int n, int window, int[] result, boolean sort, Random gen) {
		if (window >= n - 1) return nextIntTriple(n, result, sort, gen);
		if (result == null || result.length < 3) result = new int[3];
		final int z1 = n - window;
		final int z3 = 3*z1;
		int i = nextInt(z3 + window - 2, gen);
		int j = nextInt(window, gen);
		int k = nextInt(window - 1, gen);
		if (sort) sortSetAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		else setAndAdjustWindowedTriple(result, i, j, k, z1, z3);
		return result;
	}
	
	
	
	private static void adjustTriple(int[] result) {
		if (result[1] >= result[0]) {
			result[1]++;
			if (result[2] >= result[0]) {
				result[2]++;
				if (result[2] >= result[1]) result[2]++;
			}
		} else {
			if (result[2] >= result[1]) {
				result[2]++;
				if (result[2] >= result[0]) result[2]++;
			}
		}
	}
	
	private static void adjustSortTriple(int[] result) {
		if (result[1] >= result[0]) {
			result[1]++;
		} else {
			int temp = result[0];
			result[0] = result[1];
			result[1] = temp;
		}
		if (result[2] >= result[0]) {
			result[2]++;
			if (result[2] >= result[1]) {
				result[2]++;
			} else {
				int temp = result[1];
				result[1] = result[2];
				result[2] = temp;
			}
		} else {
			int temp = result[2];
			result[2] = result[1];
			result[1] = result[0];
			result[0] = temp;
		}
	}
	
	private static void setAndAdjustWindowedPair(int[] result, int i, int j, final int z1, final int z2) {
		if (i < z2) {
			if ((i & 1) == 0) {
				result[0] = i >> 1;
				result[1] = result[0] + 1 + j;
			} else {
				result[1] = i >> 1;
				result[0] = result[1] + 1 + j;
			}
		} else {
			i -= z1;
			j += z1;
			if (i >= j) result[0] = i + 1;
			else result[0] = i;
			result[1] = j;
		}
	}
	
	
	private static void setAndAdjustWindowedTriple(int[] result, int i, int j, int k, final int z1, final int z3) {
		if (k >= j) {
			k++;
		}
		if (i < z3) {
			int q = i / 3;
			int r = i % 3;
			result[r] = q;
			if (r==0) {
				result[1] = q + 1 + j;
				result[2] = q + 1 + k;
			} else if (r==1) {
				result[0] = q + 1 + j;
				result[2] = q + 1 + k;
			} else {
				result[0] = q + 1 + j;
				result[1] = q + 1 + k;
			}
		} else {
			i = i - z3 + z1;
			j += z1;
			k += z1;
			if (j < k) {
				if (i >= j) {
					i++;
					if (i >= k) i++;
				}
			} else {
				if (i >= k) {
					i++;
					if (i >= j) i++;
				}
			}
			result[0] = i;
			result[1] = j;
			result[2] = k;
		}
	}
	
	private static void sortSetAndAdjustWindowedTriple(int[] result, int i, int j, int k, final int z1, final int z3) {
		if (k >= j) {
			k++;
		} else {
			int t = j;
			j = k;
			k = t;
		}
		if (i < z3) {
			int q = i / 3;
			result[0] = q;
			result[1] = q + 1 + j;
			result[2] = q + 1 + k;
		} else {
			i = i - z3 + z1;
			j += z1;
			k += z1;
			if (i >= j) {
				i++;
				result[0] = j;
				if (i >= k) {
					i++;
					result[1] = k;
					result[2] = i;
				} else {
					result[1] = i;
					result[2] = k;
				}
			} else {
				result[0] = i;
				result[1] = j;
				result[2] = k;
			}			
		}
	}
	
}