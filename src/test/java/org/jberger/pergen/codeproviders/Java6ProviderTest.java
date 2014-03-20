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
import org.jberger.pergen.domain.Entity;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.FieldType;
import org.jberger.pergen.tests.mock.MockFileWriter;
import static org.junit.Assert.*;
import org.junit.Ignore;
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

    @Test
    public void testProvidePOJOsEverythingForAManyRelation() throws IOException {
        MockFileWriter fileWriter = new MockFileWriter();
        Java6Provider.providePOJOsEverythingForAManyRelation(fileWriter, "EntityCodeName");
        assertEquals("    private ArrayList<Integer> entityCodeNameList = new ArrayList<Integer>();\n"
                + "\n"
                + "    public void addEntityCodeName(Integer entityCodeNameId) {\n"
                + "        entityCodeNameList.add(entityCodeNameId);\n"
                + "    }\n"
                + "\n"
                + "    public void removeEntityCodeName(Integer entityCodeNameId) {\n"
                + "        entityCodeNameList.remove(entityCodeNameId);\n"
                + "    }\n"
                + "\n"
                + "    public ArrayList<Integer> getEntityCodeNameList() {\n"
                + "        return entityCodeNameList;\n"
                + "    }\n"
                + "\n", fileWriter.getWrittenData());
    }

    @Test
    public void testCaracterization1() throws IOException {
        String expected
                = "    public void save(final Dog dog) throws DAOException, NullityException {\n"
                + "        checkNullity(dog);\n"
                + "\n"
                + "        if (dog.getId() == null) {\n"
                + "            dog.setId(getNewId());\n"
                + "\n"
                + "            try {\n"
                + "                PreparedStatement query = connection.prepareStatement(\n"
                + "                    \"insert into DOG(DOG_ID) values(?)\");\n"
                + "                query.setInt(1, dog.getId().intValue());\n"
                + "\n"
                + "                query.executeUpdate();\n"
                + "            } catch (SQLException e) {\n"
                + "                throw new DAOException(\"Unable to perform insert on database.\", e);\n"
                + "            }\n"
                + "        } else {\n"
                + "            try {\n"
                + "                PreparedStatement query = connection.prepareStatement(\n"
                + "                                \"update DOG set  where DOG_ID=?\");\n"
                + "                query.setInt(1, dog.getId().intValue());\n"
                + "\n"
                + "                query.executeUpdate();\n"
                + "\n"
                + "            } catch (SQLException e) {\n"
                + "                throw new DAOException(\"Unable to perform update on database.\", e);\n"
                + "            }\n"
                + "        }\n"
                + "\n"
                + "    }\n"
                + "\n";

        MockFileWriter fileWriter = new MockFileWriter();
        Entity entity = new Entity("Dog");
        Java6Provider.provideDAOSaveMethod(fileWriter, entity);
        assertEquals(expected, fileWriter.getWrittenData());
    }
}
