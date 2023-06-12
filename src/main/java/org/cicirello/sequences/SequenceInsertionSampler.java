/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import java.util.random.RandomGenerator;
import org.cicirello.math.rand.EnhancedRandomGenerator;
import org.cicirello.math.rand.RandomSampler;
import org.cicirello.math.rand.RandomVariates;
import org.cicirello.util.ArrayMinimumLengthEnforcer;

/**
 * SequenceInsertionSampler generates random samples of array elements, without replacement.
 *
 * <p>The methods of this class implement the insertion sampling algorithm described in:
 *
 * <p>Vincent A. Cicirello. 2022. <a
 * href="https://www.cicirello.org/publications/applsci-12-05506.pdf">Cycle Mutation: Evolving
 * Permutations via Cycle Induction</a>, <i>Applied Sciences</i>, 12(11), Article 5506 (June 2022).
 * doi:<a href="https://doi.org/10.3390/app12115506">10.3390/app12115506</a>
 *
 * <p>The runtime of the sample methods is O(k<sup>2</sup>) and generates O(k) random numbers. Thus,
 * it is a better choice than both {@link SequenceReservoirSampler} and {@link SequencePoolSampler}
 * when k<sup>2</sup> &lt; n. Just like {@link SequenceReservoirSampler}, the
 * SequenceInsertionSampler only requires O(1) extra space, while {@link SequencePoolSampler}
 * requires O(n) extra space.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public final class SequenceInsertionSampler implements SequenceSampler {

  private final EnhancedRandomGenerator r;

  /**
   * Constructs a sampler wrapping a RandomGenerator used as the source of randomness.
   *
   * @param r The source of randomness.
   */
  public SequenceInsertionSampler(RandomGenerator r) {
    this.r = new EnhancedRandomGenerator(r);
  }

  /**
   * Constructs a sampler seeding the internal random number generator as specified.
   *
   * @param seed The seed for the random number generator
   */
  public SequenceInsertionSampler(long seed) {
    this.r = new EnhancedRandomGenerator(seed);
  }

  /** Constructs a sampler with a default source of randomness. */
  public SequenceInsertionSampler() {
    this.r = new EnhancedRandomGenerator();
  }

  /**
   * package use only: creates an instance wrapping an existing EnhancedRandomGenerator
   *
   * @param r the source of randomness
   * @param reserved currently does nothing, and only serves purpose of differentiating between this
   *     internal constructor and the public constructor
   */
  SequenceInsertionSampler(EnhancedRandomGenerator r, boolean reserved) {
    this.r = r;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public int[] nextSample(int[] source, int k, int[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = r.sampleInsertion(source.length, k, target);
    for (int i = 0; i < k; i++) {
      target[i] = source[target[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public long[] nextSample(long[] source, int k, long[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public short[] nextSample(short[] source, int k, short[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public byte[] nextSample(byte[] source, int k, byte[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public double[] nextSample(double[] source, int k, double[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public float[] nextSample(float[] source, int k, float[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public char[] nextSample(char[] source, int k, char[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length()
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public char[] nextSample(String source, int k, char[] target) {
    return nextSample(source.toCharArray(), k, target);
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  @Override
  public <T> T[] nextSample(T[] source, int k, T[] target) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = SequenceSamplerUtils.allocateIfNecessary(source, k, target);
    int[] indexes = r.sampleInsertion(source.length, k, null);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  @Override
  public int[] nextSample(int[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public long[] nextSample(long[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public short[] nextSample(short[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public byte[] nextSample(byte[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public double[] nextSample(double[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public float[] nextSample(float[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public char[] nextSample(char[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  @Override
  public char[] nextSample(String source, double p) {
    return nextSample(source, r.nextBinomial(source.length(), p), null);
  }

  @Override
  public <T> T[] nextSample(T[] source, double p) {
    return nextSample(source, r.nextBinomial(source.length, p), null);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static int[] sample(int[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static long[] sample(long[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static short[] sample(short[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static byte[] sample(byte[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static double[] sample(double[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static float[] sample(float[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static char[] sample(char[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source String with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The String from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length() * p.
   * @param r The source of randomness.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static char[] sample(String source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length(), p, r), null, r);
  }

  /**
   * Generates a random sample, without replacement, from a given source array with a specified
   * probability of an element's inclusion in the sample.
   *
   * @param source The array from which to sample.
   * @param p The probability that element is included in the sample. The expected sample size is
   *     source.length * p.
   * @param r The source of randomness.
   * @param <T> The type of array elements.
   * @return An array containing the sample, whose sample size is simply the length of the array.
   */
  public static <T> T[] sample(T[] source, double p, RandomGenerator r) {
    return sample(source, RandomVariates.nextBinomial(source.length, p, r), null, r);
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static int[] sample(int[] source, int k, int[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = RandomSampler.sampleInsertion(source.length, k, target, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[target[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static long[] sample(long[] source, int k, long[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static short[] sample(short[] source, int k, short[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static byte[] sample(byte[] source, int k, byte[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static char[] sample(char[] source, int k, char[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k chars, without replacement, from a given source String. All n
   * choose k combinations are equally likely, where n is the length of the source String.
   *
   * @param source The source to sample.
   * @param k The number of random samples (must be no greater than source.length()).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length()
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static char[] sample(String source, int k, char[] target, RandomGenerator r) {
    return sample(source.toCharArray(), k, target, r);
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static double[] sample(double[] source, int k, double[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static float[] sample(float[] source, int k, float[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = ArrayMinimumLengthEnforcer.enforce(target, k);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }

  /**
   * Generates a random sample of k elements, without replacement, from a given source array. All n
   * choose k combinations are equally likely, where n is the length of the source array.
   *
   * @param source The source array to sample.
   * @param k The number of random samples (must be no greater than source.length).
   * @param target An array to hold the result. If target is null or target.length is less than k,
   *     then this method will construct a new array for the result.
   * @param r The source of randomness.
   * @param <T> The type of array elements.
   * @return An array containing the random sample.
   * @throws IllegalArgumentException if k &gt; source.length
   * @throws NegativeArraySizeException if k &lt; 0
   */
  public static <T> T[] sample(T[] source, int k, T[] target, RandomGenerator r) {
    SequenceSamplerUtils.validateK(k, source.length);
    target = SequenceSamplerUtils.allocateIfNecessary(source, k, target);
    int[] indexes = RandomSampler.sampleInsertion(source.length, k, null, r);
    for (int i = 0; i < k; i++) {
      target[i] = source[indexes[i]];
    }
    return target;
  }
}
