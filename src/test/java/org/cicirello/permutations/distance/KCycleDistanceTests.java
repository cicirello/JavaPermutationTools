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
 * JUnit tests for KCycleDistance.
 */
public class KCycleDistanceTests {
	
	@Test
	public void testAllOneCycle() {
		for (int k = 2; k <= 5; k++) {
			KCycleDistance d = new KCycleDistance(k);
			// All one cycle
			for (int n = 2; n <= 16; n *= 2) {
				int[] perm = new int[n];
				for (int i = 0; i < n; i++) {
					perm[i] = (i + 1) % n;
				}
				Permutation p1 = new Permutation(n, 0);
				Permutation p2 = new Permutation(perm);
				int expected = 1 + (n > k ? (int)Math.ceil((n-k)/(k-1.0)) : 0);
				assertEquals(expected, d.distance(p1, p2));
				assertEquals(expected, d.distance(p2, p1));
			}
		}
	}
	
	@Test
	public void testMaximalCycles() {
		for (int k = 2; k <= 5; k++) {
			KCycleDistance d = new KCycleDistance(k);
			// maximal cycles
			for (int n = 2; n <= 16; n *= 2) {
				int[] perm = new int[n];
				for (int i = 0; i < n; i++) {
					if (i%2==1) {
						perm[i] = perm[i-1];
						perm[i-1] = i;
					} else {
						perm[i] = i;
					}
				}
				Permutation p1 = new Permutation(n, 0);
				Permutation p2 = new Permutation(perm);
				int expected = n / 2;
				assertEquals(expected, d.distance(p1, p2));
				assertEquals(expected, d.distance(p2, p1));
			}
		}
	}
	
	@Test
	public void testMixOfCycleLengths() {
		int[] p = { 1, 0, 4, 2, 3, 6, 7, 8, 5, 10, 11, 12, 13, 9, 19, 14, 15, 16, 17, 18 };
		int[] expectedDist = {0, 0, 15, 9, 7, 6};
		for (int k = 2; k <= 5; k++) {
			KCycleDistance d = new KCycleDistance(k);
			// Mix of cycle lengths
			Permutation p1 = new Permutation(p.length, 0);
			Permutation p2 = new Permutation(p);
			assertEquals(expectedDist[k], d.distance(p1, p2));
			assertEquals(expectedDist[k], d.distance(p2, p1));
			IllegalArgumentException thrown = assertThrows( 
				IllegalArgumentException.class,
				() -> d.distance(new Permutation(1), new Permutation(2))
			);
		}
		
	}
	
	@Test
	public void testExceptions() {
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new KCycleDistance(1)
		);
	}
}
