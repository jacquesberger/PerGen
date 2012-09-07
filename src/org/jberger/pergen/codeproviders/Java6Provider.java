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

package org.jberger.pergen.codeproviders;

import org.jberger.pergen.domain.FieldType;
import org.jberger.pergen.domain.RelationType;
import org.jberger.pergen.domain.EntityInformations;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.Relation;
import org.jberger.pergen.files.CodeFileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides all the Java code.
 */
public final class Java6Provider {

    /**
     * To avoid instanciation.
     */
    private Java6Provider() {
    }

    /**
     * Generates the header comment of a class file.
     * @param writer The file writer.
     * @throws IOException From the file writer.
     */
    public static void provideHeaderComment(final CodeFileWriter writer)
                                            throws IOException {
        writer.write("/*\n");
        writer.write(" * This file was generated with the PerGen tool.\n");
        writer.write(" * This code is free to use and distribute.\n");
        writer.write(" */\n\n");
    }

    /**
     * Generates the package declaration of the class.
     * @param writer The file writer.
     * @param packageName The package name.
     * @throws IOException From the file writer.
     */
    public static void providePackageDeclaration(final CodeFileWriter writer,
                                                 final String packageName)
                                                 throws IOException {
        writer.write("package " + packageName + ";\n\n");
    }

    /**
     * Generates the class declaration line.
     * @param writer The file writer.
     * @param className The class name.
     * @throws IOException From the file writer.
     */
    public static void provideClassDeclaration(final CodeFileWriter writer,
                                               final String className)
                                               throws IOException {
        writer.write("public class " + className + " {\n\n");
    }

    public static void provideDefaultConstructor(final CodeFileWriter writer,
                                                 final String className)
                                                 throws IOException {
        writer.write("    public " + className + "() {\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the POJOs Id field and its getter and setter.
     * @param writer The file writer.
     * @throws IOException From the file writer.
     */
    public static void providePOJOsIdGetterSetter(final CodeFileWriter writer)
                                              throws IOException {
        writer.write("    private Integer id = null;\n\n");

        writer.write("    public Integer getId() {\n");
        writer.write("        return id;\n");
        writer.write("    }\n\n");

        writer.write("    public void setId(Integer newId) {\n");
        writer.write("        id = newId;\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the class ending token.
     * @param writer The file writer.
     * @throws IOException From the file writer.
     */
    public static void provideClassEnd(final CodeFileWriter writer)
                                       throws IOException {
        writer.write("}\n");
    }

    /**
     * Generates the POJOs import statements.
     * @param writer The file writer.
     * @param entity The entity of the generating class.
     * @throws IOException From the file writer.
     */
    public static void providePOJOsImports(final CodeFileWriter writer,
                                           final EntityInformations entity)
                                           throws IOException {
        if (entity.hasADateField()) {
            writer.write("import java.util.Date;\n");
        }

        if (entity.hasAManyRelation()) {
            writer.write("import java.util.ArrayList;\n");
        }
        writer.write("\n");
    }

    /**
     * Generates the DAOs import statements.
     * @param writer The file writer.
     * @param entityCodeName The entity class to import in the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAOsImports(final CodeFileWriter writer,
                                          final String entityCodeName)
                                          throws IOException {
        writer.write("import java.sql.Connection;\n");
        writer.write("import java.sql.PreparedStatement;\n");
        writer.write("import java.sql.ResultSet;\n");
        writer.write("import java.sql.SQLException;\n");
        writer.write("import java.util.ArrayList;\n\n");

        writer.write("import pojos." + entityCodeName + ";\n\n");
    }

    /**
     * Generates a field and its getter and setter in a POJO.
     * @param writer The file writer.
     * @param field The field to generate.
     * @throws IOException From the file writer.
     */
    public static void providePOJOsFieldAndGetterSetter(
                                                 final CodeFileWriter writer,
                                                 final Field field)
                                                 throws IOException {
        String javaType = mapType(field.getOriginalDataType());

        writer.write("    private " + javaType + " " + field.getJavaName()
                     + " = null;\n\n");

        writer.write("    public " + javaType + " " + field.getGetterName()
                     + "() {\n");
        writer.write("        return " + field.getJavaName() + ";\n");
        writer.write("    }\n\n");

        writer.write("    public void " + field.getSetterName() + "("
                     + javaType + " newValue) {\n");
        writer.write("        " + field.getJavaName() + " = newValue"
                     + ";\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the field for the one relation and its getter and setter in
     * a POJO.
     * @param writer The file writer.
     * @param entityCodeName The entity code name to which the relation must
     *                       be made.
     * @throws IOException From the file writer.
     */
    public static void providePOJOsEverythingForAOneRelation(
                                                final CodeFileWriter writer,
                                                final String entityCodeName)
                                                throws IOException {
        char[] workingName = entityCodeName.toCharArray();
        workingName[0] = Character.toLowerCase(workingName[0]);
        String lowcaseName = new String(workingName);

        writer.write("    private Integer " + lowcaseName + "Id = null;\n\n");

        writer.write("    public Integer get" + entityCodeName + "Id() {\n");
        writer.write("        return " + lowcaseName + "Id;\n");
        writer.write("    }\n\n");

        writer.write("    public void set" + entityCodeName + "Id(Integer new"
                     + entityCodeName + ") {\n");
        writer.write("        " + lowcaseName + "Id = new" + entityCodeName
                     + ";\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the arraylist for a many relation. Also generates an add,
     * a remove and a get method for operations on the arraylist.
     * @param writer The file writer.
     * @param entityCodeName The code name of the entity to which the relation
     *                       is made.
     * @throws IOException From the file writer.
     */
    public static void providePOJOsEverythingForAManyRelation(
                                               final CodeFileWriter writer,
                                               final String entityCodeName)
                                               throws IOException {
        char[] workingName = entityCodeName.toCharArray();
        workingName[0] = Character.toLowerCase(workingName[0]);
        String lowcaseName = new String(workingName);

        writer.write("    private ArrayList<Integer> " + lowcaseName
                     + "List = new ArrayList<Integer>();\n\n");

        writer.write("    public void add" + entityCodeName + "(Integer "
                     + lowcaseName + "Id) {\n");
        writer.write("        " + lowcaseName + "List.add("
                     + lowcaseName + "Id);\n");
        writer.write("    }\n\n");

        writer.write("    public void remove" + entityCodeName
                     + "(Integer " + lowcaseName + "Id) {\n");
        writer.write("        " + lowcaseName + "List.remove("
                     + lowcaseName + "Id);\n");
        writer.write("    }\n\n");

        writer.write("    public ArrayList<Integer> get" + entityCodeName
                     + "List() {\n");
        writer.write("        return " + lowcaseName + "List;\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the DAOs connection field and public constructor.
     * @param writer The file writer.
     * @param className The class name.
     * @throws IOException From the file writer.
     */
    public static void provideDAOsConnectionAndConstructor(
                                                        final CodeFileWriter writer,
                                                        final String className)
                                                        throws IOException {
        writer.write("    private Connection connection = null;\n\n");

        writer.write("    public " + className
                     + "(final Connection databaseConnection) {\n");
        writer.write("        connection = databaseConnection;\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the DAOException class.
     * @param writer The file writer.
     * @throws IOException From the file writer.
     */
    public static void provideDAOExceptionClass(final CodeFileWriter writer)
                                                throws IOException {
        writer.write("public class DAOException extends Exception {\n\n");
        writer.write("    static final long serialVersionUID = "
                     + "200710190246666L;\n\n");
        writer.write("    public DAOException(final String message, "
                     + "final Exception e) {\n");
        writer.write("        super(message, e);\n");
        writer.write("    }\n\n");
        writer.write("}\n");
    }

    /**
     * Generates the NullityException class.
     * @param writer The file writer.
     * @throws IOException From the file writer.
     */
    public static void provideNullityExceptionClass(final CodeFileWriter writer)
                                                    throws IOException {
        writer.write("public class NullityException extends Exception {\n\n");
        writer.write("    static final long serialVersionUID = "
                     + "200710190250666L;\n\n");
        writer.write("    public NullityException(final String className, "
                     + "final String field) {\n");
        writer.write("         super(\"The field \" + field + \" of the class "
                     + "\" + className + \" should not contain a null "
                     + "value.\");\n");
        writer.write("    }\n\n");
        writer.write("}\n");
    }


    /**
     * Generates the get method for a DAO.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAOGetMethod(final CodeFileWriter writer,
                                           final EntityInformations entity)
                                           throws IOException {
        writer.write("    public " + entity.getCodeName() + " get"
                     + entity.getCodeName() + "(final Integer id) "
                     + "throws DAOException {\n");
        writer.write("        try {\n");

        writer.write("            PreparedStatement query = "
                     + "connection.prepareStatement(\"select * from "
                     + entity.getSqlName() + " where "
                     + entity.getSqlName() + "_ID=?\");\n");
        writer.write("            query.setInt(1, id.intValue());\n\n");

        writer.write("            ResultSet result = query.executeQuery();\n");
        writer.write("            result.beforeFirst();\n");
        writer.write("            if (result.next()) {\n");

        String variableName = entity.getCodeName().toLowerCase();
        writer.write("                " + entity.getCodeName()
                     + " " + variableName + " = new " + entity.getCodeName()
                     + "();\n");
        writer.write("                " + variableName
                     + ".setId(result.getInt(\"" + entity.getSqlName()
                     + "_ID\"));\n\n");

        for (Field field : entity.getFields()) {
            writer.write("                " + variableName + "."
                         + field.getSetterName() + "(result."
                         + mapResultSetGetter(field.getOriginalDataType())
                         + "(\"" + field.getSqlName() + "\"));\n");
        }
        writer.write("\n");

        for (Relation manyToOne : entity.getAllManyToOneRelations()) {
            EntityInformations link = manyToOne.getEntity();
            writer.write("                " + variableName + ".set"
                         + link.getCodeName() + "Id(result.getInt(\""
                         + link.getSqlName() + "_ID\"));\n");
        }
        writer.write("\n");

        for (Relation many : entity.getAllMANYRelations()) {
            EntityInformations link = many.getEntity();
            String statementName = link.getCodeName().toLowerCase() + "s";
            String linkTable = link.getSqlName();
            if (many.isManyToMany()) {
                linkTable = many.getNameOfLinkTable();
            }

            writer.write("                PreparedStatement " + statementName
                         + " = connection.prepareStatement(\"select "
                         + link.getSqlName() + "_ID from " + linkTable
                         + " where " + entity.getSqlName() + "_ID=?\");\n");
            writer.write("                " + statementName + ".setInt(1, "
                         + variableName + ".getId().intValue());\n");

            String resultName = statementName + "Result";
            writer.write("                ResultSet " + resultName + " = "
                         + statementName + ".executeQuery();\n");
            writer.write("                " + resultName + ".beforeFirst();\n");
            writer.write("                while (" + resultName
                         + ".next()) {\n");
            writer.write("                    " + variableName + ".add"
                         + link.getCodeName() + "(" + resultName + ".getInt(\""
                         + link.getSqlName() + "_ID\"));\n");
            writer.write("                }\n\n");
        }

        writer.write("                return " + variableName + ";\n");
        writer.write("            }\n");
        writer.write("        } catch (SQLException e) {\n");
        writer.write("            throw new DAOException(\"Unable to perform "
                     + "query on database.\", e);\n");
        writer.write("        }\n");
        writer.write("        return null;\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the getAll method for the DAO.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAOGetAllMethod(final CodeFileWriter writer,
                                              final EntityInformations entity)
                                              throws IOException {
        writer.write("    public ArrayList<" + entity.getCodeName()
                     + "> getAll" + entity.getCodeName()
                     + "s() throws DAOException {\n");
        writer.write("        try {\n");

        writer.write("            PreparedStatement query = "
                     + "connection.prepareStatement(\"select * from "
                     + entity.getSqlName() + "\");\n\n");

        writer.write("            ResultSet result = query.executeQuery();\n");
        writer.write("            result.beforeFirst();\n");

        writer.write("            ArrayList<" + entity.getCodeName()
                     + "> list = new ArrayList<" + entity.getCodeName()
                     + ">();\n\n");

        writer.write("            while (result.next()) {\n");

        String variableName = entity.getCodeName().toLowerCase();
        writer.write("                " + entity.getCodeName()
                     + " " + variableName + " = new " + entity.getCodeName()
                     + "();\n");
        writer.write("                " + variableName
                     + ".setId(result.getInt(\"" + entity.getSqlName()
                     + "_ID\"));\n\n");

        for (Field field : entity.getFields()) {
            writer.write("                " + variableName + "."
                         + field.getSetterName() + "(result."
                         + mapResultSetGetter(field.getOriginalDataType())
                         + "(\"" + field.getSqlName() + "\"));\n");
        }
        writer.write("\n");

        for (Relation manyToOne : entity.getAllManyToOneRelations()) {
            EntityInformations link = manyToOne.getEntity();
            writer.write("                " + variableName + ".set"
                         + link.getCodeName() + "Id(result.getInt(\""
                         + link.getSqlName() + "_ID\"));\n");
        }
        writer.write("\n");

        for (Relation many : entity.getAllMANYRelations()) {
            EntityInformations link = many.getEntity();
            String statementName = link.getCodeName().toLowerCase() + "s";
            String linkTable = link.getSqlName();
            if (many.isManyToMany()) {
                linkTable = many.getNameOfLinkTable();
            }

            writer.write("                PreparedStatement " + statementName
                         + " = connection.prepareStatement(\"select "
                         + link.getSqlName() + "_ID from " + linkTable
                         + " where " + entity.getSqlName() + "_ID=?\");\n");
            writer.write("                " + statementName + ".setInt(1, "
                         + variableName + ".getId().intValue());\n");

            String resultName = statementName + "Result";
            writer.write("                ResultSet " + resultName + " = "
                         + statementName + ".executeQuery();\n");
            writer.write("                " + resultName + ".beforeFirst();\n");
            writer.write("                while (" + resultName
                         + ".next()) {\n");
            writer.write("                    " + variableName + ".add"
                         + link.getCodeName() + "(" + resultName + ".getInt(\""
                         + link.getSqlName() + "_ID\"));\n");
            writer.write("                }\n\n");
        }

        writer.write("                list.add(" + variableName + ");\n");
        writer.write("            }\n\n");
        writer.write("            return list;\n");

        writer.write("        } catch (SQLException e) {\n");
        writer.write("            throw new DAOException(\"Unable to perform "
                     + "query on database.\", e);\n");
        writer.write("        }\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the DAOs delete method.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAODeleteMethod(final CodeFileWriter writer,
                                              final EntityInformations entity)
                                              throws IOException {
        writer.write("    public void delete(final Integer id) "
                     + "throws DAOException {\n");
        writer.write("        try {\n");

        for (Relation relation : entity.getAllManyToManyRelations()) {
            String variableName = relation.getEntity()
                                           .getOriginalName().toLowerCase()
                                  + "s";
            writer.write("            PreparedStatement " + variableName
                         + " = connection.prepareStatement(\"delete from "
                         + relation.getNameOfLinkTable() + " where "
                         + entity.getSqlName() + "_ID=?\");\n");
            writer.write("            " + variableName + ".setInt(1, id);\n");
            writer.write("            " + variableName
                         + ".executeUpdate();\n\n");
        }

        writer.write("            PreparedStatement query = "
                     + "connection.prepareStatement(\"delete from "
                     + entity.getSqlName() + " where " + entity.getSqlName()
                     + "_ID=?\");\n");
        writer.write("            query.setInt(1, id);\n");
        writer.write("            query.executeUpdate();\n");

        writer.write("        } catch (SQLException e) {\n");
        writer.write("            throw new DAOException(\"Unable to "
                     + "perform delete on database.\", e);\n");
        writer.write("        }\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates de checkNullity method of a DAO.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAOCheckNullityMethod(
                                               final CodeFileWriter writer,
                                               final EntityInformations entity)
                                               throws IOException {
        String variableName = entity.getCodeName().toLowerCase();
        writer.write("    protected void checkNullity(final "
                     + entity.getCodeName() + " " + variableName
                     + ") throws NullityException {\n");

        for (Field field : entity.getFields()) {
            if (field.isRequired()) {
                writer.write("        if (" + variableName + "."
                             + field.getGetterName() + "() == null) {\n");
                writer.write("            throw new NullityException(\""
                             + entity.getCodeName() + "\", \""
                             + field.getJavaName() + "\");\n");
                writer.write("        }\n\n");
            }
        }

        for (Relation relation : entity.getRelations()) {
            EntityInformations link = relation.getEntity();
            if (relation.getType() == RelationType.Type.ONE) {
                writer.write("        if (" + variableName + ".get"
                             + link.getCodeName() + "Id() == null) {\n");
                writer.write("            throw new NullityException(\""
                             + entity.getCodeName() + "\", \""
                             + link.getCodeName() + "Id\");\n");
                writer.write("        }\n\n");
            } else {
                if (!relation.isMaybeZero()) {
                    writer.write("        if (" + variableName + ".get"
                                 + link.getCodeName()
                                 + "List().size() == 0) {\n");
                    writer.write("            throw new NullityException(\""
                                 + entity.getCodeName() + "\", \"");
                    if (relation.isManyToMany()) {
                        writer.write(relation.getNameOfLinkTable() + ":");
                    }
                    writer.write(link.getCodeName() + "Id\");\n");
                    writer.write("        }\n\n");
                }
            }
        }

        writer.write("    }\n\n");
    }

    /**
     * Generates the getNewId method of a DAO.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAONewIdMethod(final CodeFileWriter writer,
                                             final EntityInformations entity)
                                             throws IOException {
        writer.write("    protected Integer getNewId() throws DAOException "
                     + "{\n");
        writer.write("        try {\n");

        writer.write("            PreparedStatement query = "
                     + "connection.prepareStatement(\"select max("
                     + entity.getSqlName() + "_ID) as NEWID from "
                     + entity.getSqlName() + "\");\n");
        writer.write("            ResultSet result = query.executeQuery();\n");

        writer.write("            if (result.next()) {\n");
        writer.write("                return new Integer("
                     + "result.getInt(\"NEWID\") + 1);\n");
        writer.write("            } else {\n");
        writer.write("                return new Integer(0);\n");
        writer.write("            }\n");

        writer.write("         } catch (SQLException e) {\n");
        writer.write("             throw new DAOException(\"Unable to "
                     + "perform query on database.\", e);\n");
        writer.write("         }\n");
        writer.write("    }\n\n");
    }

    /**
     * Generates the DAO save method.
     * @param writer The file writer.
     * @param entity The entity of the DAO.
     * @throws IOException From the file writer.
     */
    public static void provideDAOSaveMethod(final CodeFileWriter writer,
                                            final EntityInformations entity)
                                            throws IOException {
        String parameter = entity.getCodeName().toLowerCase();
        writer.write("    public void save(final " + entity.getCodeName()
                     + " " + parameter + ") throws DAOException, "
                     + "NullityException {\n");
        writer.write("        checkNullity(" + parameter + ");\n\n");

        writer.write("        if (" + parameter + ".getId() == null) {\n");
        writer.write("            " + parameter + ".setId(getNewId());\n\n");

        writer.write("            try {\n");
        writer.write("                PreparedStatement query = "
                     + "connection.prepareStatement(\n");
        writer.write("                    \"insert into " + entity.getSqlName()
                     + "(" + entity.getSqlName() + "_ID");

        int questionMarksNumber = 1;
        for (Field field : entity.getFields()) {
            questionMarksNumber++;
            writer.write(", " + field.getSqlName());
        }
        for (Relation toOne : entity.getAllManyToOneRelations()) {
            questionMarksNumber++;
            writer.write(", " + toOne.getEntity().getSqlName() + "_ID");
        }
        writer.write(") values(?");
        for (int marksCount = 2; marksCount <= questionMarksNumber;
                                                                 marksCount++) {
            writer.write(", ?");
        }
        writer.write(")\");\n");

        writer.write("                query.setInt(1, " + parameter
                     + ".getId().intValue());\n");
        int parameterNumber = 2;
        for (Field field : entity.getFields()) {
            writer.write("                query."
                         + mapStatementSetter(field.getOriginalDataType())
                         + "(" + parameterNumber + ", ");
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                writer.write("new java.sql.Date(");
            }
            writer.write(parameter + "." + field.getGetterName() + "()");
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                writer.write(".getTime())");
            }
            writer.write(");\n");
            parameterNumber++;
        }
        for (Relation toOne : entity.getAllManyToOneRelations()) {
            writer.write("                query.setInt(" + parameterNumber
                         + ", " + parameter + ".get"
                         + toOne.getEntity().getCodeName() + "Id());\n");
            parameterNumber++;
        }
        writer.write("\n");

        writer.write("                query.executeUpdate();\n");
        writer.write("            } catch (SQLException e) {\n");
        writer.write("                throw new DAOException(\"Unable to "
                     + "perform insert on database.\", e);\n");
        writer.write("            }\n");
        writer.write("        } else {\n");
        writer.write("            try {\n");

        writer.write("                PreparedStatement query = "
                     + "connection.prepareStatement(\n");
        writer.write("                                \"update "
                     + entity.getSqlName() + " set ");
        int updateParameter = 0;
        for (Field field : entity.getFields()) {
            if (updateParameter > 0) {
                writer.write(", ");
            }
            writer.write(field.getSqlName() + "=?");
            updateParameter++;
        }
        for (Relation toOne : entity.getAllManyToOneRelations()) {
            if (updateParameter > 0) {
                writer.write(", ");
            }
            writer.write(toOne.getEntity().getSqlName() + "_ID=?");
            updateParameter++;
        }
        writer.write(" where " + entity.getSqlName() + "_ID=?\");\n");

        int updateParameterNumber = 1;
        for (Field field : entity.getFields()) {
            writer.write("                query."
                         + mapStatementSetter(field.getOriginalDataType())
                         + "(" + updateParameterNumber + ", ");
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                writer.write("new java.sql.Date(");
            }
            writer.write(parameter + "." + field.getGetterName() + "()");
            if (field.getOriginalDataType() == FieldType.Type.DATE) {
                writer.write(".getTime())");
            }
            writer.write(");\n");
            updateParameterNumber++;
        }
        for (Relation toOne : entity.getAllManyToOneRelations()) {
            writer.write("                query.setInt("
                         + updateParameterNumber + ", " + parameter + ".get"
                         + toOne.getEntity().getCodeName() + "Id());\n");
            updateParameterNumber++;
        }
        writer.write("                query.setInt(" + updateParameterNumber
                     + ", " + parameter + ".getId().intValue());\n\n");

        writer.write("                query.executeUpdate();\n\n");

        for (Relation manyToMany : entity.getAllManyToManyRelations()) {
            String variableName = "delete"
                                  + manyToMany.getEntity().getCodeName();
            writer.write("                PreparedStatement " + variableName
                         + " = connection.prepareStatement(\"delete from "
                         + manyToMany.getNameOfLinkTable() + " where "
                         + entity.getSqlName() + "_ID=?\");\n");
            writer.write("                " + variableName + ".setInt(1, "
                         + parameter + ".getId());\n");
            writer.write("                " + variableName
                         + ".executeUpdate();\n\n");
        }

        writer.write("            } catch (SQLException e) {\n");
        writer.write("                throw new DAOException(\"Unable to "
                     + "perform update on database.\", e);\n");
        writer.write("            }\n");
        writer.write("        }\n\n");

        ArrayList<Relation> manyToManyList = entity.getAllManyToManyRelations();
        if (manyToManyList.size() > 0) {
            writer.write("        try {\n");

            for (Relation relation : manyToManyList) {
                EntityInformations link = relation.getEntity();
                writer.write("            for (Integer id : "
                             + parameter + ".get" + link.getCodeName()
                             + "List()) {\n");
                writer.write("                PreparedStatement link = "
                             + "connection.prepareStatement(\n");
                writer.write("                        \"insert into "
                             + relation.getNameOfLinkTable() + "("
                             + link.getSqlName() + "_ID, " + entity.getSqlName()
                             + "_ID) values(?, ?)\");\n");
                writer.write("                link.setInt(1, id);\n");
                writer.write("                link.setInt(2, " + parameter
                             + ".getId());\n");
                writer.write("                link.executeUpdate();\n");
                writer.write("            }\n\n");
            }

            writer.write("        } catch (SQLException e) {\n");
            writer.write("            throw new DAOException(\"Unable to "
                         + "perform insert on database.\", e);\n");
            writer.write("        }\n");
        }

        writer.write("    }\n\n");
    }

    /**
     * Maps the original data type of a field with a Java type.
     * @param type The original data type.
     * @return The Java type.
     */
    private static String mapType(final FieldType.Type type) {
        switch (type) {
        case DATE:
            return "Date";
        case INTEGER:
            return "Integer";
        case REAL:
            return "Double";
        case STRING:
            return "String";
        default:
            return null;
        }
    }

    /**
     * Maps the original data type of a field with a Java ResultSet getter.
     * @param type The original data type.
     * @return The ResultSet getter.
     */
    private static String mapResultSetGetter(final FieldType.Type type) {
        switch (type) {
        case DATE:
            return "getDate";
        case INTEGER:
            return "getInt";
        case REAL:
            return "getDouble";
        case STRING:
            return "getString";
        default:
            return null;
        }
    }

    /**
     * Maps the original data type of a field with a Java PreparedStatement
     * setter.
     * @param type The original data type.
     * @return The setter name.
     */
    private static String mapStatementSetter(final FieldType.Type type) {
        switch (type) {
        case DATE:
            return "setDate";
        case INTEGER:
            return "setInt";
        case REAL:
            return "setDouble";
        case STRING:
            return "setString";
        default:
            return null;
        }
    }
}
