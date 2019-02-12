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

import org.junit.jupiter.api.*;

public class IdentifierTransformerTest {

    @Test
    public void testCamelCaseOneWord() {
        Assertions.assertEquals("test", IdentifierTransformer.snakeCaseIdentifierToCamelCase("test"));
    }

    @Test
    public void testPascalCaseOneWord() {
        Assertions.assertEquals("Test", IdentifierTransformer.snakeCaseIdentifierToPascalCase("test"));
    }

    @Test
    public void testCamelCaseTwoWords() {
        Assertions.assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("test_word"));
    }

    @Test
    public void testPascalCaseTwoWords() {
        Assertions.assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("test_word"));
    }

    @Test
    public void testCamelCaseTwoWordsUpCase() {
        Assertions.assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("TEST_WORD"));
    }

    @Test
    public void testPascalCaseTwoWordsUpCase() {
        Assertions.assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("TEST_WORD"));
    }

    @Test
    public void testCamelCaseTwoWordsMultipleUnderScore() {
        Assertions.assertEquals("testWord", IdentifierTransformer.snakeCaseIdentifierToCamelCase("TEST__WORD"));
    }

    @Test
    public void testPascalCaseTwoWordsMultipleUnderScore() {
        Assertions.assertEquals("TestWord", IdentifierTransformer.snakeCaseIdentifierToPascalCase("TEST__WORD"));
    }


    @Test
    public void testCamelCaseMultipleWords() {
        Assertions.assertEquals("thisTestIsSuperLong", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_test_is_super_long"));
    }

    @Test
    public void testPascalCaseMultipleWords() {
        Assertions.assertEquals("ThisTestIsSuperLong", IdentifierTransformer.snakeCaseIdentifierToPascalCase("this_test_is_super_long"));
    }

    @Test
    public void testMultipleUnderscore() {
        Assertions.assertEquals("thisTest", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_____test"));
    }

    @Test
    public void testBeginningWithUnderscore() {
        Assertions.assertEquals("thisIsTesting", IdentifierTransformer.snakeCaseIdentifierToCamelCase("_this_is_testing"));
    }

    @Test
    public void testBeginningWithUnderscorePascalCase() {
        Assertions.assertEquals("ThisIsTesting", IdentifierTransformer.snakeCaseIdentifierToPascalCase("_this_is_testing"));
    }

    @Test
    public void testFinishingWithUnderscore() {
        Assertions.assertEquals("thisIsTesting", IdentifierTransformer.snakeCaseIdentifierToCamelCase("this_is_testing_"));
    }

    @Test
    public void testEmptyString() {
        Assertions.assertEquals("", IdentifierTransformer.snakeCaseIdentifierToCamelCase(""));
    }

    @Test
    public void testEmptyStringPascalCase() {
        Assertions.assertEquals("", IdentifierTransformer.snakeCaseIdentifierToPascalCase(""));
    }

    @Test
    public void testOnlyUnderscores() {
        Assertions.assertEquals("", IdentifierTransformer.snakeCaseIdentifierToCamelCase("____"));
    }

    @Test
    public void testOnlyUnderscoresPascalCase() {
        Assertions.assertEquals("", IdentifierTransformer.snakeCaseIdentifierToPascalCase("____"));
    }

    @Test
    public void testTransformIdentifierNormal() {
        Assertions.assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("first_name"), "FIRST_NAME");
    }

    @Test
    public void testTransformIdentifierMixed() {
        Assertions.assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("Last_Name"), "LAST_NAME");
    }

    @Test
    public void testTransformIdentifierNumber() {
        Assertions.assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase("address2"), "ADDRESS2");
    }

    @Test
    public void testTransformIdentifierEmpty() {
        Assertions.assertEquals(IdentifierTransformer.snakeCaseIdentifierToSqlStandardCase(""), "");
    }
}
