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
 * Utility class of basic linear algebra matrix operations, where matrices are 
 * represented as 2-D Java arrays.
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a> 
 * @version 07.01.2018
 */
public class MatrixOps {
	
	/**
	 * Transpose a square matrix inline.
	 * @param matrix the matrix to transpose, with result replacing contents of matrix.
	 * @return The matrix is returned for convenience for passing to other methods requiring a matrix as parameter.  It is the same
	 *         object reference that was passed as a parameter.
	 */
	public static int[][] transposeSquareMatrixInline(int[][] matrix) {
		if (matrix.length != matrix[0].length) throw new IllegalArgumentException("Matrix must be square.");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i+1; j < matrix.length; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		return matrix;
	}
	
	/**
	 * Transpose a square matrix inline.
	 * @param matrix the matrix to transpose, with result replacing contents of matrix.
	 * @return The matrix is returned for convenience for passing to other methods requiring a matrix as parameter.  It is the same
	 *         object reference that was passed as a parameter.
	 */
	public static double[][] transposeSquareMatrixInline(double[][] matrix) {
		if (matrix.length != matrix[0].length) throw new IllegalArgumentException("Matrix must be square.");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i+1; j < matrix.length; j++) {
				double temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		return matrix;
	}
	
	/**
	 * Computes C = A + B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static int[][] sum(int[][] A, int[][] B, int[][] C) {
		if (A.length != B.length) throw new IllegalArgumentException("Number of rows of A and B must be equal.");
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) C = new int[A.length][A[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				C[i][j] = A[i][j] + B[i][i];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A + B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static double[][] sum(double[][] A, double[][] B, double[][] C) {
		if (A.length != B.length) throw new IllegalArgumentException("Number of rows of A and B must be equal.");
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) C = new double[A.length][A[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				C[i][j] = A[i][j] + B[i][i];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A - B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static int[][] difference(int[][] A, int[][] B, int[][] C) {
		if (A.length != B.length) throw new IllegalArgumentException("Number of rows of A and B must be equal.");
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) C = new int[A.length][A[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				C[i][j] = A[i][j] - B[i][i];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A - B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static double[][] difference(double[][] A, double[][] B, double[][] C) {
		if (A.length != B.length) throw new IllegalArgumentException("Number of rows of A and B must be equal.");
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) C = new double[A.length][A[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				C[i][j] = A[i][j] - B[i][i];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A * B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static int[][] product(int[][] A, int[][] B, int[][] C) {
		if (A[0].length != B.length) throw new IllegalArgumentException("Number of columns of A must equal number of rows of B.");
		if (C == null) C = new int[A.length][B[0].length];
		else {
			if (C.length != A.length) throw new IllegalArgumentException("Number of rows of A and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of B and C must be equal.");
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B[i].length; j++) {
				C[i][j] = 0;
				for (int k = 0; k < B.length; k++) {
					C[i][j] = C[i][j] + A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A * B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static double[][] product(double[][] A, double[][] B, double[][] C) {
		if (A[0].length != B.length) throw new IllegalArgumentException("Number of columns of A must equal number of rows of B.");
		if (C == null) C = new double[A.length][B[0].length];
		else {
			if (C.length != A.length) throw new IllegalArgumentException("Number of rows of A and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of B and C must be equal.");
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B[i].length; j++) {
				C[i][j] = 0;
				for (int k = 0; k < B.length; k++) {
					C[i][j] = C[i][j] + A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}
}