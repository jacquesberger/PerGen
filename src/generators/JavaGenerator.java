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

package generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import domain.FieldInformations;
import domain.GlobalInformations;
import domain.EntityInformations;
import domain.Relation;
import domain.RelationType;
import files.CodeFileWriter;
import codeproviders.Java6Provider;

/**
 * Launches the code generation.
 */
public final class JavaGenerator {

    /**
     * Generates the DAOException.java file.
     * @param directory The directory in which the file must be created.
     */
    private static void generateDAOException(final String directory) {
        String fileName = directory + "\\DAOException.java";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            CodeFileWriter writer = new CodeFileWriter(fileWriter);
            Java6Provider.provideHeaderComment(writer);
            Java6Provider.providePackageDeclaration(writer, "daos");
            Java6Provider.provideDAOExceptionClass(writer);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the DAOs (Data Access Object - containing the JDBC code)
     * corresponding to the entities.
     * @param global All the entities.
     * @param directory The directory in which the "daos" directory will
     *                  be created.
     */
    public static void generateDAOs(final GlobalInformations global,
                                    final String directory) {
        String realDirectory = directory + "\\daos";
        new File(realDirectory).mkdir();
        generateDAOException(realDirectory);
        generateNullityException(realDirectory);

        for (EntityInformations entity : global.getEntities()) {
            String className = entity.getCodeName() + "DAO";
            String fileName = realDirectory + "\\" + className
                              + ".java";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                CodeFileWriter writer = new CodeFileWriter(fileWriter);
                Java6Provider.provideHeaderComment(writer);
                Java6Provider.providePackageDeclaration(writer, "daos");
                Java6Provider.provideDAOsImports(writer, entity.getCodeName());
                Java6Provider.provideClassDeclaration(writer, className);
                Java6Provider.provideDAOsConnectionAndConstructor(writer,
                                                                  className);

                Java6Provider.provideDAOGetMethod(writer, entity);
                Java6Provider.provideDAOGetAllMethod(writer, entity);
                Java6Provider.provideDAODeleteMethod(writer, entity);
                Java6Provider.provideDAOCheckNullityMethod(writer, entity);
                Java6Provider.provideDAONewIdMethod(writer, entity);
                Java6Provider.provideDAOSaveMethod(writer, entity);

                Java6Provider.provideClassEnd(writer);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates the NullityException.java file.
     * @param directory The directory in which the file must be created.
     */
    private static void generateNullityException(final String directory) {
        String fileName = directory + "\\NullityException.java";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            CodeFileWriter writer = new CodeFileWriter(fileWriter);
            Java6Provider.provideHeaderComment(writer);
            Java6Provider.providePackageDeclaration(writer, "daos");
            Java6Provider.provideNullityExceptionClass(writer);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the POJOs (Plain-Old Java Object) corresponding to the
     * entities.
     * @param global All the entities.
     * @param directory The directory in which the "pojos" directory will
     *                  be created.
     */
    public static void generatePOJOs(final GlobalInformations global,
                                     final String directory) {
        String realDirectory = directory + "\\pojos";
        new File(realDirectory).mkdir();

        for (EntityInformations entity : global.getEntities()) {
            String fileName = realDirectory + "\\" + entity.getCodeName()
                              + ".java";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                CodeFileWriter writer = new CodeFileWriter(fileWriter);
                Java6Provider.provideHeaderComment(writer);
                Java6Provider.providePackageDeclaration(writer, "pojos");
                Java6Provider.providePOJOsImports(writer, entity);
                Java6Provider.provideClassDeclaration(writer,
                                                      entity.getCodeName());
                Java6Provider.provideDefaultConstructor(writer,
                                                        entity.getCodeName());

                Java6Provider.providePOJOsIdGetterSetter(writer);
                for (FieldInformations field : entity.getFields()) {
                    Java6Provider.providePOJOsFieldAndGetterSetter(writer,
                                                                   field);
                }

                for (Relation relation : entity.getRelations()) {
                    if (relation.getType() == RelationType.Type.ONE) {
                        Java6Provider.providePOJOsEverythingForAOneRelation(
                                            writer,
                                            relation.getEntity().getCodeName());
                    } else {
                        Java6Provider.providePOJOsEverythingForAManyRelation(
                                            writer,
                                            relation.getEntity().getCodeName());
                    }
                }

                Java6Provider.provideClassEnd(writer);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To avoid instanciation.
     */
    private JavaGenerator() {
    }
}
