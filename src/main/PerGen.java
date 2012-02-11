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

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;

import domain.GlobalInformations;
import domain.RelationAnalyzer;
import explorers.EntityAndFieldExplorer;
import explorers.RelationExplorer;

import node.Node;

import parser.Parser;

import lexer.Lexer;
import generators.SQLGenerator;
import generators.JavaGenerator;
import transformers.SqlTransformer;
import transformers.JavaTransformer;

public final class PerGen {

    public static void main(final String[] args) {
	if (args.length != 1) {
	    showUsage();
	    System.exit(1);
	}

	try {
	    generateCodeFromUserSpecifications(args[0]);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static void generateCodeFromUserSpecifications(String completeFilePath)
	    throws Exception {
	Node ast = parseInputFile(completeFilePath);

	GlobalInformations global = new GlobalInformations();
	ast.apply(new EntityAndFieldExplorer(global));
	RelationExplorer relationExplorer = new RelationExplorer();
	ast.apply(relationExplorer);

	applyCodeTransformations(global);
	RelationAnalyzer.analyse(global, relationExplorer.getRelations());
	generateCode(extractDirectoryFromFilePath(completeFilePath), global);
    }

    private static Node parseInputFile(String completeFilePath) throws Exception {
	PushbackReader lecture = new PushbackReader(new BufferedReader(new FileReader(
	        completeFilePath)));

	Lexer lexer = new Lexer(lecture);
	Parser parser = new Parser(lexer);
	Node ast = parser.parse();
	return ast;
    }

    private static void applyCodeTransformations(GlobalInformations global) {
	SqlTransformer.transform(global);
	JavaTransformer.transform(global);
    }

    private static void generateCode(String directory, GlobalInformations global) {
	SQLGenerator.generate(global, directory + "\\script.sql");
	JavaGenerator.generatePOJOs(global, directory);
	JavaGenerator.generateDAOs(global, directory);
    }

    private static String extractDirectoryFromFilePath(String completeFilePath) {
	String pathSeparator = File.pathSeparator;
	int lastIndex = completeFilePath.lastIndexOf(pathSeparator);
	if (lastIndex == -1) {
	    return "";
	} else {
	    return completeFilePath.substring(0, lastIndex);
	}
    }

    private static void showUsage() {
	System.out.println("Wrong parameters...");
	System.out.println("Use : java PerGen <file>");
	System.out.println("<file> = absolute or relative file path, " + "no spaces allowed");
    }
}
