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

import java.util.Arrays;

/**
 * An ObjectSequence is a sequence of values of any one object type, provided the objects are Comparable.  
 * It provides support for comparing elements within a single sequence, 
 * as well as for comparing elements between a pair of sequences. 
 * Its primary purpose within this library is in support of algorithms on sequences, such as
 * the computation of distance metrics found in the classes of the {@link org.cicirello.sequences.distance} package.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.29
 * @since 1.1 
 */
public final class ObjectSequence<E extends Comparable<E>> implements Sequence<E> {
	
	private final E[] a;
	
	/**
	 * Constructs an ObjectSequence from a given array of objects.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public ObjectSequence(E[] array) {
		a = array;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObjectSequence<E> copy() {
		return new ObjectSequence<E>(a.clone());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int length() {
		return a.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equal(int i, int j) {
		return a[i].compareTo(a[j]) == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equal(int i, Sequence<E> other, int j) {
		return a[i].compareTo(((ObjectSequence<E>)other).a[j]) == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean lessThan(int i, int j) {
		return a[i].compareTo(a[j]) < 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean lessThan(int i, Sequence<E> other, int j) {
		return a[i].compareTo(((ObjectSequence<E>)other).a[j]) < 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean lessThanOrEqual(int i, int j) {
		return a[i].compareTo(a[j]) <= 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean lessThanOrEqual(int i, Sequence<E> other, int j) {
		return a[i].compareTo(((ObjectSequence<E>)other).a[j]) <= 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean greaterThan(int i, int j) {
		return a[i].compareTo(a[j]) > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean greaterThan(int i, Sequence<E> other, int j) {
		return a[i].compareTo(((ObjectSequence<E>)other).a[j]) > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean greaterThanOrEqual(int i, int j) {
		return a[i].compareTo(a[j]) >= 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean greaterThanOrEqual(int i, Sequence<E> other, int j) {
		return a[i].compareTo(((ObjectSequence<E>)other).a[j]) >= 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swap(int i, int j) {
		E temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	/**
	 * Returns the object in position i of the sequence
	 * @param i Index of element to get
	 * @return object in position i
	 */
	@Override
	public E get(int i) {
		return a[i];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(int i, E v) {
		a[i] = v;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sort() {
		Arrays.sort(a);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int search(E element) {
		return Arrays.binarySearch(a, element);
	}
	
	
}