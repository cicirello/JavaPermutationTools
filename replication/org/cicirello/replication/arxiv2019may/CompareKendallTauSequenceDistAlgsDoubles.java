/*
 * Copyright 2018-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.replication.arxiv2019may;

import org.cicirello.sequences.distance.KendallTauSequenceDistance;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.management.*;

/**
 * <p>This program replicates the data for the paper:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2019arXiv.html" target=_top>"Kendall Tau
 * Sequence Distance: Extending Kendall Tau from Ranks to Sequences,"</a> 
 * arXiv preprint arXiv:1905.02752 [cs.DM], May 2019.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class CompareKendallTauSequenceDistAlgsDoubles {
	
	public static void main(String[] args) {
		KendallTauSequenceDistance dHash = new KendallTauSequenceDistance();
		KendallTauSequenceDistance dSort = new KendallTauSequenceDistance(true);
		final int MIN_L = 256;
		final int MAX_L = 131072;
		final int N = 100;
		int[] alphabetSize = {1, 4, 16, 64, 256, 1024, 4096, 16384, 65536};
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		System.out.printf("%8s\t%8s\t%8s\t%8s\n", "L", "Alphabet", "TimeHash", "TimeSort");
		for (int L = MIN_L; L <= MAX_L; L *= 2) {
			for (int j = 0; j < alphabetSize.length; j++) {
				int R = alphabetSize[j];
				//if (R < 1) R = 1;
				double[][] iArrays = new double[N][];
				double[][] iShuffled = new double[N][];
				for (int i = 0; i < N; i++) {
					iArrays[i] = randDoubleSequence(L, R);
					iShuffled[i] = shuffleCopy(iArrays[i]);
				}
				System.out.printf("%8d\t%8d", L, R);
				long start = bean.getCurrentThreadCpuTime();
				for (int i = 0; i < N; i++) {
					int d = dHash.distance(iArrays[i], iShuffled[i]);
				}
				long end = bean.getCurrentThreadCpuTime();
				double seconds = 1.0*(end-start)/1000000000/N;
				System.out.printf("\t%8.6f", seconds);
				start = bean.getCurrentThreadCpuTime();
				for (int i = 0; i < N; i++) {
					int d = dSort.distance(iArrays[i], iShuffled[i]);
				}
				end = bean.getCurrentThreadCpuTime();
				seconds = 1.0*(end-start)/1000000000/N;
				System.out.printf("\t%8.6f", seconds);
				System.out.println();
			}
		}
	}
	
	
	public static double[] shuffleCopy(double[] s) {
		double[] r = s.clone();
		for (int i = s.length-1; i > 0; i--) {
			int j = ThreadLocalRandom.current().nextInt(i+1);
			if (i==j) continue;
			double temp = r[i];
			r[i] = r[j];
			r[j] = temp;
		}
		return r;
	}
	
	
	
	public static double[] randDoubleSequence(int length, int range) {
		double[] s = new double[length];
		for (int i = 0; i < length; i++) {
			s[i] = 1.0*ThreadLocalRandom.current().nextInt(range);
		}
		return s;
	}
	
	
	
}