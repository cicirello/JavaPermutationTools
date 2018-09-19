/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 *
 */

package org.cicirello.examples.jpt;

import org.cicirello.sequences.distance.*;

/**
 * Basic examples of the use of the classes from the 
 * org.cicirello.sequences.distance package.
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class SequenceDistanceExamples {
	public static void main(String[] args) {
		
		System.out.println("If you are running this example program, make");
		System.out.println("sure you also read the source code and comments.");
		System.out.println("Output of example program in isolation of the");
		System.out.println("source and comments won't tell you much.");
		System.out.println();
		
		// The sequence distance measures work with sequences of any element type,
		// such as arrays of any primitive type, arrays of objects, as well as the characters of a String.
		
		// Construct SequenceDistanceMeasurer objects.
		SequenceDistanceMeasurer em = new ExactMatchDistance();
		SequenceDistanceMeasurer lcs = new LongestCommonSubsequenceDistance();
		SequenceDistanceMeasurer edit1 = new EditDistance(1, 1, 1);
		SequenceDistanceMeasurer edit2 = new EditDistance(1, 1, 2);
		
		// String examples:
		String s1 = "The quick brown fox jumped over the lazy dog.";
		String s2 = "The quick brown fox tripped over the crazy frog.";
		System.out.println("String example:");
		System.out.println("s1: "+s1);
		System.out.println("s2: "+s2);
		System.out.println("exact match distance of s1 and s2: " + em.distance(s1,s2));
		System.out.println("longest common subsequence distance of s1 and s2: " + lcs.distance(s1,s2));
		System.out.println("edit distance (cost of 1 for insertions, deletions, and changes) of s1 and s2: " + edit1.distance(s1,s2));
		System.out.println("edit distance (cost of 1 for insertions, deletions, and 2 for changes) of s1 and s2: " + edit2.distance(s1,s2));
		
		// array of ints example
		int[] a1 = { 1, 3, 7, 2, 5, 1, 1, 3, 8, 9, 10, 2, 5};
		int[] a2 = { 2, 3, 7, 5, 1, 3, 8, 1, 10, 9, 1, 2, 5};
		System.out.println("Array example:");
		System.out.print("a1: ");
		for (int i = 0; i < a1.length; i++) System.out.print(" " + a1[i]);
		System.out.println();
		System.out.print("a2: ");
		for (int i = 0; i < a2.length; i++) System.out.print(" " + a2[i]);
		System.out.println();
		System.out.println("exact match distance of a1 and a2: " + em.distance(a1,a2));
		System.out.println("longest common subsequence distance of a1 and a2: " + lcs.distance(a1,a2));
		System.out.println("edit distance (cost of 1 for insertions, deletions, and changes) of a1 and a2: " + edit1.distance(a1,a2));
		System.out.println("edit distance (cost of 1 for insertions, deletions, and 2 for changes) of a1 and a2: " + edit2.distance(a1,a2));
		
	}
}
