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
package org.cicirello.sequences.distance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Internal abstract class for use by relabeling phase for computing Kendall Tau Sequence Distance.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
abstract class AbstractRelabelBySorting {

  final int internalRelabel(Comparable[] s1, Comparable[] s2, int[][] relabeling) {
    Comparable[] c1 = s1.clone();
    Arrays.sort(c1);
    int[] labels = new int[c1.length];
    int current = labels[0] = 0;
    for (int i = 1; i < labels.length; i++) {
      if (c1[i] != c1[i - 1]) current++;
      labels[i] = current;
    }

    for (int i = 0; i < relabeling.length; i++) {
      int j = Arrays.binarySearch(c1, s1[i]);
      relabeling[i][0] = labels[j];
      j = Arrays.binarySearch(c1, s2[i]);
      validateElementIndex(j);
      relabeling[i][1] = labels[j];
    }
    return current + 1;
  }

  final int internalRelabel(List<Comparable> s1, List<Comparable> s2, int[][] relabeling) {
    Comparable[] c1 = s1.toArray(new Comparable[s1.size()]);
    Arrays.sort(c1);
    int[] labels = new int[c1.length];
    int current = labels[0] = 0;
    for (int i = 1; i < labels.length; i++) {
      if (c1[i] != c1[i - 1]) current++;
      labels[i] = current;
    }
    Iterator<Comparable> iter1 = s1.iterator();
    Iterator<Comparable> iter2 = s2.iterator();
    for (int i = 0; i < relabeling.length; i++) {
      int j = Arrays.binarySearch(c1, iter1.next());
      relabeling[i][0] = labels[j];
      j = Arrays.binarySearch(c1, iter2.next());
      validateElementIndex(j);
      relabeling[i][1] = labels[j];
    }
    return current + 1;
  }

  final void validateElementIndex(int index) {
    if (index < 0) {
      throw new IllegalArgumentException(
          "Sequences must contain same elements: s2 contains at least one element not in s1.");
    }
  }
}
