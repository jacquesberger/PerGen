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
import java.sql.DriverManager;

/**
 * Test purpose only. Gives a Connection object.
 */
public final class ConnectionManager {

    /**
     * Private construction to avoid instanciation.
     */
    private ConnectionManager() {

    }

    /**
     * Creates an active MySQL connection.
     * @return An active MySQL connection.
     * @throws Exception Multiple possibilities.
     */
    public static Connection getMySQLConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql:///test";
        String username = "prototype";
        String password = "test";

        return newJdbcConnection(driver, url + "?user=" + username
                                         + "&password=" + password);
    }

    /**
     * Creates a JDBC connection.
     * @param driver Name of the JDBC driver.
     * @param connectionString Connection string to connect to database.
     * @return A JDBC connection.
     * @throws Exception Multiple possibilities.
     */
    private static Connection newJdbcConnection(final String driver,
                                                final String connectionString)
                                                throws Exception {

        Connection myConnection;

        Class.forName(driver).newInstance();
        myConnection = DriverManager.getConnection(connectionString);
        myConnection.setAutoCommit(true);

        return myConnection;
    }
}
