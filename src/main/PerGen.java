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

/**
 * Contains the main.
 */
public final class PerGen {

    /**
     * The main of the program.
     * @param args Program's arguments.
     */
    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong parameters...");
            System.out.println("Use : java PerGen <file>");
            System.out.println("<file> = absolute or relative file path, "
                               + "no spaces allowed");

            System.exit(1);
        }

        String completeFilePath = args[0];
        String directory;
        int lastIndex = completeFilePath.lastIndexOf('\\');
        if (lastIndex == -1) {
            lastIndex = completeFilePath.lastIndexOf('/');
            if (lastIndex == -1) {
                directory = "";
            } else {
                directory = completeFilePath.substring(0, lastIndex);
            }
        } else {
            directory = completeFilePath.substring(0, lastIndex);
        }

        try {
            PushbackReader lecture = new PushbackReader(
                                     new BufferedReader(
                                     new FileReader(completeFilePath)));
            try {
                Lexer lexer = new Lexer(lecture);
                Parser parser = new Parser(lexer);

                Node ast = parser.parse();

                GlobalInformations global = new GlobalInformations();
                ast.apply(new EntityAndFieldExplorer(global));
                RelationExplorer relationExplorer = new RelationExplorer();
                ast.apply(relationExplorer);
                SqlTransformer.transform(global);
                JavaTransformer.transform(global);
                RelationAnalyzer.analyse(global,
                                         relationExplorer.getRelations());
                SQLGenerator.generate(global, directory + "\\script.sql");
                JavaGenerator.generatePOJOs(global, directory);
                JavaGenerator.generateDAOs(global, directory);
            } finally {
                lecture.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To avoid instanciation.
     */
    private PerGen() {
    }
}
