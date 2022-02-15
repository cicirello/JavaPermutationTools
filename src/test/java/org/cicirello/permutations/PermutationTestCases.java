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
 */
package org.cicirello.permutations;

import java.util.SplittableRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the constructors and methods of the Permutation class, as well as the PermutationIterator.
 */
public class PermutationTestCases {
	
	// Set to false to enable chi square tests for randomness of constructor and
	// scramble, specifically for the constructor and scramble that uses ThreadLocalRandom
	// since it is not seedable.  Tests passed repeatedly as of last update.
	// If that constructor or scramble is updated, then this flag should be set false to enable
	// tests (and reset true after passing).
	// Note: This doesn't disable those tests for the constructors/scramble methods
	// that use a source of randomness that is seedable.
	private static final boolean disableChiSquareTests = true;
	
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
		
		assertEquals(0, p1.getInverse().length, "length of inverse of zero length permutation should be 0");
		
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
				assertEquals(i, p.toInteger());
				assertEquals(BigInteger.valueOf(i), p.toBigInteger());
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
			assertEquals(which, p.toInteger());
			assertEquals(BigInteger.valueOf(which), p.toBigInteger());
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
				assertEquals(i, p.toInteger());
				assertEquals(big, p.toBigInteger());
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
			assertEquals(which, p.toInteger());
			assertEquals(bigWhich, p.toBigInteger());
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
			assertEquals(bigWhich, p.toBigInteger());
			validatePermutation(p, n);
		}
	}
	
	@Test
	public void testToIntegerExceptions() {
		UnsupportedOperationException thrown = assertThrows( 
			UnsupportedOperationException.class,
			() -> (new Permutation(13)).toInteger()
		);
	}
	
	@Test
	public void testPermutationConstructorFromArray() {
		int[][] arrays = { { 0 }, { 1, 0 }, { 1, 0, 2 }, { 3, 1, 2, 0 } };
		String[] str = {"0", "1 0", "1 0 2", "3 1 2 0"};
		int s = 0;
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.clone());
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i), "elements should be in same order");
			}
			assertEquals(str[s], p.toString());
			s++;
		}
		assertEquals("", (new Permutation(0)).toString());
		final int[] negative = { 1, 4, -1, 0, 3};
		final int[] tooHigh = { 1, 4, 5, 0, 3};
		final int[] duplicate = { 1, 4, 2, 1, 3};
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new Permutation(negative)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new Permutation(tooHigh)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> new Permutation(duplicate)
		);
	}
	
	@Test
	public void testPermutationSetFromArray() {
		int[][] arrays = { { 0 }, { 1, 0 }, { 1, 0, 2 }, { 3, 1, 2, 0 } };
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			p.set(a.clone());
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		final int[] negative = { 1, 4, -1, 0, 3};
		final int[] tooHigh = { 1, 4, 5, 0, 3};
		final int[] duplicate = { 1, 4, 2, 1, 3};
		final int[] tooLong = { 1, 2, 4, 5, 0, 3};
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(5)).set(negative)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(5)).set(tooHigh)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(5)).set(duplicate)
		);
		assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(5)).set(tooLong)
		);
	}
	
	@Test
	public void testPermutationMechanicSet() {
		
		class MyMech extends Permutation.Mechanic {
			public void testSet(Permutation p, int[] a) {
				set(p, a);
			}
			public void testSet(Permutation p, int i, int v) {
				set(p, i, v);
			}
			public void testSet(Permutation p, int i, int[] a) {
				set(p, i, a);
			}
			public void testSet(Permutation p, int[] s, int from, int to, int numElements) {
				set(p, s, from, to, numElements);
			}
		}
		MyMech test = new MyMech();
		
		int[][] arrays = { { 0 }, { 1, 0 }, { 1, 0, 2 }, { 3, 1, 2, 0 } };
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			test.testSet(p, a.clone()); 
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			for (int i = 0; i < a.length; i++) {
				test.testSet(p, i, a[i]);
			} 
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			test.testSet(p, 0, a.clone()); 
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a);
			int[] change = a.length > 2 ? new int[] {7, 5} : new int[] {7};
			int start = a.length > 1 ? 1 : 0;
			test.testSet(p, start, change);
			for (int i = 0; i < start; i++) {
				assertEquals(a[i], p.get(i));
			}
			for (int i = 0; i < change.length; i++) {
				assertEquals(change[i], p.get(start+i), "checking changed elements");
			}
			for (int i = start + change.length; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a.length, 0);
			test.testSet(p, a.clone(), 0, 0, a.length); 
			validatePermutation(p, a.length);
			for (int i = 0; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a);
			int[] change = a.length > 2 ? new int[] {7, 5} : new int[] {7};
			int start = a.length > 1 ? 1 : 0;
			test.testSet(p, change, 0, start, change.length);
			for (int i = 0; i < start; i++) {
				assertEquals(a[i], p.get(i));
			}
			for (int i = 0; i < change.length; i++) {
				assertEquals(change[i], p.get(start+i), "checking changed elements");
			}
			for (int i = start + change.length; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
		for (int[] a : arrays) {
			Permutation p = new Permutation(a);
			int[] change = a.length > 2 ? new int[] {9, 9, 7, 5, 9, 9} : new int[] {9, 9, 7, 9, 9};
			int start = a.length > 1 ? 1 : 0;
			test.testSet(p, change, 2, start, change.length - 4);
			for (int i = 0; i < start; i++) {
				assertEquals(a[i], p.get(i));
			}
			for (int i = 2; i < change.length-2; i++) {
				assertEquals(change[i], p.get(start+i-2), "checking changed elements");
			}
			for (int i = start + change.length - 4; i < a.length; i++) {
				assertEquals(a[i], p.get(i));
			}
		}
	}
	
	@Test
	public void testPermutationCopyConstructor() {
		for (int n = 1; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p = new Permutation(n);
				Permutation copy = new Permutation(p);
				assertEquals(p, copy);
				assertEquals(p.hashCode(), copy.hashCode());
			}
		}
	}
	
	@Test
	public void testPermutationCopyMethod() {
		for (int n = 1; n <= 10; n++) {
			for (int i = 0; i < 10; i++) {
				Permutation p = new Permutation(n);
				Permutation copy = p.copy();
				assertEquals(p, copy);
				assertTrue(p != copy);
				assertEquals(p.hashCode(), copy.hashCode());
			}
		}
	}
	
	@Test
	public void testPermutationEqualsSpecialCases() {
		Permutation p = new Permutation(5);
		assertTrue(p.equals(p));
		assertFalse(p.equals(null));
		assertFalse(p.equals("hello"));
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
				assertEquals(m, copy1.length());
				assertEquals(m, copy2.length());
				validatePermutation(copy1, m);
				validatePermutation(copy2, m);
				for (int i = 0; i < m; i++) {
					assertEquals(i, copy1.get(i));
					assertEquals(m-1-i, copy2.get(i));
				}
			}
		}
	}
	
	@Test
	public void testPermutationEquals() {
		Permutation p = new Permutation(10);
		for (int n = 1; n < 10; n++) {
			Permutation partialCopy = new Permutation(p, n);
			assertNotEquals(p, partialCopy);
		}
		Permutation copy = new Permutation(p);
		assertEquals(p, copy);
		assertNotEquals(new Permutation(new int[] {0, 1, 2, 3}), new Permutation(new int[] {3, 1, 2, 0}));
	}
	
	@Test
	public void testPermutationInverse() {
		int[] before = {4, 2, 5, 0, 3, 1};
		int[] beforeCopy = before.clone();
		Permutation p = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		int[] inv = p.getInverse();
		assertArrayEquals(expected, inv);
		assertArrayEquals(beforeCopy, before);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		assertArrayEquals(array, p.getInverse());
	}
	
	@Test
	public void testPermutationInverseP() {
		int[] before = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(before);
		Permutation p2 = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		Permutation pExpected = new Permutation(expected);
		Permutation inv = p.getInversePermutation();
		assertEquals(pExpected, inv);
		assertEquals(p2, p);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		assertEquals(p, p.getInversePermutation());
	}
	
	@Test
	public void testPermutationInvert() {
		int[] before = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(before);
		int[] expected = {3, 5, 1, 4, 0, 2};
		Permutation pExpected = new Permutation(expected);
		p.invert();
		assertEquals(pExpected, p);
		int[] array = {0, 1, 2, 3, 4, 5};
		p = new Permutation(array);
		pExpected = new Permutation(array);
		p.invert();
		assertEquals(pExpected, p);
	}
	
	@Test
	public void testToArray() {
		int[] init = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(init.clone());
		int[] array = p.toArray();
		assertArrayEquals(init, array);
		for (int i = 0; i < array.length; i++) {
			// change the array to confirm did not affect Permutation
			array[i] = i;
		}
		assertArrayEquals(init, p.toArray());
		
		array = null;
		array = p.toArray(array);
		assertArrayEquals(init, array);
		
		array = new int[6];
		int[] result = p.toArray(array);
		assertTrue(result == array);
		assertArrayEquals(init, result);
		
		array = new int[7];
		result = null;
		result = p.toArray(array);
		assertFalse(result == array);
		assertArrayEquals(init, result);
	}
	
	@Test
	public void testGetRange() {
		int[] init = {4, 2, 5, 0, 3, 1};
		Permutation p = new Permutation(init.clone());
		for (int i = 0; i < init.length; i++) {
			for (int j = i; j < init.length; j++) {
				int[] a = p.get(i, j);
				assertEquals(j-i+1, a.length);
				for (int k = 0; k < a.length; k++) {
					assertEquals(init[i+k], a[k]);
				}
			}
		}
		for (int i = 0; i < init.length; i++) {
			for (int j = i; j < init.length; j++) {
				int[] result = new int[j-i+1];
				int[] a = p.get(i, j, result);
				assertTrue(result == a);
				assertEquals(j-i+1, a.length);
				for (int k = 0; k < a.length; k++) {
					assertEquals(init[i+k], a[k]);
				}
			}
		}
		for (int i = 0; i < init.length; i++) {
			for (int j = i; j < init.length; j++) {
				int[] result = null;
				int[] a = p.get(i, j, result);
				assertEquals(j-i+1, a.length);
				for (int k = 0; k < a.length; k++) {
					assertEquals(init[i+k], a[k]);
				}
			}
		}
		for (int i = 0; i < init.length; i++) {
			for (int j = i; j < init.length; j++) {
				int[] result = new int[j-i+2];
				int[] a = p.get(i, j, result);
				assertTrue(result != a);
				assertEquals(j-i+1, a.length);
				for (int k = 0; k < a.length; k++) {
					assertEquals(init[i+k], a[k]);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(3)).get(1,0)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(3)).get(1,0, null)
		);
	}
	
	@Test
	public void testPermutationRemoveInsert() {
		Permutation p = new Permutation(5);
		for (int i = 0; i < p.length(); i++) {
			for (int j = 0; j < p.length(); j++) {
				Permutation copy = new Permutation(p);
				copy.removeAndInsert(i, j);
				if (i < j) {
					for (int k = 0; k < i; k++) {
						assertEquals(p.get(k), copy.get(k));
					}
					for (int k = i; k < j; k++) {
						assertEquals(p.get(k+1), copy.get(k));
					}
					assertEquals(p.get(i), copy.get(j));
					for (int k = j+1; k < p.length(); k++) {
						assertEquals(p.get(k), copy.get(k));
					}
				} else if (i > j) {
					for (int k = 0; k < j; k++) {
						assertEquals(p.get(k), copy.get(k));
					}
					assertEquals(p.get(i), copy.get(j));
					for (int k = j+1; k <= i; k++) {
						assertEquals(p.get(k-1), copy.get(k));
					}
					for (int k = i+1; k < p.length(); k++) {
						assertEquals(p.get(k), copy.get(k));
					}
				} else {
					assertEquals(p, copy);
				}
			}
		}
	}
	
	@Test
	public void testPermutationBlockRemoveInsert() {
		int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] a1 = {7, 0, 1, 2, 3, 4, 5, 6, 8, 9, 10};
		int[] a2 = {0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 2};
		int[] a3 = {7, 8, 0, 1, 2, 3, 4, 5, 6, 9, 10};
		int[] a4 = {0, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2};
		int[] a5 = {0, 3, 4, 5, 6, 7, 1, 2, 8, 9, 10};
		int[] a6 = {0, 7, 8, 1, 2, 3, 4, 5, 6, 9, 10};
		Permutation p = new Permutation(a);
		Permutation p1 = new Permutation(a1);
		Permutation mutant = new Permutation(p);
		mutant.removeAndInsert(7, 1, 0);
		assertEquals(p1, mutant);
		Permutation p2 = new Permutation(a2);
		mutant = new Permutation(p);
		mutant.removeAndInsert(2, 1, 10);
		assertEquals(p2, mutant);
		Permutation p3 = new Permutation(a3);
		mutant = new Permutation(p);
		mutant.removeAndInsert(7, 2, 0);
		assertEquals(p3, mutant);
		Permutation p4 = new Permutation(a4);
		mutant = new Permutation(p);
		mutant.removeAndInsert(1, 2, 9);
		assertEquals(p4, mutant);
		Permutation p5 = new Permutation(a5);
		mutant = new Permutation(p);
		mutant.removeAndInsert(1, 2, 6);
		assertEquals(p5, mutant);
		Permutation p6 = new Permutation(a6);
		mutant = new Permutation(p);
		mutant.removeAndInsert(7, 2, 1);
		assertEquals(p6, mutant);
		
		mutant = new Permutation(p);
		mutant.removeAndInsert(1, 0, 3);
		assertEquals(p, mutant);
		mutant = new Permutation(p);
		mutant.removeAndInsert(1, 3, 1);
		assertEquals(p, mutant);
	}
	
	@Test
	public void testPermutationReverse() {
		Permutation p = new Permutation(10);
		Permutation copy = new Permutation(p);
		copy.reverse();
		for (int i = 0; i < p.length(); i++) {
			assertEquals(p.get(i), copy.get(p.length()-1-i));
		}
		for (int j = 0; j < p.length(); j++) {
			for (int k = j+1; k < p.length(); k++) {
				copy = new Permutation(p);
				copy.reverse(j, k);
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
				copy = new Permutation(p);
				copy.reverse(k, j);
				for (int i = 0; i < j; i++) {
					assertEquals(p.get(i), copy.get(i));
				}
				shift = 0;
				for (int i = j; i <= k; i++) {
					assertEquals(p.get(i), copy.get(k-shift));
					shift++;
				}
				for (int i = k+1; i < p.length(); i++) {
					assertEquals(p.get(i), copy.get(i));
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
	
	@Test
	public void testCycle() {
		// 2-cycles
		int[] indexes = new int[2];
		for (int i = 1; i <= 5; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			indexes[0] = 0;
			indexes[1] = i-1;
			p2.cycle(indexes);
			assertEquals(p.get(0), p2.get(i-1));
			assertEquals(p.get(i-1), p2.get(0));
			for (int j = 1; j < i-1; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
		}
		for (int i = 1; i <= 5; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			indexes[1] = 0;
			indexes[0] = i-1;
			p2.cycle(indexes);
			assertEquals(p.get(0), p2.get(i-1));
			assertEquals(p.get(i-1), p2.get(0));
			for (int j = 1; j < i-1; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
		}
		for (int i = 4; i <= 6; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			indexes[0] = (i-1)/2;
			indexes[1] = indexes[0]+1;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[0]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			for (int j = 0; j < indexes[0]; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
			for (int j = indexes[1]+1; j < i; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
		}
		// 3-cycles
		indexes = new int[3];
		for (int i = 3; i <= 6; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			indexes[0] = 0;
			indexes[1] = i/2;
			indexes[2] = i-1;
			boolean[] inCycle = new boolean[i];
			inCycle[indexes[0]] = inCycle[indexes[1]] = inCycle[indexes[2]] = true;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
			p2 = new Permutation(p);
			indexes[0] = 0;
			indexes[1] = i-1;
			indexes[2] = i/2;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
			p2 = new Permutation(p);
			indexes[0] = i/2;
			indexes[1] = 0;
			indexes[2] = i-1;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
			p2 = new Permutation(p);
			indexes[0] = i/2;
			indexes[1] = i-1;
			indexes[2] = 0;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
			p2 = new Permutation(p);
			indexes[0] = i-1;
			indexes[1] = 0;
			indexes[2] = i/2;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
			p2 = new Permutation(p);
			indexes[0] = i-1;
			indexes[1] = i/2;
			indexes[2] = 0;
			p2.cycle(indexes);
			assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
			assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
			assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
			for (int j = 0; j < i; j++) {
				// verify that non-cycle positions didn't change
				if (!inCycle[j]) {
					assertEquals(p.get(j), p2.get(j));
				}
			}
		}
		// 1 cycle and 0-cycle
		int[] indexes1 = {0};
		int[] indexes0 = {};
		for (int i = 1; i <= 4; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			p2.cycle(indexes1);
			assertEquals(p, p2);
			p2.cycle(indexes0);
			assertEquals(p, p2);
		}
	}
	
	@Test
	public void testNoncontiguousScramble() {
		Random r1 = new Random(42);
		SplittableRandom r2 = new SplittableRandom(42);
		// Verify does nothing if permutation length < 2.
		Permutation p = new Permutation(1);
		int[] indexes = {0};
		for (int i = 0; i < 5; i++) {
			p.scramble(indexes, r2);
			assertEquals(0, p.get(0));
			p.scramble(indexes, r1);
			assertEquals(0, p.get(0));
			p.scramble(indexes);
			assertEquals(0, p.get(0));
		}
		// 2 indexes
		indexes = new int[2];
		for (int n = 2; n <= 5; n++) {
			indexes[0] = n-1;
			indexes[1] = 0;
			boolean[] shouldChange = new boolean[n];
			shouldChange[indexes[0]] = shouldChange[indexes[1]] = true;
			p = new Permutation(n, 0);
			Permutation p0 = new Permutation(p);
			p.scramble(indexes, r2);
			assertEquals(0, p.get(n-1));
			assertEquals(n-1, p.get(0));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
			p = new Permutation(n, 0);
			p.scramble(indexes, r1);
			assertEquals(0, p.get(n-1));
			assertEquals(n-1, p.get(0));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
			p = new Permutation(n, 0);
			p.scramble(indexes);
			assertEquals(0, p.get(n-1));
			assertEquals(n-1, p.get(0));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
		}
		for (int n = 4; n <= 6; n++) {
			indexes[0] = (n-1)/2;
			indexes[1] = indexes[0]+1;
			boolean[] shouldChange = new boolean[n];
			shouldChange[indexes[0]] = shouldChange[indexes[1]] = true;
			p = new Permutation(n, 0);
			Permutation p0 = new Permutation(p);
			p.scramble(indexes, r2);
			assertEquals(p0.get(indexes[1]), p.get(indexes[0]));
			assertEquals(p0.get(indexes[0]), p.get(indexes[1]));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
			p = new Permutation(n, 0);
			p.scramble(indexes, r1);
			assertEquals(p0.get(indexes[1]), p.get(indexes[0]));
			assertEquals(p0.get(indexes[0]), p.get(indexes[1]));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
			p = new Permutation(n, 0);
			p.scramble(indexes);
			assertEquals(p0.get(indexes[1]), p.get(indexes[0]));
			assertEquals(p0.get(indexes[0]), p.get(indexes[1]));
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				}
			}
		}
		// 3 indexes
		indexes = new int[3];
		for (int n = 3; n <= 8; n++) {
			indexes[0] = n-1;
			indexes[1] = 0;
			indexes[2] = n/2;
			boolean[] shouldChange = new boolean[n];
			shouldChange[indexes[0]] = shouldChange[indexes[1]] = shouldChange[indexes[2]] = true;
			p = new Permutation(n, 0);
			Permutation p0 = new Permutation(p);
			p.scramble(indexes, r2);
			assertTrue(p0.get(indexes[0])!=p.get(indexes[0])
				|| p0.get(indexes[1])!=p.get(indexes[1])
				|| p0.get(indexes[2])!=p.get(indexes[2])
			);
			int[] foundInScramble = new int[n];
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				} else {
					foundInScramble[p.get(i)]++;
					foundInScramble[p0.get(i)]--;
				}
			}
			for (int i = 0; i < n; i++) {
				assertEquals(0, foundInScramble[i]);
			}
			p = new Permutation(n, 0);
			p.scramble(indexes, r1);
			assertTrue(p0.get(indexes[0])!=p.get(indexes[0])
				|| p0.get(indexes[1])!=p.get(indexes[1])
				|| p0.get(indexes[2])!=p.get(indexes[2])
			);
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				} else {
					foundInScramble[p.get(i)]++;
					foundInScramble[p0.get(i)]--;
				}
			}
			for (int i = 0; i < n; i++) {
				assertEquals(0, foundInScramble[i]);
			}
			p = new Permutation(n, 0);
			p.scramble(indexes);
			assertTrue(p0.get(indexes[0])!=p.get(indexes[0])
				|| p0.get(indexes[1])!=p.get(indexes[1])
				|| p0.get(indexes[2])!=p.get(indexes[2])
			);
			for (int i = 0; i < n; i++) {
				if (!shouldChange[i]) {
					assertEquals(p0.get(i), p.get(i));
				} else {
					foundInScramble[p.get(i)]++;
					foundInScramble[p0.get(i)]--;
				}
			}
			for (int i = 0; i < n; i++) {
				assertEquals(0, foundInScramble[i]);
			}
		}
	}
	
	@Test
	public void testSwap() {
		for (int i = 1; i <= 5; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			p2.swap(0, i-1);
			assertEquals(p.get(0), p2.get(i-1));
			assertEquals(p.get(i-1), p2.get(0));
			for (int j = 1; j < i-1; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
		}
		for (int i = 1; i <= 5; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			p2.swap(i-1, 0);
			assertEquals(p.get(0), p2.get(i-1));
			assertEquals(p.get(i-1), p2.get(0));
			for (int j = 1; j < i-1; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
		}
		for (int i = 4; i <= 10; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			p2.swap(1, i-2);
			assertEquals(p.get(1), p2.get(i-2));
			assertEquals(p.get(i-2), p2.get(1));
			for (int j = 2; j < i-2; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
			assertEquals(p.get(0), p2.get(0));
			assertEquals(p.get(i-1), p2.get(i-1));
		}
		for (int i = 4; i <= 10; i++) {
			Permutation p = new Permutation(i);
			Permutation p2 = new Permutation(p);
			p2.swap(i-2, 1);
			assertEquals(p.get(1), p2.get(i-2));
			assertEquals(p.get(i-2), p2.get(1));
			for (int j = 2; j < i-2; j++) {
				assertEquals(p.get(j), p2.get(j));
			}
			assertEquals(p.get(0), p2.get(0));
			assertEquals(p.get(i-1), p2.get(i-1));
		}
	}
	
	@Test
	public void testSwapBlocks() {
		int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		for (int i = 1; i < 11; i++) {
			for (int j = 1; i+j <= 11; j++) {
				Permutation p = new Permutation(a);
				int b = i-1;
				int c = 11-j;
				p.swapBlocks(0, b, c, 10);
				String s = "(0, " + b + ", " + c + ", 10)"; 
				int y = 0;
				for (int x = 11-j; x <= 10; x++, y++) {
					assertEquals(a[x], p.get(y), "left block of result, params="+s);
				}
				for (int x = i; x < 11-j; x++, y++) {
					assertEquals(a[x], p.get(y), "interior of swapped blocks, params="+s);
				}
				for (int x = 0; x < i; x++, y++) {
					assertEquals(a[x], p.get(y), "right block of result, params="+s);
				}
			}
		}
		for (int i = 1; i < 11; i++) {
			for (int j = 1; i+j <= 9; j++) {
				Permutation p = new Permutation(a);
				int b = i;
				int c = 10-j;
				p.swapBlocks(1, b, c, 9);
				String s = "(1, " + b + ", " + c + ", 9)"; 
				int y = 1;
				assertEquals(a[0], p.get(0));
				assertEquals(a[10], p.get(10));
				for (int x = c; x <= 9; x++, y++) {
					assertEquals(a[x], p.get(y), "left block of result, params="+s);
				}
				for (int x = b+1; x < c; x++, y++) {
					assertEquals(a[x], p.get(y), "interior of swapped blocks, params="+s);
				}
				for (int x = 1; x <= b; x++, y++) {
					assertEquals(a[x], p.get(y), "right block of result, params="+s);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(10)).swapBlocks(-1, 3, 5, 7)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(10)).swapBlocks(3, 2, 5, 7)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(10)).swapBlocks(1, 3, 3, 7)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(10)).swapBlocks(1, 3, 7, 6)
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> (new Permutation(10)).swapBlocks(1, 3, 5, 10)
		);
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
					assertEquals(p, pPrime);
					checkedFirst = true;
				}
				int permID = pPrime.toInteger();
				assertFalse(found[permID]);
				found[permID] = true;
				count++;
			}
			assertEquals(fact, count);
		}
		final PermutationIterator iter = new PermutationIterator(4);
		fact = 24;
		boolean[] found = new boolean[fact];
		int count = 0;
		while (iter.hasNext()) {
			Permutation p = iter.next();
			int permID = p.toInteger();
			assertFalse(found[permID]);
			found[permID] = true;
			count++;
		}
		assertEquals(fact, count);
		NoSuchElementException thrown = assertThrows( 
			NoSuchElementException.class,
			() -> iter.next()
		);
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
				p.scramble(false);
				validatePermutation(p, i);
				p.scramble(r1, false);
				validatePermutation(p, i);
				p.scramble(r2, false);
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
	
	@Test
	public void testScrambleFromItoJ() {
		Random r1 = new Random();
		SplittableRandom r2 = new SplittableRandom();
		for (int n = 4; n < 8; n++) {
			Permutation p = new Permutation(n);
			Permutation original = new Permutation(p);
			p.scramble(1, n-2);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
			original = new Permutation(p);
			p.scramble(1, n-2, r1);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
			original = new Permutation(p);
			p.scramble(1, n-2, r2);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
			original = new Permutation(p);
			p.scramble(0, n-1);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			original = new Permutation(p);
			p.scramble(0, n-1, r1);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			original = new Permutation(p);
			p.scramble(0, n-1, r2);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			original = new Permutation(p);
			p.scramble(1, 1, r2);
			assertEquals(original, p);
			original = new Permutation(p);
			p.scramble(1, 1, r1);
			assertEquals(original, p);
			original = new Permutation(p);
			p.scramble(1, 1);
			assertEquals(original, p);
			
			original = new Permutation(p);
			p.scramble(n-2, 1);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
			original = new Permutation(p);
			p.scramble(n-2, 1, r1);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
			original = new Permutation(p);
			p.scramble(n-2, 1, r2);
			validatePermutation(p, n);
			if (n > 1) assertNotEquals(original, p);
			assertEquals(original.get(0), p.get(0));
			assertEquals(original.get(n-1), p.get(n-1));
		}
	}
	
	@Test
	public void testUniformityOfConstructors() {
		final int N = 12000;
		Random r1 = new Random(42);
		SplittableRandom r2 = new SplittableRandom(42);
		int tooHigh0 = 0;
		int tooHigh1 = 0;
		int tooHigh2 = 0;
		for (int k = 0; k < 100; k++) {
			int[] counts0 = new int[120];
			int[] counts1 = new int[120];
			int[] counts2 = new int[120];
			for (int i = 0; i < N; i++) {
				Permutation p0 = new Permutation(5);
				Permutation p1 = new Permutation(5, r1);
				Permutation p2 = new Permutation(5, r2);
				int j0 = p0.toInteger();
				int j1 = p1.toInteger();
				int j2 = p2.toInteger();
				counts0[j0]++;
				counts1[j1]++;
				counts2[j2]++;
			}
			if (chiSquare(counts0) > 146.567) tooHigh0++;
			if (chiSquare(counts1) > 146.567) tooHigh1++;
			if (chiSquare(counts2) > 146.567) tooHigh2++;
		}
		if (!disableChiSquareTests) {
			assertTrue(tooHigh0 <= 10);
		}
		assertTrue(tooHigh1 <= 10);
		assertTrue(tooHigh2 <= 10);
	}
	
	@Test
	public void testUniformityOfScramble() {
		final int N = 12000;
		Random r1 = new Random(42);
		SplittableRandom r2 = new SplittableRandom(42);
		int tooHigh0 = 0;
		int tooHigh1 = 0;
		int tooHigh2 = 0;
		Permutation p0 = new Permutation(5);
		Permutation p1 = new Permutation(5, r1);
		Permutation p2 = new Permutation(5, r2);
		for (int k = 0; k < 100; k++) {
			int[] counts0 = new int[120];
			int[] counts1 = new int[120];
			int[] counts2 = new int[120];
			for (int i = 0; i < N; i++) {
				p0.scramble();
				p1.scramble(r1);
				p2.scramble(r2);
				int j0 = p0.toInteger();
				int j1 = p1.toInteger();
				int j2 = p2.toInteger();
				counts0[j0]++;
				counts1[j1]++;
				counts2[j2]++;
			}
			if (chiSquare(counts0) > 146.567) tooHigh0++;
			if (chiSquare(counts1) > 146.567) tooHigh1++;
			if (chiSquare(counts2) > 146.567) tooHigh2++;
		}
		if (!disableChiSquareTests) {
			assertTrue(tooHigh0 <= 10);
		}
		assertTrue(tooHigh1 <= 10);
		assertTrue(tooHigh2 <= 10);
	}
	
	private void validatePermutation(Permutation p, int n) {
		assertEquals(n, p.length());
		boolean[] inP = new boolean[n];
		for (int i = 0; i < p.length(); i++) {
			int el = p.get(i);
			assertTrue(el >= 0 && el < n);
			assertFalse(inP[el]);
			inP[el] = true;
		}
	}
	
	private double chiSquare(int[] buckets) {
		int x = 0;
		int n = 0;
		for (int e : buckets) {
			x = x + e*e;
			n += e;
		}
		return 1.0*x / (n/buckets.length) - n;
	}
	
}