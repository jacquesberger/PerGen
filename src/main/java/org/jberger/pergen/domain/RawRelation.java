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

/**
 * Contains the raw information about a relation between two entities.
 */
public class RawRelation {

    private String fromEntity;

    /**
     * The entity to which the relation aims.
     */
    private String toEntity;

    /**
     * Indicates if the relation may be null.
     */
    private boolean mayBeZero = false;

    /**
     * Indicates the relation type of this relation.
     */
    private RelationType.Type type;

    /**
     * Get the "from" entity.
     * @return The "from" entity.
     */
    public final String getFromEntity() {
        return fromEntity;
    }

    /**
     * Set the "from" entity.
     * @param from The "from" entity value.
     */
    public final void setFromEntity(final String from) {
        this.fromEntity = from;
    }

    /**
     * Indicates if the relation may be null.
     * @return true if the relation can be null, false otherwise.
     */
    public final boolean isMayBeZero() {
        return mayBeZero;
    }

    /**
     * Set the "may be zero" property.
     * @param canBeZero true if the relation can be null, false otherwise.
     */
    public final void setMayBeZero(final boolean canBeZero) {
        mayBeZero = canBeZero;
    }

    /**
     * Get the "to" entity.
     * @return The "to" entity value.
     */
    public final String getToEntity() {
        return toEntity;
    }

    /**
     * Set the "to" entity.
     * @param to The "to" entity value.
     */
    public final void setToEntity(final String to) {
        toEntity = to;
    }

    /**
     * Get the relation type.
     * @return The relation type.
     */
    public final RelationType.Type getType() {
        return type;
    }

    /**
     * Set the relation type between the two entities.
     * @param relationType The relation type.
     */
    public final void setType(final RelationType.Type relationType) {
        type = relationType;
    }
}
