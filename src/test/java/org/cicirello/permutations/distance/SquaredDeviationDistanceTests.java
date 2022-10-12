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
 * JUnit tests for SquaredDeviationDistance.
 */
public class SquaredDeviationDistanceTests extends SharedTestForPermutationDistance {
	
	@Test
	public void testMax() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
		for (int n = 0; n <= 7; n++) {
			int expected = bruteForceComputeMax(d,n);
			assertEquals(expected, d.max(n), "Failed on length: " + n);
			assertEquals(1.0*expected, d.maxf(n), "Failed on length: " + n);
		}
	}
	
	@Test
	public void testIdenticalPermutations() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
		identicalPermutations(d);
	}
	
	@Test
	public void testExceptions() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> d.distance(new Permutation(1), new Permutation(2))
		);
	}
	
	@Test
	public void testSquaredDeviationDistance() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
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
	}
}
