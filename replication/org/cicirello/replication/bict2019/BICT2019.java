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
package org.cicirello.replication.bict2019;

import org.cicirello.permutations.Permutation;
import org.cicirello.permutations.distance.*;
import static org.cicirello.math.stats.Statistics.*;
import org.cicirello.math.la.JacobiDiagonalization;
import org.cicirello.math.la.MatrixOps;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>This program replicates the data for the paper:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2019bict.html">"Classification 
 * of Permutation Distance Metrics for Fitness Landscape Analysis,"</a> 
 * Proceedings of the 11th International Conference on Bio-inspired 
 * Information and Communications Technologies, March 2019.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 */
public class BICT2019 {
	
	public static void main(String[] args) {
		final int LENGTH = (args.length > 0) ? Integer.parseInt(args[0]) : 10;
		final int COUNT = fact(LENGTH);
		
		// List of distance measures to use in analysis
		ArrayList<PermutationDistanceMeasurer> measures = new ArrayList<PermutationDistanceMeasurer>();
		measures.add(new ExactMatchDistance());
		measures.add(new InterchangeDistance());
		measures.add(new AcyclicEdgeDistance());
		measures.add(new CyclicEdgeDistance());
		measures.add(new RTypeDistance());
		measures.add(new CyclicRTypeDistance());
		measures.add(new KendallTauDistance());	
		measures.add(new ReinsertionDistance());
		measures.add(new DeviationDistance());
		measures.add(new SquaredDeviationDistance());
		measures.add(new LeeDistance());
		
		
		int numMeasures = measures.size();
				
		// Reference permutation
		Permutation p1 = new Permutation(LENGTH, 0);
		
		// Array to store distances to reference permutation
		int[][] data = new int[numMeasures][COUNT];
		
		// Iterate over all permutations and compute distances (for all distance measures) to reference permutation
		for (int i = 0; i < COUNT; i++) {
			Permutation p2 = new Permutation(LENGTH, i);
			for (int j = 0; j < numMeasures; j++) {
				data[j][i] = measures.get(j).distance(p1,p2);
			}
		}
		
		// Compute correlation matrix
		double[][] corr = correlationMatrix(data);
		
		
		System.out.println("Data for the Tables of Section 3 of the paper.");
		System.out.println("PCA using all permutations of length " + LENGTH);
		System.out.println();
		
		// Output correlation matrix.  Matrix is symmetric so just outputting lower triangle.
		System.out.println("Correlation Matrix (lower triangle)\n");
		for (int i = 0; i < numMeasures; i++) {
			System.out.print("\t" + measures.get(i).getClass().getSimpleName());
		}
		System.out.println();
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			for (int j = 0; j <= i; j++) {
				System.out.printf("\t%6.3f", corr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		
		// Compute eigenvalues and eigenvectors with Jacobi method.
		JacobiDiagonalization j = new JacobiDiagonalization(corr);
		boolean converged = j.compute();
		
		// Get the matrix of eigenvectors.
		double[][] v = j.eigenvectors();
		
		// Transpose the matrix to make it easier to sort the eigenvectors by eigenvalue.
		// i.e., since java 2D arrays are arrays of arrays, having eigenvectors in rows instead of columns will make it easier to sort.
		v = MatrixOps.transposeSquareMatrixInline(v);
		
		// Nested class used for sorting.
		class EigenValueVectorPair implements Comparable<EigenValueVectorPair> {
			double value;
			double[] vector;
			EigenValueVectorPair(double value, double[] vector) {
				this.value = value;
				this.vector = vector;
			}
			public int compareTo(EigenValueVectorPair other) {
				if (Math.abs(value) > Math.abs(other.value)) return -1;
				if (Math.abs(value) < Math.abs(other.value)) return 1;
				return 0;
			}
		}
		
		// Sort the eigenvectors by eigenvalue (largest eigenvalue to smallest)
		EigenValueVectorPair[] pairs = new EigenValueVectorPair[v.length];
		double sumEigenvalues = 0;
		for (int i = 0; i < v.length; i++) {
			pairs[i] = new EigenValueVectorPair(j.eigenvalues()[i], v[i]);
			sumEigenvalues = sumEigenvalues + pairs[i].value;
		}
		Arrays.sort(pairs);
		
		
		System.out.println(converged ? "Convergence Achieved" : "Max Iterations Caused Termination of Jacobi Method");
		System.out.println("Eigenvalues and Eigenvectors sorted by absolute value of Eigenvalue:");
		System.out.println();
		System.out.println("pc\teigenvalue\tproportion\tcumulative");
		double runningSum = 0;
		int c = 1;
		for (EigenValueVectorPair pair : pairs) {
			runningSum = runningSum + pair.value;
			System.out.printf("%3d\t%6.4f\t%6.4f\t%6.4f\n", c, pair.value, pair.value / sumEigenvalues, runningSum / sumEigenvalues);
			c++;
		}
		System.out.println();
		System.out.println();
		System.out.print("distance");
		for (int i = 1; i <= numMeasures; i++) {
			System.out.print("\tpc" + i);
		}
		System.out.println();
		
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			for (EigenValueVectorPair pair : pairs) {
				System.out.printf("\t%6.4f", pair.vector[i]);
			}
			System.out.println();
		}
		System.out.println();
		
		// compute and output correlation coefficients between original distances and the principle components
		System.out.println("Correlation between original distance measures and principle components.");
		System.out.print("distance");
		for (int i = 1; i <= numMeasures; i++) {
			System.out.print("\tpc" + i);
		}
		System.out.println();
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			for (int k = 0; k < numMeasures; k++) {
				System.out.printf("\t%6.4f", Math.sqrt(pairs[k].value)*pairs[k].vector[i]);
			}
			System.out.println();
		}
		System.out.println();
		
		
		System.out.println();
		System.out.println("Data for the Tables of Section 4 of the paper.");
		System.out.println("PCA using sampled permutations of length 50");
		System.out.println("IMPORTANT NOTE: Since we're randomly sampling, the data");
		System.out.println("generated by this program will vary by run, so may not be");
		System.out.println("identical to the data in the paper, but should be consistent");
		System.out.println("statistically.");
		System.out.println();
		
		// Reference permutation
		final int SAMPLED_LENGTH = 50;
		p1 = new Permutation(SAMPLED_LENGTH, 0);
		
		// generate randomly sampled permutations of length 50
		for (int i = 0; i < COUNT; i++) {
			Permutation p2 = new Permutation(SAMPLED_LENGTH);
			for (int k = 0; k < numMeasures; k++) {
				data[k][i] = measures.get(k).distance(p1,p2);
			}
		}
		
		corr = correlationMatrix(data);
		
		// Output correlation matrix.  Matrix is symmetric so just outputting lower triangle.
		System.out.println("Correlation Matrix from sampled data (lower triangle)\n");
		for (int i = 0; i < numMeasures; i++) {
			System.out.print("\t" + measures.get(i).getClass().getSimpleName());
		}
		System.out.println();
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			for (int k = 0; k <= i; k++) {
				System.out.printf("\t&%6.3f", corr[i][k]);
			}
			System.out.println();
		}
		System.out.println();
		
		// Compute eigenvalues and eigenvectors with Jacobi method.
		j = new JacobiDiagonalization(corr);
		converged = j.compute();
		
		// Get the matrix of eigenvectors.
		v = j.eigenvectors();
		
		// Transpose the matrix to make it easier to sort the eigenvectors by eigenvalue.
		// i.e., since java 2D arrays are arrays of arrays, having eigenvectors in rows instead of columns will make it easier to sort.
		v = MatrixOps.transposeSquareMatrixInline(v);
		
		// Sort the eigenvectors by eigenvalue (largest eigenvalue to smallest)
		pairs = new EigenValueVectorPair[v.length];
		sumEigenvalues = 0;
		for (int i = 0; i < v.length; i++) {
			pairs[i] = new EigenValueVectorPair(j.eigenvalues()[i], v[i]);
			sumEigenvalues = sumEigenvalues + pairs[i].value;
		}
		Arrays.sort(pairs);
		
		System.out.println(converged ? "Convergence Achieved" : "Max Iterations Caused Termination of Jacobi Method");
		System.out.println("Eigenvalues and Eigenvectors (using sampled data) sorted by absolute value of Eigenvalue:");
		System.out.println();
		System.out.println("pc\teigenvalue\tproportion\tcumulative");
		runningSum = 0;
		c = 1;
		for (EigenValueVectorPair pair : pairs) {
			runningSum = runningSum + pair.value;
			System.out.printf("%3d\t%6.4f\t%6.4f\t%6.4f\n", c, pair.value, pair.value / sumEigenvalues, runningSum / sumEigenvalues);
			c++;
		}
		System.out.println();
		System.out.println();
		System.out.print("distance");
		for (int i = 1; i <= 5 /*numMeasures*/; i++) {
			System.out.print("\tpc" + i);
		}
		System.out.println();
		
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			int x = 0;
			for (EigenValueVectorPair pair : pairs) {
				System.out.printf("\t&%6.4f", pair.vector[i]);
				x++;
				if (x==5) break;
			}
			System.out.println();
		}
		System.out.println();
		
		// compute and output correlation coefficients between original distances and the principle components
		System.out.println("Correlation between original distance measures and principle components.");
		System.out.print("distance");
		for (int i = 1; i <= 5 /*numMeasures*/; i++) {
			System.out.print("\tpc" + i);
		}
		System.out.println();
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			for (int k = 0; k < 5 /*numMeasures*/; k++) {
				System.out.printf("\t&%6.4f", Math.sqrt(pairs[k].value)*pairs[k].vector[i]);
			}
			System.out.println();
		}
		System.out.println();
		
		
		System.out.println();
		System.out.println();
		System.out.println("Data for Fitness Distance Correlation Examples from Section 5 of the paper");
		System.out.println("Note that the data generated here may not be identical");
		System.out.println("to the data in the paper due to random behavior, but should");
		System.out.println("be consistent statistically.");
		System.out.println();
		System.out.println();
		System.out.println("Fitness Distance Correlation example 1 (R-permutation landscape): TSP");
		System.out.println("Simple TSP example with known optimal: cities arranged on a circle.");
		
		double[][] fdcTable = new double[numMeasures][5];
		
		// Generate city locations: on a circle centered at (0,0).
		final int NUM_CITIES = 20;
		final double RADIUS = 1;
		double[][] cities = new double[NUM_CITIES][2];
		double angle = 0.0;
		final double DELTA_A = 2.0 * Math.PI / NUM_CITIES;
		for (int i = 0; i < NUM_CITIES; i++) {
			cities[i][0] = RADIUS * Math.cos(angle);
			cities[i][1] = RADIUS * Math.sin(angle);
			angle += DELTA_A;
		}
		
		int[] optimalAsArray = new int[NUM_CITIES];
		for (int i = 0; i < NUM_CITIES; i++) optimalAsArray[i] = i;
		Permutation opimalTour = new Permutation(optimalAsArray);
		
		ArrayList<CyclicReversalIndependentDistance> cyclicAndReversal = new ArrayList<CyclicReversalIndependentDistance>();
		ArrayList<CyclicIndependentDistance> cyclic = new ArrayList<CyclicIndependentDistance>();
		for (PermutationDistanceMeasurer m : measures) {
			cyclicAndReversal.add(new CyclicReversalIndependentDistance(m));
			cyclic.add(new CyclicIndependentDistance(m));
		}
		
		// generate randomly sampled permutations, and other data needed for fitness-distance correlation
		// Simple TSP
		final int NUM_SAMPLES = 100000;
		double[] tourLength = new double[NUM_SAMPLES];
		double[][] dataD = new double[numMeasures][NUM_SAMPLES];
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Permutation randomTour = new Permutation(NUM_CITIES);
			tourLength[i] = tourCost(cities, randomTour);
			for (int k = 0; k < numMeasures; k++) {
				dataD[k][i] = cyclicAndReversal.get(k).distance(opimalTour,randomTour);
			}
		}
		
		System.out.println();
		System.out.println("Outputting fitness-distance correlations.");
		for (int i = 0; i < numMeasures; i++) {
			fdcTable[i][0] = correlation(tourLength, dataD[i]);
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\n", fdcTable[i][0]);
		}
		System.out.println();
		
		System.out.println();
		System.out.println();
		System.out.println("Fitness Distance Correlation example 2 (R-permutation directed edges): Asymmetric TSP");
		System.out.println("Simple ATSP example with known optimal: cities arranged on a circle.");
		
		// generate randomly sampled permutations, and other data needed for fitness-distance correlation
		// Simple Asymmetric TSP
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Permutation randomTour = new Permutation(NUM_CITIES);
			tourLength[i] = tourCostAsymmetric(cities, randomTour);
			for (int k = 0; k < numMeasures; k++) {
				dataD[k][i] = cyclic.get(k).distance(opimalTour,randomTour);
			}
		}
		System.out.println();
		System.out.println("Outputting fitness-distance correlations.");
		for (int i = 0; i < numMeasures; i++) {
			fdcTable[i][1] = correlation(tourLength, dataD[i]);
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\n", fdcTable[i][1]);
		}
		System.out.println();
		
		System.out.println();
		System.out.println();
		System.out.println("Fitness Distance Correlation example 3: A-permutation landscape");
		System.out.println("Simple mapping example with known optimal.");
		double[] fitness = new double[NUM_SAMPLES];
		final int MAPPING_LENGTH = 10;
		optimalAsArray = new int[MAPPING_LENGTH];
		for (int i = 0; i < MAPPING_LENGTH; i++) optimalAsArray[i] = i;
		Permutation opimalPerm = new Permutation(optimalAsArray);
		ExactMatchDistance em = new ExactMatchDistance();
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Permutation randomPerm = new Permutation(MAPPING_LENGTH);
			fitness[i] = em.distance(opimalPerm, randomPerm);
			if (fitness[i] > 0.0) {
				fitness[i] *= (1.0+0.5*ThreadLocalRandom.current().nextDouble());
			}
			for (int k = 0; k < numMeasures; k++) {
				dataD[k][i] = measures.get(k).distance(opimalPerm,randomPerm);
			}
		}
		System.out.println();
		System.out.println("Outputting fitness-distance correlations.");
		for (int i = 0; i < numMeasures; i++) {
			fdcTable[i][2] = correlation(fitness, dataD[i]);
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\n", fdcTable[i][2]);
		}
		System.out.println();
		
		
		System.out.println();
		System.out.println();
		System.out.println("Fitness Distance Correlation example 4: P-permutation landscape");
		System.out.println("Simple ranking example with known optimal.");
		KendallTauDistance tau = new KendallTauDistance();
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Permutation randomPerm = new Permutation(MAPPING_LENGTH);
			fitness[i] = tau.distance(opimalPerm, randomPerm);
			if (fitness[i] > 0.0) {
				fitness[i] *= (1.0+0.5*ThreadLocalRandom.current().nextDouble());
			}
			for (int k = 0; k < numMeasures; k++) {
				dataD[k][i] = measures.get(k).distance(opimalPerm,randomPerm);
			}
		}
		System.out.println();
		System.out.println("Outputting fitness-distance correlations.");
		for (int i = 0; i < numMeasures; i++) {
			fdcTable[i][3] = correlation(fitness, dataD[i]);
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\n", fdcTable[i][3]);
		}
		System.out.println();
		
		
		System.out.println();
		System.out.println();
		System.out.println("Fitness Distance Correlation example 5: P-permutation, cyclic, landscape");
		System.out.println("Simple cyclic P-permutation example with known optimal.");
		LeeDistance lee = new LeeDistance();
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Permutation randomPerm = new Permutation(MAPPING_LENGTH);
			fitness[i] = lee.distance(opimalPerm, randomPerm);
			if (fitness[i] > 0.0) {
				fitness[i] *= (1.0+0.5*ThreadLocalRandom.current().nextDouble());
			}
			for (int k = 0; k < numMeasures; k++) {
				dataD[k][i] = measures.get(k).distance(opimalPerm,randomPerm);
			}
		}
		System.out.println();
		System.out.println("Outputting fitness-distance correlations.");
		for (int i = 0; i < numMeasures; i++) {
			fdcTable[i][4] = correlation(fitness, dataD[i]);
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\n", fdcTable[i][4]);
		}
		System.out.println();
		
		System.out.println();
		System.out.println("Outputting All FDC Data as One Table");
		System.out.println("Distance\t& Landscape 1\t& Landscape 2\t& Landscape 3\t& Landscape 4\t& Landscape 5");
		for (int i = 0; i < numMeasures; i++) {
			System.out.print(measures.get(i).getClass().getSimpleName());
			System.out.printf("\t&%6.4f\t&%6.4f\t&%6.4f\t&%6.4f\t&%6.4f\n", fdcTable[i][0], fdcTable[i][1], fdcTable[i][2], fdcTable[i][3], fdcTable[i][4]);
		}
		System.out.println();
	}
	
	private static double tourCost(double[][] cities, Permutation p) {
		double cost = 0;
		for (int i = 0; i < p.length(); i++) {
			int start = p.get(i);
			int end = p.get((i+1)%p.length());
			double deltaX = cities[start][0] - cities[end][0];
			double deltaY = cities[start][1] - cities[end][1];
			cost = cost + Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		}
		return cost;
	}
	
	private static double tourCostAsymmetric(double[][] cities, Permutation p) {
		double cost = 0;
		for (int i = 0; i < p.length(); i++) {
			int start = p.get(i);
			int end = p.get((i+1)%p.length());
			double deltaX = cities[start][0] - cities[end][0];
			double deltaY = cities[start][1] - cities[end][1];
			double edgeCost = start < end || (end==0 && start==p.length()-1) ? Math.sqrt(deltaX*deltaX + deltaY*deltaY) : 2.0; 
			cost = cost + edgeCost;
		}
		return cost;
	}
	
	
	private static int fact(int N) {
		int f = 1;
		for (int i = 2; i <= N; i++) {
			f *= i;
		}
		return f;
	}
}
