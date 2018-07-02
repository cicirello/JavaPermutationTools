/*
 * Copyright 2010, 2014, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Interchange Distance:
 *
 * <p>Interchange distance is the minimum number of swaps necessary to transform one
 * permutation into the other.</p>
 *
 * <p>Interchange distance is computed efficiently by counting the
 * number of permutation cycles between the permutations. A permutation
 * cycle of length k is transformed into k fixed points with
 * k − 1 swaps (a fixed point is a cycle of length 1).</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 * 
 * <p>Computing Interchange distance using cycle lengths is described in:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2016evc.html">"The Permutation in a Haystack Problem 
 * and the Calculus of Search Landscapes,"</a> 
 * IEEE Transactions on Evolutionary Computation, 20(3):434-446, June 2016.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 * @version 06.06.2018
 */
public class InterchangeDistance extends PermutationDistanceMeasurerBase
{
	
	
	
	
    /**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2)
	{
	    int numSwaps = 0;
	    int length = p1.length();
        boolean[] used = new boolean[length];
        for (int k = 0; k < length; k++)
        {
            if (p1.get(k) == p2.get(k)) used[p1.get(k)] = true;   
        }
        int i = 0;
        for (i = 0; i < used.length; i++) {
            if (!used[p1.get(i)]) { break; }  
        }
        if (i >= used.length) return 0;
		int iLast = i;
		
		int[] invP1 = p1.getInverse();
        boolean done = true;
        do {
            done = true;
            int cycleSize = 0;
            int j = p1.get(i);
            while (!used[j]) {
                 used[j] = true;
                 cycleSize++;
                 j = p2.get(i);
				 i = invP1[j];
            }
            numSwaps += (cycleSize-1);
            for (i = iLast + 1; i < used.length; i++) {
                if (!used[p1.get(i)]) { done = false; break; }  
            }
			iLast = i;
        } while (!done);
        return numSwaps;
	}
}
