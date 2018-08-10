package org.esa.s2tbx.grm;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.esa.s2tbx.grm.segmentation.BaatzSchapeNode;
import org.esa.s2tbx.grm.segmentation.Graph;
import org.esa.s2tbx.grm.segmentation.Node;
import org.esa.s2tbx.grm.segmentation.tiles.AbstractTileSegmenter;
import org.esa.s2tbx.grm.segmentation.tiles.ProcessingTile;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jean Coravu.
 */
public class GraphTest {

    public GraphTest() {
    }

    @Test
    public void testDetectBorderNodes() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        List<Node> result = graph.detectBorderNodes(tile, imageWidth, imageHeight);
        assertNotNull(result);
        assertEquals(1, result.size());

        Node node = result.get(0);
        assertNotNull(node);
        assertEquals(2, node.getId());
    }

    @Test
    public void testBuildBorderPixelMap() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;
        int tileCountX = (imageWidth / tileSizeX);
        int tileCountY = (imageHeight / tileSizeY);

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        Int2ObjectMap<List<Node>> borderPixelMap = graph.buildBorderPixelMap(tile, 0, 0, tileCountX, tileCountY, imageWidth);

        assertNotNull(borderPixelMap);
        assertEquals(2, borderPixelMap.size());
    }

    @Test
    public void testRemoveDuplicatedNodes() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;
        int tileCountX = (imageWidth / tileSizeX);
        int tileCountY = (imageHeight / tileSizeY);

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        Int2ObjectMap<List<Node>> borderPixelMap = graph.buildBorderPixelMap(tile, 0, 0, tileCountX, tileCountY, imageWidth);

        assertNotNull(borderPixelMap);
        assertEquals(2, borderPixelMap.size());

        graph.removeDuplicatedNodes(borderPixelMap, imageWidth);

        assertEquals(3, graph.getNodeCount());
    }

    @Test
    public void testRemoveUnstableSegments() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        graph.removeUnstableSegments(tile, imageWidth);

        assertEquals(2, graph.getNodeCount());
    }

    @Test
    public void testFindUselessNodes() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        List<Node> nodesToIterate = graph.findUselessNodes(tile, imageWidth);
        assertNotNull(nodesToIterate);
        assertEquals(2, nodesToIterate.size());
    }

    @Test
    public void testRemoveUselessNodes() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        Int2ObjectMap<Node> borderNodes = new Int2ObjectLinkedOpenHashMap<Node>(1);
        Node node = graph.getNodeAt(0);
        borderNodes.put(node.getId(), node);
        graph.removeUselessNodes(borderNodes, tile);

        assertEquals(1, graph.getNodeCount());
    }

    @Test
    public void testRescaleGraph() {
        int imageWidth = 10;
        int imageHeight = 10;
        int tileLeftX = 0;
        int tileTopY = 0;
        int tileSizeX = 3;
        int tileSizeY = 3;
        int tileMargin = 1;

        Graph graph = checkGraph(imageWidth);

        ProcessingTile tile = AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, tileMargin, imageWidth, imageHeight);

        assertEquals(0, graph.getNodeAt(0).getId());
        assertEquals(2, graph.getNodeAt(1).getId());
        assertEquals(5, graph.getNodeAt(2).getId());

        graph.rescaleGraph(tile, imageWidth);

        assertEquals(0, graph.getNodeAt(0).getId());
        assertEquals(2, graph.getNodeAt(1).getId());
        assertEquals(11, graph.getNodeAt(2).getId());
    }

    private static Graph checkGraph(int imageWidth) {
        BaatzSchapeNode firstNode = new BaatzSchapeNode(0, 0, 0, 0);
        BaatzSchapeNode secondNode = new BaatzSchapeNode(1, 1, 0, 0);
        BaatzSchapeNode thirdNode = new BaatzSchapeNode(2, 2, 0, 0);
        BaatzSchapeNode forthNode = new BaatzSchapeNode(3, 3, 0, 0);
        BaatzSchapeNode fifthNode = new BaatzSchapeNode(4, 4, 0, 0);
        BaatzSchapeNode sixthNode = new BaatzSchapeNode(5, 5, 0, 0);

        firstNode.addEdge(secondNode, 1);
        firstNode.addEdge(thirdNode, 1);
        firstNode.addEdge(forthNode, 1);

        thirdNode.addEdge(forthNode, 1);
        thirdNode.addEdge(fifthNode, 1);
        thirdNode.addEdge(sixthNode, 1);

        forthNode.addEdge(fifthNode, 1);
        forthNode.addEdge(sixthNode, 1);
        forthNode.addEdge(thirdNode, 1);

        sixthNode.addEdge(thirdNode, 1);

        Graph graph = new Graph();
        graph.addNode(firstNode);
        graph.addNode(secondNode);
        graph.addNode(thirdNode);
        graph.addNode(forthNode);
        graph.addNode(fifthNode);
        graph.addNode(sixthNode);

        firstNode.updateInternalAttributes(secondNode, imageWidth);

        thirdNode.updateInternalAttributes(forthNode, imageWidth);
        thirdNode.updateInternalAttributes(fifthNode, imageWidth);

        assertEquals(6, graph.getNodeCount());

        graph.removeExpiredNodes();

        assertEquals(3, graph.getNodeCount());

        return graph;
    }
}
