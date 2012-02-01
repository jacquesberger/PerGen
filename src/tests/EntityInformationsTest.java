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

package tests;

import domain.EntityInformations;
import domain.FieldInformations;
import domain.FieldType;
import junit.framework.TestCase;

/**
 * Test class for EntityInformations.
 */
public class EntityInformationsTest extends TestCase {

    /**
     * Test the getOriginalName method.
     */
    public final void testGetOriginalName() {
        EntityInformations entity = new EntityInformations("Rain");

        assertTrue(entity.getOriginalName().equals("Rain"));
        assertFalse(entity.getOriginalName().equals("Object"));
    }

    /**
     * Test the addField and isFieldDefined methods.
     */
    public final void testAddFieldAndIsDefined() {
        EntityInformations entity = new EntityInformations("Rain");
        FieldInformations field = new FieldInformations("field",
                                     FieldType.Type.INTEGER, true);

        entity.addField(field);
        assertTrue(entity.isFieldDefined("field"));
        assertFalse(entity.isFieldDefined("broken"));
    }

    /**
     * Test the getField method.
     */
    public final void testGetField() {
        EntityInformations entity = new EntityInformations("Rain");
        FieldInformations field = new FieldInformations("field",
                                     FieldType.Type.INTEGER, true);

        entity.addField(field);
        assertTrue(entity.getField("field") == field);
        assertNull(entity.getField("broken"));
    }

    /**
     * Test the getFields method.
     */
    public final void testGetFields() {
        final int fieldCount = 3;
        EntityInformations entity = new EntityInformations("entity");
        entity.addField(new FieldInformations("field1",
                                              FieldType.Type.INTEGER, true));
        entity.addField(new FieldInformations("field2",
                                              FieldType.Type.INTEGER, true));
        entity.addField(new FieldInformations("field3",
                                              FieldType.Type.INTEGER, true));
        assertTrue(entity.getFields().size() == fieldCount);
    }
}
