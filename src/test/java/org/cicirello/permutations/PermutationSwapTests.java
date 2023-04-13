/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

/** JUnit tests for swap and swapBlocks methods. */
public class PermutationSwapTests {

  @Test
  public void testHashCodeInvalidation() {
    Permutation p1 = new Permutation(4, 0);
    Permutation p2 = new Permutation(4, 0);
    assertEquals(p1.hashCode(), p2.hashCode());
    p2.swap(1, 2);
    assertNotEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void testSwap() {
    for (int i = 1; i <= 5; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      p2.swap(0, i - 1);
      assertEquals(p.get(0), p2.get(i - 1));
      assertEquals(p.get(i - 1), p2.get(0));
      for (int j = 1; j < i - 1; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
    }
    for (int i = 1; i <= 5; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      p2.swap(i - 1, 0);
      assertEquals(p.get(0), p2.get(i - 1));
      assertEquals(p.get(i - 1), p2.get(0));
      for (int j = 1; j < i - 1; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
    }
    for (int i = 4; i <= 10; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      p2.swap(1, i - 2);
      assertEquals(p.get(1), p2.get(i - 2));
      assertEquals(p.get(i - 2), p2.get(1));
      for (int j = 2; j < i - 2; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
      assertEquals(p.get(0), p2.get(0));
      assertEquals(p.get(i - 1), p2.get(i - 1));
    }
    for (int i = 4; i <= 10; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      p2.swap(i - 2, 1);
      assertEquals(p.get(1), p2.get(i - 2));
      assertEquals(p.get(i - 2), p2.get(1));
      for (int j = 2; j < i - 2; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
      assertEquals(p.get(0), p2.get(0));
      assertEquals(p.get(i - 1), p2.get(i - 1));
    }
  }

  @Test
  public void testSwapBlocks() {
    int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    for (int i = 1; i < 11; i++) {
      for (int j = 1; i + j <= 11; j++) {
        Permutation p = new Permutation(a);
        int b = i - 1;
        int c = 11 - j;
        p.swapBlocks(0, b, c, 10);
        String s = "(0, " + b + ", " + c + ", 10)";
        int y = 0;
        for (int x = 11 - j; x <= 10; x++, y++) {
          assertEquals(a[x], p.get(y), "left block of result, params=" + s);
        }
        for (int x = i; x < 11 - j; x++, y++) {
          assertEquals(a[x], p.get(y), "interior of swapped blocks, params=" + s);
        }
        for (int x = 0; x < i; x++, y++) {
          assertEquals(a[x], p.get(y), "right block of result, params=" + s);
        }
      }
    }
    for (int i = 1; i < 11; i++) {
      for (int j = 1; i + j <= 9; j++) {
        Permutation p = new Permutation(a);
        int b = i;
        int c = 10 - j;
        p.swapBlocks(1, b, c, 9);
        String s = "(1, " + b + ", " + c + ", 9)";
        int y = 1;
        assertEquals(a[0], p.get(0));
        assertEquals(a[10], p.get(10));
        for (int x = c; x <= 9; x++, y++) {
          assertEquals(a[x], p.get(y), "left block of result, params=" + s);
        }
        for (int x = b + 1; x < c; x++, y++) {
          assertEquals(a[x], p.get(y), "interior of swapped blocks, params=" + s);
        }
        for (int x = 1; x <= b; x++, y++) {
          assertEquals(a[x], p.get(y), "right block of result, params=" + s);
        }
      }
    }
  }

  @Test
  public void testSwapBlocksExceptions() {
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class, () -> (new Permutation(10)).swapBlocks(-1, 3, 5, 7));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> (new Permutation(10)).swapBlocks(3, 2, 5, 7));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> (new Permutation(10)).swapBlocks(1, 3, 3, 7));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> (new Permutation(10)).swapBlocks(1, 3, 7, 6));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> (new Permutation(10)).swapBlocks(1, 3, 5, 10));
  }
}
