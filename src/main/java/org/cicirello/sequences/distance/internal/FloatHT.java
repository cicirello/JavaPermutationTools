/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2025 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

/** Internal class for hashtable of floats. */
final class FloatHT {

  private final Node[] table;
  private final int mask;

  FloatHT(int minSize) {
    minSize = adjustSize(minSize);
    mask = minSize - 1;
    table = new Node[minSize];
  }

  int adjustSize(int minSize) {
    final int MAX_SIZE = 0x40000000;
    if (minSize > MAX_SIZE) {
      return MAX_SIZE;
    }
    minSize = minSize - 1;
    minSize = minSize | (minSize >> 1);
    minSize = minSize | (minSize >> 2);
    minSize = minSize | (minSize >> 4);
    minSize = minSize | (minSize >> 8);
    minSize = minSize | (minSize >> 16);
    return minSize + 1;
  }

  int size() {
    return table.length;
  }

  int mask() {
    return mask;
  }

  int populate(float[] s1) {
    int current = -1;
    for (int i = 0; i < s1.length; i++) {
      if (!containsKey(s1[i])) {
        current++;
        put(s1[i], current);
      }
    }
    return current;
  }

  int index(float key) {
    int x = Float.floatToIntBits(key);
    return (x ^ (x >>> 16)) & mask;
  }

  boolean containsKey(float key) {
    for (Node current = table[index(key)]; current != null; current = current.next) {
      if (current.key == key) return true;
    }
    return false;
  }

  int get(float key) {
    for (Node current = table[index(key)]; current != null; current = current.next) {
      if (current.key == key) return current.value;
    }
    // NOTE: our internal usage never puts a negative as a value
    return -1;
  }

  void put(float key, int value) {
    // warning: assumes key is not already in hash table (only used internally so ok).
    int i = index(key);
    table[i] = new Node(key, value, table[i]);
  }

  static final class Node {
    float key;
    int value;
    Node next;

    Node(float key, int value, Node next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }
}
