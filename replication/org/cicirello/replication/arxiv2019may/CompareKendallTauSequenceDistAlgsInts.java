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
import java.lang.ref.WeakReference;

/**
* <p>This program replicates the data for the paper:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2019arXiv.html" target=_top>"Kendall Tau
 * Sequence Distance: Extending Kendall Tau from Ranks to Sequences,"</a> 
 * arXiv preprint arXiv:1905.02752 [cs.DM], May 2019.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class CompareKendallTauSequenceDistAlgsInts {
	
	public static int toTime(KendallTauSequenceDistance d, int[][] a, int[][] b) {
		// Summing and returning the sum of distances is
		// just to prevent the Java JIT from considering this dead code and optimizing it away.
		int total = 0;
		for (int i = 0; i < a.length; i++) {
			int distance = d.distance(a[i], b[i]);
			total += distance;
		}
		return total;
	}
	
	public static void main(String[] args) {
		KendallTauSequenceDistance dHash = new KendallTauSequenceDistance();
		KendallTauSequenceDistance dSort = new KendallTauSequenceDistance(true);
		final int MIN_L = 256;
		final int MAX_L = 131072;
		final int N = 100;
		int[] alphabetSize = {1, 4, 16, 64, 256, 1024, 4096, 16384, 65536};
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		bean.setThreadCpuTimeEnabled(true);
		// WARM UP THE JAVA JIT.
		int[][] iArrays = new int[N*N][];
		int[][] iShuffled = new int[N*N][];
		for (int i = 0; i < iArrays.length; i++) {
			iArrays[i] = randIntSequence(1000, 100);
			iShuffled[i] = shuffleCopy(iArrays[i]);
		}
		toTime(dHash, iArrays, iShuffled);
		toTime(dSort, iArrays, iShuffled);
		// force garbage collection of extra large arrays used during warmup
		WeakReference ref1 = new WeakReference<Object>(iArrays);
		WeakReference ref2 = new WeakReference<Object>(iShuffled);
		iArrays = null;
		iShuffled = null;
		while(ref1.get() != null || ref2.get() != null) {
			System.gc();
		}
		// END WARM UP PHASE.
		System.out.printf("%8s\t%8s\t%8s\t%8s\n", "L", "Alphabet", "TimeHash", "TimeSort");
		int x = 0;
		for (int L = MIN_L; L <= MAX_L; L *= 2) {
			for (int j = 0; j < alphabetSize.length; j++) {
				int R = alphabetSize[j];
				//if (R < 1) R = 1;
				iArrays = new int[N][];
				iShuffled = new int[N][];
				for (int i = 0; i < N; i++) {
					iArrays[i] = randIntSequence(L, R);
					iShuffled[i] = shuffleCopy(iArrays[i]);
				}
				System.out.printf("%8d\t%8d", L, R);
				long start = bean.getCurrentThreadCpuTime();
				int dSum1 = toTime(dHash, iArrays, iShuffled);
				long end = bean.getCurrentThreadCpuTime();
				double seconds = 1.0*(end-start)/1000000000/N;
				System.out.printf("\t%8.6f", seconds);
				start = bean.getCurrentThreadCpuTime();
				int dSum2 = toTime(dSort, iArrays, iShuffled);
				end = bean.getCurrentThreadCpuTime();
				seconds = 1.0*(end-start)/1000000000/N;
				System.out.printf("\t%8.6f", seconds);
				System.out.println();
				// Do something with the return values to trick Java JIT.
				x += (dSum1 - dSum2);
			}
		}
		System.out.println("Done " + x);
	}
	
	
	public static int[] shuffleCopy(int[] s) {
		int[] r = s.clone();
		for (int i = s.length-1; i > 0; i--) {
			int j = ThreadLocalRandom.current().nextInt(i+1);
			if (i==j) continue;
			int temp = r[i];
			r[i] = r[j];
			r[j] = temp;
		}
		return r;
	}
	
	
	
	public static int[] randIntSequence(int length, int range) {
		int[] s = new int[length];
		for (int i = 0; i < length; i++) {
			s[i] = ThreadLocalRandom.current().nextInt(range);
		}
		return s;
	}
	
	
	
}