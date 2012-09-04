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
 * Exception raised when aa definition in an entity is refering to a field
 * that does not exist.
 */
public class FieldNotDefinedException extends RuntimeException {

    /**
     * For serialization.
     */
    static final long serialVersionUID = 200711141528666L;

    /**
     * Creates a new instance.
     * @param fieldName The name of the field that is not defined.
     * @param entityName The name of the entity.
     */
    public FieldNotDefinedException(final String fieldName,
                                    final String entityName) {
        super("The field " + fieldName + " is not defined in entity "
              + entityName + ".");
    }
}
