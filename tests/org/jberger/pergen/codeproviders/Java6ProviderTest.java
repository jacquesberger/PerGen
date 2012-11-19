/*
 * Copyright 2012 Jacques Berger.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jberger.pergen.codeproviders;

import java.io.IOException;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.FieldType;
import org.jberger.pergen.tests.mock.MockFileWriter;
import static org.junit.Assert.*;
import org.junit.Test;

public class Java6ProviderTest {

    @Test
    public void testProvideDefaultConstructor() throws Exception {
        MockFileWriter fileWriter = new MockFileWriter();
        Java6Provider.provideDefaultConstructor(fileWriter, "Book");
        assertEquals("    public Book() {\n    }\n\n", fileWriter.getWrittenData());
    }

    @Test
    public void testProvidePackageDeclaration() throws IOException {
        MockFileWriter fileWriter = new MockFileWriter();
        Java6Provider.providePackageDeclaration(fileWriter, "pergen");
        assertEquals("package pergen;\n\n", fileWriter.getWrittenData());
    }

    @Test
    public void testProvidePojosFieldGetterSetter() throws IOException {
        MockFileWriter fileWriter = new MockFileWriter();
        Java6Provider.providePOJOsFieldAndGetterSetter(fileWriter, new Field("test", FieldType.Type.STRING, true));
        assertEquals("    private String test = null;\n"
                + "\n"
                + "    public String getTest() {\n"
                + "        return test;\n"
                + "    }\n"
                + "\n"
                + "    public void setTest(String newValue) {\n"
                + "        test = newValue;\n"
                + "    }\n"
                + "\n", fileWriter.getWrittenData());
    }
}
