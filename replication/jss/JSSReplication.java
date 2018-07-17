/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * JSSReplication.java is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * JSSReplication.java is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java package org.cicirello.permutations.  If not, 
 * see <http://www.gnu.org/licenses/>.
 *
 */

import org.cicirello.permutations.Permutation;
import org.cicirello.permutations.distance.*;
import static org.cicirello.math.stats.Statistics.*;
import org.cicirello.math.la.JacobiDiagonalization;
import org.cicirello.math.la.MatrixOps;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * This Java program generates all of the data used in the manuscript titled
 * "Classification and Java Package of Permutation Distance Metrics" submitted in July 2018 by Vincent A. Cicirello to
 * the Journal of Statistical Software.
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a> 
 * @version 1.18.7.2
 * @since 1.0
 */
public class JSSReplication {
	
	public static void main(String[] args) {
		final int LENGTH = (args.length > 0) ? Integer.parseInt(args[0]) : 10;
		final boolean INCLUDE_REVERSAL = args.length > 1 && args[1].equalsIgnoreCase("R");
		final int COUNT = fact(LENGTH);
		
		// List of distance measures to use in analysis
		ArrayList<PermutationDistanceMeasurer> measures = new ArrayList<PermutationDistanceMeasurer>();
		measures.add(new ExactMatchDistance());
		measures.add(new InterchangeDistance());
		measures.add(new AcyclicEdgeDistance());
		measures.add(new CyclicEdgeDistance());
		measures.add(new RTypeDistance());
		measures.add(new CyclicRTypeDistance());
		if (INCLUDE_REVERSAL) measures.add(new ReversalDistance(LENGTH));
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
	}
	
	private static int fact(int N) {
		int f = 1;
		for (int i = 2; i <= N; i++) {
			f *= i;
		}
		return f;
	}
}