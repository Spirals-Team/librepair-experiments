package net.coljate.graph.impl;

import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.GraphTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableHashBackedDirectedGraphTest {

    @Nested
    class ZeroRelationshipsTest implements GraphTest.ZeroRelationshipTests<Object, Object> {

        @Override
        public MutableHashBackedDirectedGraph<Object, Object> createTestCollection() {
            return MutableHashBackedDirectedGraph.create();
        }

    }

    @Nested
    class OneRelationshipTest implements GraphTest.OneRelationshipTest<Object, Object> {

        private DirectedRelationship<Object, Object> relationship;

        @BeforeEach
        public final void resetRelationship() {
            this.relationship = DirectedRelationship.of(new Object(), new Object(), new Object());
        }

        @Override
        public DirectedRelationship<Object, Object> getCollectionElement() {
            return relationship;
        }

        @Override
        public MutableHashBackedDirectedGraph<Object, Object> createTestCollection() {
            final MutableHashBackedDirectedGraph<Object, Object> graph = MutableHashBackedDirectedGraph.create();
            graph.add(this.getCollectionElement());
            return graph;
        }

    }

}
