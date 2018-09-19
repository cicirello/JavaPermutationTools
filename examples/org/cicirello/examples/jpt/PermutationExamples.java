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

import org.cicirello.permutations.*;
import java.util.HashMap;

/**
 * <p>Basic examples of the use of the classes from the 
 * org.cicirello.permutations package, including the
 * {@link org.cicirello.permutations.Permutation Permutation} and 
 * {@link org.cicirello.permutations.PermutationIterator PermutationIterator} classes.</p>
 *
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class PermutationExamples {
	
	public static void main(String[] args) {
		
		System.out.println("If you are running this example program, make sure you also read the source code and comments.");
		System.out.println("Output of example program in isolation of the source and comments won't tell you much.");
		
		// You can construct Permutation objects in several ways.
		// Here are a couple examples (see API for additional constructors).
		
		// Generating a random permutation of length 10:
		Permutation p1 = new Permutation(10);
		
		// Initializing a Permutation from an array of ints.  Note: array must contain unique elements
		// and must be permutation of the integers from 0 to N-1 (where N is length).
		int[] a = { 3, 0, 4, 2, 1, 5};
		Permutation p2 = new Permutation(a);
		
		// Making a copy of a permutation:
		Permutation p3 = new Permutation(p1);
		
		// Constructing a Permutation from an int in mixed-radix representation.
		int length = 5;
		int permID = 10;
		Permutation p4 = new Permutation(length, permID);
		
		// The Permutation class overrides Object.toString so you can easily generate text output that 
		// includes descriptions of permutations.  For example:
		System.out.println("Permutation p1 is: " + p1);
		
		// The Permutation class overrides Object.hashCode so you can use Permutation objects as the
		// key to a HashMap, HashSet, etc.
		HashMap<Permutation,String> map = new HashMap<Permutation,String>();
		map.put(p1, "A permutation maps to me.");
		
		// Object.equals overridden to check if 2 permutation objects consist in the same permutation of integers.
		if (p2.equals(p3)) {
			System.out.println("p2 and p3 are the same.");
		} else {
			System.out.println("p2 and p3 are not the same.");
		}
		
		// length method returns the length, get method accesses a specific element.
		System.out.println("i\tp1.get(i)");
		for (int i = 0; i < p1.length(); i++) {
			System.out.println(i + "\t" + p1.get(i));
		}
		
		// getInverse returns an array of ints that is the inverse of the Permutation
		int[] inv = p1.getInverse();
		Permutation pInv = new Permutation(inv);
		System.out.println("p1: " + p1);
		System.out.println("inverse of p1: " + pInv);
		
		// several mutator methods to change state of permutation, such as reverse, rotate, swap, and some others
		p1.reverse();
		System.out.println("after reversing p1: " + p1);
		p1.reverse(1, 4);
		System.out.println("then reversing from index 1 to 4: " + p1);
		p1.swap(2, 6);
		System.out.println("then swapping indices 2 and 6: " + p1);
		p1.removeAndInsert(5, 2);
		System.out.println("then removing from index 5 and reinserting at index 2: " + p1);
		p1.rotate(3);
		System.out.println("then a left circular rotation 3 positions: " + p1);
		p1.scramble(1, 6);
		System.out.println("then randomly shuffling from index 1 to 6: " + p1);
		
		// The PermutationIterator class enables iterating over all permutations of a given length.
		// And the Permutation class implements the Iterable interface making use of PermutationIterator
		// relatively easy.  In the following example, we iterate over all Permutations of length 4 beginning at
		// {3, 1, 0, 2}
		Permutation p5 = new Permutation(new int[] {3, 1, 0, 2});
		System.out.println("All permutations of length 4 beginning at " + p5);
		for (Permutation p : p5) {
			System.out.println(p);
		}
	}
}