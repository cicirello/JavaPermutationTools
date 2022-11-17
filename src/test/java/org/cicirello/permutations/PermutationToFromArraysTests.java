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

/** JUnit tests for Permutations to/from arrays. */
public class PermutationToFromArraysTests extends SharedTestHelpersPermutation {

  @Test
  public void testPermutationConstructorFromArray() {
    int[][] arrays = {{0}, {1, 0}, {1, 0, 2}, {3, 1, 2, 0}};
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
  }

  @Test
  public void testPermutationConstructorFromArrayExceptions() {
    final int[] negative = {1, 4, -1, 0, 3};
    final int[] tooHigh = {1, 4, 5, 0, 3};
    final int[] duplicate = {1, 4, 2, 1, 3};
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> new Permutation(negative));
    thrown = assertThrows(IllegalArgumentException.class, () -> new Permutation(tooHigh));
    thrown = assertThrows(IllegalArgumentException.class, () -> new Permutation(duplicate));
  }

  @Test
  public void testPermutationSetFromArray() {
    int[][] arrays = {{0}, {1, 0}, {1, 0, 2}, {3, 1, 2, 0}};
    for (int[] a : arrays) {
      Permutation p = new Permutation(a.length, 0);
      p.set(a.clone());
      validatePermutation(p, a.length);
      for (int i = 0; i < a.length; i++) {
        assertEquals(a[i], p.get(i));
      }
    }
  }

  @Test
  public void testPermutationSetFromArrayExceptions() {
    final int[] negative = {1, 4, -1, 0, 3};
    final int[] tooHigh = {1, 4, 5, 0, 3};
    final int[] duplicate = {1, 4, 2, 1, 3};
    final int[] tooLong = {1, 2, 4, 5, 0, 3};
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> (new Permutation(5)).set(negative));
    thrown = assertThrows(IllegalArgumentException.class, () -> (new Permutation(5)).set(tooHigh));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> (new Permutation(5)).set(duplicate));
    assertThrows(IllegalArgumentException.class, () -> (new Permutation(5)).set(tooLong));
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
        assertEquals(j - i + 1, a.length);
        for (int k = 0; k < a.length; k++) {
          assertEquals(init[i + k], a[k]);
        }
      }
    }
    for (int i = 0; i < init.length; i++) {
      for (int j = i; j < init.length; j++) {
        int[] result = new int[j - i + 1];
        int[] a = p.get(i, j, result);
        assertTrue(result == a);
        assertEquals(j - i + 1, a.length);
        for (int k = 0; k < a.length; k++) {
          assertEquals(init[i + k], a[k]);
        }
      }
    }
    for (int i = 0; i < init.length; i++) {
      for (int j = i; j < init.length; j++) {
        int[] result = null;
        int[] a = p.get(i, j, result);
        assertEquals(j - i + 1, a.length);
        for (int k = 0; k < a.length; k++) {
          assertEquals(init[i + k], a[k]);
        }
      }
    }
    for (int i = 0; i < init.length; i++) {
      for (int j = i; j < init.length; j++) {
        int[] result = new int[j - i + 2];
        int[] a = p.get(i, j, result);
        assertTrue(result != a);
        assertEquals(j - i + 1, a.length);
        for (int k = 0; k < a.length; k++) {
          assertEquals(init[i + k], a[k]);
        }
      }
    }
  }

  @Test
  public void testGetRangeExceptions() {
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> (new Permutation(3)).get(1, 0));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> (new Permutation(3)).get(1, 0, null));
  }
}
