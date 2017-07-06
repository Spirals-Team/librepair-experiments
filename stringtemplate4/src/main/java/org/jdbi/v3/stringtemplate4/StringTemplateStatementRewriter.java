/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.stringtemplate4;

import static java.util.Objects.requireNonNull;

import org.jdbi.v3.core.statement.Binding;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.rewriter.ColonPrefixStatementRewriter;
import org.jdbi.v3.core.rewriter.RewrittenStatement;
import org.jdbi.v3.core.rewriter.StatementRewriter;
import org.stringtemplate.v4.ST;

/**
 * Rewrites a StringTemplate template, using the attributes on the {@link StatementContext} as template parameters.
 */
public class StringTemplateStatementRewriter implements StatementRewriter {
    private final StatementRewriter delegate;

    /**
     * Constructs a rewriter which uses colon-prefixed parameter names.
     */
    public StringTemplateStatementRewriter() {
        this(new ColonPrefixStatementRewriter());
    }

    /**
     * Constructs a rewriter which uses the specified delegate rewriter, after parsing out any StringTemplate
     * expressions.
     *
     * @param delegate the delegate rewriter.
     */
    public StringTemplateStatementRewriter(StatementRewriter delegate) {
        this.delegate = requireNonNull(delegate);
    }

    @Override
    public RewrittenStatement rewrite(String sql, Binding params, StatementContext ctx) {
        ST template = new ST(sql);

        ctx.getAttributes().forEach(template::add);

        return delegate.rewrite(template.render(), params, ctx);
    }
}
