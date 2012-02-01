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

package tests;

import domain.EntityInformations;
import domain.GlobalInformations;

import junit.framework.TestCase;


/**
 * Tests for the GlobalInformations class.
 */
public class GlobalInformationsTest extends TestCase {

    /**
     * Test the addEntity and isEntityDefied methods.
     */
    public final void testAddEntityIsEntityDefined() {
        String entityName = "cheese";
        GlobalInformations global = new GlobalInformations();

        assertFalse(global.isEntityDefined(entityName));

        EntityInformations entity = new EntityInformations(entityName);
        global.addEntity(entity);

        assertTrue(global.isEntityDefined(entityName));
        assertFalse(global.isEntityDefined("other"));
    }

    /**
     * Test the getEntity method.
     */
    public final void testGetEntity() {
        String entityName = "cheese";
        GlobalInformations global = new GlobalInformations();

        EntityInformations entity = new EntityInformations(entityName);
        global.addEntity(entity);

        assertNotNull(global.getEntity(entityName));
        assertNull(global.getEntity("other"));
    }

    /**
     * Test the getEntities method.
     */
    public final void testGetEntities() {
        final int entityCount = 3;
        GlobalInformations global = new GlobalInformations();
        global.addEntity(new EntityInformations("first"));
        global.addEntity(new EntityInformations("second"));
        global.addEntity(new EntityInformations("third"));
        assertTrue(global.getEntities().size() == entityCount);
    }
}
