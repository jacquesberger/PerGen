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

public class FieldTest {

    @Test
    public final void testIsRequired() {
        Field field = new Field("Test",
                FieldType.Type.DATE,
                true);
        assertTrue(field.isRequired());
    }

    @Test
    public final void testGetOriginalName() {
        String fieldName = "Test";
        Field field = new Field(fieldName,
                FieldType.Type.DATE,
                true);
        assertEquals(fieldName, field.getOriginalName());
    }

    @Test
    public final void testGetOriginalDataType() {
        FieldType.Type type = FieldType.Type.DATE;
        Field field = new Field("Test",
                type,
                true);
        assertEquals(type, field.getOriginalDataType());
    }

    @Test
    public final void testJavaName() {
        Field field = new Field("first_name", 
                                                        FieldType.Type.STRING, 
                                                        false);
        assertEquals("firstName", field.getJavaName());
    }
    
    @Test
    public final void testSqlName() {
        Field field = new Field("first_name", 
                                                        FieldType.Type.STRING, 
                                                        false);
        assertEquals("FIRST_NAME", field.getSqlName());
    }
    
    @Test
    public final void testGetterName() {
        Field field = new Field("first_name", 
                                                        FieldType.Type.STRING, 
                                                        false);
        assertEquals("getFirstName", field.getGetterName());
    }
    
    @Test
    public final void testSetterName() {
        Field field = new Field("first_name", 
                                                        FieldType.Type.STRING, 
                                                        false);
        assertEquals("setFirstName", field.getSetterName());
    }
}
