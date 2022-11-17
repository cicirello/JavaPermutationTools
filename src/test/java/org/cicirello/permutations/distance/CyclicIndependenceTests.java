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

/** JUnit tests for cyclic independence. */
public class CyclicIndependenceTests {

  @Test
  public void testCyclicIndependentDistance() {
    ExactMatchDistance em = new ExactMatchDistance();
    CyclicIndependentDistance d = new CyclicIndependentDistance(em);
    int[] original = {0, 1, 2, 3};
    int[] different = {0, 2, 1, 3};
    Permutation p1 = new Permutation(original);
    Permutation p2 = new Permutation(original);
    Permutation pd = new Permutation(different);
    Permutation pr1 = new Permutation(p1);
    pr1.rotate(1);
    Permutation pr2 = new Permutation(p1);
    pr1.rotate(2);
    Permutation pr3 = new Permutation(p1);
    pr1.rotate(3);
    assertEquals(0, d.distance(p1, p2));
    assertEquals(0, d.distance(p1, pr1));
    assertEquals(0, d.distance(p1, pr2));
    assertEquals(0, d.distance(p1, pr3));
    assertEquals(2, d.distance(pd, p2));
    assertEquals(2, d.distance(pd, pr1));
    assertEquals(2, d.distance(pd, pr2));
    assertEquals(2, d.distance(pd, pr3));

    assertEquals(0, d.distancef(p1, p2));
    assertEquals(0, d.distancef(p1, pr1));
    assertEquals(0, d.distancef(p1, pr2));
    assertEquals(0, d.distancef(p1, pr3));
    assertEquals(2, d.distancef(pd, p2));
    assertEquals(2, d.distancef(pd, pr1));
    assertEquals(2, d.distancef(pd, pr2));
    assertEquals(2, d.distancef(pd, pr3));
  }

  @Test
  public void testCyclicIndependentDistanceDouble() {
    ExactMatchDistance em = new ExactMatchDistance();
    CyclicIndependentDistanceDouble d = new CyclicIndependentDistanceDouble(em);
    int[] original = {0, 1, 2, 3};
    int[] different = {0, 2, 1, 3};
    Permutation p1 = new Permutation(original);
    Permutation p2 = new Permutation(original);
    Permutation pd = new Permutation(different);
    Permutation pr1 = new Permutation(p1);
    pr1.rotate(1);
    Permutation pr2 = new Permutation(p1);
    pr1.rotate(2);
    Permutation pr3 = new Permutation(p1);
    pr1.rotate(3);
    assertEquals(0, d.distancef(p1, p2));
    assertEquals(0, d.distancef(p1, pr1));
    assertEquals(0, d.distancef(p1, pr2));
    assertEquals(0, d.distancef(p1, pr3));
    assertEquals(2, d.distancef(pd, p2));
    assertEquals(2, d.distancef(pd, pr1));
    assertEquals(2, d.distancef(pd, pr2));
    assertEquals(2, d.distancef(pd, pr3));
  }
}
