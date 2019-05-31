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

/**
 * <p>Extend this abstract class to define a distance metric for 
 * permutations where distance is an integer value.</p>
 *
 * <p>If your sequences are guaranteed not to contain duplicates, and 
 * the pair of sequences is guaranteed to contain the same set of elements, and are of the same length,
 * then consider instead extending or using the classes that extend 
 * the AbstractPermutationDistanceMeasurer
 * class.  Those classes are specifically for distance between permutations of the integers from 0 to N-1.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.19.5.30
 * @since 1.1
 */
abstract class AbstractSequenceDistanceMeasurer implements SequenceDistanceMeasurer, SequenceDistanceMeasurerDouble {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(long[] s1, long[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(int[] s1, int[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(short[] s1, short[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(byte[] s1, byte[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(char[] s1, char[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(double[] s1, double[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(float[] s1, float[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(boolean[] s1, boolean[] s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(String s1, String s2) {
		return distance(s1, s2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(Object[] s1, Object[] s2) {
		return distance(s1, s2);
	}
}