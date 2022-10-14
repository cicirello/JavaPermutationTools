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

/**
 * JUnit tests for LongestCommonSubsequenceDistance.
 */
public class LongestCommonSubsequenceTests extends InternalTestHelpersSequenceDistance {
	
	private LongestCommonSubsequenceDistance d;
		
	@BeforeEach
	public void init() {
		d = new LongestCommonSubsequenceDistance(); 
	}
	
	@Test
	public void testLCSObjectSequences() {
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
	public void testIdentical() {
		identicalSequences(d);
	}
	
	@Test
	public void testBoolean() {
		boolean[] b1 = {true, false, true, false, true, false, true, true, true, true, true, true};
		boolean[] b2 = {false, false, true, true, true, false, false, false};
		// lcs is 5... lengths are 12 and 8
		assertEquals(10, d.distance(b1,b2));
		assertEquals(10.0, d.distancef(b1,b2));
		boolean[] b3 = {false, false, true, true, true, false, false, false, false, false, false};
		assertEquals(13, d.distance(b1,b3));
		assertEquals(13.0, d.distancef(b1,b3));
	}
	
	@Test
	public void testInt() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testLong() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testShort() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testByte() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testDouble() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testFloat() {
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		char[] a3 = s3.toCharArray();
		
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
	
	@Test
	public void testCharAndStrings() {
		// lcs of next pair is 8... lengths 17 and 15
		// distance should be 17+15-2*8 = 16
		String s1 = "abacadaeafahaiaja";
		String s2 = "kamnopalaaqaaaa";
		String s3 = "abacadaeafahaiajazazazazaza";
		assertEquals(16, d.distance(s1,s2));
		// increasing length of s1 without changing lcs should increase distance to 26
		assertEquals(26, d.distance(s3,s2));
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		assertEquals(16, d.distance(a1,a2));
		char[] a3 = s3.toCharArray();
		assertEquals(26, d.distance(a3,a2));
	}
}
