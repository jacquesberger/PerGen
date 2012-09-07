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
import java.util.Hashtable;

import org.jberger.pergen.exceptions.BidirectionalRelationException;
import org.jberger.pergen.exceptions.EntityNotDefinedException;
import org.jberger.pergen.exceptions.MultipleRelationException;
import org.jberger.pergen.exceptions.NotSupportedException;
import org.jberger.pergen.generators.SQLGenerator;

/**
 * This class analyses the raw relations and constructs them correctly.
 */
public final class RelationAnalyzer {

    /**
     * To avoid instanciation.
     */
    private RelationAnalyzer() {
    }

    /**
     * Performs the analysis which consist of validating and building the
     * real relations in the GlobalInformations class.
     * @param infos The global informations about the entities.
     * @param relations The raw relations.
     */
    public static void analyse(final GlobalInformations infos,
                               final ArrayList<RawRelation> relations) {
        entityExistenceVerification(infos, relations);
        multipleRelationVerification(infos, relations);
        buildRelations(infos, relations);
    }

    /**
     * Verify the existance of every entities involved.
     * @param infos The global informations about the entities.
     * @param relations The raw relations.
     */
    private static void entityExistenceVerification(
                                    final GlobalInformations infos,
                                    final ArrayList<RawRelation> relations) {
        for (RawRelation relation : relations) {
            if (!infos.isEntityDefined(relation.getToEntity())) {
                throw new EntityNotDefinedException(relation.getFromEntity(),
                                                    relation.getToEntity());
            }
        }
    }

    /**
     * Verify if an entity defines multiple relations to the same entity.
     * @param infos The global informations about the entities.
     * @param relations The raw relations.
     */
    private static void multipleRelationVerification(
                                       final GlobalInformations infos,
                                       final ArrayList<RawRelation> relations) {
        Hashtable<String, ArrayList<String>> table =
                                     new Hashtable<String, ArrayList<String>>();
        for (RawRelation relation : relations) {
            ArrayList<String> relationList =
                                            table.get(relation.getFromEntity());
            if (relationList == null) {
                relationList = new ArrayList<String>();
                table.put(relation.getFromEntity(), relationList);
            }

            if (relationList.contains(relation.getToEntity())) {
                throw new MultipleRelationException(relation.getFromEntity(),
                                                    relation.getToEntity());
            } else {
               relationList.add(relation.getToEntity());
            }
        }
    }

    /**
     * Builds the relations in the entities of the global informations and
     * validate that every relation is bidirectional.
     * @param infos The global informations about the entities.
     * @param relations The raw relations.
     */
    private static void buildRelations(final GlobalInformations infos,
                                       final ArrayList<RawRelation> relations) {
        ArrayList<RawRelation> copy =
                                     (ArrayList<RawRelation>) relations.clone();
        while (copy.size() > 0) {
            RawRelation first = copy.get(0);
            copy.remove(first);
            RawRelation second = null;

            for (RawRelation thisRelation : copy) {
                if (thisRelation.getFromEntity().equals(first.getToEntity())
                    && thisRelation.getToEntity().equals(first.getFromEntity()))
                {
                    second = thisRelation;
                    break;
                }
            }

            if (second == null) {
                throw new BidirectionalRelationException(first.getFromEntity(),
                                                         first.getToEntity());
            }

            copy.remove(second);

            Entity firstEntity =
                                         infos.getEntity(first.getFromEntity());
            Entity secondEntity =
                                        infos.getEntity(second.getFromEntity());

            if (first.getType() == RelationType.Type.ONE
                && second.getType() == RelationType.Type.ONE) {
                throw new NotSupportedException("One to one relations "
                                                + "are not supported.");
            }

            boolean manyToMany = first.getType() == RelationType.Type.MANY
                                 && second.getType() == RelationType.Type.MANY;
            String linkName = null;
            if (manyToMany) {
                linkName = SQLGenerator.buildJunctionTableName(
                                                     firstEntity.getSqlName(),
                                                     secondEntity.getSqlName());
            }

            Relation firstRelation = new Relation(secondEntity, first.getType(),
                                                  first.isMayBeZero(),
                                                  manyToMany, linkName);
            firstEntity.addRelation(firstRelation);

            Relation secondRelation = new Relation(firstEntity,
                                                   second.getType(),
                                                   second.isMayBeZero(),
                                                   manyToMany, linkName);
            secondEntity.addRelation(secondRelation);
        }
    }
}
