/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.parsing.parser.sql.ddl.truncate;

import com.dangdang.ddframe.rdb.sharding.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.parsing.lexer.token.DefaultKeyword;
import com.dangdang.ddframe.rdb.sharding.parsing.lexer.token.Keyword;
import com.dangdang.ddframe.rdb.sharding.parsing.lexer.LexerEngine;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.clause.TableReferencesClauseParser;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.sql.SQLParser;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.sql.ddl.DDLStatement;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Truncate parser.
 *
 * @author zhangliang
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTruncateParser implements SQLParser {
    
    private final ShardingRule shardingRule;
    
    private final LexerEngine lexerEngine;
    
    private final TableReferencesClauseParser tableReferencesClauseParser;
    
    public AbstractTruncateParser(final ShardingRule shardingRule, final LexerEngine lexerEngine) {
        this.shardingRule = shardingRule;
        this.lexerEngine = lexerEngine;
        tableReferencesClauseParser = new TableReferencesClauseParser(shardingRule, lexerEngine);
    }
    
    @Override
    public DDLStatement parse() {
        lexerEngine.nextToken();
        lexerEngine.skipIfEqual(DefaultKeyword.TABLE);
        lexerEngine.skipAll(getSkippedKeywordsBetweenTruncateTableAndTableName());
        DDLStatement result = new DDLStatement();
        tableReferencesClauseParser.parse(result, true);
        return result;
    }
    
    protected Keyword[] getSkippedKeywordsBetweenTruncateTableAndTableName() {
        return new Keyword[0];
    }
}
