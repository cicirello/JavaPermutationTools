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
 *
 */
package org.cicirello.sequences;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.HashMap;
import org.cicirello.math.stats.Statistics;

/**
 * JUnit 4 tests for the SequenceSampler class.
 */
public class SequenceSamplerTests {
	
	private final static double EPSILON = 1e-10;
	
	// Tests with parameters: array, double.
	
	// Set this is true to run the t-test.  Due to randomness,
	// the t statistics won't be exactly the same each time.
	// An occasional failure is expected if this test is run a large enough times.
	// E.g., Test case is implemented with an alpha = 0.01, so 1 in 100 failures
	// is OK.  And it runs several tests for different length arrays, etc.
	// So set this to true only if you change the code, and then set back to false
	// after passing.
	private static final boolean DISABLE_T_TEST = true;
	
	private static final int TTEST_NUM_SAMPLES = 100000; //101;
	
	// Two-sided test, so alpha=0.01 level needs tCrit_{1 - alpha/2} = tCrit_{0.995}
	// This first one is 100 degrees of freedom (use for TTEST_NUM_SAMPLES = 101).
	// The second one is for infinite number of samples (use for very large TTEST_NUM_SAMPLES).
	private static final double CRITICAL_VALUE_995 = 2.626;
	private static final double CRITICAL_VALUE_995_INF = 2.576;
	
	@Test
	public void testSamplePTTest() {
		if (DISABLE_T_TEST) {
			return;
		}
		final double sqrt = Math.sqrt(TTEST_NUM_SAMPLES);
		for (int n = 1; n <= 10; n++) {
			int[] array = new int[n];
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				int[] L = new int[TTEST_NUM_SAMPLES];
				for (int i = 0; i < TTEST_NUM_SAMPLES; i++) {
					L[i] = SequenceSampler.sample(array, p).length;
				}
				double stDev = Math.sqrt(Statistics.varianceSample(L));
				double t = Math.abs(sqrt*(Statistics.mean(L) - n*p)/stDev);
				double crit = TTEST_NUM_SAMPLES == 101 ? CRITICAL_VALUE_995 : CRITICAL_VALUE_995_INF;
				System.out.println("t="+t);
				assertTrue("t test for sample size, t="+t, t < crit);
			}
		}
	}
	
	@Test
	public void testSampleIntP() {
		for (int n = 1; n <= 10; n++) {
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
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleLongP() {
		for (int n = 1; n <= 10; n++) {
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
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleFloatP() {
		for (int n = 1; n <= 10; n++) {
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
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleDoubleP() {
		for (int n = 1; n <= 10; n++) {
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
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleShortP() {
		for (int n = 1; n <= 10; n++) {
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
				allSame[i] = 100;
			}
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleCharP() {
		for (int n = 1; n <= 10; n++) {
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
				allSame[i] = 100;
			}
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleStringP() {
		for (int n = 1; n <= 10; n++) {
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
				allSame[i] = 100;
			}
			String allDiffS = new String(allDiff);
			String mixedQuantitiesS = new String(mixedQuantities);
			String allSameS = new String(allSame);
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiffS, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantitiesS, p));
				validateSample(allSame, SequenceSampler.sample(allSameS, p));
			}
		}
	}
	
	@Test
	public void testSampleByteP() {
		for (int n = 1; n <= 10; n++) {
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
				allSame[i] = 100;
			}
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@Test
	public void testSampleObjectsP() {
		for (int n = 1; n <= 10; n++) {
			Integer[] allDiff = new Integer[n];
			Integer[] mixedQuantities = new Integer[n];
			Integer[] allSame = new Integer[n];
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
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final int[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		int[] target = new int[4];
		int[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new int[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final int[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		int[] target = new int[4];
		int[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new int[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final int[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		int[] target = new int[4];
		int[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new int[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final long[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		long[] target = new long[4];
		long[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new long[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final long[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		long[] target = new long[4];
		long[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new long[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final long[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		long[] target = new long[4];
		long[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new long[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final short[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		short[] target = new short[4];
		short[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new short[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final short[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		short[] target = new short[4];
		short[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new short[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final short[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		short[] target = new short[4];
		short[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new short[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final byte[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		byte[] target = new byte[4];
		byte[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new byte[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final byte[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		byte[] target = new byte[4];
		byte[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new byte[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final byte[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		byte[] target = new byte[4];
		byte[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new byte[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final char[] source = { '2', '4', '6', '8' };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final char[] source = { '2', '4', '6', '8' };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final char[] source = { '2', '4', '6', '8' };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final double[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		double[] target = new double[4];
		double[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new double[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final double[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		double[] target = new double[4];
		double[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new double[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final double[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		double[] target = new double[4];
		double[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new double[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final float[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		float[] target = new float[4];
		float[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new float[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final float[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		float[] target = new float[4];
		float[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new float[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final float[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		float[] target = new float[4];
		float[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new float[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(sAllDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(sMixed, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(sSame, k, null), k);
			}
		}
		final String source = "abcd";
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(sAllDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(sMixed, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(sSame, k, null), k);
			}
		}
		final String source = "abcd";
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(sAllDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(sMixed, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(sSame, k, null), k);
			}
		}
		final String source = "abcd";
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		char[] target = new char[4];
		char[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new char[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(sAllDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(sMixed, k, null), k);
				validateSample(allSame, SequenceSampler.sample(sSame, k, null), k);
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
				validateSample(allDiff, SequenceSampler.sampleReservoir(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleReservoir(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleReservoir(allSame, k, null), k);
			}
		}
		final String[] source = { "2", "4", "6", "8" };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleReservoir(source, 5, null)
		);
		String[] target = new String[4];
		String[] result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new String[3];
		result = SequenceSampler.sampleReservoir(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.samplePool(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.samplePool(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.samplePool(allSame, k, null), k);
			}
		}
		final String[] source = { "2", "4", "6", "8" };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.samplePool(source, 5, null)
		);
		String[] target = new String[4];
		String[] result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new String[3];
		result = SequenceSampler.samplePool(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sampleInsertion(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sampleInsertion(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sampleInsertion(allSame, k, null), k);
			}
		}
		final String[] source = { "2", "4", "6", "8" };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> SequenceSampler.sampleInsertion(source, 5, null)
		);
		String[] target = new String[4];
		String[] result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new String[3];
		result = SequenceSampler.sampleInsertion(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
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
				validateSample(allDiff, SequenceSampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, SequenceSampler.sample(allSame, k, null), k);
			}
		}
	}
	
	// validate (array, sample, k) methods
	
	private void validateSample(int[] array, int[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(long[] array, long[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(short[] array, short[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(byte[] array, byte[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(char[] array, char[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(double[] array, double[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(float[] array, float[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	private void validateSample(String[] array, String[] sample, int k) {
		assertEquals("sample size", k, sample.length);
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
	
	// validate(array, sample) methods
	
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
	
	private void validateSample(Object[] array, Object[] sample) {
		HashMap<Object, Integer> counts = new HashMap<Object, Integer>();
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


