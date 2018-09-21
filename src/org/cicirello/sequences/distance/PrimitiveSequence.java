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
 
package org.cicirello.sequences.distance;

import java.util.Arrays;

/**
 * A PrimitiveSequence is a sequence of values of any one of Java's eight primitive types.  
 * An object of this class does not contain objects wrapping primitives,
 * but rather it contains an array of primitive values.  It provides support for comparing elements within a single sequence, 
 * as well as for comparing elements between a pair of sequences. A single PrimitiveSequence cannot contain a
 * mix of values of multiple primitive types.  That is not its purpose.  An example use of this class might be to enable
 * implementing an algorithm, such as a sorting algorithm, that manipulates an array in a manner that is independent of the
 * primitives it contains.  Its primary purpose within this library is in support of algorithms on sequences, such as
 * the computation of distance metrics found in the classes of the {@link org.cicirello.sequences.distance} package.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.29
 * @since 1.1 
 */
final class PrimitiveSequence implements Sequence<PrimitiveValue> {
	
	private final AbstractPrimitiveSequence a;
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(long[] array) {
		a = new Long(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(int[] array) {
		a = new Int(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(short[] array) {
		a = new Short(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(byte[] array) {
		a = new Byte(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(char[] array) {
		a = new Char(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(boolean[] array) {
		a = new Boolean(array);
	}
	
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(double[] array) {
		a = new Double(array);
	}
	
		
	/**
	 * Constructs a PrimitiveSequence from a given array of primitive values.
	 * The array is not cloned, and the sequence object holds a reference to the very array.
	 * This allows changing contents of array either directly or via the methods of this sequence class.
	 * @param array The array.
	 */
	public PrimitiveSequence(float[] array) {
		a = new Float(array);
	}
	
	private PrimitiveSequence(PrimitiveSequence s) {
		this.a = s.a.copy();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimitiveSequence copy() {
		return new PrimitiveSequence(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int length() {
		return a.length();
	}
	
	/**
	 * Checks if the elements at the specified indices are equal.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the elements in positions i and j are equal (per == check on primitive values), and false otherwise.
	 */
	@Override
	public boolean equal(int i, int j) {
		return a.equal(i, j);
	}
	
	/**
	 * Checks if the elements at the specified indices of a pair of Sequences are equal.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence must be a PrimitiveSequence containing elements of the same primitive type.
	 * @param j Index into other Sequence.
	 * @return true if the elements in positions i of this Sequence and j of the other Sequence are equal (per == check on primitive values)
	 * and false otherwise.
	 * @throws ClassCastException if either other is not a PrimitiveSequence, or if this and other contain different primitive type values.
	 */
	@Override
	public boolean equal(int i, Sequence<PrimitiveValue> other, int j) {
		return a.equal(i, ((PrimitiveSequence)other).a, j);
	}
	
	/**
	 * Checks if one element is less than another.  If elements are booleans, false is treated as less than true.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is less than the element in position j (per &lt; check on primitive values), and false otherwise.
	 */
	@Override
	public boolean lessThan(int i, int j) {
		return a.lessThan(i, j);
	}
	
	/**
	 * Checks if element from this Sequence is less than an element in the other.  If elements are booleans, false is treated as less than true.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence must be a PrimitiveSequence containing elements of the same primitive type.
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is less than the element in position j of other (per &lt; check on primitive values), and false otherwise.
	 * @throws ClassCastException if either other is not a PrimitiveSequence, or if this and other contain different primitive type values.
	 */
	@Override
	public boolean lessThan(int i, Sequence<PrimitiveValue> other, int j) {
		return a.lessThan(i, ((PrimitiveSequence)other).a, j);
	}
	
	/**
	 * Checks if one element is less than or equal to another.  If elements are booleans, false is treated as less than true.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is less than or equal to the element in position j (per &lt;= check on primitive values), and false otherwise.
	 */
	@Override
	public boolean lessThanOrEqual(int i, int j) {
		return a.lessThanOrEqual(i, j);
	}
	
	/**
	 * Checks if element from this Sequence is less than or equal to an element in the other.  If elements are booleans, false is treated as less than true.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence must be a PrimitiveSequence containing elements of the same primitive type.
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is less than or equal to the element in position j of other (per &lt;= check on primitive values), and false otherwise.
	 * @throws ClassCastException if either other is not a PrimitiveSequence, or if this and other contain different primitive type values.
	 */
	@Override
	public boolean lessThanOrEqual(int i, Sequence<PrimitiveValue> other, int j) {
		return a.lessThanOrEqual(i, ((PrimitiveSequence)other).a, j);
	}
	
	/**
	 * Checks if one element is greater than another.  If elements are booleans, false is treated as less than true.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is greater than the element in position j (per &gt; check on primitive values), and false otherwise.
	 */
	@Override
	public boolean greaterThan(int i, int j) {
		return a.lessThan(j, i);
	}
	
	/**
	 * Checks if element from this Sequence is greater than an element in the other.  If elements are booleans, false is treated as less than true.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence must be a PrimitiveSequence containing elements of the same primitive type.
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is greater than the element in position j of other (per &gt; check on primitive values), and false otherwise.
	 * @throws ClassCastException if either other is not a PrimitiveSequence, or if this and other contain different primitive type values.
	 */
	@Override
	public boolean greaterThan(int i, Sequence<PrimitiveValue> other, int j) {
		return ((PrimitiveSequence)other).a.lessThan(j, a, i);
	}
	
	/**
	 * Checks if one element is greater than or equal to another.  If elements are booleans, false is treated as less than true.
	 * @param i First index.
	 * @param j Second index.
	 * @return true if the element in positions i is greater than or equal to the element in position j (per &gt;= check on primitive values), and false otherwise.
	 */
	@Override
	public boolean greaterThanOrEqual(int i, int j) {
		return a.lessThanOrEqual(j, i);
	}
	
	/**
	 * Checks if element from this Sequence is greater than or equal to an element in the other.  If elements are booleans, false is treated as less than true.
	 * @param i Index into this Sequence.
	 * @param other The other Sequence must be a PrimitiveSequence containing elements of the same primitive type.
	 * @param j Index into other Sequence.
	 * @return true if the element in positions i of this is greater than or equal to the element in position j of other (per &gt;= check on primitive values), and false otherwise.
	 * @throws ClassCastException if either other is not a PrimitiveSequence, or if this and other contain different primitive type values.
	 */
	@Override
	public boolean greaterThanOrEqual(int i, Sequence<PrimitiveValue> other, int j) {
		return ((PrimitiveSequence)other).a.lessThanOrEqual(j, a, i);
	}
		
	/**
	 * Returns an object that wraps the primitive value in position i.
	 * @param i Index of element to get
	 * @return object wrapping the primitive in position i
	 */
	@Override
	public PrimitiveValue get(int i) {
		return a.get(i);
	}
	
	/**
	 * Sets the primitive value in position i to the primitive wrapped by the specified wrapper object.
	 * If the wrapped primitive is of a different type than the rest of the elements in this array, it
	 * is first converted to the type of the rest of the elements.
	 * @param i The index
	 * @param v An object wrapping the primitive to set.
	 */
	@Override
	public void set(int i, PrimitiveValue v) {
		a.set(i, v);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swap(int i, int j) {
		a.swap(i, j);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sort() {
		a.sort();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int search(PrimitiveValue element) {
		return a.search(element);
	}
	
	
	
	private static abstract class AbstractPrimitiveSequence {
		
		abstract public int length();
		
		abstract public boolean equal(int i, int j);
		
		abstract public boolean equal(int i, AbstractPrimitiveSequence other, int j);
		
		abstract public boolean lessThan(int i, int j);
		
		abstract public boolean lessThan(int i, AbstractPrimitiveSequence other, int j);
		
		abstract public boolean lessThanOrEqual(int i, int j);
		
		abstract public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j);
		
		abstract public int compareElements(int i, int j);
		
		abstract public PrimitiveValue get(int i);
		
		abstract public void set(int i, PrimitiveValue v);
		
		abstract public void swap(int i, int j);
		
		abstract public AbstractPrimitiveSequence copy();
		
		abstract public void sort();
		
		abstract public int search(PrimitiveValue element);
	}
	
	/**
	 * The Long class is a wrapper of an array of primitive long values.
	 *
	 * @since 2.0
	 */
	private static final class Long extends AbstractPrimitiveSequence {
		private final long[] array;
		
		public Long(long[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Long(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.longValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Long)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Long)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Long)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			long diff = array[i] - array[j];
			int diffInt = (int)diff;
			if ((long)diffInt == diff) return diffInt;
			if (diff < 0L) return Integer.MIN_VALUE;
			return Integer.MAX_VALUE;
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.longValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				long temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Int class is a wrapper of an array of primitive int values.
	 *
	 * @since 2.0
	 */
	private static final class Int extends AbstractPrimitiveSequence {
		private final int[] array;
		
		public Int(int[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Int(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.intValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Int)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Int)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Int)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			return array[i] - array[j];
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.intValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Short class is a wrapper of an array of primitive short values.
	 *
	 * @since 2.0
	 */
	private static final class Short extends AbstractPrimitiveSequence {
		private final short[] array;
		
		public Short(short[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Short(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.shortValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Short)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Short)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Short)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			return array[i] - array[j];
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.shortValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				short temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Byte class is a wrapper of an array of primitive byte values.
	 *
	 * @since 2.0
	 */
	private static final class Byte extends AbstractPrimitiveSequence {
		private final byte[] array;
		
		public Byte(byte[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Byte(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.byteValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Byte)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Byte)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Byte)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			return array[i] - array[j];
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.byteValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				byte temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Char class is a wrapper of an array of primitive char values.
	 *
	 * @since 2.0
	 */
	private static final class Char extends AbstractPrimitiveSequence {
		private final char[] array;
		
		public Char(char[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Char(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.charValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Char)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Char)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Char)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			return array[i] - array[j];
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.charValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				char temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Boolean class is a wrapper of an array of primitive boolean values.
	 *
	 * @since 2.0
	 */
	private static final class Boolean extends AbstractPrimitiveSequence {
		private final boolean[] array;
		
		public Boolean(boolean[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Boolean(array.clone());
		}
		
		@Override
		public void sort() {
			int i = 0;
			int j = array.length-1;
			do {
				for ( ; i < array.length && !array[i]; i++);
				for ( ; j >= 0 && array[j]; j--);
				if (j > i) {
					array[i] = false;
					i++;
					array[j] = true;
					j--;
				}
			} while (j > i);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			boolean b = element.booleanValue();
			if (b && array[array.length-1]) return array.length-1;
			if (!b && !array[0]) return 0;
			if (b) return -array.length - 1;
			return -1;
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Boolean)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return !array[i] && array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return !array[i] && ((Boolean)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return !array[i] || array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return !array[i] || ((Boolean)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			if (array[i] == array[j]) return 0;
			if (array[i]) return 1;
			return -1;
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.booleanValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				boolean temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Double class is a wrapper of an array of primitive double values.
	 *
	 * @since 2.0
	 */
	private static final class Double extends AbstractPrimitiveSequence {
		private final double[] array;
		
		public Double(double[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Double(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.doubleValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Double)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Double)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Double)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			if (array[i] == array[j]) return 0;
			if (array[i] < array[j]) return -1;
			return 1;
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.doubleValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				double temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
	
	/**
	 * The Float class is a wrapper of an array of primitive float values.
	 *
	 * @since 2.0
	 */
	private static final class Float extends AbstractPrimitiveSequence {
		private final float[] array;
		
		public Float(float[] array) { this.array = array; }
		
		@Override
		public AbstractPrimitiveSequence copy() {
			return new Float(array.clone());
		}
		
		@Override
		public void sort() {
			Arrays.sort(array);
		}
		
		@Override
		public int search(PrimitiveValue element) {
			return Arrays.binarySearch(array, element.floatValue());
		}
	
		@Override
		public int length() { return array.length; }
		
		@Override
		public boolean equal(int i, int j) { return array[i] == array[j]; }
		
		@Override
		public boolean equal(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] == ((Float)other).array[j];
		}
		
		@Override
		public boolean lessThan(int i, int j) { return array[i] < array[j]; }
		
		@Override
		public boolean lessThan(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] < ((Float)other).array[j];
		}
		
		@Override
		public boolean lessThanOrEqual(int i, int j) { return array[i] <= array[j]; }
		
		@Override
		public boolean lessThanOrEqual(int i, AbstractPrimitiveSequence other, int j) {
			return array[i] <= ((Float)other).array[j];
		}
		
		@Override
		public int compareElements(int i, int j) {
			if (array[i] == array[j]) return 0;
			if (array[i] < array[j]) return -1;
			return 1;
		}
		
		@Override
		public PrimitiveValue get(int i) {
			return new PrimitiveValue(array[i]);
		}
		
		@Override		
		public void set(int i, PrimitiveValue v) {
			array[i] = v.floatValue();
		}
		
		@Override
		public void swap(int i, int j) {
			if (i != j) {
				float temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
	}
}