/*
 * Copyright 2015-2016, 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.replication.ieeetevc2016;

import org.cicirello.permutations.*;
import org.cicirello.permutations.distance.*;
import org.cicirello.math.stats.Statistics;

/**
 * <p>This program computes the fitness distance correlations for the "Permutation in a Haystack" fitness landscapes 
 * found in Table II of the paper:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2016evc.html">"The Permutation in a Haystack Problem 
 * and the Calculus of Search Landscapes,"</a> 
 * IEEE Transactions on Evolutionary Computation, 20(3):434-446, June 2016.</p>
 *
 * <p>NOTE: This is not the original code from this paper.  Source code has been heavily refactored since then, but results should correspond.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class FDC {
	
	public static void main(String[] args) {
		
		// list of distance metrics used
		PermutationDistanceMeasurer[] d = new PermutationDistanceMeasurer[26];
		d[0] = new ExactMatchDistance();
		d[1] = new InterchangeDistance();
		d[2] = new DeviationDistance();
		d[3] = new AcyclicEdgeDistance();
		d[4] = new CyclicEdgeDistance();
		d[5] = new RTypeDistance();
		d[6] = new CyclicRTypeDistance();
		d[7] = new KendallTauDistance();
		d[8] = new ReinsertionDistance();
		
		// warning: this next is slow: brute force breadth first computation of reversal distance for all 10! permutations of length 10
		d[9] = new ReversalDistance(10);
		
		d[10] = new ScrambleDistance();
		d[11] = new ReversalIndependentDistance(d[7]);
		d[12] = new ReversalIndependentDistance(d[1]);
		d[13] = new ReversalIndependentDistance(d[8]);
		d[14] = new ReversalIndependentDistance(d[9]);
		d[15] = new ReversalIndependentDistance(d[10]);
		d[16] = new CyclicReversalIndependentDistance(d[7]);
		d[17] = new CyclicReversalIndependentDistance(d[1]);
		d[18] = new CyclicReversalIndependentDistance(d[8]);
		d[19] = new CyclicReversalIndependentDistance(d[9]);
		d[20] = new CyclicReversalIndependentDistance(d[10]);
		d[21] = new CyclicIndependentDistance(d[7]);
		d[22] = new CyclicIndependentDistance(d[1]);
		d[23] = new CyclicIndependentDistance(d[8]);
		d[24] = new CyclicIndependentDistance(d[9]);
		d[25] = new CyclicIndependentDistance(d[10]);
		
		// table element [i][j] must be correlation coefficient of distance d[i] and distance d[distanceIndices[i][j]]
		int[][] distanceIndices = { 
			{7, 1, 8, 9, 10}, // 0
			{7, 1, 8, 9, 10}, // 1
			{7, 1, 8, 9, 10}, // 2
			{11, 12, 13, 14, 15}, // 3 need reversal independence
			{16, 17, 18, 19, 20}, // 4 need reversal and cyclic independence
			{7, 1, 8, 9, 10}, // 5
			{21, 22, 23, 24, 25}, // 6 need cyclic independence
			{7, 1, 8, 9, 10}, // 7
			{7, 1, 8, 9, 10}  // 8
		};
		
		Permutation p = new Permutation(10, 0);
		
		final int FACT = 3628800;
		
		int[][] data = new int[d.length][FACT];
		
		// warning: following iterates over all permutations of length 10
		// Compute distance to reference permutation from all permutations of length 10 for all distances in d
		int i = 0;
		for (Permutation q : p) {
			for (int j = 0; j < d.length; j++) {
				data[j][i] = d[j].distance(p, q);
			}
			i++;
		}
		
		System.out.println("Table of fitness distance correlations for several \"Permutation in a Haystack\" landscapes and distances.");
		System.out.println("Essentially generates the data in Table 2 of \"The Permutation in a Haystack Problem and the Calculus of Search Landscapes\"");
		System.out.println("by Vincent A. Cicirello, from IEEE Transactions on Evolutionary Computation, 20(3): 434-446, June 2016.");
		System.out.println("NOTE: This is not the original code.  Source code has been heavily refactored since, but results should correspond.");
		
		System.out.println();
		System.out.println("Haystack Landscape \t    AdjSwap\tInterchange\tReinsertion\t   Reversal\t   Scramble");
		for (i = 0; i < distanceIndices.length; i++) {
			System.out.printf("%19s", d[i].getClass().getSimpleName());
			for (int j = 0; j < distanceIndices[i].length; j++) {
				int k = distanceIndices[i][j];
				System.out.printf("\t%11.3f", Statistics.correlation(data[i], data[k]));
			}
			System.out.println();
		}
	}
}