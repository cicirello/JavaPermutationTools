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

/**
 * A functional interface for defining custom binary operators on Permutations. See the {@link
 * Permutation#apply(PermutationFullBinaryOperator,Permutation)} method. In this interface, the
 * {@link #apply} method is passed both references to the raw arrays of ints that are encapsulated
 * by the Permutations, as well as references to the original Permutation objects to enable
 * utilizing the methods of the {@link Permutation} class. If the operator that you are implementing
 * only requires the raw arrays of ints, then consider implementing the {@link
 * PermutationBinaryOperator} interface instead.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
@FunctionalInterface
public interface PermutationFullBinaryOperator {

  /**
   * Applies an operator on the raw representations of a pair of Permutations. Implementers of this
   * interface are responsible for ensuring that the apply method maintains valid permutations of
   * the integers in {0, 1, ..., rawPermutation1.length - 1 }, and likewise for rawPermutation2.
   *
   * @param rawPermutation1 A reference to the raw array of ints underlying a Permutation object.
   *     Changes to this array will directly change the Permutation object that encapsulates it.
   * @param rawPermutation2 A reference to the raw array of ints underlying a Permutation object.
   *     Changes to this array will directly change the Permutation object that encapsulates it.
   * @param p1 A reference to the Permutation object that encapsulates rawPermutation1.
   * @param p2 A reference to the Permutation object that encapsulates rawPermutation2.
   */
  void apply(int[] rawPermutation1, int[] rawPermutation2, Permutation p1, Permutation p2);
}
