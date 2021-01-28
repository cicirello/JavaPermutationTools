/*
 * Copyright 2019-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * <p>The original version of Normalized Deviation distance (Ronald, 1998) is the 
 * sum of the positional deviation of the permutation elements
 * divided by N-1 (where N is the length of the permutation).
 * The positional deviation of an element is the difference in its location in the two
 * permutations.  Normalizing by dividing by N-1 causes each element's contribution to 
 * distance to be in the interval [0,1].</p>
 *
 * <p>Sevaux and Sorensen (2005) suggested a different normalizing factor that provides a distance
 * in the interval [0,1].  Maximal distance occurs for an inverted permutation.  The normalizing
 * factor is (N<sup>2</sup>/2) when N is even and (N<sup>2</sup>-1)/2 when N is odd.</p>
 *
 * <p>For example, consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [1, 0, 5, 2, 4, 3].
 * Element 0 is displaced by 1 position.  Likewise for elements 1 and 2.
 * Element 3 is displaced by 2 positions.
 * Element 4 is in the same position in both.
 * Element 5 is displaced by 3 positions.</p> 
 *
 * <p>Sum the deviations: 1 + 1 + 1 + 2 + 0 + 3 = 8.</p>  
 *
 * <p>The length is 6, which is even, so we'll divide by 18.  So, 
 * normalized deviation distance is 8 / 18 = 0.444...</p>
 *
 * <p>If instead, p2 = [5, 4, 3, 2, 1, 0], then 0 and 5 are both displaced by 5 positions, 1 and 4
 * are displaced by 3 positions, and 2 and 3 are displaced by 1 position.  Sum of deviations is
 * then: 2 * 5 + 2 * 3 + 2 * 1 = 18.  The length is still 6, so we again divide by 18, and distance is 1.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 * 
 * <p>Original normalized deviation distance was introduced in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in 
 * Proc. IEEE CEC. IEEE Press, 1998, pp. 558–563.</p>
 *
 * <p>This version of normalized deviation distance was introduced in:<br>
 * M. Sevaux and K Sorensen, "Permutation distance measures for memetic algorithms with population
 * management," in Proc. of MIC2005, 2005.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.28.2021  
 * 
 */
public final class DeviationDistanceNormalized2005 implements PermutationDistanceMeasurerDouble, NormalizedPermutationDistanceMeasurerDouble  {

	private DeviationDistance devDistance;
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public DeviationDistanceNormalized2005() {
		devDistance = new DeviationDistance();
	}
	
	@Override
	public double distancef(Permutation p1, Permutation p2) {
		if (p1.length() <= 1) return 0; 
		return devDistance.distancef(p1,p2) * 2.0 / (p1.length() * p1.length() - (p1.length() & 1));
	}
	
	@Override
	public double maxf(int length) {
		if (length <= 1) return 0;
		return 1.0;
	}
	
	@Override
	public double normalizedDistance(Permutation p1, Permutation p2) {
		return distancef(p1,p2); 
	}
	
}