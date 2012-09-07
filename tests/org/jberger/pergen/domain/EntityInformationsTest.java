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

import static org.junit.Assert.*;
import org.junit.Test;

public class EntityInformationsTest {

    @Test
    public final void testGetOriginalName() {
        String entityName = "Rain";
        EntityInformations entity = new EntityInformations(entityName);
        assertEquals(entityName, entity.getOriginalName());
    }

    @Test
    public final void testIsDefined() {
        EntityInformations entity = new EntityInformations("Rain");
        String fieldName = "field";
        Field field = new Field(fieldName,
                                     FieldType.Type.INTEGER, true);
        entity.addField(field);
        assertTrue(entity.isFieldDefined(fieldName));
    }

    @Test
    public final void testGetField() {
        EntityInformations entity = new EntityInformations("Rain");
        String fieldName = "field";
        Field field = new Field(fieldName,
                                     FieldType.Type.INTEGER, true);

        entity.addField(field);
        assertSame(field, entity.getField(fieldName));
    }
    
    @Test
    public final void testGetUndefinedField() {
        EntityInformations entity = new EntityInformations("Rain");
        assertNull(entity.getField("broken"));
    }
}
