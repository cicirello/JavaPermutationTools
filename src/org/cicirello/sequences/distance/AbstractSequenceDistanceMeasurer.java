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
 */
package org.cicirello.sequences.distance;



/**
 * <p>Extend this abstract class to define a distance metric for permutations where distance is an integer value.  To extend this class,
 * you must implement the method {@link org.cicirello.sequences.distance.AbstractSequenceDistanceMeasurer#distance(org.cicirello.sequences.Sequence,org.cicirello.sequences.Sequence)}.
 * In doing so, your distance measure will in turn support arrays of any primitive type, as well as Strings, as the default implementations of all of the other distance and
 * distancef methods provided in this abstract class delegate computation to that method.</p>
 *
 * <p>If your sequences are guaranteed not to contain duplicates, and the pair of sequences is guaranteed to contain the same set of elements, and are of the same length,
 * then consider instead extending or using the classes that extend 
 * the {@link org.cicirello.permutations.distance.AbstractPermutationDistanceMeasurer AbstractPermutationDistanceMeasurer}
 * class.  Those classes are specifically for distance between permutations of the integers from 0 to N-1.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.28
 * @since 1.1
 */
abstract class AbstractSequenceDistanceMeasurer implements SequenceDistanceMeasurer, SequenceDistanceMeasurerDouble {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(long[] s1, long[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(int[] s1, int[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(short[] s1, short[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(byte[] s1, byte[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(char[] s1, char[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(double[] s1, double[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(float[] s1, float[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(boolean[] s1, boolean[] s2) {
		return distancef(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(String s1, String s2) {
		return distancef(new PrimitiveSequence(s1.toCharArray()),new PrimitiveSequence(s2.toCharArray()));
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(long[] s1, long[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(int[] s1, int[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(short[] s1, short[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(byte[] s1, byte[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(char[] s1, char[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(double[] s1, double[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(float[] s1, float[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(boolean[] s1, boolean[] s2) {
		return distance(new PrimitiveSequence(s1),new PrimitiveSequence(s2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int distance(String s1, String s2) {
		return distance(new PrimitiveSequence(s1.toCharArray()),new PrimitiveSequence(s2.toCharArray()));
	}
	
	/**
	 * Measures the distance between two Sequences.
	 * 
	 * @param s1 First String.
	 * @param s2 Second String.
	 * @param <T> Type of element in sequence
	 * @return distance between s1 and s2
	 */
	<T> int distance(Sequence<T> s1, Sequence<T> s2) {
		return distance(s1,s2);
	}
	
	/**
	 * Measures the distance between two Sequences.
	 * 
	 * @param s1 First String.
	 * @param s2 Second String.
	 * @param <T> Type of element in sequence
	 * @return distance between s1 and s2
	 */
	<T> double distancef(Sequence<T> s1, Sequence<T> s2) {
		return distance(s1,s2);
	}
	
}