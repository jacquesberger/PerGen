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

import org.jberger.pergen.domain.FieldInformations;
import org.jberger.pergen.domain.FieldType;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the class FieldInformations.
 */
public class FieldInformationsTest {

    /**
     * Test for the isRequired method.
     */
    @Test
    public final void testIsRequired() {
        FieldInformations field = new FieldInformations("Test",
                                                        FieldType.Type.DATE,
                                                        true);
        assertTrue(field.isRequired());
        assertFalse(!field.isRequired());
    }

    /**
     * Test for the getOriginalName method.
     */
    @Test
    public final void testGetOriginalName() {
        FieldInformations field = new FieldInformations("Test",
                                                        FieldType.Type.DATE,
                                                        true);
        assertTrue(field.getOriginalName().equals("Test"));
        assertFalse(field.getOriginalName().equals("Dummy"));
    }

    /**
     * Test for the getOriginalDataType method.
     */
    @Test
    public final void testGetOriginalDataType() {
        FieldInformations field = new FieldInformations("Test",
                                                        FieldType.Type.DATE,
                                                        true);
        assertTrue(field.getOriginalDataType()
                   == FieldType.Type.DATE);
        assertFalse(field.getOriginalDataType()
                   == FieldType.Type.STRING);
    }

    /**
     * Test for the getStringLength and setStringLength methods.
     */
    @Test
    public final void testGetSetStringLength() {
        FieldInformations field = new FieldInformations("Test",
                                             FieldType.Type.STRING, false);
        assertNull(field.getStringLength());

        final int stringLength = 60;
        field.setStringLength(stringLength);

        assertTrue(field.getStringLength().intValue() == stringLength);
    }
}
