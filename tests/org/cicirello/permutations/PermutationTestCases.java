/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations;

import java.util.SplittableRandom;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the constructors and methods of the Permutation class, as well as the PermutationIterator.
 */
public class PermutationTestCases {
	
	@Test
	public void testPermutationConstructor() {
		for (int n = 1; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p = new Permutation(n);
				validatePermutation(p, n);
			}
		}
		SplittableRandom r = new SplittableRandom();
		for (int n = 1; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p = new Permutation(n, r);
				validatePermutation(p, n);
			}
		}
	}
	
	@Test
	public void testPermutationConstructorSpecific() {
		int fact = 1;
		for (int n = 1; n <= 4; n++) {
			fact *= n;
			for (int i = 0; i < fact; i++) {
				Permutation p = new Permutation(n, i);
				assertEquals("toInteger should produce same int value", i, p.toInteger());
				validatePermutation(p, n);
			}
		}
	}
	
	@Test
	public void testPermutationConstructorFromArray() {
		int[][] arrays = { { 0 }, { 1, 0 }, { 1, 0, 2 }, { 3, 1, 2, 0 } };
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.clone());
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) assertEquals("elements should be in same order", a[i], p.get(i));
		}
	}
	
	@Test
	public void testPermutationCopyConstructor() {
		for (int n = 1; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p = new Permutation(n);
				Permutation copy = new Permutation(p);
				assertEquals("copy should create an identical copy", p, copy);
			}
		}
	}
	
	@Test
	public void testPermutationConstructorCopyPartial() {
		for (int n = 5, m = 2; n <= 10; n++, m++) {
			Permutation p = new Permutation(n);
			Permutation copy = new Permutation(p, m);
			assertEquals("partial copy length", m, copy.length());
			for (int i = 0; i < m; i++) assertEquals("elements should be in same order", p.get(i), copy.get(i));
		}
	}
	
	@Test
	public void testPermutationEquals() {
		Permutation p = new Permutation(10);
		for (int n = 1; n < 10; n++) {
			Permutation partialCopy = new Permutation(p, n);
			assertNotEquals("lengths differ not equal", p, partialCopy);
		}
		Permutation copy = new Permutation(p);
		assertEquals("identical copy should be equal", p, copy);
		assertNotEquals("different at end points", new Permutation(new int[] {0, 1, 2, 3}), new Permutation(new int[] {3, 1, 2, 0}));
	}
	
	@Test
	public void testPermutationInverse() {
		Permutation p = new Permutation(new int[] {4, 2, 5, 0, 3, 1});
		int[] expected = {3, 5, 1, 4, 0, 2};
		int[] inv = p.getInverse();
		assertArrayEquals("inverse", expected, inv);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		assertArrayEquals("inverse", array, p.getInverse());
	}
	
	@Test
	public void testPermutationRemoveInsert() {
		Permutation p = new Permutation(5);
		for (int i = 0; i < p.length(); i++) {
			for (int j = 0; j < p.length(); j++) {
				if (i==j) continue;
				Permutation copy = new Permutation(p);
				copy.removeAndInsert(i, j);
				if (i < j) {
					for (int k = 0; k < i; k++) {
						assertEquals("elements before removal point should not change", p.get(k), copy.get(k));
					}
					for (int k = i; k < j; k++) {
						assertEquals("elements from removal to insertion point should shift once", p.get(k+1), copy.get(k));
					}
					assertEquals("removed element should be at insertion point", p.get(i), copy.get(j));
					for (int k = j+1; k < p.length(); k++) {
						assertEquals("elements after insertion point should not change", p.get(k), copy.get(k));
					}
				} else {
					for (int k = 0; k < j; k++) {
						assertEquals("elements before insertion point should not change", p.get(k), copy.get(k));
					}
					assertEquals("removed element should be at insertion point", p.get(i), copy.get(j));
					for (int k = j+1; k <= i; k++) {
						assertEquals("elements from removal to insertion point should shift once", p.get(k-1), copy.get(k));
					}
					for (int k = i+1; k < p.length(); k++) {
						assertEquals("elements after removal point should not change", p.get(k), copy.get(k));
					}
				}
			}
		}
	}
	
	@Test
	public void testPermutationReverse() {
		Permutation p = new Permutation(10);
		Permutation copy = new Permutation(p);
		copy.reverse();
		for (int i = 0; i < p.length(); i++) {
			assertEquals("elements should be in reverse order", p.get(i), copy.get(p.length()-1-i));
		}
		for (int j = 0; j < p.length(); j++) {
			for (int k = j+1; k < p.length(); k++) {
				copy = new Permutation(p);
				copy.reverse(j, k);
				for (int i = 0; i < j; i++) {
					assertEquals("elements before should not change", p.get(i), copy.get(i));
				}
				int shift = 0;
				for (int i = j; i <= k; i++) {
					assertEquals("elements should be reversed", p.get(i), copy.get(k-shift));
					shift++;
				}
				for (int i = k+1; i < p.length(); i++) {
					assertEquals("elements after should not change", p.get(i), copy.get(i));
				}
			}
		}
	}
	
	@Test
	public void testPermutationRotate() {
		Permutation p = new Permutation(10);
		for (int r = 0; r < p.length(); r++) {
			Permutation copy = new Permutation(p);
			copy.rotate(r);
			for (int i = 0; i < p.length(); i++) {
				int j = (i + r) % p.length();
				assertEquals("elements should be left rotated " + r + " places", p.get(j), copy.get(i));
			}
		}
	}
	
	@Test
	public void testPermutationIterator() {
		int fact = 1;
		for (int n = 1; n <= 5; n++) {
			Permutation p = new Permutation(n);
			boolean checkedFirst = false;
			fact *= n;
			boolean[] found = new boolean[fact];
			int count = 0;
			for (Permutation pPrime : p) {
				if (!checkedFirst) {
					assertEquals("first permutation given by Iterator should be p", p, pPrime);
					checkedFirst = true;
				}
				int permID = pPrime.toInteger();
				assertFalse("should not give same permutation more than once", found[permID]);
				found[permID] = true;
				count++;
			}
			assertEquals("Should iterate over all permutations of given length", fact, count);
		}
	}
	
	
	private void validatePermutation(Permutation p, int n) {
		assertEquals("permutation length", n, p.length());
		boolean[] inP = new boolean[n];
		for (int i = 0; i < p.length(); i++) {
			int el = p.get(i);
			assertTrue("permutation element values", el >= 0 && el < n);
			assertFalse("permutation elements shouldn't be duplicated", inP[el]);
			inP[el] = true;
		}
	}
	
}