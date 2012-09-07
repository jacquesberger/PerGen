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


import org.jberger.pergen.node.ADateDataType;
import org.jberger.pergen.node.AEntityDefinition;
import org.jberger.pergen.node.AFieldDefinition;
import org.jberger.pergen.node.AIntegerDataType;
import org.jberger.pergen.node.AMoreIdentifier;
import org.jberger.pergen.node.ARealDataType;
import org.jberger.pergen.node.AStringDataType;
import org.jberger.pergen.node.AStringLength;
import org.jberger.pergen.node.AUnicityDefinition;
import org.jberger.pergen.analysis.DepthFirstAdapter;
import org.jberger.pergen.domain.EntityInformations;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.FieldType;
import org.jberger.pergen.domain.GlobalInformations;
import org.jberger.pergen.domain.UnicityConstraint;
import org.jberger.pergen.exceptions.EntityAlreadyDefinedException;
import org.jberger.pergen.exceptions.FieldAlreadyDefinedException;
import org.jberger.pergen.exceptions.FieldNotDefinedException;

/**
 * Runs through the input file and collects the entity names and fields.
 */
public class EntityAndFieldExplorer extends DepthFirstAdapter {

    /**
     * The data structure to fill with entities and fields informations.
     */
    private GlobalInformations entitiesInfos;

    /**
     * The current data type used during the evaluation of the data type
     * of the field.
     */
    private FieldType.Type currentDataType;

    /**
     * The length of the string, set to null if the data type is not STRING or
     * also set to null if string length not specified.
     */
    private Integer stringLength;

    /**
     * Current entity during the evaluation of the entity.
     */
    private EntityInformations currentEntity;

    /**
     * Current unicity constraint during the evaluation of the constraint.
     */
    private UnicityConstraint currentUnicity;

    /**
     * Create a new instance.
     * @param infos The data structure to fill with entities informations.
     */
    public EntityAndFieldExplorer(final GlobalInformations infos) {
        super();
        entitiesInfos = infos;
    }

    /**
     * Before analysing an entity.
     * @param node The node.
     */
    @Override
    public final void inAEntityDefinition(final AEntityDefinition node) {
        String entityName = node.getIdentifier().getText();

        if (entitiesInfos.isEntityDefined(entityName)) {
            throw new EntityAlreadyDefinedException(entityName);
        }

        EntityInformations newEntity = new EntityInformations(entityName);
        entitiesInfos.addEntity(newEntity);
        currentEntity = newEntity;
    }

    /**
     * After analysing an entity definition.
     * @param node The node.
     */
    @Override
    public final void outAEntityDefinition(final AEntityDefinition node) {
        currentEntity = null;
    }

    /**
     * Before analysing a field definition.
     * @param node The node.
     */
    @Override
    public final void inAFieldDefinition(final AFieldDefinition node) {
        currentDataType = null;
        stringLength = null;
    }

    /**
     * After a field definition.
     * @param node The node.
     */
    @Override
    public final void outAFieldDefinition(final AFieldDefinition node) {
        String fieldName = node.getIdentifier().getText();

        if (currentEntity.isFieldDefined(fieldName)) {
            throw new FieldAlreadyDefinedException(fieldName,
                                               currentEntity.getOriginalName());
        }

        Field field = new Field(fieldName,
                                                        currentDataType,
                                                    node.getRequired() != null);

        if (stringLength != null) {
            field.setStringLength(stringLength);
        }

        currentEntity.addField(field);
    }

    /**
     * After evaluating the string length.
     * @param node The node.
     */
    @Override
    public final void outAStringLength(final AStringLength node) {
        stringLength = new Integer(node.getNumber().getText());
    }

    /**
     * After analysing a date data type.
     * @param node The node.
     */
    @Override
    public final void outADateDataType(final ADateDataType node) {
        currentDataType = FieldType.Type.DATE;
    }

    /**
     * After analysing an Integer data type.
     * @param node The node.
     */
    @Override
    public final void outAIntegerDataType(final AIntegerDataType node) {
        currentDataType = FieldType.Type.INTEGER;
    }

    /**
     * After analysing a real data type.
     * @param node The node.
     */
    @Override
    public final void outARealDataType(final ARealDataType node) {
        currentDataType = FieldType.Type.REAL;
    }

    /**
     * After analysing a string data type.
     * @param node The node.
     */
    @Override
    public final void outAStringDataType(final AStringDataType node) {
        currentDataType = FieldType.Type.STRING;
    }

    /**
     * Before analysing a unicity definition.
     * @param node The node.
     */
    @Override
    public final void inAUnicityDefinition(final AUnicityDefinition node) {
        currentUnicity = new UnicityConstraint();
        addFieldToUnicityConstraint(currentUnicity,
                                    node.getIdentifier().getText());
    }

    /**
     * After analysing a unicity definition.
     * @param node The node.
     */
    @Override
    public final void outAUnicityDefinition(final AUnicityDefinition node) {
        currentEntity.addUnicityConstraint(currentUnicity);
        currentUnicity = null;
    }

    /**
     * Add a field to a unicity constraint.
     * @param unicity The unicity constraint.
     * @param fieldName The field to add.
     */
    private void addFieldToUnicityConstraint(final UnicityConstraint unicity,
                                             final String fieldName) {
        Field field = currentEntity.getField(fieldName);
        if (field == null) {
            throw new FieldNotDefinedException(fieldName,
                                               currentEntity.getOriginalName());
        }

        unicity.addField(field);
    }

    /**
     * After analysing additional identifiers, which only happens during
     * the evaluation of a unicity constraint.
     * @param node The node.
     */
    @Override
    public final void outAMoreIdentifier(final AMoreIdentifier node) {
        if (currentUnicity != null) {
            addFieldToUnicityConstraint(currentUnicity,
                                        node.getIdentifier().getText());
        }
    }
}
