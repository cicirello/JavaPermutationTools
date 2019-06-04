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
import java.util.Arrays;
import java.util.SplittableRandom;

/**
 * JUnit 4 tests for the methods of the RandomIndexer class.
 */
public class RandomIndexerSampleTests {
	
	private static double EPSILON = 1e-10;
	
	// Part of each test case in this class is a chi square goodness of fit test
	// on a large number of samples to test for uniformity of results.
	// Some of these tests are of methods that rely on ThreadLocalRandom, which
	// doesn't allow setting a seed, so it is impossible to 100% replicate tests
	// from one run of the test set to the next.
	// These test case fully passed the most recent time run.
	// If you make any changes to the sampling methods that depend upon ThreadLocalRandom,
	// then change this constant to false, run all tests, and switch back to true if
	// they pass.
	private static final boolean DISABLE_CHI_SQUARE_TESTS = true;
	
	@Test
	public void testSampleReservoir_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 200;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleReservoir(n, k, null);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleReservoir(n, k, null);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSampleReservoir_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleReservoir(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < 100; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleReservoir(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH, countH <= 10);
			}
		}
	}
	
	@Test
	public void testSampleReservoir_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleReservoir(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < 100; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleReservoir(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH, countH <= 10);
			}
		}
	}
	
	
	
	
	
	
	@Test
	public void testSamplePool_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 200;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.samplePool(n, k, null);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.samplePool(n, k, null);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSamplePool_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.samplePool(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.samplePool(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSamplePool_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.samplePool(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.samplePool(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	
	
	
	
	
	@Test
	public void testSample_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 200;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sample(n, k, null);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sample(n, k, null);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSample_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(40);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sample(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sample(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSample_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sample(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sample(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	
	@Test
	public void testPair_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 200;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntPair(n, null);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 2; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][] buckets = new int[n][n];
				int numBuckets = n*(n-1)/2;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntPair(n, null);
					buckets[result[0]][result[1]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	@Test
	public void testTriple_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 200;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntTriple(n, null);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 3; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][][] buckets = new int[n][n][n];
				int numBuckets = n*(n-1)*(n-2)/6;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntTriple(n, null);
					buckets[result[0]][result[1]][result[2]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	
	@Test
	public void testTriple_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntTriple(n, null, gen);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
			}
		}
		for (int n = 3; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][][] buckets = new int[n][n][n];
				int numBuckets = n*(n-1)*(n-2)/6;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntTriple(n, null, gen);
					buckets[result[0]][result[1]][result[2]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	@Test
	public void testTriple_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntTriple(n, null, gen);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
			}
		}
		for (int n = 3; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][][] buckets = new int[n][n][n];
				int numBuckets = n*(n-1)*(n-2)/6;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntTriple(n, null, gen);
					buckets[result[0]][result[1]][result[2]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	@Test
	public void testPair_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntPair(n, null, gen);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
			}
		}
		for (int n = 2; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][] buckets = new int[n][n];
				int numBuckets = n*(n-1)/2;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntPair(n, null, gen);
					buckets[result[0]][result[1]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	@Test
	public void testPair_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 6; n++) {
			for (int i = 0; i < 10; i++) {
				int[] result = RandomIndexer.nextIntPair(n, null, gen);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
			}
		}
		for (int n = 2; n <= 6; n++) {
			int countH = 0;
			for (int trial = 0; trial < TRIALS; trial++) {
				int[][] buckets = new int[n][n];
				int numBuckets = n*(n-1)/2;
				for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
					int[] result = RandomIndexer.nextIntPair(n, null, gen);
					buckets[result[0]][result[1]]++;
				}
				double chi = chiSquare(buckets, numBuckets);
				if (chi > limit95[numBuckets-1]) countH++;
			}
			assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
		}
	}
	
	
	
	
	
	@Test
	public void testSampleInsertion_ThreadLocalRandom() {
		final int REPS_PER_BUCKET = 250;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleInsertion(n, k, null);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleInsertion(n, k, null);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSampleInsertion_SplittableRandom() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleInsertion(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleInsertion(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testSampleInsertion_Random() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 200;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 1; n <= 6; n++) {
			for (int k = 0; k <= n; k++) {
				int[] result = RandomIndexer.sampleInsertion(n, k, null, gen);
				assertEquals("Length of result should be " + k, k, result.length);
				boolean[] inResult = new boolean[n];
				for (int i = 0; i < k; i++) {
					assertTrue("Each integer should be at least 0 and less than " + k, result[i] < n && result[i] >= 0);
					assertFalse("Result shouldn't contain duplicates", inResult[result[i]]);
					inResult[result[i]] = true;
				}
			}
		}
		for (int n = 1; n <= 5; n++) {
			int m = n < 3 ? n : 3;
			for (int k = 1; k <= m; k++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[] buckets1 = new int[n];
					int[][] buckets2 = new int[n][n];
					int[][][] buckets3 = new int[n][n][n];
					int numBuckets = k==1 ? n : (k==2 ? n*(n-1)/2 : n*(n-1)*(n-2)/6);
					for (int j = 0; j < REPS_PER_BUCKET * numBuckets; j++) {
						int[] result = RandomIndexer.sampleInsertion(n, k, null, gen);
						Arrays.sort(result);
						switch (k) {
							case 1: buckets1[result[0]] += 1; break;
							case 2: buckets2[result[0]][result[1]] += 1; break;
							case 3: buckets3[result[0]][result[1]][result[2]] += 1; break;
						}
					}
					switch (k) {
						case 1: 
						for (int x = 0; x < n; x++) {
							assertTrue("failed to generate any samples: "+x, buckets1[x]>0);
						}
						double chi1 = chiSquare(buckets1, numBuckets);
						if (chi1 > limit95[numBuckets-1]) countH++;
						break;
						case 2: 
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								assertTrue("failed to generate any samples: ("+x+", "+y+")", buckets2[x][y]>0);
							}
						}
						double chi2 = chiSquare(buckets2, numBuckets);
						if (chi2 > limit95[numBuckets-1]) countH++;
						break;
						case 3:
						for (int x = 0; x < n; x++) {
							for (int y = x+1; y < n; y++) {
								for (int z = y+1; z < n; z++) {
									assertTrue("failed to generate any samples: ("+x+", "+y+", "+z+")", buckets3[x][y][z]>0);
								}
							}
						}
						double chi3 = chiSquare(buckets3, numBuckets);
						if (chi3 > limit95[numBuckets-1]) countH++;
						break;
					}
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n+" k="+k, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntPair_TLR() {
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 10; n++) {
			for (int w = 1; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntPair(n, w, null);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
				assertTrue("integers should be within window w="+w, result[1]-result[0] <= w);
			}
		}
		if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 2; n <= 7; n++) {
			for (int w = 1; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][] buckets = new int[n][n];
					int numBuckets = w*(n-w) + w*(w-1)/2;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntPair(n, w, null);
						buckets[result[0]][result[1]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							flatBuckets[k] = buckets[i][j];
							k++;
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntPair_SR() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 10; n++) {
			for (int w = 1; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntPair(n, w, null, gen);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
				assertTrue("integers should be within window w="+w, result[1]-result[0] <= w);
			}
		}
		for (int n = 2; n <= 7; n++) {
			for (int w = 1; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][] buckets = new int[n][n];
					int numBuckets = w*(n-w) + w*(w-1)/2;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntPair(n, w, null, gen);
						buckets[result[0]][result[1]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							flatBuckets[k] = buckets[i][j];
							k++;
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntPair_R() {
		Random gen = new Random(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 2; n <= 10; n++) {
			for (int w = 1; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntPair(n, w, null, gen);
				assertEquals("Length of result should be 2", 2, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[1] < n);
				assertTrue("integers should be within window w="+w, result[1]-result[0] <= w);
			}
		}
		for (int n = 2; n <= 7; n++) {
			for (int w = 1; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][] buckets = new int[n][n];
					int numBuckets = w*(n-w) + w*(w-1)/2;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntPair(n, w, null, gen);
						buckets[result[0]][result[1]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							flatBuckets[k] = buckets[i][j];
							k++;
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntTriple_TLR() {
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 10; n++) {
			for (int w = 2; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
				assertTrue("integers should be within window w="+w, result[2]-result[0] <= w);
			}
		}
		//if (DISABLE_CHI_SQUARE_TESTS) return;
		for (int n = 3; n <= 6; n++) {
			for (int w = 2; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][][] buckets = new int[n][n][n];
					int numBuckets = w*(n-w)*(w-1)/2 + w*(w-1)*(w-2)/6;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null);
						buckets[result[0]][result[1]][result[2]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							for (int h = j+1; h < n && h <= i+w; h++) {
								flatBuckets[k] = buckets[i][j][h];
								k++;
							}
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntTriple_SR() {
		SplittableRandom gen = new SplittableRandom(42);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 10; n++) {
			for (int w = 2; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null, gen);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
				assertTrue("integers should be within window w="+w, result[2]-result[0] <= w);
			}
		}
		for (int n = 3; n <= 6; n++) {
			for (int w = 2; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][][] buckets = new int[n][n][n];
					int numBuckets = w*(n-w)*(w-1)/2 + w*(w-1)*(w-2)/6;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null, gen);
						buckets[result[0]][result[1]][result[2]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							for (int h = j+1; h < n && h <= i+w; h++) {
								flatBuckets[k] = buckets[i][j][h];
								k++;
							}
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	@Test
	public void testNextWindowedIntTriple_R() {
		Random gen = new Random(41);
		final int REPS_PER_BUCKET = 100;
		final int TRIALS = 100;
		double[] limit95 = {
			EPSILON, 3.841, 5.991, 7.815, 9.488, 
			11.07, 12.59, 14.07, 15.51, 16.92, 
			18.31, 19.68, 21.03,
			22.36, 23.69, 25.0,
			26.3, 27.59, 28.87, 30.14, 31.41
		};
		for (int n = 3; n <= 10; n++) {
			for (int w = 2; w < n; w++) {
				int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null, gen);
				assertEquals("Length of result should be 3", 3, result.length);
				assertNotEquals("integers should be different", result[0], result[1]);
				assertNotEquals("integers should be different", result[0], result[2]);
				assertNotEquals("integers should be different", result[2], result[1]);
				assertTrue("result should be sorted", result[0] < result[1]);
				assertTrue("result should be sorted", result[1] < result[2]);
				assertTrue("integers should be at least 0", result[0] >= 0);
				assertTrue("integers should be less than " + n, result[2] < n);
				assertTrue("integers should be within window w="+w, result[2]-result[0] <= w);
			}
		}
		for (int n = 3; n <= 6; n++) {
			for (int w = 2; w < n; w++) {
				int countH = 0;
				for (int trial = 0; trial < TRIALS; trial++) {
					int[][][] buckets = new int[n][n][n];
					int numBuckets = w*(n-w)*(w-1)/2 + w*(w-1)*(w-2)/6;
					for (int i = 0; i < REPS_PER_BUCKET * numBuckets; i++) {
						int[] result = RandomIndexer.nextWindowedIntTriple(n, w, null, gen);
						buckets[result[0]][result[1]][result[2]]++;
					}
					int[] flatBuckets = new int[numBuckets];
					int k = 0;
					for (int i = 0; i < n; i++) {
						for (int j = i+1; j < n && j <= i+w; j++) {
							for (int h = j+1; h < n && h <= i+w; h++) {
								flatBuckets[k] = buckets[i][j][h];
								k++;
							}
						}
					}
					double chi = chiSquare(flatBuckets, numBuckets);
					if (chi > limit95[numBuckets-1]) countH++;
				}
				assertTrue("chi square too high too often, countHigh=" + countH + " n="+n, countH <= TRIALS*0.1);
			}
		}
	}
	
	
	private double chiSquare(int[] buckets, int numBuckets) {
		int x = 0;
		int n = 0;
		for (int e : buckets) {
			x = x + e*e;
			n += e;
		}
		return 1.0*x / (n/numBuckets) - n;
	}
	
	private double chiSquare(int[][] buckets, int numBuckets) {
		int m = 0;
		int n = 0;
		for (int x = 0; x < buckets.length; x++) {
			for (int y = x+1; y < buckets.length; y++) {
				int e = buckets[x][y];
				m = m + e*e;
				n += e;
			}
		}
		return 1.0*m / (n/numBuckets) - n;
	}
	
	private double chiSquare(int[][][] buckets, int numBuckets) {
		int m = 0;
		int n = 0;
		for (int x = 0; x < buckets.length; x++) {
			for (int y = x+1; y < buckets.length; y++) {
				for (int z = y+1; z < buckets.length; z++) {
					int e = buckets[x][y][z];
					m = m + e*e;
					n += e;
				}
			}
		}
		return 1.0*m / (n/numBuckets) - n;
	}
}