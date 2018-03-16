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

import java.io.IOException;

public class Adapter implements Appendable {
    private final Element adaptable;
    private char content;
    private final CharSequence oneCharSequence = new CharSequence() {
        @Override
        public CharSequence subSequence(int start, int end) {
            if (start == 0 && end == 0) {
                return this;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        @Override
        public int length() {
            return 1;
        }

        @Override
        public char charAt(int index) {
            return content;
        }
    };

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        adaptable.characters(csq, start, end);
        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        content = c;
        return append(oneCharSequence, 0, 1);
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        return append(csq, 0, csq.length());
    }

    public Adapter(Element adaptable) {
        this.adaptable = adaptable;
    }
}
