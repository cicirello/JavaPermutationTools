/*
 * Copyright 2015-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
* Reversal Distance:
*
* <p>Reversal Distance is the minimum number of subpermutation reversals necessary to transform one
* permutation into the other.  This is an NP-Hard problem.</p>
*
* <p>Our original motivation for implementing this required computing reversal distance from a
* target permutation for all permutations of that target.  Therefore, we were going to exhaust
* all N! permutations of an N element list regardless of how we computed this.</p>
*
* <p>Therefore, the approach taken in this implementation is to compute a lookup table with N!
* elements, one for each of the N! possible permutations.  The lookup table maps permutations to
* distances.  The distances are computed using a breadth first enumeration outward from the
* permutation [0, 1, ..., (N-1)].  The starting permutation is distance 0 from itself.
* The N*(N-1)/2 permutations that are derived by reversing a subpermutation are a distance of 1 from 
* the initial permutation.  And we continue in this manner, computing all that are a distance of 2, and
* then 3, etc.</p>
*
* <p>The total cost of this is: O(N! * N^3) since each permutation has N^2 neighbors, generating a neighbor is linear cost, and there are N! permutations.
* Since our original application required computing distance for all N! permutations, the amortized cost (if your application
* also has that requirement) of computing distance is O(N^3).</p>
*
* <p>We have not used this for N &gt; 10.  Warning: time to construct distance measure increases factorially.</p>
*
* @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
* @version 1.29.2021
*/
public final class ReversalDistance extends AbstractPermutationDistanceMeasurer {

	private byte[] dist;
	private final int PERM_LENGTH;
	private int maxd;

	/**
	 * Construct the distance measure.  Default handles permutations of length n=10
	 */
	public ReversalDistance() {
		this(10);
	}
	
	/**
	 * Defines a distance measure for permutations of length n.  n must be no greater than 12.
	 * 
	 * @param n The length of the permutations supported.
	 * @throws IllegalArgumentException when n is greater than 12
	 */
	public ReversalDistance(int n) {
		if (n > 12 || n < 0) throw new IllegalArgumentException("Requires 0 <= n <= 12.");
		PERM_LENGTH = n;
		int fact = 1;
		maxd = 0;
		for (int i = 2; i <= n; i++) fact *= i;
		dist = new byte[fact];
		Permutation p = new Permutation(n,0);
		for (int i = 0; i < n-1; i++) {
			for (int j = i+1; j < n; j++) {
				p.reverse(i,j);
				int v = p.toInteger();
				p.reverse(i,j);
				dist[v] = 1;
				maxd = 1;
			}
		}
		int visited = n * (n-1) / 2 + 1;
		int start = 1;
		for (byte d = 1; visited < fact; d++) {
			for ( ; start < fact && dist[start] != 0 && dist[start] < d; start++);
			for (int e = start; e < fact; e++) {
				if (dist[e] == d) {
					p = new Permutation(n, e);
					for (int i = 0; i < n-1; i++) {
						for (int j = i+1; j < n; j++) {
							p.reverse(i,j);
							int v = p.toInteger();
							p.reverse(i,j);
							if (v > 0 && dist[v]==0) {
								maxd = dist[v] = (byte)(d + 1);
								visited++;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
	 * @throws IllegalArgumentException if length of the permutations
	 * is not equal to the
	 * the permutation length for which this was configured at time
	 * of construction.
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		if (p2.length() != p1.length() || p1.length() != PERM_LENGTH) throw new IllegalArgumentException("This distance measurer is configured for permutations of length " + PERM_LENGTH + " only.");
		int[] inv1 = p1.getInverse();
		int[] r2 = new int[inv1.length];
		for (int i = 0; i < inv1.length; i++) {
			r2[i] = inv1[p2.get(i)];
		}
		return dist[(new Permutation(r2)).toInteger()];
	}	
	
	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if length is not equal to the
	 * the permutation length for which this was configured at time
	 * of construction.
	 */
	@Override
	public int max(int length) {
		if (PERM_LENGTH != length) {
			throw new IllegalArgumentException("This distance measurer was not configured for length: " + length);
		}
		return maxd;
	}
	
}