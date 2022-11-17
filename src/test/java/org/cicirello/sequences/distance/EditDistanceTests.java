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

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.*;

/** JUnit tests for EditDistance. */
public class EditDistanceTests extends InternalTestHelpersSequenceDistance {

  @Test
  public void testEditDistanceExceptions() {
    final EditDistance d = new EditDistance(1.5, 1.5, 1.5);
    UnsupportedOperationException thrown =
        assertThrows(UnsupportedOperationException.class, () -> d.distance(new int[1], new int[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new long[1], new long[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new short[1], new short[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new byte[1], new byte[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new double[1], new double[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new float[1], new float[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new boolean[1], new boolean[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new char[1], new char[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class, () -> d.distance(new String[1], new String[1]));
    thrown =
        assertThrows(
            UnsupportedOperationException.class,
            () -> d.distance(new ArrayList<String>(), new ArrayList<String>()));
    thrown = assertThrows(UnsupportedOperationException.class, () -> d.distance("a", "a"));

    IllegalArgumentException illegal =
        assertThrows(IllegalArgumentException.class, () -> new EditDistance(-1, 0, 0));
    illegal = assertThrows(IllegalArgumentException.class, () -> new EditDistance(0, -1, 0));
    illegal = assertThrows(IllegalArgumentException.class, () -> new EditDistance(0, 0, -1));
    illegal = assertThrows(IllegalArgumentException.class, () -> new EditDistance(-0.01, 0, 0));
    illegal = assertThrows(IllegalArgumentException.class, () -> new EditDistance(0, -0.01, 0));
    illegal = assertThrows(IllegalArgumentException.class, () -> new EditDistance(0, 0, -0.01));
  }

  @Test
  public void testEditObjectSequences() {
    String[] s1 = {
      "a", "b", "a", "c", "a", "d", "a", "e", "a", "f", "a", "h", "a", "i", "a", "j", "a"
    };
    String[] s2 = {"k", "a", "m", "n", "o", "p", "a", "l", "a", "a", "q", "a", "a", "a", "a"};
    EditDistance d = new EditDistance(1, 1, 2);
    assertEquals(16, d.distance(s1, s2));
    assertEquals(16.0, d.distancef(s1, s2));
    assertEquals(16, d.distance(toList(s1), toList(s2)));
    assertEquals(16.0, d.distancef(toList(s1), toList(s2)));
    d = new EditDistance(3, 3, 6);
    assertEquals(48, d.distance(s1, s2));
    assertEquals(48.0, d.distancef(s1, s2));
    assertEquals(48, d.distance(toList(s1), toList(s2)));
    assertEquals(48.0, d.distancef(toList(s1), toList(s2)));

    String[] s3 = {
      "a", "a", "a", "a", "a", "b", "c", "d", "e", "f", "a", "a", "a", "a", "a", "b", "c", "d", "e",
      "f", "a", "a", "a", "a", "a"
    };
    String[] s4 = {
      "b", "b", "b", "b", "b", "b", "c", "d", "e", "f", "b", "b", "b", "b", "b", "b", "c", "d", "e",
      "f", "b", "b", "b", "b", "b"
    };
    d = new EditDistance(2, 2, 3);
    assertEquals(45, d.distance(s3, s4));
    assertEquals(45.0, d.distancef(s3, s4));
    assertEquals(45, d.distance(toList(s3), toList(s4)));
    assertEquals(45.0, d.distancef(toList(s3), toList(s4)));

    d = new EditDistance(3, 3, 6);
    NonComparable[] c1 = new NonComparable[17];
    for (int i = 0; i < 17; i++) {
      c1[i] = (i % 2) == 0 ? new NonComparable(0) : new NonComparable(i);
    }
    NonComparable[] c2 = new NonComparable[16];
    for (int i = 0; i < 16; i++) {
      c2[i] = (i % 2 == 0) ? new NonComparable(100 + i) : new NonComparable(0);
    }
    assertEquals(51, d.distance(c1, c2));
    assertEquals(51, d.distance(toList(c1), toList(c2)));
  }

  @Test
  public void testIdentical() {
    EditDistance d = new EditDistance(1, 2, 10);
    identicalSequences(d);
    identicalSequencesD(d);
    d = new EditDistance(1.0, 2.0, 10.0);
    identicalSequences(d);
    identicalSequencesD(d);
    d = new EditDistance(2, 1, 10);
    identicalSequences(d);
    identicalSequencesD(d);
    d = new EditDistance(3, 2, 1);
    identicalSequences(d);
    identicalSequencesD(d);
    d = new EditDistance(4, 5, 2);
    identicalSequences(d);
    identicalSequencesD(d);
    d = new EditDistance(4.2, 5.2, 2.2);
    identicalSequencesD(d);
    d = new EditDistance(3.2, 2.2, 1.2);
    identicalSequencesD(d);
    d = new EditDistance(2.2, 1.2, 10.2);
    identicalSequencesD(d);
    d = new EditDistance(1.2, 2.2, 10.2);
    identicalSequencesD(d);
  }

  @Test
  public void testEditDistance() {
    int cost_i = 1;
    int cost_d = 1;
    // lcs of next pair is 8... lengths 17 and 15
    String s1 = "abacadaeafahaiaja";
    String s2 = "kamnopalaaqaaaa";
    EditDistance d = new EditDistance(cost_i, cost_d, cost_i + cost_d);
    assertEquals(16, d.distance(s1, s2));
    assertEquals(16.0, d.distancef(s1, s2));
    char[] a1 = s1.toCharArray();
    char[] a2 = s2.toCharArray();
    assertEquals(16, d.distance(a1, a2));
    assertEquals(16.0, d.distancef(a1, a2));
    cost_i = 3;
    cost_d = 3;
    EditDistance d2 = new EditDistance(cost_i, cost_d, cost_i + cost_d);
    assertEquals(48, d2.distance(s1, s2));
    assertEquals(48.0, d2.distancef(s1, s2));
    {
      boolean[] b1 = {true, false, true, false, true, false, true, true, true, true, true, true};
      boolean[] b2 = {false, false, true, true, true, false, false, false};
      // lcs is 5... lengths are 12 and 8
      assertEquals(10, d.distance(b1, b2));
      assertEquals(10.0, d.distancef(b1, b2));
      assertEquals(30, d2.distance(b1, b2));
      assertEquals(30.0, d2.distancef(b1, b2));
    }
    { // int
      int[] b1 = new int[a1.length];
      int[] b2 = new int[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }
    { // long
      long[] b1 = new long[a1.length];
      long[] b2 = new long[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }
    { // short
      short[] b1 = new short[a1.length];
      short[] b2 = new short[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = (short) a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = (short) a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }
    { // byte
      byte[] b1 = new byte[a1.length];
      byte[] b2 = new byte[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = (byte) a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = (byte) a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }
    { // double
      double[] b1 = new double[a1.length];
      double[] b2 = new double[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }
    { // float
      float[] b1 = new float[a1.length];
      float[] b2 = new float[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(16, d.distance(b1, b2));
      assertEquals(16.0, d.distancef(b1, b2));
      assertEquals(48, d2.distance(b1, b2));
      assertEquals(48.0, d2.distancef(b1, b2));
    }

    s1 = "aaaaabcdefaaaaabcdefaaaaa";
    s2 = "bbbbbbcdefbbbbbbcdefbbbbb";
    cost_i = 2;
    cost_d = 2;
    int cost_c = 3;
    d = new EditDistance(cost_i, cost_d, cost_c);
    assertEquals(45, d.distance(s1, s2));
    assertEquals(45.0, d.distancef(s1, s2));
    a1 = s1.toCharArray();
    a2 = s2.toCharArray();
    assertEquals(45, d.distance(a1, a2));
    assertEquals(45.0, d.distancef(a1, a2));
    { // int
      int[] b1 = new int[a1.length];
      int[] b2 = new int[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    { // long
      long[] b1 = new long[a1.length];
      long[] b2 = new long[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    { // short
      short[] b1 = new short[a1.length];
      short[] b2 = new short[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = (short) a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = (short) a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    { // byte
      byte[] b1 = new byte[a1.length];
      byte[] b2 = new byte[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = (byte) a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = (byte) a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    { // double
      double[] b1 = new double[a1.length];
      double[] b2 = new double[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    { // float
      float[] b1 = new float[a1.length];
      float[] b2 = new float[a2.length];
      for (int i = 0; i < b1.length; i++) {
        b1[i] = a1[i];
      }
      for (int i = 0; i < b2.length; i++) {
        b2[i] = a2[i];
      }
      assertEquals(45, d.distance(b1, b2));
      assertEquals(45.0, d.distancef(b1, b2));
    }
    EditDistance dist1 = new EditDistance(1.1, 2.0, 2.0);
    EditDistance dist2 = new EditDistance(2.0, 1.1, 2.0);
    EditDistance dist3 = new EditDistance(2.0, 2.0, 1.1);
    assertEquals(1.1, dist1.distancef("a", "ab"));
    assertEquals(1.1, dist2.distancef("ab", "a"));
    assertEquals(1.1, dist3.distancef("a", "b"));
  }

  private void identicalSequencesD(SequenceDistanceMeasurerDouble d) {
    for (int n = 0; n <= 10; n++) {
      int[] a1 = new int[n];
      long[] a2 = new long[n];
      short[] a3 = new short[n];
      byte[] a4 = new byte[n];
      char[] a5 = new char[n];
      float[] a6 = new float[n];
      double[] a7 = new double[n];
      boolean[] a8 = new boolean[n];
      for (int i = 0; i < n; i++) {
        a1[i] = ThreadLocalRandom.current().nextInt(100);
        a2[i] = ThreadLocalRandom.current().nextInt(100);
        a3[i] = (short) ThreadLocalRandom.current().nextInt(100);
        a4[i] = (byte) ThreadLocalRandom.current().nextInt(100);
        a5[i] = (char) ThreadLocalRandom.current().nextInt(100);
        a6[i] = ThreadLocalRandom.current().nextInt(100);
        a7[i] = ThreadLocalRandom.current().nextInt(100);
        a8[i] = ThreadLocalRandom.current().nextBoolean();
      }
      String a9 = new String(a5);
      // distance of a sequence to itself should be 0
      assertEquals(0.0, d.distancef(a1, a1.clone()));
      assertEquals(0.0, d.distancef(a2, a2.clone()));
      assertEquals(0.0, d.distancef(a3, a3.clone()));
      assertEquals(0.0, d.distancef(a4, a4.clone()));
      assertEquals(0.0, d.distancef(a5, a5.clone()));
      assertEquals(0.0, d.distancef(a6, a6.clone()));
      assertEquals(0.0, d.distancef(a7, a7.clone()));
      assertEquals(0.0, d.distancef(a8, a8.clone()));
      assertEquals(0.0, d.distancef(a9, new String(a9)));
    }
  }
}
