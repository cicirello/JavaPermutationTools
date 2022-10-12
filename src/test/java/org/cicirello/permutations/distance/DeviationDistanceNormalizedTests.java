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
 * JUnit tests for DeviationDistanceNormalized and DeviationDistanceNormalized2005.
 */
public class DeviationDistanceNormalizedTests extends SharedTestForPermutationDistanceDouble {
	
	@Test
	public void testMax() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		for (int n = 0; n <= 7; n++) {
			assertEquals(bruteForceComputeMaxD(d,n), d.maxf(n), "Failed on length: " + n);
		}
	}
	
	@Test
	public void testIdenticalPermutations() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		identicalPermutationsDouble(d);
	}
	
	@Test
	public void testDeviationDistanceNormalized() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		for (int n = 2; n <= 10; n++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			for (int i = 1; i < n; i++) {
				// rotations are a special case that are easy to compute analytically (perfect for unit tests)
				copy.rotate(1);
				int expected = 2*i*(n-i);
				double expectedD = expected / (n-1.0);
				assertEquals(expectedD, d.distancef(p, copy));
			}
		}
	}
	
	@Test
	public void testExceptions() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testMax2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
		for (int n = 0; n <= 7; n++) {
			assertEquals(bruteForceComputeMaxD(d,n), d.maxf(n), "Failed on length: " + n);
		}
	}
	
	@Test
	public void testIdenticalPermutations2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
		identicalPermutationsDouble(d);
	}
	
	@Test
	public void testDeviationDistanceNormalized2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
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
				assertEquals(expectedD, d.distancef(p, copy));
			}
			// Reverse of permutation should be distance 1.0 from original.
			assertEquals(1, d.distancef(p, reversed));
		}
	}
	
	@Test
	public void testExceptions2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distancef(new Permutation(1), new Permutation(2))
		);
	}
}
