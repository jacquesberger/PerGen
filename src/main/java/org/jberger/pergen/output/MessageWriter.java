/*
 * Copyright 2012 Jacques Berger.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jberger.pergen.output;

import org.jberger.pergen.files.PrintStreamWrapper;

public class MessageWriter {
    private PrintStreamWrapper out;
    
    public MessageWriter(PrintStreamWrapper out) {
        this.out = out;
    }

    public void displayUsage() {
        out.println("Wrong parameters...");
        out.println("Use : java PerGen <file>");
        out.println("<file> = absolute or relative file path, no spaces allowed");
    }
    
    public void displayErrorMessage(Exception e) {
        out.println("Error: " + e.getLocalizedMessage());
    }
}