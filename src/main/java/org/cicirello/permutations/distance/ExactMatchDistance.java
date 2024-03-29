/*
 * JavaPermutationTools - A Java library for computation on permutations.
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import org.cicirello.permutations.Permutation;

/**
 * Exact Match distance is an extension of Hamming distance but to non-binary strings, in this case,
 * permutations. It is the count of the number of positions for which the two permutations contain
 * different elements.
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * <p>Exact match distance was introduced in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press,
 * 1998, pp. 558–563.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class ExactMatchDistance implements NormalizedPermutationDistanceMeasurer {

  /** Constructs the distance measurer as specified in the class documentation. */
  public ExactMatchDistance() {}

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
    int misMatchPoints = 0;
    for (int i = 0; i < p1.length(); i++) {
      if (p1.get(i) != p2.get(i)) {
        misMatchPoints++;
      }
    }
    return misMatchPoints;
  }

  @Override
  public int max(int length) {
    if (length <= 1) return 0;
    return length;
  }
}
