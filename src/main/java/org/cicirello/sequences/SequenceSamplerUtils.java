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
package org.cicirello.sequences;

import java.lang.reflect.Array;

/**
 * SequenceSamplerUtils is an internal utility class with utility methods related to efficiently
 * generating random samples of array elements, without replacement.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
class SequenceSamplerUtils {

  /** prevent instantiation with a private constructor. */
  private SequenceSamplerUtils() {}

  static void validateK(int k, int sourceLength) {
    if (k > sourceLength) {
      throw new IllegalArgumentException("k must be no greater than length of source array");
    }
  }

  @SuppressWarnings("unchecked")
  static <T> T[] allocateIfNecessary(T[] source, int k, T[] target) {
    if (target == null) {
      target = (T[]) Array.newInstance(source.getClass().getComponentType(), k);
    } else if (target.length < k) {
      target = (T[]) Array.newInstance(target.getClass().getComponentType(), k);
    }
    return target;
  }
}
