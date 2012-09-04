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

/*
 * NOT A GENERATED FILE, FOR TEST PURPOSE ONLY.
 */

package org.jberger.pergen.prototype;

import java.sql.Connection;


/**
 * Main to test the DAOs.
 */
public final class PrototypeTest {

    /**
     * Main for DAOs testing.
     * @param args Program arguments...none here.
     */
    public static void main(final String[] args) {

        try {
            Connection cnx = ConnectionManager.getMySQLConnection();
            AuthorDAO a = new AuthorDAO(cnx);
            a.delete(3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Private constructor to avoid instanciation.
     */
    private PrototypeTest() {

    }
}
