/*
 * Copyright 2016 the original author or authors.
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
package org.springframework.data.redis.core.index;

import org.springframework.data.redis.core.convert.SpelIndexResolver;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.util.ObjectUtils;

/**
 * {@link SpelIndexDefinition} defines index that is evaluated based on a {@link SpelExpression} requires the
 * {@link SpelIndexResolver} to be evaluated.
 * 
 * @author Christoph Strobl
 * @since 1.7
 */
public class SpelIndexDefinition extends RedisIndexDefinition {

	private final String expression;

	/**
	 * Creates new {@link SpelIndexDefinition}.
	 * 
	 * @param keyspace must not be {@literal null}.
	 * @param expression must not be {@literal null}.
	 * @param indexName must not be {@literal null}.
	 */
	public SpelIndexDefinition(String keyspace, String expression, String indexName) {
		super(keyspace, null, indexName);
		this.expression = expression;
	}

	/**
	 * Get the raw expression.
	 * 
	 * @return
	 */
	public String getExpression() {
		return expression;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.index.RedisIndexDefinition#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result += ObjectUtils.nullSafeHashCode(expression);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.index.RedisIndexDefinition#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof SpelIndexDefinition)) {
			return false;
		}
		SpelIndexDefinition that = (SpelIndexDefinition) obj;
		return ObjectUtils.nullSafeEquals(this.expression, that.expression);
	}

}
