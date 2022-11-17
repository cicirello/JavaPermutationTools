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
 * Implement this interface to define a distance metric for permutations that supports normalizing
 * the distance to the interval [0,1].
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public interface NormalizedPermutationDistanceMeasurerDouble
    extends PermutationDistanceMeasurerDouble {

  /**
   * Computes the maximum possible distance between permutations of a specified length.
   *
   * @param length Permutation length.
   * @return the maximum distance between a pair of permutations of the specified length.
   */
  double maxf(int length);

  /**
   * Measures the distance between two permutations, normalized to the interval [0.0, 1.0].
   *
   * @param p1 first permutation
   * @param p2 second permutation
   * @return distance between p1 and p2 normalized to the interval [0.0, 1.0]
   * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
   */
  default double normalizedDistance(Permutation p1, Permutation p2) {
    double m = maxf(p1.length());
    if (m == 0) return 0;
    return distancef(p1, p2) / m;
  }
}
