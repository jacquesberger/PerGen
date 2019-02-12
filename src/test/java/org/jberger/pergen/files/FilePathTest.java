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
package org.jberger.pergen.files;

import org.junit.jupiter.api.*;

public class FilePathTest {

    @Test
    public void testExtractDirectoryEmptyString() {
        Assertions.assertEquals("", FilePath.extractDirectory(""));
    }

    @Test
    public void testExtractDirectoryNormalCase() {
        Assertions.assertEquals("", FilePath.extractDirectory("timesheet.json"));
    }

    @Test
    public void testExtractDirectoryWithDir() {
        Assertions.assertEquals("test", FilePath.extractDirectory("test/timesheet.json"));
    }

    @Test
    public void testExtractDirectoryWithCompleteDir() {
        Assertions.assertEquals("/user/test", FilePath.extractDirectory("/user/test/timesheet.json"));
    }

    @Test
    public void testExtractDirectoryWithDirWindows() {
        Assertions.assertEquals("test", FilePath.extractDirectory("test\\timesheet.json"));
    }

    @Test
    public void testExtractDirectoryWithCompleteDirWindows() {
        Assertions.assertEquals("C:\\user\\test", FilePath.extractDirectory("C:\\user\\test\\timesheet.json"));
    }
}