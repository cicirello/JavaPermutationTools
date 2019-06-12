/*
 * Copyright 2010, 2017-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Edit Distance:
 *
 * <p>This is an implementation of Wagner and Fischer's dynamic programming algorithm for computing string edit distance,
 * but adapted to permutations rather than general strings.</p>
 * 
 * <p>Edit distance is the minimum cost to transform one permutation into the other, which is the sum of the costs
 * of the edit operations necessary to do so.  This edit distance considers 3 edit operations:
 * Inserts which insert a new element into the permutation, Deletes which remove an element from the permutation, and
 * Changes which replace an element with a different element.</p>
 *
 * <p>The edit distance is parameterized by costs for the edit operations.  We provide one constructor which enables you to specify
 * 3 costs, 1 for each type of edit operation.</p>  
 *
 * <p>And we also provide a default constructor, which assigns a cost of 0.5 for insert,
 * 0.5 for delete, and which assigns an infinite cost to changes, essentially disallowing that 3rd type of edit operation.
 * We chose this as the default as it is equivalent to counting the number of reinsertion operations necessary to transform 
 * one permutation into the other, known as Reinsertion Distance.  A reinsertion operation removes an element and reinserts it
 * in a different position, and is treated as a single composite operation.</p>
 *
 * <p>Runtime: O(n<sup>2</sup>), where n is the permutation length.</p>
 *
 * <p>Wagner and Fischer's String Edit Distance was introduced in:<br>
 * R. A. Wagner and M. J. Fischer, "The string-to-string correction problem," Journal of the ACM, vol. 21, no. 1, pp. 168–173, January 1974.</p>
 *
 * <p>Its application as a means of computing Reinsertion Distance is first described in:<br>
 * V. A. Cicirello and R. Cernera, <a href="https://www.cicirello.org/publications/cicirello2013flairs.html" target=_top>"Profiling the distance characteristics 
 * of mutation operators for permutation-based genetic algorithms,"</a> 
 * in Proceedings of the 26th FLAIRS Conference. AAAI Press, May 2013, pp. 46–51.</p> 
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.6.12
 * @since 1.0
 */
public final class EditDistance implements PermutationDistanceMeasurerDouble 
{
	private double insertCost;
	private double deleteCost;
	private double changeCost;
  
	/**
	* Constructs an EditDistance function.
	*  
	* @param insertCost Cost of an insertion operation.
	* @param deleteCost Cost of a deletion operation.
	* @param changeCost Cost of a change operation.
	*  
	*/
	public EditDistance(double insertCost, double deleteCost, double changeCost) {
		this.insertCost = insertCost;
		this.deleteCost = deleteCost;
		this.changeCost = changeCost;
	}
  
	/**
	 * Default edit distance computes number of remove and reinsert operations to
	 * transform one permutation into the other.  And does not use change operations.
	 */
	public EditDistance() {
		insertCost = deleteCost = 0.5;
		changeCost = 1.0;
	}
  
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distancef(Permutation p1, Permutation p2) {
		int L1 = p1.length();
		int L2 = p2.length();
		if (L1 == L2 && L1 <= 1) return 0;
		double[][] D = new double[L1 + 1][L2 + 1];
		for (int i = 1; i <= L1; i++) {
			D[i][0] = D[i-1][0] + deleteCost;
		}
		for (int j = 1; j <= L2; j++) {
			D[0][j] = D[0][j-1] + insertCost;
		}
		for (int i = 1; i <= L1; i++) {
			for (int j = 1; j <= L2; j++) {
				double m1 = D[i-1][j-1] + ((p1.get(i-1) != p2.get(j-1)) ? changeCost : 0);
				double m2 = D[i-1][j] + deleteCost;
				double m3 = D[i][j-1] + insertCost;
				double min = m1;
				if (m2 < min) min = m2;
				if (m3 < min) min = m3;
				D[i][j] = min;
			}
		}
		return D[L1][L2];
	}
	
	
	
	/* // REMOVED FOR NOW
	 * The maxf method is not currently unsupported when computing
	 * edit distance.
	 *
	 * @throws UnsupportedOperationException If this method is invoked. 
	 */
	//@Override
	//public double maxf(int length) {
		//throw new UnsupportedOperationException("Unimplemented.");
		/* // This is close but doesn't work.  Might be too complex to be worth computing here.
		if (length <= 1) return 0;
		double combined = insertCost + deleteCost;
		if (combined <= changeCost) {
			return (length-1)*combined;
		}
		if (length % 2 == 0) {
			double m1 = (length-1)*combined;
			double m2 = length*changeCost;
			double m3 = combined + (length-2)*changeCost;
			double max = m1 < m2 ? m1 : m2;
			if (m3 < max) max = m3;
			return max;
		} else {
			double m = (length-1)*changeCost;
			double m1 = (length-2)*combined;
			double m2 = length*changeCost;
			double m3 = combined + (length-2)*changeCost;
			double max = m1 < m2 ? m1 : m2;
			if (m3 < max) max = m3;
			return m > max ? m : max;
		} */
	//}
  
}
