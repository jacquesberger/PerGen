/* Copyright 2007 Jacques Berger

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.jberger.pergen.exceptions;

/**
 * Raised when a relation is not defined in both entities.
 */
public class BidirectionalRelationException extends RuntimeException {

    /**
     * For serialization.
     */
    static final long serialVersionUID = 200711282343666L;

    /**
     * Creates a new instance.
     * @param firstEntity One of the entities.
     * @param secondEntity The other entity involved.
     */
    public BidirectionalRelationException(final String firstEntity,
                                          final String secondEntity) {
        super("The relation between the entity " + firstEntity
              + " and " + secondEntity + " must be defined in both entities.");
    }
}
