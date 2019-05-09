/*
 * Copyright 2010, 2015, 2017-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * along with JavaPermutationTools.  If not, see <http://www.gnu.org/licenses/>. *
 */
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
 * Extend this abstract class to define a distance metric for permutations
 * where distance is an integer value.
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.5.9
 * @since 1.0
 *
 */
abstract class AbstractPermutationDistanceMeasurer implements PermutationDistanceMeasurer, PermutationDistanceMeasurerDouble {
	
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	final public double distancef(Permutation p1, Permutation p2) {
		return distance(p1,p2);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * This method is supported by any extending class that implements the max method.
	 * If the max method is not supported by this class, then neither is maxf.  Consult the
	 * documentation of the max method for support information.
	 *
	 * @throws UnsupportedOperationException If this class doesn't support the max method.
	 */
	@Override
	final public double maxf(int length) {
		return max(length);
	}
	
	
	/**
	 * <p>Measures the distance between two permutations, normalized to the interval [0.0, 1.0].</p>
	 *
	 * <p>This method is supported by any implementing class that implements the max method.
	 * If max is unsupported, then so is normalizedDistance.  Please consult the documentation
	 * of max for support information.</p>
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 * @throws UnsupportedOperationException If this class doesn't support the max method.
	 * @since 1.2.4
	 */
	@Override
	final public double normalizedDistance(Permutation p1, Permutation p2) {
		int m = max(p1.length());
		if (m==0) return 0;
		return distancef(p1,p2) / m;
	}
}
