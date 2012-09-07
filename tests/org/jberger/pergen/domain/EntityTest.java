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

import org.jberger.pergen.exceptions.AmbiguousFieldNameException;
import static org.junit.Assert.*;
import org.junit.Test;

public class EntityTest {

    @Test
    public final void testGetOriginalName() {
        String entityName = "Rain";
        Entity entity = new Entity(entityName);
        assertEquals(entityName, entity.getOriginalName());
    }

    @Test
    public final void testIsDefined() {
        Entity entity = new Entity("Rain");
        String fieldName = "field";
        Field field = new Field(fieldName,
                                     FieldType.Type.INTEGER, true);
        entity.addField(field);
        assertTrue(entity.isFieldDefined(fieldName));
    }

    @Test
    public final void testGetField() {
        Entity entity = new Entity("Rain");
        String fieldName = "field";
        Field field = new Field(fieldName,
                                     FieldType.Type.INTEGER, true);

        entity.addField(field);
        assertSame(field, entity.getField(fieldName));
    }
    
    @Test
    public final void testGetUndefinedField() {
        Entity entity = new Entity("Rain");
        assertNull(entity.getField("broken"));
    }
    
    @Test
    public final void testSqlName() {
        Entity entity = new Entity("entity_name");
        assertEquals("ENTITY_NAME", entity.getSqlName());
    }
    
    @Test
    public final void testJavaName() {
        Entity entity = new Entity("entity_name");
        assertEquals("EntityName", entity.getJavaName());
    }
    
    @Test(expected=AmbiguousFieldNameException.class)
    public final void testJavaNameAmbiguity() {
        Entity entity = new Entity("entity_name");
        Field field1 = new Field("field_name", FieldType.Type.STRING, false);
        Field field2 = new Field("field__name", FieldType.Type.STRING, false);
        entity.addField(field1);
        entity.addField(field2);
    }
    
    @Test(expected=AmbiguousFieldNameException.class)
    public final void testSqlNameAmbiguity() {
        fail("Non testable");
        Entity entity = new Entity("entity_name");
        Field field1 = new Field("field_name_test", FieldType.Type.STRING, false);
        Field field2 = new Field("field_name_test", FieldType.Type.STRING, false);
        entity.addField(field1);
        entity.addField(field2);
    }
}
