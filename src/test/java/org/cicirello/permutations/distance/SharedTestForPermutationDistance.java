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

/** Shared by several test classes. */
public class SharedTestForPermutationDistance {

  final void identicalPermutations(PermutationDistanceMeasurer d) {
    for (int n = 0; n <= 10; n++) {
      Permutation p = new Permutation(n);
      Permutation copy = new Permutation(p);
      assertEquals(
          0,
          d.distance(p, copy),
          "distance of a permutation to itself should be 0; length was " + n);
      assertEquals(
          0, d.distance(p, p), "distance of a permutation to itself should be 0; length was " + n);
    }
  }

  final void reversalInvariance(PermutationDistanceMeasurer d) {
    for (int n = 0; n <= 10; n++) {
      Permutation p = new Permutation(n);
      Permutation copy = new Permutation(p);
      copy.reverse();
      assertEquals(0, d.distance(p, copy));
    }
  }

  final void rotationalInvariance(PermutationDistanceMeasurer d) {
    for (int n = 2; n <= 10; n++) {
      Permutation p = new Permutation(n);
      Permutation copy = new Permutation(p);
      for (int i = 1; i < n; i++) {
        copy.rotate(1);
        assertEquals(0, d.distance(p, copy));
      }
    }
  }

  final int bruteForceComputeMax(PermutationDistanceMeasurer d, int n) {
    int max = 0;
    Permutation p1 = new Permutation(n, 0);
    for (Permutation p2 : p1) {
      max = Math.max(max, d.distance(p1, p2));
    }
    return max;
  }

  final double validateNormalizedDistance(NormalizedPermutationDistanceMeasurer d, int n) {
    double max = 0;
    Permutation p1 = new Permutation(n, 0);
    for (Permutation p2 : p1) {
      max = Math.max(max, d.normalizedDistance(p1, p2));
    }
    return max;
  }
}
