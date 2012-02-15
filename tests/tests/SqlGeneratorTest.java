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


import generators.SQLGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SqlGeneratorTest {

    @Test
    public void testBuildJunctionTableNameNaturalOrder() {
	assertEquals(SQLGenerator.buildJunctionTableName("AUTHOR", "BOOK"), "AUTHOR_BOOK");
    }

    @Test
    public void testBuildJunctionTableNameReverseOrder() {
	assertEquals(SQLGenerator.buildJunctionTableName("BOOK", "AUTHOR"), "AUTHOR_BOOK");
    }
}
