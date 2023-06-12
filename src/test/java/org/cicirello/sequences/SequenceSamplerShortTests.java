/*
 * Copyright 2019-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.SplittableRandom;
import org.junit.jupiter.api.*;

/** JUnit tests for sequence samplers of shorts. */
public class SequenceSamplerShortTests {

  @Test
  public void testSampleComposite() {
    SequenceCompositeSampler r = new SequenceCompositeSampler(new SplittableRandom(42));
    validateSamples(r::nextSample);
    r = new SequenceCompositeSampler(42);
    validateSamples(r::nextSample);
    r = new SequenceCompositeSampler();
    validateSamples(r::nextSample);
  }

  @Test
  public void testSampleReservoir() {
    SequenceReservoirSampler r = new SequenceReservoirSampler(new SplittableRandom(42));
    validateSamples(r::nextSample);
    r = new SequenceReservoirSampler(42);
    validateSamples(r::nextSample);
    r = new SequenceReservoirSampler();
    validateSamples(r::nextSample);
  }

  @Test
  public void testSamplePool() {
    SequencePoolSampler r = new SequencePoolSampler(new SplittableRandom(42));
    validateSamples(r::nextSample);
    r = new SequencePoolSampler(42);
    validateSamples(r::nextSample);
    r = new SequencePoolSampler();
    validateSamples(r::nextSample);
  }

  @Test
  public void testSampleInsertion() {
    SequenceInsertionSampler r = new SequenceInsertionSampler(new SplittableRandom(42));
    validateSamples(r::nextSample);
    r = new SequenceInsertionSampler(42);
    validateSamples(r::nextSample);
    r = new SequenceInsertionSampler();
    validateSamples(r::nextSample);
  }

  @Test
  public void testSampleCompositeP() {
    SequenceCompositeSampler r = new SequenceCompositeSampler(new SplittableRandom(42));
    validateWithP(r::nextSample);
    r = new SequenceCompositeSampler(42);
    validateWithP(r::nextSample);
  }

  @Test
  public void testSampleReservoirP() {
    SequenceReservoirSampler r = new SequenceReservoirSampler(new SplittableRandom(42));
    validateWithP(r::nextSample);
    r = new SequenceReservoirSampler(42);
    validateWithP(r::nextSample);
  }

  @Test
  public void testSamplePoolP() {
    SequencePoolSampler r = new SequencePoolSampler(new SplittableRandom(42));
    validateWithP(r::nextSample);
    r = new SequencePoolSampler(42);
    validateWithP(r::nextSample);
  }

  @Test
  public void testSampleInsertionP() {
    SequenceInsertionSampler r = new SequenceInsertionSampler(new SplittableRandom(42));
    validateWithP(r::nextSample);
    r = new SequenceInsertionSampler(42);
    validateWithP(r::nextSample);
  }

  @Test
  public void testSample() {
    validateSamples(SequenceSampler::sample);
  }

  @Test
  public void testSampleShortP() {
    validateWithP(SequenceSampler::sample);
  }

  @Test
  public void testSampleCompositeStaticP() {
    SplittableRandom r = new SplittableRandom(42);
    validateWithP((source, p) -> SequenceCompositeSampler.sample(source, p, r));
  }

  @Test
  public void testSamplePoolStaticP() {
    SplittableRandom r = new SplittableRandom(42);
    validateWithP((source, p) -> SequencePoolSampler.sample(source, p, r));
  }

  @Test
  public void testSampleInsertionStaticP() {
    SplittableRandom r = new SplittableRandom(42);
    validateWithP((source, p) -> SequenceInsertionSampler.sample(source, p, r));
  }

  @Test
  public void testSampleReservoirStaticP() {
    SplittableRandom r = new SplittableRandom(42);
    validateWithP((source, p) -> SequenceReservoirSampler.sample(source, p, r));
  }

  private void validateWithP(PSampler sampler) {
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
        allDiff[i] = (short) (100 + i);
        mixedQuantities[i] = (short) (100 + j);
        allSame[i] = 100;
      }
      for (double p = 0.25; p <= 0.8; p += 0.25) {
        validateSample(allDiff, sampler.sample(allDiff, p));
        validateSample(mixedQuantities, sampler.sample(mixedQuantities, p));
        validateSample(allSame, sampler.sample(allSame, p));
      }
    }
  }

  @FunctionalInterface
  interface PSampler {
    short[] sample(short[] source, double p);
  }

  @FunctionalInterface
  interface Sampler {
    short[] sample(short[] source, int k, short[] target);
  }

  private void validateSample(short[] array, short[] sample, int k) {
    assertEquals(k, sample.length);
    validateSample(array, sample);
  }

  private void validateSample(short[] array, short[] sample) {
    HashMap<Short, Integer> counts = new HashMap<Short, Integer>();
    for (int i = 0; i < array.length; i++) {
      if (counts.containsKey(array[i])) {
        counts.put(array[i], counts.get(array[i]) + 1);
      } else {
        counts.put(array[i], 1);
      }
    }
    for (int i = 0; i < sample.length; i++) {
      assertTrue(counts.containsKey(sample[i]));
      if (counts.get(sample[i]) <= 1) {
        counts.remove(sample[i]);
      } else {
        counts.put(sample[i], counts.get(sample[i]) - 1);
      }
    }
  }

  private void validateSamples(Sampler sampler) {
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
        allDiff[i] = (short) (100 + i);
        mixedQuantities[i] = (short) (100 + j);
        allSame[i] = (short) (100);
      }
      for (int k = 1; k <= n; k++) {
        validateSample(allDiff, sampler.sample(allDiff, k, null), k);
        validateSample(mixedQuantities, sampler.sample(mixedQuantities, k, null), k);
        validateSample(allSame, sampler.sample(allSame, k, null), k);
      }
    }
    final short[] source = {2, 4, 6, 8};
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> sampler.sample(source, 5, null));
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
