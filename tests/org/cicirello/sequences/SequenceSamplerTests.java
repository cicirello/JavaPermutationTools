/*
 * Copyright 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.HashMap;

/**
 * JUnit 4 tests for the SequenceSampler class.
 */
public class SequenceSamplerTests {
	
	private final static double EPSILON = 1e-10;
	
	
	// int tests
	
	@Test
	public void testSampleReservoirInt() {
		for (int n = 1; n < 9; n++) {
			int[] allDiff = new int[n];
			int[] mixedQuantities = new int[n];
			int[] allSame = new int[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolInt() {
		for (int n = 1; n < 9; n++) {
			int[] allDiff = new int[n];
			int[] mixedQuantities = new int[n];
			int[] allSame = new int[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionInt() {
		for (int n = 1; n < 9; n++) {
			int[] allDiff = new int[n];
			int[] mixedQuantities = new int[n];
			int[] allSame = new int[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInt() {
		for (int n = 1; n < 9; n++) {
			int[] allDiff = new int[n];
			int[] mixedQuantities = new int[n];
			int[] allSame = new int[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// long tests
	
	@Test
	public void testSampleReservoirLong() {
		for (int n = 1; n < 9; n++) {
			long[] allDiff = new long[n];
			long[] mixedQuantities = new long[n];
			long[] allSame = new long[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolLong() {
		for (int n = 1; n < 9; n++) {
			long[] allDiff = new long[n];
			long[] mixedQuantities = new long[n];
			long[] allSame = new long[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionLong() {
		for (int n = 1; n < 9; n++) {
			long[] allDiff = new long[n];
			long[] mixedQuantities = new long[n];
			long[] allSame = new long[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleLong() {
		for (int n = 1; n < 9; n++) {
			long[] allDiff = new long[n];
			long[] mixedQuantities = new long[n];
			long[] allSame = new long[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	
	// short tests
	
	@Test
	public void testSampleReservoirShort() {
		for (int n = 1; n < 9; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = (short)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolShort() {
		for (int n = 1; n < 9; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = (short)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionShort() {
		for (int n = 1; n < 9; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = (short)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleShort() {
		for (int n = 1; n < 9; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = (short)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// byte tests
	
	@Test
	public void testSampleReservoirByte() {
		for (int n = 1; n < 9; n++) {
			byte[] allDiff = new byte[n];
			byte[] mixedQuantities = new byte[n];
			byte[] allSame = new byte[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (byte)(100 + i);
				mixedQuantities[i] = (byte)(100 + j);
				allSame[i] = (byte)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolByte() {
		for (int n = 1; n < 9; n++) {
			byte[] allDiff = new byte[n];
			byte[] mixedQuantities = new byte[n];
			byte[] allSame = new byte[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (byte)(100 + i);
				mixedQuantities[i] = (byte)(100 + j);
				allSame[i] = (byte)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionByte() {
		for (int n = 1; n < 9; n++) {
			byte[] allDiff = new byte[n];
			byte[] mixedQuantities = new byte[n];
			byte[] allSame = new byte[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (byte)(100 + i);
				mixedQuantities[i] = (byte)(100 + j);
				allSame[i] = (byte)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleByte() {
		for (int n = 1; n < 9; n++) {
			byte[] allDiff = new byte[n];
			byte[] mixedQuantities = new byte[n];
			byte[] allSame = new byte[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (byte)(100 + i);
				mixedQuantities[i] = (byte)(100 + j);
				allSame[i] = (byte)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// char tests
	
	@Test
	public void testSampleReservoirChar() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolChar() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionChar() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleChar() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// double tests
	
	@Test
	public void testSampleReservoirD() {
		for (int n = 1; n < 9; n++) {
			double[] allDiff = new double[n];
			double[] mixedQuantities = new double[n];
			double[] allSame = new double[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolD() {
		for (int n = 1; n < 9; n++) {
			double[] allDiff = new double[n];
			double[] mixedQuantities = new double[n];
			double[] allSame = new double[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionD() {
		for (int n = 1; n < 9; n++) {
			double[] allDiff = new double[n];
			double[] mixedQuantities = new double[n];
			double[] allSame = new double[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleD() {
		for (int n = 1; n < 9; n++) {
			double[] allDiff = new double[n];
			double[] mixedQuantities = new double[n];
			double[] allSame = new double[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// float tests
	
	@Test
	public void testSampleReservoirF() {
		for (int n = 1; n < 9; n++) {
			float[] allDiff = new float[n];
			float[] mixedQuantities = new float[n];
			float[] allSame = new float[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolF() {
		for (int n = 1; n < 9; n++) {
			float[] allDiff = new float[n];
			float[] mixedQuantities = new float[n];
			float[] allSame = new float[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionF() {
		for (int n = 1; n < 9; n++) {
			float[] allDiff = new float[n];
			float[] mixedQuantities = new float[n];
			float[] allSame = new float[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleF() {
		for (int n = 1; n < 9; n++) {
			float[] allDiff = new float[n];
			float[] mixedQuantities = new float[n];
			float[] allSame = new float[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = 100 + i;
				mixedQuantities[i] = 100 + j;
				allSame[i] = 100;
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	// String tests
	
	@Test
	public void testSampleReservoirS() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			String sAllDiff = new String(allDiff);
			String sMixed = new String(mixedQuantities);
			String sSame = new String(allSame);
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(sAllDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(sMixed, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(sSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolS() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			String sAllDiff = new String(allDiff);
			String sMixed = new String(mixedQuantities);
			String sSame = new String(allSame);
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(sAllDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(sMixed, k, null));
				validateSample(allSame, SequenceSampler.samplePool(sSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionS() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			String sAllDiff = new String(allDiff);
			String sMixed = new String(mixedQuantities);
			String sSame = new String(allSame);
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(sAllDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(sMixed, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(sSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleS() {
		for (int n = 1; n < 9; n++) {
			char[] allDiff = new char[n];
			char[] mixedQuantities = new char[n];
			char[] allSame = new char[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (char)(100 + i);
				mixedQuantities[i] = (char)(100 + j);
				allSame[i] = (char)(100);
			}
			String sAllDiff = new String(allDiff);
			String sMixed = new String(mixedQuantities);
			String sSame = new String(allSame);
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(sAllDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(sMixed, k, null));
				validateSample(allSame, SequenceSampler.sample(sSame, k, null));
			}
		}
	}
	
	// Object tests
	
	@Test
	public void testSampleReservoirO() {
		for (int n = 1; n < 9; n++) {
			String[] allDiff = new String[n];
			String[] mixedQuantities = new String[n];
			String[] allSame = new String[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = "" + (char)(100 + i);
				mixedQuantities[i] = "" + (char)(100 + j);
				allSame[i] = "" + (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSamplePoolO() {
		for (int n = 1; n < 9; n++) {
			String[] allDiff = new String[n];
			String[] mixedQuantities = new String[n];
			String[] allSame = new String[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = "" + (char)(100 + i);
				mixedQuantities[i] = "" + (char)(100 + j);
				allSame[i] = "" + (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleInsertionO() {
		for (int n = 1; n < 9; n++) {
			String[] allDiff = new String[n];
			String[] mixedQuantities = new String[n];
			String[] allSame = new String[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = "" + (char)(100 + i);
				mixedQuantities[i] = "" + (char)(100 + j);
				allSame[i] = "" + (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null));
			}
		}
	}
	
	@Test
	public void testSampleO() {
		for (int n = 1; n < 9; n++) {
			String[] allDiff = new String[n];
			String[] mixedQuantities = new String[n];
			String[] allSame = new String[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = "" + (char)(100 + i);
				mixedQuantities[i] = "" + (char)(100 + j);
				allSame[i] = "" + (char)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null));
				validateSample(allSame, SequenceSampler.sample(allSame, k, null));
			}
		}
	}
	
	
	private void validateSample(int[] array, int[] sample) {
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(long[] array, long[] sample) {
		HashMap<Long, Integer> counts = new HashMap<Long, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(short[] array, short[] sample) {
		HashMap<Short, Integer> counts = new HashMap<Short, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(byte[] array, byte[] sample) {
		HashMap<Byte, Integer> counts = new HashMap<Byte, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(char[] array, char[] sample) {
		HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(double[] array, double[] sample) {
		HashMap<Double, Integer> counts = new HashMap<Double, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(float[] array, float[] sample) {
		HashMap<Float, Integer> counts = new HashMap<Float, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	private void validateSample(String[] array, String[] sample) {
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
}


