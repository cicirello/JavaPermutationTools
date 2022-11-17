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

/** JUnit tests for KendallTauDistance. */
public class KendallTauDistanceTests extends SharedTestForPermutationDistance {

  @Test
  public void testNormalized() {
    KendallTauDistance d = new KendallTauDistance();
    for (int n = 0; n <= 7; n++) {
      assertEquals(n <= 1 ? 0.0 : 1.0, validateNormalizedDistance(d, n), "Failed on length: " + n);
    }
  }

  @Test
  public void testMax() {
    KendallTauDistance d = new KendallTauDistance();
    for (int n = 0; n <= 7; n++) {
      int expected = bruteForceComputeMax(d, n);
      assertEquals(expected, d.max(n), "Failed on length: " + n);
      assertEquals(1.0 * expected, d.maxf(n), "Failed on length: " + n);
    }
  }

  @Test
  public void testIdenticalPermutations() {
    KendallTauDistance d = new KendallTauDistance();
    identicalPermutations(d);
  }

  @Test
  public void testKendallTauDistance() {
    KendallTauDistance d = new KendallTauDistance();
    for (int n = 2; n <= 10; n++) {
      // maximal distance is permutation reversed
      Permutation p = new Permutation(n);
      Permutation copy = new Permutation(p);
      copy.reverse();
      int expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(p, copy));
      copy.reverse();
      copy.swap(0, n - 1);
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(p, copy));
    }
    Permutation p = new Permutation(6);
    for (Permutation q : p) {
      assertEquals(naiveKendalTau(p, q), d.distance(p, q));
    }
  }

  @Test
  public void testExceptions() {
    KendallTauDistance d = new KendallTauDistance();
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> d.distance(new Permutation(1), new Permutation(2)));
  }

  private int naiveKendalTau(Permutation p1, Permutation p2) {
    int count = 0;
    int L1 = p1.length();

    int[] invP1 = p1.getInverse();
    int[] invP2 = p2.getInverse();

    for (int i = 0; i < L1 - 1; i++) {
      for (int j = i + 1; j < L1; j++) {
        if ((invP1[i] - invP1[j]) * (invP2[i] - invP2[j]) < 0) count++;
      }
    }

    return count;
  }
}
