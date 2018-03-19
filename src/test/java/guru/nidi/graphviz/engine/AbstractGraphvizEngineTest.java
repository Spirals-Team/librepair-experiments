/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
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
package guru.nidi.graphviz.engine;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class AbstractGraphvizEngineTest {

    static class GraphvizEngineDummy extends AbstractJsGraphvizEngine {
        GraphvizEngineDummy() {
            super(true);
        }

        @Override
        protected void doInit() throws Exception {
            // nothing
        }

        @Override
        protected String jsExecute(String call) {
            return call;
        }
    }

    @Test
    void vizExecTotalMemoryIsSet() {
        final GraphvizEngineDummy engineUnderTest = new GraphvizEngineDummy();
        final Options options = Options.create().format(Format.SVG).totalMemory(320000);

        final String vizResult = engineUnderTest.jsVizExec("digraph{ a -> b}", options);

        assertThat(vizResult, is("Viz('digraph{ a -> b}',{format:'svg',engine:'dot',totalMemory:'320000'});"));
    }

    @Test
    void vizExecTotalMemoryIsNotSet() {
        final GraphvizEngineDummy engineUnderTest = new GraphvizEngineDummy();
        final Options options = Options.create().format(Format.SVG);

        final String vizResult = engineUnderTest.jsVizExec("digraph{ a -> b}", options);

        assertThat(vizResult, is("Viz('digraph{ a -> b}',{format:'svg',engine:'dot'});"));
    }
}