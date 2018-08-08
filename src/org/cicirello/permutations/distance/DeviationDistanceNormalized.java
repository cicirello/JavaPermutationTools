/*
 * Copyright 2014, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
 * Normalized Deviation Distance:
 *
 * <p>Normalized Deviation distance is the sum of the positional deviation of the permutation elements
 * divided by N-1 (where N is the length of the permutation).
 * The positional deviation of an element is the difference in its location in the two
 * permutations.</p>
 *
 * <p>For example, consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [1, 0, 5, 2, 4, 3].
 * Element 0 is displaced by 1 position.  Likewise for elements 1 and 2.
 * Element 3 is displaced by 2 positions.
 * Element 4 is in the same position in both.
 * Element 5 is displaced by 3 positions.</p> 
 *
 * <p>Sum the deviations: 1 + 1 + 1 + 2 + 0 + 3 = 8.</p>  
 *
 * <p>The length is 6.  So, normalized deviation distance is 8 / 6 = 1.3333.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 * 
 * <p>Normalized deviation distance was introduced in the following (although they simply
 * referred to it as deviation distance):<br>
 *  V. Campos, M. Laguna, and R. Marti, "Context-independent scatter and tabu search for permutation problems," 
 *  INFORMS Journal on Computing, vol. 17, no. 1, pp. 111–122, 2005.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.2  
 * @since 1.0
 * 
 */
public class DeviationDistanceNormalized implements PermutationDistanceMeasurerDouble  {

	private DeviationDistance devDistance;
	
	/**
	 * Construct the distance measure.
	 */
	public DeviationDistanceNormalized() {
		devDistance = new DeviationDistance();
	}
	
	/**
	 * This method implements the normalized deviation distance.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(Permutation p1, Permutation p2) {
		if (p1.length() == 1) return 0; 
		return devDistance.distancef(p1,p2) / (p1.length() - 1);
	}

}