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

package exceptions;

/**
 * Exception raised when a field is defined more than once in an entity.
 * Each field name must be unique in an entity.
 */
public class FieldAlreadyDefinedException extends RuntimeException {

    /**
     * For serialization.
     */
    static final long serialVersionUID = 200711030227666L;

    /**
     * Creates the exception.
     * @param fieldName The name of the field in violation.
     * @param entityName The name of the entity containing the field.
     */
    public FieldAlreadyDefinedException(final String fieldName,
                                        final String entityName) {
        super("The field " + fieldName + " is defined more than once in "
              + "the entity " + entityName + ".");
    }
}
