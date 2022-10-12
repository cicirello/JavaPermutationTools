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
 * JUnit tests for AcyclicEdgeDistance.
 */
public class AcyclicEdgeDistanceTests extends SharedTestForPermutationDistance {
	
	@Test
	public void testMax() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		for (int n = 0; n <= 7; n++) {
			int expected = bruteForceComputeMax(d,n);
			assertEquals(expected, d.max(n), "Failed on length: " + n);
			assertEquals(1.0*expected, d.maxf(n), "Failed on length: " + n);
		}
	}
	
	@Test
	public void testIdenticalPermutations() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		identicalPermutations(d);
	}
	
	@Test
	public void testReversalIndependence() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		reversalInvariance(d);
	}
	
	@Test
	public void testAcyclicEdgeDistance() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		int[] a1 = { 0, 1, 2, 3, 4, 5};
		int[] a2 = { 0, 2, 4, 1, 5, 3};
		int[] a3 = { 0, 1, 2, 4, 5, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		assertEquals(5, d.distance(p1, p2));
		p2.reverse();
		assertEquals(5, d.distance(p1, p2));
		assertEquals(2, d.distance(p1, p3));
		p3.reverse();
		assertEquals(2, d.distance(p1, p3));
	}
	
	@Test
	public void testExceptions() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
}
