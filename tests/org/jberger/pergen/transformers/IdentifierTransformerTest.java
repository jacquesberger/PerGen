/* Copyright 2012 Jacques Berger

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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IdentifierTransformerTest {

    @Test
    public void testCamelCaseOneWord() {
	assertEquals("test", IdentifierTransformer.snakeCaseIdentifierToCamelCase("test"));
    }
    
    @Test
    public void testPascalCaseOneWord() {
	assertEquals("Test", IdentifierTransformer.snakeCaseIdentifierToPascalCase("test"));
    }

    @Test
    public void testCamelCaseTwoWords() {
	assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("test_word"));
    }
    
    @Test
    public void testPascalCaseTwoWords() {
	assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("test_word"));
    }
    
    @Test
    public void testCamelCaseTwoWordsUpCase() {
	assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("TEST_WORD"));
    }
    
    @Test
    public void testPascalCaseTwoWordsUpCase() {
	assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("TEST_WORD"));
    }
    
    @Test
    public void testCamelCaseTwoWordsMultipleUnderScore() {
	assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("TEST__WORD"));
    }
    
    @Test
    public void testPascalCaseTwoWordsMultipleUnderScore() {
	assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("TEST__WORD"));
    }

    
    @Test
    public void testCamelCaseMultipleWords() {
	assertEquals("thisTestIsSuperLong", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_test_is_super_long"));
    }
    
    @Test
    public void testPascalCaseMultipleWords() {
	assertEquals("ThisTestIsSuperLong", IdentifierTransformer.snakeCaseIdentifierToPascalCase("this_test_is_super_long"));
    }
    
    @Test
    public void testMultipleUnderscore() {
	assertEquals("thisTest", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_____test"));
    }
    
    @Test
    public void testBeginningWithUnderscore() {
	assertEquals("thisIsTesting", IdentifierTransformer.snakeCaseIdentifierToCamelCase("_this_is_testing"));
    }
    
    @Test
    public void testBeginningWithUnderscorePascalCase() {
	assertEquals("ThisIsTesting", IdentifierTransformer.snakeCaseIdentifierToPascalCase("_this_is_testing"));
    }
    
    @Test
    public void testFinishingWithUnderscore() {
	assertEquals("thisIsTesting", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_is_testing_"));
    }
    
    @Test
    public void testEmptyString() {
	assertEquals("", IdentifierTransformer.snakeCaseIdentifierToCamelCase(""));
    }
    
    @Test
    public void testEmptyStringPascalCase() {
	assertEquals("", IdentifierTransformer.snakeCaseIdentifierToPascalCase(""));
    }
    
    @Test
    public void testOnlyUnderscores() {
	assertEquals("", IdentifierTransformer.snakeCaseIdentifierToCamelCase("____"));
    }
    
    @Test
    public void testOnlyUnderscoresPascalCase() {
	assertEquals("", IdentifierTransformer.snakeCaseIdentifierToPascalCase("____"));
    }
    
        @Test
    public void testTransformIdentifierNormal() {
	assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("first_name"), "FIRST_NAME");
    }

    @Test
    public void testTransformIdentifierMixed() {
	assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("Last_Name"), "LAST_NAME");
    }
    
    @Test
    public void testTransformIdentifierNumber() {
	assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("address2"), "ADDRESS2");
    }
    
    @Test
    public void testTransformIdentifierEmpty() {
	assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase(""), "");
    }
}
