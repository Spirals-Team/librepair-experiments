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
 *
 */

package org.esigate.parser;

import org.apache.http.HttpResponse;
import org.esigate.impl.DriverRequest;

import java.io.IOException;

/**
 * The current context used during parsing.
 * 
 * @author Francois-Xavier Bonnet
 */
public interface ParserContext {

    /** @return {@linkplain org.apache.http.HttpRequest} associated with current processing. */
    DriverRequest getHttpRequest();

    /** @return {@linkplain HttpResponse} associated with current processing. */
    HttpResponse getHttpResponse();

    /**
     * @param e
     *            exception
     * @return <code>true</code> if error has been handled by this element and it should not be propagated further.
     */
    boolean reportError(Exception e);

    Element getCurrent();

    <T> T findAncestor(Class<T> type);

    /**
     * Writes characters into current writer.
     * 
     * @param cs
     *            sequence
     * @throws IOException
     *             exception
     */
    void characters(CharSequence cs) throws IOException;

}
