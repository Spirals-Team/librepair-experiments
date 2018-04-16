/*
 * (C) Copyright 2003-2017, by Michael Behrisch and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;

import junit.framework.*;

/**
 * Unit test for {@link Subgraph} class.
 *
 * @author Michael Behrisch
 * @since Sep 21, 2004
 * @deprecated Since {@link Subgraph} is deprecated.
 */
@Deprecated
public class SubgraphTest
    extends TestCase
{
    // ~ Instance fields --------------------------------------------------------

    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";

    // ~ Constructors -----------------------------------------------------------

    /**
     * @see junit.framework.TestCase#TestCase(java.lang.String)
     */
    public SubgraphTest(String name)
    {
        super(name);
    }

    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testInducedSubgraphListener()
    {
        UndirectedGraph<String, DefaultEdge> g = init(true);
        UndirectedSubgraph<String, DefaultEdge> sub = new UndirectedSubgraph<>(g, null, null);

        assertEquals(g.vertexSet(), sub.vertexSet());
        assertEquals(g.edgeSet(), sub.edgeSet());

        g.addEdge(v3, v4);

        assertEquals(g.vertexSet(), sub.vertexSet());
        assertEquals(g.edgeSet(), sub.edgeSet());
    }

    /**
     * Tests Subgraph.
     */
    public void testSubgraph()
    {
        UndirectedGraph<String, DefaultEdge> g = init(false);
        UndirectedSubgraph<String, DefaultEdge> sub = new UndirectedSubgraph<>(g, null, null);

        assertEquals(g.vertexSet(), sub.vertexSet());
        assertEquals(g.edgeSet(), sub.edgeSet());

        Set<String> vset = new HashSet<>(g.vertexSet());
        g.removeVertex(v1);
        assertEquals(vset, sub.vertexSet()); // losing track

        g = init(false);
        vset = new HashSet<>();
        vset.add(v1);
        sub = new UndirectedSubgraph<>(g, vset, null);
        assertEquals(vset, sub.vertexSet());
        assertEquals(0, sub.degreeOf(v1));
        assertEquals(Collections.EMPTY_SET, sub.edgeSet());

        vset.add(v2);
        vset.add(v3);
        sub = new UndirectedSubgraph<>(g, vset, new HashSet<>(g.getAllEdges(v1, v2)));
        assertEquals(vset, sub.vertexSet());
        assertEquals(1, sub.edgeSet().size());
    }

    /**
     * .
     */
    public void testSubgraphListener()
    {
        UndirectedGraph<String, DefaultEdge> g = init(true);
        UndirectedSubgraph<String, DefaultEdge> sub = new UndirectedSubgraph<>(g, null, null);

        assertEquals(g.vertexSet(), sub.vertexSet());
        assertEquals(g.edgeSet(), sub.edgeSet());

        Set<String> vset = new HashSet<>(g.vertexSet());
        g.removeVertex(v1);
        vset.remove(v1);
        assertEquals(vset, sub.vertexSet()); // not losing track
        assertEquals(g.edgeSet(), sub.edgeSet());
    }

    private UndirectedGraph<String, DefaultEdge> init(boolean listenable)
    {
        UndirectedGraph<String, DefaultEdge> g;

        if (listenable) {
            g = new ListenableUndirectedGraph<>(DefaultEdge.class);
        } else {
            g = new SimpleGraph<>(DefaultEdge.class);
        }

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v1);
        g.addEdge(v1, v4);

        return g;
    }

    public void testInducedSubgraphUnderlyingEdgeAddition()
    {
        ListenableGraph<Object, DefaultEdge> baseGraph =
            new ListenableUndirectedGraph<>(DefaultEdge.class);

        baseGraph.addVertex(v1);
        baseGraph.addVertex(v2);

        Set<Object> initialVertexes = new LinkedHashSet<>();
        initialVertexes.add(v1);
        Subgraph<Object, DefaultEdge, ListenableGraph<Object, DefaultEdge>> subgraph =
            new Subgraph<>(baseGraph, initialVertexes, null);
        baseGraph.addEdge(v1, v2);

        assertFalse(subgraph.containsEdge(v1, v2));
    }

    public void testEdges()
    {
        UndirectedGraph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        Graphs.addAllVertices(g, Arrays.asList(1, 2, 3, 4, 5));
        g.addEdge(1, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        DefaultEdge e14 = g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(3, 3);
        g.addEdge(4, 5);
        g.addEdge(5, 5);
        g.addEdge(5, 2);

        UndirectedSubgraph<Integer, DefaultEdge> sg = new UndirectedSubgraph<>(g);
        assertEquals(10, sg.edgeSet().size());
        sg.removeVertex(2);
        assertEquals(5, sg.edgeSet().size());
        assertEquals(2, sg.edgesOf(1).size());
        assertFalse(sg.containsVertex(2));
        assertEquals(2, sg.edgesOf(3).size());
        assertEquals(2, sg.edgesOf(4).size());
        assertEquals(2, sg.edgesOf(5).size());

        sg.removeEdge(e14);
        assertEquals(4, sg.edgeSet().size());
        assertEquals(1, sg.edgesOf(1).size());
        assertEquals(2, sg.edgesOf(3).size());
        assertEquals(1, sg.edgesOf(4).size());
        assertEquals(2, sg.edgesOf(5).size());

        assertEquals(10, g.edgeSet().size());
    }

    public void testNonValidVerticesFilter()
    {
        UndirectedGraph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        Graphs.addAllVertices(g, Arrays.asList(1, 2, 3, 4, 5));
        g.addEdge(1, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(3, 3);
        g.addEdge(4, 5);
        g.addEdge(5, 5);
        g.addEdge(5, 2);

        UndirectedSubgraph<Integer, DefaultEdge> sg = new UndirectedSubgraph<>(
            g, new HashSet<>(Arrays.asList(1, 3, 100, 200, 300, 500, 800, 1000)));
        assertEquals(2, sg.edgeSet().size());
        assertEquals(2, sg.vertexSet().size());
    }

    public void testNonValidEdgesFilter()
    {
        UndirectedGraph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        Graphs.addAllVertices(g, Arrays.asList(1, 2, 3, 4, 5));
        DefaultEdge e1 = g.addEdge(1, 2);
        g.addEdge(1, 2);
        DefaultEdge e2 = g.addEdge(1, 3);
        DefaultEdge e3 = g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(3, 3);
        DefaultEdge e4 = g.addEdge(4, 5);
        DefaultEdge e5 = g.addEdge(5, 5);
        g.addEdge(5, 2);

        DefaultEdge nonValid1 = g.addEdge(5, 1);
        g.removeEdge(nonValid1);
        DefaultEdge nonValid2 = g.addEdge(5, 1);
        g.removeEdge(nonValid2);

        UndirectedSubgraph<Integer, DefaultEdge> sg = new UndirectedSubgraph<>(
            g, null, new HashSet<>(Arrays.asList(e1, e2, e3, e4, e5, nonValid1, nonValid2)));
        assertEquals(5, sg.edgeSet().size());
        assertEquals(5, sg.vertexSet().size());
    }

    public void testInOutEdgesUndirected()
    {
        UndirectedGraph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        Graphs.addAllVertices(g, Arrays.asList(1, 2, 3, 4, 5));
        DefaultEdge e12 = g.addEdge(1, 2);
        DefaultEdge e13 = g.addEdge(1, 3);
        DefaultEdge e23 = g.addEdge(2, 3);
        DefaultEdge e24_1 = g.addEdge(2, 4);
        DefaultEdge e24_2 = g.addEdge(2, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        DefaultEdge e44 = g.addEdge(4, 4);
        g.addEdge(4, 5);

        UndirectedSubgraph<Integer,
            DefaultEdge> sg = new UndirectedSubgraph<>(
                g, new HashSet<>(Arrays.asList(1, 2, 3, 4)),
                new HashSet<>(Arrays.asList(e12, e13, e23, e24_1, e24_2, e44)));

        assertEquals(6, sg.edgeSet().size());

        assertEquals(new HashSet<>(Arrays.asList(e12, e13)), sg.edgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e12, e24_1, e24_2, e23)), sg.edgesOf(2));
        assertEquals(new HashSet<>(Arrays.asList(e13, e23)), sg.edgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e44)), sg.edgesOf(4));

        assertEquals(2, sg.degreeOf(1));
        assertEquals(4, sg.degreeOf(2));
        assertEquals(2, sg.degreeOf(3));
        assertEquals(4, sg.degreeOf(4));

        assertEquals(new HashSet<>(Arrays.asList(e12, e13)), sg.incomingEdgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e12, e24_1, e24_2, e23)), sg.incomingEdgesOf(2));
        assertEquals(new HashSet<>(Arrays.asList(e13, e23)), sg.incomingEdgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e44)), sg.incomingEdgesOf(4));

        assertEquals(2, sg.inDegreeOf(1));
        assertEquals(4, sg.inDegreeOf(2));
        assertEquals(2, sg.inDegreeOf(3));
        assertEquals(4, sg.inDegreeOf(4));

        assertEquals(new HashSet<>(Arrays.asList(e12, e13)), sg.outgoingEdgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e12, e24_1, e24_2, e23)), sg.outgoingEdgesOf(2));
        assertEquals(new HashSet<>(Arrays.asList(e13, e23)), sg.outgoingEdgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e44)), sg.outgoingEdgesOf(4));

        assertEquals(2, sg.outDegreeOf(1));
        assertEquals(4, sg.outDegreeOf(2));
        assertEquals(2, sg.outDegreeOf(3));
        assertEquals(4, sg.outDegreeOf(4));
    }

    public void testInOutEdgesDirected()
    {
        DirectedGraph<Integer, DefaultEdge> g = new DirectedPseudograph<>(DefaultEdge.class);
        Graphs.addAllVertices(g, Arrays.asList(1, 2, 3, 4, 5));
        DefaultEdge e12 = g.addEdge(1, 2);
        DefaultEdge e13 = g.addEdge(1, 3);
        DefaultEdge e23 = g.addEdge(2, 3);
        DefaultEdge e24_1 = g.addEdge(2, 4);
        DefaultEdge e24_2 = g.addEdge(2, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        DefaultEdge e44 = g.addEdge(4, 4);
        g.addEdge(4, 5);

        DirectedSubgraph<Integer,
            DefaultEdge> sg = new DirectedSubgraph<>(
                g, new HashSet<>(Arrays.asList(1, 2, 3, 4)),
                new HashSet<>(Arrays.asList(e12, e13, e23, e24_1, e24_2, e44)));

        assertEquals(6, sg.edgeSet().size());

        assertEquals(new HashSet<>(Arrays.asList(e12, e13)), sg.edgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e12, e24_1, e24_2, e23)), sg.edgesOf(2));
        assertEquals(new HashSet<>(Arrays.asList(e13, e23)), sg.edgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e44)), sg.edgesOf(4));

        assertEquals(2, sg.degreeOf(1));
        assertEquals(4, sg.degreeOf(2));
        assertEquals(2, sg.degreeOf(3));
        assertEquals(4, sg.degreeOf(4));

        assertEquals(new HashSet<>(), sg.incomingEdgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e12)), sg.incomingEdgesOf(2));
        assertEquals(new HashSet<>(Arrays.asList(e13, e23)), sg.incomingEdgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e44)), sg.incomingEdgesOf(4));

        assertEquals(0, sg.inDegreeOf(1));
        assertEquals(1, sg.inDegreeOf(2));
        assertEquals(2, sg.inDegreeOf(3));
        assertEquals(3, sg.inDegreeOf(4));

        assertEquals(new HashSet<>(Arrays.asList(e12, e13)), sg.outgoingEdgesOf(1));
        assertEquals(new HashSet<>(Arrays.asList(e24_1, e24_2, e23)), sg.outgoingEdgesOf(2));
        assertEquals(new HashSet<>(), sg.outgoingEdgesOf(3));
        assertEquals(new HashSet<>(Arrays.asList(e44)), sg.outgoingEdgesOf(4));

        assertEquals(2, sg.outDegreeOf(1));
        assertEquals(3, sg.outDegreeOf(2));
        assertEquals(0, sg.outDegreeOf(3));
        assertEquals(1, sg.outDegreeOf(4));
    }

}

// End SubgraphTest.java
