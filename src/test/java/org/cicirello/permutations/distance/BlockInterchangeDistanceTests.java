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
 *
 */
package org.cicirello.permutations.distance;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.cicirello.permutations.Permutation;

/**
 * JUnit tests for BlockInterchangeDistance.
 */
public class BlockInterchangeDistanceTests extends SharedTestForPermutationDistance {
	
	@Test
	public void testMax() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		for (int n = 0; n <= 7; n++) {
			int expected = bruteForceComputeMax(d,n);
			assertEquals(expected, d.max(n), "Failed on length: " + n);
			assertEquals(1.0*expected, d.maxf(n), "Failed on length: " + n);
		}
	}
	
	@Test
	public void testIdenticalPermutations() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		identicalPermutations(d);
	}
	
	@Test
	public void testBlockInterchangeDistance() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		for (int i = 0; i < 10; i++) {
			// maximal case is reversed permutation
			Permutation p = new Permutation(i);
			Permutation rev = new Permutation(p);
			rev.reverse();
			assertEquals(i/2, d.distance(p, rev));
			assertEquals(i/2, d.distance(rev, p));
		}
		int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] a1 = {0, 5, 2, 3, 4, 1, 6, 7, 8, 9};
		int[] a12 = {0, 5, 3, 4, 1, 2, 6, 7, 8, 9};
		int[] a13 = {0, 5, 4, 1, 2, 3, 6, 7, 8, 9};
		int[] a2 = {0, 5, 6, 3, 4, 1, 2, 7, 8, 9};
		int[] a23 = {0, 5, 6, 4, 1, 2, 3, 7, 8, 9};
		int[] a24 = {0, 4, 5, 6, 7, 3, 1, 2, 8, 9};
		int[] a3 = {0, 5, 6, 7, 4, 1, 2, 3, 8, 9};
		int[] a4 = {4, 5, 6, 7, 0, 1, 2, 3, 8, 9};
		int[] a5 = {5, 6, 7, 8, 9, 0, 1, 2, 3, 4};
		Permutation p = new Permutation(a);
		Permutation p1 = new Permutation(a1);
		Permutation p12 = new Permutation(a12);
		Permutation p13 = new Permutation(a13);
		Permutation p2 = new Permutation(a2);
		Permutation p23 = new Permutation(a23);
		Permutation p24 = new Permutation(a24);
		Permutation p3 = new Permutation(a3);
		Permutation p4 = new Permutation(a4);
		Permutation p5 = new Permutation(a5);
		// these cases are all 1 block interchange of varying size blocks
		assertEquals(1, d.distance(p, p1));
		assertEquals(1, d.distance(p, p2));
		assertEquals(1, d.distance(p, p3));
		assertEquals(1, d.distance(p, p4));
		assertEquals(1, d.distance(p, p5));
		assertEquals(1, d.distance(p1, p));
		assertEquals(1, d.distance(p2, p));
		assertEquals(1, d.distance(p3, p));
		assertEquals(1, d.distance(p4, p));
		assertEquals(1, d.distance(p5, p));
		assertEquals(1, d.distance(p, p12));
		assertEquals(1, d.distance(p, p13));
		assertEquals(1, d.distance(p12, p));
		assertEquals(1, d.distance(p13, p));
		assertEquals(1, d.distance(p, p23));
		assertEquals(1, d.distance(p, p24));
		assertEquals(1, d.distance(p23, p));
		assertEquals(1, d.distance(p24, p));
	}
	
	@Test
	public void testExceptions() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
}
