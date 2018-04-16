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
package com.facebook.presto.kudu;

import com.facebook.presto.spi.TableIdentity;

import java.nio.charset.StandardCharsets;

public class KuduTableIdentity
        implements TableIdentity
{
    private final String tableId;

    public KuduTableIdentity(String tableId)
    {
        this.tableId = tableId;
    }

    public String getTableId()
    {
        return tableId;
    }

    @Override
    public byte[] serialize()
    {
        return tableId.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof KuduTableIdentity) {
            return tableId.equals(((KuduTableIdentity) obj).tableId);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return tableId.hashCode();
    }

    public static KuduTableIdentity deserialize(byte[] bytes)
    {
        return new KuduTableIdentity(new String(bytes, StandardCharsets.UTF_8));
    }
}
