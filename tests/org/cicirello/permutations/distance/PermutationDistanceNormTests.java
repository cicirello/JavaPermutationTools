/*
 * Copyright 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * JUnit 4 tests for the normalizedDistance method of various classes that implement permutation distance metrics.
 */
public class PermutationDistanceNormTests {
	
	private final static double EPSILON = 1e-10;
	
	@Test
	public void testBlockInterchangeDistance() {
		BlockInterchangeDistance d = new BlockInterchangeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testAcyclicEdgeDistance() {
		AcyclicEdgeDistance d = new AcyclicEdgeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=2 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testCyclicEdgeDistance() {
		CyclicEdgeDistance d = new CyclicEdgeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=3 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testCyclicRTypeDistance() {
		CyclicRTypeDistance d = new CyclicRTypeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=2 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testRTypeDistance() {
		RTypeDistance d = new RTypeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testDeviationDistance() {
		DeviationDistance d = new DeviationDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testDeviationDistanceNormalized() {
		DeviationDistanceNormalized d = new DeviationDistanceNormalized();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMaxD(d,n), EPSILON);
		}
	}
	
	@Test
	public void testDeviationDistanceNormalized2005() {
		DeviationDistanceNormalized2005 d = new DeviationDistanceNormalized2005();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMaxD(d,n), EPSILON);
		}
	}
	
	@Test
	public void testSquaredDeviationDistance() {
		SquaredDeviationDistance d = new SquaredDeviationDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testLeeDistance() {
		LeeDistance d = new LeeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testExactMatchDistance() {
		ExactMatchDistance d = new ExactMatchDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testInterchangeDistance() {
		InterchangeDistance d = new InterchangeDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	@Test
	public void testKendallTauDistance() {
		KendallTauDistance d = new KendallTauDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
		
	@Test
	public void testReinsertionDistance() {
		ReinsertionDistance d = new ReinsertionDistance();
		for (int n = 0; n <= 7; n++) {
			assertEquals("Failed on length: " + n, n<=1 ? 0.0 : 1.0, bruteForceComputeMax(d,n), EPSILON);
		}
	}
	
	//@Test // uncomment if we implement
	public void testEditDistance() {
		/* // Uncomment if we implement. 
		for (double i = 0.0; i < 1.0; i += 0.1) {
			for (double d = 0.0; d < 1.0; d += 0.1) {
				for (double c = i+d; c < 1.0; c += 0.1) {
					EditDistance dist = new EditDistance(i, d, c);
					for (int n = 0; n <= 7; n++) {
						assertEquals("Failed on length " + n + " and (i,d,c) = (" + i + ", " + d + ", " + c + ")", n<=1 ? 0.0 : 1.0, bruteForceComputeMaxD(d,n), EPSILON);
					}
				}
			}
		}
		for (double i = 0.0; i < 1.0; i += 0.1) {
			for (double d = 0.0; d < 1.0; d += 0.1) {
				for (double c =0.0; c < 1.0 && c < i+d; c += 0.1) {
					EditDistance dist = new EditDistance(i, d, c);
					for (int n = 0; n <= 7; n++) {
						assertEquals("Failed on length " + n + " and (i,d,c) = (" + i + ", " + d + ", " + c + ")", n<=1 ? 0.0 : 1.0, bruteForceComputeMaxD(d,n), EPSILON);
					}
				}
			}
		} */
	}
	
	private double bruteForceComputeMax(NormalizedPermutationDistanceMeasurer d, int n) {
		double max = 0;
		Permutation p1 = new Permutation(n, 0);
		for (Permutation p2 : p1) {
			max = Math.max(max, d.normalizedDistance(p1,p2));
		}
		return max;
	}
	
	private double bruteForceComputeMaxD(NormalizedPermutationDistanceMeasurerDouble d, int n) {
		double max = 0;
		Permutation p1 = new Permutation(n, 0);
		for (Permutation p2 : p1) {
			max = Math.max(max, d.normalizedDistance(p1,p2));
		}
		return max;
	}
	
}