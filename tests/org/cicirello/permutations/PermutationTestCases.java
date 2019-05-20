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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.math.BigInteger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the constructors and methods of the Permutation class, as well as the PermutationIterator.
 */
public class PermutationTestCases {
	
	@Test
	public void testZeroLengthPermutations() {
		// different ways of constructing 0 length permutations.
		Permutation p1 = new Permutation(0);		
		Permutation p2 = new Permutation(new int[] {});
		Permutation p3 = new Permutation(0,0);
		Permutation copy = new Permutation(p1);
		
		// the above 4 should all be equivalent, so check
		assertEquals(p1,p2);
		assertEquals(p1,p3);
		assertEquals(p1,copy);
		assertEquals(p2,p3);
		assertEquals(p2,copy);
		assertEquals(p3,copy);
		
		assertEquals("length of inverse of zero length permutation should be 0", 0, p1.getInverse().length);
		
		// confirm that reverse() and scramble() don't throw exceptions
		p1.reverse();
		p1.scramble();
		p1.scramble(new Random());
		p1.scramble(new SplittableRandom());
		p1.scramble(true);
		p1.scramble(new Random(),true);
		p1.scramble(new SplittableRandom(),true);
	}
	
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
		for (int n = 1; n <= 6; n++) {
			fact *= n;
			for (int i = 0; i < fact; i++) {
				Permutation p = new Permutation(n, i);
				assertEquals("toInteger should produce same int value", i, p.toInteger());
				assertEquals("toBigInteger should produce same int value", BigInteger.valueOf(i), p.toBigInteger());
				validatePermutation(p, n);
			}
		}
		int n = 12;
		fact = 1;
		for (int i = 2; i <= n; i++) {
			fact *= i;
		}
		SplittableRandom r = new SplittableRandom();
		for (int i = 0; i < 100; i++) {
			int which = r.nextInt(fact);
			Permutation p = new Permutation(n, which);
			assertEquals("toInteger should produce same int value", which, p.toInteger());
			assertEquals("toBigInteger should produce same int value", BigInteger.valueOf(which), p.toBigInteger());
			validatePermutation(p, n);
		}
	}
	
	@Test
	public void testPermutationConstructorSpecificBigInt() {
		int fact = 1;
		for (int n = 1; n <= 6; n++) {
			fact *= n;
			for (int i = 0; i < fact; i++) {
				BigInteger big = BigInteger.valueOf(i);
				Permutation p = new Permutation(n, big);
				assertEquals("toInteger should produce same int value", i, p.toInteger());
				assertEquals("toBigInteger should produce same int value", big, p.toBigInteger());
				validatePermutation(p, n);
			}
		}
		int n = 12;
		fact = 1;
		for (int i = 2; i <= n; i++) {
			fact *= i;
		}
		SplittableRandom r = new SplittableRandom();
		for (int i = 0; i < 100; i++) {
			int which = r.nextInt(fact);
			BigInteger bigWhich = BigInteger.valueOf(which);
			Permutation p = new Permutation(n, bigWhich);
			assertEquals("toInteger should produce same int value", which, p.toInteger());
			assertEquals("toBigInteger should produce same int value", bigWhich, p.toBigInteger());
			validatePermutation(p, n);
		}
		n = 20;
		BigInteger f = BigInteger.ONE;
		for (int i = 2; i <= n; i++) {
			f = f.multiply(BigInteger.valueOf(i));
		}
		for (int i = 0; i < 20; i++) {
			BigInteger bigWhich = new BigInteger(f.bitLength()-1, ThreadLocalRandom.current());
			Permutation p = new Permutation(n, bigWhich);
			assertEquals("toBigInteger should produce same BigInteger value", bigWhich, p.toBigInteger());
			validatePermutation(p, n);
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
	public void testPermutationSetFromArray() {
		int[][] arrays = { { 0 }, { 1, 0 }, { 1, 0, 2 }, { 3, 1, 2, 0 } };
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			p.set(a);
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
		for (int n = 1; n <= 10; n++) {
			int[] forward = new int[n];
			int[] backward = new int[n];
			for (int i = 0; i < n; i++) {
				backward[n-1-i] = forward[i] = i;
			}
			Permutation p1 = new Permutation(forward);
			Permutation p2 = new Permutation(backward);
			for (int m = 0; m <= n; m++) {
				Permutation copy1 = new Permutation(p1, m);
				Permutation copy2 = new Permutation(p2, m);
				assertEquals("partial copy length", m, copy1.length());
				assertEquals("partial copy length", m, copy2.length());
				validatePermutation(copy1, m);
				validatePermutation(copy2, m);
				for (int i = 0; i < m; i++) {
					assertEquals("should be sorted order", i, copy1.get(i));
					assertEquals("should be reverse sorted order", m-1-i, copy2.get(i));
				}
			}
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
		int[] before = {4, 2, 5, 0, 3, 1};
		int[] beforeCopy = before.clone();
		Permutation p = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		int[] inv = p.getInverse();
		assertArrayEquals("inverse", expected, inv);
		assertArrayEquals("confirm inverse didn't change original", beforeCopy, before);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		assertArrayEquals("inverse", array, p.getInverse());
	}
	
	@Test
	public void testPermutationInverseP() {
		int[] before = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(before);
		Permutation p2 = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		Permutation pExpected = new Permutation(expected);
		Permutation inv = p.getInversePermutation();
		assertEquals("inverse", pExpected, inv);
		assertEquals("confirm inverse didn't change original", p2, p);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		assertEquals("inverse", p, p.getInversePermutation());
	}
	
	@Test
	public void testPermutationInvert() {
		int[] before = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		Permutation pExpected = new Permutation(expected);
		p.invert();
		assertEquals("inverse", pExpected, p);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		pExpected = new Permutation(array);
		p.invert();
		assertEquals("inverse", pExpected, p);
	}
	
	@Test
	public void testToArray() {
		int[] init = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(init);
		int[] array = p.toArray();
		assertArrayEquals("should be equal to current state", init, array);
		for (int i = 0; i < array.length; i++) {
			// change the array to confirm did not affect Permutation
			array[i] = i;
		}
		assertArrayEquals("should still be equal to current state", init, p.toArray());
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
	
	@Test
	public void testScramble() {
		Random r1 = new Random();
		SplittableRandom r2 = new SplittableRandom();
		for (int i = 0; i < 8; i++) {
			Permutation p = new Permutation(i);
			for (int j = 0; j < 10; j++) {
				Permutation original = new Permutation(p);
				p.scramble();
				validatePermutation(p, i);
				original = new Permutation(p);
				p.scramble(r1);
				validatePermutation(p, i);
				original = new Permutation(p);
				p.scramble(r2);
				validatePermutation(p, i);
				original = new Permutation(p);
				p.scramble(true);
				validatePermutation(p, i);
				if (i > 1) assertNotEquals(original, p);
				original = new Permutation(p);
				p.scramble(r1, true);
				validatePermutation(p, i);
				if (i > 1) assertNotEquals(original, p);
				original = new Permutation(p);
				p.scramble(r2, true);
				validatePermutation(p, i);
				if (i > 1) assertNotEquals(original, p);
			}
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