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


package org.cicirello.math.stats;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the methods of the Statistics class.
 */
public class StatisticsTests {
	
	private static double EPSILON = 1e-10;
	
	@Test
	public void testMeanOfInts() {
		for (int n = 1; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 3.0, Statistics.mean(data), EPSILON);
		}
		for (int n = 1; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n", (n+1)/2.0, Statistics.mean(data), EPSILON);
		}	
	}
	
	@Test
	public void testMeanOfDoubles() {
		for (int n = 1; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 3.0, Statistics.mean(data), EPSILON);
		}
		for (int n = 1; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n as doubles", (n+1)/2.0, Statistics.mean(data), EPSILON);
		}	
	}
	
	@Test
	public void testVarianceOfInts() {
		for (int n = 1; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 0.0, Statistics.variance(data), EPSILON);
		}
		double[] expected = {0.25, 2.0/3.0, 5.0/4.0 };
		for (int n = 2; n <= 4; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n", expected[n-2], Statistics.variance(data), EPSILON);
		}	
	}
	
	@Test
	public void testVarianceOfDoubles() {
		for (int n = 1; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 0.0, Statistics.variance(data), EPSILON);
		}
		double[] expected = {0.25, 2.0/3.0, 5.0/4.0 };
		for (int n = 2; n <= 4; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n", expected[n-2], Statistics.variance(data), EPSILON);
		}	
	}
	
	@Test
	public void testSampleVarianceOfInts() {
		for (int n = 1; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 0.0, Statistics.varianceSample(data), EPSILON);
		}
		// Note: This assumes that the variance method passes its tests.
		for (int n = 2; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n", Statistics.variance(data)*n/(n-1), Statistics.varianceSample(data), EPSILON);
		}	
	}
	
	@Test
	public void testSampleVarianceOfDoubles() {
		for (int n = 1; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("All elements the same", 0.0, Statistics.varianceSample(data), EPSILON);
		}
		// Note: This assumes that the variance method passes its tests.
		for (int n = 2; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
			}
			assertEquals("ints from 1 to n", Statistics.variance(data)*n/(n-1), Statistics.varianceSample(data), EPSILON);
		}	
	}
	
	@Test
	public void testCovarianceOfInts() {
		for (int n = 1; n <= 10; n++) {
			int[] data = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("covariance of X with itself, X all same", 0.0, Statistics.covariance(data, data.clone()), EPSILON);
		}
		double[] expected = {0.25, 2.0/3.0, 5.0/4.0 };
		for (int n = 2; n <= 4; n++) {
			int[] data = new int[n];
			int[] data2 = new int[n];
			int[] data3 = new int[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
				data2[i] = 3 * data[i];
				data3[i] = 4 * data[i];
			}
			// assumes variance method already tested... uses it in expected result
			assertEquals("covariance of X with itself", Statistics.variance(data), Statistics.covariance(data, data.clone()), EPSILON);
			assertEquals("covariance of X with itself", 3*4*Statistics.variance(data), Statistics.covariance(data2, data3), EPSILON);
		}	
	}
	
	@Test
	public void testCovarianceOfDoubles() {
		for (int n = 1; n <= 10; n++) {
			double[] data = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = 3;
			}
			assertEquals("covariance of X with itself, X all same", 0.0, Statistics.covariance(data, data.clone()), EPSILON);
		}
		double[] expected = {0.25, 2.0/3.0, 5.0/4.0 };
		for (int n = 2; n <= 4; n++) {
			double[] data = new double[n];
			double[] data2 = new double[n];
			double[] data3 = new double[n];
			for (int i = 0; i < n; i++) {
				data[i] = i+1;
				data2[i] = 3.3 * data[i];
				data3[i] = 1.4 * data[i];
			}
			// assumes variance method already tested... uses it in expected result
			assertEquals("covariance of X with itself", Statistics.variance(data), Statistics.covariance(data, data.clone()), EPSILON);
			assertEquals("covariance of X with itself", 3.3*1.4*Statistics.variance(data), Statistics.covariance(data2, data3), EPSILON);
		}	
	}
	
	
	@Test
	public void testCorrelationOfInts() {
		for (int n = 2; n <= 10; n++) {
			int[] x = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = i+1;
			}
			int[] y = x.clone();
			assertEquals("X and Y identical", 1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] *= -1;
			}
			assertEquals("Y = -X", -1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] = 3*x[i];
			}
			assertEquals("Y = alpha * X", 1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] *= -1;
			}
			assertEquals("Y = -alpha * X", -1.0, Statistics.correlation(x, y), EPSILON);
		}
	}
	
	@Test
	public void testCorrelationOfDoubles() {
		for (int n = 2; n <= 10; n++) {
			double[] x = new double[n];
			for (int i = 0; i < n; i++) {
				x[i] = i+1;
			}
			double[] y = x.clone();
			assertEquals("X and Y identical", 1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] *= -1;
			}
			assertEquals("Y = -X", -1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] = 3*x[i];
			}
			assertEquals("Y = alpha * X", 1.0, Statistics.correlation(x, y), EPSILON);
			for (int i = 0; i < n; i++) {
				y[i] *= -1;
			}
			assertEquals("Y = -alpha * X", -1.0, Statistics.correlation(x, y), EPSILON);
		}
	}
	
	
	
	
}