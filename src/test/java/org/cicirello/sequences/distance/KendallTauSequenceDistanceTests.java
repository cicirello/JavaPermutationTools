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
package org.cicirello.sequences.distance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.*;

/** JUnit tests for KendallTauSequenceDistance. */
public class KendallTauSequenceDistanceTests extends InternalTestHelpersKendallTau {

  @Test
  public void testTauObjectSequences() {
    KendallTauSequenceDistance d = new KendallTauSequenceDistance();
    assertEquals(0, d.distance(new String[0], new String[0]));
    assertEquals(0, d.distance(new ArrayList<String>(), new ArrayList<String>()));
    for (int n = 2; n <= 8; n++) {
      // maximal distance if all unique elements (i.e., a permutation) is reversed sequence
      String[] s1 = new String[n];
      String[] s2 = new String[n];
      String[] s3 = new String[n];
      char letter = 'a';
      for (int i = 0; i < n; i++) {
        s3[i] = s1[i] = s2[n - 1 - i] = ("" + letter);
        letter = (char) (letter + 1);
      }
      s3[0] = s2[0];
      s3[n - 1] = s2[n - 1];
      // maximal distance
      int expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(s1, s2));
      assertEquals(expected, d.distance(s2, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s2)));
      assertEquals(expected, d.distance(toList(s2), toList(s1)));
      // end points swapped
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(s1, s3));
      assertEquals(expected, d.distance(s3, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s3)));
      assertEquals(expected, d.distance(toList(s3), toList(s1)));
    }
    for (int n = 2; n <= 8; n++) {
      // maximal distance if all unique elements (i.e., a permutation) is reversed sequence
      NonComparable[] s1 = new NonComparable[n];
      NonComparable[] s2 = new NonComparable[n];
      NonComparable[] s3 = new NonComparable[n];
      for (int i = 0; i < n; i++) {
        s3[i] = s1[i] = s2[n - 1 - i] = new NonComparable(i + 2);
      }
      s3[0] = s2[0];
      s3[n - 1] = s2[n - 1];
      int expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(s1, s2));
      assertEquals(expected, d.distance(s2, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s2)));
      assertEquals(expected, d.distance(toList(s2), toList(s1)));
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(s1, s3));
      assertEquals(expected, d.distance(s3, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s3)));
      assertEquals(expected, d.distance(toList(s3), toList(s1)));
    }
  }

  @Test
  public void testTauAlg2ObjectSequences() {
    final KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
    for (int n = 2; n <= 8; n++) {
      // maximal distance if all unique elements (i.e., a permutation) is reversed sequence
      String[] s1 = new String[n];
      String[] s2 = new String[n];
      String[] s3 = new String[n];
      char letter = 'a';
      for (int i = 0; i < n; i++) {
        s3[i] = s1[i] = s2[n - 1 - i] = ("" + letter);
        letter = (char) (letter + 1);
      }
      s3[0] = s2[0];
      s3[n - 1] = s2[n - 1];
      // maximal distance
      int expected = n * (n - 1) / 2;
      assertEquals(expected, d.distance(s1, s2));
      assertEquals(expected, d.distance(s2, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s2)));
      assertEquals(expected, d.distance(toList(s2), toList(s1)));
      // end points swapped
      expected = 2 * n - 3;
      assertEquals(expected, d.distance(s1, s3));
      assertEquals(expected, d.distance(s3, s1));
      assertEquals(expected, d.distance(toList(s1), toList(s3)));
      assertEquals(expected, d.distance(toList(s3), toList(s1)));
    }

    NonComparable[] s1 = new NonComparable[3];
    NonComparable[] s2 = new NonComparable[3];
    for (int i = 0; i < 3; i++) {
      s1[i] = s2[i] = new NonComparable(i + 2);
    }
    ClassCastException thrown = assertThrows(ClassCastException.class, () -> d.distance(s1, s2));
    ArrayStoreException thrown2 =
        assertThrows(ArrayStoreException.class, () -> d.distance(toList(s1), toList(s2)));
  }

  @Test
  public void testKendallTauDistanceExceptions() {
    final KendallTauSequenceDistance d = new KendallTauSequenceDistance();

    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new int[3], new int[4]));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new long[3], new long[4]));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new short[3], new short[4]));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new byte[3], new byte[4]));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> d.distance(new double[3], new double[4]));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new float[3], new float[4]));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> d.distance(new boolean[3], new boolean[4]));
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(new char[3], new char[4]));
    thrown =
        assertThrows(
            IllegalArgumentException.class, () -> d.distance(new String[3], new String[4]));
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance("hello", "hello again"));
    final ArrayList<String> s1 = new ArrayList<String>();
    final ArrayList<String> s2 = new ArrayList<String>();
    s1.add("a");
    s1.add("b");
    s2.add("a");
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(s1, s2));
  }

  @Test
  public void testKendallTauDistanceExceptionsDiffElements() {
    final KendallTauSequenceDistance d = new KendallTauSequenceDistance();
    helperKendallTauDistanceExceptionsDiffElements(d);
  }

  @Test
  public void testKendallTauDistanceExceptionsDiffElementsAlg2() {
    final KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
    helperKendallTauDistanceExceptionsDiffElements(d);
  }

  @Test
  public void testKendallTauDistance() {
    KendallTauSequenceDistance d = new KendallTauSequenceDistance();
    identicalSequences(d);
    helperForKendallTauCases(d);
  }

  @Test
  public void testKendallTauDistanceAlg2() {
    KendallTauSequenceDistance d = new KendallTauSequenceDistance(true);
    identicalSequences(d);
    helperForKendallTauCases(d);
    helperForKendallTauBooleans(d);
  }

  @Test
  public void testKendallTauDistanceAlg1() {
    KendallTauSequenceDistance d = new KendallTauSequenceDistance(false);
    identicalSequences(d);
    helperForKendallTauCases(d);
    helperForKendallTauBooleans(d);
  }

  final void helperKendallTauDistanceExceptionsDiffElements(final KendallTauSequenceDistance d) {

    final int[] i1 = {1, 2, 3};
    final int[] i2 = {1, 2, 4};
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(i1, i2));
    final String[] str1 = {"a", "a", "c"};
    final String[] str2 = {"a", "a", "d"};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(str1, str2));
    final ArrayList<String> aL1 = new ArrayList<String>();
    final ArrayList<String> aL2 = new ArrayList<String>();
    for (String e : str1) aL1.add(e);
    for (String e : str2) aL2.add(e);
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(aL1, aL2));
    final double[] d1 = {1, 2, 3};
    final double[] d2 = {1, 2, 4};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(d1, d2));
    final float[] f1 = {1f, 2f, 3f};
    final float[] f2 = {1f, 2f, 4f};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(f1, f2));
    final long[] L1 = {1, 2, 3};
    final long[] L2 = {1, 2, 4};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(L1, L2));
    final short[] sh1 = {1, 2, 3};
    final short[] sh2 = {1, 2, 4};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(sh1, sh2));
    final byte[] b1 = {1, 2, 3};
    final byte[] b2 = {1, 2, 4};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(b1, b2));
    final char[] ch1 = {'1', '2', '3'};
    final char[] ch2 = {'1', '2', '4'};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(ch1, ch2));
    final String g1 = "123";
    final String g2 = "124";
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(g1, g2));
    final boolean[] bool1 = {false, false, true};
    final boolean[] bool2 = {false, false, false};
    thrown = assertThrows(IllegalArgumentException.class, () -> d.distance(bool1, bool2));
    final String diffCounts1 = "ababa";
    final String diffCounts2 = "babab";
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(diffCounts1, diffCounts2));
    final String diffCounts3 = "aaaab";
    thrown =
        assertThrows(IllegalArgumentException.class, () -> d.distance(diffCounts1, diffCounts3));
  }
}
