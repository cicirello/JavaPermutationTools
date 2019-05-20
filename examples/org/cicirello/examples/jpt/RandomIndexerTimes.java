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


package org.cicirello.examples.jpt;

import java.util.concurrent.ThreadLocalRandom;
import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import org.cicirello.math.stats.Statistics;
import org.cicirello.math.rand.RandomIndexer;

/**
 * Simple program comparing CPU time of RandomIndexer.nextInt vs ThreadLocalRandom.nextInt
 * to check whether there is a time advantage to using it for small bounds, as is commonly
 * encountered as array indexes; and whether there is a time disadvantage for larger bounds.
 * There is an optional command line argument, which is used to do the timing with multiple threads
 * to test whether threading affects runtime.  To use that command line argument, simply pass an integer
 * value for the number of threads via the command line.
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class RandomIndexerTimes {
	
	private final static int N = 1000000;
	private final static int TRIALS = 100;
	
	public static void main(String[] args) {
		
		final int NUM_THREADS = args.length > 0 ? Integer.parseInt(args[0]) : 0;
		if (NUM_THREADS > 0) {
			timeWithThreads(NUM_THREADS);
			System.exit(0);
		}
		
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		
		// Attempt to "warm-up" Java's JIT compiler.
		for (int bound = 2; bound <= 512; bound*= 2) {
			for (int j = 0; j < 100000; j++) {
				RandomIndexer.nextInt(bound-1);
				RandomIndexer.nextInt(bound);
				RandomIndexer.nextInt(bound+1);
				ThreadLocalRandom.current().nextInt(bound-1);
				ThreadLocalRandom.current().nextInt(bound);
				ThreadLocalRandom.current().nextInt(bound+1);
			}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msLowBound = new ArrayList[2]; 
		msLowBound[0] = new ArrayList<double[]>();
		msLowBound[1] = new ArrayList<double[]>();
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msHighBound = new ArrayList[2]; 
		msHighBound[0] = new ArrayList<double[]>();
		msHighBound[1] = new ArrayList<double[]>();
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msPow2Bound = new ArrayList[2]; 
		msPow2Bound[0] = new ArrayList<double[]>();
		msPow2Bound[1] = new ArrayList<double[]>();
		System.out.printf("%6s\t%10s\t%10s\t%10s\t%10s\n", "Bound", "TLR", "RI", "t", "dof");
		for (int bound = 1; bound <= 512; bound++) {
			double[][] ms = new double[2][TRIALS];
			for (int j = 0; j < TRIALS; j++) {
				long start = bean.getCurrentThreadCpuTime();
				for (int i = 0; i < N; i++) {
					ThreadLocalRandom.current().nextInt(bound);
				}
				long middle = bean.getCurrentThreadCpuTime();
				for (int i = 0; i < N; i++) {
					RandomIndexer.nextInt(bound);
				}
				long end = bean.getCurrentThreadCpuTime();
				ms[0][j] = (middle-start) / 1000000.0;
				ms[1][j] = (end-middle) / 1000000.0;
			}
			if ((bound & (bound-1)) != 0) {
				if (bound <= 256) {
					msLowBound[0].add(ms[0]);
					msLowBound[1].add(ms[1]);
				} else {
					msHighBound[0].add(ms[0]);
					msHighBound[1].add(ms[1]);
				}
			} else {
				msPow2Bound[0].add(ms[0]);
				msPow2Bound[1].add(ms[1]);
			}
			Number[] tTest = Statistics.tTestWelch(ms[0],ms[1]);
			double t = tTest[0].doubleValue();
			int dof = tTest[1].intValue();
			// times are converted to seconds during output
			System.out.printf("%6d\t%10.7f\t%10.7f\t%10.4f\t%10d\n", bound, Statistics.mean(ms[0])/1000, Statistics.mean(ms[1])/1000, t, dof);
		}
		double[] a0 = toArray(msLowBound[0]);
		double[] a1 = toArray(msLowBound[1]);
		Number[] tTest = Statistics.tTestWelch(a0,a1);
		double t = tTest[0].doubleValue();
		int dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "LOW", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
		a0 = toArray(msHighBound[0]);
		a1 = toArray(msHighBound[1]);
		tTest = Statistics.tTestWelch(a0,a1);
		t = tTest[0].doubleValue();
		dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "HIGH", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
		a0 = toArray(msPow2Bound[0]);
		a1 = toArray(msPow2Bound[1]);
		tTest = Statistics.tTestWelch(a0,a1);
		t = tTest[0].doubleValue();
		dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "POW2", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
	}
	
	private static void timeWithThreads(final int NUM_THREADS) {
		// Attempt to "warm-up" Java's JIT compiler.
		for (int bound = 2; bound <= 512; bound*= 2) {
			int j = 100000;
			Thread t1 = new RandomIndexerTimeThread(bound-1, j/NUM_THREADS);
			t1.start();
			Thread t2 = new RandomIndexerTimeThread(bound, j/NUM_THREADS);
			t2.start();
			Thread t3 = new RandomIndexerTimeThread(bound+1, j/NUM_THREADS);
			t3.start();
			try {
				t1.join();
				t2.join();
				t3.join();
			} catch (InterruptedException e) {
				System.out.println(e);
				System.exit(0);
			}
			t1 = new ThreadLocalRandomTimeThread(bound-1, j/NUM_THREADS);
			t1.start();
			t2 = new ThreadLocalRandomTimeThread(bound, j/NUM_THREADS);
			t2.start();
			t3 = new ThreadLocalRandomTimeThread(bound+1, j/NUM_THREADS);
			t3.start();
			try {
				t1.join();
				t2.join();
				t3.join();
			} catch (InterruptedException e) {
				System.out.println(e);
				System.exit(0);
			}
		}
		
		
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msLowBound = new ArrayList[2]; 
		msLowBound[0] = new ArrayList<double[]>();
		msLowBound[1] = new ArrayList<double[]>();
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msHighBound = new ArrayList[2]; 
		msHighBound[0] = new ArrayList<double[]>();
		msHighBound[1] = new ArrayList<double[]>();
		@SuppressWarnings("unchecked")
		ArrayList<double[]>[] msPow2Bound = new ArrayList[2]; 
		msPow2Bound[0] = new ArrayList<double[]>();
		msPow2Bound[1] = new ArrayList<double[]>();
		System.out.printf("%6s\t%10s\t%10s\t%10s\t%10s\n", "Bound", "TLR", "RI", "t", "dof");
		System.out.flush();
		for (int bound = 1; bound <= 512; bound++) {
			double[][] ms = new double[2][TRIALS];
			for (int j = 0; j < TRIALS; j++) {
				Thread[] t1 = new Thread[NUM_THREADS];
				Thread[] t2 = new Thread[NUM_THREADS];
				for (int x = 0; x < NUM_THREADS; x++) {
					t1[x] = new ThreadLocalRandomTimeThread(bound, N / NUM_THREADS);
					t2[x] = new RandomIndexerTimeThread(bound, N / NUM_THREADS);
				}
				long start = System.nanoTime();
				for (Thread s : t1) {
					s.start();
				}
				try {
					for (Thread s : t1) {
						s.join();
					}
				} catch (InterruptedException e) {
					System.out.println(e);
					System.exit(0);
				}
				long middle = System.nanoTime();
				for (Thread s : t2) {
					s.start();
				}
				try {
					for (Thread s : t2) {
						s.join();
					}
				} catch (InterruptedException e) {
					System.out.println(e);
					System.exit(0);
				}
				long end = System.nanoTime();
				ms[0][j] = (middle-start) / 1000000.0;
				ms[1][j] = (end-middle) / 1000000.0;
			}
			if ((bound & (bound-1)) != 0) {
				if (bound <= 256) {
					msLowBound[0].add(ms[0]);
					msLowBound[1].add(ms[1]);
				} else {
					msHighBound[0].add(ms[0]);
					msHighBound[1].add(ms[1]);
				}
			} else {
				msPow2Bound[0].add(ms[0]);
				msPow2Bound[1].add(ms[1]);
			}
			Number[] tTest = Statistics.tTestWelch(ms[0],ms[1]);
			double t = tTest[0].doubleValue();
			int dof = tTest[1].intValue();
			// times are converted to seconds during output
			System.out.printf("%6d\t%10.7f\t%10.7f\t%10.4f\t%10d\n", bound, Statistics.mean(ms[0])/1000, Statistics.mean(ms[1])/1000, t, dof);
			System.out.flush();
		}
		double[] a0 = toArray(msLowBound[0]);
		double[] a1 = toArray(msLowBound[1]);
		Number[] tTest = Statistics.tTestWelch(a0,a1);
		double t = tTest[0].doubleValue();
		int dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "LOW", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
		a0 = toArray(msHighBound[0]);
		a1 = toArray(msHighBound[1]);
		tTest = Statistics.tTestWelch(a0,a1);
		t = tTest[0].doubleValue();
		dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "HIGH", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
		a0 = toArray(msPow2Bound[0]);
		a1 = toArray(msPow2Bound[1]);
		tTest = Statistics.tTestWelch(a0,a1);
		t = tTest[0].doubleValue();
		dof = tTest[1].intValue();
		// times are converted to seconds during output
		System.out.printf("%6s\t%10.7f\t%10.7f\t%10.4f\t%10d\n", "POW2", Statistics.mean(a0)/1000, Statistics.mean(a1)/1000, t, dof);
	}
	
	private static final class RandomIndexerTimeThread extends Thread {
		private final int bound;
		private final int samples;
		public RandomIndexerTimeThread(int bound, int samples) {
			this.bound = bound;
			this.samples = samples;
		}
		public void run() {
			for (int i = 0; i < samples; i++) {
				RandomIndexer.nextInt(bound);
			}
		}
	}
	
	private static final class ThreadLocalRandomTimeThread extends Thread {
		private final int bound;
		private final int samples;
		public ThreadLocalRandomTimeThread(int bound, int samples) {
			this.bound = bound;
			this.samples = samples;
		}
		public void run() {
			for (int i = 0; i < samples; i++) {
				ThreadLocalRandom.current().nextInt(bound);
			}
		}
	}
	
	private static double[] toArray(ArrayList<double[]> from) {
		int n = 0;
		for (double[] e : from) n += e.length;
		double[] a = new double[n];
		int i = 0;
		for (double[] e : from) {
			for (int j = 0; j < e.length; j++) {
				a[i] = e[j];
				i++;
			}
		}
		return a;
	}
}