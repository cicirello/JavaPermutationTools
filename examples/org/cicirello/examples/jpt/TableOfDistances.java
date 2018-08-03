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
 *
 */

package org.cicirello.examples.jpt;

import org.cicirello.permutations.Permutation;
import org.cicirello.permutations.distance.*;

/**
 * <p>Simple example illustrating: (a) using an iterator to iterate over all permutations of a
 * given length, and (b) computing the distance between a pair of permutations.  The example
 * generates a table of distances to a randomly selected permutation of length 4.  Table contains
 * one row for each of the 4! = 24 permutations of length 4, and columns for several of the distance
 * metrics from the library.</p>
 *
 * <p>Note: In general, when using the PermutationIterator, please be aware that there are N! possible 
 * permutations of length N.  Unless the permutation length is small, you will likely either want to 
 * find an alternative approach for whatever it is you're doing, or you'll need a secondary way 
 * out of the iteration.</p>
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class TableOfDistances {
	
	public static void main(String[] args) {
		// list of distance metrics used in example
		PermutationDistanceMeasurer[] d = {
			new AcyclicEdgeDistance(),
			new CyclicEdgeDistance(),
			new RTypeDistance(),
			new CyclicRTypeDistance(),
			new DeviationDistance(),
			new SquaredDeviationDistance(),
			new LeeDistance(),
			new KendallTauDistance(),
			new ReinsertionDistance(),
			new ExactMatchDistance(),
			new InterchangeDistance()
		};
		
		// Generate a random permutation of length 4 (deliberately short due to what this example does).
		Permutation p = new Permutation(4);
		
		// output a table header
		System.out.print("Permutation");
		for (int i = 0; i < d.length; i++) {
			System.out.printf("\td%d", i);
		}
		System.out.println();
		
		// The Permutation class implements Iterable in such a way to enable iterating
		// over all permutations of the length of that permutation.  First iteration is the
		// very permutation.
		for (Permutation q : p) {
			// Permutation implements toString so we can just print one if we need to.
			System.out.print(q);
			
			// iterate over the array of distances we initialized previously
			for (int i = 0; i < d.length; i++) {
				System.out.printf("\t%3d", d[i].distance(p, q));
			}
			System.out.println();
		}
		System.out.println();
		
		// output a list of the distance measures to identify the column headings from above
		System.out.println("Distance measures used above");
		for (int i = 0; i < d.length; i++) {
			System.out.printf("d%d: %s\n", i, d[i].getClass().getSimpleName());
		}
	}
}
