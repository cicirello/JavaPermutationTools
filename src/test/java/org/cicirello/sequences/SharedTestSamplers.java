/*
 * Copyright 2019-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

/**
 * Internal helpers for tests of sequence samplers.
 */
public class SharedTestSamplers {
	
	// validate (array, sample, k) methods
	
	final void validateSample(int[] array, int[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(long[] array, long[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(short[] array, short[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(byte[] array, byte[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(char[] array, char[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(double[] array, double[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(float[] array, float[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	final void validateSample(String[] array, String[] sample, int k) {
		assertEquals(k, sample.length);
		validateSample(array, sample);
	}
	
	// validate(array, sample) methods
	
	final void validateSample(int[] array, int[] sample) {
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(long[] array, long[] sample) {
		HashMap<Long, Integer> counts = new HashMap<Long, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(short[] array, short[] sample) {
		HashMap<Short, Integer> counts = new HashMap<Short, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(byte[] array, byte[] sample) {
		HashMap<Byte, Integer> counts = new HashMap<Byte, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(char[] array, char[] sample) {
		HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(double[] array, double[] sample) {
		HashMap<Double, Integer> counts = new HashMap<Double, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(float[] array, float[] sample) {
		HashMap<Float, Integer> counts = new HashMap<Float, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
	
	final void validateSample(Object[] array, Object[] sample) {
		HashMap<Object, Integer> counts = new HashMap<Object, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (counts.containsKey(array[i])) {
				counts.put(array[i], counts.get(array[i])+1);
			} else {
				counts.put(array[i], 1);
			}
		}
		for (int i = 0; i < sample.length; i++) {
			assertTrue(counts.containsKey(sample[i]));
			if (counts.get(sample[i]) <= 1) {
				counts.remove(sample[i]);
			} else {
				counts.put(sample[i], counts.get(sample[i])-1);
			}
		}
	}
}
