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
 * Squared Deviation distance is the sum of the squares of the positional deviations of the
 * permutation elements. The positional deviation of an element is the difference in its location in
 * the two permutations.
 *
 * <p>For example, consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [1, 0, 5, 2, 4, 3]. Element 0 is
 * displaced by 1 position. Likewise for elements 1 and 2. Element 3 is displaced by 2 positions.
 * Element 4 is in the same position in both. Element 5 is displaced by 3 positions.
 *
 * <p>Sum the squared deviations: 1^2 + 1^2 + 1^2 + 2^2 + 0^2 + 3^2 = 1 + 1 + 1 + 4 + 9 = 16.
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * <p>Squared deviation distance is described in:<br>
 * M. Sevaux and K. Sorensen, "Permutation distance measures for memetic algorithms with population
 * management," The 6th Metaheuristics International Conference, August, 2005.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class SquaredDeviationDistance implements NormalizedPermutationDistanceMeasurer {

  /** Constructs the distance measurer as specified in the class documentation. */
  public SquaredDeviationDistance() {}

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
    int distancePoints = 0;

    int[] invP2 = p2.getInverse();

    for (int i = 0; i < invP2.length; i++) {
      int dev = invP2[p1.get(i)] - i;
      distancePoints += (dev * dev);
    }
    return distancePoints;
  }

  @Override
  public int max(int length) {
    if (length <= 1) return 0;
    return (length * length * length - length) / 3;
  }
}
