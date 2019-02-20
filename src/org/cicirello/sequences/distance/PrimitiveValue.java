/*
 * Copyright 2018-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 
package org.cicirello.sequences.distance;

/**
 * The PrimitiveValue class is a wrapper class for all eight of Java's primitive types.
 * In some simple sense it is similar to the existing wrapper classes from the Java API.
 * However, it has some key differences.  First, one class that can be used with any primitive
 * type value, unlike the set of wrapper classes in the Java API.  Second, although in the Java API,
 * the six number types have wrappers that share a superclass ( {@link java.lang.Number Number} ),
 * the wrappers for the boolean and char types are not in that hierarchy.  The only ancestor class that all
 * eight of Java's wrapper classes have in common is the Object class.  Third, the PrimitiveValue class
 * otherwise has much less functionality.  It has been implemented to support specific methods
 * of the {@link PrimitiveSequence PrimitiveSequence} class, specifically the 
 * {@link PrimitiveSequence#get(int) PrimitiveSequence.get} method, and the 
 * {@link PrimitiveSequence#set(int, PrimitiveValue) PrimitiveSequence.set} method.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.27
 * @since 1.1
 */
final class PrimitiveValue {
	
	private final AbstractPrimitiveValue v;
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(int value) {
		v = new PrimitiveValue.Int(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(long value) {
		v = new PrimitiveValue.Long(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(short value) {
		v = new PrimitiveValue.Short(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(byte value) {
		v = new PrimitiveValue.Byte(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(char value) {
		v = new PrimitiveValue.Char(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(boolean value) {
		v = new PrimitiveValue.Boolean(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(double value) {
		v = new PrimitiveValue.Double(value);
	}
	
	/**
	 * Constructs a newly allocated PrimitiveValue object that wraps the primitive argument.
	 * @param value The primitive value to wrap.
	 */
	public PrimitiveValue(float value) {
		v = new PrimitiveValue.Float(value);
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an int, which may involve rounding or truncation.
	 * In the case of a boolean, this method returns 1 for true and 0 for false.
	 * @return the primitive value after converting to int (if necessary)
	 */
	public int intValue() {
		return v.intValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an long, which may involve rounding or truncation.
	 * In the case of a boolean, this method returns 1 for true and 0 for false.
	 * @return the primitive value after converting to long (if necessary)
	 */
	public long longValue() {
		return v.longValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an short, which may involve rounding or truncation.
	 * In the case of a boolean, this method returns 1 for true and 0 for false.
	 * @return the primitive value after converting to short (if necessary)
	 */
	public short shortValue() {
		return v.shortValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an byte, which may involve rounding or truncation.
	 * In the case of a boolean, this method returns 1 for true and 0 for false.
	 * @return the primitive value after converting to byte (if necessary)
	 */
	public byte byteValue() {
		return v.byteValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an char, which may involve rounding or truncation.
	 * In the case of a boolean, this method returns 1 for true and 0 for false.
	 * @return the primitive value after converting to char (if necessary)
	 */
	public char charValue() {
		return v.charValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as an boolean, which may involve rounding or truncation.
	 * In the case of numeric types, non-zero values are true and zero values are false.
	 * @return the primitive value after converting to boolean (if necessary)
	 */
	public boolean booleanValue() {
		return v.booleanValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as a double, which may involve rounding.
	 * In the case of a boolean, this method returns 1.0 for true and 0.0 for false.
	 * @return the primitive value after converting to double (if necessary)
	 */
	public double doubleValue() {
		return v.doubleValue();
	}
	
	/**
	 * Returns the value of the specified PrimitiveValue as a float, which may involve rounding.
	 * In the case of a boolean, this method returns 1.0 for true and 0.0 for false.
	 * @return the primitive value after converting to float (if necessary)
	 */
	public float floatValue() {
		return v.floatValue();
	}
	
	/**
	 * Compares this object to the specified object. The result is true if and only if the argument is not null, 
	 * it wraps a primitive of the same primitive type, and the wrapped primitive values are equal in terms of the == 
	 * operator.
	 * @param obj The other object to compare to.
	 * @return true if the objects are the same, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof PrimitiveValue)) return false;
		PrimitiveValue other = (PrimitiveValue)obj;
		return v.equals(other.v);
	}
	
	/**
	 * The hashCode of the PrimitiveValue is simply the wrapped value converted to an int.  For wrapped booleans, true is 
	 * 1 and false is 0.
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return v.hashCode();
	}
	
	
	private static abstract class AbstractPrimitiveValue {
		AbstractPrimitiveValue() {}
		abstract int intValue();
		abstract long longValue();
		abstract short shortValue();
		abstract byte byteValue();
		abstract char charValue();
		abstract boolean booleanValue();
		abstract double doubleValue();
		abstract float floatValue();
		@Override
		public int hashCode() { return intValue(); }
	}
	
	private static final class Long extends AbstractPrimitiveValue {
		private final long value;
		Long(long value) {this.value = value;}
		int intValue() { return (int)value; }
		long longValue() { return value; }
		short shortValue() { return (short)value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0L; }
		double doubleValue() { return value; }
		float floatValue() { return value; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Long)) return false;
			Long other = (Long)obj;
			return value == other.value;
		}
	}
	
	private static final class Int extends AbstractPrimitiveValue {
		private final int value;
		Int(int value) {this.value = value;}
		int intValue() { return value; }
		long longValue() { return value; }
		short shortValue() { return (short)value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0; }
		double doubleValue() { return value; }
		float floatValue() { return value; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Int)) return false;
			Int other = (Int)obj;
			return value == other.value;
		}
	}
	
	private static final class Short extends AbstractPrimitiveValue {
		private final short value;
		Short(short value) {this.value = value;}
		int intValue() { return value; }
		long longValue() { return value; }
		short shortValue() { return value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0; }
		double doubleValue() { return value; }
		float floatValue() { return value; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Short)) return false;
			Short other = (Short)obj;
			return value == other.value;
		}
	}
	
	private static final class Byte extends AbstractPrimitiveValue {
		private final byte value;
		Byte(byte value) {this.value = value;}
		int intValue() { return value; }
		long longValue() { return value; }
		short shortValue() { return value; }
		byte byteValue() { return value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0; }
		double doubleValue() { return value; }
		float floatValue() { return value; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Byte)) return false;
			Byte other = (Byte)obj;
			return value == other.value;
		}
	}
	
	private static final class Char extends AbstractPrimitiveValue {
		private final char value;
		Char(char value) {this.value = value;}
		int intValue() { return value; }
		long longValue() { return value; }
		short shortValue() { return (short)value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return value; }
		boolean booleanValue() { return value != 0; }
		double doubleValue() { return value; }
		float floatValue() { return value; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Char)) return false;
			Char other = (Char)obj;
			return value == other.value;
		}
	}
	
	private static final class Boolean extends AbstractPrimitiveValue {
		private final boolean value;
		Boolean(boolean value) {this.value = value;}
		int intValue() { return value ? 1 : 0; }
		long longValue() { return value ? 1 : 0; }
		short shortValue() { return value ? (short)1 : (short)0; }
		byte byteValue() { return value ? (byte)1 : (byte)0; }
		char charValue() { return value ? (char)1 : (char)0; }
		boolean booleanValue() { return value; }
		double doubleValue() { return value ? 1.0 : 0.0; }
		float floatValue() { return value ? 1.0f : 0.0f; }
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Boolean)) return false;
			Boolean other = (Boolean)obj;
			return value == other.value;
		}
	}
	
	private static final class Double extends AbstractPrimitiveValue {
		private final double value;
		Double(double value) {this.value = value;}
		int intValue() { return (int)value; }
		long longValue() { return (long)value; }
		short shortValue() { return (short)value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0.0; }
		double doubleValue() { return value; }
		float floatValue() { return (float)value; }
		@Override
		public int hashCode() {
			if (value == 0.0) return 0;
			double temp = value;
			int cast = (int)temp;
			while (cast == 0) {
				temp *= 10;
				cast = (int)temp;
			}
			if (cast > -10 && cast < 10) temp *= 1000000;
			return (int)temp;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Double)) return false;
			Double other = (Double)obj;
			return value == other.value;
		}
	}
	
	private static final class Float extends AbstractPrimitiveValue {
		private final float value;
		Float(float value) {this.value = value;}
		int intValue() { return (int)value; }
		long longValue() { return (long)value; }
		short shortValue() { return (short)value; }
		byte byteValue() { return (byte)value; }
		char charValue() { return (char)value; }
		boolean booleanValue() { return value != 0.0f; }
		double doubleValue() { return value; }
		float floatValue() { return value; } 
		@Override
		public int hashCode() {
			if (value == 0.0f) return 0;
			float temp = value;
			int cast = (int)temp;
			while (cast == 0) {
				temp = temp * 10;
				cast = (int)temp;
			}
			if (cast > -10 && cast < 10) temp = temp * 1000000;
			return (int)temp;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof Float)) return false;
			Float other = (Float)obj;
			return value == other.value;
		}
	}
	
}