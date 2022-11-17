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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/** JUnit tests for inverting a Permutation. */
public class PermutationInverseTests {

  @Test
  public void testGetInverse0() {
    Permutation p1 = new Permutation(0);
    assertEquals(
        0, p1.getInverse().length, "length of inverse of zero length permutation should be 0");
  }

  @Test
  public void testGetInverse() {
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
  public void testGetInversePermutation() {
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
  public void testInvert() {
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
}
