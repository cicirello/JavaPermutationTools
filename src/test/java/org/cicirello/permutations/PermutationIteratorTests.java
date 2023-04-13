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

import java.util.NoSuchElementException;
import org.junit.jupiter.api.*;

/** JUnit tests for the PermutationIterator. */
public class PermutationIteratorTests {

  @Test
  public void testPermutationIterator() {
    int fact = 1;
    for (int n = 1; n <= 5; n++) {
      Permutation p = new Permutation(n);
      boolean checkedFirst = false;
      fact *= n;
      boolean[] found = new boolean[fact];
      int count = 0;
      for (Permutation pPrime : p) {
        if (!checkedFirst) {
          assertEquals(p, pPrime);
          checkedFirst = true;
        }
        int permID = pPrime.toInteger();
        assertFalse(found[permID]);
        found[permID] = true;
        count++;
      }
      assertEquals(fact, count);
    }
    final PermutationIterator iter = new PermutationIterator(4);
    fact = 24;
    boolean[] found = new boolean[fact];
    int count = 0;
    while (iter.hasNext()) {
      Permutation p = iter.next();
      int permID = p.toInteger();
      assertFalse(found[permID]);
      found[permID] = true;
      count++;
    }
    assertEquals(fact, count);
    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> iter.next());
  }
}
