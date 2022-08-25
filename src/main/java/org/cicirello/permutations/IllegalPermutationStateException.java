/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations;

/**
 * This is a {@link RuntimeException} that is thrown by certain methods of the {@link Permutation}
 * class to indicate that the Permutation object's state is invalid, and any subsequent calls
 * to methods on that object may be unpredictable.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, 
 * <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a> 
 */
public class IllegalPermutationStateException extends RuntimeException {
	
	/**
	 * Construct an IllegalPermutationStateException without specifying a cause.
	 *
	 * @param message A descriptive message about the exception that is thrown.
	 */
	public IllegalPermutationStateException(String message) {
		super(message);
	}
	
	/**
	 * Construct an IllegalPermutationStateException.
	 *
	 * @param message A descriptive message about the exception that is thrown.
	 * @param cause The cause of the exception.
	 */
	public IllegalPermutationStateException(String message, Throwable cause) {
		super(message, cause);
	}
}
