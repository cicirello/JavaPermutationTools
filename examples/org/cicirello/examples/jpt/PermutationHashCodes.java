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

package org.cicirello.examples.jpt;

import org.cicirello.permutations.Permutation;
import java.util.HashSet;

/**
 * <p>Purpose: This example program originally served a specific purpose during development.
 * Specifically, I wanted to confirm that the use of Arrays.hashCode to implement the hashCode
 * method of the Permutation class was a good choice (i.e., did it adequately avoid collisions).
 * In general, it does a good job for arrays, but I wanted to confirm that the special nature of
 * an array containing a permutation of the integers from 0 to N-1 didn't lead to a large number of collisions.</p>
 * <p>Command line arguments: Two optional command line arguments, both integers.
 * First argument is max permutation length (default is 9).
 * Second argument is max permutation length to exhaustively print"
 * (default is 0).</p>
 * <p>Function: For each permutation length, up to 8 (or max specified on command
 * line), iterates over all permutations of that length, computing number
 * of hashCode collisions, i.e., number of permutations that share a
 * hashCode with another permutation of that length.  It does not count 
 * collisions across different length permutations.  Output includes 
 * length, collision count, and percentage of permutations of a given
 * length that collide (i.e., if N is length, the collision percentage
 * is: (#collisions / N!).</p>
 * <p>WARNING: If N is the first command line argument, runtime is at least O((N+1)!),
 * if there are relatively few collisions.  On my test system, the default
 * of N=9 is fast, while there is a slight pause for N=10, and a long wait
 * for N=11.</p>
 *
 * @author Vincent A. Cicirello, https://www.cicirello.org/
 */
public class PermutationHashCodes {
	public static void main(String[] args) {
		
		System.out.println("If you are running this example program, make");
		System.out.println("sure you also read the source code and comments.");
		System.out.println("Output of example program in isolation of the");
		System.out.println("source and comments won't tell you much.");
		
		System.out.println();
		System.out.println("Purpose: Explore whether current Permutation.hashCode is a good choice.");
		System.out.println("Command line arguments: Two optional command line arguments, both integers.");
		System.out.println("    First argument is max permutation length (default is 9).");
		System.out.println("    Second argument is max permutation length to exhaustively print");
		System.out.println("    (default is 0).");
		System.out.println("Function: For each permutation length, up to 8 (or max specified on command");
		System.out.println("    line), iterates over all permutations of that length, computing number");
		System.out.println("    of hashCode collisions, i.e., number of permutations that share a");
		System.out.println("    hashCode with another permutation of that length.  It does not count ");
		System.out.println("    collisions across different length permutations.  Output includes ");
		System.out.println("    length, collision count, and percentage of permutations of a given");
		System.out.println("    length that collide (i.e., if N is length, the collision percentage");
		System.out.println("    is: (#collisions / N!).");
		System.out.println("WARNING: If N is the first command line argument, runtime is at least O((N+1)!),");
		System.out.println("    if there are relatively few collisions.  On my test system, the default");
		System.out.println("    of N=9 is fast, while there is a slight pause for N=10, and a long wait");
		System.out.println("    for N=11.");
		
		final int MAX_L = args.length > 0 ? Integer.parseInt(args[0]) : 9;
		final int PRINT_LIMIT = args.length > 1 ? Integer.parseInt(args[1]) : 0;
		
		long fact = 1;
		
		for (int i = 1; i <= MAX_L; i++) {
			fact = fact * (long)i;
			
			System.out.println();
			
			// Let p be a Permutation of length i.  Permutation 0 has elements in increasing order.
			Permutation p = new Permutation(i, 0);
			
			HashSet<Integer> collisionCheck = new HashSet<Integer>((int)(fact/0.75 + 1));
			int collisionCount = 0;
			
			if (i <= PRINT_LIMIT) {
				System.out.println("Outputting all permutations of length " + i + " and their hashcodes.");
			}
			
			// Iterate over all permutations of length i.
			for (Permutation p1 : p) {
				int hash = p1.hashCode();
				if (collisionCheck.contains(hash)) collisionCount++;
				else collisionCheck.add(hash);
				
				if (i <= PRINT_LIMIT) {
					System.out.print(p1);
					System.out.print("\tHash: ");
					System.out.println(hash);
				}
			}
			double pct = 1.0 * collisionCount / fact;
			System.out.printf("Length: %d, #collisions: %d, Collision Percentage: %.5f\n", i, collisionCount, pct);
		}
	}
}
