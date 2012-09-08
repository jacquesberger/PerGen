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

package org.jberger.pergen.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PushbackReader;
import org.jberger.pergen.domain.GlobalInformations;
import org.jberger.pergen.domain.RelationAnalyzer;
import org.jberger.pergen.explorers.EntityAndFieldExplorer;
import org.jberger.pergen.explorers.RelationExplorer;
import org.jberger.pergen.files.FilePath;
import org.jberger.pergen.generators.JavaGenerator;
import org.jberger.pergen.generators.SQLGenerator;
import org.jberger.pergen.lexer.Lexer;
import org.jberger.pergen.node.Node;
import org.jberger.pergen.parser.Parser;

public final class PerGen {

    public static void main(final String[] args) {
	if (args.length != 1) {
	    ConsoleWriter.displayUsage();
	    System.exit(1);
	}

	try {
	    generateCodeFromUserSpecifications(args[0]);
	} catch (Exception e) {
            ConsoleWriter.displayErrorMessage(e);
	}
    }

    private static void generateCodeFromUserSpecifications(String completeFilePath)
	    throws Exception {
	Node ast = parseInputFile(completeFilePath);

	GlobalInformations global = new GlobalInformations();
	ast.apply(new EntityAndFieldExplorer(global));
	RelationExplorer relationExplorer = new RelationExplorer();
	ast.apply(relationExplorer);

	RelationAnalyzer.analyse(global, relationExplorer.getRelations());
	generateCode(FilePath.extractDirectory(completeFilePath), global);
    }

    private static Node parseInputFile(String completeFilePath) throws Exception {
	PushbackReader lecture = new PushbackReader(new BufferedReader(new FileReader(
	        completeFilePath)));

	Lexer lexer = new Lexer(lecture);
	Parser parser = new Parser(lexer);
	Node ast = parser.parse();
	return ast;
    }

    private static void generateCode(String directory, GlobalInformations global) {
	SQLGenerator.generate(global, directory + "\\script.sql");
	JavaGenerator.generatePOJOs(global, directory);
	JavaGenerator.generateDAOs(global, directory);
    }
}
