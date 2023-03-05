/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences.
 * Copyright (C) 2018-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import java.util.Arrays;
import org.cicirello.permutations.Permutation;

/**
 * This class implements the weighted Kendall tau distance. In the original Kendall tau distance,
 * each inverted pair of elements (i.e., such that element x appears someplace before y in
 * Permutation p1, but someplace after y in Permutation p2) contributes 1 to the distance. Thus,
 * since there are n(n-1)/2 pairs of elements, the maximum of Kendall tau distance is n(n-1)/2 where
 * n is the permutation length. In this weighted Kendall tau distance, each element x of the
 * permutation has an associated weight w(x), and each inverted pair x, y (where x appears before
 * sometime prior to y in p1, but sometime after y in p2) contributes w(x) * w(y) to the weighted
 * Kendall tau distance.
 *
 * <p>The weighted Kendall tau distance was first described in:<br>
 * "Failure proximity: a fault localization-based approach" (Liu and Han, SIGSOFT 2006, pages
 * 46-56).
 *
 * <p>The runtime of JPT's implementation is O(n lg n), where n is the permutation length. This
 * runtime is achieved using a modified version of mergesort to sum the weighted inversions.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class WeightedKendallTauDistance
    implements NormalizedPermutationDistanceMeasurerDouble {

  private final double[] weights;
  private final double maxDistance;

  /**
   * Constructs an instance of the WeightedKendallTauDistance.
   *
   * @param weights An array of weights, such that weights[e] is the weight of element e.
   */
  public WeightedKendallTauDistance(double[] weights) {
    this.weights = weights.clone();
    double max = 0;
    for (int i = 0; i < weights.length - 1; i++) {
      double runningSum = 0;
      for (int j = i + 1; j < weights.length; j++) {
        runningSum += weights[j];
      }
      max += weights[i] * runningSum;
    }
    maxDistance = max;
  }

  /**
   * Gets the length of permutations supported by this instance of WeightedKendallTauDistance, which
   * is equal to the length of the array of weights passed to the constructor.
   *
   * @return The length of supported Permutations.
   */
  public int supportedLength() {
    return weights.length;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if p1.length() is not equal to supportedLength(), or if
   *     p2.length() is not equal to supportedLength().
   */
  @Override
  public double distancef(Permutation p1, Permutation p2) {
    if (p1.length() != weights.length || p2.length() != weights.length) {
      throw new IllegalArgumentException("p1 and/or p2 not of supported length of this instance");
    }
    // use inverse of p1 as a relabeling
    int[] invP1 = p1.getInverse();

    // relabel array copy of p2 and likewise map weights to weights of relabeled copy
    int[] arrayP2 = new int[invP1.length];
    double[] w = new double[weights.length];
    for (int i = 0; i < arrayP2.length; i++) {
      arrayP2[i] = invP1[p2.get(i)];
      w[arrayP2[i]] = weights[p2.get(i)];
    }

    return countWeightedInversions(arrayP2, w, 0, arrayP2.length - 1);
  }

  /**
   * {@inheritDoc}
   *
   * <p><b>This implementation ignores the length parameter since this distance is configured for
   * one specific length based upon the weights passed during construction.</b>
   */
  @Override
  public double maxf(int length) {
    return maxDistance;
  }

  private double countWeightedInversions(int[] array, double[] w, int first, int last) {
    if (last <= first) {
      return 0;
    }
    int m = (first + last) >> 1;
    return countWeightedInversions(array, w, first, m)
        + countWeightedInversions(array, w, m + 1, last)
        + merge(array, w, first, m + 1, last + 1);
  }

  private double merge(int[] array, double[] w, int first, int midPlus, int lastPlus) {
    int[] left = Arrays.copyOfRange(array, first, midPlus);
    int[] right = Arrays.copyOfRange(array, midPlus, lastPlus);
    int i = 0;
    int j = 0;
    int k = first;
    double weightedCount = 0;
    double leftWeights = 0;
    for (int x = 0; x < left.length; x++) {
      leftWeights += w[left[x]];
    }
    while (i < left.length && j < right.length) {
      if (left[i] < right[j]) {
        leftWeights -= w[left[i]];
        array[k] = left[i];
        i++;
        k++;
      } else {
        // inversions
        weightedCount += w[right[j]] * leftWeights;
        array[k] = right[j];
        j++;
        k++;
      }
    }
    System.arraycopy(left, i, array, k, left.length - i);
    System.arraycopy(right, j, array, k, right.length - j);
    return weightedCount;
  }
}
