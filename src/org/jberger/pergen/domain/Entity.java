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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import org.jberger.pergen.transformers.JavaTransformer;
import org.jberger.pergen.transformers.SqlTransformer;

public class Entity {

    private String originalName;
    private String sqlName;
    private String javaName;
    private ArrayList<UnicityConstraint> unicityList;
    private Hashtable<String, Field> fields;
    private ArrayList<Relation> relations;

    public Entity(final String name) {
        originalName = name;
        fields = new Hashtable<String, Field>();
        unicityList = new ArrayList<UnicityConstraint>();
        relations = new ArrayList<Relation>();
        
        sqlName = SqlTransformer.transformIdentifier(originalName);
        javaName = JavaTransformer.snakeCaseIdentifierToPascalCase(originalName);
    }

    public final String getOriginalName() {
        return originalName;
    }

    public final void addField(final Field field) {
        fields.put(field.getOriginalName(), field);
    }

    public final boolean isFieldDefined(final String fieldName) {
        return fields.containsKey(fieldName);
    }

    public final Field getField(final String originalFieldName) {
        return fields.get(originalFieldName);
    }

    public final void addUnicityConstraint(final UnicityConstraint constraint) {
        unicityList.add(constraint);
    }

    public final void addRelation(final Relation relation) {
        relations.add(relation);
    }

    public String getSqlName() {
        return sqlName;
    }
    
    public final Collection<Field> getFields() {
        return fields.values();
    }

    public final Collection<Relation> getRelations() {
        return relations;
    }

    public final Collection<UnicityConstraint> getUnicityConstraints() {
        return unicityList;
    }

    public final String getJavaName() {
        return javaName;
    }

    public final boolean hasAManyRelation() {
        for (Relation relation : relations) {
            if (relation.getType() == RelationType.Type.MANY) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasADateField() {
        for (Field field : fields.values()) {
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                return true;
            }
        }
        return false;
    }

    public final ArrayList<Relation> getAllManyToManyRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (relation.isManyToMany()) {
                returnList.add(relation);
            }
        }
        return returnList;
    }

    public final ArrayList<Relation> getAllManyToOneRelations() {
        ArrayList<Relation> returnList = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if (relation.getType() == RelationType.Type.ONE) {
                returnList.add(relation);
            }
        }
        return returnList;
    }

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