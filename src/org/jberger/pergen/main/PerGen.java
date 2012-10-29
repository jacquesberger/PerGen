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

import org.jberger.pergen.domain.DataLayerSpecifications;
import org.jberger.pergen.files.FileLoader;
import org.jberger.pergen.files.FilePath;
import org.jberger.pergen.files.PrintStreamWrapper;
import org.jberger.pergen.generators.JavaGenerator;
import org.jberger.pergen.generators.SQLGenerator;
import org.jberger.pergen.output.MessageWriter;

public final class PerGen {

    public static void main(final String[] args) {
        MessageWriter writer = new MessageWriter(new PrintStreamWrapper(System.out));
	if (args.length != 1) {
	    writer.displayUsage();
	    System.exit(1);
	}

	try {
            String inputFileContent = FileLoader.loadFileIntoString(args[0]);
            String workingDirectory = FilePath.extractDirectory(args[0]);
            InputFileParser parser = new InputFileParser(inputFileContent);
	    DataLayerSpecifications specs = parser.extractSpecifications();
            generateCode(workingDirectory, specs);
	} catch (Exception e) {
            writer.displayErrorMessage(e);
	}
    }


    private static void generateCode(String directory, DataLayerSpecifications global) {
	SQLGenerator.generate(global, directory + "\\script.sql");
        JavaGenerator java = new JavaGenerator(directory);
	java.generate(global);
    }
}
