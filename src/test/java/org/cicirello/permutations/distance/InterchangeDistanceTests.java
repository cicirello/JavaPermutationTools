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

/** JUnit tests for InterchangeDistance. */
public class InterchangeDistanceTests extends SharedTestForPermutationDistance {

  @Test
  public void testNormalized() {
    InterchangeDistance d = new InterchangeDistance();
    for (int n = 0; n <= 7; n++) {
      assertEquals(n <= 1 ? 0.0 : 1.0, validateNormalizedDistance(d, n), "Failed on length: " + n);
    }
  }

  @Test
  public void testMax() {
    InterchangeDistance d = new InterchangeDistance();
    for (int n = 0; n <= 7; n++) {
      int expected = bruteForceComputeMax(d, n);
      assertEquals(expected, d.max(n), "Failed on length: " + n);
      assertEquals(1.0 * expected, d.maxf(n), "Failed on length: " + n);
    }
  }

  @Test
  public void testIdenticalPermutations() {
    InterchangeDistance d = new InterchangeDistance();
    identicalPermutations(d);
  }

  @Test
  public void testInterchangeDistance() {
    InterchangeDistance d = new InterchangeDistance();
    int[] a1 = {0, 1, 2, 3, 4, 5};
    int[] a2 = {2, 4, 1, 0, 5, 3};
    int[] a3 = {5, 1, 2, 3, 4, 0};
    int[] a4 = {0, 3, 2, 1, 4, 5};
    int[] a5 = {2, 5, 4, 1, 0, 3};
    Permutation p1 = new Permutation(a1);
    Permutation p2 = new Permutation(a2);
    Permutation p3 = new Permutation(a3);
    Permutation p4 = new Permutation(a4);
    Permutation p5 = new Permutation(a5);

    assertEquals(5, d.distance(p1, p2));
    assertEquals(1, d.distance(p1, p3));
    assertEquals(1, d.distance(p1, p4));
    assertEquals(4, d.distance(p1, p5));
  }

  @Test
  public void testExceptions() {
    InterchangeDistance d = new InterchangeDistance();
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d.distance(new Permutation(1), new Permutation(2)));
  }
}
