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
 *
 */
package org.cicirello.permutations.distance;

import static org.junit.jupiter.api.Assertions.*;

import org.cicirello.permutations.Permutation;
import org.junit.jupiter.api.*;

/** JUnit tests for CycleDistance. */
public class CycleDistanceTests extends SharedTestForPermutationDistance {

  @Test
  public void testNormalized() {
    CycleDistance d = new CycleDistance();
    for (int n = 0; n <= 7; n++) {
      assertEquals(n <= 1 ? 0.0 : 1.0, validateNormalizedDistance(d, n), "Failed on length: " + n);
    }
  }

  @Test
  public void testMax() {
    CycleDistance d = new CycleDistance();
    for (int n = 0; n <= 7; n++) {
      int expected = bruteForceComputeMax(d, n);
      assertEquals(expected, d.max(n), "Failed on length: " + n);
      assertEquals(1.0 * expected, d.maxf(n), "Failed on length: " + n);
    }
  }

  @Test
  public void testIdenticalPermutations() {
    CycleDistance d = new CycleDistance();
    identicalPermutations(d);
  }

  @Test
  public void testCycleDistance() {
    CycleDistance d = new CycleDistance();
    int[][] cases = {
      {1, 0},
      {1, 2, 0},
      {1, 2, 3, 0},
      {1, 0, 3, 2},
      {1, 2, 3, 4, 5, 6, 7, 0},
      {7, 1, 2, 3, 4, 5, 6, 0},
      {7, 1, 5, 3, 4, 2, 6, 0},
      {7, 6, 5, 4, 3, 2, 1, 0},
      {2, 3, 4, 5, 0, 1, 7, 6},
      {15, 3, 4, 5, 6, 1, 7, 2, 9, 10, 11, 8, 13, 14, 12, 0}
    };
    int[] expected = {1, 1, 1, 2, 1, 1, 2, 4, 3, 5};
    for (int i = 0; i < expected.length; i++) {
      Permutation p1 = new Permutation(cases[i].length, 0);
      Permutation p2 = new Permutation(cases[i]);
      assertEquals(expected[i], d.distance(p1, p2));
      assertEquals(expected[i], d.distance(p2, p1));
    }
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d.distance(new Permutation(1), new Permutation(2)));
  }

  @Test
  public void testExceptions() {
    CycleDistance d = new CycleDistance();
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d.distance(new Permutation(1), new Permutation(2)));
  }
}
