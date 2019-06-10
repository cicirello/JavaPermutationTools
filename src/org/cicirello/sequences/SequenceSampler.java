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
 
package org.cicirello.sequences;

import org.cicirello.math.rand.RandomIndexer;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * ArraySampler is a class of utility methods related to 
 * efficiently generating random samples of array elements, without replacement.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 1.19.6.10
 * @since 1.5
 *
 */
public final class SequenceSampler {
	
	/**
	 * Class of static utility methods so prevent instantiation
	 * with a private constructor.
	 */
	private SequenceSampler() {};
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static int[] sample(int[] source, int k, int[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static long[] sample(long[] source, int k, long[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static short[] sample(short[] source, int k, short[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static byte[] sample(byte[] source, int k, byte[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sample(char[] source, int k, char[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k chars, without replacement, from a
	 * given source String.  All n choose k combinations are equally
	 * likely, where n is the length of the source String.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length()).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length()
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sample(String source, int k, char[] target) {
		if (2 * k < source.length()) {
			if (k * k < 2 * source.length()) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static double[] sample(double[] source, int k, double[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static float[] sample(float[] source, int k, float[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>
	 * <p>This method chooses among the ArraySampler.samplePool, 
	 * ArraySampler.sampleReservoir, and ArraySampler.sampleInsertion 
	 * methods based on the values of source.length and k.</p>
	 * <p>The runtime is O(min(n, k<sup>2</sup>))
	 * and it generates O(min(k, n-k)) random numbers.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @param <T> The type of array elements.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static <T> T[] sample(T[] source, int k, T[] target) {
		if (2 * k < source.length) {
			if (k * k < 2 * source.length) return sampleInsertion(source, k, target);
			else return samplePool(source, k, target);
		} else return sampleReservoir(source, k, target);
	}

	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static int[] sampleReservoir(int[] source, int k, int[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new int[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static long[] sampleReservoir(long[] source, int k, long[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new long[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static short[] sampleReservoir(short[] source, int k, short[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new short[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static byte[] sampleReservoir(byte[] source, int k, byte[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new byte[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sampleReservoir(char[] source, int k, char[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new char[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k chars, without replacement, from a
	 * given source String.  All n choose k combinations are equally
	 * likely, where n is the length of the source String.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length()).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length()
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sampleReservoir(String source, int k, char[] target) {
		if (k > source.length()) {
			throw new IllegalArgumentException("k must be no greater than length of source");
		}
		if (target == null || target.length < k) target = new char[k];
		for (int i = 0; i < k; i++) {
			target[i] = source.charAt(i);
		}
		for (int i = k; i < source.length(); i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source.charAt(i);
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static double[] sampleReservoir(double[] source, int k, double[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new double[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static float[] sampleReservoir(float[] source, int k, float[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new float[k];
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>Uses the reservoir sampling algorithm (Algorithm R) 
	 * from J. Vitter's 1985 article "Random Sampling
	 * with a Reservoir" from ACM Transactions on Mathematical Software.  
	 * The runtime is O(n)
	 * and it generates O(n-k) random numbers.  Thus, it is an 
	 * especially good choice as k
	 * approaches n.  Only constant extra space required.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @param <T> The type of array elements.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] sampleReservoir(T[] source, int k, T[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null) {
			target = (T[])Array.newInstance(source.getClass().getComponentType(), k);
		} else if (target.length < k) {
			target = (T[])Array.newInstance(target.getClass().getComponentType(), k);
		}
		for (int i = 0; i < k; i++) {
			target[i] = source[i];
		}
		for (int i = k; i < source.length; i++) {
			int j = RandomIndexer.nextInt(i+1);
			if (j < k) {
				target[j] = source[i];
			}
		}
		return target;
	}
	
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static int[] samplePool(int[] source, int k, int[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new int[k];
		int[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static long[] samplePool(long[] source, int k, long[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new long[k];
		long[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static short[] samplePool(short[] source, int k, short[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new short[k];
		short[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static byte[] samplePool(byte[] source, int k, byte[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new byte[k];
		byte[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] samplePool(char[] source, int k, char[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new char[k];
		char[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k chars, without replacement, from a
	 * given source String.  All n choose k combinations are equally
	 * likely, where n is the length of the source String.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source to sample.
	 * @param k The number of random samples (must be no greater than source.length()).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length()
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] samplePool(String source, int k, char[] target) {
		if (k > source.length()) {
			throw new IllegalArgumentException("k must be no greater than length of source");
		}
		return samplePool(source.toCharArray(), k, target);
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static double[] samplePool(double[] source, int k, double[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new double[k];
		double[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static float[] samplePool(float[] source, int k, float[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new float[k];
		float[] pool = source.clone();
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(n)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than sampleReservoir when k &lt; n-k.
	 * However, this uses O(n) extra space, whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @param <T> The type of array elements.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] samplePool(T[] source, int k, T[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null) {
			target = (T[]) Array.newInstance(source.getClass().getComponentType(), k);
		} else if (target.length < k) {
			target = (T[])Array.newInstance(target.getClass().getComponentType(), k);
		}
		T[] pool = Arrays.copyOf(source, source.length);
		int remaining = pool.length;
		for (int i = 0; i < k; i++) {
			int j = RandomIndexer.nextInt(remaining);
			target[i] = pool[j];
			remaining--;
			pool[j] = pool[remaining];
		}
		return target;
	}
	
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * Just like sampleReservoir, the sampleInsertion method only requires O(1) extra space,
	 * while samplePool requires O(n) extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static int[] sampleInsertion(int[] source, int k, int[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		target = RandomIndexer.sampleInsertion(source.length, k, target);
		for (int i = 0; i < k; i++) {
			target[i] = source[target[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static long[] sampleInsertion(long[] source, int k, long[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new long[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static short[] sampleInsertion(short[] source, int k, short[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new short[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static byte[] sampleInsertion(byte[] source, int k, byte[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new byte[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sampleInsertion(char[] source, int k, char[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new char[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k chars, without replacement, from a
	 * given source String.  All n choose k combinations are equally
	 * likely, where n is the length of the source String.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source to sample.
	 * @param k The number of random samples (must be no greater than source.length()).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length()
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static char[] sampleInsertion(String source, int k, char[] target) {
		if (k > source.length()) {
			throw new IllegalArgumentException("k must be no greater than length of source");
		}
		if (target == null || target.length < k) target = new char[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length(), k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source.charAt(indexes[i]);
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static double[] sampleInsertion(double[] source, int k, double[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new double[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	public static float[] sampleInsertion(float[] source, int k, float[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null || target.length < k) target = new float[k];
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	/**
	 * <p>Generates a random sample of k elements, without replacement, from a
	 * given source array.  All n choose k combinations are equally
	 * likely, where n is the length of the source array.</p>  
	 * <p>The runtime is O(k<sup>2</sup>)
	 * and it generates O(k) random numbers.  Thus, it is a better 
	 * choice than both sampleReservoir and samplePool when k<sup>2</sup> &lt; n.
	 * However, this uses O(k) extra space (better than samplePool), 
	 * whereas the reservoir algorithm
	 * uses no extra space.</p>
	 * <p>This method is safe to use with threads, as it uses ThreadLocalRandom
	 * as the underlying source of randomness.</p>
	 *
	 * @param source The source array to sample.
	 * @param k The number of random samples (must be no greater than source.length).
	 * @param target An array to hold the result.  If target is null or target.length is less than k, 
	 * then this method will construct a new array for the result.
	 * @param <T> The type of array elements.
	 * @return An array containing the random sample.
	 * @throws IllegalArgumentException if k &gt; source.length
	 * @throws NegativeArraySizeException if k &lt; 0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] sampleInsertion(T[] source, int k, T[] target) {
		if (k > source.length) {
			throw new IllegalArgumentException("k must be no greater than length of source array");
		}
		if (target == null) {
			target = (T[])Array.newInstance(source.getClass().getComponentType(), k);
		} else if (target.length < k) {
			target = (T[])Array.newInstance(target.getClass().getComponentType(), k);
		}
		int[] indexes = RandomIndexer.sampleInsertion(source.length, k, null);
		for (int i = 0; i < k; i++) {
			target[i] = source[indexes[i]];
		}
		return target;
	}
	
	
	
}