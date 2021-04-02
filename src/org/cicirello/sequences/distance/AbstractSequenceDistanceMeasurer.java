/*
 * Copyright 2018-2019, 2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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

import java.util.List;

/**
 * <p>Extend this abstract class to define a distance metric for 
 * permutations where distance is an integer value.</p>
 *
 * <p>If your sequences are guaranteed not to contain duplicates, and 
 * the pair of sequences is guaranteed to contain the same set of elements, and are of the same length,
 * then consider instead extending or using the classes that extend 
 * the AbstractPermutationDistanceMeasurer
 * class.  Those classes are specifically for distance between permutations of the integers from 0 to N-1.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 4.2.2021
 */
abstract class AbstractSequenceDistanceMeasurer implements SequenceDistanceMeasurer, SequenceDistanceMeasurerDouble {
	
}