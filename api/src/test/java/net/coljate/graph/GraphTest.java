package net.coljate.graph;

import net.coljate.set.SetTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface GraphTest<V, E> extends SetTest<Relationship<V, E>> {

    @Override
    Graph<V, E> createTestCollection();

    interface ZeroRelationshipTests<V, E> extends GraphTest<V, E>, SetTest.ZeroElementTests<Relationship<V, E>> {

        @Test
        default void testEdges() {
            final Graph<V, E> graph = this.createTestCollection();
            assertTrue(graph.edges().isEmpty());
            assertThat(graph.size()).isEqualTo(0);
        }

        @Test
        default void testVertices() {
            final Graph<V, E> graph = this.createTestCollection();
            assertTrue(graph.vertices().isEmpty());
            assertThat(graph.order()).isEqualTo(0);
        }

    }

    interface OneRelationshipTest<V, E> extends GraphTest<V, E>, SetTest.OneElementTests<Relationship<V, E>> {

        @Test
        default void testVertices() {
            final Graph<V, E> graph = this.createTestCollection();
            assertThat(graph.vertices().count()).isEqualTo(2);
            assertThat(graph.order()).isEqualTo(2);
        }

        @Test
        default void testEdges() {
            final Graph<V, E> graph = this.createTestCollection();
            assertThat(graph.edges().count()).isEqualTo(1);
            assertThat(graph.size()).isEqualTo(1);
        }

    }

}
