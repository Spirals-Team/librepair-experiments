/*
 * (C) Copyright 2017-2017, by Dimitrios Michail and Contributors.
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

import java.io.*;

import org.jgrapht.*;

/**
 * Default implementation of the graph type.
 * 
 * <p>
 * The graph type describes various properties of a graph such as whether it is directed, undirected
 * or mixed, whether it contain self-loops (edges with the same source and target vertices), whether
 * it contain parallel-edges (multiple edges with the same source and target) and whether it is
 * weighted or not.
 * 
 * @author Dimitrios Michail
 */
public class DefaultGraphType
    implements GraphType, Serializable
{
    private static final long serialVersionUID = 4291049312119347474L;

    private final boolean directed;
    private final boolean undirected;
    private final boolean selfLoops;
    private final boolean multipleEdges;
    private final boolean weighted;
    private final boolean allowsCycles;
    private final boolean modifiable;

    private DefaultGraphType(
        boolean directed, boolean undirected, boolean selfLoops, boolean multipleEdges,
        boolean weighted, boolean allowsCycles, boolean modifiable)
    {
        this.directed = directed;
        this.undirected = undirected;
        this.selfLoops = selfLoops;
        this.multipleEdges = multipleEdges;
        this.weighted = weighted;
        this.allowsCycles = allowsCycles;
        this.modifiable = modifiable;
    }

    @Override
    public boolean isDirected()
    {
        return directed && !undirected;
    }

    @Override
    public boolean isUndirected()
    {
        return undirected && !directed;
    }

    @Override
    public boolean isMixed()
    {
        return undirected && directed;
    }

    @Override
    public boolean isAllowingMultipleEdges()
    {
        return multipleEdges;
    }

    @Override
    public boolean isAllowingSelfLoops()
    {
        return selfLoops;
    }

    @Override
    public boolean isWeighted()
    {
        return weighted;
    }

    @Override
    public boolean isAllowingCycles()
    {
        return allowsCycles;
    }

    @Override
    public boolean isModifiable()
    {
        return modifiable;
    }

    @Override
    public boolean isSimple()
    {
        return !isAllowingMultipleEdges() && !isAllowingSelfLoops();
    }

    @Override
    public boolean isPseudograph()
    {
        return isAllowingMultipleEdges() && isAllowingSelfLoops();
    }

    @Override
    public boolean isMultigraph()
    {
        return isAllowingMultipleEdges() && !isAllowingSelfLoops();
    }

    @Override
    public GraphType asDirected()
    {
        return new Builder(this).directed().build();
    }

    @Override
    public GraphType asUndirected()
    {
        return new Builder(this).undirected().build();
    }

    @Override
    public GraphType asMixed()
    {
        return new Builder(this).mixed().build();
    }

    @Override
    public GraphType asUnweighted()
    {
        return new Builder(this).weighted(false).build();
    }

    @Override
    public GraphType asWeighted()
    {
        return new Builder(this).weighted(true).build();
    }

    @Override
    public GraphType asModifiable()
    {
        return new Builder(this).modifiable(true).build();
    }

    @Override
    public GraphType asUnmodifiable()
    {
        return new Builder(this).modifiable(false).build();
    }

    /**
     * A simple graph type. An undirected graph for which at most one edge connects any two
     * vertices, and self-loops are not permitted.
     * 
     * @return a simple graph type
     */
    public static DefaultGraphType simple()
    {
        return new Builder()
            .undirected().allowSelfLoops(false).allowMultipleEdges(false).weighted(false).build();
    }

    /**
     * A multigraph type. A non-simple undirected graph in which no self-loops are permitted, but
     * multiple edges between any two vertices are.
     * 
     * @return a multigraph type
     */
    public static DefaultGraphType multigraph()
    {
        return new Builder()
            .undirected().allowSelfLoops(false).allowMultipleEdges(true).weighted(false).build();
    }

    /**
     * A pseudograph type. A non-simple undirected graph in which both graph self-loops and multiple
     * edges are permitted.
     * 
     * @return a pseudograph type
     */
    public static DefaultGraphType pseudograph()
    {
        return new Builder()
            .undirected().allowSelfLoops(true).allowMultipleEdges(true).weighted(false).build();
    }

    /**
     * A directed simple graph type. An undirected graph for which at most one edge connects any two
     * vertices, and self-loops are not permitted.
     * 
     * @return a directed simple graph type
     */
    public static DefaultGraphType directedSimple()
    {
        return new Builder()
            .directed().allowSelfLoops(false).allowMultipleEdges(false).weighted(false).build();
    }

    /**
     * A directed multigraph type. A non-simple undirected graph in which no self-loops are
     * permitted, but multiple edges between any two vertices are.
     * 
     * @return a directed multigraph type
     */
    public static DefaultGraphType directedMultigraph()
    {
        return new Builder()
            .directed().allowSelfLoops(false).allowMultipleEdges(true).weighted(false).build();
    }

    /**
     * A directed pseudograph type. A non-simple undirected graph in which both graph self-loops and
     * multiple edges are permitted.
     * 
     * @return a directed pseudograph type
     */
    public static DefaultGraphType directedPseudograph()
    {
        return new Builder()
            .directed().allowSelfLoops(true).allowMultipleEdges(true).weighted(false).build();
    }

    /**
     * A mixed graph type. A graph having a set of undirected and a set of directed edges, which may
     * contain self-loops and multiple edges are permitted.
     * 
     * @return a mixed graph type
     */
    public static DefaultGraphType mixed()
    {
        return new Builder()
            .mixed().allowSelfLoops(true).allowMultipleEdges(true).weighted(false).build();
    }

    /**
     * A directed acyclic graph.
     * 
     * @return a directed acyclic graph type
     */
    public static DefaultGraphType dag()
    {
        return new Builder()
            .directed().allowSelfLoops(false).allowMultipleEdges(true).allowCycles(false)
            .weighted(false).build();
    }

    /**
     * A builder for {@link DefaultGraphType}.
     * 
     * @author Dimitrios Michail
     */
    public static class Builder
    {
        private boolean directed;
        private boolean undirected;
        private boolean allowSelfLoops;
        private boolean allowMultipleEdges;
        private boolean weighted;
        private boolean allowCycles;
        private boolean modifiable;

        /**
         * Construct a new Builder.
         */
        public Builder()
        {
            this.directed = false;
            this.undirected = true;
            this.allowSelfLoops = true;
            this.allowMultipleEdges = true;
            this.weighted = false;
            this.allowCycles = true;
            this.modifiable = true;
        }

        /**
         * Construct a new Builder.
         * 
         * @param type the type to base the builder
         */
        public Builder(GraphType type)
        {
            this.directed = type.isDirected() || type.isMixed();
            this.undirected = type.isUndirected() || type.isMixed();
            this.allowSelfLoops = type.isAllowingSelfLoops();
            this.allowMultipleEdges = type.isAllowingMultipleEdges();
            this.weighted = type.isWeighted();
            this.allowCycles = type.isAllowingCycles();
            this.modifiable = type.isModifiable();
        }

        /**
         * Set the type as directed.
         * 
         * @return the builder
         */
        public Builder directed()
        {
            this.directed = true;
            this.undirected = false;
            return this;
        }

        /**
         * Set the type as undirected.
         * 
         * @return the builder
         */
        public Builder undirected()
        {
            this.directed = false;
            this.undirected = true;
            return this;
        }

        /**
         * Set the type as mixed.
         * 
         * @return the builder
         */
        public Builder mixed()
        {
            this.directed = true;
            this.undirected = true;
            return this;
        }

        /**
         * Set whether to allow self-loops.
         * 
         * @param value if true self-values are allowed, otherwise not
         * @return the builder
         */
        public Builder allowSelfLoops(boolean value)
        {
            this.allowSelfLoops = value;
            return this;
        }

        /**
         * Set whether to allow multiple edges.
         * 
         * @param value if true multiple edges are allowed, otherwise not
         * @return the builder
         */
        public Builder allowMultipleEdges(boolean value)
        {
            this.allowMultipleEdges = value;
            return this;
        }

        /**
         * Set whether the graph will be weighted.
         * 
         * @param value if true the graph will be weighted, otherwise unweighted
         * @return the builder
         */
        public Builder weighted(boolean value)
        {
            this.weighted = value;
            return this;
        }

        /**
         * Set whether the graph will allow cycles.
         * 
         * @param value if true the graph will allow cycles, otherwise not
         * @return the builder
         */
        public Builder allowCycles(boolean value)
        {
            this.allowCycles = value;
            return this;
        }

        /**
         * Set whether the graph is modifiable.
         * 
         * @param value if true the graph will be modifiable, otherwise not
         * @return the builder
         */
        public Builder modifiable(boolean value)
        {
            this.modifiable = value;
            return this;
        }

        /**
         * Build the type.
         * 
         * @return the type
         */
        public DefaultGraphType build()
        {
            return new DefaultGraphType(
                directed, undirected, allowSelfLoops, allowMultipleEdges, weighted, allowCycles,
                modifiable);
        }

    }

}
