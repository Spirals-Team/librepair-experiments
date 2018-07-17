/*
 * (C) Copyright 2003-2018, by Christoph Grüne and Contributors.
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
package org.jgrapht.alg.cycle;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.util.TypeUtil;

import java.util.*;

/**
 * Implementation of an algorithm for the local augmentation problem for the cyclic exchange neighborhood,
 * i.e. it finds subset-disjoint negative cycles in a graph, based on
 * Ravindra K. Ahuja, James B. Orlin, Dushyant Sharma,
 * A composite very large-scale neighborhood structure for the capacitated minimum spanning tree problem,
 * Operations Research Letters, Volume 31, Issue 3, 2003, Pages 185-194, ISSN 0167-6377,
 * https://doi.org/10.1016/S0167-6377(02)00236-5. (http://www.sciencedirect.com/science/article/pii/S0167637702002365)
 *
 * A subset-disjoint cycle is a cycle such that no two vertices in the cycle are in the same subset of a given partition of the whole vertex set.
 *
 * This algorithm returns the first found subset-disjoint cycle. Furthermore, this algorithm may enumerate all paths up
 * to the length given by the parameter <code>lengthBound</code>, i.e the algorithm runs in exponential time.
 *
 * @param <V> the vertex type
 * @param <E> the edge type
 *
 * @author Christoph Grüne
 * @since June 7, 2018
 */
public class AhujaOrlinSharmaCyclicExchangeLocalAugmentation<V, E> {

    /**
     * Implementation of a labeled path.
     * It is used in AhujaOrlinSharmaCyclicExchangeLocalAugmentation to efficiently maintain the paths in the calculation.
     *
     * @param <V> the vertex type
     *
     * @author Christoph Grüne
     * @since June 7, 2018
     */
    private static class LabeledPath<V> implements Cloneable {

        private ArrayList<V> vertices;
        private HashSet<Integer> labels;

        private double cost;

        /**
         * Constructs a LabeledPath with the given inputs
         *
         * @param vertices the vertices of the path in order of the path
         * @param cost the cost of the edges connecting the vertices
         * @param labels the mapping of the vertices to labels (subsets)
         */
        public LabeledPath(ArrayList<V> vertices, double cost, HashSet<Integer> labels) {
            this.vertices = vertices;
            this.cost = cost;
            this.labels = labels;
        }

        /**
         * Constructs an empty path with cost Double.MAX_VALUE
         */
        public LabeledPath(int lengthBound) {
            this(new ArrayList<>(lengthBound), Double.MAX_VALUE, new HashSet<>());
        }

        /**
         * Adds a vertex to the path
         *
         * @param v the vertex
         * @param edgeCost the cost of the edge connecting the last vertex of the path and the new vertex
         * @param label the label of the new vertex
         */
        public void addVertex(V v, double edgeCost, int label) {
            this.vertices.add(v);
            this.cost += edgeCost;
            this.labels.add(label);
        }

        /**
         * Returns whether this instance dominates <code>path2</code>.
         * A labeled path path1 dominated another path path2 iff
         * cost of path1 are lower than cost of path2,
         * both paths have the same start and end vertex,
         * and the set of all labels of path1 are a subset of the labels of path 2.
         *
         * @param path2 the second labeled path
         * @return whether this instance dominates <code>path2</code>
         */
        public boolean dominates(LabeledPath<V> path2) {

            if(this.getCost() >= path2.getCost()) {
                return false;
            }
            if(!this.getTail().equals(path2.getTail())) {
                return false;
            }
            if(!this.getHead().equals(path2.getHead())) {
                return false;
            }
            if(!path2.getLabels().containsAll(this.getLabels())) {
                return false;
            }

            return true;
        }

        /**
         * Returns the start vertex of the path
         *
         * @return the start vertex of the path
         */
        public V getHead() {
            return vertices.get(0);
        }

        /**
         * Returns the end vertex of the path
         *
         * @return the end vertex of the path
         */
        public V getTail() {
            return vertices.get(vertices.size() - 1);
        }

        /**
         * Returns whether the path is empty, i.e. has no vertices
         *
         * @return whether the path is empty
         */
        public boolean isEmpty() {
            return vertices.isEmpty();
        }

        /**
         * Returns an ordered list of the vertices of the path
         *
         * @return an ordered list of the vertices of the path
         */
        public List<V> getVertices() {
            return vertices;
        }

        /**
         * Returns the labels of all vertices of the path
         *
         * @return the labels of all vertices of the path
         */
        public Set<Integer> getLabels() {
            return labels;
        }

        /**
         * Returns the cost of the path
         *
         * @return the cost of the path
         */
        public double getCost() {
            return cost;
        }

        /**
         * Returns a shallow copy of this labeled path instance. Vertices are not cloned.
         *
         * @return a shallow copy of this path.
         *
         * @throws RuntimeException in case the clone is not supported
         *
         * @see java.lang.Object#clone()
         */
        public LabeledPath<V> clone() {
            try {
                LabeledPath<V> newLabeledPath = TypeUtil.uncheckedCast(super.clone());
                newLabeledPath.vertices = (ArrayList<V>) this.vertices.clone();
                newLabeledPath.labels = (HashSet<Integer>) this.labels.clone();
                newLabeledPath.cost = this.cost;

                return newLabeledPath;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    private Graph<V, E> graph;
    private Map<V, Integer> labelMap;
    private int lengthBound;

    /**
     * Constructs an algorithm with given inputs
     *
     * @param graph the (improvement) graph on which to calculate the local augmentation
     * @param lengthBound the (inclusive) upper bound for the length of cycles to detect.
     * @param labelMap the labelMap of the vertices encoding the subsets of vertices
     */
    public AhujaOrlinSharmaCyclicExchangeLocalAugmentation(Graph<V, E> graph, int lengthBound, Map<V, Integer> labelMap) {
        this.graph = Objects.requireNonNull(graph, "Graph cannot be null");
        if (!graph.getType().isWeighted()) {
            throw new IllegalArgumentException("Graph is not weighted");
        }
        if (graph.getType().isAllowingSelfLoops()) {
            throw new IllegalArgumentException("Graph cannot have self-loops");
        }
        this.lengthBound = lengthBound;
        this.labelMap = Objects.requireNonNull(labelMap, "Labels cannot be null");
    }

    /**
     * Calculates a valid subset-disjoint negative cycle.
     * If there is no such cycle, it returns an empty LabeledPath instance with cost Double.MAX_VALUE.
     *
     * @return a valid subset-disjoint negative cycle encoded as LabeledPath
     */
    public GraphWalk<V, E> getLocalAugmentationCycle() {

        int k = 1;

        /*
         * Store the path sets via a with Pair<V, V> as for the head and tail of the path such that the domination tests runs faster.
         * Furthermore, use a TreeSet to sort for the cost of the paths such that the domination test stops optimally concerning the cost of the paths.
         */
        Map<Pair<V, V>, TreeSet<LabeledPath<V>>> pathsLengthK = new LinkedHashMap<>();
        Map<Pair<V, V>, TreeSet<LabeledPath<V>>> pathsLengthKplus1 = new LinkedHashMap<>();

        // initialize pathsLengthK for k = 1
        for(E e : graph.edgeSet()) {
            if(graph.getEdgeWeight(e) < 0) {
                // initialize all paths of cost < 0
                V sourceVertex = graph.getEdgeSource(e);
                V targetVertex = graph.getEdgeTarget(e);
                if (!labelMap.get(sourceVertex).equals(labelMap.get(targetVertex))) {
                    ArrayList<V> pathVertices = new ArrayList<>(lengthBound);
                    HashSet<Integer> pathLabels = new HashSet<>();
                    pathVertices.add(sourceVertex);
                    pathVertices.add(targetVertex);
                    pathLabels.add(labelMap.get(sourceVertex));
                    pathLabels.add(labelMap.get(targetVertex));
                    LabeledPath<V> path = new LabeledPath<>(pathVertices, graph.getEdgeWeight(e), pathLabels);

                    // add path to set of paths of length 1
                    addPathToPathStructure(pathsLengthK, path);
                }
            }
        }

        while (k < lengthBound) {

            // go through all valid paths of length k
            for (TreeSet<LabeledPath<V>> tree : pathsLengthK.values()) {
                for(LabeledPath<V> path : tree) {
                    V head = path.getHead();
                    V tail = path.getTail();

                    // the path builds a valid negative cycle
                    if (graph.containsEdge(tail, head)
                            && path.getCost() + graph.getEdgeWeight(graph.getEdge(tail, head)) < 0) {
                        LabeledPath<V> cycleResult = path.clone();
                        cycleResult.addVertex(head, graph.getEdgeWeight(graph.getEdge(tail, head)), labelMap.get(head));

                        return new GraphWalk<>(graph, cycleResult.vertices, cycleResult.cost);
                    }

                    for (E e : graph.outgoingEdgesOf(tail)) {
                        V currentVertex = graph.getEdgeTarget(e);
                        // extend the path if the extension is still negative and correctly labeled
                        double edgeWeight = graph.getEdgeWeight(e);
                        int currentLabel = labelMap.get(currentVertex);
                        if (!path.getLabels().contains(currentLabel) && path.getCost() + edgeWeight < 0) {
                            LabeledPath<V> newPath = path.clone();
                            newPath.addVertex(currentVertex, edgeWeight, currentLabel);

                            /*
                             * check if paths are dominated, i.e. if the path is definitely worse than other paths
                             * and does not have to be considered in the future
                             */
                            if (!removeDominatedPaths(newPath, pathsLengthKplus1)) {
                                addPathToPathStructure(pathsLengthKplus1, newPath);
                            }
                        }
                    }
                }
            }
            // update k and the corresponding sets
            k += 1;
            pathsLengthK = pathsLengthKplus1;
            pathsLengthKplus1 = new LinkedHashMap<>();
        }

        return new GraphWalk<>(graph, new ArrayList<>(lengthBound), Double.MAX_VALUE);
    }

    /**
     * Removes all paths that are dominated from all calculated paths of length k + 1.
     * This is important out of efficiency reasons, otherwise many unnecessary paths may
     * be considered in further calculations.
     *
     *
     * @param path the currently calculated path
     * @param pathsLengthKplus1 all before calculated paths of length k + 1
     *
     * @return whether <code>path</code> was removed in the procedure
     */
    private boolean removeDominatedPaths(LabeledPath<V> path, Map<Pair<V, V>, TreeSet<LabeledPath<V>>> pathsLengthKplus1) {
        boolean removePath = false;

        TreeSet<LabeledPath<V>> pathsToCheck = pathsLengthKplus1.get(Pair.of(path.getHead(), path.getTail()));
        if(pathsToCheck != null) {
            // simulates LabeledPath.dominates(LabeledPath) by using the index structure
            for (Iterator<LabeledPath<V>> it = pathsToCheck.descendingIterator(); it.hasNext(); ) {
                LabeledPath<V> pathInSetKplus1 = it.next();

                if (pathInSetKplus1.dominates(path)) {
                    removePath = true;
                    /*
                     * we can break because domination of paths is transitive, i.e. pathInSetKplus1 already removed the dominated paths
                     */
                    break;
                }
                if (path.dominates(pathInSetKplus1)) {
                    it.remove();
                }
            }
        }

        return removePath;
    }

    private void addPathToPathStructure(Map<Pair<V, V>, TreeSet<LabeledPath<V>>> paths, LabeledPath<V> path) {
        Pair<V, V> currentKey = Pair.of(path.getHead(), path.getTail());
        TreeSet<LabeledPath<V>> currentPathSet = paths.get(currentKey);
        if(currentPathSet == null) {
            currentPathSet = new TreeSet<>(Comparator.comparingDouble(o -> o.cost));
            paths.put(currentKey, currentPathSet);
        }
        currentPathSet.add(path);
    }
}
