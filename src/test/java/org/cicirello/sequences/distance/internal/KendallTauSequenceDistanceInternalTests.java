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
  public void testKendallTauSequenceDistance_HashTableBaseClass() {
    class TestHT extends RelabelByHashing.BaseHT {
      TestHT(int min) {
        super(32, min);
      }
    }
    for (int n = 1; n <= 32; n *= 2) {
      TestHT ht = new TestHT(n);
      assertEquals(n, ht.minSize);
      assertEquals(n - 1, ht.mask);
    }
    TestHT ht = new TestHT(3);
    assertEquals(4, ht.minSize);
    for (int n = 5; n < 8; n++) {
      ht = new TestHT(n);
      assertEquals(8, ht.minSize);
      assertEquals(7, ht.mask);
    }
    for (int n = 9; n < 16; n++) {
      ht = new TestHT(n);
      assertEquals(16, ht.minSize);
      assertEquals(15, ht.mask);
    }
    for (int n = 17; n <= 64; n++) {
      ht = new TestHT(n);
      assertEquals(32, ht.minSize);
      assertEquals(31, ht.mask);
    }
  }
}
