/*
 * Copyright 2013 the original author or authors.
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
package org.springframework.data.redis;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of {@link ObjectFactory} that returns unique incremented Longs as Strings
 * 
 * @author Jennifer Hickey
 */
public class LongAsStringObjectFactory implements ObjectFactory<String> {

	private AtomicLong counter = new AtomicLong();

	public String instance() {
		return String.valueOf(counter.getAndIncrement());
	}

}
