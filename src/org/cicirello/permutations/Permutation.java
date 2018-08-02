/*
 * Copyright 2005, 2010, 2014-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of package org.cicirello.permutations.
 *
 * Java package org.cicirello.permutations is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * Java package org.cicirello.permutations is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java package org.cicirello.permutations.  If not, 
 * see <http://www.gnu.org/licenses/>.
 *
 */
package org.cicirello.permutations;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Iterator;


/**
 * Representation of a permutation of the integers from 0 to N-1, inclusive.
 * This class provides the functionality to generate random permutations, and to
 * manipulate permutations in a variety of ways.
 * 
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a> 
 * @version 2.18.8.1
 * @since 1.0
 */
public final class Permutation implements Serializable, Iterable<Permutation>
{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Initializes a random permutation of n integers.  Uses
	 * java.util.concurrent.ThreadLocalRandom as the source of efficient random number generation.
     * @param n the length of the permutation
     */
	public Permutation(int n) {
		this(n, ThreadLocalRandom.current());
	}
 
    /**
     * Initializes a random permutation of n integers.
     * @param n the length of the permutation
	 * @param r A source of randomness.
     */
    public Permutation(int n, SplittableRandom r) {
        permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;   
        }
        scramble(r);
    }
	
	/**
     * Initializes a random permutation of n integers.
     * @param n the length of the permutation
	 * @param r A source of randomness.
     */
    public Permutation(int n, Random r) {
        permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;   
        }
        scramble(r);
    }
	
	/**
	* Initializes a specific permutation from an integer in mixed radix form representing the chosen
	* permutation.  See the toInteger() method which can be used to generate this value for a given
	* permutation.  The n! permutations of the integers from 0 to n-1 are mapped to the integers from
	* 0..(n!-1).
	* @param n The length of the permutation.
	* @param value The integer value of the permutation in the interval: 0..(n!-1).
	*/
	public Permutation(int n, int value) {
		permutation = new int[n];
		for (int i = 0; i < n; i++) {
            permutation[i] = i;   
        }
		for (int i = 0; i < n-1; i++) {
			int j = i + value % (n-i);
			int temp = permutation[j];
			for (int k = j; k > i; k--) {
				permutation[k] = permutation[k-1];
			}
			permutation[i] = temp;
			value = value / (n-i);
		}
	}
	
	/**
	 * Initializes a permutation of n integers to be identical to the elements of an array.
     * @param p An array of integers. Each of the integers in the interval [0, p.length) must occur exactly one time each.
	 */
	public Permutation(int[] p) {
		boolean[] inP = new boolean[p.length];
		for (int e : p) {
			if (e < 0 || e >= p.length) throw new IllegalArgumentException("Elements of p must be in interval [0, p.length)");
			if (inP[e]) throw new IllegalArgumentException("Duplicate elements of p are not allowed.");
			inP[e] = true;
		}
		permutation = p.clone();
	}
	
    /**
     * Initializes a permutation of n integers to be identical to a given
     * permutation.
     * @param p the given permutation.
     */
    public Permutation(Permutation p) {
        permutation = new int[p.permutation.length];
        System.arraycopy(p.permutation, 0, permutation, 0, p.permutation.length);
    }
    
    /**
     * Initializes a permutation of integers to be identical to a subset of a given
     * permutation.  Note: if the desired length is less than the source permutation,
	 * then the resulting permutation will not contain all of the integers in [0,n).
     * @param p the given permutation.
     * @param length size of sub-permutation
     */
    public Permutation(Permutation p, int length) {
		if (length > p.permutation.length) length = p.permutation.length;
        permutation = new int[length];
        System.arraycopy(p.permutation, 0, permutation, 0, length);
    }
	
	
	/**
	* Generates a unique integer representing the permutation.  Maps the permutations of the integers, 0..(N-1), to 
	* the integers, 0..(N!-1), using a mixed radix representation.
	* @return a mixed radix representation of the permutation
	*/
	public int toInteger() {
		int N = permutation.length;
		int[] index = new int[N];
		for (int i = 0; i < N; i++) index[i] = i;
		int result = 0;
		int multiplier = 1;
		int factor = N;
		for (int i = 0; i < N-1; i++) {
			result += multiplier * index[permutation[i]];
			for (int j = permutation[i]; j < N; j++) {
				index[j]--;
			}
			multiplier *= factor;
			factor--;
		}
		return result;
	}
     
	 /**
	 * Computes the inverse of the permutation.
	 *
	 * @return The inverse of the permutation, such that for all i, if
	 * pi(i) = j, then inv(j) = i
	 */
    public int[] getInverse() {
		int[] inverse = new int[permutation.length];
		for (int i = 0; i < permutation.length; i++) {
			inverse[permutation[i]] = i;
		}
		return inverse;
	}  
    
	/**
     * Randomly shuffles the permutation. Uses
	 * java.util.concurrent.ThreadLocalRandom as 
	 * the source of efficient random number generation.
     */
    public void scramble() {
		scramble(ThreadLocalRandom.current());
	}
	
    /**
     * Randomly shuffles the permutation.
	 * @param r a source of randomness.
     */
    public void scramble(Random r) {
		for (int i = permutation.length - 1; i > 0; i--) {
			int j = r.nextInt(i+1);
			if (i != j) {
				swap(i,j);
			}
		}
	}
    
	/**
     * Randomly shuffles the permutation.
	 * @param r a source of randomness.
     */
    public void scramble(SplittableRandom r) {
		for (int i = permutation.length - 1; i > 0; i--) {
			int j = r.nextInt(i+1);
			if (i != j) {
				swap(i,j);
			}
		}
	}
	
	/**
     * Randomly shuffles a segment. Uses
	 * java.util.concurrent.ThreadLocalRandom as 
	 * the source of efficient random number generation.
     * @param i endpoint of the segment
     * (precondition: 0 &le; i &lt; length())
     * @param j endpoint of the segment
     * (precondition: 0 &le; j &lt; length())
     */
    public void scramble(int i, int j) {
		scramble(i, j, ThreadLocalRandom.current());
	}
        
    /**
     * Randomly shuffles a segment.
     * @param i endpoint of the segment
     * (precondition: 0 &le; i &lt; length())
     * @param j endpoint of the segment
     * (precondition: 0 &le; j &lt; length())
	 * @param r source of randomness
     */
    public void scramble(int i, int j, Random r) {
		if (i==j) { return; }
        if (i > j) {
            i ^= j;
            j ^= i;
            i ^= j;
        }
		boolean changed = false;
		for (int k = j; k > i + 1; k--) {
			int l = i + r.nextInt(k-i+1);
			if (l != k) {
				swap(l,k);
				changed = true;
			}
		}
		if (!changed || r.nextBoolean()) {
			swap(i,i+1);
		}
    }
	
	/**
     * Randomly shuffles a segment.
     * @param i endpoint of the segment
     * (precondition: 0 &le; i &lt; length())
     * @param j endpoint of the segment
     * (precondition: 0 &le; j &lt; length())
	 * @param r source of randomness
     */
    public void scramble(int i, int j, SplittableRandom r) {
		if (i==j) { return; }
        if (i > j) {
            i ^= j;
            j ^= i;
            i ^= j;
        }
		boolean changed = false;
		for (int k = j; k > i + 1; k--) {
			int l = i + r.nextInt(k-i+1);
			if (l != k) {
				swap(l,k);
				changed = true;
			}
		}
		if (!changed || r.nextBoolean()) {
			swap(i,i+1);
		}
    }
	
    /**
     * Retrieves the i'th integer of the permutation.
     * @param i the index of the integer to retrieve.
     * (precondition: 0 &le; i &lt; length())
     * @return the integer in the i'th position.
     */
    public int get(int i) {
        return permutation[i];
    }
    
    /**
     * Retrieves the length of the permutation.
     * @return length of the permutation
     */
    public int length() {
        return permutation.length;   
    }
    
    /**
     * Swaps 2 integers in the permutation.
     * @param i position of first to swap
     * (precondition: 0 &le; i &lt; length() &and; i != j)
     * @param j the position of the second to swap
     * (precondition: 0 &le; j &lt; length() &and; i != j)
     */
    public void swap(int i, int j) {
        if (i==j) return;
        // swapping 2 integers using 3 exclusive-or's
        permutation[i] ^= permutation[j];
        permutation[j] ^= permutation[i];
        permutation[i] ^= permutation[j];
    }
    
    /**
     * Reverses the order of the elements in the permutation.
     */
    public void reverse() {
    	for (int i = 0, j = permutation.length-1; i < j; i++, j--) {
    		swap(i, j);
    	}
    }
	
	/**
	 * Reverses the order of the elements of a subrange of  the permutation.
	 * @param i position of first index
     * (precondition: 0 &le; i &lt; length() &and; i != j)
     * @param j the position of the second index
     * (precondition: 0 &le; j &lt; length() &and; i != j)
	 */
	public void reverse(int i, int j) {
		if (i > j) {
            i ^= j;
            j ^= i;
            i ^= j;
        }
		for ( ; i < j; i++, j--) {
			swap(i, j);
		}
	}
    
    /**
     * Removes integer from one position and then inserts it into a
     * a new position shifting the rest of the permutation as necessary.
     * @param i position of integer to remove and insert
     * (precondition: 0 &le; i &lt; length())
     * @param j the position of the insertion point
     * (precondition: 0 &le; j &lt; length())
     */
    public void removeAndInsert(int i, int j) {
        if (i < j) {
            int n = permutation[i];
            System.arraycopy(permutation, i+1, permutation, i, j-i);
            permutation[j] = n;
        } else if (i > j) {
            int n = permutation[i];
            System.arraycopy(permutation, j, permutation, j+1, i-j);
            permutation[j] = n;
        }
    }
    
	/**
	* Circular rotation of permutation (to the left).
	* @param numPositions Number of positions to rotate.
	*/
	public void rotate(int numPositions) {
		if (numPositions >= permutation.length || numPositions < 0) numPositions = Math.floorMod(numPositions, permutation.length);
		if (numPositions==0) return;
		int[] temp = new int[numPositions];
		System.arraycopy(permutation, 0, temp, 0, numPositions);
		System.arraycopy(permutation, numPositions, permutation, 0, permutation.length-numPositions);
		System.arraycopy(temp, 0, permutation, permutation.length-numPositions, numPositions);
	}
    
    /**
     * Removes a sub-array of integers from one position and then inserts it into a
     * a new position shifting the rest of the permutation as necessary.
     * @param i position of first integer in sub-array to remove and insert
     * (precondition: 0 &le; i &lt; length())
     * @param size the length of the sub-array
     * (precondition: size + i &lt; length() and size + j - 1 &lt; length())
     * @param j the position of the insertion point
     * (precondition: 0 &le; j &lt; length())
     */
    public void removeAndInsert(int i, int size, int j) {
        if ((size == 0) || (i == j)) {
            return;
        } else if (size == 1) {
            removeAndInsert(i, j);
        } else if (i > j) {
			int[] temp = new int[i-j];
			System.arraycopy(permutation, j, temp, 0, i-j);
			System.arraycopy(permutation, i, permutation, j, size);
			System.arraycopy(temp, 0, permutation, j+size, i-j);
		} else if (i < j) {
			int[] temp = new int[size];
            System.arraycopy(permutation, i, temp, 0, size);
            System.arraycopy(permutation, i+size, permutation, i, j-i);
            System.arraycopy(temp, 0, permutation, j, size);
		}
    }
    
    /**
     * Sets the integer located in a given position.
     * Warning: be careful... does not try to enforce unique values.
     * @param i index of the position
     * (precondition: 0 &le; i &lt; length())
     * @param value the integer you want in the given position
     */
    public void set(int i, int value) {
        permutation[i] = value;
    }
	
	/**
	 * Returns an Iterator over all Permutations the length of this Permutation.  Iteration begins at this Permutation.
	 * This Iterator does not iterate over the integers within the Permutation.
	 * If you do need to iterate over all permutations of a given length, then this method is much more efficient than
	 * using the {@link #Permutation(int,int)} constructor repeatedly incrementing the value passed for the second parameter.
	 * 
	 * @since 2.0
	 * @return an Iterator
	 */
	@Override
	public Iterator<Permutation> iterator() {
		return new PermutationIterator(this);
	}
    
	/**
	* Creates a String representing the permutation.
	* @return a space separated sequence of the permutation's elements
	*/
	@Override
    public String toString() {
        String permS = "";
        for (int i : permutation) {
            permS += (i + " ");   
        }
        return permS;
    }
	
	/**
	* Equality test: Two permutations are equal if they are of
	* the same length and contain the same elements in the same order.
	* @param other the permutation to which to compare
	* @return true if this is equal to other, and false otherwise
	*/
	@Override
	public boolean equals(Object other) {
		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof Permutation)) return false;
		Permutation o = (Permutation)other;
		if (permutation.length != o.permutation.length) return false;
		for (int i = 0; i < permutation.length; i++) {
			if (permutation[i] != o.permutation[i]) return false;
		}
		return true;
	}
	
	/**
	* Uses Java's Arrays class's method for generating a hashCode from an array of ints.
	* @return a hashCode for the permutation
	*/
	@Override
	public int hashCode() {
		return Arrays.hashCode(permutation);
	}
    
    private final int[] permutation;
}
