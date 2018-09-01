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
 */
package org.cicirello.sequences.distance;


import org.cicirello.sequences.Sequence;

/**
 * <p>EditDistance is an implementation of Wagner and Fischer's dynamic programming algorithm for computing string edit distance.</p>
 * 
 * <p>Edit distance is the minimum cost to transform one string (or sequence) into the other, which is the sum of the costs
 * of the edit operations necessary to do so.  This edit distance considers 3 edit operations:
 * Inserts which insert a new element into the sequence, Deletes which remove an element from the sequence, and
 * Changes which replace an element with a different element.</p>
 *
 * <p>The edit distance is parameterized by costs for the edit operations.  We provide two constructors which enable you to specify
 * 3 costs, 1 for each type of edit operation.  One of the constructors expects integer costs, and the other double valued costs.
 * If you specify costs as integers, then all of the distance and distancef methods from the 
 * {@link org.cicirello.sequences.distance.SequenceDistanceMeasurer SequenceDistanceMeasurer} 
 * and {@link org.cicirello.sequences.distance.SequenceDistanceMeasurerDouble SequenceDistanceMeasurerDouble} 
 * interfaces are available.  If costs are specified as doubles, then
 * only the distancef methods will function, while the distance methods will throw exceptions.</p>
 *
 * <p>This class supports computing EditDistance for Java String objects or arrays of any of the primitive types, or arrays of objects.  It makes no assumptions
 * about the contents of the Strings or arrays, and they can contain duplicates, or can be such that some elements only appear in one or
 * the other of the sequences, or can be of different lengths.</p>
 *
 * <p>Another class (with same name but in different package) is available if you need to compute distance specifically between permutations,
 * rather than general sequences.  That class is the {@link org.cicirello.permutations.distance.EditDistance} class which computes distance between permutations
 * of the integers from 0 to N-1.</p> 
 *
 * <p>Runtime: O(n*m), where n and m are the lengths of the two sequences (i.e., Strings or arrays).</p> 
 *
 * <p>Wagner and Fischer's String Edit Distance was introduced in:<br>
 * R. A. Wagner and M. J. Fischer, "The string-to-string correction problem," Journal of the ACM, vol. 21, no. 1, pp. 168–173, January 1974.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.28
 * @since 1.1
 */
public class EditDistance extends AbstractSequenceDistanceMeasurer {
	
	private final int insert_i;
	private final int delete_i;
	private final int change_i;
	
	private final double insert_d;
	private final double delete_d;
	private final double change_d;
	
	/**
	 * Constructs an edit distance measure with the specified edit operation costs.  With costs as doubles, all of the distancef methods that
	 * compute distance as double values are available.  The distance methods that compute integer-valued distances may or may not be available if
	 * this constructor is used with double valued costs.  If the costs are equal to integer values if cast to type double, then the distance methods
	 * will also function.  Otherwise, they will throw an exception.  For safety, it is recommended to only use the distancef methods if you use this
	 * constructor to pass costs as type double.  If you desire integer valued distances, then use the other constructor to pass costs as ints.
	 *
	 * @param insertCost Cost of an insertion operation. Must be non-negative.
	 * @param deleteCost Cost of an deletion operation. Must be non-negative.
	 * @param changeCost Cost of an change operation. Must be non-negative.
	 */
	public EditDistance(double insertCost, double deleteCost, double changeCost) {
		if (insertCost < 0.0 || deleteCost < 0.0 || changeCost < 0.0) throw new IllegalArgumentException("Costs must be non-negative.");
		insert_d = insertCost;
		delete_d = deleteCost;
		change_d = changeCost;
		if (isIntAsDouble(insertCost) && isIntAsDouble(deleteCost) && isIntAsDouble(changeCost)) {
			insert_i = (int)insertCost;
			delete_i = (int)deleteCost;
			change_i = (int)changeCost;
		} else {
			// Use -1 on these to disallow use of distance (the one that returns an int)... make sure to throw an exception there
			insert_i = delete_i = change_i = -1;
		}
	}
	
	/**
	 * Constructs an edit distance measure with the specified edit operation costs.  With integer costs, all of the distance and distancef methods
	 * are available.
	 * @param insertCost Cost of an insertion operation. Must be non-negative.
	 * @param deleteCost Cost of an deletion operation. Must be non-negative.
	 * @param changeCost Cost of an change operation. Must be non-negative.
	 */
	public EditDistance(int insertCost, int deleteCost, int changeCost) {
		if (insertCost < 0 || deleteCost < 0 || changeCost < 0) throw new IllegalArgumentException("Costs must be non-negative.");
		insert_d = insert_i = insertCost;
		delete_d = delete_i = deleteCost;
		change_d = change_i = changeCost;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException if costs were initialized with double values.
	 */
	@Override
	public <T> int distance(Sequence<T> s1, Sequence<T> s2) {
		if (insert_i < 0) throw new UnsupportedOperationException("EditDistance.distance not supported for floating-point costs.");
		int L1 = s1.length();
		int L2 = s2.length();
		int[][] D = new int[L1 + 1][L2 + 1];
		for (int i = 1; i <= L1; i++) {
			D[i][0] = D[i-1][0] + delete_i;
		}
		for (int j = 1; j <= L2; j++) {
			D[0][j] = D[0][j-1] + insert_i;
		}
		for (int i = 1; i <= L1; i++) {
			for (int j = 1; j <= L2; j++) { 
				int m1 = D[i-1][j-1] + ((!s1.equal(i-1, s2, j-1)) ? change_i : 0);
				int m2 = D[i-1][j] + delete_i;
				int m3 = D[i][j-1] + insert_i;
				int min = m1;
				if (m2 < min) min = m2;
				if (m3 < min) min = m3;
				D[i][j] = min;
			}
		}
		return D[L1][L2];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> double distancef(Sequence<T> s1, Sequence<T> s2) {
		int L1 = s1.length();
		int L2 = s2.length();
		double[][] D = new double[L1 + 1][L2 + 1];
		for (int i = 1; i <= L1; i++) {
			D[i][0] = D[i-1][0] + delete_d;
		}
		for (int j = 1; j <= L2; j++) {
			D[0][j] = D[0][j-1] + insert_d;
		}
		for (int i = 1; i <= L1; i++) {
			for (int j = 1; j <= L2; j++) { 
				double m1 = D[i-1][j-1] + ((!s1.equal(i-1, s2, j-1)) ? change_d : 0);
				double m2 = D[i-1][j] + delete_d;
				double m3 = D[i][j-1] + insert_d;
				double min = m1;
				if (m2 < min) min = m2;
				if (m3 < min) min = m3;
				D[i][j] = min;
			}
		}
		return D[L1][L2];
	}
	
	private boolean isIntAsDouble(double d) {
		 return ((double)((int)d)) == d;
	}
}