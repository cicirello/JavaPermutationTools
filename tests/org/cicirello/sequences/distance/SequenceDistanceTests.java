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
 *
 */
package org.cicirello.sequences.distance;

import org.junit.*;
import static org.junit.Assert.*;
import org.cicirello.sequences.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

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
		assertEquals("same", 0, d.distance(toList(a0),toList(a1)));
		String[] a2 = {"f", "a", "b", "c", "d", "e"};
		String[] a3 = {"f", "b", "c", "d", "e", "a"};
		String[] a4 = {"a", "d", "c", "b", "e", "f"};
		assertEquals("maximal distance", 6, d.distance(a1,a2));
		assertEquals("end points differ", 2, d.distance(a1,a3));
		assertEquals("differ in interior positions", 2, d.distance(a1,a4));
		assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
		assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
		assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);	
		assertEquals("maximal distance", 6, d.distance(toList(a1),toList(a2)));
		assertEquals("end points differ", 2, d.distance(toList(a1),toList(a3)));
		assertEquals("differ in interior positions", 2, d.distance(toList(a1),toList(a4)));
		assertEquals("maximal distance", 6, d.distancef(toList(a1),toList(a2)), EPSILON);
		assertEquals("end points differ", 2, d.distancef(toList(a1),toList(a3)), EPSILON);
		assertEquals("differ in interior positions", 2, d.distancef(toList(a1),toList(a4)), EPSILON);
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
		
		assertEquals("identical except for extras", 3, d.distance(toList(a1),toList(b1)));
		assertEquals("maximal distance", 9, d.distance(toList(a1),toList(b2)));
		assertEquals("end points of shorter differ", 5, d.distance(toList(a1),toList(b3)));
		assertEquals("differ in interior positions", 5, d.distance(toList(a1),toList(b4)));
		assertEquals("identical except for extras", 3, d.distance(toList(b1),toList(a1)));
		assertEquals("maximal distance", 9, d.distance(toList(b2),toList(a1)));
		assertEquals("end points of shorter differ", 5, d.distance(toList(b3),toList(a1)));
		assertEquals("differ in interior positions", 5, d.distance(toList(b4),toList(a1)));
		
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
		assertEquals("maximal distance", 6, d.distance(toList(c1),toList(c2)));
		assertEquals("end points differ", 2, d.distance(toList(c1),toList(c3)));
	}
	
	private ArrayList<String> toList(String[] a) {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : a) list.add(s);
		return list;
	}
	
	private ArrayList<NonComparable> toList(NonComparable[] a) {
		ArrayList<NonComparable> list = new ArrayList<NonComparable>();
		for (NonComparable s : a) list.add(s);
		return list;
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
		{ // int
			int[] a1 = {0, 1, 2, 3, 4, 5};
			int[] a2 = {5, 0, 1, 2, 3, 4};
			int[] a3 = {5, 1, 2, 3, 4, 0};
			int[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
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
		{ // long
			long[] a1 = {0, 1, 2, 3, 4, 5};
			long[] a2 = {5, 0, 1, 2, 3, 4};
			long[] a3 = {5, 1, 2, 3, 4, 0};
			long[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			long[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			long[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			long[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			long[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
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
		{ // short
			short[] a1 = {0, 1, 2, 3, 4, 5};
			short[] a2 = {5, 0, 1, 2, 3, 4};
			short[] a3 = {5, 1, 2, 3, 4, 0};
			short[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			short[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			short[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			short[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			short[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
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
		{ // byte
			byte[] a1 = {0, 1, 2, 3, 4, 5};
			byte[] a2 = {5, 0, 1, 2, 3, 4};
			byte[] a3 = {5, 1, 2, 3, 4, 0};
			byte[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			byte[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			byte[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			byte[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			byte[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
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
		{ // char
			char[] a1 = {0, 1, 2, 3, 4, 5};  
			char[] a2 = {5, 0, 1, 2, 3, 4};
			char[] a3 = {5, 1, 2, 3, 4, 0};
			char[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			String s1 = new String(a1);
			String s2 = new String(a2);
			String s3 = new String(a3);
			String s4 = new String(a4);
			assertEquals("maximal distance", 6, d.distance(s1,s2));
			assertEquals("end points differ", 2, d.distance(s1,s3));
			assertEquals("differ in interior positions", 2, d.distance(s1,s4));
			char[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			char[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			char[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			char[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
			// tests with different length sequences
			assertEquals("identical except for extras", 3, d.distance(a1,b1));
			assertEquals("maximal distance", 9, d.distance(a1,b2));
			assertEquals("end points of shorter differ", 5, d.distance(a1,b3));
			assertEquals("differ in interior positions", 5, d.distance(a1,b4));
			assertEquals("identical except for extras", 3, d.distance(b1,a1));
			assertEquals("maximal distance", 9, d.distance(b2,a1));
			assertEquals("end points of shorter differ", 5, d.distance(b3,a1));
			assertEquals("differ in interior positions", 5, d.distance(b4,a1));
			String t1 = new String(b1);
			String t2 = new String(b2);
			String t3 = new String(b3);
			String t4 = new String(b4);
			assertEquals("identical except for extras", 3, d.distance(s1,t1));
			assertEquals("maximal distance", 9, d.distance(s1,t2));
			assertEquals("end points of shorter differ", 5, d.distance(s1,t3));
			assertEquals("differ in interior positions", 5, d.distance(s1,t4));
			assertEquals("identical except for extras", 3, d.distance(t1,s1));
			assertEquals("maximal distance", 9, d.distance(t2,s1));
			assertEquals("end points of shorter differ", 5, d.distance(t3,s1));
			assertEquals("differ in interior positions", 5, d.distance(t4,s1));
			
			assertEquals("identical except for extras", 3, d.distancef(s1,t1), EPSILON);
			assertEquals("maximal distance", 9, d.distancef(s1,t2), EPSILON);
			assertEquals("end points of shorter differ", 5, d.distancef(s1,t3), EPSILON);
			assertEquals("differ in interior positions", 5, d.distancef(s1,t4), EPSILON);
			assertEquals("identical except for extras", 3, d.distancef(t1,s1), EPSILON);
			assertEquals("maximal distance", 9, d.distancef(t2,s1), EPSILON);
			assertEquals("end points of shorter differ", 5, d.distancef(t3,s1), EPSILON);
			assertEquals("differ in interior positions", 5, d.distancef(t4,s1), EPSILON);
		}
		{ // double
			double[] a1 = {0, 1, 2, 3, 4, 5};
			double[] a2 = {5, 0, 1, 2, 3, 4};
			double[] a3 = {5, 1, 2, 3, 4, 0};
			double[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			double[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			double[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			double[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			double[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
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
		{ // float
			float[] a1 = {0, 1, 2, 3, 4, 5};
			float[] a2 = {5, 0, 1, 2, 3, 4};
			float[] a3 = {5, 1, 2, 3, 4, 0};
			float[] a4 = {0, 3, 2, 1, 4, 5};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			float[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
			float[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
			float[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
			float[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
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
		{ // boolean
			boolean[] a1 = {false, true, false, true, false, true};
			boolean[] a2 = {true, false, true, false, true, false};
			boolean[] a3 = {true, true, false, true, false, false};
			boolean[] a4 = {false, true, true, false, false, true};
			assertEquals("maximal distance", 6, d.distance(a1,a2));
			assertEquals("end points differ", 2, d.distance(a1,a3));
			assertEquals("differ in interior positions", 2, d.distance(a1,a4));
			assertEquals("maximal distance", 6, d.distancef(a1,a2), EPSILON);
			assertEquals("end points differ", 2, d.distancef(a1,a3), EPSILON);
			assertEquals("differ in interior positions", 2, d.distancef(a1,a4), EPSILON);
			boolean[] b1 = {false, true, false, true, false, true, true, true, true};
			boolean[] b2 = {true, false, true, false, true, false, false, false, false};
			boolean[] b3 = {true, true, false, true, false, false, true, true, true};
			boolean[] b4 = {false, true, true, false, false, true, true, true, true};
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
	}
	
	@Test
	public void testEditDistanceExceptions() {
		final EditDistance d = new EditDistance(1.5, 1.5, 1.5);
		UnsupportedOperationException thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new int[1], new int[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new long[1], new long[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new short[1], new short[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new byte[1], new byte[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new double[1], new double[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new float[1], new float[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new boolean[1], new boolean[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new char[1], new char[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new String[1], new String[1])
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance(new ArrayList<String>(), new ArrayList<String>())
		);
		thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> d.distance("a", "a")
		);
		
		IllegalArgumentException illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(-1, 0, 0)
		);
		illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(0, -1, 0)
		);
		illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(0, 0, -1)
		);
		illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(-0.01, 0, 0)
		);
		illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(0, -0.01, 0)
		);
		illegal = assertThrows( 
			IllegalArgumentException.class,
			() -> new EditDistance(0, 0, -0.01)
		);
	}
	
	@Test
	public void testEditDistance() {
		EditDistance d = new EditDistance(1, 2, 10);
		identicalSequences(d);
		identicalSequencesD(d);
		d = new EditDistance(1.0, 2.0, 10.0);
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
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		assertEquals(16, d.distance(a1,a2));
		assertEquals(16.0, d.distancef(a1,a2), EPSILON);
		cost_i = 3;
		cost_d = 3;
		EditDistance d2 = new EditDistance(cost_i, cost_d, cost_i+cost_d);
		assertEquals(48, d2.distance(s1,s2));
		assertEquals(48.0, d2.distancef(s1,s2), EPSILON);
		{
			boolean[] b1 = {true, false, true, false, true, false, true, true, true, true, true, true};
			boolean[] b2 = {false, false, true, true, true, false, false, false};
			// lcs is 5... lengths are 12 and 8
			assertEquals(10, d.distance(b1,b2));
			assertEquals(10.0, d.distancef(b1,b2), EPSILON);
			assertEquals(30, d2.distance(b1,b2));
			assertEquals(30.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // int
			int[] b1 = new int[a1.length];
			int[] b2 = new int[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // long
			long[] b1 = new long[a1.length];
			long[] b2 = new long[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // short
			short[] b1 = new short[a1.length];
			short[] b2 = new short[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (short)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (short)a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // byte
			byte[] b1 = new byte[a1.length];
			byte[] b2 = new byte[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (byte)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (byte)a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // double
			double[] b1 = new double[a1.length];
			double[] b2 = new double[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}
		{ // float
			float[] b1 = new float[a1.length];
			float[] b2 = new float[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			assertEquals(16.0, d.distancef(b1,b2), EPSILON);
			assertEquals(48, d2.distance(b1,b2));
			assertEquals(48.0, d2.distancef(b1,b2), EPSILON);
		}	
		
		s1 = "aaaaabcdefaaaaabcdefaaaaa";
		s2 = "bbbbbbcdefbbbbbbcdefbbbbb";
		cost_i = 2;
		cost_d = 2;
		int cost_c = 3;
		d = new EditDistance(cost_i, cost_d, cost_c);
		assertEquals(45, d.distance(s1,s2));
		assertEquals(45.0, d.distancef(s1,s2), EPSILON);
		a1 = s1.toCharArray();
		a2 = s2.toCharArray();
		assertEquals(45, d.distance(a1,a2));
		assertEquals(45.0, d.distancef(a1,a2), EPSILON);
		{ // int
			int[] b1 = new int[a1.length];
			int[] b2 = new int[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		{ // long
			long[] b1 = new long[a1.length];
			long[] b2 = new long[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		{ // short
			short[] b1 = new short[a1.length];
			short[] b2 = new short[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (short)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (short)a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		{ // byte
			byte[] b1 = new byte[a1.length];
			byte[] b2 = new byte[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (byte)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (byte)a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		{ // double
			double[] b1 = new double[a1.length];
			double[] b2 = new double[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		{ // float
			float[] b1 = new float[a1.length];
			float[] b2 = new float[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(45, d.distance(b1,b2));
			assertEquals(45.0, d.distancef(b1,b2), EPSILON);
		}
		EditDistance dist1 = new EditDistance(1.1, 2.0, 2.0);
		EditDistance dist2 = new EditDistance(2.0, 1.1, 2.0);
		EditDistance dist3 = new EditDistance(2.0, 2.0, 1.1);
		assertEquals(1.1, dist1.distancef("a", "ab"), EPSILON);
		assertEquals(1.1, dist2.distancef("ab", "a"), EPSILON);
		assertEquals(1.1, dist3.distancef("a", "b"), EPSILON);
	}
	
	@Test
	public void testEditObjectSequences() {
		String[] s1 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a"};
		String[] s2 = {"k","a","m","n","o","p","a","l","a","a","q","a","a","a","a"};
		EditDistance d = new EditDistance(1, 1, 2);
		assertEquals(16, d.distance(s1,s2));
		assertEquals(16.0, d.distancef(s1,s2), EPSILON);
		assertEquals(16, d.distance(toList(s1),toList(s2)));
		assertEquals(16.0, d.distancef(toList(s1),toList(s2)), EPSILON);
		d = new EditDistance(3, 3, 6);
		assertEquals(48, d.distance(s1,s2));
		assertEquals(48.0, d.distancef(s1,s2), EPSILON);
		assertEquals(48, d.distance(toList(s1),toList(s2)));
		assertEquals(48.0, d.distancef(toList(s1),toList(s2)), EPSILON);
		
		String[] s3 = {"a","a","a","a","a","b","c","d","e","f","a","a","a","a","a","b","c","d","e","f","a","a","a","a","a"};
		String[] s4 = {"b","b","b","b","b","b","c","d","e","f","b","b","b","b","b","b","c","d","e","f","b","b","b","b","b"};
		d = new EditDistance(2, 2, 3);
		assertEquals(45, d.distance(s3,s4));
		assertEquals(45.0, d.distancef(s3,s4), EPSILON);
		assertEquals(45, d.distance(toList(s3),toList(s4)));
		assertEquals(45.0, d.distancef(toList(s3),toList(s4)), EPSILON);
		
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
		assertEquals(51, d.distance(toList(c1),toList(c2)));
	}
	
	@Test
	public void testLCSObjectSequences() {
		LongestCommonSubsequenceDistance d = new LongestCommonSubsequenceDistance();
		String[] s1 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a"};
		String[] s2 = {"k","a","m","n","o","p","a","l","a","a","q","a","a","a","a"};
		assertEquals(16, d.distance(s1,s2));
		assertEquals(16, d.distance(toList(s1),toList(s2)));
		String[] s3 = {"a","b","a","c","a","d","a","e","a","f","a","h","a","i","a","j","a","z","a","z","a","z","a","z","a","z","a"};
		assertEquals(26, d.distance(s3,s2));
		assertEquals(26, d.distance(toList(s3),toList(s2)));
		
		NonComparable[] c1 = new NonComparable[17];
		for (int i = 0; i < 17; i++) {
			c1[i] = (i%2)==0 ? new NonComparable(0) : new NonComparable(i);
		}
		NonComparable[] c2 = new NonComparable[16];
		for (int i = 0; i < 16; i++) {
			c2[i] = (i%2==0) ? new NonComparable(100 + i) : new NonComparable(0);
		}
		assertEquals(17, d.distance(c1,c2));
		assertEquals(17, d.distance(toList(c1),toList(c2)));
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
		String s3 = "abacadaeafahaiajazazazazaza";
		// increasing length of s1 without changing lcs should increase distance to 26
		assertEquals(26, d.distance(s3,s2));
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		assertEquals(16, d.distance(a1,a2));
		char[] a3 = s3.toCharArray();
		assertEquals(26, d.distance(a3,a2));
		{
			boolean[] b1 = {true, false, true, false, true, false, true, true, true, true, true, true};
			boolean[] b2 = {false, false, true, true, true, false, false, false};
			// lcs is 5... lengths are 12 and 8
			assertEquals(10, d.distance(b1,b2));
			assertEquals(10.0, d.distancef(b1,b2), EPSILON);
			boolean[] b3 = {false, false, true, true, true, false, false, false, false, false, false};
			assertEquals(13, d.distance(b1,b3));
			assertEquals(13.0, d.distancef(b1,b3), EPSILON);
		}
		{ // int
			int[] b1 = new int[a1.length];
			int[] b2 = new int[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			int[] b3 = new int[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
		{ // long
			long[] b1 = new long[a1.length];
			long[] b2 = new long[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			long[] b3 = new long[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
		{ // short
			short[] b1 = new short[a1.length];
			short[] b2 = new short[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (short)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (short)a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			short[] b3 = new short[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = (short)a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
		{ // byte
			byte[] b1 = new byte[a1.length];
			byte[] b2 = new byte[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = (byte)a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = (byte)a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			byte[] b3 = new byte[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = (byte)a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
		{ // double
			double[] b1 = new double[a1.length];
			double[] b2 = new double[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			double[] b3 = new double[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
		{ // float
			float[] b1 = new float[a1.length];
			float[] b2 = new float[a2.length];
			for (int i = 0; i < b1.length; i++) {
				b1[i] = a1[i];
			}
			for (int i = 0; i < b2.length; i++) {
				b2[i] = a2[i];
			}
			assertEquals(16, d.distance(b1,b2));
			float[] b3 = new float[a3.length];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = a3[i];
			}
			assertEquals(26, d.distance(b3,b2));
		}
	}
	
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
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			assertEquals("maximal distance", expected, d.distance(toList(s1),toList(s2)));
			assertEquals("maximal distance", expected, d.distance(toList(s2),toList(s1)));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
			assertEquals("end points swapped", expected, d.distance(toList(s1),toList(s3)));
			assertEquals("end points swapped", expected, d.distance(toList(s3),toList(s1)));
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
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			assertEquals("maximal distance", expected, d.distance(toList(s1),toList(s2)));
			assertEquals("maximal distance", expected, d.distance(toList(s2),toList(s1)));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
			assertEquals("end points swapped", expected, d.distance(toList(s1),toList(s3)));
			assertEquals("end points swapped", expected, d.distance(toList(s3),toList(s1)));
		}
	}
	
	@Test
	public void testTauAlg2ObjectSequences() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
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
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			assertEquals("maximal distance", expected, d.distance(toList(s1),toList(s2)));
			assertEquals("maximal distance", expected, d.distance(toList(s2),toList(s1)));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
			assertEquals("end points swapped", expected, d.distance(toList(s1),toList(s3)));
			assertEquals("end points swapped", expected, d.distance(toList(s3),toList(s1)));
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
			assertEquals("maximal distance", expected, d.distance(s1,s2));
			assertEquals("maximal distance", expected, d.distance(s2,s1));
			assertEquals("maximal distance", expected, d.distance(toList(s1),toList(s2)));
			assertEquals("maximal distance", expected, d.distance(toList(s2),toList(s1)));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(s1,s3));
			assertEquals("end points swapped", expected, d.distance(s3,s1));
			assertEquals("end points swapped", expected, d.distance(toList(s1),toList(s3)));
			assertEquals("end points swapped", expected, d.distance(toList(s3),toList(s1)));
		}
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
		class TestHT extends KendallTauSequenceDistance.BaseHT {
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
	
	private void helperKendallTauDistanceExceptionsDiffElements(final KendallTauSequenceDistance d) {
	
		final int[] i1 = { 1, 2, 3 };
		final int[] i2 = { 1, 2, 4 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(i1, i2)
		);
		final String[] str1 = {"a", "a", "c"};
		final String[] str2 = {"a", "a", "d"};
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(str1, str2)
		);
		final ArrayList<String> aL1 = new ArrayList<String>();
		final ArrayList<String> aL2 = new ArrayList<String>();
		for (String e : str1) aL1.add(e);
		for (String e : str2) aL2.add(e);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(aL1, aL2)
		);
		final double[] d1 = { 1, 2, 3 };
		final double[] d2 = { 1, 2, 4 };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(d1, d2)
		);
		final float[] f1 = { 1f, 2f, 3f };
		final float[] f2 = { 1f, 2f, 4f };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(f1, f2)
		);
		final long[] L1 = { 1, 2, 3 };
		final long[] L2 = { 1, 2, 4 };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(L1, L2)
		);
		final short[] sh1 = { 1, 2, 3 };
		final short[] sh2 = { 1, 2, 4 };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(sh1, sh2)
		);
		final byte[] b1 = { 1, 2, 3 };
		final byte[] b2 = { 1, 2, 4 };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(b1, b2)
		);
		final char[] ch1 = { '1', '2', '3' };
		final char[] ch2 = { '1', '2', '4' };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(ch1, ch2)
		);
		final String g1 = "123";
		final String g2 = "124";
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(g1, g2)
		);
		final boolean[] bool1 = { false, false, true };
		final boolean[] bool2 = { false, false, false };
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(bool1, bool2)
		);
		final String diffCounts1 = "ababa";
		final String diffCounts2 = "babab";
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(diffCounts1, diffCounts2)
		);
		final String diffCounts3 = "aaaab";
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(diffCounts1, diffCounts3)
		);
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
	}
	
	@Test
	public void testKendallTauDistanceAlg1() {
		KendallTauSequenceDistance d = new KendallTauSequenceDistance(false);
		identicalSequences(d);
		helperForKendallTauCases(d);
	}
	
	private void helperForKendallTauCases(KendallTauSequenceDistance d) {
		// test first with simpler cases: all unique elements
		for (int n = 2; n <= 8; n++) {
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
			// test with negative values. rationale: default algorithm
			// relies on hash tables, so make sure negatives don't break hash table.
			int[] neg1 = new int[n];
			int[] neg2 = new int[n];
			int[] neg3 = new int[n];
			for (int i = 0; i < n; i++) {
				neg1[i] = -s1[i];
				neg2[i] = -s2[i];
				neg3[i] = -s3[i];
			}
			expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(neg1,neg2));
			assertEquals("maximal distance", expected, d.distance(neg2,neg1));
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(neg1,neg3));
			assertEquals("end points swapped", expected, d.distance(neg3,neg1));
			{ // long
				long[] t1 = new long[n];
				long[] t2 = new long[n];
				long[] t3 = new long[n];
				for (int i = 0; i < n; i++) {
					t1[i] = s1[i]; t2[i] = s2[i]; t3[i] = s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				// test with negative values. rationale: default algorithm
				// relies on hash tables, so make sure negatives don't break hash table.	
				for (int i = 0; i < n; i++) {
					t1[i] = -t1[i];
					t2[i] = -t2[i];
					t3[i] = -t3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
			}
			{ // short
				short[] t1 = new short[n];
				short[] t2 = new short[n];
				short[] t3 = new short[n];
				for (int i = 0; i < n; i++) {
					t1[i] = (short)s1[i]; t2[i] = (short)s2[i]; t3[i] = (short)s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				// test with negative values. rationale: default algorithm
				// relies on hash tables, so make sure negatives don't break hash table.	
				for (int i = 0; i < n; i++) {
					t1[i] = (short)(-1 * t1[i]);
					t2[i] = (short)(-1 * t2[i]);
					t3[i] = (short)(-1 * t3[i]);
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
			}
			{ // byte
				byte[] t1 = new byte[n];
				byte[] t2 = new byte[n];
				byte[] t3 = new byte[n];
				for (int i = 0; i < n; i++) {
					t1[i] = (byte)s1[i]; t2[i] = (byte)s2[i]; t3[i] = (byte)s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				// test with negative values. rationale: default algorithm
				// relies on hash tables, so make sure negatives don't break hash table.	
				for (int i = 0; i < n; i++) {
					t1[i] = (byte)(-1 * t1[i]);
					t2[i] = (byte)(-1 * t2[i]);
					t3[i] = (byte)(-1 * t3[i]);
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
			}
			{ // char
				char[] t1 = new char[n];
				char[] t2 = new char[n];
				char[] t3 = new char[n];
				for (int i = 0; i < n; i++) {
					t1[i] = (char)s1[i]; t2[i] = (char)s2[i]; t3[i] = (char)s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				String u1 = new String(t1);
				String u2 = new String(t2);
				String u3 = new String(t3);
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(u1,u2));
				assertEquals("maximal distance", expected, d.distance(u2,u1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(u1,u3));
				assertEquals("end points swapped", expected, d.distance(u3,u1));
			}
			{ // float
				float[] t1 = new float[n];
				float[] t2 = new float[n];
				float[] t3 = new float[n];
				for (int i = 0; i < n; i++) {
					t1[i] = s1[i]; t2[i] = s2[i]; t3[i] = s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				// test with negative values. rationale: default algorithm
				// relies on hash tables, so make sure negatives don't break hash table.	
				for (int i = 0; i < n; i++) {
					t1[i] = -t1[i];
					t2[i] = -t2[i];
					t3[i] = -t3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
			}
			{ // double
				double[] t1 = new double[n];
				double[] t2 = new double[n];
				double[] t3 = new double[n];
				for (int i = 0; i < n; i++) {
					t1[i] = s1[i]; t2[i] = s2[i]; t3[i] = s3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
				// test with negative values. rationale: default algorithm
				// relies on hash tables, so make sure negatives don't break hash table.	
				for (int i = 0; i < n; i++) {
					t1[i] = -t1[i];
					t2[i] = -t2[i];
					t3[i] = -t3[i];
				}
				expected = n*(n-1)/2;
				assertEquals("maximal distance", expected, d.distance(t1,t2));
				assertEquals("maximal distance", expected, d.distance(t2,t1));
				expected = 2*n-3;
				assertEquals("end points swapped", expected, d.distance(t1,t3));
				assertEquals("end points swapped", expected, d.distance(t3,t1));
			}
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
		{ // long
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			long[] t1 = new long[5];
			for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
			long[] t2 = new long[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
			}
		}
		{ // short
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			short[] t1 = new short[5];
			for (int i = 0; i < 5; i++) s1[i] = t1[i] = (short)r.get(i);
			short[] t2 = new short[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) s2[i] = t2[i] = (short)q.get(i);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
			}
		}
		{ // byte
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			byte[] t1 = new byte[5];
			for (int i = 0; i < 5; i++) s1[i] = t1[i] = (byte)r.get(i);
			byte[] t2 = new byte[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) s2[i] = t2[i] = (byte)q.get(i);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
			}
		}
		{ // char
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			char[] t1 = new char[5];
			for (int i = 0; i < 5; i++) s1[i] = t1[i] = (char)r.get(i);
			String u1 = new String(t1);
			char[] t2 = new char[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) s2[i] = t2[i] = (char)q.get(i);
				String u2 = new String(t2);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(u1,u2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(u2,u1));
			}
		}
		{ // float
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			float[] t1 = new float[5];
			for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
			float[] t2 = new float[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
			}
		}
		{ // double
			s1 = new int[5];
			s2 = new int[5];
			Permutation r = new Permutation(5);
			double[] t1 = new double[5];
			for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
			double[] t2 = new double[5];
			for (Permutation q : r) {
				for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
				int expected = naiveKendalTau(s1,s2);
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t1,t2));
				assertEquals("checking consistence with naive implementation of unique element version", expected, d.distance(t2,t1));
			}
		}
		// Now test with duplicate elements
		String t1 = "abcdaabb";
		String t2 = "dcbababa";
		assertEquals("case where discordant pair counting fails", 9, d.distance(t1,t2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(t2,t1));
		char[] c1 = t1.toCharArray();
		char[] c2 = t2.toCharArray();
		long[] L1 = new long[c1.length];
		long[] L2 = new long[c2.length];
		short[] sh1 = new short[c1.length];
		short[] sh2 = new short[c2.length];
		byte[] b1 = new byte[c1.length];
		byte[] b2 = new byte[c2.length];
		int[] i1 = new int[c1.length];
		int[] i2 = new int[c2.length];
		float[] f1 = new float[c1.length];
		float[] f2 = new float[c2.length];
		double[] d1 = new double[c1.length];
		double[] d2 = new double[c2.length];
		for (int i = 0; i < c1.length; i++) {
			L1[i] = i1[i] = c1[i];
			d1[i] = f1[i] = i1[i];
			sh1[i] = (short)c1[i];
			b1[i] = (byte)c1[i];
			L2[i] = i2[i] = c2[i];
			d2[i] = f2[i] = i2[i];
			sh2[i] = (short)c2[i];
			b2[i] = (byte)c2[i];
		}
		assertEquals("case where discordant pair counting fails", 9, d.distance(c1,c2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(c2,c1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(L1,L2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(L2,L1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(sh1,sh2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(sh2,sh1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(b1,b2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(b2,b1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(i1,i2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(i2,i1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(f1,f2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(f2,f1));
		assertEquals("case where discordant pair counting fails", 9, d.distance(d1,d2));
		assertEquals("case where discordant pair counting fails", 9, d.distance(d2,d1));
		for (int n = 2; n < 8; n++) {
			for (int i = 1; i < n; i++) {
				boolean[] a1 = new boolean[n];
				boolean[] a2 = new boolean[n];
				for (int j = 0; j < i; j++) {
					a2[j] = a1[n-1-j] = true;
				}
				int expected = i * (n-i);
				assertEquals("boolean case", expected, d.distance(a2,a1));
				assertEquals("boolean case", expected, d.distance(a1,a2));
			}
		}
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
			int[] a1 = new int[n];
			long[] a2 = new long[n];
			short[] a3 = new short[n];
			byte[] a4 = new byte[n];
			char[] a5 = new char[n];
			float[] a6 = new float[n];
			double[] a7 = new double[n];
			boolean[] a8 = new boolean[n];
			for (int i = 0; i < n; i++) {
				a1[i] = ThreadLocalRandom.current().nextInt(100);
				a2[i] = ThreadLocalRandom.current().nextInt(100);
				a3[i] = (short)ThreadLocalRandom.current().nextInt(100);
				a4[i] = (byte)ThreadLocalRandom.current().nextInt(100);
				a5[i] = (char)ThreadLocalRandom.current().nextInt(100);
				a6[i] = ThreadLocalRandom.current().nextInt(100);
				a7[i] = ThreadLocalRandom.current().nextInt(100);
				a8[i] = ThreadLocalRandom.current().nextBoolean();
			}
			String a9 = new String(a5);
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a1, a1.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a2, a2.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a3, a3.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a4, a4.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a5, a5.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a6, a6.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a7, a7.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a8, a8.clone()));
			assertEquals("distance of a sequence to itself should be 0", 0, d.distance(a9, new String(a9)));
		}
	}
	
	private void identicalSequencesD(SequenceDistanceMeasurerDouble d) {
		for (int n = 0; n <= 10; n++) {
			int[] a1 = new int[n];
			long[] a2 = new long[n];
			short[] a3 = new short[n];
			byte[] a4 = new byte[n];
			char[] a5 = new char[n];
			float[] a6 = new float[n];
			double[] a7 = new double[n];
			boolean[] a8 = new boolean[n];
			for (int i = 0; i < n; i++) {
				a1[i] = ThreadLocalRandom.current().nextInt(100);
				a2[i] = ThreadLocalRandom.current().nextInt(100);
				a3[i] = (short)ThreadLocalRandom.current().nextInt(100);
				a4[i] = (byte)ThreadLocalRandom.current().nextInt(100);
				a5[i] = (char)ThreadLocalRandom.current().nextInt(100);
				a6[i] = ThreadLocalRandom.current().nextInt(100);
				a7[i] = ThreadLocalRandom.current().nextInt(100);
				a8[i] = ThreadLocalRandom.current().nextBoolean();
			}
			String a9 = new String(a5);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a1, a1.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a2, a2.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a3, a3.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a4, a4.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a5, a5.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a6, a6.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a7, a7.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a8, a8.clone()), EPSILON);
			assertEquals("distance of a sequence to itself should be 0", 0.0, d.distancef(a9, new String(a9)), EPSILON);
		}
	}

}


