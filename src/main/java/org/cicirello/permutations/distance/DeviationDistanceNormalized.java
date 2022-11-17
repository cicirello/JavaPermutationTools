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
 * Normalized Deviation distance is the sum of the positional deviation of the permutation elements
 * divided by N-1 (where N is the length of the permutation). The positional deviation of an element
 * is the difference in its location in the two permutations. Normalizing by dividing by N-1 causes
 * each element's contribution to distance to be in the interval [0,1].
 *
 * <p>For example, consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [1, 0, 5, 2, 4, 3]. Element 0 is
 * displaced by 1 position. Likewise for elements 1 and 2. Element 3 is displaced by 2 positions.
 * Element 4 is in the same position in both. Element 5 is displaced by 3 positions.
 *
 * <p>Sum the deviations: 1 + 1 + 1 + 2 + 0 + 3 = 8.
 *
 * <p>The length is 6. So, normalized deviation distance is 8 / (6-1) = 1.6.
 *
 * <p>Runtime: O(n), where n is the permutation length.
 *
 * <p>Normalized deviation distance was introduced in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press,
 * 1998, pp. 558–563.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class DeviationDistanceNormalized
    implements NormalizedPermutationDistanceMeasurerDouble {

  private DeviationDistance devDistance;

  /** Constructs the distance measurer as specified in the class documentation. */
  public DeviationDistanceNormalized() {
    devDistance = new DeviationDistance();
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
   */
  @Override
  public double distancef(Permutation p1, Permutation p2) {
    if (p1.length() != p2.length()) {
      throw new IllegalArgumentException("Permutations must be the same length");
    }
    if (p1.length() <= 1) return 0;
    return devDistance.distancef(p1, p2) / (p1.length() - 1);
  }

  @Override
  public double maxf(int length) {
    if (length <= 1) return 0;
    return (length * length - (length & 1)) / (2.0 * (length - 1));
  }
}
