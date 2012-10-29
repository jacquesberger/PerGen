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

package org.jberger.pergen.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.DataLayerSpecifications;
import org.jberger.pergen.domain.Entity;
import org.jberger.pergen.domain.Relation;
import org.jberger.pergen.domain.RelationType;
import org.jberger.pergen.files.CodeFileWriter;
import org.jberger.pergen.codeproviders.Java6Provider;

/**
 * Launches the code generation.
 */
public final class JavaGenerator {

    String directory;
    
    public JavaGenerator(String dir) {
        directory = dir;
    }
    
    public void generate(final DataLayerSpecifications global) {
        generateDAOs(global);
        generatePOJOs(global);
    }
    
    /**
     * Generates the DAOException.java file.
     */
    private void generateDAOException(String workingDirectory) {
        String fileName = workingDirectory + "\\DAOException.java";
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
     */
    private void generateDAOs(final DataLayerSpecifications global) {
        String realDirectory = directory + "\\daos";
        new File(realDirectory).mkdir();
        generateDAOException(realDirectory);
        generateNullityException(realDirectory);

        for (Entity entity : global.getEntities()) {
            String className = entity.getJavaName() + "DAO";
            String fileName = realDirectory + "\\" + className
                              + ".java";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                CodeFileWriter writer = new CodeFileWriter(fileWriter);
                Java6Provider.provideHeaderComment(writer);
                Java6Provider.providePackageDeclaration(writer, "daos");
                Java6Provider.provideDAOsImports(writer, entity.getJavaName());
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
     */
    private void generateNullityException(String workingDirectory) {
        String fileName = workingDirectory + "\\NullityException.java";
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
     */
    private void generatePOJOs(final DataLayerSpecifications global) {
        String realDirectory = directory + "\\pojos";
        new File(realDirectory).mkdir();

        for (Entity entity : global.getEntities()) {
            String fileName = realDirectory + "\\" + entity.getJavaName()
                              + ".java";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                CodeFileWriter writer = new CodeFileWriter(fileWriter);
                Java6Provider.provideHeaderComment(writer);
                Java6Provider.providePackageDeclaration(writer, "pojos");
                Java6Provider.providePOJOsImports(writer, entity);
                Java6Provider.provideClassDeclaration(writer,
                                                      entity.getJavaName());
                Java6Provider.provideDefaultConstructor(writer,
                                                        entity.getJavaName());

                Java6Provider.providePOJOsIdGetterSetter(writer);
                for (Field field : entity.getFields()) {
                    Java6Provider.providePOJOsFieldAndGetterSetter(writer,
                                                                   field);
                }

                for (Relation relation : entity.getRelations()) {
                    if (relation.getType() == RelationType.Type.ONE) {
                        Java6Provider.providePOJOsEverythingForAOneRelation(
                                            writer,
                                            relation.getEntity().getJavaName());
                    } else {
                        Java6Provider.providePOJOsEverythingForAManyRelation(
                                            writer,
                                            relation.getEntity().getJavaName());
                    }
                }

                Java6Provider.provideClassEnd(writer);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}