package org.esa.s2tbx.grm.segmentation.tiles;

import org.esa.s2tbx.grm.segmentation.*;
import org.esa.snap.utils.BufferedInputStreamWrapper;
import org.esa.snap.utils.BufferedOutputStreamWrapper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Jean Coravu
 */
public class FullLambdaScheduleTileSegmenter extends AbstractTileSegmenter {

    public FullLambdaScheduleTileSegmenter(Dimension imageSize, Dimension tileSize, int totalIterationsForSecondSegmentation,
                                           float threshold, boolean fastSegmentation)
                                           throws IOException {

        super(imageSize, tileSize, totalIterationsForSecondSegmentation, threshold, fastSegmentation);
    }

    @Override
    protected FullLambdaScheduleNode buildNode(int nodeId, BoundingBox box, Contour contour, int perimeter, int area, int numberOfComponentsPerPixel) {
        return new FullLambdaScheduleNode(nodeId, box, contour, perimeter, area, numberOfComponentsPerPixel);
    }

    @Override
    public AbstractSegmenter buildSegmenter(float threshold) {
        return new FullLambdaScheduleSegmenter(threshold);
    }

    @Override
    protected void writeNode(BufferedOutputStreamWrapper nodesFileStream, Node nodeToWrite) throws IOException {
        super.writeNode(nodesFileStream, nodeToWrite);

        FullLambdaScheduleNode node = (FullLambdaScheduleNode)nodeToWrite;
        int count = node.getNumberOfComponentsPerPixel();
        for (int i=0; i<count; i++) {
            nodesFileStream.writeFloat(node.getMeansAt(i));
        }
    }

    @Override
    protected Node readNode(BufferedInputStreamWrapper nodesFileStream) throws IOException {
        FullLambdaScheduleNode node = (FullLambdaScheduleNode)super.readNode(nodesFileStream);

        int count = node.getNumberOfComponentsPerPixel();
        for (int i=0; i<count; i++) {
            node.setMeansAt(i, nodesFileStream.readFloat());
        }

        return node;
    }
}
