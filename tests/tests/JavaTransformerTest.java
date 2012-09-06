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

package tests;

import org.jberger.pergen.transformers.JavaTransformer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JavaTransformerTest {

    @Test
    public void testCamelCaseOneWord() {
	assertEquals("test", JavaTransformer.snakeCaseIdentifierToCamelCase("test"));
    }
    
    @Test
    public void testPascalCaseOneWord() {
	assertEquals("Test", JavaTransformer.snakeCaseIdentifierToPascalCase("test"));
    }

    @Test
    public void testCamelCaseTwoWords() {
	assertEquals("testWord", JavaTransformer.snakeCaseIdentifierToCamelCase("test_word"));
    }
    
    @Test
    public void testPascalCaseTwoWords() {
	assertEquals("TestWord", JavaTransformer.snakeCaseIdentifierToPascalCase("test_word"));
    }
    
    @Test
    public void testCamelCaseTwoWordsUpCase() {
	assertEquals("testWord", JavaTransformer.snakeCaseIdentifierToCamelCase("TEST_WORD"));
    }
    
    @Test
    public void testPascalCaseTwoWordsUpCase() {
	assertEquals("TestWord", JavaTransformer.snakeCaseIdentifierToPascalCase("TEST_WORD"));
    }
    
    @Test
    public void testCamelCaseTwoWordsMultipleUnderScore() {
	assertEquals("testWord", JavaTransformer.snakeCaseIdentifierToCamelCase("TEST__WORD"));
    }
    
    @Test
    public void testPascalCaseTwoWordsMultipleUnderScore() {
	assertEquals("TestWord", JavaTransformer.snakeCaseIdentifierToPascalCase("TEST__WORD"));
    }

    
    @Test
    public void testCamelCaseMultipleWords() {
	assertEquals("thisTestIsSuperLong", JavaTransformer.snakeCaseIdentifierToCamelCase("this_test_is_super_long"));
    }
    
    @Test
    public void testPascalCaseMultipleWords() {
	assertEquals("ThisTestIsSuperLong", JavaTransformer.snakeCaseIdentifierToPascalCase("this_test_is_super_long"));
    }
    
    @Test
    public void testMultipleUnderscore() {
	assertEquals("thisTest", JavaTransformer.snakeCaseIdentifierToCamelCase("this_____test"));
    }
    
    @Test
    public void testBeginningWithUnderscore() {
	assertEquals("thisIsTesting", JavaTransformer.snakeCaseIdentifierToCamelCase("_this_is_testing"));
    }
    
    @Test
    public void testBeginningWithUnderscorePascalCase() {
	assertEquals("ThisIsTesting", JavaTransformer.snakeCaseIdentifierToPascalCase("_this_is_testing"));
    }
    
    @Test
    public void testFinishingWithUnderscore() {
	assertEquals("thisIsTesting", JavaTransformer.snakeCaseIdentifierToCamelCase("this_is_testing_"));
    }
    
    @Test
    public void testEmptyString() {
	assertEquals("", JavaTransformer.snakeCaseIdentifierToCamelCase(""));
    }
    
    @Test
    public void testEmptyStringPascalCase() {
	assertEquals("", JavaTransformer.snakeCaseIdentifierToPascalCase(""));
    }
    
    @Test
    public void testOnlyUnderscores() {
	assertEquals("", JavaTransformer.snakeCaseIdentifierToCamelCase("____"));
    }
    
    @Test
    public void testOnlyUnderscoresPascalCase() {
	assertEquals("", JavaTransformer.snakeCaseIdentifierToPascalCase("____"));
    }
}
