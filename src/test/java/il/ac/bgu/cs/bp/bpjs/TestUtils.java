/*
 * The MIT License
 *
 * Copyright 2017 michael.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package il.ac.bgu.cs.bp.bpjs;

import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.analysis.Node;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.joining;

/**
 * Just a static place for some repeated methods useful for testing.
 * 
 * @author michael
 */
public abstract class TestUtils {
    
    
    /**
     * Preventing the instantiation of subclasses.
     */
    private TestUtils(){}
    
    
    public static String traceEventNamesString( List<Node> trace, String delimiter ) {
        
        return trace.stream()
                    .map(Node::getLastEvent)
                    .filter(Objects::nonNull)
                    .map(BEvent::getName)
                    .collect(joining(delimiter));
    }
    
    public static String eventNamesString( List<BEvent> trace, String delimiter ) {
        return trace.stream()
                    .map(BEvent::getName)
                    .collect(joining(delimiter));
    }
}
