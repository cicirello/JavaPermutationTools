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

import java.util.SplittableRandom;
import org.junit.jupiter.api.*;

/** JUnit tests for removing and reinserting. */
public class PermutationRemoveInsertTests {

  @Test
  public void testPermutationRemoveInsert() {
    Permutation p = new Permutation(5, new SplittableRandom(42));
    for (int i = 0; i < p.length(); i++) {
      for (int j = 0; j < p.length(); j++) {
        Permutation copy = new Permutation(p);
        assertEquals(p.hashCode(), copy.hashCode());
        copy.removeAndInsert(i, j);
        if (i < j) {
          for (int k = 0; k < i; k++) {
            assertEquals(p.get(k), copy.get(k));
          }
          for (int k = i; k < j; k++) {
            assertEquals(p.get(k + 1), copy.get(k));
          }
          assertEquals(p.get(i), copy.get(j));
          for (int k = j + 1; k < p.length(); k++) {
            assertEquals(p.get(k), copy.get(k));
          }
          assertNotEquals(p.hashCode(), copy.hashCode());
        } else if (i > j) {
          for (int k = 0; k < j; k++) {
            assertEquals(p.get(k), copy.get(k));
          }
          assertEquals(p.get(i), copy.get(j));
          for (int k = j + 1; k <= i; k++) {
            assertEquals(p.get(k - 1), copy.get(k));
          }
          for (int k = i + 1; k < p.length(); k++) {
            assertEquals(p.get(k), copy.get(k));
          }
          assertNotEquals(p.hashCode(), copy.hashCode());
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
    assertEquals(p.hashCode(), mutant.hashCode());
    mutant.removeAndInsert(7, 1, 0);
    assertEquals(p1, mutant);
    assertNotEquals(p.hashCode(), mutant.hashCode());
    Permutation p2 = new Permutation(a2);
    mutant = new Permutation(p);
    mutant.removeAndInsert(2, 1, 10);
    assertEquals(p2, mutant);
    Permutation p3 = new Permutation(a3);
    mutant = new Permutation(p);
    assertEquals(p.hashCode(), mutant.hashCode());
    mutant.removeAndInsert(7, 2, 0);
    assertEquals(p3, mutant);
    assertNotEquals(p.hashCode(), mutant.hashCode());
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
}
