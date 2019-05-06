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
 * <p>Kendall Tau distance is
 * the minimum number of adjacent swaps necessary to transform one sequence into the other.
 * Thus, for this reason, it is sometimes also known as bubble sort distance since bubble sort uses adjacent swaps to sort.</p>
 *
 * <p>As a distance metric, it originated specifically to measure distance between permutations (i.e., sequence of unique elements).
 * In the case of permutations, it is very closely related to Kendall tau rank correlation.  Kendall tau distance (for permutations) is essentially
 * the count of the number of permutation inversions (or discordant pairs), 
 * providing a minimum distance of 0 for identical permutations and a maximum distance of n(n-1)/2,
 * where n is the length.  The max case is distance between a permutation and its reverse.</p>
 *
 * <p>Adapting Kendall tau distance from permutations to general sequences (i.e., strings that can contain duplicate elements) is usually,
 * and (in our opinion) incorrectly done using the equivalent adaptation of Kendall tau rank correlation to partial rankings (i.e., rankings 
 * involving ties).  That extension of Kendall tau distance to strings uses the same sort of count of discordant pairs as when computing
 * Kendall tau rank correlation with ties.  In the case of strings, this is wrong.  The string elements are not ranks, and the result does not
 * give you minimum adjacent swaps to edit one string to the other.</p>
 *
 * <p>Consider this example.  Let s1 = "abcdaabb" and s2 = "dcbababa".  Computing number of discordant pairs (in the sense of Kendall
 * tau rank correlation) in this case is 11.  I'll leave it as an exercise to the reader of this documentation to confirm.  However,
 * it is possible to edit s2 into s1 with fewer than 11 adjacent swaps--namely with 9 swaps as follows: "cdbababa", "cbdababa", "bcdababa",
 * "bcadbaba", "bacdbaba", "abcdbaba", "abcdabba", "abcdabab", "abcdaabb".</p>
 *
 * <p>Our implementation, instead, actually computed the minimum number of adjacent swaps to transform s1 into s2, and works
 * as follows.  First, the alphabet of s1 is mapped to the integers from 0 to L-1 (where L is the number of unique characters).
 * This step is done in O(n lg n) time by sorting a copy of s1 and comparing adjacent elements for equality.  Second, s1 and s2 are 
 * both relabeled from the original alphabet to this new integer based alphabet.  This relabeling step is also O(n lg n), involving a binary search
 * of the sorted copy of s1 for each of the n elements of s1 and each of the n elements of s2.</p>
 *
 * <p>The third step involves performing a bucket sort of the relabeled s1.  Specifically, L empty buckets are initialized, where buckets are
 * implemented as simple singly linked lists.  For each element of the relabeled s1, its index is added to the bucket corresponding to
 * its integer label, maintaining the order the indices were added.  Each bucket contains the indices of all copies of the corresponding
 * element.  For example, if s1 = "abaabc", and if the relabeled version is "010012", then bucket 0 contains (0, 2, 3), bucket 1 contains (1, 4),
 * bucket 2 contains (5).  Next, another bucket sort of the relabeled s2 is performed in the same way, with an additional set of L
 * buckets.  There will be at most 2n buckets (if all elements are unique), so the time to initialize the empty buckets is O(n) in worst case.
 * The time to populate the buckets with the indices of the relabeled elements of s1 and s2 is O(n), involving simple linear iteration.
 * Therefore, this bucket sort step is likewise O(n) time.</p>
 *
 * <p>This fourth step involves generating a permutation that maps the elements of s1 to the corresponding elements of s2.  Consider as an example
 * the following partially defined string s1 = "**A**A**A**".  Each of the * are characters other than "A".  Now consider an s2 = "AA********A".
 * Both contain 3 copies of "A".  In s1 they are at positions {2, 5, 8}, and in s2 positions {0, 1, 10}.  In the shortest sequence of adjacent swaps that
 * transforms s1 to s2, the "A" in position 2 of s1 must correspond to the "A" in position 0 of s2.  Likewise,
 * the "A" in position 5 of s1 must correspond to the "A" in position 1 of s2, and  
 * the "A" in position 8 of s1 must correspond to the "A" in position 10 of s2.  Why?  Well, if they were mapped any other way, then at some point
 * in the sequence of adjacent swaps, there'd be a swap of a pair of adjacent "A"s.  Such a swap accomplishes nothing other than increasing the edit 
 * sequence length.  So the minimum number of adjacent swaps must not have any like this.</p>
 *
 * <p>Therefore, the fourth step involves iterating over the buckets from step 3 to generate a mapping between elements of s1 and s2.
 * If bucket b1(i,j) is the index of the jth copy of element i in s1, and similarly for b2(i,j), then this mapping M will be such that
 * M[b1(i,j)] = b2(i,j).  Generating this mapping requires O(n) time.</p>
 *
 * <p>The previous step provides a permutation of the integers from 0 to n-1 that maps the elements between the two strings (sequences).
 * The final step is to count the inversions in that permutation which is done in O(n lg n) time with a modified mergesort.</p>
 *
 * <p>Runtime: O(n lg n), where n is the length of the shorter of the two sequences.</p>
 *
 * <p>If your sequences are guaranteed not to have duplicates, and to contain the same set of elements, then consider instead using the
 * {@link org.cicirello.permutations.distance.KendallTauDistance} class, which assumes permutations of the integers from 0 to N-1.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.31
 * @since 1.1
 */
public final class KendallTauSequenceDistance extends AbstractSequenceDistanceMeasurer {
	
	private final boolean USE_HASHMAP;
	
	/**
	 * The KendallTauDistance class provides two algorithms.  The runtime of both algorithms is O(n lg n)
	 * where n is the length of the sequence.  Both sequences must be the same length.
	 * The default algorithm requires sequence elements to be hashable, and supports sequences of any primitive type,
	 * or a sequence of an object type of a class that has overridden the hashCode 
	 * and equals methods of the Object class.
	 */
	public KendallTauSequenceDistance() {
		USE_HASHMAP = true;
	}
	
	/**
	 * The KendallTauDistance class provides two algorithms.  The runtime of both algorithms is O(n lg n)
	 * where n is the length of the sequence.  Both sequences must be the same length.
	 * The default algorithm requires sequence elements to be hashable, and supports sequences of any primitive type,
	 * or a sequence of an object type of a class that has overridden the hashCode 
	 * and equals methods of the Object class.
	 * The alternate algorithm requires sequence elements to be comparable (e.g., sequences of any primitive type,
	 * or a sequence of an object type that implements the Comparable interface).
	 * Our experiments indicate that the alternate algorithm tends to be slower (even though same asymptotic runtime).
	 * However, in cases where hash collisions are common, the alternate algorithm may run faster than the default,
	 * whose performance depends on the performance of a hash table.
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