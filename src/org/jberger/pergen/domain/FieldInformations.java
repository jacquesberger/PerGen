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

package org.jberger.pergen.domain;

/**
 * Contains all the informations about a field. A field is contained
 * within an entity.
 */
public class FieldInformations {

    /**
     * Corresponds to the original name of the field.
     */
    private String originalName;

    /**
     * The sql name of the field.
     */
    private String sqlName;

    /**
     * The code name of the field.
     */
    private String codeName;

    /**
     * The name of the setter of the field.
     */
    private String setterName;

    /**
     * The name of the getter of the field.
     */
    private String getterName;

    /**
     * Corresponds to the original data type of the field.
     */
    private FieldType.Type originalDataType;

    /**
     * Indicates whether the field is required or not. In other words,
     * true if it is mandatory field, false otherwise. A null value will not
     * be accepted in the generated class.
     */
    private boolean isRequired;

    /**
     * String length. May be defined if the data type is STRING.
     */
    private Integer stringLength;

    /**
     * Creates a new instance.
     * @param fieldName The original name of the field.
     * @param dataType The data type of the field.
     * @param fieldRequired true if the field is required, false otherwise.
     */
    public FieldInformations(final String fieldName,
                             final FieldType.Type dataType,
                             final boolean fieldRequired) {
        originalName = fieldName;
        originalDataType = dataType;
        isRequired = fieldRequired;
        stringLength = null;
    }

    /**
     * Get the original name of the field.
     * @return The original name of the field.
     */
    public final String getOriginalName() {
        return originalName;
    }

    /**
     * Get the original data type of the field.
     * @return The original data type of the field.
     */
    public final FieldType.Type getOriginalDataType() {
        return originalDataType;
    }

    /**
     * Indicates if the field is required or not.
     * @return true if the field is required, false otherwise.
     */
    public final boolean isRequired() {
        return isRequired;
    }

    /**
     * Get the length of the field, if is of STRING data type.
     * @return The string length.
     */
    public final Integer getStringLength() {
        return stringLength;
    }

    /**
     * Set the length of the field, if is of STRING data type.
     * @param length The length of the string.
     */
    public final void setStringLength(final Integer length) {
        stringLength = length;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String newSqlName) {
        sqlName = newSqlName;
    }

    /**
     * Get the code name of the field.
     * @return The code name.
     */
    public final String getCodeName() {
        return codeName;
    }

    /**
     * Set the code name of the field.
     * @param newName The new code name.
     */
    public final void setCodeName(final String newName) {
        codeName = newName;
    }

    /**
     * Get the code getter name of the field.
     * @return The getter name.
     */
    public final String getGetterName() {
        return getterName;
    }

    /**
     * Set the code getter name of the field.
     * @param newName The new getter name.
     */
    public final void setGetterName(final String newName) {
        getterName = newName;
    }

    /**
     * Get the code setter name of the field.
     * @return The setter name.
     */
    public final String getSetterName() {
        return setterName;
    }

    /**
     * Set the code setter name of the field.
     * @param newName The new setter name.
     */
    public final void setSetterName(final String newName) {
        setterName = newName;
    }
}
