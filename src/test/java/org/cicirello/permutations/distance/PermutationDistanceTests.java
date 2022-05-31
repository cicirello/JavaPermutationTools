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
import org.cicirello.permutations.*;

/**
 * JUnit tests for the various classes that implement permutation distance metrics.
 */
public class PermutationDistanceTests {
	
	private final static double EPSILON = 1e-10;
	
	@Test
	public void testCyclicReversalIndependentDistance() {
		ExactMatchDistance em = new ExactMatchDistance();
		CyclicReversalIndependentDistance d = new CyclicReversalIndependentDistance(em);
		int[] original =  { 0, 1, 2, 3, 4 };
		int[] different = { 3, 4, 0, 2, 1 };
		
		Permutation p = new Permutation(original);
		Permutation[] rotated = new Permutation[original.length];
		Permutation[] reversed = new Permutation[original.length];
		rotated[0] = new Permutation(original);
		for (int i = 1; i < rotated.length; i++) {
			rotated[i] = new Permutation(original);
			rotated[i].rotate(i);
		}
		for (int i = 0; i < reversed.length; i++) {
			reversed[i] = new Permutation(rotated[i]);
			reversed[i].reverse();
		}
		for (int i = 0; i < rotated.length; i++) {
			assertEquals(0, d.distance(p, rotated[i]));
			assertEquals(0, d.distance(p, reversed[i]));
			assertEquals(0, d.distancef(p, rotated[i]), EPSILON);
			assertEquals(0, d.distancef(p, reversed[i]), EPSILON);
		}
		rotated[0] = new Permutation(different);
		for (int i = 1; i < rotated.length; i++) {
			rotated[i] = new Permutation(different);
			rotated[i].rotate(i);
		}
		for (int i = 0; i < reversed.length; i++) {
			reversed[i] = new Permutation(rotated[i]);
			reversed[i].reverse();
		}
		for (int i = 0; i < rotated.length; i++) {
			assertEquals(2, d.distance(p, rotated[i]));
			assertEquals(2, d.distance(p, reversed[i]));
			assertEquals(2, d.distancef(p, rotated[i]), EPSILON);
			assertEquals(2, d.distancef(p, reversed[i]), EPSILON);
		}
	}
	
	@Test
	public void testCyclicReversalIndependentDistanceDouble() {
		ExactMatchDistance em = new ExactMatchDistance();
		CyclicReversalIndependentDistanceDouble d = new CyclicReversalIndependentDistanceDouble(em);
		int[] original =  { 0, 1, 2, 3, 4 };
		int[] different = { 3, 4, 0, 2, 1 };
		
		Permutation p = new Permutation(original);
		Permutation[] rotated = new Permutation[original.length];
		Permutation[] reversed = new Permutation[original.length];
		rotated[0] = new Permutation(original);
		for (int i = 1; i < rotated.length; i++) {
			rotated[i] = new Permutation(original);
			rotated[i].rotate(i);
		}
		for (int i = 0; i < reversed.length; i++) {
			reversed[i] = new Permutation(rotated[i]);
			reversed[i].reverse();
		}
		for (int i = 0; i < rotated.length; i++) {
			assertEquals(0, d.distancef(p, rotated[i]), EPSILON);
			assertEquals(0, d.distancef(p, reversed[i]), EPSILON);
		}
		rotated[0] = new Permutation(different);
		for (int i = 1; i < rotated.length; i++) {
			rotated[i] = new Permutation(different);
			rotated[i].rotate(i);
		}
		for (int i = 0; i < reversed.length; i++) {
			reversed[i] = new Permutation(rotated[i]);
			reversed[i].reverse();
		}
		for (int i = 0; i < rotated.length; i++) {
			assertEquals(2, d.distancef(p, rotated[i]), EPSILON);
			assertEquals(2, d.distancef(p, reversed[i]), EPSILON);
		}
	}
	
	@Test
	public void testCyclicIndependentDistance() {
		ExactMatchDistance em = new ExactMatchDistance();
		CyclicIndependentDistance d = new CyclicIndependentDistance(em);
		int[] original = { 0, 1, 2, 3 };
		int[] different = {0, 2, 1, 3 };
		Permutation p1 = new Permutation(original);
		Permutation p2 = new Permutation(original);
		Permutation pd = new Permutation(different);
		Permutation pr1 = new Permutation(p1);
		pr1.rotate(1);
		Permutation pr2 = new Permutation(p1);
		pr1.rotate(2);
		Permutation pr3 = new Permutation(p1);
		pr1.rotate(3);
		assertEquals(0, d.distance(p1, p2));
		assertEquals(0, d.distance(p1, pr1));
		assertEquals(0, d.distance(p1, pr2));
		assertEquals(0, d.distance(p1, pr3));
		assertEquals(2, d.distance(pd, p2));
		assertEquals(2, d.distance(pd, pr1));
		assertEquals(2, d.distance(pd, pr2));
		assertEquals(2, d.distance(pd, pr3));

		assertEquals(0, d.distancef(p1, p2), EPSILON);
		assertEquals(0, d.distancef(p1, pr1), EPSILON);
		assertEquals(0, d.distancef(p1, pr2), EPSILON);
		assertEquals(0, d.distancef(p1, pr3), EPSILON);
		assertEquals(2, d.distancef(pd, p2), EPSILON);
		assertEquals(2, d.distancef(pd, pr1), EPSILON);
		assertEquals(2, d.distancef(pd, pr2), EPSILON);
		assertEquals(2, d.distancef(pd, pr3), EPSILON);
	}
	
	@Test
	public void testCyclicIndependentDistanceDouble() {
		ExactMatchDistance em = new ExactMatchDistance();
		CyclicIndependentDistanceDouble d = new CyclicIndependentDistanceDouble(em);
		int[] original = { 0, 1, 2, 3 };
		int[] different = {0, 2, 1, 3 };
		Permutation p1 = new Permutation(original);
		Permutation p2 = new Permutation(original);
		Permutation pd = new Permutation(different);
		Permutation pr1 = new Permutation(p1);
		pr1.rotate(1);
		Permutation pr2 = new Permutation(p1);
		pr1.rotate(2);
		Permutation pr3 = new Permutation(p1);
		pr1.rotate(3);
		assertEquals(0, d.distancef(p1, p2), EPSILON);
		assertEquals(0, d.distancef(p1, pr1), EPSILON);
		assertEquals(0, d.distancef(p1, pr2), EPSILON);
		assertEquals(0, d.distancef(p1, pr3), EPSILON);
		assertEquals(2, d.distancef(pd, p2), EPSILON);
		assertEquals(2, d.distancef(pd, pr1), EPSILON);
		assertEquals(2, d.distancef(pd, pr2), EPSILON);
		assertEquals(2, d.distancef(pd, pr3), EPSILON);
	}
	
	@Test
	public void testReversalIndependentDistance() {
		ExactMatchDistance em = new ExactMatchDistance();
		ReversalIndependentDistance d = new ReversalIndependentDistance(em);
		int[] original = { 0, 1, 2, 3, 4, 5, 6 };
		int[] other    = { 0, 5, 4, 3, 2, 1, 6 };
		int[] other2   = { 6, 1, 2, 3, 4, 5, 0 };
		int[] reversed = { 6, 5, 4, 3, 2, 1, 0 };
		Permutation p1 = new Permutation(original);
		Permutation p2 = new Permutation(original);
		Permutation pr = new Permutation(reversed);
		Permutation p4to2 = new Permutation(other);
		Permutation p2to4 = new Permutation(other2);
		assertEquals(0, d.distance(p1, p2));
		assertEquals(0, d.distance(p1, pr));
		assertEquals(0, d.distance(pr, p2));
		assertEquals(2, d.distance(p1, p4to2));
		assertEquals(2, d.distance(p4to2, p1));
		assertEquals(2, d.distance(p1, p2to4));
		assertEquals(2, d.distance(p2to4, p1));
		assertEquals(0, d.distancef(p1, p2), EPSILON);
		assertEquals(0, d.distancef(p1, pr), EPSILON);
		assertEquals(0, d.distancef(pr, p2), EPSILON);
		assertEquals(2, d.distancef(p1, p4to2), EPSILON);
		assertEquals(2, d.distancef(p4to2, p1), EPSILON);
		assertEquals(2, d.distancef(p1, p2to4), EPSILON);
		assertEquals(2, d.distancef(p2to4, p1), EPSILON);	
	}
	
	@Test
	public void testReversalIndependentDistanceDouble() {
		ExactMatchDistance em = new ExactMatchDistance();
		ReversalIndependentDistanceDouble d = new ReversalIndependentDistanceDouble(em);
		int[] original = { 0, 1, 2, 3, 4, 5, 6 };
		int[] other    = { 0, 5, 4, 3, 2, 1, 6 };
		int[] other2   = { 6, 1, 2, 3, 4, 5, 0 };
		int[] reversed = { 6, 5, 4, 3, 2, 1, 0 };
		Permutation p1 = new Permutation(original);
		Permutation p2 = new Permutation(original);
		Permutation pr = new Permutation(reversed);
		Permutation p4to2 = new Permutation(other);
		Permutation p2to4 = new Permutation(other2);
		assertEquals(0, d.distancef(p1, p2), EPSILON);
		assertEquals(0, d.distancef(p1, pr), EPSILON);
		assertEquals(0, d.distancef(pr, p2), EPSILON);
		assertEquals(2, d.distancef(p1, p4to2), EPSILON);
		assertEquals(2, d.distancef(p4to2, p1), EPSILON);
		assertEquals(2, d.distancef(p1, p2to4), EPSILON);
		assertEquals(2, d.distancef(p2to4, p1), EPSILON);	
	}
	
	@Test
	public void testReversalDistance() {
		ReversalDistance d4 = new ReversalDistance(4);
		int[] a4 = {0, 1, 2, 3};
		int[] a4_2 = {1, 0, 3, 2};
		Permutation p4 = new Permutation(a4);
		Permutation p4_2 = new Permutation(a4_2);
		assertEquals(2, d4.distance(p4,p4_2));
		ReversalDistance d5 = new ReversalDistance(5);
		int[] a5 = {0, 1, 4, 2, 3};
		int[] a5_2 = {1, 0, 4, 3, 2};
		Permutation p5 = new Permutation(a5);
		Permutation p5_2 = new Permutation(a5_2);
		assertEquals(2, d5.distance(p5,p5_2));
		
		ReversalDistance d = new ReversalDistance();
		assertEquals(2, d.distance(p5,p5_2));
		assertEquals(4, d.max(5));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d5.distance(new Permutation(5), new Permutation(6))
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d5.distance(new Permutation(6), new Permutation(6))
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new ReversalDistance(-1)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new ReversalDistance(13)
		);
	}
	
	@Test
	public void testScrambleDistance() {
		ScrambleDistance d = new ScrambleDistance();
		identicalPermutations(d);
		for (int i = 2; i <= 10; i++) {
			Permutation p1 = new Permutation(i);
			Permutation p2 = new Permutation(p1);
			p2.scramble(true);
			assertEquals(1, d.distance(p1,p2));
		}
	}
	
	@Test
	public void testBlockInterchangeDistance() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		identicalPermutations(d);
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
		assertEquals(5, d.distance(p1, p2));
		p2.reverse();
		assertEquals(5, d.distance(p1, p2));
		assertEquals(2, d.distance(p1, p3));
		p3.reverse();
		assertEquals(2, d.distance(p1, p3));
		
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
		assertEquals(6, d.distance(p1, p2));
		p2.reverse();
		assertEquals(6, d.distance(p1, p2));
		p2.reverse();
		for (int i = 1; i < a1.length; i++) {
			p2.rotate(1);
			assertEquals(6, d.distance(p1, p2));
		}
		assertEquals(3, d.distance(p1, p3));
		p3.reverse();
		assertEquals(3, d.distance(p1, p3));
		p3.reverse();
		for (int i = 1; i < a1.length; i++) {
			p3.rotate(1);
			assertEquals(3, d.distance(p1, p3));
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
		assertEquals(6, d.distance(p1, p2));
		for (int i = 1; i < a1.length; i++) {
			p2.rotate(1);
			assertEquals(6, d.distance(p1, p2));
		}
		assertEquals(3, d.distance(p1, p3));
		for (int i = 1; i < a1.length; i++) {
			p3.rotate(1);
			assertEquals(3, d.distance(p1, p3));
		}
		Permutation r = new Permutation(p1);
		r.reverse();
		assertEquals(6, d.distance(p1, r));
		
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
		assertEquals(5, d.distance(p1, p2));
		assertEquals(2, d.distance(p1, p3));
		Permutation r = new Permutation(p1);
		r.reverse();
		assertEquals(5, d.distance(p1, r));
		
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
				assertEquals(expected, d.distance(p, copy));
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
				assertEquals(expectedD, d.distancef(p, copy), EPSILON);
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
				assertEquals(expectedD, d.distancef(p, copy), EPSILON);
			}
			// Reverse of permutation should be distance 1.0 from original.
			assertEquals(1, d.distancef(p, reversed), EPSILON);
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
				assertEquals(expected, d.distance(p, copy));
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
				assertEquals(expected, d.distance(p, copy));
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
		assertEquals(6, d.distance(p1,p2));
		assertEquals(2, d.distance(p1,p3));
		assertEquals(2, d.distance(p1,p4));
		
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

		assertEquals(5, d.distance(p1,p2));
		assertEquals(1, d.distance(p1,p3));
		assertEquals(1, d.distance(p1,p4));
		assertEquals(4, d.distance(p1,p5));
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testWeightedKendallTauDistance_WeightsAllOneCase() {
		for (int n = 2; n <= 10; n++) {
			double[] weights = new double[n];
			for (int i = 0; i < n; i++) {
				weights[i] = 1;
			}
			WeightedKendallTauDistance d = new WeightedKendallTauDistance(weights);
			assertEquals(n, d.supportedLength());
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			assertEquals(0.0, d.distancef(p, copy), 1E-10);
			//maximal distance is permutation reversed
			copy.reverse();
			double expected = n*(n-1)/2;
			assertEquals(expected, d.distancef(p,copy));
			copy.reverse();
			copy.swap(0,n-1);
			expected = 2*n-3;
			assertEquals(expected, d.distancef(p,copy), 1E-10);
		}
		final WeightedKendallTauDistance d = new WeightedKendallTauDistance(new double[] {1, 1, 1, 1, 1, 1});
		Permutation p = new Permutation(6);
		for (Permutation q : p) {
			assertEquals(naiveKendalTau(p,q), d.distancef(p,q), 1E-10);
		}
		
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(5), new Permutation(6))
		);
		assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(6), new Permutation(5))
		);
	}
	
	@Test
	public void testWeightedKendallTauDistance() {
		double[] weights = {8, 2, 10, 20, 5, 1};
		int[] p1 = { 5, 2, 0, 3, 1, 4};
		WeightedKendallTauDistance d = new WeightedKendallTauDistance(weights);
		assertEquals(0.0, d.distancef(new Permutation(p1), new Permutation(p1)), 1E-10);
		int[] p2 = { 4, 2, 0, 3, 1, 5 };
		double expected = 41*5 + 40;
		assertEquals(expected, d.distancef(new Permutation(p1), new Permutation(p2)), 1E-10);
		int[] p3 = { 5, 2, 0, 1, 3, 4};
		expected = 40;
		assertEquals(expected, d.distancef(new Permutation(p1), new Permutation(p3)), 1E-10);
	}
	
	@Test
	public void testWeightedKendallTauDistanceReversed() {
		double[] weights = {8, 2, 10, 20, 5, 1};
		WeightedKendallTauDistance d = new WeightedKendallTauDistance(weights);
		int[] perm = { 5, 2, 0, 3, 1, 4};
		Permutation p1 = new Permutation(perm);
		Permutation p2 = new Permutation(p1);
		p2.reverse();
		double expected = 45.0 + 40.0*5 + 20*20 + 10*10 + 8*2;
		assertEquals(expected, d.distancef(new Permutation(p1), new Permutation(p2)), 1E-10);
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
			assertEquals(expected, d.distance(p,copy));
			copy.reverse();
			copy.swap(0,n-1);
			expected = 2*n-3;
			assertEquals(expected, d.distance(p,copy));
		}
		Permutation p = new Permutation(6);
		for (Permutation q : p) {
			assertEquals(naiveKendalTau(p,q), d.distance(p,q));
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
			assertEquals(expected, d.distance(p,copy));
			assertEquals(expected, d.distance(copy,p));
		}
		int[] a1 = {0, 1, 2, 3, 4, 5};
		int[] a2 = {0, 4, 2, 3, 1, 5};
		int[] a3 = {1, 5, 4, 2, 3, 0};
		int[] a4 = {5, 3, 1, 2, 0, 4};
		Permutation p1 = new Permutation(a1);
		Permutation p2 = new Permutation(a2);
		Permutation p3 = new Permutation(a3);
		Permutation p4 = new Permutation(a4);
		assertEquals(2, d.distance(p1,p2));
		assertEquals(3, d.distance(p1,p3));
		assertEquals(3, d.distance(p1,p4));
		
		Permutation p = new Permutation(5);
		EditDistance edit = new EditDistance();
		for (Permutation q : p) {
			// NOTE: If this assertion fails, problem is either in ReinsertionDistance or EditDistance
			// Should correspond if they are both correct.
			assertEquals(edit.distancef(p,q), d.distancef(p,q), EPSILON);
			assertEquals(edit.distancef(q,p), d.distancef(q,p), EPSILON);
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
				assertEquals(reinsert.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of exact match distance (just computed differently).
		d = new EditDistance(999.0, 999.0, 1.0);
		ExactMatchDistance em = new ExactMatchDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(em.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of 2.5 * exact match distance.
		d = new EditDistance(999.0, 999.0, 2.5);
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(2.5*em.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		// following parameter values result in equivalent of 2.25 * reinsertion distance.
		d = new EditDistance(1.5, 0.75, 999.0);
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(2.25*reinsert.distancef(p1,p2), d.distancef(p1,p2), EPSILON);
			}
		}
		
		// Different length case
		d = new EditDistance(1.0, 9.0, 9.0);
		int[] first = { 0, 1, 2 };
		int[] second = { 0, 1, 2, 3 };
		assertEquals(1.0, d.distancef(new Permutation(first), new Permutation(second)), EPSILON);
	}
	
	@Test
	public void testCycleDistance() {
		CycleDistance d = new CycleDistance();
		identicalPermutations(d);
		int[][] cases = {
			{1, 0},
			{1, 2, 0},
			{1, 2, 3, 0},
			{1, 0, 3, 2},
			{1, 2, 3, 4, 5, 6, 7, 0},
			{7, 1, 2, 3, 4, 5, 6, 0},
			{7, 1, 5, 3, 4, 2, 6, 0},
			{7, 6, 5, 4, 3, 2, 1, 0}
		};
		int[] expected = {1, 1, 1, 2, 1, 1, 2, 4};
		for (int i = 0; i < expected.length; i++) {
			Permutation p1 = new Permutation(cases[i].length, 0);
			Permutation p2 = new Permutation(cases[i]);
			assertEquals(expected[i], d.distance(p1, p2));
			assertEquals(expected[i], d.distance(p2, p1));
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	private void identicalPermutations(PermutationDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			assertEquals(0, d.distance(p, copy), "distance of a permutation to itself should be 0; length was " + n);
			assertEquals(0, d.distance(p, p), "distance of a permutation to itself should be 0; length was " + n);
		}
	}
	
	private void identicalPermutationsDouble(PermutationDistanceMeasurerDouble d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			assertEquals(0.0, d.distancef(p, copy), EPSILON, "distance of a permutation to itself should be 0; length was " + n);
			assertEquals(0.0, d.distancef(p, p), EPSILON, "distance of a permutation to itself should be 0; length was " + n);
		}
	}
	
	private void reversalInvariance(PermutationDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			copy.reverse();
			assertEquals(0, d.distance(p, copy));
		}
	}
	
	private void rotationalInvariance(PermutationDistanceMeasurer d) {
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				copy.rotate(1);
				assertEquals(0, d.distance(p, copy));
			}
		}
	}
}