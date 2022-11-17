/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.SplittableRandom;
import org.junit.jupiter.api.*;

/** JUnit tests for scrambling a Permutation. */
public class PermutationScrambleTests extends SharedTestHelpersPermutation {

  // Set to false to enable chi square tests for randomness of constructor and
  // scramble, specifically for the constructor and scramble that uses ThreadLocalRandom
  // since it is not seedable.  Tests passed repeatedly as of last update.
  // If that constructor or scramble is updated, then this flag should be set false to enable
  // tests (and reset true after passing).
  // Note: This doesn't disable those tests for the constructors/scramble methods
  // that use a source of randomness that is seedable.
  private static final boolean disableChiSquareTests = true;

  @Test
  public void testScrambleZero() {
    Permutation p1 = new Permutation(0);
    // confirm that scramble() don't throw exceptions
    p1.scramble();
    p1.scramble(new SplittableRandom());
    p1.scramble(true);
    p1.scramble(new SplittableRandom(), true);
  }

  @Test
  public void testScramble() {
    SplittableRandom r2 = new SplittableRandom();
    for (int i = 0; i < 8; i++) {
      Permutation p = new Permutation(i);
      for (int j = 0; j < 10; j++) {
        Permutation original = new Permutation(p);
        p.scramble();
        validatePermutation(p, i);
        original = new Permutation(p);
        p.scramble(r2);
        validatePermutation(p, i);
        p.scramble(false);
        validatePermutation(p, i);
        p.scramble(r2, false);
        validatePermutation(p, i);
        original = new Permutation(p);
        p.scramble(true);
        validatePermutation(p, i);
        if (i > 1) assertNotEquals(original, p);
        original = new Permutation(p);
        p.scramble(r2, true);
        validatePermutation(p, i);
        if (i > 1) assertNotEquals(original, p);
      }
    }
  }

  @Test
  public void testScrambleFromItoJ() {
    SplittableRandom r2 = new SplittableRandom();
    for (int n = 4; n < 8; n++) {
      Permutation p = new Permutation(n);
      Permutation original = new Permutation(p);
      p.scramble(1, n - 2);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      assertEquals(original.get(0), p.get(0));
      assertEquals(original.get(n - 1), p.get(n - 1));
      original = new Permutation(p);
      p.scramble(1, n - 2, r2);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      assertEquals(original.get(0), p.get(0));
      assertEquals(original.get(n - 1), p.get(n - 1));
      original = new Permutation(p);
      p.scramble(0, n - 1);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      original = new Permutation(p);
      p.scramble(0, n - 1, r2);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      original = new Permutation(p);
      p.scramble(1, 1, r2);
      assertEquals(original, p);
      original = new Permutation(p);
      p.scramble(1, 1);
      assertEquals(original, p);

      original = new Permutation(p);
      p.scramble(n - 2, 1);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      assertEquals(original.get(0), p.get(0));
      assertEquals(original.get(n - 1), p.get(n - 1));
      original = new Permutation(p);
      p.scramble(n - 2, 1, r2);
      validatePermutation(p, n);
      if (n > 1) assertNotEquals(original, p);
      assertEquals(original.get(0), p.get(0));
      assertEquals(original.get(n - 1), p.get(n - 1));
    }
  }

  @Test
  public void testUniformityOfScramble() {
    final int N = 12000;
    SplittableRandom r2 = new SplittableRandom(42);
    int tooHigh0 = 0;
    int tooHigh2 = 0;
    Permutation p0 = new Permutation(5);
    Permutation p2 = new Permutation(5, r2);
    for (int k = 0; k < 100; k++) {
      int[] counts0 = new int[120];
      int[] counts2 = new int[120];
      for (int i = 0; i < N; i++) {
        p0.scramble();
        p2.scramble(r2);
        int j0 = p0.toInteger();
        int j2 = p2.toInteger();
        counts0[j0]++;
        counts2[j2]++;
      }
      if (chiSquare(counts0) > 146.567) tooHigh0++;
      if (chiSquare(counts2) > 146.567) tooHigh2++;
    }
    if (!disableChiSquareTests) {
      assertTrue(tooHigh0 <= 10);
    }
    assertTrue(tooHigh2 <= 10);
  }
}
