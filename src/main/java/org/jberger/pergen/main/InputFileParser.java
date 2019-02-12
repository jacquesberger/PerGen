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

package org.jberger.pergen.main;

import java.io.BufferedReader;
import java.io.PushbackReader;
import java.io.StringReader;
import org.jberger.pergen.domain.DataLayerSpecifications;
import org.jberger.pergen.domain.RelationAnalyzer;
import org.jberger.pergen.explorers.EntityAndFieldExplorer;
import org.jberger.pergen.explorers.RelationExplorer;
import org.jberger.pergen.generated.lexer.Lexer;
import org.jberger.pergen.generated.node.Node;
import org.jberger.pergen.generated.parser.Parser;

public class InputFileParser {

    private String content;

    public InputFileParser(String inputFileContent) {
        this.content = inputFileContent;
    }

    public DataLayerSpecifications extractSpecifications()
            throws Exception {
        Node ast = parseInputFile();

        DataLayerSpecifications specs = new DataLayerSpecifications();
        ast.apply(new EntityAndFieldExplorer(specs));
        RelationExplorer relationExplorer = new RelationExplorer();
        ast.apply(relationExplorer);

        RelationAnalyzer analyzer = new RelationAnalyzer(relationExplorer.getRelations());
        analyzer.analyse(specs);
        return specs;
    }

    private Node parseInputFile() throws Exception {
        PushbackReader lecture = new PushbackReader(new BufferedReader(new StringReader(
                content)));

        Lexer lexer = new Lexer(lecture);
        Parser parser = new Parser(lexer);
        Node ast = parser.parse();
        return ast;
    }
}
