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
 * Exception raised when an entity is defined more than once.
 */
public class EntityAlreadyDefinedException extends RuntimeException {

    /**
     * For serialization.
     */
    static final long serialVersionUID = 200710270152666L;

    /**
     * Create a new Exception.
     * @param entityName The name of the entity defined more than once.
     */
    public EntityAlreadyDefinedException(final String entityName) {
        super("The entity " + entityName + " is already defined.");
    }
}
