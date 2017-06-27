/**
 * Copyright 2005-2016 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.idGenerator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This implementation uses a sequence number to generate unique ids. <b>Only for testing</b>
 * 
 * @author Gotzon Illarramendi
 * @since HDIV 2.1.0
 */
public class SequentialUidGenerator implements UidGenerator {

	private final AtomicLong seq = new AtomicLong(0);

	public Serializable generateUid() {
		return seq.getAndIncrement();
	}

	public Serializable parseUid(final String encodedUid) {

		return encodedUid;
	}

}
