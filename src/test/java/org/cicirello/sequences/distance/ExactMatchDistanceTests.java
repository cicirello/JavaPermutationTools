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

import org.junit.jupiter.api.*;

/** JUnit tests for ExactMatchDistance. */
public class ExactMatchDistanceTests extends InternalTestHelpersSequenceDistance {

  private ExactMatchDistance d;

  @BeforeEach
  public void init() {
    d = new ExactMatchDistance();
  }

  @Test
  public void testEMObjectSequences() {
    ExactMatchDistance d = new ExactMatchDistance();
    String[] a0 = {"a", "b", "c", "d", "e", "f"};
    String[] a1 = {"a", "b", "c", "d", "e", "f"};
    assertEquals(0, d.distance(a0, a1), "same");
    assertEquals(0, d.distance(toList(a0), toList(a1)), "same");
    String[] a2 = {"f", "a", "b", "c", "d", "e"};
    String[] a3 = {"f", "b", "c", "d", "e", "a"};
    String[] a4 = {"a", "d", "c", "b", "e", "f"};
    assertEquals(6, d.distance(a1, a2), "maximal distance");
    assertEquals(2, d.distance(a1, a3), "end points differ");
    assertEquals(2, d.distance(a1, a4), "differ in interior positions");
    assertEquals(6, d.distancef(a1, a2), "maximal distance");
    assertEquals(2, d.distancef(a1, a3), "end points differ");
    assertEquals(2, d.distancef(a1, a4), "differ in interior positions");
    assertEquals(6, d.distance(toList(a1), toList(a2)), "maximal distance");
    assertEquals(2, d.distance(toList(a1), toList(a3)), "end points differ");
    assertEquals(2, d.distance(toList(a1), toList(a4)), "differ in interior positions");
    assertEquals(6, d.distancef(toList(a1), toList(a2)), "maximal distance");
    assertEquals(2, d.distancef(toList(a1), toList(a3)), "end points differ");
    assertEquals(2, d.distancef(toList(a1), toList(a4)), "differ in interior positions");
    String[] b1 = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    String[] b2 = {"f", "a", "b", "c", "d", "e", "g", "h", "i"};
    String[] b3 = {"f", "b", "c", "d", "e", "a", "g", "h", "i"};
    String[] b4 = {"a", "d", "c", "b", "e", "f", "g", "h", "i"};
    assertEquals(3, d.distance(a1, b1), "identical except for extras");
    assertEquals(9, d.distance(a1, b2), "maximal distance");
    assertEquals(5, d.distance(a1, b3), "end points of shorter differ");
    assertEquals(5, d.distance(a1, b4), "differ in interior positions");
    assertEquals(3, d.distance(b1, a1), "identical except for extras");
    assertEquals(9, d.distance(b2, a1), "maximal distance");
    assertEquals(5, d.distance(b3, a1), "end points of shorter differ");
    assertEquals(5, d.distance(b4, a1), "differ in interior positions");

    assertEquals(3, d.distance(toList(a1), toList(b1)), "identical except for extras");
    assertEquals(9, d.distance(toList(a1), toList(b2)), "maximal distance");
    assertEquals(5, d.distance(toList(a1), toList(b3)), "end points of shorter differ");
    assertEquals(5, d.distance(toList(a1), toList(b4)), "differ in interior positions");
    assertEquals(3, d.distance(toList(b1), toList(a1)), "identical except for extras");
    assertEquals(9, d.distance(toList(b2), toList(a1)), "maximal distance");
    assertEquals(5, d.distance(toList(b3), toList(a1)), "end points of shorter differ");
    assertEquals(5, d.distance(toList(b4), toList(a1)), "differ in interior positions");

    NonComparable[] c1 = new NonComparable[6];
    NonComparable[] c2 = new NonComparable[6];
    NonComparable[] c3 = new NonComparable[6];
    for (int i = 0; i < c1.length; i++) {
      c1[i] = new NonComparable(i);
      c2[(i + 1) % c2.length] = new NonComparable(i);
      c3[i] = new NonComparable(i);
    }
    NonComparable temp = c3[0];
    c3[0] = c3[5];
    c3[5] = temp;
    assertEquals(6, d.distance(c1, c2), "maximal distance");
    assertEquals(2, d.distance(c1, c3), "end points differ");
    assertEquals(6, d.distance(toList(c1), toList(c2)), "maximal distance");
    assertEquals(2, d.distance(toList(c1), toList(c3)), "end points differ");
  }

  @Test
  public void testIdentical() {
    identicalSequences(d);
  }

  @Test
  public void testInt() {
    int[] a1 = {0, 1, 2, 3, 4, 5};
    int[] a2 = {5, 0, 1, 2, 3, 4};
    int[] a3 = {5, 1, 2, 3, 4, 0};
    int[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2), "maximal distance");
    assertEquals(2, d.distance(a1, a3), "end points differ");
    assertEquals(2, d.distance(a1, a4), "differ in interior positions");
    assertEquals(6, d.distancef(a1, a2), "maximal distance");
    assertEquals(2, d.distancef(a1, a3), "end points differ");
    assertEquals(2, d.distancef(a1, a4), "differ in interior positions");
    int[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    int[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    int[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    int[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1), "identical except for extras");
    assertEquals(9, d.distance(a1, b2), "maximal distance");
    assertEquals(5, d.distance(a1, b3), "end points of shorter differ");
    assertEquals(5, d.distance(a1, b4), "differ in interior positions");
    assertEquals(3, d.distance(b1, a1), "identical except for extras");
    assertEquals(9, d.distance(b2, a1), "maximal distance");
    assertEquals(5, d.distance(b3, a1), "end points of shorter differ");
    assertEquals(5, d.distance(b4, a1), "differ in interior positions");
  }

  @Test
  public void testLong() {
    long[] a1 = {0, 1, 2, 3, 4, 5};
    long[] a2 = {5, 0, 1, 2, 3, 4};
    long[] a3 = {5, 1, 2, 3, 4, 0};
    long[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    long[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    long[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    long[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    long[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }

  @Test
  public void testShort() {
    short[] a1 = {0, 1, 2, 3, 4, 5};
    short[] a2 = {5, 0, 1, 2, 3, 4};
    short[] a3 = {5, 1, 2, 3, 4, 0};
    short[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    short[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    short[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    short[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    short[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }

  @Test
  public void tesByte() {
    byte[] a1 = {0, 1, 2, 3, 4, 5};
    byte[] a2 = {5, 0, 1, 2, 3, 4};
    byte[] a3 = {5, 1, 2, 3, 4, 0};
    byte[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    byte[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    byte[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    byte[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    byte[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }

  @Test
  public void testChar() {
    char[] a1 = {0, 1, 2, 3, 4, 5};
    char[] a2 = {5, 0, 1, 2, 3, 4};
    char[] a3 = {5, 1, 2, 3, 4, 0};
    char[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    String s1 = new String(a1);
    String s2 = new String(a2);
    String s3 = new String(a3);
    String s4 = new String(a4);
    assertEquals(6, d.distance(s1, s2));
    assertEquals(2, d.distance(s1, s3));
    assertEquals(2, d.distance(s1, s4));
    char[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    char[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    char[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    char[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
    String t1 = new String(b1);
    String t2 = new String(b2);
    String t3 = new String(b3);
    String t4 = new String(b4);
    assertEquals(3, d.distance(s1, t1));
    assertEquals(9, d.distance(s1, t2));
    assertEquals(5, d.distance(s1, t3));
    assertEquals(5, d.distance(s1, t4));
    assertEquals(3, d.distance(t1, s1));
    assertEquals(9, d.distance(t2, s1));
    assertEquals(5, d.distance(t3, s1));
    assertEquals(5, d.distance(t4, s1));

    assertEquals(3, d.distancef(s1, t1));
    assertEquals(9, d.distancef(s1, t2));
    assertEquals(5, d.distancef(s1, t3));
    assertEquals(5, d.distancef(s1, t4));
    assertEquals(3, d.distancef(t1, s1));
    assertEquals(9, d.distancef(t2, s1));
    assertEquals(5, d.distancef(t3, s1));
    assertEquals(5, d.distancef(t4, s1));
  }

  @Test
  public void testBoolean() {
    boolean[] a1 = {false, true, false, true, false, true};
    boolean[] a2 = {true, false, true, false, true, false};
    boolean[] a3 = {true, true, false, true, false, false};
    boolean[] a4 = {false, true, true, false, false, true};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    boolean[] b1 = {false, true, false, true, false, true, true, true, true};
    boolean[] b2 = {true, false, true, false, true, false, false, false, false};
    boolean[] b3 = {true, true, false, true, false, false, true, true, true};
    boolean[] b4 = {false, true, true, false, false, true, true, true, true};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }

  @Test
  public void testDouble() {
    double[] a1 = {0, 1, 2, 3, 4, 5};
    double[] a2 = {5, 0, 1, 2, 3, 4};
    double[] a3 = {5, 1, 2, 3, 4, 0};
    double[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    double[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    double[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    double[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    double[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }

  @Test
  public void testFloat() {
    float[] a1 = {0, 1, 2, 3, 4, 5};
    float[] a2 = {5, 0, 1, 2, 3, 4};
    float[] a3 = {5, 1, 2, 3, 4, 0};
    float[] a4 = {0, 3, 2, 1, 4, 5};
    assertEquals(6, d.distance(a1, a2));
    assertEquals(2, d.distance(a1, a3));
    assertEquals(2, d.distance(a1, a4));
    assertEquals(6, d.distancef(a1, a2));
    assertEquals(2, d.distancef(a1, a3));
    assertEquals(2, d.distancef(a1, a4));
    float[] b1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    float[] b2 = {5, 0, 1, 2, 3, 4, 6, 7, 8};
    float[] b3 = {5, 1, 2, 3, 4, 0, 6, 7, 8};
    float[] b4 = {0, 3, 2, 1, 4, 5, 6, 7, 8};
    // tests with different length sequences
    assertEquals(3, d.distance(a1, b1));
    assertEquals(9, d.distance(a1, b2));
    assertEquals(5, d.distance(a1, b3));
    assertEquals(5, d.distance(a1, b4));
    assertEquals(3, d.distance(b1, a1));
    assertEquals(9, d.distance(b2, a1));
    assertEquals(5, d.distance(b3, a1));
    assertEquals(5, d.distance(b4, a1));
  }
}
