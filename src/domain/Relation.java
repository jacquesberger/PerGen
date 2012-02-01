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

package domain;

/**
 * Represents a relation between the entity containing this object and another
 * entity.
 */
public class Relation {

    /**
     * The entity.
     */
    private EntityInformations entity;

    /**
     * The relation type.
     */
    private RelationType.Type type;

    /**
     * May be zero.
     */
    private boolean maybeZero;

    /**
     * Is many to many ?
     */
    private boolean isManyToMany;

    /**
     * The name of the table generated for a many-to-many relation.
     * Will be null if isManyToMany is false.
     */
    private String nameOfLinkTable;

    /**
     * Creates a new instance.
     * @param entityInfo The entity.
     * @param relationType The relation type.
     * @param canBeZero Can be zero.
     * @param manyToMany Indicates if the relation is a "Many to many"
     *                   relation.
     * @param tableLink The name of link table if many-to-many.
     */
    public Relation(final EntityInformations entityInfo,
                    final RelationType.Type relationType,
                    final boolean canBeZero,
                    final boolean manyToMany,
                    final String tableLink) {
        entity = entityInfo;
        type = relationType;
        maybeZero = canBeZero;
        isManyToMany = manyToMany;
        nameOfLinkTable = tableLink;
    }

    /**
     * Get the entity.
     * @return The entity.
     */
    public final EntityInformations getEntity() {
        return entity;
    }

    /**
     * May be zero.
     * @return true if the relation can be zero, false otherwise.
     */
    public final boolean isMaybeZero() {
        return maybeZero;
    }

    /**
     * Get the relation type.
     * @return The relation type.
     */
    public final RelationType.Type getType() {
        return type;
    }

    /**
     * Indicates if the relation is Many to Many.
     * @return true if many-to-many, false otherwise.
     */
    public final boolean isManyToMany() {
        return isManyToMany;
    }

    /**
     * Gets the name of the link table for a many-to-many relation.
     * @return The name of the link table, null if non applicable.
     */
    public final String getNameOfLinkTable() {
        return nameOfLinkTable;
    }
    
    public String getEntitySqlName() {
	return entity.getSqlName();
    }
}
