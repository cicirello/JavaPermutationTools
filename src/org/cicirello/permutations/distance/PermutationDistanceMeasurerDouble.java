/*
 * Copyright 2010, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of package org.cicirello.permutations.distance.
 *
 * Java package org.cicirello.permutations.distance is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * Java package org.cicirello.permutations.distance is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java package org.cicirello.permutations.  If not, 
 * see <http://www.gnu.org/licenses/>.
 *
 */
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
 * Implement this interface, PermutationDistanceMeasurerDouble, to define a distance metric for permutations,
 * where the distance is a floating-point value.
 * 
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 * @version 1.18.6.20
 * @since 1.0
 */
public interface PermutationDistanceMeasurerDouble
{
	/**
	 * Measures the distance between two permutations
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 */
	double distancef(Permutation p1, Permutation p2);
	
	/*
	 * Measures the distance between two permutations, with reversal independence:
	 * distance = min { distance(p1,p2), distance(p1,reverse(p2)) }
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 */
	//double distancefReversalIndependent(Permutation p1, Permutation p2);
	
	/*
	 * Measures the distance between two permutations, with cyclic independence:
	 * distance = min_{i in [0,N)} distance(p1,rotate(p2,i))
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 */
	//double distancefCyclicIndependent(Permutation p1, Permutation p2);
	
	/*
	 * Measures the distance between two permutations, with cyclic and reversal independence:
	 * distance = min_{i in [0,N)} { distance(p1,rotate(p2,i)), distance(p1,rotate(reverse(p2),i)) }
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 */
	//double distancefCyclicReversalIndependent(Permutation p1, Permutation p2);
}