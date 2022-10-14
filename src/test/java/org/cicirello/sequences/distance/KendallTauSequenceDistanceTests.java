/*
 * Copyright 2018-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * JUnit tests for KendallTauSequenceDistance.
 */
public class KendallTauSequenceDistanceTests extends InternalTestHelpersKendallTau {
	
	@Test
	public void testTauObjectSequences() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance();
		assertEquals(0, d.distance(new String[0], new String[0]));
		assertEquals(0, d.distance(new ArrayList<String>(), new ArrayList<String>())); 
		for (int n = 2; n <= 8; n++) {
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
			// maximal distance
			int expected = n*(n-1)/2;
			assertEquals(expected, d.distance(s1,s2));
			assertEquals(expected, d.distance(s2,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s2)));
			assertEquals(expected, d.distance(toList(s2),toList(s1)));
			// end points swapped
			expected = 2*n-3;
			assertEquals(expected, d.distance(s1,s3));
			assertEquals(expected, d.distance(s3,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s3)));
			assertEquals(expected, d.distance(toList(s3),toList(s1)));
		}
		for (int n = 2; n <= 8; n++) {
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
			assertEquals(expected, d.distance(s1,s2));
			assertEquals(expected, d.distance(s2,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s2)));
			assertEquals(expected, d.distance(toList(s2),toList(s1)));
			expected = 2*n-3;
			assertEquals(expected, d.distance(s1,s3));
			assertEquals(expected, d.distance(s3,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s3)));
			assertEquals(expected, d.distance(toList(s3),toList(s1)));
		}
	}
	
	@Test
	public void testTauAlg2ObjectSequences() {
		final KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
		for (int n = 2; n <= 8; n++) {
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
			// maximal distance
			int expected = n*(n-1)/2;
			assertEquals(expected, d.distance(s1,s2));
			assertEquals(expected, d.distance(s2,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s2)));
			assertEquals(expected, d.distance(toList(s2),toList(s1)));
			// end points swapped
			expected = 2*n-3;
			assertEquals(expected, d.distance(s1,s3));
			assertEquals(expected, d.distance(s3,s1));
			assertEquals(expected, d.distance(toList(s1),toList(s3)));
			assertEquals(expected, d.distance(toList(s3),toList(s1)));
		}
		
		NonComparable[] s1 = new NonComparable[3];
		NonComparable[] s2 = new NonComparable[3];
		for (int i = 0; i < 3; i++) {
			s1[i] = s2[i] = new NonComparable(i+2);
		}
		ClassCastException thrown = assertThrows( 
			ClassCastException.class,
			() -> d.distance(s1, s2)
		);
		ArrayStoreException thrown2 = assertThrows( 
			ArrayStoreException.class,
			() -> d.distance(toList(s1), toList(s2))
		);
	}
	
	@Test
	public void testKendallTauDistanceExceptions() {
		final KendallTauSequenceDistance d = new KendallTauSequenceDistance();
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new int[3], new int[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new long[3], new long[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new short[3], new short[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new byte[3], new byte[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new double[3], new double[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new float[3], new float[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new boolean[3], new boolean[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new char[3], new char[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new String[3], new String[4])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance("hello", "hello again")
		);
		final ArrayList<String> s1 = new ArrayList<String>();
		final ArrayList<String> s2 = new ArrayList<String>();
		s1.add("a");
		s1.add("b");
		s2.add("a");
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(s1, s2)
		);
	}
	
	@Test
	public void testKendallTauSequenceDistance_HashTableBaseClass() {
		class TestHT extends RelabelByHashing.BaseHT {
			TestHT(int min) {
				super(32, min);
			}
		}
		for (int n = 1; n <= 32; n *= 2) {
			TestHT ht = new TestHT(n);
			assertEquals(n, ht.minSize);
			assertEquals(n-1, ht.mask);
		}
		TestHT ht = new TestHT(3);
		assertEquals(4, ht.minSize);
		for (int n = 5; n < 8; n++) {
			ht = new TestHT(n);
			assertEquals(8, ht.minSize);
			assertEquals(7, ht.mask);
		}
		for (int n = 9; n < 16; n++) {
			ht = new TestHT(n);
			assertEquals(16, ht.minSize);
			assertEquals(15, ht.mask);
		}
		for (int n = 17; n <= 64; n++) {
			ht = new TestHT(n);
			assertEquals(32, ht.minSize);
			assertEquals(31, ht.mask);
		}
	}
	
	@Test
	public void testKendallTauDistanceExceptionsDiffElements() {
		final KendallTauSequenceDistance d = new KendallTauSequenceDistance();
		helperKendallTauDistanceExceptionsDiffElements(d);
	}
	
	@Test
	public void testKendallTauDistanceExceptionsDiffElementsAlg2() {
		final KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
		helperKendallTauDistanceExceptionsDiffElements(d);
	}
	
	@Test
	public void testKendallTauDistance() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance();
		identicalSequences(d);
		helperForKendallTauCases(d);
	}
	
	@Test
	public void testKendallTauDistanceAlg2() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
		identicalSequences(d);
		helperForKendallTauCases(d);
		helperForKendallTauBooleans(d);
	}
	
	@Test
	public void testKendallTauDistanceAlg1() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance(false);
		identicalSequences(d);
		helperForKendallTauCases(d);
		helperForKendallTauBooleans(d);
	}
}
