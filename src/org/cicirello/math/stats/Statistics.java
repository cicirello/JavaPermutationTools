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

/**
 * Utility class of basic statistics.
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a> 
 * @version 1.18.7.1
 * @since 1.0
 */
public class Statistics {
	
	/**
	 * Computes mean of a dataset.
	 * @param data The dataset.
	 * @return the mean of the data.
	 */
	public static double mean(int[] data) {
		double mean = 0;
		int sum = 0;
		for (int e : data) sum = sum + e;
		mean = 1.0 * sum / data.length;
		return mean;
	}
	
	/**
	 * Computes mean of a dataset.
	 * @param data The dataset.
	 * @return the mean of the data.
	 */
	public static double mean(double[] data) {
		double mean = 0;
		for (double e : data) mean = mean + e;
		mean = mean / data.length;
		return mean;
	}
	
	/**
	 * Computes variance of a population.
	 * @param data The dataset.
	 * @return the variance of the data.
	 */
	public static double variance(int[] data) {
		if (data.length < 2) return 0.0;
		double mean = mean(data);
		double sumSquares = 0;
		double sum = 0;
		for (int e : data) {
			sumSquares = sumSquares + (e-mean)*(e-mean); 
			sum = sum + (e-mean);
		}
		return (sumSquares - sum*sum/data.length)/data.length;
	}
	
	
	/**
	 * Computes variance of a population.
	 * @param data The dataset.
	 * @return the variance of the data.
	 */
	public static double variance(double[] data) {
		if (data.length < 2) return 0.0;
		double mean = mean(data);
		double sumSquares = 0;
		double sum = 0;
		for (double e : data) {
			sumSquares = sumSquares + (e-mean)*(e-mean); 
			sum = sum + (e-mean);
		}
		return (sumSquares - sum*sum/data.length)/data.length;
	}
	
	/**
	 * Computes variance of a sample.
	 * @param data The dataset.
	 * @return the variance of the data.
	 */
	public static double varianceSample(int[] data) {
		if (data.length < 2) return 0.0;
		double mean = mean(data);
		double sumSquares = 0;
		double sum = 0;
		for (int e : data) {
			sumSquares = sumSquares + (e-mean)*(e-mean); 
			sum = sum + (e-mean);
		}
		return (sumSquares - sum*sum/data.length)/(data.length-1.0);
	}
	
	
	/**
	 * Computes variance of a sample.
	 * @param data The dataset.
	 * @return the variance of the data.
	 */
	public static double varianceSample(double[] data) {
		if (data.length < 2) return 0.0;
		double mean = mean(data);
		double sumSquares = 0;
		double sum = 0;
		for (double e : data) {
			sumSquares = sumSquares + (e-mean)*(e-mean); 
			sum = sum + (e-mean);
		}
		return (sumSquares - sum*sum/data.length)/(data.length-1.0);
	}
	
	/**
	 * Computes covariance for a pair of random variables.
	 * @param X Array of samples of first variable.
	 * @param Y Array of samples of second variable.
	 * @return the covariance of X and Y.
	 */
	public static double covariance(int[] X, int[] Y) {
		if (X.length < 2) return 0.0;
		if (X.length != Y.length) throw new IllegalArgumentException("Arrays must have same length!");
		double meanX = mean(X);
		double meanY = mean(Y);
		double sumX = 0;
		double sumY = 0;
		double sumProduct = 0;
		for (int i = 0; i < X.length; i++) {
			sumX = sumX + (X[i] - meanX);
			sumY = sumY + (Y[i] - meanY);
			sumProduct = sumProduct + (X[i] - meanX)*(Y[i] - meanY);
		}
		return (sumProduct - sumX*sumY/X.length)/X.length;
	}
	
		
	/**
	 * Computes covariance for a pair of random variables.
	 * @param X Array of samples of first variable.
	 * @param Y Array of samples of second variable.
	 * @return the covariance of X and Y.
	 */
	public static double covariance(double[] X, double[] Y) {
		if (X.length < 2) return 0.0;
		if (X.length != Y.length) throw new IllegalArgumentException("Arrays must have same length!");
		double meanX = mean(X);
		double meanY = mean(Y);
		double sumX = 0;
		double sumY = 0;
		double sumProduct = 0;
		for (int i = 0; i < X.length; i++) {
			sumX = sumX + (X[i] - meanX);
			sumY = sumY + (Y[i] - meanY);
			sumProduct = sumProduct + (X[i] - meanX)*(Y[i] - meanY);
		}
		return (sumProduct - sumX*sumY/X.length)/X.length;
	}
	
	/**
	 * Computes correlation coefficient for a pair of random variables.
	 * @param X Array of samples of first variable.
	 * @param Y Array of samples of second variable.
	 * @return the correlation coefficient of X and Y.
	 */
	public static double correlation(int[] X, int[] Y) {
		double varX = variance(X);
		if (varX == 0.0) return 0.0;
		double varY = variance(Y);
		if (varY == 0.0) return 0.0;
		double covar = covariance(X,Y);
		if (covar == 0.0) return 0.0;
		
		boolean negate = false;
		if (covar < 0.0) {
			negate = true;
			covar = -covar;
		}
		double logCovar = Math.log(covar);
		double logVarX = Math.log(varX);
		double logVarY = Math.log(varY);
		double corr = Math.exp(logCovar - 0.5 * logVarX - 0.5 * logVarY);
		if (negate) corr = -corr;
		return corr;
	}
	
		
	/**
	 * Computes correlation coefficient for a pair of random variables.
	 * @param X Array of samples of first variable.
	 * @param Y Array of samples of second variable.
	 * @return the correlation coefficient of X and Y.
	 */
	public static double correlation(double[] X, double[] Y) {
		double varX = variance(X);
		if (varX == 0.0) return 0.0;
		double varY = variance(Y);
		if (varY == 0.0) return 0.0;
		double covar = covariance(X,Y);
		if (covar == 0.0) return 0.0;
		
		boolean negate = false;
		if (covar < 0.0) {
			negate = true;
			covar = -covar;
		}
		double logCovar = Math.log(covar);
		double logVarX = Math.log(varX);
		double logVarY = Math.log(varY);
		double corr = Math.exp(logCovar - 0.5 * logVarX - 0.5 * logVarY);
		if (negate) corr = -corr;
		return corr;
	}
	
	/**
	 * Computes correlation matrix.
	 * @param data The data with random variables in rows and samples in columns.
	 * @return the correlation matrix, M, where M[i][j] is the correlation coefficient of data[i] and data[j].
	 */
	public static double[][] correlationMatrix(int[][] data) {
		double[][] corr = new double[data.length][data.length];
		for (int i = 0; i < data.length; i++) {
			corr[i][i] = 1.0;
			for (int j = i+1; j < data.length; j++) {
				corr[i][j] = corr[j][i] = correlation(data[i], data[j]);
			}
		}
		return corr;
	}
	
	/**
	 * Computes correlation matrix.
	 * @param data The data with random variables in rows and samples in columns.
	 * @return the correlation matrix, M, where M[i][j] is the correlation coefficient of data[i] and data[j].
	 */
	public static double[][] correlationMatrix(double[][] data) {
		double[][] corr = new double[data.length][data.length];
		for (int i = 0; i < data.length; i++) {
			corr[i][i] = 1.0;
			for (int j = i+1; j < data.length; j++) {
				corr[i][j] = corr[j][i] = correlation(data[i], data[j]);
			}
		}
		return corr;
	}
}

