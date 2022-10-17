/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 */
package org.cicirello.permutations;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for rotating a Permutation.
 */
public class PermutationRotateTests {
	
	@Test
	public void testPermutationRotate() {
		Permutation p = new Permutation(10);
		for (int r = 0; r < p.length(); r++) {
			Permutation copy = new Permutation(p);
			copy.rotate(r);
			for (int i = 0; i < p.length(); i++) {
				int j = (i + r) % p.length();
				assertEquals(p.get(j), copy.get(i), "elements should be left rotated " + r + " places");
			}
		}
		Permutation copy = new Permutation(p);
		copy.rotate(p.length());
		assertEquals(p, copy);
		copy = new Permutation(p);
		copy.rotate(-1);
		for (int i = 1; i < p.length(); i++) {
			assertEquals(p.get(i-1), copy.get(i), "elements should be RIGHT rotated 1 place");
		}
		assertEquals(p.get(p.length()-1), copy.get(0), "elements should be RIGHT rotated 1 place");
	}
}
