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
 * RType distance treats the permutations as if they represent sets of directed edges, and counts
 * the number of edges that differ.
 *
 * <p>Consider the example permutation: [1, 5, 2, 4, 0, 3]. RType distance treats this as equivalent
 * to the set of directed edges: {(1,5), (5,2), (2,4), (4,0), (0,3)}.
 *
 * <p>E.g., distance between [1, 5, 2, 4, 0, 3] and [ 5, 1, 4, 0, 3, 2] is 3. Why? Well, the first
 * permutation has the directed edges: {(1,5), (5,2), (2,4), (4,0), (0,3)}. The second has 2 of
 * these (4,0), and (0,3), but does not include 3 of the edges: (1,5), (5,2), (2,4)
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * <p>RType distance was introduced in:<br>
 * V. Campos, M. Laguna, and R. Marti, "Context-independent scatter and tabu search for permutation
 * problems," INFORMS Journal on Computing, vol. 17, no. 1, pp. 111–122, 2005.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class RTypeDistance implements NormalizedPermutationDistanceMeasurer {

  /** Constructs the distance measurer as specified in the class documentation. */
  public RTypeDistance() {}

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
    int countNonSharedEdges = 0;
    if (p2.length() == 0) return 0;
    int[] successors2 = new int[p2.length()];
    for (int i = 0; i < successors2.length - 1; i++) {
      successors2[p2.get(i)] = p2.get(i + 1);
    }
    successors2[p2.get(successors2.length - 1)] = -1;

    for (int i = 0; i < successors2.length - 1; i++) {
      if (p1.get(i + 1) != successors2[p1.get(i)]) countNonSharedEdges++;
    }
    return countNonSharedEdges;
  }

  @Override
  public int max(int length) {
    if (length <= 1) return 0;
    return length - 1;
  }
}
