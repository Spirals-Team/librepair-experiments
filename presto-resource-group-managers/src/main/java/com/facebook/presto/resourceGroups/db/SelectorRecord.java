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
package com.facebook.presto.resourceGroups.db;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Objects.requireNonNull;

public class SelectorRecord
{
    private final long resourceGroupId;
    private final Optional<Pattern> userRegex;
    private final Optional<Pattern> sourceRegex;
    private final Set<String> clientTags;

    public SelectorRecord(long resourceGroupId, Optional<Pattern> userRegex, Optional<Pattern> sourceRegex, Set<String> clientTags)
    {
        this.resourceGroupId = resourceGroupId;
        this.userRegex = requireNonNull(userRegex, "userRegex is null");
        this.sourceRegex = requireNonNull(sourceRegex, "sourceRegex is null");
        this.clientTags = requireNonNull(clientTags, "clientTags is null");
    }

    public long getResourceGroupId()
    {
        return resourceGroupId;
    }

    public Optional<Pattern> getUserRegex()
    {
        return userRegex;
    }

    public Optional<Pattern> getSourceRegex()
    {
        return sourceRegex;
    }

    public Set<String> getClientTags()
    {
        return clientTags;
    }

    public static class Mapper
            implements ResultSetMapper<SelectorRecord>
    {
        private static Splitter clientTagSplitter = Splitter.on(',').trimResults().omitEmptyStrings();

        @Override
        public SelectorRecord map(int index, ResultSet resultSet, StatementContext context)
                throws SQLException
        {
            return new SelectorRecord(
                    resultSet.getLong("resource_group_id"),
                    Optional.ofNullable(resultSet.getString("user_regex")).map(Pattern::compile),
                    Optional.ofNullable(resultSet.getString("source_regex")).map(Pattern::compile),
                    ImmutableSet.copyOf(clientTagSplitter.split(nullToEmpty(resultSet.getString("client_tags")))));
        }
    }
}
