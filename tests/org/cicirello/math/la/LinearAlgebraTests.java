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

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Random;

/**
 * JUnit 4 tests for the methods of the Statistics class.
 */
public class LinearAlgebraTests {
	
	private static double EPSILON = 1e-10;
	
	@Test
	public void testTransposeInts() {
		for (int n = 0; n < 5; n++) {
			int[][] m = getSquareIntsForTranspose(n);
			int[][] transpose = getSquareIntsForTranspose(n);
			int[][] result = MatrixOps.transposeSquareMatrixInline(transpose);
			assertEquals("should return reference to parameter", (Object)transpose, (Object)result);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					assertEquals("position [i][j] should be equal to position [j][i] of original", m[j][i], transpose[i][j]);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.transposeSquareMatrixInline(new int[1][2])
		);
	}
	
	@Test
	public void testTransposeDoubles() {
		for (int n = 0; n < 5; n++) {
			double[][] m = getSquareDoublesForTranspose(n);
			double[][] transpose = getSquareDoublesForTranspose(n);
			double[][] result = MatrixOps.transposeSquareMatrixInline(transpose);
			assertEquals("should return reference to parameter", (Object)transpose, (Object)result);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					assertEquals("position [i][j] should be equal to position [j][i] of original", m[j][i], transpose[i][j], 0.0);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.transposeSquareMatrixInline(new double[1][2])
		);
	}
	
	@Test
	public void testSumInts() {
		for (int n = 0; n < 5; n++) {
			for (int m = 0; m < 5; m++) {
				int[][] a = getMatrixAInts(n,m);
				int[][] b = getMatrixBInts(n,m);
				int[][] c = MatrixOps.sum(a,b,null);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c[i][j]);
					}
				}
				c = null;
				c = MatrixOps.sum(a,b);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c[i][j]);
					}
				}
				int[][] c2 = new int[n][m];
				int[][] c3 = MatrixOps.sum(a,b,c2);
				assertEquals((Object)c2, (Object)c3);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c2[i][j]);
					}
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new int[1][2], new int[2][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new int[1][2], new int[1][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new int[1][1], new int[1][1], new int[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new int[1][1], new int[1][1], new int[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new int[0][0], new int[0][0], new int[1][2])
		);
	}
	
	@Test
	public void testSumDoubles() {
		for (int n = 0; n < 5; n++) {
			for (int m = 0; m < 5; m++) {
				double[][] a = getMatrixA_D(n,m);
				double[][] b = getMatrixB_D(n,m);
				double[][] c = MatrixOps.sum(a,b,null);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c[i][j], EPSILON);
					}
				}
				c = null;
				c = MatrixOps.sum(a,b);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c[i][j], EPSILON);
					}
				}
				double[][] c2 = new double[n][m];
				double[][] c3 = MatrixOps.sum(a,b,c2);
				assertEquals((Object)c2, (Object)c3);
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("sum",n*m+1, c2[i][j], EPSILON);
					}
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new double[1][2], new double[2][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new double[1][2], new double[1][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new double[1][1], new double[1][1], new double[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new double[1][1], new double[1][1], new double[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.sum(new double[0][0], new double[0][0], new double[1][2])
		);
	}
	
	@Test
	public void testDifferenceInts() {
		for (int n = 0; n < 5; n++) {
			for (int m = 0; m < 5; m++) {
				int[][] a = fillMatrix(n,m,n*m+1);
				int[][] b = getMatrixBInts(n,m);
				int[][] c = MatrixOps.difference(a,b,null);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				int k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c[i][j]);
						k++;
					}
				}
				c = null;
				c = MatrixOps.difference(a,b);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c[i][j]);
						k++;
					}
				}
				int[][] c2 = new int[n][m];
				int[][] c3 = MatrixOps.difference(a,b,c2);
				assertEquals((Object)c2, (Object)c3);
				k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c2[i][j]);
						k++;
					}
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new int[1][2], new int[2][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new int[1][2], new int[1][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new int[1][1], new int[1][1], new int[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new int[1][1], new int[1][1], new int[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new int[0][0], new int[0][0], new int[1][2])
		);
	}
	
	@Test
	public void testDifferenceDoubles() {
		for (int n = 0; n < 5; n++) {
			for (int m = 0; m < 5; m++) {
				double[][] a = fillMatrix(n,m,n*m+1.0);
				double[][] b = getMatrixB_D(n,m);
				double[][] c = MatrixOps.difference(a,b,null);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				int k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c[i][j], EPSILON);
						k++;
					}
				}
				c = null;
				c = MatrixOps.difference(a,b);
				assertEquals("result rows",n, c.length);
				if (n > 0) assertEquals("result cols", m, c[0].length);
				k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c[i][j], EPSILON);
						k++;
					}
				}
				double[][] c2 = new double[n][m];
				double[][] c3 = MatrixOps.difference(a,b,c2);
				assertEquals((Object)c2, (Object)c3);
				k = 1;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						assertEquals("diff",k, c2[i][j], EPSILON);
						k++;
					}
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new double[1][2], new double[2][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new double[1][2], new double[1][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new double[1][1], new double[1][1], new double[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new double[1][1], new double[1][1], new double[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.difference(new double[0][0], new double[0][0], new double[1][2])
		);
	}
	
	@Test
	public void testProductIdentityInt() {
		int[][] A = new int[0][0];
		int[][] B = new int[0][0];
		int[][] C = MatrixOps.product(A,B,null);
		assertEquals(0, C.length);
		C = new int[0][0];
		int[][] C2 = MatrixOps.product(A,B,C);
		assertEquals(0, C.length);
		assertEquals((Object)C, (Object)C2);
		C = null;
		C = MatrixOps.product(A,B);
		assertEquals(0, C.length);
		for (int n = 1; n < 5; n++) {
			int[][] I = getI(n);
			for (int m = 1; m < 5; m++) {
				C = C2 = null;
				A = getMatrixAInts(n,m);
				C = MatrixOps.product(I,A,null);
				assertEquals(A.length, C.length);
				assertEquals(A[0].length, C[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C[i]);
				}
				C = new int[n][m];
				C2 = MatrixOps.product(I,A,C);
				assertEquals((Object)C, (Object)C2);
				assertEquals(A.length, C2.length);
				assertEquals(A[0].length, C2[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C2[i]);
				}
				C = null;
				C = MatrixOps.product(I,A);
				assertEquals(A.length, C.length);
				assertEquals(A[0].length, C[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C[i]);
				}
				B = getMatrixAInts(m,n);
				C = MatrixOps.product(B,I,null);
				assertEquals(B.length, C.length);
				assertEquals(B[0].length, C[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C[i]);
				}
				C = new int[m][n];
				C2 = MatrixOps.product(B,I,C);
				assertEquals((Object)C, (Object)C2);
				assertEquals(B.length, C2.length);
				assertEquals(B[0].length, C2[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C2[i]);
				}
				C = null;
				C = MatrixOps.product(B,I);
				assertEquals(B.length, C.length);
				assertEquals(B[0].length, C[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C[i]);
				}
			}
		}
		
	}
	
	@Test
	public void testProductIdentityDouble() {
		double[][] A = new double[0][0];
		double[][] B = new double[0][0];
		double[][] C = MatrixOps.product(A,B,null);
		assertEquals(0, C.length);
		C = new double[0][0];
		double[][] C2 = MatrixOps.product(A,B,C);
		assertEquals(0, C.length);
		assertEquals((Object)C, (Object)C2);
		C = null;
		C = MatrixOps.product(A,B);
		assertEquals(0, C.length);
		for (int n = 1; n < 5; n++) {
			double[][] I = getI_d(n);
			for (int m = 1; m < 5; m++) {
				C = C2 = null;
				A = getMatrixA_D(n,m);
				C = MatrixOps.product(I,A,null);
				assertEquals(A.length, C.length);
				assertEquals(A[0].length, C[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C[i], EPSILON);
				}
				C = new double[n][m];
				C2 = MatrixOps.product(I,A,C);
				assertEquals((Object)C, (Object)C2);
				assertEquals(A.length, C2.length);
				assertEquals(A[0].length, C2[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C2[i], EPSILON);
				}
				C = null;
				C = MatrixOps.product(I,A);
				assertEquals(A.length, C.length);
				assertEquals(A[0].length, C[0].length);
				for (int i = 0; i < A.length; i++) {
					assertArrayEquals(A[i], C[i], EPSILON);
				}
				B = getMatrixA_D(m,n);
				C = MatrixOps.product(B,I,null);
				assertEquals(B.length, C.length);
				assertEquals(B[0].length, C[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C[i], EPSILON);
				}
				C = new double[m][n];
				C2 = MatrixOps.product(B,I,C);
				assertEquals((Object)C, (Object)C2);
				assertEquals(B.length, C2.length);
				assertEquals(B[0].length, C2[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C2[i], EPSILON);
				}
				C = null;
				C = MatrixOps.product(B,I);
				assertEquals(B.length, C.length);
				assertEquals(B[0].length, C[0].length);
				for (int i = 0; i < B.length; i++) {
					assertArrayEquals(B[i], C[i], EPSILON);
				}
			}
		}	
	}
	
	@Test
	public void testProductInt() {
		for (int n = 1; n < 5; n++) {
			for (int m = 1; m < 5; m++) {
				int[][] A = getMultA(n,m);
				int[][] B = getMultB(m,n);
				int[][] C = MatrixOps.product(A, B);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6, C[0][0]);
				int sum = (m+1)*m/2;
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i]);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i]);
				}
				C = null;
				C = MatrixOps.product(A, B, null);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6, C[0][0]);
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i]);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i]);
				}
				int[][] C2 = new int[n][n];
				C = null;
				C = MatrixOps.product(A, B, C2);
				assertEquals((Object)C, (Object)C2);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6, C[0][0]);
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i]);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i]);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[1][2], new int[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[0][1], new int[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[1][1], new int[0][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[1][1], new int[1][1], new int[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[1][1], new int[1][1], new int[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new int[0][0], new int[0][0], new int[1][2])
		);
	}
	
	@Test
	public void testProductDouble() {
		for (int n = 1; n < 5; n++) {
			for (int m = 1; m < 5; m++) {
				double[][] A = getMultA_d(n,m);
				double[][] B = getMultB_d(m,n);
				double[][] C = MatrixOps.product(A, B);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6.0, C[0][0], EPSILON);
				double sum = (m+1)*m/2;
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i], EPSILON);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i], EPSILON);
				}
				C = null;
				C = MatrixOps.product(A, B, null);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6.0, C[0][0], EPSILON);
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i], EPSILON);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i], EPSILON);
				}
				double[][] C2 = new double[n][n];
				C = null;
				C = MatrixOps.product(A, B, C2);
				assertEquals((Object)C, (Object)C2);
				assertEquals(A.length, C.length);
				assertEquals(B[0].length, C[0].length);
				assertEquals("[0][0]", (m)*(m+1)*(2*m+1)/6.0, C[0][0], EPSILON);
				for (int i = 1; i < C[0].length; i++) {
					assertEquals("[0][i]", i*sum, C[0][i], EPSILON);
				}
				for (int i = 1; i < C.length; i++) {
					assertArrayEquals(C[0], C[i], EPSILON);
				}
			}
		}
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[1][2], new double[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[0][1], new double[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[1][1], new double[0][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[1][1], new double[1][1], new double[1][2])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[1][1], new double[1][1], new double[2][1])
		);
		thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> MatrixOps.product(new double[0][0], new double[0][0], new double[1][2])
		);
	}
	
	@Test
	public void testJacobi() {
		for (int n = 1; n <= 100; n++) {
			double[][] A = getDiagonal(n);
			JacobiDiagonalization jd = new JacobiDiagonalization(A);
			assertNull(jd.eigenvectors());
			assertNull(jd.eigenvalues());
			assertTrue(jd.compute());
			double[][] P = jd.eigenvectors();
			double[] eig = jd.eigenvalues();
			double[][] lambda = new double[eig.length][eig.length];
			for (int i = 0; i < lambda.length; i++) {
				lambda[i][i] = eig[i];
			}
			double[][] left = MatrixOps.product(A, P);
			double[][] right = MatrixOps.product(P, lambda);
			assertEquals(left.length, right.length);
			assertEquals(left[0].length, right[0].length);
			for (int i = 0; i < left.length; i++) {
				assertArrayEquals("diagonal already", left[i], right[i], JacobiDiagonalization.EPSILON);
			}
			if (n <= 5) {
				A = getSymmetric(n);
				jd = new JacobiDiagonalization(A);
				assertNull("eigvectors should be null", jd.eigenvectors());
				assertNull("eigvalues should be null", jd.eigenvalues());
				assertTrue("should converge", jd.compute());
				P = jd.eigenvectors();
				eig = jd.eigenvalues();
				lambda = new double[eig.length][eig.length];
				for (int i = 0; i < lambda.length; i++) {
					lambda[i][i] = eig[i];
				}
				left = MatrixOps.product(A, P);
				right = MatrixOps.product(P, lambda);
				assertEquals(left.length, right.length);
				assertEquals(left[0].length, right[0].length);
				for (int i = 0; i < left.length; i++) {
					assertArrayEquals("symmetric: " + n, left[i], right[i], 10*n*JacobiDiagonalization.EPSILON);
				}
			}
		}
	}
	
	private double[][] getSymmetric(int n) {
		double[][] A = new double[n][n];
		Random gen = new Random(42);
		for (int i = 0; i < n; i++) {
			A[i][i] = gen.nextDouble()+1;
			for (int j = i+1; j < n; j++) {
				A[i][j] = A[j][i] = gen.nextDouble()+1;
			}
		}
		return A;
	}
	
	private double[][] getDiagonal(int n) {
		double[][] A = new double[n][n];
		Random gen = new Random(42);
		for (int i = 0; i < n; i++) {
			A[i][i] = 99*gen.nextDouble() + 1;
		}
		return A;
	}
	
	private double[][] getMultA_d(int n, int m) {
		double[][] A = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				A[i][j] = j+1;
			}
		}
		return A;
	}
	
	private double[][] getMultB_d(int n, int m) {
		double[][] B = new double[n][m];
		for (int i = 0; i < n; i++) {
			B[i][0] = i+1;
			for (int j = 1; j < m; j++) {
				B[i][j] = j;
			}
		}
		return B;
	}
	
	
	private int[][] getMultA(int n, int m) {
		int[][] A = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				A[i][j] = j+1;
			}
		}
		return A;
	}
	
	private int[][] getMultB(int n, int m) {
		int[][] B = new int[n][m];
		for (int i = 0; i < n; i++) {
			B[i][0] = i+1;
			for (int j = 1; j < m; j++) {
				B[i][j] = j;
			}
		}
		return B;
	}
	
	private int[][] getI(int n) {
		int[][] I = new int[n][n];
		for (int i = 0; i < n; i++) {
			I[i][i] = 1;
		}
		return I;
	}
	
	private double[][] getI_d(int n) {
		double[][] I = new double[n][n];
		for (int i = 0; i < n; i++) {
			I[i][i] = 1.0;
		}
		return I;
	}
	
	private int[][] fillMatrix(int n, int m, int value) {
		if (n==0) m = 0;
		int[][] M = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = value;
			}
		}
		return M;
	}
	
	private double[][] fillMatrix(int n, int m, double value) {
		if (n==0) m = 0;
		double[][] M = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = value;
			}
		}
		return M;
	}
	
	private double[][] getMatrixA_D(int n, int m) {
		if (n==0) m = 0;
		double[][] M = new double[n][m];
		int k = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = k;
				k++;
			}
		}
		return M;
	}
	
	private double[][] getMatrixB_D(int n, int m) {
		if (n==0) m = 0;
		double[][] M = new double[n][m];
		int k = n*m;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = k;
				k--;
			}
		}
		return M;
	}
	
	private int[][] getMatrixAInts(int n, int m) {
		if (n==0) m = 0;
		int[][] M = new int[n][m];
		int k = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = k;
				k++;
			}
		}
		return M;
	}
	
	private int[][] getMatrixBInts(int n, int m) {
		if (n==0) m = 0;
		int[][] M = new int[n][m];
		int k = n*m;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				M[i][j] = k;
				k--;
			}
		}
		return M;
	}
	
	
	private int[][] getSquareIntsForTranspose(int n) {
		int[][] m = new int[n][n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				m[i][j] = k;
				k++;
			}
		}
		return m;
	}
	
	private double[][] getSquareDoublesForTranspose(int n) {
		double[][] m = new double[n][n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				m[i][j] = k;
				k++;
			}
		}
		return m;
	}
	
}