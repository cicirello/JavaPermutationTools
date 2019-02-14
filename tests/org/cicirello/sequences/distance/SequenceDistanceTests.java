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
 *
 */
package org.cicirello.sequences.distance;

import org.junit.*;
import static org.junit.Assert.*;
import org.cicirello.sequences.*;
import java.util.concurrent.ThreadLocalRandom;

// KendallTau tests uses Permutation class
import org.cicirello.permutations.Permutation;

/**
 * JUnit 4 tests for the various classes that implement sequence distance metrics.
 */
public class SequenceDistanceTests {
	
	private final static double EPSILON = 1e-10;
	
	@Test
	public void testEMObjectSequences() {
		ExactMatchDistance d = new ExactMatchDistance();
		String[] a0 = {"a", "b", "c", "d", "e", "f"};
		String[] a1 = {"a", "b", "c", "d", "e", "f"};
		assertEquals("same", 0, d.distance(a0,a1));
		String[] a2 = {"f", "a", "b", "c", "d", "e"};
		String[] a3 = {"f", "b", "c", "d", "e", "a"};
		String[] a4 = {"a", "d", "c", "b", "e", "f"};
		assertEquals("maximal distance", 6, d.distance(a1,a2));
		assertEquals("end points differ", 2, d.distance(a1,a3));
		assertEquals("differ in interior positions", 2, d.distance(a1,a4));
		String[] b1 = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
		String[] b2 = {"f", "a", "b", "c", "d", "e", "g", "h", "i"};
		String[] b3 = {"f", "b", "c", "d", "e", "a", "g", "h", "i"};
		String[] b4 = {"a", "d", "c", "b", "e", "f", "g", "h", "i"};
		assertEquals("identical except for extras", 3, d.distance(a1,b1));
		assertEquals("maximal distance", 9, d.distance(a1,b2));
		assertEquals("end points of shorter differ", 5, d.distance(a1,b3));
		assertEquals("differ in interior positions", 5, d.distance(a1,b4));
		assertEquals("identical except for extras", 3, d.distance(b1,a1));
		assertEquals("maximal distance", 9, d.distance(b2,a1));
		assertEquals("end points of shorter differ", 5, d.distance(b3,a1));
		assertEquals("differ in interior positions", 5, d.distance(b4,a1));
		
		NonComparable[] c1 = new NonComparable[6];
		NonComparable[] c2 = new NonComparable[6];
		NonComparable[] c3 = new NonComparable[6];
		for (int i = 0; i < c1.length; i++) {
			c1[i] = new NonComparable(i);
			c2[(i+1)%c2.length] = new NonComparable(i);
			c3[i] = new NonComparable(i);
		}
		NonComparable temp = c3[0];
		c3[0] = c3[5];
		c3[5] = temp;
		assertEquals("maximal distance", 6, d.distance(c1,c2));
		assertEquals("end points differ", 2, d.distance(c1,c3));
	}
	
	private static class NonComparable {
		private int id;
		public NonComparable(int me) {
			id = me;
		}
		public boolean equals(Object other) {
			return ((NonComparable)other).id == id;
		}
		public int hashCode() {
			return id;
		}
	}
	
	@Test
	public void testExactMatchDistance() {
		ExactMatchDistance d = new ExactMatchDistance();
		identicalSequences(d);
		int[] a1 = {0, 1, 2, 3, 4, 5};
		int[] a2 = {5, 0, 1, 2, 3, 4};
		int[] a3 = {5, 1, 2, 3, 4, 0};
		int[] a4 = {0, 3, 2, 1, 4, 5};
		// testing with sequences of any one type should be sufficient, since the various sequence types have been separately tested,
		// and all of the individual distance methods delegate computation to a common method.
		assertEquals("maximal distance", 6, d.distance(a1,a2));
		assertEquals("end points differ", 2, d.distance(a1,a3));
		assertEquals("differ in interior positions", 2, d.distance(a1,a4));
		int[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		int[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
		int[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
		int[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
		// tests with different length sequences
		assertEquals("identical except for extras", 3, d.distance(a1,b1));
		assertEquals("maximal distance", 9, d.distance(a1,b2));
		assertEquals("end points of shorter differ", 5, d.distance(a1,b3));
		assertEquals("differ in interior positions", 5, d.distance(a1,b4));
		assertEquals("identical except for extras", 3, d.distance(b1,a1));
		assertEquals("maximal distance", 9, d.distance(b2,a1));
		assertEquals("end points of shorter differ", 5, d.distance(b3,a1));
		assertEquals("differ in interior positions", 5, d.distance(b4,a1));
	}
	
	@Test
	public void testEditDistance() {
		EditDistance d = new EditDistance(1, 2, 10);
		identicalSequences(d);
		identicalSequencesD(d);
		d = new EditDistance(2, 1, 10);
		identicalSequences(d);
		identicalSequencesD(d);
		d = new EditDistance(3, 2, 1);
		identicalSequences(d);
		identicalSequencesD(d);
		d = new EditDistance(4, 5, 2);
		identicalSequences(d);
		identicalSequencesD(d);
		d = new EditDistance(4.2, 5.2, 2.2);
		identicalSequencesD(d);
		d = new EditDistance(3.2, 2.2, 1.2);
		identicalSequencesD(d);
		d = new EditDistance(2.2, 1.2, 10.2);
		identicalSequencesD(d);
		d = new EditDistance(1.2, 2.2, 10.2);
		identicalSequencesD(d);
		int cost_i = 1;
		int cost_d = 1;
		// lcs of next pair is 8... lengths 17 and 15
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		d = new EditDistance(cost_i, cost_d, cost_i+cost_d);
		assertEquals(16, d.distance(s1,s2));
		assertEquals(16.0, d.distancef(s1,s2), EPSILON);
		cost_i = 3;
		cost_d = 3;
		d = new EditDistance(cost_i, cost_d, cost_i+cost_d);
		assertEquals(48, d.distance(s1,s2));
		assertEquals(48.0, d.distancef(s1,s2), EPSILON);
		s1 = "aaaaabcdefaaaaabcdefaaaaa";
		s2 = "bbbbbbcdefbbbbbbcdefbbbbb";
		cost_i = 2;
		cost_d = 2;
		int cost_c = 3;
		d = new EditDistance(cost_i, cost_d, cost_c);
		assertEquals(45, d.distance(s1,s2));
		assertEquals(45.0, d.distancef(s1,s2), EPSILON);
	}
	
	@Test
	public void testEditObjectSequences() {
		String[] s1 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a"};
		String[] s2 = {"k","a","m","n","o","p","a","l","a","a","q","a","a","a","a"};
		EditDistance d = new EditDistance(1, 1, 2);
		assertEquals(16, d.distance(s1,s2));
		assertEquals(16.0, d.distancef(s1,s2), EPSILON);
		d = new EditDistance(3, 3, 6);
		assertEquals(48, d.distance(s1,s2));
		assertEquals(48.0, d.distancef(s1,s2), EPSILON);
		
		String[] s3 = {"a","a","a","a","a","b","c","d","e","f","a","a","a","a","a","b","c","d","e","f","a","a","a","a","a"};
		String[] s4 = {"b","b","b","b","b","b","c","d","e","f","b","b","b","b","b","b","c","d","e","f","b","b","b","b","b"};
		d = new EditDistance(2, 2, 3);
		assertEquals(45, d.distance(s3,s4));
		assertEquals(45.0, d.distancef(s3,s4), EPSILON);
		
		d = new EditDistance(3, 3, 6);
		NonComparable[] c1 = new NonComparable[17];
		for (int i = 0; i < 17; i++) {
			c1[i] = (i%2)==0 ? new NonComparable(0) : new NonComparable(i);
		}
		NonComparable[] c2 = new NonComparable[16];
		for (int i = 0; i < 16; i++) {
			c2[i] = (i%2==0) ? new NonComparable(100 + i) : new NonComparable(0);
		}
		assertEquals(51, d.distance(c1,c2));
	}
	
	@Test
	public void testLCSObjectSequences() {
		LongestCommonSubsequenceDistance d = new LongestCommonSubsequenceDistance();
		String[] s1 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a"};
		String[] s2 = {"k","a","m","n","o","p","a","l","a","a","q","a","a","a","a"};
		assertEquals(16, d.distance(s1,s2));
		String[] s3 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a","z","a","z","a","z","a","z","a","z","a"};
		assertEquals(26, d.distance(s3,s2));
		
		NonComparable[] c1 = new NonComparable[17];
		for (int i = 0; i < 17; i++) {
			c1[i] = (i%2)==0 ? new NonComparable(0) : new NonComparable(i);
		}
		NonComparable[] c2 = new NonComparable[16];
		for (int i = 0; i < 16; i++) {
			c2[i] = (i%2==0) ? new NonComparable(100 + i) : new NonComparable(0);
		}
		assertEquals(17, d.distance(c1,c2));
	}
	
	@Test
	public void testLongestCommonSubsequenceDistance() {
		LongestCommonSubsequenceDistance d = new LongestCommonSubsequenceDistance();
		identicalSequences(d);
		// lcs of next pair is 8... lengths 17 and 15
		// distance should be 17+15-2*8 = 16
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		assertEquals(16, d.distance(s1,s2));
		s1 = "abacadaeafahaiajazazazazaza";
		// increasing length of s1 without changing lcs should increase distance to 26
		assertEquals(26, d.distance(s1,s2));
	}
	
	@Test
	public void testTauObjectSequences() {
		KendallTauDistance d = new KendallTauDistance();
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			String[] s1 = new String[n];
			String[] s2 = new String[n];
			String[] s3 = new String[n];
			char letter = 'a';
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = (""+letter);
				letter = (char)(letter + 1);
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			NonComparable[] s1 = new NonComparable[n];
			NonComparable[] s2 = new NonComparable[n];
			NonComparable[] s3 = new NonComparable[n];
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = new NonComparable(i+2);
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
	}
	
	@Test
	public void testTauAlg2ObjectSequences() {
		KendallTauDistance d = new KendallTauDistance(true);
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			String[] s1 = new String[n];
			String[] s2 = new String[n];
			String[] s3 = new String[n];
			char letter = 'a';
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = (""+letter);
				letter = (char)(letter + 1);
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			NonComparable[] s1 = new NonComparable[n];
			NonComparable[] s2 = new NonComparable[n];
			NonComparable[] s3 = new NonComparable[n];
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = new NonComparable(i+2);
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
	}
	
	@Test
	public void testKendallTauDistance() {
		KendallTauDistance d = new KendallTauDistance();
		identicalSequences(d);
		// test first with simpler cases: all unique elements
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			int[] s1 = new int[n];
			int[] s2 = new int[n];
			int[] s3 = new int[n];
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = i+2;
				// deliberately didn't use 0 to n-1 for a bit of white box testing (i.e., testing the indexing into arrays of queues)
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
		Permutation p = new Permutation(6);
		int[] s1 = new int[6];
		for (int i = 0; i < 6; i++) s1[i] = p.get(i);
		int[] s2 = new int[6];
		for (Permutation q : p) {
			for (int i = 0; i < 6; i++) s2[i] = q.get(i);
			int expected = naiveKendalTau(s1,s2);
			assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(s1,s2));
			assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(s2,s1));
		}
		// Now test with duplicate elements
		String t1 = "abcdaabb";
		String t2 = "dcbababa";
		assertEquals("case where discordant pair counting fails", 9, d.distance(t1,t2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(t2,t1));
	}
	
	@Test
	public void testKendallTauDistanceAlg2() {
		KendallTauDistance d = new KendallTauDistance(true);
		identicalSequences(d);
		// test first with simpler cases: all unique elements
		for (int n = 2; n <= 10; n++) {
			//maximal distance if all unique elements (i.e., a permutation) is reversed sequence
			int[] s1 = new int[n];
			int[] s2 = new int[n];
			int[] s3 = new int[n];
			for (int i = 0; i < n; i++) {
				s3[i] = s1[i] = s2[n-1-i] = i+2;
				// deliberately didn't use 0 to n-1 for a bit of white box testing (i.e., testing the indexing into arrays of queues)
			}
			s3[0] = s2[0];
			s3[n-1] = s2[n-1];
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
		}
		Permutation p = new Permutation(6);
		int[] s1 = new int[6];
		for (int i = 0; i < 6; i++) s1[i] = p.get(i);
		int[] s2 = new int[6];
		for (Permutation q : p) {
			for (int i = 0; i < 6; i++) s2[i] = q.get(i);
			int expected = naiveKendalTau(s1,s2);
			assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(s1,s2));
			assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(s2,s1));
		}
		// Now test with duplicate elements
		String t1 = "abcdaabb";
		String t2 = "dcbababa";
		assertEquals("case where discordant pair counting fails", 9, d.distance(t1,t2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(t2,t1));
	}
	
	// simple naive O(n^2) version if elements are all unique
	private int naiveKendalTau(int[] s1, int[] s2) {
		int count = 0;
		int L1 = s1.length;
	  
		int[] invS1 = new int[L1];
		int[] invS2 = new int[L1];
		for (int i = 0; i < L1; i++) {
			invS2[s2[i]] = invS1[s1[i]] = i;
		}
		
		for (int i = 0; i < L1-1; i++) {
			for (int j = i+1; j < L1; j++) {
				if ((invS1[i]-invS1[j])*(invS2[i]-invS2[j]) < 0) count++; 
			}
		}
		
		return count;
	}
	
	
	
	private void identicalSequences(SequenceDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) a[i] = ThreadLocalRandom.current().nextInt(100);
			// testing with sequences of any one type should be sufficient, since the various sequence types have been separately tested,
			// and all of the individual distance methods delegate computation to a common method.
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a, a.clone()));
		}
	}
	
	private void identicalSequencesD(SequenceDistanceMeasurerDouble d) {
		for (int n = 0; n <= 10; n++) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) a[i] = ThreadLocalRandom.current().nextInt(100);
			// testing with sequences of any one type should be sufficient, since the various sequence types have been separately tested,
			// and all of the individual distance methods delegate computation to a common method.
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a, a.clone()), EPSILON);
		}
	}

}


