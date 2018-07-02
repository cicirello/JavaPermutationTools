/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of package org.cicirello.math.la.
 *
 * Java package org.cicirello.math.la is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * Java package org.cicirello.math.la is distributed in the hope 
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

package org.cicirello.math.la;

/**
 * This class uses Jacobi iteration to compute the eigenvalues and eigenvectors of a symmetric matrix, provided as a 2-D Java array.
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a> 
 * @version 07.01.2018
 */
public class JacobiDiagonalization {
	
	/**
	 * Default precision level for convergence check.
	 */
	public static final double EPSILON = 1e-5;
	
	/**
	 * Default max number of iterations.
	 */
	public static final int MAX_ITERATIONS = 10000000;
	
	private double[][] a;
	private final int N;
	private double[][] eigenvectors;
	private double[] eigenvalues;
	
	
	/**
	 * Initializes a Jacobi iteration for finding eigenvalues and eigenvectors of a matrix.
	 * @param matrix a square matrix
	 */
	public JacobiDiagonalization(int[][] matrix) {
		if (matrix.length != matrix[0].length) throw new IllegalArgumentException("Must be a square matrix.");
		N = matrix.length;
		a = new double[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a[i][j] = matrix[i][j];
			}
		}
	}
	
	/**
	 * Initializes a Jacobi iteration for finding eigenvalues and eigenvectors of a matrix.
	 * @param matrix a square matrix
	 */
	public JacobiDiagonalization(double[][] matrix) {
		if (matrix.length != matrix[0].length) throw new IllegalArgumentException("Must be a square matrix.");
		N = matrix.length;
		a = new double[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a[i][j] = matrix[i][j];
			}
		}
	}
	
	/**
	 * Gets the computed eigenvectors.
	 * @return The eigenvectors are the columns of the returned matrix.  Returns null if not yet computed.
	 */
	public double[][] eigenvectors() {
		return eigenvectors;
	}
	
	/**
	 * Gets the computed eigenvalues.
	 * @return The eigenvalues.  Returns null if not yet computed.
	 */
	public double[] eigenvalues() {
		return eigenvalues;
	}
	
	/**
	 * Computes the eigenvalues and eigenvectors using Jacobi Iteration.  Uses the default precision level JacobiDiagonalization.EPSILON
	 * where off-diagonal elements less than JacobiDiagonalization.EPSILON in absolute value are set to 0.
	 * Uses the default max number of iterations JacobiDiagonalization.MAX_ITERATIONS.
	 * @return true if Jacobi method converged, and false if terminated due to maximum number of iterations
	 */
	public boolean compute() {
		return compute(EPSILON, MAX_ITERATIONS);
	}
	
	/**
	 * Computes the eigenvalues and eigenvectors using Jacobi Iteration.  Uses the default max number of iterations JacobiDiagonalization.MAX_ITERATIONS.
	 *
	 * @param epsilon Precision level, where off-diagonal elements less than epsilon in absolute value are set to 0.
	 * @return true if Jacobi method converged, and false if terminated due to maximum number of iterations
	 */
	public boolean compute(double epsilon) {
		return compute(epsilon, MAX_ITERATIONS);
	}
	
	/**
	 * Computes the eigenvalues and eigenvectors using Jacobi Iteration.  Uses the default precision level JacobiDiagonalization.EPSILON
	 * where off-diagonal elements less than JacobiDiagonalization.EPSILON in absolute value are set to 0. 
	 *
	 * @param maxIters Maximum number of iterations.
	 * @return true if Jacobi method converged, and false if terminated due to maximum number of iterations
	 */
	public boolean compute(int maxIters) {
		return compute(EPSILON, maxIters);
	}
	
	/**
	 * Computes the eigenvalues and eigenvectors using Jacobi Iteration.  
	 *
	 * @param epsilon Precision level, where off-diagonal elements less than epsilon in absolute value are set to 0.
	 * @param maxIters Maximum number of iterations.
	 * @return true if Jacobi method converged, and false if terminated due to maximum number of iterations
	 */
	public boolean compute(double epsilon, int maxIters) {
		for (int i = 0; i < maxIters; i++) {
			if (oneIteration(epsilon)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean oneIteration(double epsilon) {
		// find largest in absolute value element not on main diagonal
		int p = -1;
		int q = -1;
		double a_pq = 0;
		double sumOffDiagonal = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i==j) continue;
				double absAij = Math.abs(a[i][j]);
				if (p < 0 || absAij > a_pq) {
					p=i;
					q=j;
					a_pq = absAij;
				}
				sumOffDiagonal = sumOffDiagonal + absAij;
			}
		}
		// check for convergence
		if (sumOffDiagonal == 0.0) {
			if (eigenvalues == null) {
				eigenvalues = new double[N];
				for (int i = 0; i < N; i++) {
					eigenvalues[i] = a[i][i];
				}
			}
			if (eigenvectors == null) {
				eigenvectors = copy(a);
			}
			return true;
		}
		// If a_pq is small, simply set entry to 0 and don't rotate.
		if (a_pq < epsilon) {
			a[p][q] = 0;
			return false;
		}
		// compute some values
		double f = -a_pq;
		double g = 0.5*(a[p][p]-a[q][q]);
		double g_sign = g < 0 ? -1 : 1;
		double h = g_sign * f / Math.sqrt(f*f + g*g);
		double sin_theta = h / Math.sqrt(2*(1+Math.sqrt(1-h*h)));
		double cos_theta = Math.sqrt(1 - sin_theta * sin_theta);
		// compute rotation 
		double e_pp = cos_theta;
		double e_qq = cos_theta;
		double e_pq = sin_theta;
		double e_qp = -sin_theta;
		// update eigenvector approximations
		if (eigenvectors == null) {
			eigenvectors = new double[N][N];
			for (int i = 0; i < N; i++) {
				eigenvectors[i][i] = 1;
			}
			eigenvectors[p][p] = eigenvectors[q][q] = cos_theta;
			eigenvectors[p][q] = sin_theta;
			eigenvectors[q][p] = -sin_theta;
		} else {
			double[][] temp = new double[N][2];
			for (int i = 0; i < N; i++) {
				temp[i][0] = eigenvectors[i][p]*e_pp + eigenvectors[i][q]*e_qp;
				temp[i][1] = eigenvectors[i][p]*e_pq + eigenvectors[i][q]*e_qq;
			}
			for (int i = 0; i < N; i++) {
				eigenvectors[i][p] = temp[i][0];
				eigenvectors[i][q] = temp[i][1];
			}
		}
		// rotate A
		double[][] temp = new double[N][2];
		for (int i = 0; i < N; i++) {
			temp[i][0] = a[i][p]*e_pp + a[i][q]*e_qp;
			temp[i][1] = a[i][p]*e_pq + a[i][q]*e_qq;
		}
		for (int i = 0; i < N; i++) {
			a[i][p] = temp[i][0];
			a[i][q] = temp[i][1];
		}
		for (int i = 0; i < N; i++) {
			temp[i][0] = a[p][i]*e_pp + a[q][i]*e_qp;
			temp[i][1] = a[p][i]*e_pq + a[q][i]*e_qq;
		}
		for (int i = 0; i < N; i++) {
			a[p][i] = temp[i][0];
			a[q][i] = temp[i][1];
		}
		// update eigenvalues
		if (eigenvalues == null) {
			eigenvalues = new double[N];
		} 
		for (int i = 0; i < N; i++) {
			eigenvalues[i] = a[i][i];
		}
		return false; 
	}
	
	private double[][] copy(double[][] array) {
		double[][] result = new double[array.length][];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i].clone();
		}
		return result;
	}
	
}