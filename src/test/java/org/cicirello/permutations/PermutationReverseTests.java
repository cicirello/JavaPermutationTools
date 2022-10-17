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
 * JUnit tests for reversing a Permutation.
 */
public class PermutationReverseTests {
	
	@Test
	public void testReverse0() {
		Permutation p1 = new Permutation(0);
		// verify reversing 0 length Permutation doesn't throw an exception.
		p1.reverse();
	}
	
	@Test
	public void testReverseComplete() {
		for (int n = 1; n <= 8; n *= 2) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p);
			copy.reverse();
			for (int i = 0; i < n; i++) {
				assertEquals(p.get(i), copy.get(n-1-i));
			}
		}
	}
	
	@Test
	public void testReverseSub() {
		Permutation p = new Permutation(8);
		for (int j = 0; j < p.length(); j++) {
			for (int k = j+1; k < p.length(); k++) {
				Permutation copy = new Permutation(p);
				copy.reverse(j, k);
				validateReversal(p, copy, j, k);
				copy = new Permutation(p);
				copy.reverse(k, j);
				validateReversal(p, copy, j, k);
			}
		}
	}
	
	private void validateReversal(Permutation p, Permutation copy, int j, int k) {
		for (int i = 0; i < j; i++) {
			assertEquals(p.get(i), copy.get(i));
		}
		int shift = 0;
		for (int i = j; i <= k; i++) {
			assertEquals(p.get(i), copy.get(k-shift));
			shift++;
		}
		for (int i = k+1; i < p.length(); i++) {
			assertEquals(p.get(i), copy.get(i));
		}
	}
}
