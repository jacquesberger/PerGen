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
 * Raised when an entity refers to a non existing entity.
 */
public class EntityNotDefinedException extends RuntimeException {

    /**
     * For serialization.
     */
    static final long serialVersionUID = 200711270106666L;

    /**
     * Creates a new instance.
     * @param fromEntity The name of the entity containing the false
     *                   definition.
     * @param entity The name of the not defined entity.
     */
    public EntityNotDefinedException(final String fromEntity,
                                     final String entity) {
        super("The entity " + entity + " specified in " + fromEntity
              + " does not exist.");
    }
}
