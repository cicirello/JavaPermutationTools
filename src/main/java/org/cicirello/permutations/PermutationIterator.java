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

package org.cicirello.permutations;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator over all permutations of a specified length, n, of the integers in the interval [0,n).
 * The runtime of the constructors is O(n), where n is the permutation length. The {@link
 * #hasNext()} method is O(1). The runtime of the {@link #next()} method is O(n), as it does O(n)
 * swaps in the worst-case, and regardless of number of swaps it returns a copy (an O(n) operation)
 * of the internally maintained Permutation object so the caller can safely modify the returned
 * Permutation without risk of interfering with the operation of the Iterator.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public class PermutationIterator implements Iterator<Permutation> {

  private Permutation p;
  private int[] lastSwap;
  private boolean done;

  /**
   * Initializes a PermutationIterator to iterate over all permutations of a given length.
   * Specifically, it iterates over permutations of the first n integers, i.e., the integers in the
   * interval [0, n-1]. The first permutation in the iteration is chosen randomly.
   *
   * @param n The length of the permutations.
   */
  public PermutationIterator(int n) {
    this(new Permutation(n));
  }

  /**
   * Initializes a PermutationIterator to iterate over all permutations the same length as a given
   * permutation. Specifically, it iterates over permutations of the first n integers, i.e., the
   * integers in the interval [0, n-1). The first permutation in the iteration is specified as a
   * parameter.
   *
   * @param p The first permutation in the iteration.
   */
  public PermutationIterator(Permutation p) {
    this.p = new Permutation(p);
    lastSwap = new int[p.length()];
    for (int i = 0; i < lastSwap.length; i++) lastSwap[i] = i;
    done = false;
  }

  /**
   * Checks if this PermutationIterator has more Permutations.
   *
   * @return true if and only if this PermutationIterator has more Permutations to iterate over.
   */
  @Override
  public boolean hasNext() {
    return !done;
  }

  /**
   * Gets the Permutation for the next iteration.
   *
   * @return The Permutation for the next iteration.
   * @throws NoSuchElementException if hasNext() is false
   */
  @Override
  public Permutation next() {
    if (done) throw new NoSuchElementException();
    Permutation n = new Permutation(p);
    if (lastSwap.length <= 1) {
      done = true;
    } else {
      for (int i = lastSwap.length - 2; i >= 0; i--) {
        if (lastSwap[i] != i) p.swap(i, lastSwap[i]);
        if (lastSwap[i] == lastSwap.length - 1) {
          lastSwap[i] = i;
          if (i == 0) done = true;
          continue;
        }
        lastSwap[i]++;
        p.swap(i, lastSwap[i]);
        break;
      }
    }
    return n;
  }
}
