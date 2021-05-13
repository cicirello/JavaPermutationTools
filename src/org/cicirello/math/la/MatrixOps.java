/*
 * Copyright 2018-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

package org.cicirello.math.la;

/**
 * Utility class of basic linear algebra matrix operations, where matrices are 
 * represented as 2-D Java arrays.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>  
 * @version 5.13.2021
 */
public final class MatrixOps {
	
	// Utility class of static methods.  Should not be instantiated.
	private MatrixOps() {}
	
	/**
	 * Transpose a square matrix inline.
	 * @param matrix the matrix to transpose, with result replacing contents of matrix.
	 * @return The matrix is returned for convenience for passing to other 
	 * methods requiring a matrix as parameter.  It is the same
	 *         object reference that was passed as a parameter.
	 */
	public static int[][] transposeSquareMatrixInline(int[][] matrix) {
		if (matrix.length > 0 && matrix.length != matrix[0].length) throw new IllegalArgumentException("Matrix must be square.");
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
		if (matrix.length > 0 && matrix.length != matrix[0].length) throw new IllegalArgumentException("Matrix must be square.");
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
		if (A.length == 0) {
			if (C==null) return new int[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
		}
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) {
			C = new int[A.length][A[0].length];
		} else {
			if (C.length != B.length) throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A, B, and C must be equal.");
		}
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[i].length; j++) {
				C[i][j] = A[i][j] + B[i][j];
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
		if (A.length == 0) {
			if (C==null) return new double[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
		}
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) {
			C = new double[A.length][A[0].length];
		} else {
			if (C.length != B.length) throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A, B, and C must be equal.");
		}
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[i].length; j++) {
				C[i][j] = A[i][j] + B[i][j];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A + B.
	 * @param A matrix
	 * @param B matrix
	 * @return A reference to a new matrix C containing the sum.
	 */
	public static int[][] sum(int[][] A, int[][] B) {
		return sum(A, B, null);
	}
	
	/**
	 * Computes C = A + B.
	 * @param A matrix
	 * @param B matrix
	 * @return A reference to a new matrix C containing the sum.
	 */
	public static double[][] sum(double[][] A, double[][] B) {
		return sum(A, B, null);
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
		if (A.length == 0) {
			if (C==null) return new int[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
		}
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) {
			C = new int[A.length][A[0].length];
		} else {
			if (C.length != B.length) throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A, B, and C must be equal.");
		}
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[i].length; j++) {
				C[i][j] = A[i][j] - B[i][j];
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
		if (A.length == 0) {
			if (C==null) return new double[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
		}
		if (A[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A and B must be equal.");
		if (C == null) {
			C = new double[A.length][A[0].length];
		} else {
			if (C.length != B.length) throw new IllegalArgumentException("Number of rows of A, B, and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of A, B, and C must be equal.");
		}
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[i].length; j++) {
				C[i][j] = A[i][j] - B[i][j];
			}
		}
		return C;
	}
	
	/**
	 * Computes C = A - B.
	 * @param A matrix
	 * @param B matrix
	 * @return A reference to a new matrix C containing the difference.
	 */
	public static int[][] difference(int[][] A, int[][] B) {
		return difference(A, B, null);
	}
	
	/**
	 * Computes C = A - B.
	 * @param A matrix
	 * @param B matrix
	 * @return A reference to a new matrix C containing the difference.
	 */
	public static double[][] difference(double[][] A, double[][] B) {
		return difference(A, B, null);
	}
	
	/**
	 * Computes C = A * B.
	 * @param A matrix
	 * @param B matrix
	 * @param C if C is null then a new matrix is constructed for result, otherwise C is used for result.
	 * @return A reference to the C matrix.
	 */
	public static int[][] product(int[][] A, int[][] B, int[][] C) {
		if ((A.length == 0 && B.length > 0) || (A.length > 0 && B.length == 0)) {
			throw new IllegalArgumentException("If either matrix has 0 rows, both must.");
		} else if (A.length == 0) {
			if (C==null) return new int[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("C's dimensions are inconsistent with A and B.");
		}
		if (A[0].length != B.length) throw new IllegalArgumentException("Number of columns of A must equal number of rows of B.");
		if (C == null) C = new int[A.length][B[0].length];
		else {
			if (C.length != A.length) throw new IllegalArgumentException("Number of rows of A and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of B and C must be equal.");
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B[0].length; j++) {
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
		if ((A.length == 0 && B.length > 0) || (A.length > 0 && B.length == 0)) {
			throw new IllegalArgumentException("If either matrix has 0 rows, both must.");
		} else if (A.length == 0) {
			if (C==null) return new double[0][0];
			else if (C.length==0) return C;
			else throw new IllegalArgumentException("C's dimensions are inconsistent with A and B.");
		}
		if (A[0].length != B.length) throw new IllegalArgumentException("Number of columns of A must equal number of rows of B.");
		if (C == null) C = new double[A.length][B[0].length];
		else {
			if (C.length != A.length) throw new IllegalArgumentException("Number of rows of A and C must be equal.");
			if (C[0].length != B[0].length) throw new IllegalArgumentException("Number of columns of B and C must be equal.");
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B[0].length; j++) {
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
	 * @return A reference to a new matrix C containing the product.
	 */
	public static int[][] product(int[][] A, int[][] B) {
		return product(A,B,null);
	}
	
	/**
	 * Computes C = A * B.
	 * @param A matrix
	 * @param B matrix
	 * @return A reference to a new matrix C containing the product.
	 */
	public static double[][] product(double[][] A, double[][] B) {
		return product(A,B,null);
	}
}