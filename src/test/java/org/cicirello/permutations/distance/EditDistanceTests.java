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
 * JUnit tests for EditDistance.
 */
public class EditDistanceTests extends SharedTestForPermutationDistanceDouble {
	
	@Test
	public void testIdenticalPermutations() {
		EditDistance d = new EditDistance();
		identicalPermutationsDouble(d);
	}
	
	@Test
	public void testDefaultWeights() {
		EditDistance d = new EditDistance();
		// Default edit operation weights is equivalent to ReinsertionDistance (just computed differently).
		// So assuming reinsertionDistance passes its tests, we can use it to computed expected values here.
		ReinsertionDistance reinsert = new ReinsertionDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(reinsert.distancef(p1,p2), d.distancef(p1,p2));
			}
		}
	}
	
	@Test
	public void testWeightedLikeEM() {
		// following parameter values result in equivalent of exact match distance (just computed differently).
		EditDistance d = new EditDistance(999.0, 999.0, 1.0);
		ExactMatchDistance em = new ExactMatchDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(em.distancef(p1,p2), d.distancef(p1,p2));
			}
		}
	}
	
	@Test
	public void testWeightedLike2dot5EM() {
		// following parameter values result in equivalent of 2.5 * exact match distance.
		EditDistance d = new EditDistance(999.0, 999.0, 2.5);
		ExactMatchDistance em = new ExactMatchDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(2.5*em.distancef(p1,p2), d.distancef(p1,p2));
			}
		}
	}
	
	@Test
	public void testWeightedLike2dot25Reinsertion() {
		// following parameter values result in equivalent of 2.25 * reinsertion distance.
		EditDistance d = new EditDistance(1.5, 0.75, 999.0);
		ReinsertionDistance reinsert = new ReinsertionDistance();
		for (int n = 2; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p1 = new Permutation(n);
				Permutation p2 = new Permutation(n);
				assertEquals(2.25*reinsert.distancef(p1,p2), d.distancef(p1,p2));
			}
		}
	}
	
	@Test
	public void testDifferentLengthCase() {
		// Different length case
		EditDistance d = new EditDistance(1.0, 9.0, 9.0);
		int[] first = { 0, 1, 2 };
		int[] second = { 0, 1, 2, 3 };
		assertEquals(1.0, d.distancef(new Permutation(first), new Permutation(second)));
	}
}
