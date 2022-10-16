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
 * JUnit tests for the applyThenValidate and apply methods of the Permutation class.
 */
public class PermutationOperatorsTests {
	
	@Test
	public void testIllegalPermutationStateExceptionConstructors() {
		IllegalPermutationStateException e1 = new IllegalPermutationStateException("test message");
		assertEquals("test message", e1.getMessage());
		assertNull(e1.getCause());
		e1 = new IllegalPermutationStateException("test message", new IllegalArgumentException());
		assertEquals("test message", e1.getMessage());
		assertTrue(e1.getCause() instanceof IllegalArgumentException);
		e1 = new IllegalPermutationStateException("test message", new IllegalPermutationStateException("testing 1 2 3"));
		assertEquals("test message", e1.getMessage());
		assertTrue(e1.getCause() instanceof IllegalPermutationStateException);
	}
	
	@Test
	public void testUnaryOperator() {
		Permutation p = new Permutation(10);
		p.apply(perm -> { for (int i = 0; i < perm.length; i++) { perm[i] = i; }});
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p.get(i));
		}
		p.apply(perm -> { for (int i = 0; i < perm.length; i++) { perm[perm.length-1-i] = i; }});
		for (int i = 0; i < 10; i++) {
			assertEquals(9-i, p.get(i));
		}
	}
	
	@Test
	public void testValidatedUnaryOperator() {
		final Permutation p = new Permutation(10);
		p.applyThenValidate(perm -> { for (int i = 0; i < perm.length; i++) { perm[i] = i; }});
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p.get(i));
		}
		p.applyThenValidate(perm -> { for (int i = 0; i < perm.length; i++) { perm[perm.length-1-i] = i; }});
		for (int i = 0; i < 10; i++) {
			assertEquals(9-i, p.get(i));
		}
		IllegalPermutationStateException thrown = assertThrows( 
			IllegalPermutationStateException.class,
			() -> p.applyThenValidate(perm -> { for (int i = 0; i < perm.length; i++) { perm[i] = 0; }})
		);
		assertTrue(thrown.getCause() instanceof IllegalArgumentException);
	}
	
	@Test
	public void testBinaryOperator() {
		Permutation p1 = new Permutation(10);
		Permutation p2 = new Permutation(10);
		p1.apply((perm1, perm2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = i; perm2[perm1.length-1-i] = i; }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p1.get(i));
			assertEquals(9-i, p2.get(i));
		}
		p1.apply((perm1, perm2) -> { for (int i = 0; i < perm1.length; i++) { perm2[i] = i; perm1[perm1.length-1-i] = i; }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p2.get(i));
			assertEquals(9-i, p1.get(i));
		}
	}
	
	@Test
	public void testValidatedBinaryOperator() {
		final Permutation p1 = new Permutation(10);
		final Permutation p2 = new Permutation(10);
		p1.applyThenValidate((perm1, perm2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = i; perm2[perm1.length-1-i] = i; }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p1.get(i));
			assertEquals(9-i, p2.get(i));
		}
		p1.applyThenValidate((perm1, perm2) -> { for (int i = 0; i < perm1.length; i++) { perm2[i] = i; perm1[perm1.length-1-i] = i; }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p2.get(i));
			assertEquals(9-i, p1.get(i));
		}
		IllegalPermutationStateException thrown = assertThrows( 
			IllegalPermutationStateException.class,
			() -> p1.applyThenValidate((perm1, perm2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = 0; perm2[perm1.length-1-i] = 0; }}, p2)
		);
		assertTrue(thrown.getCause() instanceof IllegalArgumentException);
	}
	
	@Test
	public void testFullUnaryOperator() {
		Permutation p = new Permutation(10);
		p.apply((perm, original) -> { for (int i = 0; i < perm.length; i++) { perm[i] = i; assertEquals(perm[i], original.get(i)); }});
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p.get(i));
		}
		p.apply((perm, original) -> { for (int i = 0; i < perm.length; i++) { perm[perm.length-1-i] = i; assertEquals(perm[perm.length-1-i], original.get(perm.length-1-i)); }});
		for (int i = 0; i < 10; i++) {
			assertEquals(9-i, p.get(i));
		}
	}
	
	@Test
	public void testValidatedFullUnaryOperator() {
		final Permutation p = new Permutation(10);
		p.applyThenValidate((perm, original) -> { for (int i = 0; i < perm.length; i++) { perm[i] = i; assertEquals(perm[i], original.get(i)); }});
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p.get(i));
		}
		p.applyThenValidate((perm, original) -> { for (int i = 0; i < perm.length; i++) { perm[perm.length-1-i] = i; assertEquals(perm[perm.length-1-i], original.get(perm.length-1-i)); }});
		for (int i = 0; i < 10; i++) {
			assertEquals(9-i, p.get(i));
		}
		IllegalPermutationStateException thrown = assertThrows( 
			IllegalPermutationStateException.class,
			() -> p.applyThenValidate((perm, original) -> { for (int i = 0; i < perm.length; i++) { perm[i] = 0; }})
		);
		assertTrue(thrown.getCause() instanceof IllegalArgumentException);
	}
	
	@Test
	public void testFullBinaryOperator() {
		Permutation p1 = new Permutation(10);
		Permutation p2 = new Permutation(10);
		p1.apply((perm1, perm2, o1, o2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = i; perm2[perm1.length-1-i] = i; assertEquals(i, o1.get(i)); assertEquals(i, o2.get(9-i)); }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p1.get(i));
			assertEquals(9-i, p2.get(i));
		}
		p1.apply((perm1, perm2, o1, o2) -> { for (int i = 0; i < perm1.length; i++) { perm2[i] = i; perm1[perm1.length-1-i] = i; assertEquals(i, o2.get(i)); assertEquals(i, o1.get(9-i)); }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p2.get(i));
			assertEquals(9-i, p1.get(i));
		}
	}
	
	@Test
	public void testValidatedFullBinaryOperator() {
		final Permutation p1 = new Permutation(10);
		final Permutation p2 = new Permutation(10);
		p1.applyThenValidate((perm1, perm2, o1, o2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = i; perm2[perm1.length-1-i] = i; assertEquals(i, o1.get(i)); assertEquals(i, o2.get(9-i)); }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p1.get(i));
			assertEquals(9-i, p2.get(i));
		}
		p1.applyThenValidate((perm1, perm2, o1, o2) -> { for (int i = 0; i < perm1.length; i++) { perm2[i] = i; perm1[perm1.length-1-i] = i; assertEquals(i, o2.get(i)); assertEquals(i, o1.get(9-i)); }}, p2);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, p2.get(i));
			assertEquals(9-i, p1.get(i));
		}
		IllegalPermutationStateException thrown = assertThrows( 
			IllegalPermutationStateException.class,
			() -> p1.applyThenValidate((perm1, perm2, o1, o2) -> { for (int i = 0; i < perm1.length; i++) { perm1[i] = 0; perm2[perm1.length-1-i] = 0; }}, p2)
		);
		assertTrue(thrown.getCause() instanceof IllegalArgumentException);
	}
}
