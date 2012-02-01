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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * Contains all the information about an entity.
 */
public class EntityInformations {

    /**
     * The original name of the entity, as written in the input file.
     */
    private String originalName;

    /**
     * The sql name of the entity.
     */
    private String sqlName;

    /**
     * The code name of the entity.
     */
    private String codeName;

    /**
     * The list of all unicity constraints.
     */
    private ArrayList<UnicityConstraint> unicityList;

    /**
     * List of fields in the entity.
     */
    private Hashtable<String, FieldInformations> fields;

    /**
     * Integrity constraints.
     */
    private ArrayList<Relation> relations;

    /**
     * Create a new instance.
     * @param name The original name of the entity.
     */
    public EntityInformations(final String name) {
        originalName = name;
        fields = new Hashtable<String, FieldInformations>();
        unicityList = new ArrayList<UnicityConstraint>();
        relations = new ArrayList<Relation>();
    }

    /**
     * Get the original name of the entity.
     * @return The original name.
     */
    public final String getOriginalName() {
        return originalName;
    }

    /**
     * Add a field to the entity. If a field of the same name is already
     * in the entity, the first one will be overidden.
     * @param field The field to add.
     */
    public final void addField(final FieldInformations field) {
        fields.put(field.getOriginalName(), field);
    }

    /**
     * Indicates if the field is already defined in this entity.
     * @param fieldName The name of the field.
     * @return true if already defined, false otherwise.
     */
    public final boolean isFieldDefined(final String fieldName) {
        return fields.containsKey(fieldName);
    }

    /**
     * Get a field from the orginal name.
     * @param originalFieldName The original name of the field.
     * @return The field if found, null otherwise.
     */
    public final FieldInformations getField(final String originalFieldName) {
        return fields.get(originalFieldName);
    }

    /**
     * Add a unicity constraint to the entity.
     * @param constraint The constraint to add.
     */
    public final void addUnicityConstraint(final UnicityConstraint constraint) {
        unicityList.add(constraint);
    }

    /**
     * Add a relation constraint to the entity.
     * @param relation The relation to add.
     */
    public final void addRelation(final Relation relation) {
        relations.add(relation);
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String newSqlName) {
        sqlName = newSqlName;
    }

    /**
     * Get all the fields.
     * @return A collection of fields.
     */
    public final Collection<FieldInformations> getFields() {
        return fields.values();
    }

    /**
     * Get all the relations.
     * @return All the relations.
     */
    public final Collection<Relation> getRelations() {
        return relations;
    }

    /**
     * Get all the unicity constraints.
     * @return All the unicity constraints.
     */
    public final Collection<UnicityConstraint> getUnicityConstraints() {
        return unicityList;
    }

    /**
     * Get the code name of the entity.
     * @return The code name.
     */
    public final String getCodeName() {
        return codeName;
    }

    /**
     * Set the code name of the entity.
     * @param newName The new code name.
     */
    public final void setCodeName(final String newName) {
        codeName = newName;
    }

    /**
     * Indicates if the entity has a relation of type MANY.
     * @return true if the entity has a MANY relation, false otherwise.
     */
    public final boolean hasAManyRelation() {
        for (Relation relation : relations) {
            if (relation.getType() == RelationType.Type.MANY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates if the entity has a field of type DATE.
     * @return true if the entity has a DATE field, false otherwise.
     */
    public final boolean hasADateField() {
        for (FieldInformations field : fields.values()) {
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all the many-to-many relations related to this entity.
     * @return The list of many-to-many relations.
     */
    public final ArrayList<Relation> getAllManyToManyRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (relation.isManyToMany()) {
                returnList.add(relation);
            }
        }
        return returnList;
    }

    /**
     * Get all the many-to-one relations related to this entity.
     * @return The list of many-to-one relations.
     */
    public final ArrayList<Relation> getAllManyToOneRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (relation.getType() == RelationType.Type.ONE) {
                returnList.add(relation);
            }
        }
        return returnList;
    }

    /**
     * Get all the one-to-many relations related to this entity.
     * @return The list of one-to-many relations.
     */
    public final ArrayList<Relation> getAllOneToManyRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (!relation.isManyToMany()
                && relation.getType() == RelationType.Type.MANY) {
                returnList.add(relation);
            }
        }
        return returnList;
    }

    /**
     * Get all the MANY relations related to this entity.
     * @return The list of MANY relations.
     */
    public final ArrayList<Relation> getAllMANYRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (relation.getType() == RelationType.Type.MANY) {
                returnList.add(relation);
            }
        }
        return returnList;
    }
}
