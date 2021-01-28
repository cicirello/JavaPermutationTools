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
package org.cicirello.permutations.distance;

import org.junit.*;
import static org.junit.Assert.*;
import org.cicirello.permutations.*;

/**
 * JUnit 4 tests for the various classes that implement permutation distance metrics.
 */
public class PermutationDistanceTests {
	
	private final static double EPSILON = 1e-10;
	
	@Test
	public void testBlockInterchangeDistance() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		identicalPermutations(d);
		for (int i = 0; i < 10; i++) {
			// maximal case is reversed permutation
			Permutation p = new Permutation(i);
			Permutation rev = new Permutation(p);
			rev.reverse();
			assertEquals("maximal distance", i/2, d.distance(p, rev));
			assertEquals("maximal distance", i/2, d.distance(rev, p));
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
		assertEquals("1 block interchange length 1", 1, d.distance(p, p1));
		assertEquals("1 block interchange length 2", 1, d.distance(p, p2));
		assertEquals("1 block interchange length 3", 1, d.distance(p, p3));
		assertEquals("1 block interchange length 4", 1, d.distance(p, p4));
		assertEquals("1 block interchange length 5", 1, d.distance(p, p5));
		assertEquals("1 block interchange length 1", 1, d.distance(p1, p));
		assertEquals("1 block interchange length 2", 1, d.distance(p2, p));
		assertEquals("1 block interchange length 3", 1, d.distance(p3, p));
		assertEquals("1 block interchange length 4", 1, d.distance(p4, p));
		assertEquals("1 block interchange length 5", 1, d.distance(p5, p));
		assertEquals("1 block interchange lengths 1,2", 1, d.distance(p, p12));
		assertEquals("1 block interchange lengths 1,3", 1, d.distance(p, p13));
		assertEquals("1 block interchange lengths 1,2", 1, d.distance(p12, p));
		assertEquals("1 block interchange lengths 1,3", 1, d.distance(p13, p));
		assertEquals("1 block interchange lengths 2,3", 1, d.distance(p, p23));
		assertEquals("1 block interchange lengths 2,4", 1, d.distance(p, p24));
		assertEquals("1 block interchange lengths 2,3", 1, d.distance(p23, p));
		assertEquals("1 block interchange lengths 2,4", 1, d.distance(p24, p));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testAcyclicEdgeDistance() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		identicalPermutations(d);
		reversalInvariance(d);
		int[] a1 = { 0, 1, 2, 3, 4, 5};
		int[] a2 = { 0, 2, 4, 1, 5, 3};
		int[] a3 = { 0, 1, 2, 4, 5, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		assertEquals("maximal distance", 5, d.distance(p1, p2));
		p2.reverse();
		assertEquals("reversal invariant maximal distance", 5, d.distance(p1, p2));
		assertEquals("intermediate distance", 2, d.distance(p1, p3));
		p3.reverse();
		assertEquals("reversal invariant intermediate distance", 2, d.distance(p1, p3));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testCyclicEdgeDistance() {
		CyclicEdgeDistance d = new CyclicEdgeDistance();
		identicalPermutations(d);
		reversalInvariance(d);
		rotationalInvariance(d);
		int[] a1 = { 0, 1, 2, 3, 4, 5};
		int[] a2 = { 0, 2, 4, 1, 5, 3};
		int[] a3 = { 0, 1, 2, 4, 5, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		assertEquals("maximal distance", 6, d.distance(p1, p2));
		p2.reverse();
		assertEquals("reversal invariant maximal distance", 6, d.distance(p1, p2));
		p2.reverse();
		for (int i = 1; i < a1.length; i++) {
			p2.rotate(1);
			assertEquals("rotational invariant maximal distance", 6, d.distance(p1, p2));
		}
		assertEquals("intermediate distance", 3, d.distance(p1, p3));
		p3.reverse();
		assertEquals("reversal invariant intermediate distance", 3, d.distance(p1, p3));
		p3.reverse();
		for (int i = 1; i < a1.length; i++) {
			p3.rotate(1);
			assertEquals("rotational invariant intermediate distance", 3, d.distance(p1, p3));
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testCyclicRTypeDistance() {
		CyclicRTypeDistance d = new CyclicRTypeDistance();
		identicalPermutations(d);
		rotationalInvariance(d);
		int[] a1 = { 0, 1, 2, 3, 4, 5};
		int[] a2 = { 0, 2, 4, 1, 5, 3};
		int[] a3 = { 0, 1, 2, 4, 5, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		assertEquals("maximal distance", 6, d.distance(p1, p2));
		for (int i = 1; i < a1.length; i++) {
			p2.rotate(1);
			assertEquals("rotational invariant maximal distance", 6, d.distance(p1, p2));
		}
		assertEquals("intermediate distance", 3, d.distance(p1, p3));
		for (int i = 1; i < a1.length; i++) {
			p3.rotate(1);
			assertEquals("rotational invariant intermediate distance", 3, d.distance(p1, p3));
		}
		Permutation r = new Permutation(p1);
		r.reverse();
		assertEquals("reverse is maximal distance", 6, d.distance(p1, r));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testRTypeDistance() {
		RTypeDistance d = new RTypeDistance();
		identicalPermutations(d);
		int[] a1 = { 0, 1, 2, 3, 4, 5};
		int[] a2 = { 0, 2, 4, 1, 5, 3};
		int[] a3 = { 0, 1, 2, 4, 5, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		assertEquals("maximal distance", 5, d.distance(p1, p2));
		assertEquals("intermediate distance", 2, d.distance(p1, p3));
		Permutation r = new Permutation(p1);
		r.reverse();
		assertEquals("reverse is maximal distance", 5, d.distance(p1, r));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testDeviationDistance() {
		DeviationDistance d = new DeviationDistance();
		identicalPermutations(d);
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = 2*i*(n-i);
				assertEquals("deviation distance", expected, d.distance(p, copy));
			}
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testDeviationDistanceNormalized() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		identicalPermutationsDouble(d);
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = 2*i*(n-i);
				double expectedD = expected / (n-1.0);
				assertEquals("deviation distance", expectedD, d.distancef(p, copy), EPSILON);
			}
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testDeviationDistanceNormalized2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
		identicalPermutationsDouble(d);
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			Permutation reversed = new Permutation(p);
			reversed.reverse();
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = 2*i*(n-i);
				int norm = n*n;
				if (n%2==1) norm--;
				norm /= 2;
				double expectedD = expected / (norm*1.0);
				assertEquals("deviation distance", expectedD, d.distancef(p, copy), EPSILON);
			}
			// Reverse of permutation should be distance 1.0 from original.
			assertEquals("deviation distance", 1, d.distancef(p, reversed), EPSILON);
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testSquaredDeviationDistance() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
		identicalPermutations(d);
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = i*i*(n-i) + i*(n-i)*(n-i);
				assertEquals("squared deviation distance", expected, d.distance(p, copy));
			}
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testLeeDistance() {
		LeeDistance d = new LeeDistance();
		identicalPermutations(d);
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = Math.min(i,n-i)*n;
				assertEquals("Lee distance", expected, d.distance(p, copy));
			}
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testExactMatchDistance() {
		ExactMatchDistance d = new ExactMatchDistance();
		identicalPermutations(d);
		int[] a1 = {0, 1, 2, 3, 4, 5};
		int[] a2 = {5, 0, 1, 2, 3, 4};
		int[] a3 = {5, 1, 2, 3, 4, 0};
		int[] a4 = {0, 3, 2, 1, 4, 5};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		Permutation p4 = new Permutation(a4);
		assertEquals("maximal distance", 6, d.distance(p1,p2));
		assertEquals("end points differ", 2, d.distance(p1,p3));
		assertEquals("differ in interior positions", 2, d.distance(p1,p4));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testInterchangeDistance() {
		InterchangeDistance d = new InterchangeDistance();
		identicalPermutations(d);
		int[] a1 = {0, 1, 2, 3, 4, 5};
		int[] a2 = {2, 4, 1, 0, 5, 3};
		int[] a3 = {5, 1, 2, 3, 4, 0};
		int[] a4 = {0, 3, 2, 1, 4, 5};
		int[] a5 = {2, 5, 4, 1, 0, 3};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		Permutation p4 = new Permutation(a4);
		Permutation p5 = new Permutation(a5);

		assertEquals("maximal distance, one permutation cycle", 5, d.distance(p1,p2));
		assertEquals("end points switched", 1, d.distance(p1,p3));
		assertEquals("one swap different", 1, d.distance(p1,p4));
		assertEquals("two permutation cycles", 4, d.distance(p1,p5));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testKendallTauDistance() {
		KendallTauDistance d = new KendallTauDistance();
		identicalPermutations(d);
		for (int n = 2; n <= 10; n++) {
			//maximal distance is permutation reversed
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			copy.reverse();
			int expected = n*(n-1)/2;
			assertEquals("maximal distance", expected, d.distance(p,copy));
			copy.reverse();
			copy.swap(0,n-1);
			expected = 2*n-3;
			assertEquals("end points swapped", expected, d.distance(p,copy));
		}
		Permutation p = new Permutation(6);
		for (Permutation q : p) {
			assertEquals("checking consistence with naive implementation", naiveKendalTau(p,q), d.distance(p,q));
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	private int naiveKendalTau(Permutation p1, Permutation p2) {
		int count = 0;
		int L1 = p1.length();
	  
		int[] invP1 = p1.getInverse();
		int[] invP2 = p2.getInverse();
		
		for (int i = 0; i < L1-1; i++) {
			for (int j = i+1; j < L1; j++) {
				if ((invP1[i]-invP1[j])*(invP2[i]-invP2[j]) < 0) count++; 
			}
		}
		
		return count;
	}
	
	@Test
	public void testReinsertionDistance() {
		ReinsertionDistance d = new ReinsertionDistance();
		identicalPermutations(d);
		for (int n = 2; n <= 10; n++) {
			//maximal distance is permutation reversed
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			copy.reverse();
			int expected = n-1;
			assertEquals("maximal distance", expected, d.distance(p,copy));
		}
		int[] a1 = {0, 1, 2, 3, 4, 5};
		int[] a2 = {0, 4, 2, 3, 1, 5};
		int[] a3 = {1, 5, 4, 2, 3, 0};
		int[] a4 = {5, 3, 1, 2, 0, 4};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		Permutation p4 = new Permutation(a4);
		assertEquals("all but 2 on longest common subsequence", 2, d.distance(p1,p2));
		assertEquals("all but 3 on longest common subsequence", 3, d.distance(p1,p3));
		assertEquals("all but 3 on longest common subsequence", 3, d.distance(p1,p4));
		
		Permutation p = new Permutation(6);
		EditDistance edit = new EditDistance();
		for (Permutation q : p) {
			// NOTE: If this assertion fails, problem is either in ReinsertionDistance or EditDistance
			// Should correspond if they are both correct.
			assertEquals("equiv of edit with 0.5 cost removes and inserts", edit.distancef(p,q), d.distancef(p,q), EPSILON);
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testEditDistance() {
		EditDistance d = new EditDistance();
		identicalPermutationsDouble(d);
		// Default edit operation weights is equivalent to ReinsertionDistance (just computed differently).
		// So assuming reinsertionDistance passes its tests, we can use it to computed expected values here.
		ReinsertionDistance reinsert = new ReinsertionDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals("equiv of reinsertion", reinsert.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of exact match distance (just computed differently).
		d = new EditDistance(999.0, 999.0, 1.0);
		ExactMatchDistance em = new ExactMatchDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals("equiv of exact match", em.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of 2.5 * exact match distance.
		d = new EditDistance(999.0, 999.0, 2.5);
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals("equiv of 2.5 exact match", 2.5*em.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of 2.25 * reinsertion distance.
		d = new EditDistance(1.5, 0.75, 999.0);
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals("equiv of 2.25 reinsertion", 2.25*reinsert.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		
		// Different length case
		d = new EditDistance(1.0, 9.0, 9.0);
		int[] first = { 0, 1, 2 };
		int[] second = { 0, 1, 2, 3 };
		assertEquals(1.0, d.distancef(new Permutation(first), new Permutation(second)), EPSILON);
	}
	
	
	private void identicalPermutations(PermutationDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			assertEquals("distance of a permutation to itself should be 0; length was " + n, 0, d.distance(p, copy));
			assertEquals("distance of a permutation to itself should be 0; length was " + n, 0, d.distance(p, p));
		}
	}
	
	private void identicalPermutationsDouble(PermutationDistanceMeasurerDouble d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			assertEquals("distance of a permutation to itself should be 0; length was " + n, 0.0, d.distancef(p, copy), EPSILON);
			assertEquals("distance of a permutation to itself should be 0; length was " + n, 0.0, d.distancef(p, p), EPSILON);
		}
	}
	
	private void reversalInvariance(PermutationDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			copy.reverse();
			assertEquals("reversal invariant distance metric", 0, d.distance(p, copy));
		}
	}
	
	private void rotationalInvariance(PermutationDistanceMeasurer d) {
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				copy.rotate(1);
				assertEquals("rotational invariant distance metric", 0, d.distance(p, copy));
			}
		}
	}
}