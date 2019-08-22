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


package org.cicirello.math;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test cases for the methods of the MathFunctions class.
 */
public class MathFunctionsTests {
	
	// precision for floating-point comparisons
	private static final double EPSILON = 1e-10;
	
	@Test
	public void testLogGamma() {
		final int MAX = 20;
		final int D_MAX = 35;
		
		long fact = 1;
		for (int n = 1; n <= MAX+1; n++) {
			long gammaOfN = fact;
			double expected = StrictMath.log(gammaOfN);
			assertEquals(expected, MathFunctions.logGamma(n), EPSILON);
			if (n < MAX+1) fact = fact * ((long)n);
		}
		double f = fact;
		f = f * (MAX+1);
		for (int n = MAX+2; n <= D_MAX; n++) {
			double gammaOfN = f;
			double expected = StrictMath.log(gammaOfN);
			assertEquals(expected, MathFunctions.logGamma(n), EPSILON);
			f = f * n;
		}
		double gammaOf100=0;
		for (int n = D_MAX+1; n <= 100; n++) {
			gammaOf100 = f;
			f = f * n;
		}
		assertEquals(StrictMath.log(gammaOf100), MathFunctions.logGamma(100), EPSILON);
		double logGammaOf1000 = StrictMath.log(gammaOf100);
		for (int n = 100; n < 1000; n++) {
			logGammaOf1000 += StrictMath.log(n);
		}
		assertEquals(logGammaOf1000, MathFunctions.logGamma(1000), EPSILON);
		double logGammaOf2000 = logGammaOf1000;
		for (int n = 1000; n < 2000; n++) {
			logGammaOf2000 += StrictMath.log(n);
		}
		assertEquals(logGammaOf2000, MathFunctions.logGamma(2000), EPSILON);
	}
	
}