/*
 * Copyright 2018-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 */
package org.cicirello.sequences.distance;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class InternalTestHelpersSequenceDistance {
	
	final void identicalSequences(SequenceDistanceMeasurer d) {
		for (int n = 0; n <= 10; n++) {
			int[] a1 = new int[n];
			long[] a2 = new long[n];
			short[] a3 = new short[n];
			byte[] a4 = new byte[n];
			char[] a5 = new char[n];
			float[] a6 = new float[n];
			double[] a7 = new double[n];
			boolean[] a8 = new boolean[n];
			for (int i = 0; i < n; i++) {
				a1[i] = ThreadLocalRandom.current().nextInt(100);
				a2[i] = ThreadLocalRandom.current().nextInt(100);
				a3[i] = (short)ThreadLocalRandom.current().nextInt(100);
				a4[i] = (byte)ThreadLocalRandom.current().nextInt(100);
				a5[i] = (char)ThreadLocalRandom.current().nextInt(100);
				a6[i] = ThreadLocalRandom.current().nextInt(100);
				a7[i] = ThreadLocalRandom.current().nextInt(100);
				a8[i] = ThreadLocalRandom.current().nextBoolean();
			}
			String a9 = new String(a5);
			// distance of a sequence to itself should be 0
			assertEquals(0, d.distance(a1, a1.clone()));
			assertEquals(0, d.distance(a2, a2.clone()));
			assertEquals(0, d.distance(a3, a3.clone()));
			assertEquals(0, d.distance(a4, a4.clone()));
			assertEquals(0, d.distance(a5, a5.clone()));
			assertEquals(0, d.distance(a6, a6.clone()));
			assertEquals(0, d.distance(a7, a7.clone()));
			assertEquals(0, d.distance(a8, a8.clone()));
			assertEquals(0, d.distance(a9, new String(a9)));
		}
	}
	
	final ArrayList<String> toList(String[] a) {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : a) list.add(s);
		return list;
	}
	
	final ArrayList<NonComparable> toList(NonComparable[] a) {
		ArrayList<NonComparable> list = new ArrayList<NonComparable>();
		for (NonComparable s : a) list.add(s);
		return list;
	}
	
	final static class NonComparable {
		private int id;
		public NonComparable(int me) {
			id = me;
		}
		public boolean equals(Object other) {
			return ((NonComparable)other).id == id;
		}
		public int hashCode() {
			return id;
		}
	}
}
