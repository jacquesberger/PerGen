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

import org.jberger.pergen.transformers.JavaTransformer;
import org.jberger.pergen.transformers.SqlTransformer;

public class Field {

    private String originalName;
    private String sqlName;
    private String javaName;
    private String setterName;
    private String getterName;
    private FieldType.Type originalDataType;
    private boolean isRequired;
    private Integer stringLength = null;

    public Field(final String fieldName,
                             final FieldType.Type dataType,
                             final boolean fieldRequired) {
        originalName = fieldName;
        originalDataType = dataType;
        isRequired = fieldRequired;
        generateCodeNames();
    }

    public final String getOriginalName() {
        return originalName;
    }

    public final FieldType.Type getOriginalDataType() {
        return originalDataType;
    }

    public final boolean isRequired() {
        return isRequired;
    }

    public final Integer getStringLength() {
        return stringLength;
    }

    public final void setStringLength(final Integer length) {
        stringLength = length;
    }

    public String getSqlName() {
        return sqlName;
    }

    public final String getJavaName() {
        return javaName;
    }

    public final String getGetterName() {
        return getterName;
    }

    public final String getSetterName() {
        return setterName;
    }

    private void generateCodeNames() {
        String methodSuffix = JavaTransformer.snakeCaseIdentifierToPascalCase(originalName);
        sqlName = SqlTransformer.transformIdentifier(originalName);
        javaName = JavaTransformer.snakeCaseIdentifierToCamelCase(originalName);
        getterName = "get" + methodSuffix;
        setterName = "set" + methodSuffix;
    }
}