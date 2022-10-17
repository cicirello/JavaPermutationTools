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

import java.util.SplittableRandom;
import java.util.Random;
import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the constructors and methods of the Permutation class.
 */
public class PermutationConstructorRelatedTests extends SharedTestHelpersPermutation {
	
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
			BigInteger bigWhich = new BigInteger(f.bitLength()-1, new Random(42));
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
	public void testUniformityOfConstructors() {
		final int N = 12000;
		SplittableRandom r2 = new SplittableRandom(42);
		int tooHigh0 = 0;
		int tooHigh2 = 0;
		for (int k = 0; k < 100; k++) {
			int[] counts0 = new int[120];
			int[] counts2 = new int[120];
			for (int i = 0; i < N; i++) {
				Permutation p0 = new Permutation(5);
				Permutation p2 = new Permutation(5, r2);
				int j0 = p0.toInteger();
				int j2 = p2.toInteger();
				counts0[j0]++;
				counts2[j2]++;
			}
			if (chiSquare(counts0) > 146.567) tooHigh0++;
			if (chiSquare(counts2) > 146.567) tooHigh2++;
		}
		if (!disableChiSquareTests) {
			assertTrue(tooHigh0 <= 10);
		}
		assertTrue(tooHigh2 <= 10);
	}
}
