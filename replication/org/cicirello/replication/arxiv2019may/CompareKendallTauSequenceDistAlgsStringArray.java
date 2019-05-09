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
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2019arXiv.html">"Kendall Tau
 * Sequence Distance: Extending Kendall Tau from Ranks to Sequences,"</a> 
 * arXiv preprint arXiv:1905.02752 [cs.DM], May 2019.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class CompareKendallTauSequenceDistAlgsStringArray {
	
	public static void main(String[] args) {
		KendallTauSequenceDistance dHash = new KendallTauSequenceDistance();
		KendallTauSequenceDistance dSort = new KendallTauSequenceDistance(true);
		final int MIN_L = 256;
		final int MAX_L = 16384;
		final int N = 10;
		int[] alphabetSize = {256}; //, 16384, 65536};
		int[] stringLength = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		System.out.println("High Cost Comparisons");
		System.out.printf("%8s\t%8s\t%8s\t%8s\t%8s\n", "L", "Alphabet", "StrL", "TimeHash", "TimeSort");
		for (int L = MIN_L; L <= MAX_L; L *= 2) {
			for (int R : alphabetSize) {
				for (int SL : stringLength) {
					String[][] sArray = new String[N][];
					String[][] sShuffled = new String[N][];
					for (int i = 0; i < N; i++) {
						sArray[i] = randStringArray(L, R, SL);
						sShuffled[i] = shuffleCopy(sArray[i]);
					}
					System.out.printf("%8d\t%8d\t%8d", L, R, SL);
					long start = bean.getCurrentThreadCpuTime();
					for (int i = 0; i < N; i++) {
						int d = dHash.distance(sArray[i], sShuffled[i]);
					}
					long end = bean.getCurrentThreadCpuTime();
					double seconds = 1.0*(end-start)/1000000000/N;
					System.out.printf("\t%8.6f", seconds);
					start = bean.getCurrentThreadCpuTime();
					for (int i = 0; i < N; i++) {
						int d = dSort.distance(sArray[i], sShuffled[i]);
					}
					end = bean.getCurrentThreadCpuTime();
					seconds = 1.0*(end-start)/1000000000/N;
					System.out.printf("\t%8.6f", seconds);
					
					
					System.out.println();
					System.out.flush();
				}
			}
		}
		
		System.out.println("\n\nLow Cost Comparisons");
		
		System.out.printf("%8s\t%8s\t%8s\t%8s\t%8s\n", "L", "Alphabet", "StrL", "TimeHash", "TimeSort");
		for (int L = MIN_L; L <= MAX_L; L *= 2) {
			for (int R : alphabetSize) {
				for (int SL : stringLength) {
					String[][] sArray = new String[N][];
					String[][] sShuffled = new String[N][];
					for (int i = 0; i < N; i++) {
						sArray[i] = randStringArrayEasy(L, R, SL);
						sShuffled[i] = shuffleCopy(sArray[i]);
					}
					System.out.printf("%8d\t%8d\t%8d", L, R, SL);
					long start = bean.getCurrentThreadCpuTime();
					for (int i = 0; i < N; i++) {
						int d = dHash.distance(sArray[i], sShuffled[i]);
					}
					long end = bean.getCurrentThreadCpuTime();
					double seconds = 1.0*(end-start)/1000000000/N;
					System.out.printf("\t%8.6f", seconds);
					start = bean.getCurrentThreadCpuTime();
					for (int i = 0; i < N; i++) {
						int d = dSort.distance(sArray[i], sShuffled[i]);
					}
					end = bean.getCurrentThreadCpuTime();
					seconds = 1.0*(end-start)/1000000000/N;
					System.out.printf("\t%8.6f", seconds);
					
					
					System.out.println();
					System.out.flush();
				}
			}
		}
	}
	
	
	public static String[] shuffleCopy(String[] s) {
		String[] r = s.clone();
		for (int i = s.length-1; i > 0; i--) {
			int j = ThreadLocalRandom.current().nextInt(i+1);
			if (i==j) continue;
			String temp = r[i];
			r[i] = r[j];
			r[j] = temp;
		}
		return r;
	}
	
	public static String[] randStringArray(int length, int range, int strLength) {
		String[] s = new String[length];
		for (int i = 0; i < length; i++) {
			char c = (char)ThreadLocalRandom.current().nextInt(range);
			s[i] = randString(strLength, c);
		}
		return s;
	}
	
	public static String[] randStringArrayEasy(int length, int range, int strLength) {
		String[] s = new String[length];
		for (int i = 0; i < length; i++) {
			char c = (char)ThreadLocalRandom.current().nextInt(range);
			s[i] = randStringEasy(strLength, c);
		}
		return s;
	}
	
	public static String randString(int length, char c) {
		char[] s = new char[length];
		for (int i = 0; i < length - 1; i++) {
			s[i] = (char)0;
		}
		s[length - 1] = c;
		return new String(s);
	}
	
	public static String randStringEasy(int length, char c) {
		char[] s = new char[length];
		for (int i = 0; i < length - 1; i++) {
			s[i] = c;
		}
		s[length - 1] = c;
		return new String(s);
	}
}