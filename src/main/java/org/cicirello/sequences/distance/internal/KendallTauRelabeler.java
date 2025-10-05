/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2025 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences.distance.internal;

import java.util.List;

/**
 * Internal package access interface for use by two alternative implementations of the relabeling
 * phase for the two different algorithms for computing Kendall Tau Sequence Distance.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public interface KendallTauRelabeler {

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(int[] s1, int[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(long[] s1, long[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(short[] s1, short[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(byte[] s1, byte[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(char[] s1, char[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(double[] s1, double[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(float[] s1, float[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(String s1, String s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  int relabel(Object[] s1, Object[] s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param <T> The type of objects in the sequence.
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  <T> int relabel(List<T> s1, List<T> s2, int[][] relabeling);

  /**
   * Relabels the elements of the sequence to integers from 0 to k where there are k+1 distinct
   * elements in the sequences.
   *
   * @param <T> The type of objects in the sequence.
   * @param s1 The first sequence.
   * @param s2 The second sequence.
   * @param relabeling Buffer for the result, where number of rows should be same as that of the
   *     sequences and with two columns, one for each sequence.
   * @return The number of distinct labels.
   */
  default int relabel(boolean[] s1, boolean[] s2, int[][] relabeling) {
    int trueCount1 = 0;
    int trueCount2 = 0;
    for (int i = 0; i < s1.length; i++) {
      if (s1[i]) trueCount1++;
      if (s2[i]) trueCount2++;
    }
    if (trueCount1 != trueCount2) {
      throw new IllegalArgumentException("Sequences must contain same elements.");
    }
    if (trueCount1 > 0 && trueCount1 < s1.length) {
      for (int i = 0; i < relabeling.length; i++) {
        relabeling[i][0] = s1[i] ? 1 : 0;
        relabeling[i][1] = s2[i] ? 1 : 0;
      }
      return 2;
    } else {
      for (int i = 0; i < relabeling.length; i++) {
        relabeling[i][0] = relabeling[i][1] = 0;
      }
      return 1;
    }
  }
}
