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

import static org.junit.jupiter.api.Assertions.*;

import org.cicirello.math.stats.Statistics;
import org.junit.jupiter.api.*;

/** JUnit statistical tests for the SequenceSampler class. */
public class SequenceSamplerStatisticalTests {

  // Set this is true to run the t-test.  Due to randomness,
  // the t statistics won't be exactly the same each time.
  // An occasional failure is expected if this test is run a large enough times.
  // E.g., Test case is implemented with an alpha = 0.01, so 1 in 100 failures
  // is OK.  And it runs several tests for different length arrays, etc.
  // So set this to true only if you change the code, and then set back to false
  // after passing.
  private static final boolean DISABLE_T_TEST = true;

  private static final int TTEST_NUM_SAMPLES = 100000; // 101;

  // Two-sided test, so alpha=0.01 level needs tCrit_{1 - alpha/2} = tCrit_{0.995}
  // This first one is 100 degrees of freedom (use for TTEST_NUM_SAMPLES = 101).
  // The second one is for infinite number of samples (use for very large TTEST_NUM_SAMPLES).
  private static final double CRITICAL_VALUE_995 = 2.626;
  private static final double CRITICAL_VALUE_995_INF = 2.576;

  @Test
  public void testAbstractbaseClass() {
    class TestExtension extends AbstractSequenceSampler {}
    assertNotNull(new TestExtension());
  }

  @Test
  public void testSamplePTTest() {
    if (DISABLE_T_TEST) {
      return;
    }
    final double sqrt = Math.sqrt(TTEST_NUM_SAMPLES);
    for (int n = 1; n <= 10; n++) {
      int[] array = new int[n];
      for (double p = 0.25; p <= 0.8; p += 0.25) {
        int[] L = new int[TTEST_NUM_SAMPLES];
        for (int i = 0; i < TTEST_NUM_SAMPLES; i++) {
          L[i] = SequenceSampler.sample(array, p).length;
        }
        double stDev = Math.sqrt(Statistics.varianceSample(L));
        double t = Math.abs(sqrt * (Statistics.mean(L) - n * p) / stDev);
        double crit = TTEST_NUM_SAMPLES == 101 ? CRITICAL_VALUE_995 : CRITICAL_VALUE_995_INF;
        System.out.println("t=" + t);
        assertTrue(t < crit, "t test for sample size, t=" + t);
      }
    }
  }
}
