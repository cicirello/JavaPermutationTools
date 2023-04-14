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

import java.util.SplittableRandom;
import org.junit.jupiter.api.*;

/** JUnit tests for the caching and cache invalidation behavior of the hashCode() method. */
public class PermutationHashCodeCacheTests {

  @Test
  public void testCycle() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.cycle(new int[] {1, 3});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.cycle(new int[] {1, 3, 5});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testInvert() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.invert();
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testRemoveAndInsert() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(1, 4);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(4, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(1, 2);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(2, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testRemoveAndInsertSize() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(1, 1, 4);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(4, 1, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(1, 1, 2);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(2, 1, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(1, 2, 5);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(5, 2, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.removeAndInsert(2, 2, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testReverse() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.reverse();
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testReverseParams() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.reverse(1, 2);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.reverse(2, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.reverse(1, 5);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.reverse(5, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testRotate() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.rotate(1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.rotate(-1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.rotate(3);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testScramble() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble();
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(false);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(true);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    SplittableRandom rand = new SplittableRandom(73);

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(rand, false);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(rand, true);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testScrambleTwoIndexes() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(1, 2);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(2, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(1, 5);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(5, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    SplittableRandom rand = new SplittableRandom(73);

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(1, 2, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(2, 1, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(1, 5, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(5, 1, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testScrambleIndexes() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {1, 5});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {5, 1});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {1, 5, 3, 7});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    SplittableRandom rand = new SplittableRandom(73);

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {1, 5}, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {5, 1}, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.scramble(new int[] {1, 5, 3, 7}, rand);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testSet() {
    Permutation p = new Permutation(5, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.set(new int[] {0, 1, 2, 3, 4});
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testSwap() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swap(1, 5);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swap(5, 1);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testSwapBlocks() {
    Permutation p = new Permutation(10, new SplittableRandom(42));
    Permutation original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 1, 2, 2);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 1, 4, 4);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 1, 2, 4);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 4, 4);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 1, 4, 6);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 7, 7);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 4, 6);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 6, 8);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 4, 7);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 4, 5, 7);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 3, 6, 9);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());

    original = new Permutation(p);
    assertEquals(original, p);
    assertEquals(original.hashCode(), p.hashCode());
    assertEquals(original.hashCode(), p.hashCode());
    p.swapBlocks(1, 4, 7, 9);
    assertNotEquals(original, p);
    assertNotEquals(original.hashCode(), p.hashCode());
    assertNotEquals(original.hashCode(), p.hashCode());
  }

  @Test
  public void testApplyBinary() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    Permutation p2 = new Permutation(5, new SplittableRandom(73));
    Permutation original2 = new Permutation(p2);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original2, p2);
    assertEquals(original2.hashCode(), p2.hashCode());
    assertEquals(original2.hashCode(), p2.hashCode());
    p1.apply(
        (raw1, raw2) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = raw2[i] = i;
          }
        },
        p2);
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original2, p2);
    assertNotEquals(original2.hashCode(), p2.hashCode());
    assertNotEquals(original2.hashCode(), p2.hashCode());
  }

  @Test
  public void testApplyBinaryValidate() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    Permutation p2 = new Permutation(5, new SplittableRandom(73));
    Permutation original2 = new Permutation(p2);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original2, p2);
    assertEquals(original2.hashCode(), p2.hashCode());
    assertEquals(original2.hashCode(), p2.hashCode());
    p1.applyThenValidate(
        (raw1, raw2) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = raw2[i] = i;
          }
        },
        p2);
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original2, p2);
    assertNotEquals(original2.hashCode(), p2.hashCode());
    assertNotEquals(original2.hashCode(), p2.hashCode());
  }

  @Test
  public void testApplyBinaryFull() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    Permutation p2 = new Permutation(5, new SplittableRandom(73));
    Permutation original2 = new Permutation(p2);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original2, p2);
    assertEquals(original2.hashCode(), p2.hashCode());
    assertEquals(original2.hashCode(), p2.hashCode());
    p1.apply(
        (raw1, raw2, o1, o2) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = raw2[i] = i;
          }
        },
        p2);
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original2, p2);
    assertNotEquals(original2.hashCode(), p2.hashCode());
    assertNotEquals(original2.hashCode(), p2.hashCode());
  }

  @Test
  public void testApplyBinaryFullValidate() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    Permutation p2 = new Permutation(5, new SplittableRandom(73));
    Permutation original2 = new Permutation(p2);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original2, p2);
    assertEquals(original2.hashCode(), p2.hashCode());
    assertEquals(original2.hashCode(), p2.hashCode());
    p1.applyThenValidate(
        (raw1, raw2, o1, o2) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = raw2[i] = i;
          }
        },
        p2);
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original2, p2);
    assertNotEquals(original2.hashCode(), p2.hashCode());
    assertNotEquals(original2.hashCode(), p2.hashCode());
  }

  @Test
  public void testApplyUnary() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    p1.apply(
        raw1 -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = i;
          }
        });
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
  }

  @Test
  public void testApplyUnaryValidate() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    p1.applyThenValidate(
        raw1 -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = i;
          }
        });
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
  }

  @Test
  public void testApplyUnaryFull() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    p1.apply(
        (int[] raw1, Permutation o1) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = i;
          }
        });
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
  }

  @Test
  public void testApplyUnaryFullValidate() {
    Permutation p1 = new Permutation(5, new SplittableRandom(42));
    Permutation original1 = new Permutation(p1);
    assertEquals(original1, p1);
    assertEquals(original1.hashCode(), p1.hashCode());
    assertEquals(original1.hashCode(), p1.hashCode());
    p1.applyThenValidate(
        (int[] raw1, Permutation o1) -> {
          for (int i = 0; i < raw1.length; i++) {
            raw1[i] = i;
          }
        });
    assertNotEquals(original1, p1);
    assertNotEquals(original1.hashCode(), p1.hashCode());
    assertNotEquals(original1.hashCode(), p1.hashCode());
  }
}
