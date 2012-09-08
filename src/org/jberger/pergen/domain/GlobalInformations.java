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
import java.util.HashMap;
import org.jberger.pergen.exceptions.AmbiguousEntityNameException;

public class GlobalInformations {

    private HashMap<String, Entity> entities;

    public GlobalInformations() {
        entities = new HashMap<String, Entity>();
    }

    public final void addEntity(final Entity entity) throws AmbiguousEntityNameException {
        for (Entity possibleDuplicate : entities.values()) {
            if (possibleDuplicate.getJavaName().equals(entity.getJavaName())) {
                throw new AmbiguousEntityNameException(entity.getOriginalName(),
		        possibleDuplicate.getOriginalName(), entity.getJavaName());
            }
        }
        
        entities.put(entity.getOriginalName(), entity);
    }

    public final boolean isEntityDefined(final String entityName) {
        return entities.containsKey(entityName);
    }

    public final Entity getEntity(final String entityName) {
        return entities.get(entityName);
    }

    public final Collection<Entity> getEntities() {
        return entities.values();
    }
}
