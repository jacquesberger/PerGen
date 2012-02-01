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

import junit.framework.TestCase;

import org.junit.Test;

import codeproviders.MySql5Provider;

public class MySql5ProviderTest extends TestCase {

    @Test
    public void testBuildCreateStatementForJunctionTable() {
	String query = MySql5Provider.buildCreateStatementForJunctionTable("AUTHOR", "BOOK");
	String value = "CREATE TABLE AUTHOR_BOOK (\n"
	        + "  AUTHOR_BOOK_ID INTEGER NOT NULL auto_increment,\n"
	        + "  AUTHOR_ID INTEGER NOT NULL,\n  BOOK_ID INTEGER NOT NULL,\n"
	        + "  CONSTRAINT PK_AUTHOR_BOOK PRIMARY KEY (AUTHOR_BOOK_ID)\n);\n\n";
	assertEquals(query, value);
    }

    @Test
    public void testBuildForeignKeyStatement() {
	String query = MySql5Provider.buildForeignKeyStatement("AUTHOR", "BOOK");
	String value = "ALTER TABLE AUTHOR ADD (CONSTRAINT FK_AUTHOR_BOOK FOREIGN KEY ("
	        + "BOOK_ID) REFERENCES BOOK(BOOK_ID));\n\n";
	assertEquals(value, query);
    }

    @Test
    public void testBuildForeignKeyStatementsForJunctionTable() {
	String value = "ALTER TABLE AUTHOR_BOOK ADD (CONSTRAINT FK_AUTHOR_BOOK_AUTHOR FOREIGN KEY ("
	        + "AUTHOR_ID) REFERENCES AUTHOR(AUTHOR_ID));\n\n"
	        + "ALTER TABLE AUTHOR_BOOK ADD (CONSTRAINT FK_AUTHOR_BOOK_BOOK FOREIGN KEY ("
	        + "BOOK_ID) REFERENCES BOOK(BOOK_ID));\n\n";
	String queries = MySql5Provider.buildForeignKeyStatementsForJunctionTable("AUTHOR", "BOOK");
	assertEquals(queries, value);
    }
}
