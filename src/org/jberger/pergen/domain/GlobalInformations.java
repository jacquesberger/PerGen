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

package org.jberger.pergen.domain;

import java.util.Collection;
import java.util.Hashtable;

/**
 * Contains all the entities.
 */
public class GlobalInformations {

    /**
     * Contains all the entities.
     * Key: Entity name.
     * Value: Entity object.
     */
    private Hashtable<String, Entity> entities;

    /**
     * Create a new instance.
     */
    public GlobalInformations() {
        entities = new Hashtable<String, Entity>();
    }

    /**
     * Add an entity.
     * @param entity The entity to add. If this entity already exists, it
     *               will be overidden by the new one.
     */
    public final void addEntity(final Entity entity) {
        entities.put(entity.getOriginalName(), entity);
    }

    /**
     * Determines if an entity is already defined.
     * @param entityName The name of the entity to check.
     * @return true if already defined, false otherwise.
     */
    public final boolean isEntityDefined(final String entityName) {
        return entities.containsKey(entityName);
    }

    /**
     * Get an entity.
     * @param entityName The name of the entity to retrieve.
     * @return The entity if found, null otherwise.
     */
    public final Entity getEntity(final String entityName) {
        return entities.get(entityName);
    }

    /**
     * Get all the entities.
     * @return A collection of entities.
     */
    public final Collection<Entity> getEntities() {
        return entities.values();
    }
}
