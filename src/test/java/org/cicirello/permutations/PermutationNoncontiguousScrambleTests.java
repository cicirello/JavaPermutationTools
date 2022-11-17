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
public class PermutationNoncontiguousScrambleTests {

  @Test
  public void testNoncontiguousScrambleLengthOne() {
    SplittableRandom r2 = new SplittableRandom(42);
    // Verify does nothing if permutation length < 2.
    Permutation p = new Permutation(1);
    int[] indexes = {0};
    for (int i = 0; i < 5; i++) {
      p.scramble(indexes, r2);
      assertEquals(0, p.get(0));
      p.scramble(indexes);
      assertEquals(0, p.get(0));
    }
  }

  @Test
  public void testNoncontiguousScrambleTwoIndexes() {
    SplittableRandom r2 = new SplittableRandom(42);
    // 2 indexes
    int[] indexes = new int[2];
    for (int n = 2; n <= 5; n++) {
      indexes[0] = n - 1;
      indexes[1] = 0;
      boolean[] shouldChange = new boolean[n];
      shouldChange[indexes[0]] = shouldChange[indexes[1]] = true;
      Permutation p = new Permutation(n, 0);
      Permutation p0 = new Permutation(p);
      p.scramble(indexes, r2);
      assertEquals(0, p.get(n - 1));
      assertEquals(n - 1, p.get(0));
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        }
      }
      p = new Permutation(n, 0);
      p.scramble(indexes);
      assertEquals(0, p.get(n - 1));
      assertEquals(n - 1, p.get(0));
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        }
      }
    }
    for (int n = 4; n <= 6; n++) {
      indexes[0] = (n - 1) / 2;
      indexes[1] = indexes[0] + 1;
      boolean[] shouldChange = new boolean[n];
      shouldChange[indexes[0]] = shouldChange[indexes[1]] = true;
      Permutation p = new Permutation(n, 0);
      Permutation p0 = new Permutation(p);
      p.scramble(indexes, r2);
      assertEquals(p0.get(indexes[1]), p.get(indexes[0]));
      assertEquals(p0.get(indexes[0]), p.get(indexes[1]));
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        }
      }
      p = new Permutation(n, 0);
      p.scramble(indexes);
      assertEquals(p0.get(indexes[1]), p.get(indexes[0]));
      assertEquals(p0.get(indexes[0]), p.get(indexes[1]));
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        }
      }
    }
  }

  @Test
  public void testNoncontiguousScrambleThreeIndexes() {
    SplittableRandom r2 = new SplittableRandom(42);
    // 3 indexes
    int[] indexes = new int[3];
    for (int n = 3; n <= 8; n++) {
      indexes[0] = n - 1;
      indexes[1] = 0;
      indexes[2] = n / 2;
      boolean[] shouldChange = new boolean[n];
      shouldChange[indexes[0]] = shouldChange[indexes[1]] = shouldChange[indexes[2]] = true;
      Permutation p = new Permutation(n, 0);
      Permutation p0 = new Permutation(p);
      p.scramble(indexes, r2);
      assertTrue(
          p0.get(indexes[0]) != p.get(indexes[0])
              || p0.get(indexes[1]) != p.get(indexes[1])
              || p0.get(indexes[2]) != p.get(indexes[2]));
      int[] foundInScramble = new int[n];
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        } else {
          foundInScramble[p.get(i)]++;
          foundInScramble[p0.get(i)]--;
        }
      }
      for (int i = 0; i < n; i++) {
        assertEquals(0, foundInScramble[i]);
      }
      p = new Permutation(n, 0);
      p.scramble(indexes);
      assertTrue(
          p0.get(indexes[0]) != p.get(indexes[0])
              || p0.get(indexes[1]) != p.get(indexes[1])
              || p0.get(indexes[2]) != p.get(indexes[2]));
      for (int i = 0; i < n; i++) {
        if (!shouldChange[i]) {
          assertEquals(p0.get(i), p.get(i));
        } else {
          foundInScramble[p.get(i)]++;
          foundInScramble[p0.get(i)]--;
        }
      }
      for (int i = 0; i < n; i++) {
        assertEquals(0, foundInScramble[i]);
      }
    }
  }
}
