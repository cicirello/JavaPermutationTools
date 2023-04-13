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
package org.cicirello.permutations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/** JUnit tests for cycling a Permutation. */
public class PermutationCycleTests {

  @Test
  public void testOneZeroCycles() {
    // 1 cycle and 0-cycle
    int[] indexes1 = {0};
    int[] indexes0 = {};
    for (int i = 1; i <= 4; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      p2.cycle(indexes1);
      assertEquals(p, p2);
      assertEquals(p.hashCode(), p2.hashCode());
      p2.cycle(indexes0);
      assertEquals(p, p2);
      assertEquals(p.hashCode(), p2.hashCode());
    }
  }

  @Test
  public void testTwoCycles() {
    // 2-cycles
    int[] indexes = new int[2];
    for (int i = 1; i <= 5; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      indexes[0] = 0;
      indexes[1] = i - 1;
      assertEquals(p.hashCode(), p2.hashCode());
      p2.cycle(indexes);
      if (i > 1) assertNotEquals(p.hashCode(), p2.hashCode());
      assertEquals(p.get(0), p2.get(i - 1));
      assertEquals(p.get(i - 1), p2.get(0));
      for (int j = 1; j < i - 1; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
    }
    for (int i = 1; i <= 5; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      indexes[1] = 0;
      indexes[0] = i - 1;
      assertEquals(p.hashCode(), p2.hashCode());
      p2.cycle(indexes);
      if (i > 1) assertNotEquals(p.hashCode(), p2.hashCode());
      assertEquals(p.get(0), p2.get(i - 1));
      assertEquals(p.get(i - 1), p2.get(0));
      for (int j = 1; j < i - 1; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
    }
    for (int i = 4; i <= 6; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      indexes[0] = (i - 1) / 2;
      indexes[1] = indexes[0] + 1;
      assertEquals(p.hashCode(), p2.hashCode());
      p2.cycle(indexes);
      assertNotEquals(p.hashCode(), p2.hashCode());
      assertEquals(p.get(indexes[0]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      for (int j = 0; j < indexes[0]; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
      for (int j = indexes[1] + 1; j < i; j++) {
        assertEquals(p.get(j), p2.get(j));
      }
    }
  }

  @Test
  public void testThreeCycle() {
    // 3-cycles
    int[] indexes = new int[3];
    for (int i = 3; i <= 6; i++) {
      Permutation p = new Permutation(i);
      Permutation p2 = new Permutation(p);
      indexes[0] = 0;
      indexes[1] = i / 2;
      indexes[2] = i - 1;
      boolean[] inCycle = new boolean[i];
      inCycle[indexes[0]] = inCycle[indexes[1]] = inCycle[indexes[2]] = true;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
      p2 = new Permutation(p);
      indexes[0] = 0;
      indexes[1] = i - 1;
      indexes[2] = i / 2;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
      p2 = new Permutation(p);
      indexes[0] = i / 2;
      indexes[1] = 0;
      indexes[2] = i - 1;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
      p2 = new Permutation(p);
      indexes[0] = i / 2;
      indexes[1] = i - 1;
      indexes[2] = 0;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
      p2 = new Permutation(p);
      indexes[0] = i - 1;
      indexes[1] = 0;
      indexes[2] = i / 2;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
      p2 = new Permutation(p);
      indexes[0] = i - 1;
      indexes[1] = i / 2;
      indexes[2] = 0;
      p2.cycle(indexes);
      assertEquals(p.get(indexes[1]), p2.get(indexes[0]));
      assertEquals(p.get(indexes[2]), p2.get(indexes[1]));
      assertEquals(p.get(indexes[0]), p2.get(indexes[2]));
      for (int j = 0; j < i; j++) {
        // verify that non-cycle positions didn't change
        if (!inCycle[j]) {
          assertEquals(p.get(j), p2.get(j));
        }
      }
    }
  }
}
