/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

/** Helpers shared by multiple test classes. */
public class SharedTestHelpersPermutation {

  final void validatePermutation(Permutation p, int n) {
    assertEquals(n, p.length());
    boolean[] inP = new boolean[n];
    for (int i = 0; i < p.length(); i++) {
      int el = p.get(i);
      assertTrue(el >= 0 && el < n);
      assertFalse(inP[el]);
      inP[el] = true;
    }
  }

  final double chiSquare(int[] buckets) {
    int x = 0;
    int n = 0;
    for (int e : buckets) {
      x = x + e * e;
      n += e;
    }
    return 1.0 * x / (n / buckets.length) - n;
  }
}
