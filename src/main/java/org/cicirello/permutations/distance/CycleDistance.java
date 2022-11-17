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
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
 * Cycle distance is the count of the number of non-singleton permutation cycles between a pair of
 * permutations. Cycle distance is a semi-metric, satisfying all of the metric properties except for
 * the triangle inequality.
 *
 * <p>It was introduced in the following article:
 *
 * <p>Vincent A. Cicirello. 2022. <a
 * href="https://www.cicirello.org/publications/applsci-12-05506.pdf">Cycle Mutation: Evolving
 * Permutations via Cycle Induction</a>, <i>Applied Sciences</i>, 12(11), Article 5506 (June 2022).
 * doi:<a href="https://doi.org/10.3390/app12115506">10.3390/app12115506</a>
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class CycleDistance implements NormalizedPermutationDistanceMeasurer {

  /** Constructs the distance measurer as specified in the class documentation. */
  public CycleDistance() {}

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
   */
  @Override
  public int distance(Permutation p1, Permutation p2) {
    if (p1.length() != p2.length()) {
      throw new IllegalArgumentException("Permutations must be the same length");
    }
    boolean[] used = new boolean[p1.length()];
    for (int k = 0; k < used.length; k++) {
      if (p1.get(k) == p2.get(k)) {
        used[p1.get(k)] = true;
      }
    }
    int i = 0;
    for (i = 0; i < used.length; i++) {
      if (!used[p1.get(i)]) {
        break;
      }
    }

    int[] invP1 = p1.getInverse();
    int cycleCount = 0;
    int iLast = i;

    while (i < used.length) {
      int j = p1.get(i);
      while (!used[j]) {
        used[j] = true;
        j = p2.get(i);
        i = invP1[j];
      }
      cycleCount++;
      for (i = iLast + 1; i < used.length; i++) {
        if (!used[p1.get(i)]) {
          break;
        }
      }
      iLast = i;
    }
    return cycleCount;
  }

  @Override
  public int max(int length) {
    return length >> 1;
  }
}
