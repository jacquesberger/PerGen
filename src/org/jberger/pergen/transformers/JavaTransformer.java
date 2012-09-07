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

package org.jberger.pergen.transformers;

import java.util.Collection;
import java.util.Hashtable;
import org.jberger.pergen.domain.EntityInformations;
import org.jberger.pergen.domain.Field;
import org.jberger.pergen.domain.GlobalInformations;
import org.jberger.pergen.exceptions.AmbiguousEntityNameException;
import org.jberger.pergen.exceptions.AmbiguousFieldNameException;

/**
 * Performs the transformations of original names into code names.
 */
public final class JavaTransformer {

    public static String snakeCaseIdentifierToPascalCase(String identifier) {
	char[] workString = snakeCaseIdentifierToCamelCase(identifier).toCharArray();
	if (workString.length > 0) {
            workString[0] = Character.toUpperCase(workString[0]);
        }
	
	return new String(workString);
    }

    public static String snakeCaseIdentifierToCamelCase(String identifier) {
	char[] workString = identifier.toLowerCase().toCharArray();

	boolean firstCharacterMet = false;
	for (int charIndex = 0; charIndex < workString.length; charIndex++) {
	    if (workString[charIndex] == '_' && firstCharacterMet) {
		if (charIndex < workString.length - 1) {
                    workString[charIndex + 1] = Character.toUpperCase(workString[charIndex + 1]);
                }
	    } else {
		firstCharacterMet = true;
	    }
	}

	return new String(workString).replace("_", "");
    }

    /**
     * Apply the code transformations to the entities and fields.
     * 
     * @param infos
     *            The global informations.
     */
    public static void transform(final GlobalInformations infos) {
	transformEntities(infos.getEntities());
    }

    /**
     * Transform the entity names.
     * 
     * @param entities
     *            A collection of entities.
     */
    private static void transformEntities(final Collection<EntityInformations> entities) {
	Hashtable<String, EntityInformations> producedNames = new Hashtable<String, EntityInformations>();

	for (EntityInformations entity : entities) {
	    String codeName = snakeCaseIdentifierToPascalCase(entity.getOriginalName());

	    EntityInformations possibleDuplicate = producedNames.get(codeName);
	    if (possibleDuplicate != null) {
		throw new AmbiguousEntityNameException(entity.getOriginalName(),
		        possibleDuplicate.getOriginalName(), codeName);
	    }

	    entity.setCodeName(codeName);
	    producedNames.put(codeName, entity);
	    transformFields(entity.getOriginalName(), entity.getFields());
	}
    }

    /**
     * Transform the field names.
     * 
     * @param entityName
     *            The name of the entity.
     * @param fields
     *            A collection of fields in the entity.
     */
    private static void transformFields(final String entityName,
	    final Collection<Field> fields) {
	Hashtable<String, Field> producedNames = new Hashtable<String, Field>();

	for (Field field : fields) {
            String codeName = field.getJavaName();
	    Field possibleDuplicate = producedNames.get(codeName);
	    if (possibleDuplicate != null) {
		throw new AmbiguousFieldNameException(field.getOriginalName(),
		        possibleDuplicate.getOriginalName(), entityName, codeName);
	    }

	    producedNames.put(codeName, field);
	}
    }

}
