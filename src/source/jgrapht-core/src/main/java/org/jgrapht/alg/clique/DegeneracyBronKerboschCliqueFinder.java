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
package org.jgrapht.alg.clique;

import java.util.*;
import java.util.concurrent.*;

import org.jgrapht.*;
import org.jgrapht.traverse.*;

/**
 * Bron-Kerbosch maximal clique enumeration algorithm with pivot and degeneracy ordering.
 * 
 * <p>
 * The algorithm is a variant of the Bron-Kerbosch algorithm which apart from the pivoting uses a
 * degeneracy ordering of the vertices. The algorithm is described in
 * <ul>
 * <li>David Eppstein, Maarten Löffler and Darren Strash. Listing All Maximal Cliques in Sparse
 * Graphs in Near-Optimal Time. Algorithms and Computation: 21st International Symposium (ISSAC),
 * 403--414, 2010.</li>
 * </ul>
 * 
 * <p>
 * and has running time O(d n 3^{d/3}) where n is the number of vertices of the graph and d is the
 * degeneracy of the graph. The algorithm looks for a maximal clique parameterized by degeneracy, a
 * frequently-used measure of the sparseness of a graph that is closely related to other common
 * sparsity measures such as arboricity and thickness, and that has previously been used for other
 * fixed-parameter problems.
 * 
 * <p>
 * The algorithm first computes all maximal cliques and then returns the result to the user. A
 * timeout can be set using the constructor parameters.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 * @see BronKerboschCliqueFinder
 * @see PivotBronKerboschCliqueFinder
 *
 * @author Dimitrios Michail
 */
public class DegeneracyBronKerboschCliqueFinder<V, E>
    extends PivotBronKerboschCliqueFinder<V, E>
{
    /**
     * Constructs a new clique finder.
     *
     * @param graph the input graph; must be simple
     */
    public DegeneracyBronKerboschCliqueFinder(Graph<V, E> graph)
    {
        this(graph, 0L, TimeUnit.SECONDS);
    }

    /**
     * Constructs a new clique finder.
     *
     * @param graph the input graph; must be simple
     * @param timeout the maximum time to wait, if zero no timeout
     * @param unit the time unit of the timeout argument
     */
    public DegeneracyBronKerboschCliqueFinder(Graph<V, E> graph, long timeout, TimeUnit unit)
    {
        super(graph, timeout, unit);
    }

    /**
     * Lazily execute the enumeration algorithm.
     */
    @Override
    protected void lazyRun()
    {
        if (allMaximalCliques == null) {
            if (!GraphTests.isSimple(graph)) {
                throw new IllegalArgumentException("Graph must be simple");
            }
            allMaximalCliques = new ArrayList<>();

            long nanosTimeLimit;
            try {
                nanosTimeLimit = Math.addExact(System.nanoTime(), nanos);
            } catch (ArithmeticException ignore) {
                nanosTimeLimit = Long.MAX_VALUE;
            }

            List<V> ordering = new ArrayList<>();
            new DegeneracyOrderingIterator<V, E>(graph).forEachRemaining(ordering::add);

            int n = ordering.size();
            for (int i = 0; i < n; i++) {
                V vi = ordering.get(i);
                Set<V> viNeighbors = new HashSet<>();
                for (E e : graph.edgesOf(vi)) {
                    viNeighbors.add(Graphs.getOppositeVertex(graph, e, vi));
                }

                Set<V> P = new HashSet<>();
                for (int j = i + 1; j < n; j++) {
                    V vj = ordering.get(j);
                    if (viNeighbors.contains(vj)) {
                        P.add(vj);
                    }
                }

                Set<V> R = new HashSet<>();
                R.add(vi);

                Set<V> X = new HashSet<>();
                for (int j = 0; j < i; j++) {
                    V vj = ordering.get(j);
                    if (viNeighbors.contains(vj)) {
                        X.add(vj);
                    }
                }

                /*
                 * Call the pivot version
                 */
                findCliques(P, R, X, nanosTimeLimit);
            }
        }
    }

}
