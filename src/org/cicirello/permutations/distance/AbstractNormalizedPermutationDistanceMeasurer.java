/*
 * Copyright 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * where distance is an integer value, and which supports normalizing the distance. 
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.5.10
 * @since 1.2.5
 *
 */
abstract class AbstractNormalizedPermutationDistanceMeasurer extends AbstractPermutationDistanceMeasurer implements NormalizedPermutationDistanceMeasurer, NormalizedPermutationDistanceMeasurerDouble {
	
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double boundf(int length) {
		return max(length);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int bound(int length) {
		return max(length);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double normalizedByBound(Permutation p1, Permutation p2) {
		return normalizedDistance(p1, p2);
	}
}
