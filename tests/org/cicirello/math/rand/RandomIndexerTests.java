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


package org.cicirello.math.rand;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.SplittableRandom;

/**
 * JUnit 4 tests for the methods of the RandomIndexer class.
 */
public class RandomIndexerTests {
	
	private static double EPSILON = 1e-10;
	
	@Test
	public void testRandInt_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03
		};
		int countH = 0;
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 100; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextInt(i);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
				double chi = chiSquare(a);
				if (chi > limit95[i-1]) countH++;
			}
		}
		assertTrue("chi square too high too often, countHigh=" + countH, countH <= 130);
	}
	
	@Test
	public void testRandInt_SplittableRandom() {
		final int REPS_PER_BUCKET = 100;
		SplittableRandom gen = new SplittableRandom(42);
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03
		};
		int countH = 0;
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 100; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextInt(i, gen);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
				double chi = chiSquare(a);
				if (chi > limit95[i-1]) countH++;
			}
		}
		assertTrue("chi square too high too often, countHigh=" + countH, countH <= 130);
	}
	
	@Test
	public void testRandInt_Random() {
		final int REPS_PER_BUCKET = 100;
		Random gen = new Random(42);
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03
		};
		int countH = 0;
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 100; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextInt(i, gen);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
				double chi = chiSquare(a);
				if (chi > limit95[i-1]) countH++;
			}
		}
		assertTrue("chi square too high too often, countHigh=" + countH, countH <= 130);
	}
	
	@Test
	public void testRandBiasedInt_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 10;
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 10; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextBiasedInt(i);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
			}
		}
	}
	
	@Test
	public void testRandBiasedInt_SplittableRandom() {
		final int REPS_PER_BUCKET = 10;
		SplittableRandom gen = new SplittableRandom(42);
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 10; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextBiasedInt(i, gen);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
			}
		}
	}
	
	@Test
	public void testRandBiasedInt_Random() {
		final int REPS_PER_BUCKET = 10;
		Random gen = new Random(42);
		for (int i = 1; i <= 13; i++) {
			for (int trial = 0; trial < 10; trial++) {
				int[] a = new int[i];
				for (int j = 0; j < REPS_PER_BUCKET * i; j++) {
					int k = RandomIndexer.nextBiasedInt(i, gen);
					assertTrue("nextInt outside range for bound="+i, k < i && k >= 0);
					a[k] += 1;
				}
				for (int k = 0; k < i; k++) {
					assertTrue("failed to generate any samples of "+k,a[k]>0);
				}
			}
		}
	}
	
	
	
	private double chiSquare(int[] buckets) {
		int x = 0;
		int n = 0;
		for (int e : buckets) {
			x = x + e*e;
			n += e;
		}
		return 1.0*x / (n/buckets.length) - n;
	}
}