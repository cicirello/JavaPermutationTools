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

/**
 * LongestCommonSubsequenceDistance is a form of EditDistance, where the edit operations are limited
 * to deletions and insertions (i.e., no replacements or changes), and where the cost of an edit
 * operation is simply 1. It is the minimum number of element insertions and deletions necessary to
 * transform one sequence into the other. It can be computed as: N + M - 2 * lcs(s1, s2), where
 * lcs(s1, s2) is the length of the longest common subsequence of sequences s1 and s2, and N and M
 * are the lengths of s1 and s2 (see section 5 of Wagner and Fischer (1974)).
 *
 * <p>This class supports computing distance for Java String objects or arrays of any of the
 * primitive types, or arrays of objects. It makes no assumptions about the contents of the Strings
 * or arrays, and they can contain duplicates, or can be such that some elements only appear in one
 * or the other of the sequences, or can be of different lengths.
 *
 * <p>Runtime: O(n*m), where n and m are the lengths of the two sequences (i.e., Strings or arrays).
 *
 * <p>If you need to compute this distance for permutations (i.e., same length, same set of
 * elements, unique elements), then it is recommended to instead use the {@link
 * org.cicirello.permutations.distance.ReinsertionDistance} class. That class exploits the common
 * length, common set of elements, and unique elements properties of permutations to more
 * efficiently (in O(n lg n) time) compute the longest common subpermutation (i.e., that class does
 * not delegate the work to the edit distance algorithm). However, the result of ReinsertionDistance
 * is half of what LongestCommonSunsequenceDistance would compute. This is because for permutations
 * the elements that would be inserted are exactly the same as those that would be deleted by the
 * edit operations, and ReinsertionDistance is defined as an edit distance with one edit operation,
 * removal/reinsertion (i.e., a deletion is only half the operation, and the insertion is the other
 * half of the operation).
 *
 * <p>Wagner and Fischer's String Edit Distance was introduced in:<br>
 * R. A. Wagner and M. J. Fischer, "The string-to-string correction problem," Journal of the ACM,
 * vol. 21, no. 1, pp. 168–173, January 1974.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class LongestCommonSubsequenceDistance extends EditDistance {

  /** Constructs a longest common subsequence distance. */
  public LongestCommonSubsequenceDistance() {
    // Wagner and Fischer (1974) showed that this distance measure is equivalent to edit distance
    // with costs of 1 for each of deletes and insertions, and a cost of 2 (or greater) for changes.
    super(1, 1, 2);
  }
}
