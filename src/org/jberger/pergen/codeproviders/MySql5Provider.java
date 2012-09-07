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
import org.jberger.pergen.domain.Entity;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.UnicityConstraint;
import org.jberger.pergen.domain.Relation;
import org.jberger.pergen.files.CodeFileWriter;
import org.jberger.pergen.generators.SQLGenerator;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Provides all the mysql5 code.
 */
public final class MySql5Provider {

    /**
     * To avoid instanciation.
     */
    private MySql5Provider() {
    }

    /**
     * Generates a CREATE TABLE statement for an entity.
     * 
     * @param entity
     *            The entity.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    public static void provideStandardCreateTable(final Entity entity,
	    final CodeFileWriter writer) throws IOException {
	writer.write("CREATE TABLE " + entity.getSqlName() + " (\n");
	writer.write("  " + entity.getSqlName() + "_ID INTEGER NOT NULL,\n");

	for (Field field : entity.getFields()) {
	    writer.write("  " + field.getSqlName() + " " + originalTypeToDatabaseType(field.getOriginalDataType()));

	    Integer length = field.getStringLength();
	    if (length != null) {
		writer.write("(" + length.toString() + ")");
	    } else if (field.getOriginalDataType() == FieldType.Type.STRING) {
		writer.write("(255)");
	    }

	    writer.write(",\n");
	}

	for (Relation relation : entity.getRelations()) {
	    if (relation.getType() == RelationType.Type.ONE) {
		writer.write("  " + relation.getEntity().getSqlName() + "_ID INTEGER NOT NULL,\n");
	    }
	}

	writer.write("  CONSTRAINT PK_" + entity.getSqlName() + " PRIMARY " + "KEY ("
	        + entity.getSqlName() + "_ID)\n");
	writer.write(");\n\n");
    }

    public static String buildCreateStatementForJunctionTable(final String firstEntitySqlName,
	    final String secondEntitySqlName) {
	String tableName = SQLGenerator.buildJunctionTableName(firstEntitySqlName,
	        secondEntitySqlName);
	return "CREATE TABLE " + tableName + " (\n" + "  " + tableName + "_ID INTEGER NOT NULL "
	        + "auto_increment,\n" + "  " + firstEntitySqlName + "_ID INTEGER NOT NULL,\n"
	        + "  " + secondEntitySqlName + "_ID INTEGER NOT NULL,\n" + "  CONSTRAINT PK_"
	        + tableName + " PRIMARY KEY (" + tableName + "_ID)\n" + ");\n\n";
    }

    public static String buildForeignKeyStatement(String modifiedTableName,
	    String referencedTableName) {
	return "ALTER TABLE " + modifiedTableName + " ADD (" + "CONSTRAINT FK_" + modifiedTableName
	        + "_" + referencedTableName + " FOREIGN KEY (" + referencedTableName
	        + "_ID) REFERENCES " + referencedTableName + "(" + referencedTableName
	        + "_ID));\n\n";
    }

    public static String buildForeignKeyStatementsForJunctionTable(String firstReferencedTableName,
	    String secondReferencedTableName) {
	String junctionTableName = SQLGenerator.buildJunctionTableName(firstReferencedTableName,
	        secondReferencedTableName);

	return buildForeignKeyStatement(junctionTableName, firstReferencedTableName) +
	       buildForeignKeyStatement(junctionTableName, secondReferencedTableName);
    }

    /**
     * Generates the create unique index statement for a unicity constraint.
     * 
     * @param unicity
     *            The unicity constraint.
     * @param entityName
     *            The SQL name of the entity.
     * @param unicityCount
     *            The number of unicity constraints so far in the entity. It is
     *            used as a sequential number in the name of the sql constraint
     *            to avoid name conflict if multiple constraints in the same
     *            entity.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    public static void provideUnicityConstraint(final UnicityConstraint unicity,
	    final String entityName, final int unicityCount, final CodeFileWriter writer)
	    throws IOException {
	writer.write("CREATE UNIQUE INDEX INDEX_" + entityName + unicityCount + " ON " + entityName
	        + "(");

	Collection<Field> fields = unicity.getFields();
	Iterator<Field> iterator = fields.iterator();
	while (iterator.hasNext()) {
	    Field field = iterator.next();
	    writer.write(field.getSqlName());
	    if (iterator.hasNext()) {
		writer.write(", ");
	    }
	}

	writer.write(");\n\n");
    }

    private static String originalTypeToDatabaseType(final FieldType.Type type) {
	switch (type) {
	case DATE:
	    return ("DATE");
	case INTEGER:
	    return ("INTEGER");
	case REAL:
	    return ("DOUBLE");
	case STRING:
	    return ("VARCHAR");
	default:
	    return (null);
	}
    }
}
