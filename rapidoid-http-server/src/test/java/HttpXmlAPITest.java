/*
 * #%L
 * rapidoid-http-server
 * %%
 * Copyright (C) 2014 - 2017 Nikolche Mihajlovski and contributors
 * %%
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
 * #L%
 */

import org.junit.Test;
import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.http.Req;
import org.rapidoid.http.ReqHandler;
import org.rapidoid.http.Self;
import org.rapidoid.setup.On;
import org.rapidoid.test.TestCommons;
import org.rapidoid.u.U;

@Authors({"Dan Cytermann", "Nikolche Mihajlovski"})
@Since("5.5.0")
public class HttpXmlAPITest extends TestCommons {

	@Test
	public void testXmlAPI() {
		On.get("/inc/{x}").xml(new ReqHandler() {
			@Override
			public Object execute(Req req) {
				return U.num(req.param("x")) + 1;
			}
		});

		Self.get("/inc/99").expect("<Integer>100</Integer>");
	}

}
