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

package org.jberger.pergen.explorers;

import java.util.ArrayList;

import org.jberger.pergen.node.AEntityDefinition;
import org.jberger.pergen.node.AManyRelationDefinition;
import org.jberger.pergen.node.AMayBeZero;
import org.jberger.pergen.node.AOneRelationDefinition;
import org.jberger.pergen.node.ARelationInnerDefinition;

import org.jberger.pergen.domain.RawRelation;
import org.jberger.pergen.domain.RelationType;

import org.jberger.pergen.analysis.DepthFirstAdapter;

/**
 * Collects the raw information about the relations between the entities.
 * No semantic analysis is done in this class.
 */
public class RelationExplorer extends DepthFirstAdapter {

    /**
     * The list of all relations explored.
     */
    private ArrayList<RawRelation> relations;

    /**
     * The current relation.
     */
    private RawRelation currentRelation;

    /**
     * The current entity.
     */
    private String currentEntity;

    /**
     * Creates a new instance.
     */
    public RelationExplorer() {
        super();
        relations = new ArrayList<RawRelation>();
    }

    /**
     * Before analysing an entity.
     * @param node The node.
     */
    @Override
    public final void inAEntityDefinition(final AEntityDefinition node) {
        currentEntity = node.getIdentifier().getText();
    }

    /**
     * After analysing an entity.
     * @param node The node.
     */
    @Override
    public final void outAEntityDefinition(final AEntityDefinition node) {
        currentEntity = null;
    }

    /**
     * Before analysing a relation definition.
     * @param node The node.
     */
    @Override
    public final void inARelationInnerDefinition(final
                                                 ARelationInnerDefinition node)
                                                                               {
        currentRelation = new RawRelation();
        currentRelation.setFromEntity(currentEntity);
    }

    /**
     * After analysing a relation definition.
     * @param node The node.
     */
    @Override
    public final void outARelationInnerDefinition(final
                                                  ARelationInnerDefinition node)
                                                                               {
        relations.add(currentRelation);
        currentRelation = null;
    }

    /**
     * Before analysing a "one" relation.
     * @param node The node.
     */
    @Override
    public final void inAOneRelationDefinition(final
                                               AOneRelationDefinition node) {
        currentRelation.setToEntity(node.getIdentifier().getText());
        currentRelation.setType(RelationType.Type.ONE);
    }

    /**
     * Before analysing a "many" relation.
     * @param node The node.
     */
    @Override
    public final void inAManyRelationDefinition(final
                                                AManyRelationDefinition node) {
        currentRelation.setToEntity(node.getIdentifier().getText());
        currentRelation.setType(RelationType.Type.MANY);
    }

    /**
     * Before analysing a "may be zero" definition.
     * @param node The node.
     */
    @Override
    public final void inAMayBeZero(final AMayBeZero node) {
        currentRelation.setMayBeZero(true);
    }

    /**
     * Return all the raw relations.
     * @return The relations.
     */
    public final ArrayList<RawRelation> getRelations() {
        return relations;
    }
}
