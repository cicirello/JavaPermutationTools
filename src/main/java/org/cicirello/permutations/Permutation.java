/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 */
package org.cicirello.permutations;

import java.util.random.RandomGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Iterator;
import java.math.BigInteger;

import org.cicirello.math.rand.RandomIndexer;
import org.cicirello.util.Copyable;

/**
 * Representation of a permutation of the integers from 0 to N-1, inclusive.
 * This class provides the functionality to generate random permutations, and to
 * manipulate permutations in a variety of ways.
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, 
 * <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 */
public final class Permutation implements Serializable, Iterable<Permutation>, Copyable<Permutation> {
	
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Raw permutation, which should consist of a permutation of the integers in [0, permutation.length). 
	 */
	private final int[] permutation;
	
	/**
	 * Initializes a random permutation of n integers.  Uses
	 * {@link ThreadLocalRandom} as the source of efficient random number generation.
	 * @param n the length of the permutation
	 */
	public Permutation(int n) {
		permutation = new int[n];
		scramble();
	}
 
	/**
	 * Initializes a random permutation of n integers.
	 * @param n the length of the permutation
	 * @param r A source of randomness.
	 */
	public Permutation(int n, RandomGenerator r) {
		permutation = new int[n];
		scramble(r);
	}
	
	/**
	* Initializes a specific permutation from an integer in mixed radix form representing the chosen
	* permutation.  See the toInteger() method which can be used to generate this value for a given
	* permutation.  The n! permutations of the integers from 0 to n-1 are mapped to the integers from
	* 0..(n!-1).  Runtime of this constructor is O(n^2).
	* @param n The length of the permutation.
	* @param value The integer value of the permutation in the interval: 0..(n!-1).
	*/
	public Permutation(int n, int value) {
		permutation = new int[n];
		for (int i = 0; i < n; i++) {
			permutation[i] = i;   
		}
		for (int i = 0; i < n-1; i++) {
			int j = i + value % (n-i);
			int temp = permutation[j];
			for (int k = j; k > i; k--) {
				permutation[k] = permutation[k-1];
			}
			permutation[i] = temp;
			value = value / (n-i);
		}
	}
	
	/**
	* <p>Initializes a specific permutation from an integer in mixed radix form representing the chosen
	* permutation.  See the toInteger() method which can be used to generate this value for a given
	* permutation.  The n! permutations of the integers from 0 to n-1 are mapped to the integers from
	* 0..(n!-1).  Runtime of this constructor is O(n^2).</p>
	* 
	* <p>Even with the operations on BigInteger objects, the runtime is O(n^2).  It performs O(n^2)
	* operations on primitive values.  It performs only O(n) divisions on BigInteger objects.
	* It can be shown that the amortized cost of all of those divisions is bounded by the
	* most costly division (each division involves progressively smaller numbers).  The
	* largest number involved in a division is the parameter value, which can be at most n!.
	* The number n! consists of O(log((n-1)!)) = O(n log n) digits.  Java's BigInteger division method
	* currently implements the Burnikel-Ziegler algorithm, using the Toom–Cook multiplication algorithm.
	* Dividing m-digit numbers with this combination has a runtime of O(m^1.465 log(m)).  Substituting
	* the number of digits for m, we have: O(n^1.465 log(n)^2.465).  The cost of all of the divisions
	* is thus less asymptotically than the cost of the primitive operations: O(n^2).</p>
	*
	* @param n The length of the permutation.
	* @param value The integer value of the permutation in the interval: 0..(n!-1).
	*/
	public Permutation(int n, BigInteger value) {
		permutation = new int[n];
		for (int i = 0; i < n; i++) {
			permutation[i] = i;   
		}
		for (int i = 0; i < n-1; i++) {
			BigInteger[] divRem = value.divideAndRemainder(BigInteger.valueOf(n-i));
			int j = i + divRem[1].intValue();
			int temp = permutation[j];
			for (int k = j; k > i; k--) {
				permutation[k] = permutation[k-1];
			}
			permutation[i] = temp;
			value = divRem[0];
		}
	}
	
	/**
	 * Initializes a permutation of n integers to be identical to the elements of an array.
	 * @param p An array of integers. Each of the integers in the 
	 * interval [0, p.length) must occur exactly one time each.
	 * @throws IllegalArgumentException if p either contains duplicates, or contains any negative elements, 
	 *         or contains any elements equal or greater than p.length.
	 */
	public Permutation(int[] p) {
		this(p, true);
	}
	
	/**
	 * Internal constructor.
	 *
	 * @param p An array of integers. Each of the integers in the 
	 * interval [0, p.length) must occur exactly one time each.
	 * @param validate If true, constructor verifies that p is valid.
	 * @throws IllegalArgumentException If validate is true, and if p either 
	 *         contains duplicates, or contains any negative elements, 
	 *         or contains any elements equal or greater than p.length.
	 */
	private Permutation(int[] p, boolean validate) {
		if (validate) {
			boolean[] inP = new boolean[p.length];
			for (int e : p) {
				if (e < 0 || e >= p.length) throw new IllegalArgumentException("Elements of p must be in interval [0, p.length)");
				if (inP[e]) throw new IllegalArgumentException("Duplicate elements of p are not allowed.");
				inP[e] = true;
			}
		}
		permutation = p.clone();
	}
	
	/**
	 * Initializes a permutation of n integers to be identical to a given
	 * permutation.
	 * @param p the given permutation.
	 */
	public Permutation(Permutation p) {
		permutation = p.permutation.clone();
	}
	
	/**
	 * Initializes a permutation of the integers in the interval [0, length) based on their relative order
	 * in a permutation p.  If length is greater than or equal to p.length, then this constructor generates a copy of p.
	 * If length is less than p.length, then the new permutation contains the integers, 0, 1, 2, ..., (length - 1), in the 
	 * order that those elements appear in p.  For example, if p is the permutation [ 5, 3, 7, 2, 6, 1, 0, 8, 4] and if length
	 * is 4, this constructor will generate the permutation [3, 2, 1, 0] since 3 appears prior to 2, 2 appears prior to 1, and 1
	 * appears prior to 0 in permutation p.
	 *
	 * @param p the source permutation.
	 * @param length size of new permutation
	 */
	public Permutation(Permutation p, int length) {
		if (length >= p.permutation.length) {
			permutation = p.permutation.clone();
		} else if (length <= 0) {
			permutation = new int[0];
		} else {
			permutation = new int[length];
			int k = 0;
			for (int i = 0; i < p.permutation.length && k < length; i++) {
				if (p.permutation[i] < length) {
					permutation[k] = p.permutation[i];
					k++;
				}
			}
		}
	}
	
	/**
	 * Applies a custom unary operator on a Permutation object.
	 *
	 * @param operator A unary Permutation operator
	 */
	public void apply(PermutationUnaryOperator operator) {
		operator.apply(permutation);
	}
	
	/**
	 * Applies a custom unary operator on a Permutation object.
	 *
	 * @param operator A unary Permutation operator
	 */
	public void apply(PermutationFullUnaryOperator operator) {
		operator.apply(permutation, this);
	}
	
	/**
	 * Applies a custom binary operator on a pair of Permutation objects.
	 * The raw int array belonging to this is passed as the first array to
	 * operator.apply() and the raw int array belonging to other is passed
	 * as the second.
	 *
	 * @param operator A binary Permutation operator
	 * @param other The other Permutation
	 */
	public void apply(PermutationBinaryOperator operator, Permutation other) {
		operator.apply(permutation, other.permutation);
	}
	
	/**
	 * Applies a custom binary operator on a pair of Permutation objects.
	 * The raw int array belonging to this is passed as the first array to
	 * operator.apply() and the raw int array belonging to other is passed
	 * as the second, and this and other are passed as p1 and p2, respectively.
	 *
	 * @param operator A binary Permutation operator
	 * @param other The other Permutation
	 */
	public void apply(PermutationFullBinaryOperator operator, Permutation other) {
		operator.apply(permutation, other.permutation, this, other);
	}
	
	/**
	 * Creates an identical copy of this object.
	 * @return an identical copy of this object
	 */
	@Override
	public Permutation copy() {
		return new Permutation(this);
	}
	
	
	/**
	* Generates a unique integer representing the permutation.  Maps the permutations of the integers, 0..(N-1), to 
	* the integers, 0..(N!-1), using a mixed radix representation.  This method is only supported for permutations
	* of length 12 or less.  Runtime of this method is O(N^2).
	* @return a mixed radix representation of the permutation
	* @throws UnsupportedOperationException when permutation length is greater than 12.
	*/
	public int toInteger() {
		int N = permutation.length;
		if (N > 12) throw new UnsupportedOperationException("Unsupported for permutations of length greater than 12.");
		int[] index = new int[N];
		for (int i = 0; i < N; i++) index[i] = i;
		int result = 0;
		int multiplier = 1;
		int factor = N;
		for (int i = 0; i < N-1; i++) {
			result += multiplier * index[permutation[i]];
			for (int j = permutation[i]; j < N; j++) {
				index[j]--;
			}
			multiplier *= factor;
			factor--;
		}
		return result;
	}
	
	/**
	* <p>Generates a unique integer representing the permutation.  Maps the permutations of the integers, 0..(N-1), to 
	* the integers, 0..(N!-1), using a mixed radix representation.</p>  
	* <p>Even with the use of BigInteger objects, the runtime of this method is O(N^2).  Specifically,
	* it performs O(N^2) operations on primitives.  And the sequence of operations on BigIntegers costs
	* no more than the cost to compute N! using BigInteger objects, whose runtime bounded by that of the last
	* multiplication of N * (N-1)!  The number (N-1)! consists of O(log((N-1)!)) = O(N log N) digits.  Java's
	* BigInteger.multiply currently implements the Toom–Cook algorithm, which has a runtime for M-digit numbers
	* of O(M^1.465).  Thus, the cost of all of the BigInteger operations is O(N^1.465 log(N)^1.465).  Therefore,
	* the runtime is dominated by the cost of the primitive operations: O(N^2).</p>
	*
	* @return a mixed radix representation of the permutation
	*/
	public BigInteger toBigInteger() {
		int N = permutation.length;
		if (N <= 12) return BigInteger.valueOf(toInteger());
		int[] index = new int[N];
		for (int i = 0; i < N; i++) index[i] = i;
		BigInteger result = BigInteger.ZERO;
		BigInteger multiplier = BigInteger.ONE;
		int factor = N;
		for (int i = 0; i < N-1; i++) {
			result = result.add(multiplier.multiply(BigInteger.valueOf(index[permutation[i]])));
			for (int j = permutation[i]; j < N; j++) {
				index[j]--;
			}
			multiplier = multiplier.multiply(BigInteger.valueOf(factor));
			factor--;
		}
		return result;
	}
	 
	/**
	 * Computes the inverse of the permutation.
	 *
	 * @return The inverse of the permutation, such that for all i, if
	 * pi(i) = j, then inv(j) = i
	 */
	public int[] getInverse() {
		int[] inverse = new int[permutation.length];
		for (int i = 0; i < permutation.length; i++) {
			inverse[permutation[i]] = i;
		}
		return inverse;
	}  
	
	/**
	 * Computes a Permutation that is the inverse of this Permutation.
	 * Specifically, this.get(i) == j iff inverse.get(j) == i.
	 *
	 * @return The inverse of the permutation, such that for all i,
	 * this.get(i) == j iff inverse.get(j) == i. 
	 */
	public Permutation getInversePermutation() {
		return new Permutation(getInverse(), false);
	}
	
	/**
	 * Inverts the Permutation, such that if p1 is the Permutation immediately
	 * prior to the call to invert, and if p2 is the Permutation immediately after
	 * the call to invert, then p1.get(i) == j iff p2.get(j) == i, for all i, j.
	 */
	public void invert() {
		int[] inverse = getInverse();
		System.arraycopy(inverse, 0, permutation, 0, inverse.length);
	}
	
	/**
	 * Randomly shuffles the permutation. Uses
	 * {@link ThreadLocalRandom} as 
	 * the source of efficient random number generation.
	 */
	public void scramble() {
		scramble(ThreadLocalRandom.current());
	}
	
	/**
	 * Randomly shuffles the permutation.
	 * @param r a source of randomness.
	 */
	public void scramble(RandomGenerator r) {
		if (permutation.length > 0) {
			// Since we're scrambling entire permutation, just generate a new
			// permutation of integers in [0, n).
			// Avoid swapping using trick described in Knuth, Vol 2, page 145,
			// last complete paragraph.
			permutation[0] = 0;
			for (int i = 1; i < permutation.length; i++) {
				int j = RandomIndexer.nextInt(i+1, r);
				if (j == i) {
					permutation[i] = i;
				} else {
					permutation[i] = permutation[j];
					permutation[j] = i;
				}			
			}
		}
	}
	
	/**
	 * Randomly shuffles the permutation. Uses
	 * {@link ThreadLocalRandom} as 
	 * the source of efficient random number generation.
	 * 
	 * @param guaranteeDifferent if true and if permutation length is at least 2, then method
	 * guarantees that the result is a different permutation than it was originally.
	 */
	public void scramble(boolean guaranteeDifferent) {
		scramble(ThreadLocalRandom.current(), guaranteeDifferent);
	}
	
	/**
	 * Randomly shuffles the permutation. 
	 * 
	 * @param r a source of randomness.
	 * @param guaranteeDifferent if true and if permutation length is at least 2, then method
	 * guarantees that the result is a different permutation than it was originally.
	 */
	public void scramble(RandomGenerator r, boolean guaranteeDifferent) {
		if (guaranteeDifferent) {
			boolean changed = false;
			for (int i = permutation.length - 1; i > 1; i--) {
				int j = RandomIndexer.nextInt(i+1, r);
				if (i != j) {
					swap(i,j);
					changed = true;
				}
			}
			if (permutation.length > 1 && (!changed || r.nextBoolean())) {
				swap(0,1);
			}
		} else {
			scramble(r);
		}
	}
	
	/**
	 * Randomly shuffles a segment. Uses
	 * {@link ThreadLocalRandom} as 
	 * the source of efficient random number generation.
	 * @param i endpoint of the segment
	 * (precondition: 0 &le; i &lt; length())
	 * @param j endpoint of the segment
	 * (precondition: 0 &le; j &lt; length())
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length()
	 */
	public void scramble(int i, int j) {
		scramble(i, j, ThreadLocalRandom.current());
	}
		
	/**
	 * Randomly shuffles a segment.
	 * @param i endpoint of the segment
	 * (precondition: 0 &le; i &lt; length())
	 * @param j endpoint of the segment
	 * (precondition: 0 &le; j &lt; length())
	 * @param r source of randomness
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length()
	 */
	public void scramble(int i, int j, RandomGenerator r) {
		if (i==j) { return; }
		if (i > j) {
			int temp = i;
			i = j;
			j = temp;
		}
		boolean changed = false;
		for (int k = j; k > i + 1; k--) {
			int l = i + RandomIndexer.nextInt(k-i+1, r);
			if (l != k) {
				swap(l,k);
				changed = true;
			}
		}
		if (!changed || r.nextBoolean()) {
			swap(i,i+1);
		}
	}
	
	/**
	 * Randomly shuffles a non-contiguous set of permutation elements. As long as there
	 * are at least 2 different indexes passed to this method, it is guaranteed to 
	 * change the Permutation.
	 * @param indexes An array of indexes into the permutation. This method assumes
	 * that the indexes are valid indexes into the permutation.  That is, it assumes
	 * that 0 &le; indexes[i] &lt; this.length().
	 * @param r source of randomness
	 * @throws ArrayIndexOutOfBoundsException if any of the indexes[i] are negative or
	 * greater than or equal to this.length().
	 */
	public void scramble(int[] indexes, RandomGenerator r) {
		if (indexes.length > 1) {
			boolean changed = false;
			for (int j = indexes.length-1; j > 1; j--) {
				int i = RandomIndexer.nextInt(j+1, r);
				if (i != j) {
					swap(indexes[i],indexes[j]);
					changed = true;
				}
			}
			if (!changed || r.nextBoolean()) {
				swap(indexes[0],indexes[1]);
			}
		}
	}
	
	/**
	 * Randomly shuffles a non-contiguous set of permutation elements. As long as there
	 * are at least 2 different indexes passed to this method, it is guaranteed to 
	 * change the Permutation.
	 * @param indexes An array of indexes into the permutation. This method assumes
	 * that the indexes are valid indexes into the permutation.  That is, it assumes
	 * that 0 &le; indexes[i] &lt; this.length().
	 * @throws ArrayIndexOutOfBoundsException if any of the indexes[i] are negative or
	 * greater than or equal to this.length().
	 */
	public void scramble(int[] indexes) {
		scramble(indexes, ThreadLocalRandom.current());
	}
	
	/**
	 * Retrieves the i'th integer of the permutation.
	 * @param i the index of the integer to retrieve.
	 * (precondition: 0 &le; i &lt; length())
	 * @return the integer in the i'th position.
	 * @throws ArrayIndexOutOfBoundsException if i is negative, 
	 * or if i is greater than or equal to length()
	 */
	public int get(int i) {
		return permutation[i];
	}
	
	/**
	 * Retrieves a range of elements from the permutation.
	 * @param i The starting index.
	 * @param j The ending index (inclusive).
	 * @return An array containing the permutation elements from positions i through j, inclusive.
	 * @throws IllegalArgumentException if j &lt; i
	 * @throws IndexOutOfBoundsException if i is negative, or j &ge; Permutation.length()
	 */
	public int[] get(int i, int j) {
		if (j < i) throw new IllegalArgumentException("j must not be less than i");
		int[] array = new int[j-i+1];
		System.arraycopy(permutation, i, array, 0, array.length);
		return array;
	}
	
	/**
	 * Retrieves a range of elements from the permutation.
	 * @param i The starting index.
	 * @param j The ending index (inclusive).
	 * @param array An array to hold the result.  If the array is null or if its length
	 * is not equal to the number of retrieved elements, then a new array is constructed.
	 * @return The array containing the permutation elements from positions i through j, inclusive.
	 * @throws IllegalArgumentException if j &lt; i
	 * @throws IndexOutOfBoundsException if i is negative, or j &ge; Permutation.length()
	 */
	public int[] get(int i, int j, int[] array) {
		if (j < i) throw new IllegalArgumentException("j must not be less than i");
		int n = j-i+1;
		if (array == null || array.length != n) {
			array = new int[n];
		}
		System.arraycopy(permutation, i, array, 0, array.length);
		return array;
	}
	
	
	/**
	 * Generates an array of int values from the interval [0, n) in the same order
	 * that they occur in this Permutation.  The array that is returned is independent of
	 * the object state (i.e., changes to its contents will not affect the Permutation).
	 *
	 * @return an int array containing the Permutation elements in the same order that they appear in
	 * the Permutation.
	 *
	 */
	public int[] toArray() {
		return permutation.clone();
	}
	
	/**
	 * <p>Generates an array of int values from the interval [0, n) in the same order
	 * that they occur in this Permutation.  The array that is returned is independent of
	 * the object state (i.e., changes to its contents will not affect the Permutation).</p>
	 * 
	 * @param array An array to hold the result.  If array is null or if array.length is
	 * not equal to the length of the permutation, then this method will construct a new array
	 * for the result.
	 * 
	 * @return an int array containing the Permutation elements in the same order that they appear in
	 * the Permutation.
	 *
	 */
	public int[] toArray(int[] array) {
		if (array == null || array.length != permutation.length) {
			return permutation.clone();
		} else {
			System.arraycopy(permutation, 0, array, 0, array.length);
			return array;
		}
	}
	
	/**
	 * Retrieves the length of the permutation.
	 * @return length of the permutation
	 */
	public int length() {
		return permutation.length;   
	}
	
	/**
	 * Swaps 2 integers in the permutation.
	 * @param i position of first to swap
	 * (precondition: 0 &le; i &lt; length() &and; i != j)
	 * @param j the position of the second to swap
	 * (precondition: 0 &le; j &lt; length() &and; i != j)
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length()
	 */
	public void swap(int i, int j) {
		int temp = permutation[i];
		permutation[i] = permutation[j];
		permutation[j] = temp;
	}
	
	/**
	 * Creates a permutation cycle from a sequence of permutation
	 * indexes. Let p1 be the permutation before the call to cycle,
	 * and let p2 be the permutation after the call to cycle.  For i
	 * from 1 to indexes.length - 1, 
	 * p2.get(indexes[i-1])==p1.get(indexes[i]); 
	 * and p2.get(indexes[indexes.length - 1])==p1.get(indexes[0]).
	 * Note that passing an array containing two indexes to this method
	 * is equivalent to a {@link #swap}, and passing fewer than 2 indexes
	 * does nothing.
	 * @param indexes an array of indexes into the permutation.
	 * @throws ArrayIndexOutOfBoundsException if there exists any indexes[i]
	 * &ge; this.length() or indexes[i] &lt; 0.
	 */
	public void cycle(int[] indexes) {
		if (indexes.length > 1) {
			int temp = permutation[indexes[0]];
			for (int i = 1; i < indexes.length; i++) {
				permutation[indexes[i-1]] = permutation[indexes[i]];
			}
			permutation[indexes[indexes.length-1]] = temp;
		}
	}
	
	/**
	 * Swaps 2 non-overlapping blocks, where a block is a subsequence.
	 * @param a Starting index of first block.
	 * @param b Ending index, inclusive, of first block.
	 * @param i Starting index of second block.
	 * @param j Ending index, inclusive, of second block.
	 * @throws IllegalArgumentException if the following constraint is violated:
	 * 0 &le; a &le; b &lt; i &le; j &lt; length().
	 */
	public void swapBlocks(int a, int b, int i, int j) {
		if (a < 0 || b < a || i <= b || j < i || j >= permutation.length) {
			throw new IllegalArgumentException("Illegal block definition.");
		} else if (a==b && i==j) {
			// blocks are singletons
			swap(a, i);
		} else if (b+1==i) {
			// blocks are adjacent
			removeAndInsert(i, j-i+1, a);
		} else {
			int[] temp = new int[j-a+1];
			int k = j-i+1;
			System.arraycopy(permutation, i, temp, 0, k);
			int m = i-b-1;
			System.arraycopy(permutation, b+1, temp, k, m);
			System.arraycopy(permutation, a, temp, k+m, b-a+1);
			System.arraycopy(temp, 0, permutation, a, temp.length);
		}
	}
	
	/**
	 * Reverses the order of the elements in the permutation.
	 */
	public void reverse() {
		for (int i = 0, j = permutation.length-1; i < j; i++, j--) {
			swap(i, j);
		}
	}
	
	/**
	 * Reverses the order of the elements of a subrange of  the permutation.
	 * @param i position of first index
	 * (precondition: 0 &le; i &lt; length() &and; i != j)
	 * @param j the position of the second index
	 * (precondition: 0 &le; j &lt; length() &and; i != j)
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length()
	 */
	public void reverse(int i, int j) {
		if (i > j) {
			for ( ; i > j; i--, j++) {
				swap(i, j);
			}
		} else {
			for ( ; i < j; i++, j--) {
				swap(i, j);
			}
		}
	}
	
	/**
	 * Removes integer from one position and then inserts it into a
	 * a new position shifting the rest of the permutation as necessary.
	 * @param i position of integer to remove and insert
	 * (precondition: 0 &le; i &lt; length())
	 * @param j the position of the insertion point
	 * (precondition: 0 &le; j &lt; length())
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length()
	 */
	public void removeAndInsert(int i, int j) {
		if (i < j) {
			int n = permutation[i];
			System.arraycopy(permutation, i+1, permutation, i, j-i);
			permutation[j] = n;
		} else if (i > j) {
			int n = permutation[i];
			System.arraycopy(permutation, j, permutation, j+1, i-j);
			permutation[j] = n;
		}
	}
	
	/**
	* Circular rotation of permutation (to the left).
	* @param numPositions Number of positions to rotate.
	*/
	public void rotate(int numPositions) {
		if (numPositions >= permutation.length || numPositions < 0) numPositions = Math.floorMod(numPositions, permutation.length);
		if (numPositions==0) return;
		int[] temp = new int[numPositions];
		System.arraycopy(permutation, 0, temp, 0, numPositions);
		System.arraycopy(permutation, numPositions, permutation, 0, permutation.length-numPositions);
		System.arraycopy(temp, 0, permutation, permutation.length-numPositions, numPositions);
	}
	
	/**
	 * Removes a sub-array of integers from one position and then inserts it into a
	 * a new position shifting the rest of the permutation as necessary.
	 * @param i position of first integer in sub-array to remove and insert
	 * (precondition: 0 &le; i &lt; length())
	 * @param size the length of the sub-array
	 * (precondition: size + i &lt; length() and size + j - 1 &lt; length())
	 * @param j the position of the insertion point
	 * (precondition: 0 &le; j &lt; length())
	 * @throws ArrayIndexOutOfBoundsException if either i or j are negative, 
	 * or if either i or j are greater than or equal to length().
	 */
	public void removeAndInsert(int i, int size, int j) {
		if ((size == 0) || (i == j)) {
			return;
		} else if (size == 1) {
			removeAndInsert(i, j);
		} else if (i > j) {
			int[] temp = new int[i-j];
			System.arraycopy(permutation, j, temp, 0, i-j);
			System.arraycopy(permutation, i, permutation, j, size);
			System.arraycopy(temp, 0, permutation, j+size, i-j);
		} else { // Condition is implied by above: if (i < j)
			int[] temp = new int[size];
			System.arraycopy(permutation, i, temp, 0, size);
			System.arraycopy(permutation, i+size, permutation, i, j-i);
			System.arraycopy(temp, 0, permutation, j, size);
		}
	}
	
	/**
	 * Changes the state of this permutation to be identical to the elements of an array.
	 *
	 * @param p An array of integers. Each of the integers in the interval [0, p.length) 
	 * must occur exactly one time each.
	 * @throws IllegalArgumentException if p either contains duplicates, 
	 * or contains any negative elements, 
	 * or contains any elements equal or greater than p.length.
	 */
	public void set(int[] p) {
		if (p.length != permutation.length) {
			throw new IllegalArgumentException("Length of array must be same as that of permutation.");
		}
		boolean[] inP = new boolean[p.length];
		for (int e : p) {
			if (e < 0 || e >= p.length) throw new IllegalArgumentException("Elements of p must be in interval [0, p.length)");
			if (inP[e]) throw new IllegalArgumentException("Duplicate elements of p are not allowed.");
			inP[e] = true;
		}
		System.arraycopy(p, 0, permutation, 0, p.length);
	}
	
	/**
	 * Returns an Iterator over all Permutations the 
	 * length of this Permutation.  Iteration begins at this Permutation.
	 * This Iterator does not iterate over the integers within the Permutation.
	 * If you do need to iterate over all permutations 
	 * of a given length, then this method is much more efficient than
	 * using the {@link #Permutation(int,int)} constructor 
	 * repeatedly incrementing the value passed for the second parameter.
	 * 
	 * @return an Iterator
	 */
	@Override
	public Iterator<Permutation> iterator() {
		return new PermutationIterator(this);
	}
	
	/**
	* Creates a String representing the permutation.
	* @return a space separated sequence of the permutation's elements
	*/
	@Override
	public String toString() {
		String permS = "";
		if (permutation.length > 0) {
			permS += permutation[0];
			for (int i = 1; i < permutation.length; i++) {
				permS += " " + permutation[i];
			}
		}
		return permS;
	}
	
	/**
	* Equality test: Two permutations are equal if they are of
	* the same length and contain the same elements in the same order.
	* @param other the permutation to which to compare
	* @return true if this is equal to other, and false otherwise
	*/
	@Override
	public boolean equals(Object other) {
		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof Permutation)) return false;
		Permutation o = (Permutation)other;
		if (permutation.length != o.permutation.length) return false;
		for (int i = 0; i < permutation.length; i++) {
			if (permutation[i] != o.permutation[i]) return false;
		}
		return true;
	}
	
	/**
	* Uses Java's Arrays class's method for generating a hashCode from an array of ints.
	* @return a hashCode for the permutation
	*/
	@Override
	public int hashCode() {
		return Arrays.hashCode(permutation);
	}
}
