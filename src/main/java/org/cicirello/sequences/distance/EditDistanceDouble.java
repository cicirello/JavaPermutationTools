/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences.distance;

import java.util.List;

/**
 * EditDistanceDouble is an implementation of Wagner and Fischer's dynamic programming algorithm for
 * computing string edit distance. This class supports double-valued costs, while the {@link
 * EditDistance} class supports int-valued costs. If your costs are int-valued, the {@link
 * EditDistance} class may be slightly faster, but not asymptotically faster.
 *
 * <p>Edit distance is the minimum cost to transform one string (or sequence) into the other, which
 * is the sum of the costs of the edit operations necessary to do so. This edit distance considers 3
 * edit operations: Inserts which insert a new element into the sequence, Deletes which remove an
 * element from the sequence, and Changes which replace an element with a different element.
 *
 * <p>The edit distance is parameterized by costs for the edit operations. The constructor enables
 * you to specify 3 costs as values of type double, 1 for each type of edit operation.
 *
 * <p>This class supports computing edit distance for Java String objects or arrays of any of the
 * primitive types, or arrays of objects. It makes no assumptions about the contents of the Strings
 * or arrays, and they can contain duplicates, or can be such that some elements only appear in one
 * or the other of the sequences, or can be of different lengths.
 *
 * <p>Another class, {@link org.cicirello.permutations.distance.EditDistance}, is available if you
 * need to compute distance specifically between permutations, rather than general sequences. That
 * class computes distance between permutations of the integers from 0 to N-1.
 *
 * <p>Runtime: O(n*m), where n and m are the lengths of the two sequences (i.e., Strings or arrays).
 *
 * <p>Wagner and Fischer's String Edit Distance was introduced in:<br>
 * R. A. Wagner and M. J. Fischer, "The string-to-string correction problem," Journal of the ACM,
 * vol. 21, no. 1, pp. 168–173, January 1974.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public class EditDistanceDouble implements SequenceDistanceMeasurerDouble {

  private final double insert_d;
  private final double delete_d;
  private final double change_d;

  /**
   * Constructs an edit distance measure with the specified edit operation costs.
   *
   * @param insertCost Cost of an insertion operation. Must be non-negative.
   * @param deleteCost Cost of an deletion operation. Must be non-negative.
   * @param changeCost Cost of an change operation. Must be non-negative.
   * @throws IllegalArgumentException if any of the costs are negative.
   */
  public EditDistanceDouble(double insertCost, double deleteCost, double changeCost) {
    if (insertCost < 0.0 || deleteCost < 0.0 || changeCost < 0.0)
      throw new IllegalArgumentException("Costs must be non-negative.");
    insert_d = insertCost;
    delete_d = deleteCost;
    change_d = changeCost;
  }

  @Override
  public final double distancef(int[] s1, int[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(long[] s1, long[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(short[] s1, short[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(byte[] s1, byte[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(char[] s1, char[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(boolean[] s1, boolean[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(double[] s1, double[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(float[] s1, float[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(String s1, String s2) {
    return distancef(
        s1.length(),
        s2.length(),
        (i, j, costIfSame) -> s1.charAt(i) == s2.charAt(j) ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final double distancef(Object[] s1, Object[] s2) {
    return distancef(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i].equals(s2[j]) ? costIfSame : costIfSame + change_d);
  }

  @Override
  public final <T> double distancef(List<T> s1, List<T> s2) {
    return distancef(s1.toArray(), s2.toArray());
  }

  @FunctionalInterface
  private interface ChangeCost {
    /**
     * Computes cost of change.
     *
     * @param i Index into first sequence.
     * @param j Index into second sequence.
     * @param costIfSame The cost if the elements are the same.
     * @return Cost for a change.
     */
    double apply(int i, int j, double valueIfSame);
  }

  private double distancef(int n, int m, ChangeCost coster) {
    double[][] D = initD(n, m);
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        D[i][j] =
            min(
                coster.apply(i - 1, j - 1, D[i - 1][j - 1]),
                D[i - 1][j] + delete_d,
                D[i][j - 1] + insert_d);
      }
    }
    return D[n][m];
  }

  private double[][] initD(int n, int m) {
    double[][] D = new double[n + 1][m + 1];
    for (int i = 1; i <= n; i++) {
      D[i][0] = D[i - 1][0] + delete_d;
    }
    for (int j = 1; j <= m; j++) {
      D[0][j] = D[0][j - 1] + insert_d;
    }
    return D;
  }

  private double min(double m1, double m2, double m3) {
    if (m2 < m1) m1 = m2;
    return m3 < m1 ? m3 : m1;
  }
}
