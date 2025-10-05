/*
 * Copyright 2018-2025 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences.distance.internal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/** JUnit tests for KendallTauSequenceDistance. */
public class KendallTauSequenceDistanceInternalTests {

  @Test
  public void testKendallTauSequenceDistance_HashTableInt() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      IntHT ht = new IntHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    IntHT ht = new IntHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new IntHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new IntHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }

  @Test
  public void testKendallTauSequenceDistance_HashTableLong() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      LongHT ht = new LongHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    LongHT ht = new LongHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new LongHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new LongHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }

  @Test
  public void testKendallTauSequenceDistance_HashTableShort() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      ShortHT ht = new ShortHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    ShortHT ht = new ShortHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new ShortHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new ShortHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }

  @Test
  public void testKendallTauSequenceDistance_HashTableChar() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      CharHT ht = new CharHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    CharHT ht = new CharHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new CharHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new CharHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }

  @Test
  public void testKendallTauSequenceDistance_HashTableDouble() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      DoubleHT ht = new DoubleHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    DoubleHT ht = new DoubleHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new DoubleHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new DoubleHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }

  @Test
  public void testKendallTauSequenceDistance_HashTableFloat() {
    final int max = 0x40000000;
    int[] powerOfTwoCases = {1, 2, 4, 8, 16, 32};
    for (int n : powerOfTwoCases) {
      FloatHT ht = new FloatHT(n);
      assertEquals(n, ht.size());
      assertEquals(n - 1, ht.mask());
    }
    FloatHT ht = new FloatHT(3);
    assertEquals(4, ht.size());
    assertEquals(max, ht.adjustSize(max + 1));
    assertEquals(max, ht.adjustSize(max));
    for (int n = 5; n < 8; n++) {
      ht = new FloatHT(n);
      assertEquals(8, ht.size());
      assertEquals(7, ht.mask());
    }
    for (int n = 9; n < 16; n++) {
      ht = new FloatHT(n);
      assertEquals(16, ht.size());
      assertEquals(15, ht.mask());
    }
  }
}
