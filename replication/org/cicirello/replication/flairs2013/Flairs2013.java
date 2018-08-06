/*
 * Copyright 2012-2013, 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.replication.flairs2013;

import org.cicirello.permutations.*;
import org.cicirello.permutations.distance.*;
import org.cicirello.math.stats.Statistics;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>This program computes (a) the correlations between distance measures of Tables 1 and 2; and 
 * (b) the average distances induced by mutation operators for Figures 1, 2, 3, 4, and 5 from the 
 * paper:<br>
 * V. A. Cicirello and R. Cernera, <a href="https://www.cicirello.org/publications/cicirello2013flairs.html">"Profiling the distance characteristics 
 * of mutation operators for permutation-based genetic algorithms,"</a> 
 * in Proceedings of the 26th FLAIRS Conference. AAAI Press, May 2013, pp. 46–51.</p> 
 *
 * <p>NOTE: This is not the original code from this paper.  Source code has been heavily refactored since then, but results should correspond,
 * at least statistically.  One of the refactorings involved using a different, more efficient random number generator.  Since the data for
 * the figures is derived from a randomized process, the data is not identical to that used originally, but should correspond statistically.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class Flairs2013 {
	
	public static void main(String[] args) {
	
		// Code to replicate the data from Tables 1 and 2.
		
		// list of distance metrics used
		PermutationDistanceMeasurerDouble[] d = { 
			new ExactMatchDistance(), 
			new DeviationDistanceNormalized(), 
			new RTypeDistance(), 
			new EditDistance(), // defaults give equivalent of ReinsertionDistance 
			new InterchangeDistance() 
		};
		
		Permutation p = new Permutation(10, 0);
		
		final int FACT = 3628800;
		
		double[][] data = new double[d.length][FACT];
		
		// warning: following iterates over all permutations of length 10
		// Compute distance to reference permutation from all permutations of length 10 for all distances in d
		int i = 0;
		for (Permutation q : p) {
			for (int j = 0; j < d.length; j++) {
				data[j][i] = d[j].distancef(p, q);
			}
			i++;
		}
		
		// compute correlation matrix
		double[][] r = Statistics.correlationMatrix(data);
		
		System.out.println("Data from Tables 1 and 2 of the FLAIRS 2013 paper can be extracted from the following correlation matrix.");
		System.out.println("Note: columns are in same order as rows.\n");
		for (i = 0; i < r.length; i++) {
			System.out.printf("%27s", d[i].getClass().getSimpleName());
			for (int j = 0; j < r[i].length; j++) {
				System.out.printf("\t%6.3f", r[i][j]);
			}
			System.out.println();
		}
		
		
		// Generate the data that corresponds to Figures 1 through 5 of the paper
		
		int f = 1;
		System.out.println();

		double[] distanceSamples = new double[10000];
		// iterate over distance measures (one figure for each)
		for (PermutationDistanceMeasurerDouble measurer : d) {
			System.out.println("Figure " + f + " data for distance measure " + measurer.getClass().getSimpleName());
			f++;
			System.out.printf("%s\t%9s\t%9s\t%9s\t%9s\n", "Length", "Insertion", "Swap", "Scramble", "Reversal");
			for (int n = 16; n <= 1024; n *= 2) {
				System.out.printf("%6d", n);
				for (int j = 0; j < distanceSamples.length; j++) {
					p = new Permutation(n);
					Permutation neighbor = new Permutation(p);
					int[] indices = getRandomIndexPair(n);
					neighbor.removeAndInsert(indices[0], indices[1]);
					distanceSamples[j] = measurer.distancef(p, neighbor);
				}
				System.out.printf("\t%9.3f", Statistics.mean(distanceSamples));
				for (int j = 0; j < distanceSamples.length; j++) {
					p = new Permutation(n);
					Permutation neighbor = new Permutation(p);
					int[] indices = getRandomIndexPair(n);
					neighbor.swap(indices[0], indices[1]);
					distanceSamples[j] = measurer.distancef(p, neighbor);
				}
				System.out.printf("\t%9.3f", Statistics.mean(distanceSamples));
				for (int j = 0; j < distanceSamples.length; j++) {
					p = new Permutation(n);
					Permutation neighbor = new Permutation(p);
					int[] indices = getRandomIndexPair(n);
					neighbor.scramble(indices[0], indices[1]);
					distanceSamples[j] = measurer.distancef(p, neighbor);
				}
				System.out.printf("\t%9.3f", Statistics.mean(distanceSamples));
				for (int j = 0; j < distanceSamples.length; j++) {
					p = new Permutation(n);
					Permutation neighbor = new Permutation(p);
					int[] indices = getRandomIndexPair(n);
					neighbor.reverse(indices[0], indices[1]);
					distanceSamples[j] = measurer.distancef(p, neighbor);
				}
				System.out.printf("\t%9.3f", Statistics.mean(distanceSamples));
				System.out.println();
			}
			System.out.println();
		}
		
	}
	
	private static int[] getRandomIndexPair(int length) {
		int[] pair = new int[2];
		pair[0] = ThreadLocalRandom.current().nextInt(length);
		pair[1] = ThreadLocalRandom.current().nextInt(length-1);
		if (pair[1] >= pair[0]) {
			pair[1]++;
		} 
		return pair;
	}
}


