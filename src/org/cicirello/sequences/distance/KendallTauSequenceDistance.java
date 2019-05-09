/*
 * Copyright 2018-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences.distance;


import java.util.Arrays;
import java.util.HashMap;


/**
 * <p>Kendall Tau Sequence Distance is
 * the minimum number of adjacent swaps necessary to transform one sequence into the other.
 * It is an edit distance with adjacent swap as the edit operation.  It is applicable only
 * if both sequences are the same length and contain the same set of elements.</p>
 *
 * <p>As a distance metric, Kendall Tau Distance originated specifically to measure distance between 
 * permutations (i.e., sequence of unique elements).  But, the Kendall Tau Sequence Distance that
 * is implemented here is an
 * extension of Kendall Tau Distance to general sequences (i.e., strings that can contain duplicate 
 * elements).</p>
 *
 * <p>Consider this example.  Let s1 = "abcdaabb" and s2 = "dcbababa".  
 * The shortest sequence of adjacent swaps to edit s2 into s1 is the following sequence of 9 swaps:
 * "cdbababa", "cbdababa", "bcdababa",
 * "bcadbaba", "bacdbaba", "abcdbaba", "abcdabba", "abcdabab", "abcdaabb".</p>
 *
 * <p>In this Java class, we provide implementations of two algorithms.  Both algorithms are relevant
 * for computing the distance between arrays of primitive values as well as distance between String objects.
 * For computing the Kendall Tau
 * Sequence Distance of two arrays of any primitive type (e.g., arrays of ints, longs, shorts, bytes, chars,
 * floats, doubles, or booleans), as well as for computing the distance between two String objects, the runtime
 * of both algorithms is O(n lg n), where n is the length of the array or String.</p>
 *
 * <p>If you are computing the distance between two arrays of Objects, the two algorithms have the
 * following restrictions.  The default algorithm requires the objects to be of a class that 
 * overrides the hashCode and equals methods of the {@link java.lang.Object} class.  The alternate
 * algorithm requires Objects to be of a class that implements the {@link java.lang.Comparable} 
 * interface, and overrides the equals method of the {@link java.lang.Object} class.  The runtime
 * for computing distance between arrays of objects via the default algorithm is O(h(m) n + n lg n),
 * where n is the array length, m is the size of the objects in the array, and h(m) is the
 * runtime to compute a hash of an object of size m.  The runtime for the alternate algorithm for
 * arrays of objects is O(c(m) n lg n), where n and m are as before, and c(m) is the runtime of
 * the compareTo method for objects of size m.  The default algorithm is the preferred algorithm
 * in most cases.  The alternate algorithm may run faster if the cost to compare objects, c(m),
 * is significantly less than the cost to hash objects, h(m).</p>
 *
 * <p>Runtime: O(n lg n) for String objects and sequences of primitives, 
 * where n is the length of the sequence.</p>
 *
 * <p>If your sequences are guaranteed not to have duplicates, 
 * and to contain the same set of elements, then consider instead using the
 * {@link org.cicirello.permutations.distance.KendallTauDistance} class, which 
 * assumes permutations of the integers from 0 to N-1.</p>
 *
 * <p>This distance metric, and both algorithms, is first described in the paper:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2019arXiv.html" target=_top>"Kendall Tau
 * Sequence Distance: Extending Kendall Tau from Ranks to Sequences,"</a> 
 * arXiv preprint arXiv:1905.02752 [cs.DM], May 2019.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.31
 * @since 1.1
 */
public final class KendallTauSequenceDistance extends AbstractSequenceDistanceMeasurer {
	
	private final boolean USE_HASHMAP;
	
	/**
	 * The KendallTauDistance class provides two algorithms.  
	 * The default algorithm requires sequence elements to either be primitives (e.g.,
	 * byte, short, int, long, char, float, double, boolean) or to be of a class that overrides
	 * the hashCode and equals methods of the {@link java.lang.Object} class.
	 */
	public KendallTauSequenceDistance() {
		USE_HASHMAP = true;
	}
	
	/**
	 * <p>The KendallTauDistance class provides two algorithms.
	 * This constructor enables you to select which algorithm to use.</p>
	 * 
	 * <p>The default algorithm requires sequence elements to either be primitives (e.g.,
	 * byte, short, int, long, char, float, double, boolean) or to be of a class that overrides
	 * the hashCode and equals methods of the {@link java.lang.Object} class.</p>
	 *
	 * <p>The alternate algorithm requires objects to be of a class that implements 
	 * the {@link java.lang.Comparable} interface, and overrides the equals method 
	 * of the {@link java.lang.Object} class.</p>
	 *
	 * <p>Under most conditions, the preferred algorithm is the default.  The alternate
	 * algorithm may be desirable if the cost to compare objects is significantly less than the
	 * cost to hash objects, or if the objects are of a class that implements Comparable but
	 * which does not provide an implementation of hashCode.</p>
	 *
	 * @since 1.2.3
	 *
	 * @param useAlternateAlg To use the alternate algorithm pass true. To use the default algorithm pass false.
	 */
	public KendallTauSequenceDistance(boolean useAlternateAlg) {
		USE_HASHMAP = !useAlternateAlg;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	<T> int distance(Sequence<T> s1, Sequence<T> s2) {
		if (s1.length() != s2.length()) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length() == 0) return 0;
		
		int[][] relabeling = new int[s1.length()][2];
		int numLabels = USE_HASHMAP || s1 instanceof NonComparableObjectSequence ? relabelElementsToIntsWithHash(s1,s2,relabeling) : relabelElementsToInts(s1,s2,relabeling);
		
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		
		int[] mapping = mapElements(buckets, relabeling.length);
		
		return countInversions(mapping);
	}
	
	private int[] mapElements(Bucket[][] buckets, int seqLength) {
		int[] mapping = new int[seqLength];		
		for (int k = 0; k < buckets.length; k++) {
			while (buckets[k][0].head != null) {
				int i = buckets[k][0].remove();
				if (buckets[k][1].head == null) {
					throw new IllegalArgumentException("Sequences must contain same elements.");
				}
				int j = buckets[k][1].remove();
				// record mapping here
				mapping[i] = j; 
			}
			if (buckets[k][1].head != null) {
				throw new IllegalArgumentException("Sequences must contain same elements.");
			}
		}
		return mapping;
	}
	
	private Bucket[][] bucketSortElements(int[][] relabeling, int numLabels) {
		Bucket[][] buckets = new Bucket[numLabels][2];
		for (int i = 0; i < numLabels; i++) {
			buckets[i][0] = new Bucket();
			buckets[i][1] = new Bucket();
		}
		for (int i = 0; i < relabeling.length; i++) {
			buckets[relabeling[i][0]][0].add(i);
			buckets[relabeling[i][1]][1].add(i);
		}
		return buckets;
	}
	
	private <T> int relabelElementsToInts(Sequence<T> s1, Sequence<T> s2, int[][] relabeling) {
		Sequence<T> c1 = s1.copy();
		c1.sort();
		int[] labels = new int[c1.length()];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (!c1.equal(i, i-1)) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = c1.search(s1.get(i));
			relabeling[i][0] = labels[j];
			j = c1.search(s2.get(i));
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private <T> int relabelElementsToIntsWithHash(Sequence<T> s1, Sequence<T> s2, int[][] relabeling) {
		HashMap<T,Integer> labelMap = new HashMap<T,Integer>((int)(relabeling.length / 0.75)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1.get(i))) {
				current++;
				labelMap.put(s1.get(i),current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1.get(i)); 
			Integer j = labelMap.get(s2.get(i));
			if (j == null) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	// assumes all unique elements
	private int countInversions(int[] array) {
		if (array.length <= 1) return 0;
		int m = array.length / 2;
		int[] left = Arrays.copyOfRange(array, 0, m);
		int[] right = Arrays.copyOfRange(array, m, array.length);
		int count = countInversions(left) + countInversions(right);
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				array[k] = left[i];
				i++;
				k++;
			} else {
				// inversions
				count += (left.length - i);
				array[k] = right[j];
				j++;
				k++;
			}
		}
		while (i < left.length) {
			array[k] = left[i];
			i++;
			k++;
		}
		while (j < right.length) {
			array[k] = right[j];
			j++;
			k++;
		}
		return count;
	}
	
	private static final class Bucket {
		Node head;
		Node tail;
		
		void add(int value) {
			if (head == null) head = tail = new Node(value);
			else tail = tail.next = new Node(value);
		}
		
		// warning: assumes not empty
		int remove() {
			int v = head.value;
			head = head.next;
			if (head == null) tail = null;
			return v;
		}
		
		static final class Node {
			int value;
			Node next;
			Node(int value) { this.value = value; }
		}
	}
	
}