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

/** JUnit tests for cyclic reversal independence. */
public class CyclicReversalIndependenceTests {

  @Test
  public void testCyclicReversalIndependentDistance() {
    ExactMatchDistance em = new ExactMatchDistance();
    CyclicReversalIndependentDistance d = new CyclicReversalIndependentDistance(em);
    int[] original = {0, 1, 2, 3, 4};
    int[] different = {3, 4, 0, 2, 1};

    Permutation p = new Permutation(original);
    Permutation[] rotated = new Permutation[original.length];
    Permutation[] reversed = new Permutation[original.length];
    rotated[0] = new Permutation(original);
    for (int i = 1; i < rotated.length; i++) {
      rotated[i] = new Permutation(original);
      rotated[i].rotate(i);
    }
    for (int i = 0; i < reversed.length; i++) {
      reversed[i] = new Permutation(rotated[i]);
      reversed[i].reverse();
    }
    for (int i = 0; i < rotated.length; i++) {
      assertEquals(0, d.distance(p, rotated[i]));
      assertEquals(0, d.distance(p, reversed[i]));
      assertEquals(0, d.distancef(p, rotated[i]));
      assertEquals(0, d.distancef(p, reversed[i]));
    }
    rotated[0] = new Permutation(different);
    for (int i = 1; i < rotated.length; i++) {
      rotated[i] = new Permutation(different);
      rotated[i].rotate(i);
    }
    for (int i = 0; i < reversed.length; i++) {
      reversed[i] = new Permutation(rotated[i]);
      reversed[i].reverse();
    }
    for (int i = 0; i < rotated.length; i++) {
      assertEquals(2, d.distance(p, rotated[i]));
      assertEquals(2, d.distance(p, reversed[i]));
      assertEquals(2, d.distancef(p, rotated[i]));
      assertEquals(2, d.distancef(p, reversed[i]));
    }
  }

  @Test
  public void testCyclicReversalIndependentDistanceDouble() {
    ExactMatchDistance em = new ExactMatchDistance();
    CyclicReversalIndependentDistanceDouble d = new CyclicReversalIndependentDistanceDouble(em);
    int[] original = {0, 1, 2, 3, 4};
    int[] different = {3, 4, 0, 2, 1};

    Permutation p = new Permutation(original);
    Permutation[] rotated = new Permutation[original.length];
    Permutation[] reversed = new Permutation[original.length];
    rotated[0] = new Permutation(original);
    for (int i = 1; i < rotated.length; i++) {
      rotated[i] = new Permutation(original);
      rotated[i].rotate(i);
    }
    for (int i = 0; i < reversed.length; i++) {
      reversed[i] = new Permutation(rotated[i]);
      reversed[i].reverse();
    }
    for (int i = 0; i < rotated.length; i++) {
      assertEquals(0, d.distancef(p, rotated[i]));
      assertEquals(0, d.distancef(p, reversed[i]));
    }
    rotated[0] = new Permutation(different);
    for (int i = 1; i < rotated.length; i++) {
      rotated[i] = new Permutation(different);
      rotated[i].rotate(i);
    }
    for (int i = 0; i < reversed.length; i++) {
      reversed[i] = new Permutation(rotated[i]);
      reversed[i].reverse();
    }
    for (int i = 0; i < rotated.length; i++) {
      assertEquals(2, d.distancef(p, rotated[i]));
      assertEquals(2, d.distancef(p, reversed[i]));
    }
  }
}
