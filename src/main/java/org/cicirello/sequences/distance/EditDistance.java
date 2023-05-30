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
 * EditDistance is an implementation of Wagner and Fischer's dynamic programming algorithm for
 * computing string edit distance.
 *
 * <p>Edit distance is the minimum cost to transform one string (or sequence) into the other, which
 * is the sum of the costs of the edit operations necessary to do so. This edit distance considers 3
 * edit operations: Inserts which inserts a new element into the sequence, Deletes which removes an
 * element from the sequence, and Changes which replace an element with a different element.
 *
 * <p>The edit distance is parameterized by costs for the edit operations. The class provides a
 * constructor which enables you to specify 3 costs as ints, 1 for each type of edit operation. If
 * your application requires non-integer costs, then use the {@link EditDistanceDouble} class which
 * defines the costs as doubles, but is otherwise an implementation of the same algorithm.
 *
 * <p>This class supports computing EditDistance for Java String objects or arrays of any of the
 * primitive types, or arrays of objects. It makes no assumptions about the contents of the Strings
 * or arrays, and they can contain duplicates, or can be such that some elements only appear in one
 * or the other of the sequences, or can be of different lengths.
 *
 * <p>Another class (with same name but in different package) is available if you need to compute
 * distance specifically between permutations, rather than general sequences. That class is the
 * {@link org.cicirello.permutations.distance.EditDistance} class which computes distance between
 * permutations of the integers from 0 to N-1.
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
public class EditDistance extends EditDistanceDouble implements SequenceDistanceMeasurer {

  private final int insert_i;
  private final int delete_i;
  private final int change_i;

  /**
   * Constructs an edit distance measure with the specified edit operation costs. With costs as
   * doubles, all of the distancef methods that compute distance as double values are available. The
   * distance methods that compute integer-valued distances may or may not be available if this
   * constructor is used with double valued costs. If the costs are equal to integer values if cast
   * to type double, then the distance methods will also function. Otherwise, they will throw an
   * exception. For safety, it is recommended to only use the distancef methods if you use this
   * constructor to pass costs as type double. If you desire integer valued distances, then use the
   * other constructor to pass costs as ints.
   *
   * @param insertCost Cost of an insertion operation. Must be non-negative.
   * @param deleteCost Cost of an deletion operation. Must be non-negative.
   * @param changeCost Cost of an change operation. Must be non-negative.
   * @throws IllegalArgumentException if any of the costs are negative.
   * @deprecated For double-valued costs, use the {@link EditDistanceDouble} class instead.
   */
  @Deprecated
  public EditDistance(double insertCost, double deleteCost, double changeCost) {
    super(insertCost, deleteCost, changeCost);
    if (isIntAsDouble(insertCost) && isIntAsDouble(deleteCost) && isIntAsDouble(changeCost)) {
      insert_i = (int) insertCost;
      delete_i = (int) deleteCost;
      change_i = (int) changeCost;
    } else {
      // Use -1 on these to disallow use of distance (the one that returns an int)... make sure to
      // throw an exception there
      insert_i = delete_i = change_i = -1;
    }
  }

  /**
   * Constructs an edit distance measure with the specified edit operation costs.
   *
   * @param insertCost Cost of an insertion operation. Must be non-negative.
   * @param deleteCost Cost of an deletion operation. Must be non-negative.
   * @param changeCost Cost of an change operation. Must be non-negative.
   * @throws IllegalArgumentException if any of the costs are negative.
   */
  public EditDistance(int insertCost, int deleteCost, int changeCost) {
    super(insertCost, deleteCost, changeCost);
    insert_i = insertCost;
    delete_i = deleteCost;
    change_i = changeCost;
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(int[] s1, int[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(long[] s1, long[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(short[] s1, short[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(byte[] s1, byte[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(char[] s1, char[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(boolean[] s1, boolean[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(double[] s1, double[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(float[] s1, float[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i] == s2[j] ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(String s1, String s2) {
    return distance(
        s1.length(),
        s2.length(),
        (i, j, costIfSame) -> s1.charAt(i) == s2.charAt(j) ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final int distance(Object[] s1, Object[] s2) {
    return distance(
        s1.length,
        s2.length,
        (i, j, costIfSame) -> s1[i].equals(s2[j]) ? costIfSame : costIfSame + change_i);
  }

  /**
   * {@inheritDoc}
   *
   * @throws UnsupportedOperationException if costs were initialized with double values.
   */
  @Override
  public final <T> int distance(List<T> s1, List<T> s2) {
    return distance(s1.toArray(), s2.toArray());
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
    int apply(int i, int j, int valueIfSame);
  }

  private boolean isIntAsDouble(double d) {
    return ((double) ((int) d)) == d;
  }

  private int distance(int n, int m, ChangeCost coster) {
    int[][] D = initD(n, m);
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        D[i][j] =
            min(
                coster.apply(i - 1, j - 1, D[i - 1][j - 1]),
                D[i - 1][j] + delete_i,
                D[i][j - 1] + insert_i);
      }
    }
    return D[n][m];
  }

  private int[][] initD(int n, int m) {
    if (insert_i < 0)
      throw new UnsupportedOperationException(
          "EditDistance.distance not supported for floating-point costs.");
    int[][] D = new int[n + 1][m + 1];
    for (int i = 1; i <= n; i++) {
      D[i][0] = D[i - 1][0] + delete_i;
    }
    for (int j = 1; j <= m; j++) {
      D[0][j] = D[0][j - 1] + insert_i;
    }
    return D;
  }

  private int min(int m1, int m2, int m3) {
    if (m2 < m1) m1 = m2;
    return m3 < m1 ? m3 : m1;
  }
}
