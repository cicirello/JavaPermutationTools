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
package org.cicirello.sequences.distance;

import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

/**
 * Internal class for use by relabeling phase for computing Kendall Tau Sequence 
 * Distance.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, 
 * <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
final class RelabelByHashing implements KendallTauRelabeler {
	
	@Override
	public int relabel(Object[] s1, Object[] s2, int[][] relabeling) {
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
	
	@Override
	public <T> int relabel(List<T> s1, List<T> s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(int[] s1, int[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(double[] s1, double[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(float[] s1, float[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(long[] s1, long[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(short[] s1, short[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(char[] s1, char[] s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(String s1, String s2, int[][] relabeling) {
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
	
	@Override
	public int relabel(byte[] s1, byte[] s2, int[][] relabeling) {
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
	
	static class BaseHT {
		protected final int mask;
		protected final int minSize;
		
		BaseHT(int maxSize, int minSize) {
			final int MAX_SIZE = maxSize;
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
			this.minSize = minSize;
		}
	}
	
	private static final class IntHT extends BaseHT {
		
		private final Node[] table;
		
		IntHT(int min) {
			super(0x40000000, min);
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
	
	private static final class LongHT extends BaseHT {
		
		private final Node[] table;
		
		LongHT(int min) {
			super(0x40000000, min);
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
	
	private static final class ShortHT extends BaseHT {
		
		private final Node[] table;
		
		ShortHT(int min) {
			super(0x10000, min);
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
	
	private static final class CharHT extends BaseHT {
		
		private final Node[] table;
		
		CharHT(int min) {
			super(0x10000, min);
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
	
	private static final class DoubleHT extends BaseHT {
		
		private final Node[] table;
		
		DoubleHT(int min) {
			super(0x40000000, min);
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
	
	private static final class FloatHT extends BaseHT {
		
		private final Node[] table;
		
		FloatHT(int min) {
			super(0x40000000, min);
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
