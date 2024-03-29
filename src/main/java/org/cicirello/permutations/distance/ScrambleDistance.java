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
 * Scramble Distance is the minimum number of random shufflings needed to transform one permutation
 * into the other. This was implemented for a very specific purpose, and unlikely to be subsequently
 * useful.
 *
 * <p>The scramble distance is 0 if permutation p1 is identical to p2. Otherwise, scramble distance
 * is 1.
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class ScrambleDistance implements NormalizedPermutationDistanceMeasurer {

  /** Constructs the distance measurer as specified in the class documentation. */
  public ScrambleDistance() {}

  @Override
  public int distance(Permutation p1, Permutation p2) {
    if (p1.equals(p2)) return 0;
    else return 1;
  }

  @Override
  public int max(int length) {
    if (length <= 1) return 0;
    return 1;
  }
}
