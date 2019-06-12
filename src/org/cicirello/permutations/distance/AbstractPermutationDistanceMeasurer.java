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
 * @version 1.19.6.12
 * @since 1.0
 *
 */
abstract class AbstractPermutationDistanceMeasurer implements PermutationDistanceMeasurer, NormalizedPermutationDistanceMeasurer {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double distancef(Permutation p1, Permutation p2) {
		return distance(p1,p2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double maxf(int length) {
		return max(length);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double normalizedDistance(Permutation p1, Permutation p2) {
		int m = max(p1.length());
		if (m==0) return 0;
		return 1.0 * distance(p1,p2) / m;
	}
}
