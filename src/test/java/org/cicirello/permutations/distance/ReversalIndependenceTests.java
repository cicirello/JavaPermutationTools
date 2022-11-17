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

/** JUnit tests for reversal independence. */
public class ReversalIndependenceTests {

  @Test
  public void testReversalIndependentDistance() {
    ExactMatchDistance em = new ExactMatchDistance();
    ReversalIndependentDistance d = new ReversalIndependentDistance(em);
    int[] original = {0, 1, 2, 3, 4, 5, 6};
    int[] other = {0, 5, 4, 3, 2, 1, 6};
    int[] other2 = {6, 1, 2, 3, 4, 5, 0};
    int[] reversed = {6, 5, 4, 3, 2, 1, 0};
    Permutation p1 = new Permutation(original);
    Permutation p2 = new Permutation(original);
    Permutation pr = new Permutation(reversed);
    Permutation p4to2 = new Permutation(other);
    Permutation p2to4 = new Permutation(other2);
    assertEquals(0, d.distance(p1, p2));
    assertEquals(0, d.distance(p1, pr));
    assertEquals(0, d.distance(pr, p2));
    assertEquals(2, d.distance(p1, p4to2));
    assertEquals(2, d.distance(p4to2, p1));
    assertEquals(2, d.distance(p1, p2to4));
    assertEquals(2, d.distance(p2to4, p1));
    assertEquals(0, d.distancef(p1, p2));
    assertEquals(0, d.distancef(p1, pr));
    assertEquals(0, d.distancef(pr, p2));
    assertEquals(2, d.distancef(p1, p4to2));
    assertEquals(2, d.distancef(p4to2, p1));
    assertEquals(2, d.distancef(p1, p2to4));
    assertEquals(2, d.distancef(p2to4, p1));
  }

  @Test
  public void testReversalIndependentDistanceDouble() {
    ExactMatchDistance em = new ExactMatchDistance();
    ReversalIndependentDistanceDouble d = new ReversalIndependentDistanceDouble(em);
    int[] original = {0, 1, 2, 3, 4, 5, 6};
    int[] other = {0, 5, 4, 3, 2, 1, 6};
    int[] other2 = {6, 1, 2, 3, 4, 5, 0};
    int[] reversed = {6, 5, 4, 3, 2, 1, 0};
    Permutation p1 = new Permutation(original);
    Permutation p2 = new Permutation(original);
    Permutation pr = new Permutation(reversed);
    Permutation p4to2 = new Permutation(other);
    Permutation p2to4 = new Permutation(other2);
    assertEquals(0, d.distancef(p1, p2));
    assertEquals(0, d.distancef(p1, pr));
    assertEquals(0, d.distancef(pr, p2));
    assertEquals(2, d.distancef(p1, p4to2));
    assertEquals(2, d.distancef(p4to2, p1));
    assertEquals(2, d.distancef(p1, p2to4));
    assertEquals(2, d.distancef(p2to4, p1));
  }
}
