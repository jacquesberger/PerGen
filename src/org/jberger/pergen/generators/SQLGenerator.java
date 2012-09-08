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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jberger.pergen.codeproviders.MySql5Provider;

import org.jberger.pergen.domain.Entity;
import org.jberger.pergen.domain.DataLayerSpecifications;
import org.jberger.pergen.domain.Relation;
import org.jberger.pergen.domain.RelationType;
import org.jberger.pergen.domain.UnicityConstraint;
import org.jberger.pergen.files.CodeFileWriter;

/**
 * Generates the SQL script.
 */
public final class SQLGenerator {

    /**
     * Launches the generation of the alter table statements for the
     * many-to-many relations.
     * 
     * @param global
     *            All the entities.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    private static void buildManyToManyAlterTables(final DataLayerSpecifications global,
	    final CodeFileWriter writer) throws IOException {
	ArrayList<String> linkTablesDone = new ArrayList<String>();

	for (Entity entity : global.getEntities()) {
	    for (Relation relation : entity.getRelations()) {
		if (relation.isManyToMany()) {
		    if (!linkTablesDone.contains(relation.getNameOfLinkTable())) {
			linkTablesDone.add(relation.getNameOfLinkTable());
			writer.write(MySql5Provider.buildForeignKeyStatementsForJunctionTable(
			        entity.getSqlName(), relation.getEntitySqlName()));
		    }
		}
	    }
	}
    }

    /**
     * Launches the generation of the create table statements for the
     * many-to-many relations.
     * 
     * @param global
     *            All the entities.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    private static void buildManyToManyLinkTables(final DataLayerSpecifications global,
	    final CodeFileWriter writer) throws IOException {
	ArrayList<String> tablesCreated = new ArrayList<String>();

	for (Entity entity : global.getEntities()) {
	    for (Relation relation : entity.getRelations()) {
		if (relation.isManyToMany()) {
		    if (!tablesCreated.contains(relation.getNameOfLinkTable())) {
			tablesCreated.add(relation.getNameOfLinkTable());
			String query = MySql5Provider.buildCreateStatementForJunctionTable(
			        entity.getSqlName(), relation.getEntitySqlName());
			writer.write(query);
		    }
		}
	    }
	}
    }

    public static String buildJunctionTableName(String table1, String table2) {
	String first = table1;
	String second = table2;

	if (table1.compareTo(table2) > 0) {
	    first = table2;
	    second = table1;
	}

	return first + "_" + second;
    }

    /**
     * Launches the generation of the alter table statements from the
     * one-to-many relation.
     * 
     * @param global
     *            All the entities.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    private static void buildPrimaryAlterTables(final DataLayerSpecifications global,
	    final CodeFileWriter writer) throws IOException {
	for (Entity entity : global.getEntities()) {
	    for (Relation relation : entity.getRelations()) {
		if (relation.getType() == RelationType.Type.ONE) {
		    writer.write(MySql5Provider.buildForeignKeyStatement(entity.getSqlName(),
			    relation.getEntity().getSqlName()));
		}
	    }
	}
    }

    /**
     * Launches the generation of the entity tables.
     * 
     * @param global
     *            All the entities.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    private static void buildPrimaryTables(final DataLayerSpecifications global, final CodeFileWriter writer)
	    throws IOException {
	for (Entity entity : global.getEntities()) {
	    MySql5Provider.provideStandardCreateTable(entity, writer);
	}
    }

    /**
     * Launches the generation of the unicity constraints.
     * 
     * @param global
     *            All the entities.
     * @param writer
     *            The file writer.
     * @throws IOException
     *             From the file writer.
     */
    private static void buildUnicityConstraints(final DataLayerSpecifications global,
	    final CodeFileWriter writer) throws IOException {
	for (Entity entity : global.getEntities()) {
	    int unicityCount = 1;
	    for (UnicityConstraint unicity : entity.getUnicityConstraints()) {
		MySql5Provider.provideUnicityConstraint(unicity, entity.getSqlName(), unicityCount,
		        writer);
		unicityCount++;
	    }
	}
    }

    /**
     * Launches the generation of the SQL script.
     * 
     * @param global
     *            All the entities informations.
     * @param fileName
     *            The file to generate.
     */
    public static void generate(final DataLayerSpecifications global, final String fileName) {
	try {
	    FileWriter fileWriter = new FileWriter(fileName);
	    CodeFileWriter writer = new CodeFileWriter(fileWriter);
	    buildPrimaryTables(global, writer);
	    buildManyToManyLinkTables(global, writer);
	    buildPrimaryAlterTables(global, writer);
	    buildManyToManyAlterTables(global, writer);
	    buildUnicityConstraints(global, writer);
	    fileWriter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * To avoid instanciation.
     */
    private SQLGenerator() {
    }
}
