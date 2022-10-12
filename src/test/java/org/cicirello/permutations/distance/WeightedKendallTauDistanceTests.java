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
 * JUnit tests for WeightedKendallTauDistance.
 */
public class WeightedKendallTauDistanceTests {
	
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
	}
	
	@Test
	public void testWeightsAllOnes() {
		WeightedKendallTauDistance d = new WeightedKendallTauDistance(new double[] {1, 1, 1, 1, 1, 1});
		Permutation p = new Permutation(6);
		for (Permutation q : p) {
			assertEquals(naiveKendalTau(p,q), d.distancef(p,q), 1E-10);
		}
	}
	
	@Test
	public void testExceptions() {
		WeightedKendallTauDistance d = new WeightedKendallTauDistance(new double[] {1, 1, 1, 1, 1, 1});
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
}
	