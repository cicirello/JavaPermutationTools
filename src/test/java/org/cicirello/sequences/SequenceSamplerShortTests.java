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

/**
 * Internal helpers for tests of sequence samplers.
 */
public class SequenceSamplerShortTests extends SharedTestSamplers {
	
	@Test
	public void testSampleReservoir() {
		validateSamples(SequenceSampler::sampleReservoir);
	}
	
	@Test
	public void testSamplePool() {
		validateSamples(SequenceSampler::samplePool);
	}
	
	@Test
	public void testSampleInsertion() {
		validateSamples(SequenceSampler::sampleInsertion);
	}
	
	@Test
	public void testSample() {
		validateSamples(SequenceSampler::sample);
	}
	
	@Test
	public void testSampleShortP() {
		for (int n = 1; n <= 10; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = 100;
			}
			for (double p = 0.25; p <= 0.8; p += 0.25) {
				validateSample(allDiff, SequenceSampler.sample(allDiff, p));
				validateSample(mixedQuantities, SequenceSampler.sample(mixedQuantities, p));
				validateSample(allSame, SequenceSampler.sample(allSame, p));
			}
		}
	}
	
	@FunctionalInterface
	interface Sampler {
		short[] sample(short[] source, int k, short[] target);
	}
	
	final void validateSamples(Sampler sampler) {
		for (int n = 1; n < 9; n++) {
			short[] allDiff = new short[n];
			short[] mixedQuantities = new short[n];
			short[] allSame = new short[n];
			int j = 0;
			int prev = 1;
			int next = 1;
			for (int i = 0; i < n; i++) {
				if (i == next) {
					j++;
					prev++;
					next = i + prev;
				}
				allDiff[i] = (short)(100 + i);
				mixedQuantities[i] = (short)(100 + j);
				allSame[i] = (short)(100);
			}
			for (int k = 1; k <= n; k++) {
				validateSample(allDiff, sampler.sample(allDiff, k, null), k);
				validateSample(mixedQuantities, sampler.sample(mixedQuantities, k, null), k);
				validateSample(allSame, sampler.sample(allSame, k, null), k);
			}
		}
		final short[] source = { 2, 4, 6, 8 };
		IllegalArgumentException thrown = assertThrows( 
			IllegalArgumentException.class,
			() -> sampler.sample(source, 5, null)
		);
		short[] target = new short[4];
		short[] result = sampler.sample(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target == result);
		target = new short[3];
		result = sampler.sample(source, 4, target);
		assertEquals(4, result.length);
		assertTrue(target != result);
	}
}
