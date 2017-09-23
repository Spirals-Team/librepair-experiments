/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.presto.execution;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkState;

@JsonSerialize(using = DriverGroupId.Serializer.class)
@JsonDeserialize(using = DriverGroupId.Deserializer.class)
public class DriverGroupId
{
    private final boolean grouped;
    private final int groupId;

    public static DriverGroupId notGrouped()
    {
        return new DriverGroupId(false, 0);
    }

    public static DriverGroupId of(int id)
    {
        return new DriverGroupId(true, id);
    }

    public DriverGroupId(boolean grouped, int groupId)
    {
        this.grouped = grouped;
        this.groupId = groupId;
    }

    public boolean isGrouped()
    {
        return grouped;
    }

    public int getId()
    {
        checkState(grouped);
        return groupId;
    }

    @Override
    public String toString()
    {
        if (grouped) {
            return "Group" + groupId;
        }
        else {
            return "NotGrouped";
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriverGroupId that = (DriverGroupId) o;
        return grouped == that.grouped &&
                groupId == that.groupId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(grouped, groupId);
    }

    // Adapted from OptionalIntSerializer
    public static class Serializer
            extends StdSerializer<DriverGroupId>
    {
        public Serializer()
        {
            super(DriverGroupId.class);
        }

        @Override
        public boolean isEmpty(SerializerProvider provider, DriverGroupId value)
        {
            return (value == null) || !value.isGrouped();
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
                throws JsonMappingException
        {
            JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
            if (v2 != null) {
                v2.numberType(JsonParser.NumberType.INT);
            }
        }

        @Override
        public void serialize(DriverGroupId value, JsonGenerator gen, SerializerProvider provider)
                throws IOException
        {
            if (value.isGrouped()) {
                gen.writeNumber(value.getId());
            }
            else {
                gen.writeNull();
            }
        }
    }

    // Adapted from OptionalIntDeserializer
    public static class Deserializer
            extends StdDeserializer<DriverGroupId>
    {
        public Deserializer()
        {
            super(DriverGroupId.class);
        }

        @Override
        public DriverGroupId getNullValue(DeserializationContext ctxt)
        {
            return DriverGroupId.notGrouped();
        }

        @Override
        public DriverGroupId deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
        {
            return DriverGroupId.of(jp.getValueAsInt());
        }
    }
}
