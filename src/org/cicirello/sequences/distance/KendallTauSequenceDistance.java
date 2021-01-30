/*
 * Copyright 2018-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
import java.util.List;
import java.util.Iterator;

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
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/eai.13-7-2018.163925.pdf" target=_top>"Kendall Tau
 * Sequence Distance: Extending Kendall Tau from Ranks to Sequences,"</a> 
 * Industrial Networks and Intelligent Systems, 7(23), Article e1, April 2020.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.30.2021
 */
public final class KendallTauSequenceDistance extends AbstractSequenceDistanceMeasurer {
	
	private final boolean USE_HASHMAP;
	
	/**
	 * The KendallTauDistance class provides two algorithms.  
	 * The default algorithm requires sequence elements to either be primitives (e.g.,
	 * byte, short, int, long, char, float, double, boolean) or to be objects of a class that overrides
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
	 * byte, short, int, long, char, float, double, boolean) or to be objects of a class that overrides
	 * the hashCode and equals methods of the {@link java.lang.Object} class.</p>
	 *
	 * <p>The alternate algorithm requires sequence elements to either be primitives (e.g.,
	 * byte, short, int, long, char, float, double, boolean) or to be objects of a class that implements 
	 * the {@link java.lang.Comparable} interface, and overrides the equals method 
	 * of the {@link java.lang.Object} class.</p>
	 *
	 * <p>Under most conditions, the preferred algorithm is the default.  The alternate
	 * algorithm may be desirable if the cost to compare objects is significantly less than the
	 * cost to hash objects, or if the objects are of a class that implements Comparable but
	 * which does not provide an implementation of hashCode.</p>
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
	public int distance(int[] s1, int[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(long[] s1, long[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(short[] s1, short[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(byte[] s1, byte[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(char[] s1, char[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(String s1, String s2) {
		if (s1.length() != s2.length()) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length() == 0) return 0;
		
		int[][] relabeling = new int[s1.length()][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(float[] s1, float[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(double[] s1, double[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = USE_HASHMAP ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements(s1,s2,relabeling); 
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(boolean[] s1, boolean[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = relabelElements(s1,s2,relabeling);		
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 * <p>If the distance measurer object is configured, via the constructor, to use the
	 * alternate algorithm, but the arrays passed to this method do not implement
	 * the Comparable interface, then this method will disregard the choice of alternate
	 * algorithm and use the default algorithm instead.</p>
	 * @throws IllegalArgumentException if sequences are of different lengths, or contain different elements
	 */
	@Override
	public int distance(Object[] s1, Object[] s2) {
		if (s1.length != s2.length) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.length == 0) return 0;
		
		int[][] relabeling = new int[s1.length][2];
		int numLabels = (USE_HASHMAP || !(s1 instanceof Comparable[])) ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements((Comparable[])s1,(Comparable[])s2,relabeling);
		
		Bucket[][] buckets = bucketSortElements(relabeling, numLabels);
		int[] mapping = mapElements(buckets, relabeling.length);	
		return countInversions(mapping);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> int distance(List<T> s1, List<T> s2) {
		if (s1.size() != s2.size()) throw new IllegalArgumentException("Sequences must be same length for Kendall Tau distance.");
		if (s1.size() == 0) return 0;
		
		int[][] relabeling = new int[s1.size()][2];
		@SuppressWarnings("unchecked")
		int numLabels = (USE_HASHMAP || !(s1.get(0) instanceof Comparable)) ? relabelElementsWithHash(s1,s2,relabeling) : relabelElements((List<Comparable>)s1,(List<Comparable>)s2,relabeling);
		
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
	
	private int relabelElementsWithHash(Object[] s1, Object[] s2, int[][] relabeling) {
		HashMap<Object,Integer> labelMap = new HashMap<Object,Integer>((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			Integer j = labelMap.get(s2[i]);
			if (j == null) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private <T> int relabelElementsWithHash(List<T> s1, List<T> s2, int[][] relabeling) {
		HashMap<T,Integer> labelMap = new HashMap<T,Integer>((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (T e : s1) {
			if (!labelMap.containsKey(e)) {
				current++;
				labelMap.put(e,current);
			}
		}
		Iterator<T> iter1 = s1.iterator();
		Iterator<T> iter2 = s2.iterator();
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(iter1.next()); 
			Integer j = labelMap.get(iter2.next());
			if (j == null) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(int[] s1, int[] s2, int[][] relabeling) {
		IntHT labelMap = new IntHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(double[] s1, double[] s2, int[][] relabeling) {
		DoubleHT labelMap = new DoubleHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(float[] s1, float[] s2, int[][] relabeling) {
		FloatHT labelMap = new FloatHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(long[] s1, long[] s2, int[][] relabeling) {
		LongHT labelMap = new LongHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(short[] s1, short[] s2, int[][] relabeling) {
		ShortHT labelMap = new ShortHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(char[] s1, char[] s2, int[][] relabeling) {
		CharHT labelMap = new CharHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1[i])) {
				current++;
				labelMap.put(s1[i],current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1[i]); 
			int j = labelMap.get(s2[i]);
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(String s1, String s2, int[][] relabeling) {
		CharHT labelMap = new CharHT((int)(1.334 * relabeling.length)+2);
		int current = -1;
		for (int i = 0; i < relabeling.length; i++) {
			if (!labelMap.containsKey(s1.charAt(i))) {
				current++;
				labelMap.put(s1.charAt(i),current);
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			relabeling[i][0] = labelMap.get(s1.charAt(i)); 
			int j = labelMap.get(s2.charAt(i));
			if (j == -1) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j;
		}
		return current+1;
	}
	
	private int relabelElementsWithHash(byte[] s1, byte[] s2, int[][] relabeling) {
		// Since there are only 256 possible byte values, use a simple array of length 256 for the hash table.
		// Always perfect hashing with no collisions in this special case.
		int[] labelMap = new int[256];
		int current = 0;
		for (int i = 0; i < relabeling.length; i++) {
			int key = 255 & (int)s1[i];
			if (labelMap[key]==0) {
				current++;
				labelMap[key] = current;
			}
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int key1 = 255 & (int)s1[i];
			relabeling[i][0] = labelMap[key1] - 1; 
			int key2 = 255 & (int)s2[i];
			int j = labelMap[key2];
			if (j == 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = j - 1;
		}
		return current;
	}
	
	private int relabelElements(int[] s1, int[] s2, int[][] relabeling) {
		int[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(long[] s1, long[] s2, int[][] relabeling) {
		long[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(short[] s1, short[] s2, int[][] relabeling) {
		short[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(byte[] s1, byte[] s2, int[][] relabeling) {
		byte[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(char[] s1, char[] s2, int[][] relabeling) {
		char[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(String s1, String s2, int[][] relabeling) {
		char[] c1 = s1.toCharArray();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1.charAt(i));
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2.charAt(i));
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(float[] s1, float[] s2, int[][] relabeling) {
		float[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(double[] s1, double[] s2, int[][] relabeling) {
		double[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(boolean[] s1, boolean[] s2, int[][] relabeling) {
		int trueCount1 = 0;
		int trueCount2 = 0;
		for (int i = 0; i < s1.length; i++) {
			if (s1[i]) trueCount1++;
			if (s2[i]) trueCount2++;
		}
		if (trueCount1 != trueCount2) {
			throw new IllegalArgumentException("Sequences must contain same elements.");
		}
		if (trueCount1 < s1.length) {
			for (int i = 0; i < relabeling.length; i++) {
				relabeling[i][0] = s1[i] ? 1 : 0; 
				relabeling[i][1] = s2[i] ? 1 : 0;
			}
		} else {
			for (int i = 0; i < relabeling.length; i++) {
				relabeling[i][0] = relabeling[i][1] = 0; 
			}
		}
		return trueCount1 > 0 && s1.length > trueCount1 ? 2 : 1;
	}
	
	private int relabelElements(Comparable[] s1, Comparable[] s2, int[][] relabeling) {
		Comparable[] c1 = s1.clone();
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, s1[i]);
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, s2[i]);
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
		}
		return current+1;
	}
	
	private int relabelElements(List<Comparable> s1, List<Comparable> s2, int[][] relabeling) {
		Comparable[] c1 = s1.toArray(new Comparable[s1.size()]);
		Arrays.sort(c1);
		int[] labels = new int[c1.length];
		int current = labels[0] = 0;
		for (int i = 1; i < labels.length; i++) {
			if (c1[i] != c1[i-1]) current++;
			labels[i] = current;
		}
		Iterator<Comparable> iter1 = s1.iterator();
		Iterator<Comparable> iter2 = s2.iterator();
		for (int i = 0; i < relabeling.length; i++) {
			int j = Arrays.binarySearch(c1, iter1.next());
			relabeling[i][0] = labels[j];
			j = Arrays.binarySearch(c1, iter2.next());
			if (j < 0) throw new IllegalArgumentException("Sequences must contain same elements: s2 contains at least one element not in s1.");
			relabeling[i][1] = labels[j];
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
	
	// internal data structures below
	
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
	
	private static final class IntHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x40000000;
		private int mask;
		
		IntHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(int key) {
			return (key ^ (key >>> 16)) & mask;
		}
		
		boolean containsKey(int key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(int key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(int key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			int key;
			int value;
			Node next;
			Node(int key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
	private static final class LongHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x40000000;
		private int mask;
		
		LongHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(long key) {
			int x = (int)(key ^ (key >>> 32));
			return (x ^ (x >>> 16)) & mask;
		}
		
		boolean containsKey(long key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(long key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(long key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			long key;
			int value;
			Node next;
			Node(long key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
	private static final class ShortHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x10000;
		private int mask;
		
		ShortHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(short key) {
			return key & mask;
		}
		
		boolean containsKey(short key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(short key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(short key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			short key;
			int value;
			Node next;
			Node(short key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
	private static final class CharHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x10000;
		private int mask;
		
		CharHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(char key) {
			return key & mask;
		}
		
		boolean containsKey(char key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(char key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(char key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			char key;
			int value;
			Node next;
			Node(char key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
	private static final class DoubleHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x40000000;
		private int mask;
		
		DoubleHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(double key) {
			long x = Double.doubleToLongBits(key);
			int y = (int)(x ^ (x >>> 32));
			return (y ^ (y >>> 16)) & mask;
		}
		
		boolean containsKey(double key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(double key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(double key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			double key;
			int value;
			Node next;
			Node(double key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
	private static final class FloatHT {
		
		private Node[] table;
		private static final int MAX_SIZE = 0x40000000;
		private int mask;
		
		FloatHT(int minSize) {
			if (minSize > MAX_SIZE) {
				minSize = MAX_SIZE;
				mask = minSize - 1;
			} else {
				minSize = minSize - 1;
				minSize = minSize | (minSize >> 1);
				minSize = minSize | (minSize >> 2);
				minSize = minSize | (minSize >> 4);
				minSize = minSize | (minSize >> 8);
				minSize = minSize | (minSize >> 16);
				mask = minSize;
				minSize++;
			}
			table = new Node[minSize];
		}
		
		int index(float key) {
			int x = Float.floatToIntBits(key);
			return (x ^ (x >>> 16)) & mask;
		}
		
		boolean containsKey(float key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return true;
			}
			return false;
		}
		
		int get(float key) {
			for (Node current = table[index(key)]; current != null; current = current.next) {
				if (current.key == key) return current.value;
			}
			// NOTE: our internal usage never puts a negative as a value
			return -1;
		}
		
		void put(float key, int value) {
			// warning: assumes key is not already in hash table (only used internally so ok).
			int i = index(key);
			table[i] = new Node(key, value, table[i]);
		}
		
		static final class Node {
			float key;
			int value;
			Node next;
			Node(float key, int value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}
	}
	
}