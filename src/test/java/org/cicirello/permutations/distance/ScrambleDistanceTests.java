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
 * JUnit tests for ScrambleDistance.
 */
public class ScrambleDistanceTests extends SharedTestForPermutationDistance {
	
	@Test
	public void testIdenticalPermutations() {
		ScrambleDistance d = new ScrambleDistance();
		identicalPermutations(d);
	}
	
	@Test
	public void testScrambleDistance() {
		ScrambleDistance d = new ScrambleDistance();
		for (int i = 2; i <= 10; i++) {
			Permutation p1 = new Permutation(i);
			Permutation p2 = new Permutation(p1);
			p2.scramble(true);
			assertEquals(1, d.distance(p1,p2));
		}
	}
}
