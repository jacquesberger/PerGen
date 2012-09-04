/* Copyright 2011 Jacques Berger

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


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.jberger.pergen.transformers.SqlTransformer;

public class SqlTransformerTest {

    @Test
    public void testTransformIdentifierNormal() {
	assertEquals(SqlTransformer.transformIdentifier("first_name"), "FIRST_NAME");
    }

    @Test
    public void testTransformIdentifierMixed() {
	assertEquals(SqlTransformer.transformIdentifier("Last_Name"), "LAST_NAME");
    }
    
    @Test
    public void testTransformIdentifierNumber() {
	assertEquals(SqlTransformer.transformIdentifier("address2"), "ADDRESS2");
    }
    
    @Test
    public void testTransformIdentifierEmpty() {
	assertEquals(SqlTransformer.transformIdentifier(""), "");
    }
}
