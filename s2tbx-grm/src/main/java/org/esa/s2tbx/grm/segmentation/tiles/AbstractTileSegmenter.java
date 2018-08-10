package org.esa.s2tbx.grm.segmentation.tiles;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.esa.s2tbx.grm.segmentation.*;
import org.esa.snap.core.gpf.Tile;
import org.esa.snap.core.util.io.FileUtils;
import org.esa.snap.utils.BufferedInputStreamWrapper;
import org.esa.snap.utils.BufferedOutputStreamWrapper;
import org.esa.snap.utils.ObjectMemory;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jean Coravu
 */
public abstract class AbstractTileSegmenter {
    private static final Logger logger = Logger.getLogger(AbstractTileSegmenter.class.getName());

    private final File temporaryFolder;
    private final float threshold;
    private final boolean addFourNeighbors;
    private final boolean fastSegmentation;
    private final int imageWidth;
    private final int imageHeight;
    private final int totalIterationsForSecondSegmentation;
    private final int iterationsForEachFirstSegmentation;
    private final int tileWidth;
    private final int tileHeight;
    private final int iterationsForEachSecondSegmentation;
    private final TileSegmenterMetadata tileSegmenterMetadata;

    protected AbstractTileSegmenter(Dimension imageSize, Dimension tileSize, int totalIterationsForSecondSegmentation, float threshold, boolean fastSegmentation)
                                    throws IOException {

        this.imageWidth = imageSize.width;
        this.imageHeight = imageSize.height;
        this.tileWidth = tileSize.width;
        this.tileHeight = tileSize.height;
        this.totalIterationsForSecondSegmentation = totalIterationsForSecondSegmentation;
        this.iterationsForEachFirstSegmentation = computeNumberOfFirstIterations(this.tileWidth, this.tileHeight);
        this.fastSegmentation = fastSegmentation;
        this.threshold = threshold;
        this.temporaryFolder = Files.createTempDirectory("_temp").toFile();

        this.addFourNeighbors = true;
        this.iterationsForEachSecondSegmentation = 3; // TODO: find a smart value
        this.tileSegmenterMetadata = new TileSegmenterMetadata();
    }

    protected abstract Node buildNode(int nodeId, BoundingBox box, Contour contour, int perimeter, int area, int numberOfComponentsPerPixel);

    public abstract AbstractSegmenter buildSegmenter(float threshold);

    public final int computeTileMargin() {
        return (int) (Math.pow(2, this.iterationsForEachFirstSegmentation + 1) - 2);
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public final ProcessingTile buildTile(int tileLeftX, int tileTopY, int tileSizeX, int tileSizeY) {
        int margin = computeTileMargin();
        return AbstractTileSegmenter.buildTile(tileLeftX, tileTopY, tileSizeX, tileSizeY, margin, this.imageWidth, this.imageHeight);
    }

    public final AbstractSegmenter runAllTilesSecondSegmentation() throws IllegalAccessException, IOException {
        if (logger.isLoggable(Level.FINE)) {
            int tileCountX = this.tileSegmenterMetadata.getComputedTileCountX();
            int tileCountY = this.tileSegmenterMetadata.getComputedTileCountY();
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Run second segmentation for all tiles. Total iterations: " + this.totalIterationsForSecondSegmentation + ", tile column count: " +tileCountX+", tile row count: " + tileCountY + ", acumulated memory: " + this.tileSegmenterMetadata.getAccumulatedMemory()+", fusion: " + this.tileSegmenterMetadata.isFusion()+", margin: "+computeTileMargin()+", number of second iterations: "+this.iterationsForEachSecondSegmentation);
        }

        int numberOfIterationsRemaining = this.totalIterationsForSecondSegmentation;
        int iteration = 0;
        while (this.tileSegmenterMetadata.getAccumulatedMemory() > this.tileSegmenterMetadata.getAvailableMemory() && this.tileSegmenterMetadata.isFusion()) {
            iteration++;
            runSecondPartialSegmentation(iteration);

            // update number of remaining iterations
            if (numberOfIterationsRemaining < this.iterationsForEachSecondSegmentation) {
                break;
            } else {
                numberOfIterationsRemaining -= this.iterationsForEachSecondSegmentation;
            }
        }
        if (this.tileSegmenterMetadata.getAccumulatedMemory() > this.tileSegmenterMetadata.getAvailableMemory()) {
            throw new IllegalArgumentException("No more possible fusions, but can not store the output graph.");
        }
        try {
            return mergeAllGraphsAndAchieveSegmentation(numberOfIterationsRemaining);
        } finally {
            FileUtils.deleteTree(this.temporaryFolder);
        }
    }

    public final int computeTileRowIndex(ProcessingTile tile) {
        return tile.getImageTopY() / this.tileHeight;
    }

    public final int computeTileColumnIndex(ProcessingTile tile) {
        return tile.getImageLeftX() / this.tileWidth;
    }

    public void runOneTileFirstSegmentation(Tile[] sourceTiles, ProcessingTile tileToProcess) throws IllegalAccessException, IOException {
        int tileColumnIndex = computeTileColumnIndex(tileToProcess);
        int tileRowIndex = computeTileRowIndex(tileToProcess);

        if (logger.isLoggable(Level.FINE)) {
            int tileMargin = computeTileMargin();
            int firstNumberOfIterations = getIterationsForEachFirstSegmentation();
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Compute tile: row index: "+tileRowIndex+", column index: "+tileColumnIndex+", margin: "+tileMargin+", bounds: [x=" +tileToProcess.getImageLeftX()+", y="+tileToProcess.getImageTopY()+", width="+tileToProcess.getImageWidth()+", height="+tileToProcess.getImageHeight()+"], first number of iterations: "+firstNumberOfIterations);
        }

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, ""); // add an empty line
            logger.log(Level.FINEST, "Run tile first segmentation: tile region: " + tileRegionToString(tileToProcess.getRegion()) + ", tile row index: " +tileRowIndex+", tile column index: " + tileColumnIndex+", number of iterations: "+this.iterationsForEachFirstSegmentation+", margin: "+computeTileMargin()+", number of first iterations: "+this.iterationsForEachFirstSegmentation);
        }

        int numberOfNeighborLayers = computeNumberOfNeighborLayers();

        AbstractSegmenter segmenter = buildSegmenter(this.threshold);
        boolean complete = segmenter.update(sourceTiles, tileToProcess.getRegion(), this.iterationsForEachFirstSegmentation, this.fastSegmentation, this.addFourNeighbors);
        Graph graph = segmenter.getGraph();

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run tile first segmentation (after segmentation): graph node count: " + graph.getNodeCount());
        }

        // rescale the graph to be in the reference of the image
        graph.rescaleGraph(tileToProcess, this.imageWidth);

        // remove unstable segments
        graph.removeUnstableSegments(tileToProcess, this.imageWidth);

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run tile first segmentation (after remove unstable nodes): graph node count: " + graph.getNodeCount());
        }

        long graphMemory = ObjectMemory.computeSizeOf(graph);

        writeGraph(graph, tileToProcess.getNodeFileName(), tileToProcess.getEdgeFileName());

        // extract stability margin for all borders different from 0 imageWidth-1 and imageHeight -1 and write them to the stability margin
        List<Node> nodesToIterate = graph.detectBorderNodes(tileToProcess, this.imageWidth, this.imageHeight);

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run tile first segmentation (after detect border nodes): graph node count: "+graph.getNodeCount()+", boder node count: " + nodesToIterate.size());
        }

        Int2ObjectMap<Node> borderNodes = extractStabilityMargin(nodesToIterate, numberOfNeighborLayers);

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run tile first segmentation: node count to write for stability margin: " + borderNodes.size());
        }

        writeStabilityMargin(borderNodes, tileToProcess.getNodeMarginFileName(), tileToProcess.getEdgeMarginFileName());

        synchronized (this.tileSegmenterMetadata) {
            this.tileSegmenterMetadata.addTile(tileRowIndex, tileColumnIndex, tileToProcess.getImageLeftX(), tileToProcess.getImageTopY(), tileToProcess.getImageWidth(), tileToProcess.getImageHeight());
            this.tileSegmenterMetadata.addAccumulatedMemory(graphMemory);
            if (!complete) {
                this.tileSegmenterMetadata.setFusion(true);
            }
        }
    }

    public final int getIterationsForEachFirstSegmentation() {
        return iterationsForEachFirstSegmentation;
    }

    protected void writeNode(BufferedOutputStreamWrapper nodesFileStream, Node nodeToWrite) throws IOException {
        nodesFileStream.writeInt(nodeToWrite.getId());
        nodesFileStream.writeInt(nodeToWrite.getPerimeter());
        nodesFileStream.writeInt(nodeToWrite.getArea());

        BoundingBox box = nodeToWrite.getBox();
        nodesFileStream.writeInt(box.getLeftX());
        nodesFileStream.writeInt(box.getTopY());
        nodesFileStream.writeInt(box.getWidth());
        nodesFileStream.writeInt(box.getHeight());

        Contour contour = nodeToWrite.getContour();
        nodesFileStream.writeInt(contour.size());
        byte[] bits = contour.getBits();
        nodesFileStream.writeInt(bits.length);
        nodesFileStream.write(bits);

        nodesFileStream.writeInt(nodeToWrite.getNumberOfComponentsPerPixel());
    }

    protected Node readNode(BufferedInputStreamWrapper nodesFileStream) throws IOException {
        int nodeId = nodesFileStream.readInt();
        int perimeter = nodesFileStream.readInt();
        int area = nodesFileStream.readInt();

        int upperLeftX = nodesFileStream.readInt();
        int upperLeftY = nodesFileStream.readInt();
        int width = nodesFileStream.readInt();
        int height = nodesFileStream.readInt();
        BoundingBox box = new BoundingBox(upperLeftX, upperLeftY, width, height);

        int contourSize = nodesFileStream.readInt();
        int byteCount = nodesFileStream.readInt();
        byte[] bits = new byte[byteCount];
        nodesFileStream.readFully(bits);
        Contour contour = new Contour(contourSize, bits);

        int numberOfComponentsPerPixel = nodesFileStream.readInt();

        return buildNode(nodeId, box, contour, perimeter, area, numberOfComponentsPerPixel);
    }

    private int computeNumberOfNeighborLayers() {
        return (int) (Math.pow(2, this.iterationsForEachSecondSegmentation + 1) - 2);
    }

    private void runSecondPartialSegmentation(int iteration) throws IllegalAccessException, IOException {
        int tileCountX = this.tileSegmenterMetadata.getComputedTileCountX();
        int tileCountY = this.tileSegmenterMetadata.getComputedTileCountY();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Run second segmentation: iteration: "+iteration+", tile column count: " +tileCountX+", tile row count: " + tileCountY + ", acumulated memory: " + this.tileSegmenterMetadata.getAccumulatedMemory()+", fusion: " + this.tileSegmenterMetadata.isFusion()+", margin: "+computeTileMargin());
        }

        this.tileSegmenterMetadata.resetValues();

        int numberOfNeighborLayers = computeNumberOfNeighborLayers();

        for (int rowIndex = 0; rowIndex < tileCountY; rowIndex++) {
            for (int columnIndex = 0; columnIndex < tileCountX; columnIndex++) {
                BoundingBox rectangle = this.tileSegmenterMetadata.getTileAt(rowIndex, columnIndex);
                ProcessingTile currentTile = buildTile(rectangle.getLeftX(), rectangle.getTopY(), rectangle.getWidth(), rectangle.getHeight());

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, ""); // add an empty line
                    logger.log(Level.FINEST, "Run second segmentation: iteration: "+iteration+", tile region: " +tileRegionToString(currentTile.getRegion())+ ", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                Graph graph = readGraphSecondPartialSegmentation(currentTile, rowIndex, columnIndex, tileCountX, tileCountY);

                Int2ObjectMap<List<Node>> borderPixelMap = graph.buildBorderPixelMap(currentTile, rowIndex, columnIndex, tileCountX, tileCountY, this.imageWidth);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (after building border pixel map): graph node count: " +graph.getNodeCount()+", map size: "+borderPixelMap.size()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                graph.removeDuplicatedNodes(borderPixelMap, this.imageWidth);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (after removing duplicate nodes): graph node count: " +graph.getNodeCount()+", map size: "+borderPixelMap.size()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                updateNeighborsOfNoneDuplicatedNodes(borderPixelMap, this.imageWidth, this.imageHeight);

                // remove the useless nodes
                List<Node> nodesToIterate = graph.findUselessNodes(currentTile, this.imageWidth);
                Int2ObjectMap<Node> borderNodes = extractStabilityMargin(nodesToIterate, numberOfNeighborLayers);
                graph.removeUselessNodes(borderNodes, currentTile);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (after removing useless nodes): graph node count: " +graph.getNodeCount()+", numberOfNeighborLayers: "+numberOfNeighborLayers+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                // build the segmenter
                AbstractSegmenter segmenter = buildSegmenter(this.threshold);
                segmenter.setGraph(graph, this.imageWidth, this.imageHeight);
                boolean merged = segmenter.performAllIterationsWithLMBF(this.iterationsForEachSecondSegmentation);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (after segmentation): graph node count: " +graph.getNodeCount()+", completed="+(!merged)+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                graph.removeUnstableSegments(currentTile, this.imageWidth);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (after removing unstable nodes): graph node count: " +graph.getNodeCount()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                this.tileSegmenterMetadata.addAccumulatedMemory(ObjectMemory.computeSizeOf(graph));
                if (merged) {
                    this.tileSegmenterMetadata.setFusion(true);
                }

                writeGraph(graph, currentTile.getNodeFileName(), currentTile.getEdgeFileName());
            }
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Run second segmentation: (extract the stability margin for the next round): tile column count: " +tileCountX+", tile row count: " + tileCountY + ", acumulated memory: " + this.tileSegmenterMetadata.getAccumulatedMemory()+", fusion: " + this.tileSegmenterMetadata.isFusion());
        }

        // during this step we extract the stability margin for the next round
        for(int rowIndex = 0; rowIndex < tileCountY; rowIndex++) {
            for (int columnIndex = 0; columnIndex<tileCountX; columnIndex++) {
                BoundingBox rectangle = this.tileSegmenterMetadata.getTileAt(rowIndex, columnIndex);
                ProcessingTile currentTile = buildTile(rectangle.getLeftX(), rectangle.getTopY(), rectangle.getWidth(), rectangle.getHeight());

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, ""); // add an empty line
                    logger.log(Level.FINEST, "Run second segmentation: (extract the stability margin): iteration: "+iteration+", tile region: " +tileRegionToString(currentTile.getRegion())+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                Graph graph = readGraph(currentTile.getNodeFileName(), currentTile.getEdgeFileName());

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (extract the stability margin - after read graph): graph node count: "+graph.getNodeCount()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                List<Node> nodesToIterate = graph.detectBorderNodes(currentTile, this.imageWidth, this.imageHeight);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (extract the stability margin): border node count: " + nodesToIterate.size()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                Int2ObjectMap<Node> borderNodes = extractStabilityMargin(nodesToIterate, numberOfNeighborLayers);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Run second segmentation: (extract the stability margin): node count to write for stability margin: " + borderNodes.size()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
                }

                writeStabilityMargin(borderNodes, currentTile.getNodeMarginFileName(), currentTile.getEdgeMarginFileName());
            }
        }
    }

    private AbstractSegmenter mergeAllGraphsAndAchieveSegmentation(int numberOfIterations) throws IOException {
        Graph graph = new Graph();
        int tileCountX = this.tileSegmenterMetadata.getComputedTileCountX();
        int tileCountY = this.tileSegmenterMetadata.getComputedTileCountY();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Merge all graphs: number of iterations: "+numberOfIterations+", tile column count: " +tileCountX+", tile row count: " + tileCountY);
        }

        for (int row = 0; row < tileCountY; row++) {
            for (int col = 0; col < tileCountX; col++) {
                BoundingBox rectangle = this.tileSegmenterMetadata.getTileAt(row, col);
                ProcessingTile currentTile = buildTile(rectangle.getLeftX(), rectangle.getTopY(), rectangle.getWidth(), rectangle.getHeight());

                if (logger.isLoggable(Level.FINEST)) {
                    if (row ==0 && col == 0) {
                        logger.log(Level.FINEST, ""); // add an empty line
                    }
                    logger.log(Level.FINEST, "Merge all graphs: (before insert nodes from tile): number of iterations: "+numberOfIterations+", graph node count: "+graph.getNodeCount()+", tile region: "+tileRegionToString(currentTile.getRegion())+", tile row index: " + row + ", tile column index: " + col);
                }

                Graph subgraph = readGraph(currentTile.getNodeFileName(), currentTile.getEdgeFileName());
                graph.addNodes(subgraph);
            }
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Merge all graphs: (removing duplicated nodes): number of iterations: "+numberOfIterations+", graph node count: "+graph.getNodeCount() + ", tile column count: " +tileCountX+", tile row count: " + tileCountY);
        }

        // removing duplicated nodes and updating neighbors
        for (int row = 0; row < tileCountY; row++) {
            for (int col = 0; col < tileCountX; col++) {
                BoundingBox rectangle = this.tileSegmenterMetadata.getTileAt(row, col);
                ProcessingTile currentTile = buildTile(rectangle.getLeftX(), rectangle.getTopY(), rectangle.getWidth(), rectangle.getHeight());

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, ""); // add an empty line
                    logger.log(Level.FINEST, "Merge all graphs: (removing duplicated nodes): number of iterations: "+numberOfIterations+", tile region: " +tileRegionToString(currentTile.getRegion())+", tile row index: "+row+", tile column index: "+col);
                }

                Int2ObjectMap<List<Node>> borderPixelMap = graph.buildBorderPixelMap(currentTile, row, col, tileCountX, tileCountY, this.imageWidth);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Merge all graphs: (removing duplicated nodes - after building border pixel map): graph node count: " +graph.getNodeCount()+", map size: "+borderPixelMap.size()+", tile row index: " + row + ", tile column index: " + col);
                }

                graph.removeDuplicatedNodes(borderPixelMap, this.imageWidth);

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Merge all graphs: (removing duplicated nodes - after removing duplicate nodes): graph node count: " +graph.getNodeCount()+", map size: "+borderPixelMap.size()+", tile row index: " + row + ", tile column index: " + col);
                }

                updateNeighborsOfNoneDuplicatedNodes(borderPixelMap, this.imageWidth, this.imageHeight);
            }
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ""); // add an empty line
            logger.log(Level.FINE, "Merge all graphs: (before segmentation): number of iterations: "+numberOfIterations+", graph node count: "+graph.getNodeCount()+", tile column count: "+tileCountX+", tile row count: "+tileCountY);
        }

        // segmentation of the graph
        AbstractSegmenter segmenter = buildSegmenter(this.threshold);
        segmenter.setGraph(graph, this.imageWidth, this.imageHeight);
        segmenter.performAllIterationsWithLMBF(numberOfIterations);
        return segmenter;
    }

    private Graph readGraphMarginsFromTile(BoundingBox tileRectangle) throws IOException {
        ProcessingTile tile = buildTile(tileRectangle.getLeftX(), tileRectangle.getTopY(), tileRectangle.getWidth(), tileRectangle.getHeight());
        return readGraph(tile.getNodeMarginFileName(), tile.getEdgeMarginFileName());
    }

    private Graph readGraphSecondPartialSegmentation(ProcessingTile currentTile, int rowIndex, int columnIndex, int tileCountX, int tileCountY) throws IOException {
        Graph graph = readGraph(currentTile.getNodeFileName(), currentTile.getEdgeFileName());

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run second segmentation: (after read graph): graph node count: " +graph.getNodeCount()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
        }

        // add the stability margin to retrieve at top
        if (rowIndex > 0) { // (startYWithoutMargin > 0) { //(row > 0) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex-1, columnIndex));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at right
        if (columnIndex < tileCountX - 1) { //(finishXWithoutMargin < this.imageWidth) { //(col < nbTilesX - 1) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex, columnIndex+1));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at bottom
        if (rowIndex < tileCountY - 1) { //(finishYWithoutMargin < this.imageHeight) { //(row < nbTilesY - 1) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex+1, columnIndex));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at left
        if (columnIndex > 0) { // (startXWithoutMargin > 0) { //(col > 0) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex, columnIndex-1));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at top right
        if (rowIndex > 0 && columnIndex < tileCountX - 1) { // (startYWithoutMargin > 0 && finishXWithoutMargin < this.imageWidth) { //(row > 0 && col < nbTilesX - 1) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex-1, columnIndex+1));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at bottom right
        if (rowIndex < tileCountY - 1 && columnIndex < tileCountX - 1) { // (finishYWithoutMargin < this.imageHeight && finishXWithoutMargin < this.imageWidth) { //(row < nbTilesY - 1 && col < nbTilesX - 1) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex+1, columnIndex+1));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at bottom left
        if (rowIndex < tileCountY - 1 && columnIndex > 0) { // (finishYWithoutMargin < this.imageHeight && startXWithoutMargin > 0) { //(row < nbTilesY - 1 && col > 0) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex+1, columnIndex-1));
            graph.addNodes(subgraph);
        }
        // add the stability margin to retrieve at top left
        if (rowIndex > 0 && columnIndex > 0) { // (startYWithoutMargin > 0 && startXWithoutMargin > 0) { //(row > 0 && col > 0) {
            Graph subgraph = readGraphMarginsFromTile(this.tileSegmenterMetadata.getTileAt(rowIndex-1, columnIndex-1));
            graph.addNodes(subgraph);
        }

        if (logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Run second segmentation: (after add stability margin): graph node count: " +graph.getNodeCount()+", tile row index: " + rowIndex + ", tile column index: " + columnIndex);
        }

        return graph;
    }

    private void writeGraph(Graph graph, String nodesPath, String edgesPath) throws IOException {
        BufferedOutputStreamWrapper nodesFileStream = null;
        BufferedOutputStreamWrapper edgesFileStream = null;
        try {
            File nodesFile = new File(this.temporaryFolder, nodesPath);
            nodesFileStream = new BufferedOutputStreamWrapper(nodesFile);

            File edgesFile = new File(this.temporaryFolder, edgesPath);
            edgesFileStream = new BufferedOutputStreamWrapper(edgesFile);

            int nodeCount = graph.getNodeCount();
            nodesFileStream.writeInt(nodeCount);

            for (int i=0; i<nodeCount; i++) {
                Node node = graph.getNodeAt(i);
                writeNode(nodesFileStream, node);

                // write the node id in the edge file
                edgesFileStream.writeInt(node.getId());

                int edgeCount = node.getEdgeCount();
                edgesFileStream.writeInt(edgeCount);
                for (int k=0; k<edgeCount; k++) {
                    Edge edge = node.getEdgeAt(k);
                    writeEdge(edgesFileStream, edge);
                }
            }
        } finally {
            if (nodesFileStream != null) {
                try {
                    nodesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
            if (edgesFileStream != null) {
                try {
                    edgesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
        }
    }

    private void writeEdge(BufferedOutputStreamWrapper edgesFileStream, Edge edge) throws IOException {
        edgesFileStream.writeInt(edge.getTarget().getId());
        edgesFileStream.writeInt(edge.getBoundary());
    }

    private Graph readGraph(String nodesPath, String edgesPath) throws IOException {
        BufferedInputStreamWrapper nodesFileStream = null;
        BufferedInputStreamWrapper edgesFileStream = null;
        try {
            File nodesFile = new File(this.temporaryFolder, nodesPath);
            nodesFileStream = new BufferedInputStreamWrapper(nodesFile);

            int nodeCount = nodesFileStream.readInt();
            Int2ObjectMap<Node> nodesMap = new Int2ObjectLinkedOpenHashMap<Node>(nodeCount);
            Graph graph = new Graph();
            for (int i=0; i<nodeCount; i++) {
                Node node = readNode(nodesFileStream);
                nodesMap.put(node.getId(), node);
                graph.addNode(node);
            }

            File edgesFile = new File(this.temporaryFolder, edgesPath);
            edgesFileStream = new BufferedInputStreamWrapper(edgesFile);

            for (int i=0; i<nodeCount; i++) {
                Node node = graph.getNodeAt(i);
                int nodeId = edgesFileStream.readInt();
                assert(node.getId() == nodeId);

                int edgeCount = edgesFileStream.readInt();
                for (int k=0; k<edgeCount; k++) {
                    int targetNodeId = edgesFileStream.readInt();
                    int boundary = edgesFileStream.readInt();
                    Node targetNode = nodesMap.get(targetNodeId);
                    node.addEdge(targetNode, boundary);
                }
            }

            return graph;
        } finally {
            if (nodesFileStream != null) {
                try {
                    nodesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
            if (edgesFileStream != null) {
                try {
                    edgesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
        }
    }

    private void writeStabilityMargin(Int2ObjectMap<Node> borderNodes, String nodesPath, String edgesPath) throws IOException {
        BufferedOutputStreamWrapper nodesFileStream = null;
        BufferedOutputStreamWrapper edgesFileStream = null;
        try {
            File nodesFile = new File(this.temporaryFolder, nodesPath);
            nodesFileStream = new BufferedOutputStreamWrapper(nodesFile);

            File edgesFile = new File(this.temporaryFolder, edgesPath);
            edgesFileStream = new BufferedOutputStreamWrapper(edgesFile);

            // write the number of nodes
            nodesFileStream.writeInt(borderNodes.size());

            ObjectIterator<Int2ObjectMap.Entry<Node>> it = borderNodes.int2ObjectEntrySet().iterator();
            while (it.hasNext()) {
                Int2ObjectMap.Entry<Node> entry = it.next();
                Node node = entry.getValue();
                writeNode(nodesFileStream, node);

                // write the node id in the edge file
                edgesFileStream.writeInt(node.getId());

                int edgeCountToWrite = 0;
                int edgeCount = node.getEdgeCount();
                for (int k=0; k<edgeCount; k++) {
                    Edge edge = node.getEdgeAt(k);
                    Node targetNode = edge.getTarget();
                    if (borderNodes.containsKey(targetNode.getId())) {
                        edgeCountToWrite++;
                    }
                }

                // write only edges pointing to nodes which are in the stability margin.
                edgesFileStream.writeInt(edgeCountToWrite);

                for (int k=0; k<edgeCount; k++) {
                    Edge edge = node.getEdgeAt(k);
                    Node targetNode = edge.getTarget();
                    if (borderNodes.containsKey(targetNode.getId())) {
                        writeEdge(edgesFileStream, edge);
                    }
                }
            }
        } finally {
            if (nodesFileStream != null) {
                try {
                    nodesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
            if (edgesFileStream != null) {
                try {
                    edgesFileStream.close();
                } catch (IOException exception) {
                    // ignore exception
                }
            }
        }
    }

    public static ProcessingTile buildTile(int tileLeftX, int tileTopY, int tileSizeX, int tileSizeY, int tileMargin, int imageWidth, int imageHeight) {
        // compute current tile start and size
        ProcessingTile tile = new ProcessingTile();
        int finishX = tileLeftX + tileSizeX;
        int finishY = tileTopY + tileSizeY;
        // margin at the top
        if (tileTopY > 0) { //(row > 0) {
            tile.setTopMargin(tileMargin);
            tile.setImageTopY(tileTopY); // tile.rows[0] = startY;//row * tileHeight;
        } else {
            // the tile is on the top row --> no top margin
            tile.setTopMargin(0);
            tile.setImageTopY(0); // tile.rows[0] = 0;
        }

        // margin at the right
        if (finishX < imageWidth) { //(col < nbTilesX - 1) {
            tile.setRightMargin(tileMargin);
            tile.setImageRightX(tileLeftX + tileSizeX - 1); // tile.columns[1] = startX + sizeX - 1; //sizeX
        } else {
            // the tile is on the right column --> no right margin
            tile.setRightMargin(0);
            tile.setImageRightX(imageWidth - 1); // tile.columns[1] = imageWidth - 1;
        }

        // margin at the bottom
        if (finishY < imageHeight) { // (row < nbTilesY - 1) {
            tile.setBottomMargin(tileMargin);
            tile.setImageBottomY(tileTopY + tileSizeY - 1); // tile.rows[1] = startY + sizeY - 1; // sizeY
        } else {
            // the tile is on the bottom --> no bottom margin
            tile.setBottomMargin(0);
            tile.setImageBottomY(imageHeight - 1); // tile.rows[1] = imageHeight - 1;
        }

        // margin at the left
        if (tileLeftX > 0) { //(col > 0) {
            tile.setLeftMargin(tileMargin);
            tile.setImageLeftX(tileLeftX); // tile.columns[0] = startX;//col * tileWidth;
        } else {
            // the tile is on the left --> no left margin
            tile.setLeftMargin(0);
            tile.setImageLeftX(0); // tile.columns[0] = 0;
        }

        // store the tile region
        int regionLeftX = tileLeftX - tile.getLeftMargin();
        int regionTopY = tileTopY - tile.getTopMargin();
        int regionWidth = tileSizeX + tile.getLeftMargin() + tile.getRightMargin();
        int regionHeight = tileSizeY + tile.getTopMargin() + tile.getBottomMargin();
        tile.setRegion(new BoundingBox(regionLeftX, regionTopY, regionWidth, regionHeight));

        String temporaryFilesPrefix = "";
        String suffix = Integer.toString(tileLeftX) + "_" + Integer.toString(tileTopY) + ".bin";
        tile.setNodeFileName(temporaryFilesPrefix + "_node_" + suffix);
        tile.setEdgeFileName(temporaryFilesPrefix + "_edge_" + suffix);
        tile.setNodeMarginFileName(temporaryFilesPrefix + "_nodeMargin_" + suffix);
        tile.setEdgeMarginFileName(temporaryFilesPrefix + "_edgeMargin_" + suffix);

        return tile;
    }

    private static void updateNeighborsOfNoneDuplicatedNodes(Int2ObjectMap<List<Node>> borderPixelMap, int imageWidth, int imageHeight) {
        int[] neighborhood = new int[4];
        int[] cellNeighborhood = new int[4];

        ObjectIterator<Int2ObjectMap.Entry<List<Node>>> it = borderPixelMap.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<List<Node>> entry = it.next();
            int nodeId = entry.getIntKey();
            List<Node> nodes = entry.getValue();
            AbstractSegmenter.generateFourNeighborhood(neighborhood, nodeId, imageWidth, imageHeight);
            for(int j = 0; j < neighborhood.length; j++) {
                if (neighborhood[j] > -1) {
                    List<Node> neighborNodes = borderPixelMap.get(neighborhood[j]);
                    if (neighborNodes != null) {
                        Node currentNode = nodes.get(0); // currNode
                        Node firstNeighborNode = neighborNodes.get(0); // neigh
                        if (currentNode != firstNeighborNode) {
                            Edge edge = currentNode.findEdge(firstNeighborNode);
                            if (edge == null) {
                                int boundary = 0;
                                IntSet borderCells = AbstractSegmenter.generateBorderCells(currentNode.getContour(), currentNode.getId(), imageWidth);
                                IntIterator itCells = borderCells.iterator();
                                while (itCells.hasNext()) {
                                    int gridId = itCells.nextInt();
                                    List<Node> resultNodes = borderPixelMap.get(gridId);
                                    if (resultNodes != null) {
                                        AbstractSegmenter.generateFourNeighborhood(cellNeighborhood, gridId, imageWidth, imageHeight);
                                        for(int k = 0; k < cellNeighborhood.length; k++) {
                                            if (cellNeighborhood[k] > -1) {
                                                List<Node> cellNeighborNodes = borderPixelMap.get(cellNeighborhood[k]);
                                                if (cellNeighborNodes != null && cellNeighborNodes.get(0) == firstNeighborNode) {
                                                    boundary++;
                                                }
                                            }
                                        }
                                    }
                                }
                                currentNode.addEdge(firstNeighborNode, boundary);
                                firstNeighborNode.addEdge(currentNode, boundary);
                            }
                        }
                    }
                }
            }
        }
    }

    private static String tileRegionToString(BoundingBox region) {
        StringBuilder str = new StringBuilder();
        str.append("[x=")
                .append(region.getLeftX())
                .append(", y=")
                .append(region.getTopY())
                .append(", width=")
                .append(region.getWidth())
                .append(", height=")
                .append(region.getHeight())
                .append("]");
        return str.toString();
    }

    private static Int2ObjectMap<Node> extractStabilityMargin(List<Node> nodesToIterate, int numberOfLayers) {
        int defaultMissingValue = -1;
        Int2IntMap borderNodesValues = new Int2IntOpenHashMap(nodesToIterate.size());
        borderNodesValues.defaultReturnValue(defaultMissingValue);
        Int2ObjectMap<Node> borderNodes = new Int2ObjectLinkedOpenHashMap<Node>(nodesToIterate.size());
        for (int i=0; i<nodesToIterate.size(); i++) {
            Node node = nodesToIterate.get(i);
            borderNodesValues.put(node.getId(), 0);
            borderNodes.put(node.getId(), node);
        }
        for (int i=0; i<nodesToIterate.size(); i++) {
            exploreDFS(nodesToIterate.get(i), 0, borderNodesValues, borderNodes, defaultMissingValue, numberOfLayers);
        }
        return borderNodes;
    }

    private static void exploreDFS(Node node, int p, Int2IntMap borderNodesValues, Int2ObjectMap<Node> borderNodes, int defaultMissingValue, int numberOfLayers) {
        if (p > numberOfLayers) {
            return;
        } else {
            int value = borderNodesValues.get(node.getId());
            if (value != defaultMissingValue) {
                if (p <= value) {
                } else {
                    return;
                }
            } else {
            }
            borderNodesValues.put(node.getId(), p);
            borderNodes.put(node.getId(), node);
            int edgeCount = node.getEdgeCount();
            for (int i=0; i<edgeCount; i++) {
                Edge edge = node.getEdgeAt(i);
                exploreDFS(edge.getTarget(), p + 1, borderNodesValues, borderNodes, defaultMissingValue, numberOfLayers);
            }
        }
    }

    private static int computeNumberOfFirstIterations(int tileWidth, int tileHeight) {
        int numberOfFirstIterations = 1;
        int maxMargin = Math.min(tileWidth, tileHeight) / 2;
        int currMargin = (int) (Math.pow(2, numberOfFirstIterations + 1) - 2);
        while (currMargin < maxMargin) {
            numberOfFirstIterations++;
            currMargin = (int) (Math.pow(2, numberOfFirstIterations + 1) - 2);
        }
        numberOfFirstIterations--;
        return numberOfFirstIterations;
    }
}
