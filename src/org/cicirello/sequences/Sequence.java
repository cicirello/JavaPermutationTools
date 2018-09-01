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
 
package org.cicirello.sequences;

/**
 * A Sequence is an ordered sequence of values, similar to an array or a list, except a Sequence has a more limited, and specific set of 
 * functionality in support of algorithms such as distance metrics (among others) on sequences.
 * The methods of this interface are designed to provide support for comparing elements within a single sequence, 
 * as well as for comparing elements between a pair of sequences.  This interface, and the classes that implement it, have the primary purpose 
 * within this library in support of algorithms on sequences, such as
 * the computation of distance metrics found in the classes of the {@link org.cicirello.sequences.distance} package.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.29
 * @since 1.1 
 */
public interface Sequence<E> {
	
	/**
	 * Returns the length of the sequence.
	 * @return length of the array
	 */
	int length();
	
	/**
	 * Checks if the elements at the specified indices are equal.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the elements in positions i and j are equal, and false otherwise.
	 */
	boolean equal(int i, int j);
	
	/**
	 * Checks if the elements at the specified indices of a pair of Sequences are equal.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence (both sequences must be of same runtime type).
	 * @param j Index into other Sequence.
	 * @return true if the elements in positions i of this Sequence and j of the other Sequence are equal 
	 * and false otherwise.
	 * @throws ClassCastException if this and other are of different runtime types.
	 */
	boolean equal(int i, Sequence<E> other, int j);
	
	/**
	 * Checks if one element is less than another.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is less than the element in position j, and false otherwise.
	 */
	boolean lessThan(int i, int j);
	
	/**
	 * Checks if element from this Sequence is less than an element in the other.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence (both sequences must be of same runtime type).
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is less than the element in position j of other, and false otherwise.
	 * @throws ClassCastException if this and other are of different runtime types.
	 */
	boolean lessThan(int i, Sequence<E> other, int j);
	
	/**
	 * Checks if one element is less or equal to than another.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is less than or equal to the element in position j, and false otherwise.
	 */
	boolean lessThanOrEqual(int i, int j);
	
	/**
	 * Checks if element from this Sequence is less than or equal to an element in the other.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence (both sequences must be of same runtime type).
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is less than or equal to the element in position j of other, and false otherwise.
	 * @throws ClassCastException if this and other are of different runtime types.
	 */
	boolean lessThanOrEqual(int i, Sequence<E> other, int j);
	
	/**
	 * Checks if one element is greater than another.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is greater than the element in position j, and false otherwise.
	 */
	boolean greaterThan(int i, int j);
	
	/**
	 * Checks if element from this Sequence is greater than an element in the other.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence (both sequences must be of same runtime type).
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is greater than the element in position j of other, and false otherwise.
	 * @throws ClassCastException if this and other are of different runtime types.
	 */
	boolean greaterThan(int i, Sequence<E> other, int j);
	
	/**
	 * Checks if one element is greater or equal to than another.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is greater than or equal to the element in position j, and false otherwise.
	 */
	boolean greaterThanOrEqual(int i, int j);
	
	/**
	 * Checks if element from this Sequence is greater than or equal to an element in the other.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence (both sequences must be of same runtime type).
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is greater than or equal to the element in position j of other, and false otherwise.
	 * @throws ClassCastException if this and other are of different runtime types.
	 */
	boolean greaterThanOrEqual(int i, Sequence<E> other, int j);
	
	/**
	 * Swaps the elements at the specified positions.
	 * @param i First index
	 * @param j Second index
	 */
	void swap(int i, int j);
	
	/**
	 * Returns an object that corresponds to the element in position i.
	 * @param i Index of element to get
	 * @return object corresponding to the element in position i
	 */
	E get(int i);
	
	/**
	 * Sets the value for position i.
	 * @param i The index
	 * @param v The value to set.
	 */
	void set(int i, E v);
	
	/**
	 * Sorts the sequence into non-decreasing order.
	 */
	void sort();
	
	/**
	 * Searches the Sequence for an element using a search algorithm whose runtime is O(lg n), such as binary search.  
	 * The Sequence must be sorted prior to calling search.  If it is not sorted, the result is undefined.
	 * If the Sequence contains the element multiple times, there is no guarantee which will be found.
	 * @param element The target element.
	 * @return index of the element, if it is contained in the sequence; otherwise, (-(insertion point) - 1). 
	 * The insertion point is defined as the point at which the element would be inserted into the sequence if you wanted to maintain
	 * sorted order: the index of the first element greater than the element, or length() if all elements in the sequence are less than 
	 * the specified element. Note that this guarantees that the return value will be &gt;= 0 if and only if the element is found.
	 */
	int search(E element);
	
	/**
	 * Generates a copy of the sequence, including making a copy of any underlying array, etc.
	 * @return a copy of the Sequence
	 */
	Sequence<E> copy();
	
}