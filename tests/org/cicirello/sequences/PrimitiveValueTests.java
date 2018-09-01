/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 *
 */
package org.cicirello.sequences;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the PrimitiveValue class.
 */
public class PrimitiveValueTests {
	
	private static final double DELTA_0 = 0.0; // for comparisons that should be exact
	private static final float DELTA_F_0 = 0.0f; // for comparisons that should be exact
	
	@Test
	public void testPrimitiveValueLong() {
		long value = Integer.MAX_VALUE;
		value = value + 100L;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue(0L)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueInt() {
		int value = Integer.MAX_VALUE;
		value = value - 100;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue(0)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueShort() {
		short value = (short)1024;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue((short)0)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueByte() {
		byte value = (short)63;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue((byte)0)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueChar() {
		char value = (char)1024;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue((char)0)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueBoolean() {
		PrimitiveValue t = new PrimitiveValue(true);
		PrimitiveValue f = new PrimitiveValue(false);
		assertEquals("to long", (long)1, t.longValue());
		assertEquals("to int", (int)1, t.intValue());
		assertEquals("to short", (short)1, t.shortValue());
		assertEquals("to byte", (byte)1, t.byteValue());
		assertEquals("to char", (char)1, t.charValue());
		assertEquals("to double", 1.0, t.doubleValue(), DELTA_0);
		assertEquals("to float", 1.0f, t.floatValue(), DELTA_F_0);
		assertEquals("to long", (long)0, f.longValue());
		assertEquals("to int", (int)0, f.intValue());
		assertEquals("to short", (short)0, f.shortValue());
		assertEquals("to byte", (byte)0, f.byteValue());
		assertEquals("to char", (char)0, f.charValue());
		assertEquals("to double", 0.0, f.doubleValue(), DELTA_0);
		assertEquals("to float", 0.0f, f.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", t.booleanValue());
		assertFalse("to boolean false", f.booleanValue());
	}
	
	@Test
	public void testPrimitiveValueDouble() {
		double value = 17.131517192426282019129;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", (float)value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue(0.0)).booleanValue());
	}
	
	@Test
	public void testPrimitiveValueFloat() {
		float value = 17.131517192426282019129f;
		PrimitiveValue pv = new PrimitiveValue(value);
		assertEquals("to long", (long)value, pv.longValue());
		assertEquals("to int", (int)value, pv.intValue());
		assertEquals("to short", (short)value, pv.shortValue());
		assertEquals("to byte", (byte)value, pv.byteValue());
		assertEquals("to char", (char)value, pv.charValue());
		assertEquals("to double", (double)value, pv.doubleValue(), DELTA_0);
		assertEquals("to float", value, pv.floatValue(), DELTA_F_0);
		assertTrue("to boolean true", pv.booleanValue());
		assertFalse("to boolean false", (new PrimitiveValue(0.0f)).booleanValue());
	}
	
}