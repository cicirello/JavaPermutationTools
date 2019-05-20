/*
 * Copyright 2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.math.rand;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.SplittableRandom;

/**
 * RandomIndexer is a class of utility methods related to 
 * efficiently generating random indexes, and combination of indexes, into arrays.
 * The methods of this class neither directly operate, nor depend, on arrays, and can thus
 * be used wherever you need random integer values.  The name of the class is derived
 * from the motivating case, the case of efficiently generating random indexes into an array.
 * All methods are especially optimized for small integer ranges, specifically when the bound is
 * no greater than 256, i.e., for generating random integer values from the interval [0,256), but will
 * work more generally for any int bound.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 * @version 1.19.5.20
 * @since 1.4
 *
 */
public final class RandomIndexer {
	
	/**
	 * Class of static utility methods, so instantiation is not allowed.
	 */
	private RandomIndexer() {}
	
	/**
	 * Generates a random integer uniformly distributed in the interval: [0, bound).
	 * This method is optimized for small bounds (e.g., bound &lt;= 256) and otherwise
	 * delegates work to ThreadLocalRandom.nextInt(int bound) for bound &gt; 256.
	 * It uses ThreadLocalRandom.nextInt() to generate random int values, but maps them
	 * to the interval [0, bound) faster than ThreadLocalRandom does using a table
	 * of precomputed values of 2**31 mod bound to more quickly filter out overrepresented
	 * samples.  This method is thread safe, relying on ThreadLocalRandom for that safety.
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound) {
		if (bound <= 256) {
			if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
			int r = bound - 1;
			// check for power of two
			if ((bound & r) == 0) {
				return ThreadLocalRandom.current().nextInt() & r;
			}
			do {
				// generate random 32-bit int and mask off the sign bit
				r = ThreadLocalRandom.current().nextInt() & 0x7fffffff;
				// LIMIT[i] = pow(2, 31) mod i, precomputed for 0 < i < 256.
				// LIMIT[i] is thus number of extra samples to exclude to avoid
				// over-representing some int values.
			} while (r < LIMIT[bound]);
			return r % bound;
		}
		return ThreadLocalRandom.current().nextInt(bound);
	}
	
	/**
	 * Generates a random integer uniformly distributed in the interval: [0, bound).
	 * This method is optimized for small bounds (e.g., bound &lt;= 256) and otherwise
	 * delegates work to SplittableRandom.nextInt(int bound) for bound &gt; 256.
	 * It uses SplittableRandom.nextInt() to generate random int values, but maps them
	 * to the interval [0, bound) faster than SplittableRandom does using a table
	 * of precomputed values of 2**31 mod bound to more quickly filter out overrepresented
	 * samples.  Since SplittableRandom is not thread-safe, neither is this method.
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound, SplittableRandom gen) {
		if (bound <= 256) {
			if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
			int r = bound - 1;
			// check for power of two
			if ((bound & r) == 0) {
				return gen.nextInt() & r;
			}
			do {
				// generate random 32-bit int and mask off the sign bit
				r = gen.nextInt() & 0x7fffffff;
				// LIMIT[i] = pow(2, 31) mod i, precomputed for 0 < i < 256.
				// LIMIT[i] is thus number of extra samples to exclude to avoid
				// over-representing some int values.
			} while (r < LIMIT[bound]);
			return r % bound;
		}
		return gen.nextInt(bound);
	}
	
	/**
	 * Generates a random integer uniformly distributed in the interval: [0, bound).
	 * This method is optimized for small bounds (e.g., bound &lt;= 256) and otherwise
	 * delegates work to Random.nextInt(int bound) for bound &gt; 256.
	 * It uses Random.nextInt() to generate random int values, but maps them
	 * to the interval [0, bound) faster than Random does using a table
	 * of precomputed values of 2**31 mod bound to more quickly filter out overrepresented
	 * samples.
	 *
	 * @param bound Upper bound, exclusive, on range of random integers (must be positive).
	 * @param gen A source of randomness.
	 * @return a random integer between 0 (inclusive) and bound (exclusive).
	 * @throws IllegalArgumentException if the bound is not positive
	 */
	public static int nextInt(int bound, Random gen) {
		if (bound <= 256) {
			if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
			int r = bound - 1;
			// check for power of two
			if ((bound & r) == 0) {
				return gen.nextInt() & r;
			}
			do {
				// generate random 32-bit int and mask off the sign bit
				r = gen.nextInt() & 0x7fffffff;
				// LIMIT[i] = pow(2, 31) mod i, precomputed for 0 < i < 256.
				// LIMIT[i] is thus number of extra samples to exclude to avoid
				// over-representing some int values.
			} while (r < LIMIT[bound]);
			return r % bound;
		}
		return gen.nextInt(bound);
	}
	
	
	// LIMIT[i] = pow(2, 31) mod i, precomputed for 0 < i < 256.
	private static final int[] LIMIT = {0, 0x00, 0x00, 0x02, 0x00, 
		0x03, 0x02, 0x02, 0x00, 0x02, 0x08, 0x02, 0x08, 0x0b, 0x02, 0x08, 
		0x00, 0x09, 0x02, 0x03, 0x08, 0x02, 0x02, 0x06, 0x08, 0x17, 0x18, 
		0x0b, 0x10, 0x08, 0x08, 0x02, 0x00, 0x02, 0x1a, 0x17, 0x14, 0x16, 
		0x16, 0x0b, 0x08, 0x27, 0x02, 0x08, 0x18, 0x26, 0x06, 0x15, 0x20, 
		0x2c, 0x30, 0x1a, 0x18, 0x15, 0x26, 0x0d, 0x10, 0x29, 0x08, 0x37, 
		0x08, 0x3b, 0x02, 0x02, 0x00, 0x3f, 0x02, 0x32, 0x3c, 0x1d, 0x3a, 
		0x28, 0x38, 0x10, 0x16, 0x17, 0x3c, 0x02, 0x32, 0x19, 0x30, 0x41, 
		0x50, 0x50, 0x2c, 0x2b, 0x08, 0x08, 0x18, 0x43, 0x26, 0x25, 0x34, 
		0x02, 0x44, 0x03, 0x20, 0x42, 0x2c, 0x02, 0x30, 0x22, 0x1a, 0x53, 
		0x18, 0x17, 0x4a, 0x44, 0x5c, 0x5c, 0x44, 0x3b, 0x10, 0x08, 0x62, 
		0x62, 0x08, 0x0b, 0x72, 0x09, 0x08, 0x5a, 0x78, 0x50, 0x40, 0x17, 
		0x02, 0x08, 0x00, 0x08, 0x80, 0x7c, 0x44, 0x4f, 0x32, 0x26, 0x80, 
		0x11, 0x62, 0x5a, 0x80, 0x44, 0x28, 0x18, 0x80, 0x08, 0x10, 0x2c, 
		0x60, 0x8b, 0x62, 0x02, 0x88, 0x80, 0x02, 0x21, 0x80, 0x7d, 0x68, 
		0x4a, 0x80, 0x79, 0x92, 0x32, 0x50, 0x44, 0x50, 0x57, 0x80, 0x8d, 
		0x80, 0x9b, 0x08, 0x30, 0x08, 0x17, 0x70, 0xad, 0x9c, 0x3f, 0x80, 
		0x62, 0x80, 0x3b, 0x90, 0x85, 0x02, 0x91, 0x44, 0x41, 0x62, 0xa9, 
		0x80, 0x36, 0x42, 0x80, 0x2c, 0x2c, 0x02, 0x17, 0x30, 0x32, 0x22, 
		0x25, 0x80, 0xcb, 0xba, 0x1d, 0x80, 0x4f, 0x80, 0x83, 0xb4, 0xb6, 
		0x44, 0x08, 0xc8, 0x02, 0x5c, 0x59, 0x44, 0x80, 0xaa, 0x73, 0x80, 
		0xad, 0x08, 0x58, 0xd4, 0xc3, 0x62, 0x02, 0x08, 0x04, 0x80, 0x44, 
		0xe8, 0x68, 0x80, 0x37, 0x80, 0x80, 0x5a, 0x41, 0x78, 0x5d, 0x50, 
		0xc1, 0x40, 0x50, 0x94, 0xbb, 0x80, 0xa7, 0x08, 0x80
	};
	
}