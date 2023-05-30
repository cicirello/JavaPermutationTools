/*
 * JavaPermutationTools - A Java library for computation on permutations.
 * Copyright 2005-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * This is an implementation of Wagner and Fischer's dynamic programming algorithm for computing
 * string edit distance, but adapted to permutations rather than general strings.
 *
 * <p>Edit distance is the minimum cost to transform one permutation into the other, which is the
 * sum of the costs of the edit operations necessary to do so. This edit distance considers 3 edit
 * operations: Inserts which insert a new element into the permutation, Deletes which remove an
 * element from the permutation, and Changes which replace an element with a different element.
 *
 * <p>The edit distance is parameterized by costs for the edit operations. We provide one
 * constructor which enables you to specify 3 costs, 1 for each type of edit operation.
 *
 * <p>And we also provide a default constructor, which assigns a cost of 0.5 for insert, 0.5 for
 * delete, and which assigns an infinite cost to changes, essentially disallowing that 3rd type of
 * edit operation. We chose this as the default as it is equivalent to counting the number of
 * reinsertion operations necessary to transform one permutation into the other, known as
 * Reinsertion Distance. A reinsertion operation removes an element and reinserts it in a different
 * position, and is treated as a single composite operation. However, the {@link
 * ReinsertionDistance} class provides an implementation of a specialized algorithm that is more
 * efficient for this special case.
 *
 * <p>Runtime: O(n<sup>2</sup>), where n is the permutation length.
 *
 * <p>Wagner and Fischer's String Edit Distance was introduced in:<br>
 * R. A. Wagner and M. J. Fischer, "The string-to-string correction problem," Journal of the ACM,
 * vol. 21, no. 1, pp. 168–173, January 1974.
 *
 * <p>Its application as a means of computing Reinsertion Distance is first described in:<br>
 * V. A. Cicirello and R. Cernera, <a
 * href="https://www.cicirello.org/publications/cicirello2013flairs.html" target=_top>"Profiling the
 * distance characteristics of mutation operators for permutation-based genetic algorithms,"</a> in
 * Proceedings of the 26th FLAIRS Conference. AAAI Press, May 2013, pp. 46–51.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class EditDistance implements PermutationDistanceMeasurerDouble {

  private final double insertCost;
  private final double deleteCost;
  private final double changeCost;

  /**
   * Constructs an EditDistance function.
   *
   * @param insertCost Cost of an insertion operation.
   * @param deleteCost Cost of a deletion operation.
   * @param changeCost Cost of a change operation.
   * @throws IllegalArgumentException if any of the costs are negative.
   */
  public EditDistance(double insertCost, double deleteCost, double changeCost) {
    if (insertCost < 0.0 || deleteCost < 0.0 || changeCost < 0.0) {
      throw new IllegalArgumentException("Costs must be non-negative.");
    }
    this.insertCost = insertCost;
    this.deleteCost = deleteCost;
    this.changeCost = changeCost;
  }

  /**
   * Default edit distance computes number of remove and reinsert operations to transform one
   * permutation into the other. And does not use change operations.
   */
  public EditDistance() {
    insertCost = deleteCost = 0.5;
    changeCost = 1.0;
  }

  /**
   * Measures the distance between two permutations.
   *
   * @param p1 first permutation
   * @param p2 second permutation
   * @return distance between p1 and p2
   */
  @Override
  public double distancef(Permutation p1, Permutation p2) {
    int n = p1.length();
    int m = p2.length();
    if (n == m && n <= 1) return 0;
    double[][] D = new double[n + 1][m + 1];
    for (int i = 1; i <= n; i++) {
      D[i][0] = D[i - 1][0] + deleteCost;
    }
    for (int j = 1; j <= m; j++) {
      D[0][j] = D[0][j - 1] + insertCost;
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        D[i][j] =
            min(
                p1.get(i - 1) == p2.get(j - 1) ? D[i - 1][j - 1] : D[i - 1][j - 1] + changeCost,
                D[i - 1][j] + deleteCost,
                D[i][j - 1] + insertCost);
      }
    }
    return D[n][m];
  }

  private double min(double m1, double m2, double m3) {
    return Math.min(m1 < m2 ? m1 : m2, m3);
  }
}
