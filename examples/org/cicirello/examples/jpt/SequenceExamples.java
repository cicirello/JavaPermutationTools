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

import org.cicirello.sequences.*;

/**
 * Basic examples of the use of the classes from the 
 * org.cicirello.sequences package.  Important Note:
 * The classes of the org.cicirello.sequences package 
 * are mainly intended to serve as utility classes for
 * implementing algorithms that operate on sequences,
 * specifically to enable implementing such algorithms
 * on sequences independent of what type of elements
 * are contained in the sequence.
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class SequenceExamples {
	public static void main(String[] args) {
		
		System.out.println("If you are running this example program, make");
		System.out.println("sure you also read the source code and comments.");
		System.out.println("Output of example program in isolation of the");
		System.out.println("source and comments won't tell you much.");
		System.out.println();
		
		// The PrimitiveValue class is a wrapper class that supports all 8 of Java's primitive types.
		// It has been implemented to support specific methods of the PrimitiveSequence class (the get and set methods).
		// The PrimitiveValue class has simpler functionality than the Java API's Number classes (Integer, Double, etc)
		// such as no auto-boxing.
		// The PrimitiveValue class's functionality is limited to wrapping a primitive value of any of the 8 primitive types,
		// and converting among them.
		// Some examples:
		PrimitiveValue v1 = new PrimitiveValue(5);
		PrimitiveValue v2 = new PrimitiveValue(true);
		PrimitiveValue v3 = new PrimitiveValue(10.21);
		int i1 = v1.intValue();
		boolean b1 = v1.booleanValue();
		
		// The org.cicirello.sequences also defines a Sequence interface, and two classes PrimitiveSequence and
		// ObjectSequence that both implement the Sequence interface.
		// The purpose of these classes (and interface) is to enable implementing algorithms that operate on
		// sequences of any type of element without the need to know what type of element is contained in the sequence.
		
		// IMPORTANT NOTE: You can use the sequence distance measures of the org.cicirello.sequences.distance package
		// without directly using the classes and interface of the org.cicirello.sequences package, for which we now
		// provide examples.  
		
		// Some examples of constructing Sequences:

		
		// sequence of int values
		int[] iArray = {2, 6, 1, 4, 8, 4, 4, 4, 2};
		PrimitiveSequence s1 = new PrimitiveSequence(iArray);
		// sequence of double values
		double[] dArray = {1.1, 4.0, 3.1, 10.0, -2.1};
		PrimitiveSequence s2 = new PrimitiveSequence(dArray);
		
		// Likewise, you can pass an array of any of the other primitive types when constructing a PrimitiveSequence.
		
		// Note: The PrimitiveSequence object does NOT contain an array of wrapper objects.
		// It actually stores a reference to the very array passed to it, so changes to the array
		// can be made either via the methods of the PrimitiveSequence class or directly.
		// For example:
		dArray[1] = 15.2;
		s2.swap(2, 3);
		
		// The copy method constructs a copy of a PrimitiveSequence.
		// The copy does not share the same array as the original, so changes can be made
		// to the copy without effect on the original PrimitiveSequence or the original array.
		PrimitiveSequence s3 = s2.copy();
		s3.swap(2, 3);
		
		// The ObjectSequence class can be constructed from any array of any object type that implements Comparable.
		// Example of an ObjectSequence of Strings:
		String[] sArray = {"hello", "world", "sequences", "strings", "abracadabra"};
		ObjectSequence<String> s4 = new ObjectSequence<String>(sArray);

		// The primary intended purpose of the ObjectSequence and PrimitiveSequence classes, and the interface they share in common,
		// the Sequence interface is to support implementation of algorithms on Sequences in a way that is independent of
		// element type.
		
		// The selectionSort method below provides a a simple example of how these classes and interface can be used to implement algorithms
		// independent of the element type.  Rather than implementing the sort 9 times, once for each primitive type, and once for Objects,
		// it is only necessary to implement it once.
		// Note: For the PrimitiveSequences, this is very different than simply generating an array of wrapper objects and sorting that.
		// The PrimitiveSequence avoids wherever possible wrapping the elements (only the get and set methods use the PrimitiveValue wrapper class).
		
		System.out.print("Before sorting: ");
		for (int i = 0; i < iArray.length; i++) {
			System.out.print(" " + iArray[i]);
		}
		System.out.println();
		selectionSort(s1);
		System.out.print("After sorting: ");
		for (int i = 0; i < iArray.length; i++) {
			System.out.print(" " + iArray[i]);
		}
		System.out.println();
		System.out.print("Before sorting: ");
		for (int i = 0; i < dArray.length; i++) {
			System.out.print(" " + dArray[i]);
		}
		System.out.println();
		selectionSort(s2);
		System.out.print("After sorting: ");
		for (int i = 0; i < dArray.length; i++) {
			System.out.print(" " + dArray[i]);
		}
		System.out.println();
		System.out.print("Before sorting: ");
		for (int i = 0; i < sArray.length; i++) {
			System.out.print(" " + sArray[i]);
		}
		System.out.println();
		selectionSort(s4);
		System.out.print("After sorting: ");
		for (int i = 0; i < sArray.length; i++) {
			System.out.print(" " + sArray[i]);
		}
		System.out.println();
	}
	
	public static <E> void selectionSort(Sequence<E> sequence) {		
		// length method gets the length of the Sequence
		for (int i = 0; i < sequence.length() - 1; i++) {
			int minIndex = i;
			for (int j = i+1; j < sequence.length(); j++) {
				// All of the relational operators have corresponding methods that take the indices of the elements to compare
				// such as this example with lessThan.
				if (sequence.lessThan(j,minIndex)) minIndex = j;
			}
			// There are methods of the Sequence interface that 
			// change the contents of the Sequence, such as this example
			// of swap.
			if (minIndex != i) sequence.swap(i, minIndex);
		}
	}
	
}