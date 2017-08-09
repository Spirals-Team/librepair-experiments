/*
 * Copyright 1999-2101 Alibaba Group.
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
package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
class EnumSerializer implements ObjectSerializer {
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.out;

        if ((out.features & SerializerFeature.WriteEnumUsingToString.mask) != 0) {
            Enum<?> e = (Enum<?>) object;
            
            String name = e.toString();
            boolean userSingleQuote = (out.features & SerializerFeature.UseSingleQuotes.mask) != 0;
            
            if (userSingleQuote) {
                out.writeStringWithSingleQuote(name);
            } else {
                out.writeStringWithDoubleQuote(name, (char) 0, false);    
            }
        } else {
            Enum<?> e = (Enum<?>) object;
            out.writeInt(e.ordinal());
        }
    }
}
