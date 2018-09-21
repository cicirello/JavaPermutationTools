/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

/**
 * JUnit 4 tests for the Sequence classes (i.e., classes that implement Sequence interface).
 */
public class SequenceTests {
	
	@Test
	public void testObjectSequence() {
		// Testing ObjectSequence with a sequence of strings.
		// If it works for String objects, it should work for any other class of objects that are Comparable
		final int N = 26;
		String[] array = new String[N];
		for (int i = 0; i < N; i++) {
			char[] c = { (char)('A' + i) };
			array[i] = new String(c);
		}
		Sequence<String> paw = new ObjectSequence<String>(array);
		Sequence<String> other = new ObjectSequence<String>(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		String pv1 = paw.get(3);
		paw.set(7, pv1);
		String pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		String before1 = other.get(2);
		String before2 = other.get(6);
		other.swap(2, 6);
		String after1 = other.get(2);
		String after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<String> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			String v1 = paw.get(i);
			String v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		String v0_1 = paw.get(0);
		String vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			String v1 = paw.get(i);
			String v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}

	@Test
	public void testPrimitiveSequenceLong() {
		final int N = 10;
		long[] array = new long[N];
		for (int i = 0; i < N; i++) {
			array[i] = i+1;
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceInt() {
		final int N = 10;
		int[] array = new int[N];
		for (int i = 0; i < N; i++) {
			array[i] = i+1;
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceShort() {
		final int N = 10;
		short[] array = new short[N];
		for (int i = 0; i < N; i++) {
			array[i] = (short)(i+1);
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceByte() {
		final int N = 10;
		byte[] array = new byte[N];
		for (int i = 0; i < N; i++) {
			array[i] = (byte)(i+1);
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceChar() {
		final int N = 10;
		char[] array = new char[N];
		for (int i = 0; i < N; i++) {
			array[i] = (char)(i+1);
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceDouble() {
		final int N = 10;
		double[] array = new double[N];
		for (int i = 0; i < N; i++) {
			array[i] = (double)(i+1);
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}

	@Test
	public void testPrimitiveSequenceFloat() {
		final int N = 10;
		float[] array = new float[N];
		for (int i = 0; i < N; i++) {
			array[i] = (float)(i+1);
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) assertTrue(paw.equal(i,j));
				if (i<j) assertTrue(paw.lessThan(i,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,j));
				if (i>j) assertTrue(paw.greaterThan(i,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,j));
				if (i==j) assertTrue(paw.equal(i,other,j));
				if (i<j) assertTrue(paw.lessThan(i,other,j));
				if (i<=j) assertTrue(paw.lessThanOrEqual(i,other,j));
				if (i>j) assertTrue(paw.greaterThan(i,other,j));
				if (i>=j) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(7, pv1);
		PrimitiveValue pv2 = paw.get(7);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(6);
		other.swap(2, 6);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(6);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}
	
	@Test
	public void testPrimitiveSequenceBoolean() {
		final int N = 10;
		boolean[] array = new boolean[N];
		for (int i = 0; i < N; i++) {
			array[i] = i%2==0;
		}
		Sequence<PrimitiveValue> paw = new PrimitiveSequence(array);
		Sequence<PrimitiveValue> other = new PrimitiveSequence(array.clone());
		assertEquals("length", N, paw.length());
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if ((i%2) == (j%2)) assertTrue(paw.equal(i,j));
				if ((i%2==1) && (j%2==0)) assertTrue(paw.lessThan(i,j));
				if ((i%2==1) || (j%2==0)) assertTrue(paw.lessThanOrEqual(i,j));
				if ((j%2==1) && (i%2==0)) assertTrue(paw.greaterThan(i,j));
				if ((j%2==1) || (i%2==0)) assertTrue(paw.greaterThanOrEqual(i,j));
				if ((i%2) == (j%2)) assertTrue(paw.equal(i,other,j));
				if ((i%2==1) && (j%2==0)) assertTrue(paw.lessThan(i,other,j));
				if ((i%2==1) || (j%2==0)) assertTrue(paw.lessThanOrEqual(i,other,j));
				if ((j%2==1) && (i%2==0)) assertTrue(paw.greaterThan(i,other,j));
				if ((j%2==1) || (i%2==0)) assertTrue(paw.greaterThanOrEqual(i,other,j));
			}
		}
		PrimitiveValue pv1 = paw.get(3);
		paw.set(6, pv1);
		PrimitiveValue pv2 = paw.get(6);
		assertEquals("get and set", pv1, pv2);
		PrimitiveValue before1 = other.get(2);
		PrimitiveValue before2 = other.get(7);
		other.swap(2, 7);
		PrimitiveValue after1 = other.get(2);
		PrimitiveValue after2 = other.get(7);
		assertEquals("swap", before1, after2);
		assertEquals("swap", before2, after1);
		
		Sequence<PrimitiveValue> copy = paw.copy();
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertTrue(v1.equals(v2));
		}
		PrimitiveValue v0_1 = paw.get(0);
		PrimitiveValue vN_2 = copy.get(N-1);
		for (int i = 0; i < N; i++) {
			paw.set(i, v0_1);
			copy.set(i, vN_2);
		}
		for (int i = 0; i < N; i++) {
			PrimitiveValue v1 = paw.get(i);
			PrimitiveValue v2 = copy.get(i);
			assertFalse(v1.equals(v2));
		}
	}



}