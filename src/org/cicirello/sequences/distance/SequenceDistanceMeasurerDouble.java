/*
 * Copyright 2018-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import java.util.List;

/**
 * <p>Implement this interface, SequenceDistanceMeasurerDouble, to define a distance metric for sequences.  A sequence may have duplicate elements, unlike
 * a Permutation which must have unique elements.  Some SequenceDistanceMeasurers may require the pair of sequences to be the same length, while
 * others do not have that requirement.  Some SequenceDistanceMeasurers may require the pair of sequences to contain the same set of elements, while
 * others do not have that requirement.  Implementations of this interface compute distances that are floating-point valued.</p>
 *
 * <p>If your sequences are guaranteed not to contain duplicates, and the pair is guaranteed to contain the same set of elements, and are of the same length,
 * then consider instead using the classes that implement the {@link org.cicirello.permutations.distance.PermutationDistanceMeasurerDouble PermutationDistanceMeasurerDouble}
 * interface.  Those classes are specifically for distance between permutations of the integers from 0 to N-1.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.6.10
 * @since 1.1
 */
public interface SequenceDistanceMeasurerDouble {
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(long[] s1, long[] s2);
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(int[] s1, int[] s2);
	
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(short[] s1, short[] s2);
	
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(byte[] s1, byte[] s2);
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(char[] s1, char[] s2);
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(double[] s1, double[] s2);
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(float[] s1, float[] s2);
	
	/**
	 * Measures the distance between two arrays.
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(boolean[] s1, boolean[] s2);
	
	/**
	 * Measures the distance between two Strings.
	 * @param s1 First String.
	 * @param s2 Second String.
	 * @return distance between s1 and s2
	 */
	double distancef(String s1, String s2);
	
	/**
	 * Measures the distance between two arrays of objects.
	 * The objects in the arrays must be of a class that has overridden the
	 * Object.equals method.
	 *
	 * @since 1.2.3
	 * @param s1 First array.
	 * @param s2 Second array.
	 * @return distance between s1 and s2
	 */
	double distancef(Object[] s1, Object[] s2);
	
	/**
	 * Measures the distance between two lists of objects.
	 * The objects in the lists must be of a class that has overridden the
	 * Object.equals method.
	 *
	 * @since 1.5
	 * @param s1 First list.
	 * @param s2 Second list.
	 * @param <T> Type of List elements.
	 * @return distance between s1 and s2
	 */
	<T> double distancef(List<T> s1, List<T> s2);
}

