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
package tests;

import org.jberger.pergen.codeproviders.Java6Provider;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tests.mocks.MockFileWriter;

public class Java6ProviderTest {
    
    @Test
    public void testProvideDefaultConstructor() throws Exception {
        MockFileWriter fileWriter = new MockFileWriter();
        Java6Provider.provideDefaultConstructor(fileWriter, "Book");
        assertEquals("    public Book() {\n    }\n\n", fileWriter.getWrittenData());
    }
}
