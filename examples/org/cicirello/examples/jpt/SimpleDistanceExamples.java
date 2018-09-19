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

package org.cicirello.examples.jpt;

import org.cicirello.permutations.Permutation;
import org.cicirello.permutations.distance.*;

/**
 * Basic examples of the use of the classes from the 
 * org.cicirello.permutations.distance package.
 *
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class SimpleDistanceExamples {
	public static void main(String[] args) {
		
		System.out.println("If you are running this example program, make");
		System.out.println("sure you also read the source code and comments.");
		System.out.println("Output of example program in isolation of the");
		System.out.println("source and comments won't tell you much.");
		
		// Most of the Permutation distance classes implement one or both of
		// the interfaces PermutationDistanceMeasurer and PermutationDistanceMeasurerDouble.
		// Most implement both, except there are a couple that do not necessarily produce an integer result, 
		// so those only implement PermutationDistanceMeasurerDouble.
		// This enables you to write code that is independent of specific distance metric.
		// Additionally, most of the distance metrics do not require parameters when constructed.
		
		// Here are a couple examples of how to construct a distance metric object.
		// See API for complete list of available distances.
		PermutationDistanceMeasurer d1 = new ExactMatchDistance();
		PermutationDistanceMeasurer d2 = new KendallTauDistance();
		
		// To compute distance, use the distance method.
		Permutation p1 = new Permutation(10);
		Permutation p2 = new Permutation(p1);
		p2.reverse();
		System.out.println("p1 is " + p1);
		System.out.println("p2 is " + p2);
		System.out.println("Exact match distance is number of positions with same elements.");
		System.out.println("Exact match distance between p1 and p2: " + d1.distance(p1,p2));
		System.out.println("Kendall tau distance is min number of adjacent swaps needed to transform p1 to p2.");
		System.out.println("This example is the maximum case, when one permutation is reverse of the other.");
		System.out.println("Kendall tau distance between p1 and p2: " + d2.distance(p1,p2));
		
		// Some distance metrics don't necessarily compute integer distances.
		// Those only implement PermutationDistanceMeasurerDouble and thus you must
		// use the distancef method.
		PermutationDistanceMeasurerDouble d3 = new EditDistance(1, 1, 2);
		Permutation p3 = new Permutation(p1);
		p3.reverse(1,4);
		System.out.println("p1 is " + p1);
		System.out.println("p3 is " + p3);
		System.out.println("EditDistance with insertion and deletion costs equal to 1, and change cost equal to 2.");
		System.out.println("EditDistance between p1 and p3: " + d3.distancef(p1,p3));
		
	}
}
