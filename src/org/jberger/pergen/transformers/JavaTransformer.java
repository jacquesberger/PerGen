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

package org.jberger.pergen.transformers;

public final class JavaTransformer {

    public static String snakeCaseIdentifierToPascalCase(String identifier) {
	char[] workString = snakeCaseIdentifierToCamelCase(identifier).toCharArray();
	if (workString.length > 0) {
            workString[0] = Character.toUpperCase(workString[0]);
        }
	
	return new String(workString);
    }

    public static String snakeCaseIdentifierToCamelCase(String identifier) {
	char[] workString = identifier.toLowerCase().toCharArray();

	boolean firstCharacterMet = false;
	for (int charIndex = 0; charIndex < workString.length; charIndex++) {
	    if (workString[charIndex] == '_' && firstCharacterMet) {
		if (charIndex < workString.length - 1) {
                    workString[charIndex + 1] = Character.toUpperCase(workString[charIndex + 1]);
                }
	    } else {
		firstCharacterMet = true;
	    }
	}

	return new String(workString).replace("_", "");
    }
}
