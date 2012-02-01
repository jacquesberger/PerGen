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

package transformers;

import java.util.Collection;
import java.util.Hashtable;

import domain.EntityInformations;
import domain.FieldInformations;
import domain.GlobalInformations;
import exceptions.AmbiguousEntityNameException;
import exceptions.AmbiguousFieldNameException;

/**
 * Performs the transformations of original names into code names.
 */
public final class JavaTransformer {

    /**
     * To avoid instanciation.
     */
    private JavaTransformer() {
    }

    /**
     * Performs these transformations:
     * 1. Every character immediatly following a _ is capitalized.
     * 2. Every other characters are not capitalized.
     * 3. Every _ are removed.
     * 4. If firstCharCapital is true, the first character is capitalized.
     * @param identifier The string to transform.
     * @param firstCharCapital true if the first character must be capitalized,
     *                         false otherwise.
     * @return The transformed string.
     */
    private static String transformIdentifier(final String identifier,
                                              final boolean firstCharCapital) {
        char[] workString = identifier.toLowerCase().toCharArray();
        if (firstCharCapital) {
            workString[0] = Character.toUpperCase(workString[0]);
        }

        int charIndex;
        for (charIndex = 1; charIndex < workString.length; charIndex++) {
            if (workString[charIndex] == '_') {
                workString[charIndex] = ' ';
                if (charIndex < workString.length - 1) {
                    workString[charIndex + 1] =
                               Character.toUpperCase(workString[charIndex + 1]);
                }
            }
        }

        charIndex = 0;
        while (charIndex < workString.length) {
            if (workString[charIndex] == ' ') {
                for (int indexToEnd = charIndex;
                     indexToEnd < workString.length - 1; indexToEnd++) {
                    workString[indexToEnd] = workString[indexToEnd + 1];
                }
                workString[workString.length - 1] = '\0';
            } else {
                charIndex++;
            }
        }

        String modified = new String(workString);
        modified = modified.trim();
        return modified;
    }

    /**
     * Apply the code transformations to the entities and fields.
     * @param infos The global informations.
     */
    public static void transform(final GlobalInformations infos) {
        transformEntities(infos.getEntities());
    }

    /**
     * Transform the entity names.
     * @param entities A collection of entities.
     */
    private static void transformEntities(
                                final Collection<EntityInformations> entities) {
        Hashtable<String, EntityInformations> producedNames =
                                    new Hashtable<String, EntityInformations>();

        for (EntityInformations entity : entities) {
            String codeName = transformIdentifier(entity.getOriginalName(),
                                                  true);

            EntityInformations possibleDuplicate = producedNames.get(codeName);
            if (possibleDuplicate != null) {
                throw new AmbiguousEntityNameException(entity.getOriginalName(),
                                            possibleDuplicate.getOriginalName(),
                                            "code", codeName);
            }

            entity.setCodeName(codeName);
            producedNames.put(codeName, entity);
            transformFields(entity.getOriginalName(), entity.getFields());
        }
    }

    /**
     * Transform the field names.
     * @param entityName The name of the entity.
     * @param fields A collection of fields in the entity.
     */
    private static void transformFields(final String entityName,
                                   final Collection<FieldInformations> fields) {
        Hashtable<String, FieldInformations> producedNames =
                                     new Hashtable<String, FieldInformations>();

        for (FieldInformations field : fields) {
            String codeName = transformIdentifier(field.getOriginalName(),
                                                  false);

            FieldInformations possibleDuplicate = producedNames.get(codeName);
            if (possibleDuplicate != null) {
                throw new AmbiguousFieldNameException(field.getOriginalName(),
                                            possibleDuplicate.getOriginalName(),
                                            entityName, "code", codeName);
            }

            field.setCodeName(codeName);
            producedNames.put(codeName, field);

            char[] workingName = codeName.toCharArray();
            workingName[0] = Character.toUpperCase(workingName[0]);
            String capitalized = new String(workingName);
            field.setGetterName("get" + capitalized);
            field.setSetterName("set" + capitalized);
        }
    }

}
