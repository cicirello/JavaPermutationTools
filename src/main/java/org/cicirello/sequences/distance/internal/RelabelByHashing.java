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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Internal class for use by relabeling phase for computing Kendall Tau Sequence Distance.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class RelabelByHashing implements KendallTauRelabeler {

  @Override
  public int relabel(Object[] s1, Object[] s2, int[][] relabeling) {
    HashMap<Object, Integer> labelMap =
        new HashMap<Object, Integer>((int) (1.334 * relabeling.length) + 2);
    int current = -1;
    for (int i = 0; i < relabeling.length; i++) {
      if (!labelMap.containsKey(s1[i])) {
        current++;
        labelMap.put(s1[i], current);
      }
    }

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      Integer j = labelMap.get(s2[i]);
      if (j == null)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public <T> int relabel(List<T> s1, List<T> s2, int[][] relabeling) {
    HashMap<T, Integer> labelMap = new HashMap<T, Integer>((int) (1.334 * relabeling.length) + 2);
    int current = -1;
    for (T e : s1) {
      if (!labelMap.containsKey(e)) {
        current++;
        labelMap.put(e, current);
      }
    }
    Iterator<T> iter1 = s1.iterator();
    Iterator<T> iter2 = s2.iterator();
    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(iter1.next());
      Integer j = labelMap.get(iter2.next());
      if (j == null)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(int[] s1, int[] s2, int[][] relabeling) {
    IntHT labelMap = new IntHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(double[] s1, double[] s2, int[][] relabeling) {
    DoubleHT labelMap = new DoubleHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(float[] s1, float[] s2, int[][] relabeling) {
    FloatHT labelMap = new FloatHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(long[] s1, long[] s2, int[][] relabeling) {
    LongHT labelMap = new LongHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(short[] s1, short[] s2, int[][] relabeling) {
    ShortHT labelMap = new ShortHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(char[] s1, char[] s2, int[][] relabeling) {
    CharHT labelMap = new CharHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1[i]);
      int j = labelMap.get(s2[i]);
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(String s1, String s2, int[][] relabeling) {
    CharHT labelMap = new CharHT((int) (1.334 * relabeling.length) + 2);
    int current = labelMap.populate(s1);

    for (int i = 0; i < relabeling.length; i++) {
      relabeling[i][0] = labelMap.get(s1.charAt(i));
      int j = labelMap.get(s2.charAt(i));
      if (j == -1)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j;
    }
    return current + 1;
  }

  @Override
  public int relabel(byte[] s1, byte[] s2, int[][] relabeling) {
    // Since there are only 256 possible byte values, use a simple array of length 256 for the hash
    // table.
    // Always perfect hashing with no collisions in this special case.
    int[] labelMap = new int[256];
    int current = 0;
    for (int i = 0; i < relabeling.length; i++) {
      int key = 255 & (int) s1[i];
      if (labelMap[key] == 0) {
        current++;
        labelMap[key] = current;
      }
    }

    for (int i = 0; i < relabeling.length; i++) {
      int key1 = 255 & (int) s1[i];
      relabeling[i][0] = labelMap[key1] - 1;
      int key2 = 255 & (int) s2[i];
      int j = labelMap[key2];
      if (j == 0)
        throw new IllegalArgumentException(
            "Sequences must contain same elements: s2 contains at least one element not in s1.");
      relabeling[i][1] = j - 1;
    }
    return current;
  }
}
