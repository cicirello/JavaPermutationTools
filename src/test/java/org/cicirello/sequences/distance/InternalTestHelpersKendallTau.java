/*
 * Copyright 2018-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.sequences.distance;

import static org.junit.jupiter.api.Assertions.*;

import org.cicirello.permutations.Permutation;
import org.junit.jupiter.api.*;

/** Internal test helpers for Kendall Tau Sequence distance tests. */
public class InternalTestHelpersKendallTau extends InternalTestHelpersSequenceDistance {

  final void helperForKendallTauBooleans(KendallTauSequenceDistance d) {
    boolean[] allFalse1 = new boolean[5];
    boolean[] allFalse2 = new boolean[5];
    boolean[] allTrue1 = {true, true, true, true, true};
    boolean[] allTrue2 = {true, true, true, true, true};
    assertEquals(0, d.distance(allFalse1, allFalse2));
    assertEquals(0, d.distance(allTrue1, allTrue2));
    boolean[] allButOne1 = {true, true, true, true, false};
    boolean[] allButOne2 = {false, true, true, true, true};
    assertEquals(4, d.distance(allButOne1, allButOne2));
    assertEquals(0, d.distance(allButOne1, allButOne1));
    assertEquals(0, d.distance(allButOne2, allButOne2));
  }

  final void helperForKendallTauCases(KendallTauSequenceDistance d) {
    // test first with simpler cases: all unique elements
    for (int n = 2; n <= 8; n++) {
      // maximal distance if all unique elements (i.e., a permutation) is reversed sequence
      int[] s1 = new int[n];
      int[] s2 = new int[n];
      int[] s3 = new int[n];
      for (int i = 0; i < n; i++) {
        s3[i] = s1[i] = s2[n - 1 - i] = i + 2;
        // deliberately didn't use 0 to n-1 for a bit of white box testing (i.e., testing the
        // indexing into arrays of queues)
      }
      s3[0] = s2[0];
      s3[n - 1] = s2[n - 1];
      // maximal distance
      int expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(s1, s2));
      assertEquals(expected, d.distance(s2, s1));
      // end points swapped
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(s1, s3));
      assertEquals(expected, d.distance(s3, s1));
      // test with negative values. rationale: default algorithm
      // relies on hash tables, so make sure negatives don't break hash table.
      int[] neg1 = new int[n];
      int[] neg2 = new int[n];
      int[] neg3 = new int[n];
      for (int i = 0; i < n; i++) {
        neg1[i] = -s1[i];
        neg2[i] = -s2[i];
        neg3[i] = -s3[i];
      }
      expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(neg1, neg2));
      assertEquals(expected, d.distance(neg2, neg1));
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(neg1, neg3));
      assertEquals(expected, d.distance(neg3, neg1));
      { // long
        long[] t1 = new long[n];
        long[] t2 = new long[n];
        long[] t3 = new long[n];
        for (int i = 0; i < n; i++) {
          t1[i] = s1[i];
          t2[i] = s2[i];
          t3[i] = s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        // test with negative values. rationale: default algorithm
        // relies on hash tables, so make sure negatives don't break hash table.
        for (int i = 0; i < n; i++) {
          t1[i] = -t1[i];
          t2[i] = -t2[i];
          t3[i] = -t3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
      }
      { // short
        short[] t1 = new short[n];
        short[] t2 = new short[n];
        short[] t3 = new short[n];
        for (int i = 0; i < n; i++) {
          t1[i] = (short) s1[i];
          t2[i] = (short) s2[i];
          t3[i] = (short) s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        // test with negative values. rationale: default algorithm
        // relies on hash tables, so make sure negatives don't break hash table.
        for (int i = 0; i < n; i++) {
          t1[i] = (short) (-1 * t1[i]);
          t2[i] = (short) (-1 * t2[i]);
          t3[i] = (short) (-1 * t3[i]);
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
      }
      { // byte
        byte[] t1 = new byte[n];
        byte[] t2 = new byte[n];
        byte[] t3 = new byte[n];
        for (int i = 0; i < n; i++) {
          t1[i] = (byte) s1[i];
          t2[i] = (byte) s2[i];
          t3[i] = (byte) s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        // test with negative values. rationale: default algorithm
        // relies on hash tables, so make sure negatives don't break hash table.
        for (int i = 0; i < n; i++) {
          t1[i] = (byte) (-1 * t1[i]);
          t2[i] = (byte) (-1 * t2[i]);
          t3[i] = (byte) (-1 * t3[i]);
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
      }
      { // char
        char[] t1 = new char[n];
        char[] t2 = new char[n];
        char[] t3 = new char[n];
        for (int i = 0; i < n; i++) {
          t1[i] = (char) s1[i];
          t2[i] = (char) s2[i];
          t3[i] = (char) s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        String u1 = new String(t1);
        String u2 = new String(t2);
        String u3 = new String(t3);
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(u1, u2));
        assertEquals(expected, d.distance(u2, u1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(u1, u3));
        assertEquals(expected, d.distance(u3, u1));
      }
      { // float
        float[] t1 = new float[n];
        float[] t2 = new float[n];
        float[] t3 = new float[n];
        for (int i = 0; i < n; i++) {
          t1[i] = s1[i];
          t2[i] = s2[i];
          t3[i] = s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        // test with negative values. rationale: default algorithm
        // relies on hash tables, so make sure negatives don't break hash table.
        for (int i = 0; i < n; i++) {
          t1[i] = -t1[i];
          t2[i] = -t2[i];
          t3[i] = -t3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
      }
      { // double
        double[] t1 = new double[n];
        double[] t2 = new double[n];
        double[] t3 = new double[n];
        for (int i = 0; i < n; i++) {
          t1[i] = s1[i];
          t2[i] = s2[i];
          t3[i] = s3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
        // test with negative values. rationale: default algorithm
        // relies on hash tables, so make sure negatives don't break hash table.
        for (int i = 0; i < n; i++) {
          t1[i] = -t1[i];
          t2[i] = -t2[i];
          t3[i] = -t3[i];
        }
        expected = n * (n - 1) / 2;
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        expected = 2 * n - 3;
        assertEquals(expected, d.distance(t1, t3));
        assertEquals(expected, d.distance(t3, t1));
      }
    }
    Permutation p = new Permutation(6);
    int[] s1 = new int[6];
    for (int i = 0; i < 6; i++) s1[i] = p.get(i);
    int[] s2 = new int[6];
    for (Permutation q : p) {
      for (int i = 0; i < 6; i++) s2[i] = q.get(i);
      int expected = naiveKendalTau(s1, s2);
      assertEquals(
          expected,
          d.distance(s1, s2),
          "checking consistence with naive implementation of unique element version");
      assertEquals(
          expected,
          d.distance(s2, s1),
          "checking consistence with naive implementation of unique element version");
    }
    { // long
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      long[] t1 = new long[5];
      for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
      long[] t2 = new long[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
      }
    }
    { // short
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      short[] t1 = new short[5];
      for (int i = 0; i < 5; i++) s1[i] = t1[i] = (short) r.get(i);
      short[] t2 = new short[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) s2[i] = t2[i] = (short) q.get(i);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
      }
    }
    { // byte
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      byte[] t1 = new byte[5];
      for (int i = 0; i < 5; i++) s1[i] = t1[i] = (byte) r.get(i);
      byte[] t2 = new byte[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) s2[i] = t2[i] = (byte) q.get(i);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
      }
    }
    { // char
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      char[] t1 = new char[5];
      for (int i = 0; i < 5; i++) s1[i] = t1[i] = (char) r.get(i);
      String u1 = new String(t1);
      char[] t2 = new char[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) s2[i] = t2[i] = (char) q.get(i);
        String u2 = new String(t2);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
        assertEquals(expected, d.distance(u1, u2));
        assertEquals(expected, d.distance(u2, u1));
      }
    }
    { // float
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      float[] t1 = new float[5];
      for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
      float[] t2 = new float[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
      }
    }
    { // double
      s1 = new int[5];
      s2 = new int[5];
      Permutation r = new Permutation(5);
      double[] t1 = new double[5];
      for (int i = 0; i < 5; i++) t1[i] = s1[i] = r.get(i);
      double[] t2 = new double[5];
      for (Permutation q : r) {
        for (int i = 0; i < 5; i++) t2[i] = s2[i] = q.get(i);
        int expected = naiveKendalTau(s1, s2);
        assertEquals(expected, d.distance(t1, t2));
        assertEquals(expected, d.distance(t2, t1));
      }
    }
    // Now test with duplicate elements
    String t1 = "abcdaabb";
    String t2 = "dcbababa";
    assertEquals(9, d.distance(t1, t2), "case where discordant pair counting fails");
    assertEquals(9, d.distance(t2, t1), "case where discordant pair counting fails");
    char[] c1 = t1.toCharArray();
    char[] c2 = t2.toCharArray();
    long[] L1 = new long[c1.length];
    long[] L2 = new long[c2.length];
    short[] sh1 = new short[c1.length];
    short[] sh2 = new short[c2.length];
    byte[] b1 = new byte[c1.length];
    byte[] b2 = new byte[c2.length];
    int[] i1 = new int[c1.length];
    int[] i2 = new int[c2.length];
    float[] f1 = new float[c1.length];
    float[] f2 = new float[c2.length];
    double[] d1 = new double[c1.length];
    double[] d2 = new double[c2.length];
    for (int i = 0; i < c1.length; i++) {
      L1[i] = i1[i] = c1[i];
      d1[i] = f1[i] = i1[i];
      sh1[i] = (short) c1[i];
      b1[i] = (byte) c1[i];
      L2[i] = i2[i] = c2[i];
      d2[i] = f2[i] = i2[i];
      sh2[i] = (short) c2[i];
      b2[i] = (byte) c2[i];
    }
    // case where discordant pair counting fails
    assertEquals(9, d.distance(c1, c2));
    assertEquals(9, d.distance(c2, c1));
    assertEquals(9, d.distance(L1, L2));
    assertEquals(9, d.distance(L2, L1));
    assertEquals(9, d.distance(sh1, sh2));
    assertEquals(9, d.distance(sh2, sh1));
    assertEquals(9, d.distance(b1, b2));
    assertEquals(9, d.distance(b2, b1));
    assertEquals(9, d.distance(i1, i2));
    assertEquals(9, d.distance(i2, i1));
    assertEquals(9, d.distance(f1, f2));
    assertEquals(9, d.distance(f2, f1));
    assertEquals(9, d.distance(d1, d2));
    assertEquals(9, d.distance(d2, d1));
    for (int n = 2; n < 8; n++) {
      for (int i = 1; i < n; i++) {
        boolean[] a1 = new boolean[n];
        boolean[] a2 = new boolean[n];
        for (int j = 0; j < i; j++) {
          a2[j] = a1[n - 1 - j] = true;
        }
        int expected = i * (n - i);
        assertEquals(expected, d.distance(a2, a1));
        assertEquals(expected, d.distance(a1, a2));
      }
    }
  }

  // simple naive O(n^2) version if elements are all unique
  final int naiveKendalTau(int[] s1, int[] s2) {
    int count = 0;
    int L1 = s1.length;

    int[] invS1 = new int[L1];
    int[] invS2 = new int[L1];
    for (int i = 0; i < L1; i++) {
      invS2[s2[i]] = invS1[s1[i]] = i;
    }

    for (int i = 0; i < L1 - 1; i++) {
      for (int j = i + 1; j < L1; j++) {
        if ((invS1[i] - invS1[j]) * (invS2[i] - invS2[j]) < 0) count++;
      }
    }

    return count;
  }
}
