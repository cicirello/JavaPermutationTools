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

/** JUnit tests for ReversalDistance. */
public class ReversalDistanceTests {

  @Test
  public void testMax() {
    ReversalDistance d = new ReversalDistance(0);
    assertEquals(0, d.max(0));
    d = new ReversalDistance(1);
    assertEquals(0, d.max(1));
    d = new ReversalDistance(2);
    assertEquals(1, d.max(2));
    d = new ReversalDistance(3);
    assertEquals(2, d.max(3));
    d = new ReversalDistance(4);
    assertEquals(3, d.max(4));
  }

  @Test
  public void testReversalDistance() {
    ReversalDistance d4 = new ReversalDistance(4);
    int[] a4 = {0, 1, 2, 3};
    int[] a4_2 = {1, 0, 3, 2};
    Permutation p4 = new Permutation(a4);
    Permutation p4_2 = new Permutation(a4_2);
    assertEquals(2, d4.distance(p4, p4_2));
    ReversalDistance d5 = new ReversalDistance(5);
    int[] a5 = {0, 1, 4, 2, 3};
    int[] a5_2 = {1, 0, 4, 3, 2};
    Permutation p5 = new Permutation(a5);
    Permutation p5_2 = new Permutation(a5_2);
    assertEquals(2, d5.distance(p5, p5_2));

    ReversalDistance d = new ReversalDistance();
    assertEquals(2, d.distance(p5, p5_2));
    assertEquals(4, d.max(5));

    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d5.distance(new Permutation(5), new Permutation(6)));
    thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d5.distance(new Permutation(6), new Permutation(6)));
    thrown = assertThrows(IllegalArgumentException.class, () -> new ReversalDistance(-1));
    thrown = assertThrows(IllegalArgumentException.class, () -> new ReversalDistance(13));
  }
}
