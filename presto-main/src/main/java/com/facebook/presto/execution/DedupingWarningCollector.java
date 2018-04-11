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

import com.facebook.presto.spi.PrestoWarning;
import com.facebook.presto.spi.QueryId;
import com.google.common.collect.ImmutableList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class DedupingWarningCollector
        implements WarningCollector
{
    private final Set<PrestoWarning> warnings = new LinkedHashSet<>();
    private final QueryId queryId;

    public DedupingWarningCollector(QueryId queryId)
    {
        this.queryId = requireNonNull(queryId, "queryId is null");
    }

    @Override
    public void add(PrestoWarning warning)
    {
        warnings.add(new PrestoWarning(
                warning.getCode(),
                format("%s: %s", queryId, warning.getMessage())));
    }

    @Override
    public List<PrestoWarning> getWarnings()
    {
        return ImmutableList.copyOf(warnings);
    }
}
