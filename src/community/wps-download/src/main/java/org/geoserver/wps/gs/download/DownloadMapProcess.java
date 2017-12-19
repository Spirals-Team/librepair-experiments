/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wps.gs.download;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.GroundOverlay;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LatLonBox;
import de.micromata.opengis.kml.v_2_2_0.ViewRefreshMode;
import org.geoserver.config.GeoServer;
import org.geoserver.kml.KMLEncoder;
import org.geoserver.kml.KmlEncodingContext;
import org.geoserver.ows.util.CaseInsensitiveMap;
import org.geoserver.ows.util.KvpUtils;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.platform.Operation;
import org.geoserver.platform.Service;
import org.geoserver.wms.GetMap;
import org.geoserver.wms.GetMapRequest;
import org.geoserver.wms.WMS;
import org.geoserver.wms.WMSMapContent;
import org.geoserver.wms.map.AbstractMapOutputFormat;
import org.geoserver.wms.map.GetMapKvpRequestReader;
import org.geoserver.wms.map.PNGMapResponse;
import org.geoserver.wms.map.RenderedImageMap;
import org.geoserver.wms.map.RenderedImageMapOutputFormat;
import org.geoserver.wms.map.RenderedImageMapResponse;
import org.geoserver.wps.WPSException;
import org.geoserver.wps.gs.GeoServerProcess;
import org.geoserver.wps.process.ByteArrayRawData;
import org.geoserver.wps.process.RawData;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.MapContent;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.util.DefaultProgressListener;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.util.ProgressListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@DescribeProcess(title = "Map Download Process", description = "Builds a large map given a set of layer definitions, " +
        "area of interest, size and eventual target time.")
public class DownloadMapProcess implements GeoServerProcess, ApplicationContextAware {


    private final WMS wms;
    private final GetMapKvpRequestReader getMapReader;
    private Service service;

    public DownloadMapProcess(GeoServer geoServer) {
        // TODO: make these configurable
        this.wms = new WMS(geoServer) {
            @Override
            public int getMaxRenderingTime() {
                return -1;
            }

            @Override
            public int getMaxRenderingErrors() {
                return -1;
            }

            @Override
            public Long getMaxRenderingSize() {
                return null; // 
            }
        };
        this.getMapReader = new GetMapKvpRequestReader(wms);
    }

    /**
     * This process returns a potentially large map
     */
    @DescribeResult(description = "The output map", meta = {"mimeTypes=image/png,image/png8," +
            "image/gif,image/jpeg,image/geotiff,image/geotiff8,image/vnd.jpeg-png,application/vnd.google-earth.kmz",
            "chosenMimeType=format"})
    public RawData execute(
            @DescribeParameter(name = "bbox", min = 1, description = "The map area and output projection")
                    ReferencedEnvelope bbox,
            @DescribeParameter(name = "decoration", min = 0, description = "A WMS decoration layout name to watermark" +
                    " the output") String decorationName,
            @DescribeParameter(name = "time", min = 0, description = "Map time specification (a single time value or " +
                    "a range like in WMS time parameter)") String time,
            @DescribeParameter(name = "width", min = 1, description = "Map width", minValue = 1) int width,
            @DescribeParameter(name = "height", min = 1, description = "Map height", minValue = 1) int height,
            @DescribeParameter(name = "layer", min = 1, description = "List of layers", minValue = 1) Layer[] layers,
            @DescribeParameter(name = "format", min = 0, defaultValue = "image/png") final String format,
            ProgressListener progressListener) throws Exception {
        // if kmlOutput, reproject request to WGS84 (test is done indirectly to make the code work should KML not be 
        // available)
        AbstractMapOutputFormat kmlOutputFormat = (AbstractMapOutputFormat) GeoServerExtensions.bean("KMZMapProducer");
        boolean kmlOutput = kmlOutputFormat.getOutputFormatNames().contains(format);
        if (kmlOutput) {
            bbox = bbox.transform(DefaultGeographicCRS.WGS84, true);
        }

        // avoid NPE on progress listener
        if (progressListener == null) {
            progressListener = new DefaultProgressListener();
        }

        // assemble image
        RenderedImage result = buildImage(bbox, decorationName, time, width, height, layers, format, progressListener);

        // encode output (by faking a normal request)
        GetMapRequest request = new GetMapRequest();
        request.setRawKvp(Collections.emptyMap());
        request.setFormat(format);
        WMSMapContent mapContent = new WMSMapContent(request);
        try {
            mapContent.getViewport().setBounds(bbox);
            Operation operation = new Operation("GetMap", service, null, new Object[]{request});
            if (kmlOutput) {
                return buildKMLResponse(bbox, result, mapContent, operation);
            } else {
                RawData response = buildImageResponse(format, result, mapContent, operation);
                if (response != null) {
                    return response;
                }
            }
        } finally {
            mapContent.dispose();
        }

        // we got here, no supported format found
        throw new WPSException("Could not find a image map encoder for format: " + format);
    }

    private RawData buildImageResponse(String format, RenderedImage result, WMSMapContent mapContent,
                                       Operation operation) throws IOException {
        List<RenderedImageMapResponse> encoders = GeoServerExtensions.extensions(RenderedImageMapResponse.class);
        for (RenderedImageMapResponse encoder : encoders) {
            if (encoder.canHandle(operation)) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                encoder.formatImageOutputStream(result, bos, mapContent);
                return new ByteArrayRawData(bos.toByteArray(), format);
            }
        }
        return null;
    }

    private RawData buildKMLResponse(ReferencedEnvelope bbox, RenderedImage result, WMSMapContent mapContent,
                                     Operation operation) throws IOException {
        // custom KMZ building
        Kml kml = new Kml();
        Document document = kml.createAndSetDocument();
        Folder folder = document.createAndAddFolder();
        GroundOverlay go = folder.createAndAddGroundOverlay();
        go.setName("Map");
        Icon icon = go.createAndSetIcon();
        icon.setHref("image.png");
        icon.setViewRefreshMode(ViewRefreshMode.NEVER);
        icon.setViewBoundScale(0.75);

        LatLonBox gobox = go.createAndSetLatLonBox();
        gobox.setEast(bbox.getMinX());
        gobox.setWest(bbox.getMaxX());
        gobox.setNorth(bbox.getMaxY());
        gobox.setSouth(bbox.getMinY());

        // create the outupt zip
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(bos)) {
            ZipEntry entry = new ZipEntry("wms.kml");
            zip.putNextEntry(entry);
            KMLEncoder kmlEncoder = GeoServerExtensions.bean(KMLEncoder.class);
            kmlEncoder.encode(kml, zip, new KmlEncodingContext(mapContent, wms, true));

            // build and encode the image
            final PNGMapResponse pngEncoder = new PNGMapResponse(wms);
            entry = new ZipEntry("image.png");
            zip.putNextEntry(entry);
            pngEncoder.write(new RenderedImageMap(mapContent, result, "image/png"), zip, operation);
            zip.closeEntry();

            zip.finish();
            zip.flush();
        }

        return new ByteArrayRawData(bos.toByteArray(), org.geoserver.kml.KMZMapOutputFormat.MIME_TYPE);
    }

    RenderedImage buildImage(ReferencedEnvelope bbox, String decorationName, String time, int width, int height,
                             Layer[] layers, String format, ProgressListener progressListener) throws Exception {
        // build GetMap template parameters
        CaseInsensitiveMap template = new CaseInsensitiveMap(new HashMap());
        template.put("service", "WMS");
        template.put("request", "GetMap");
        template.put("transparent", "false");
        template.put("width", String.valueOf(width));
        template.put("height", String.valueOf(height));
        if (time != null) {
            template.put("time", time);
        }
        template.put("bbox", bbox.getMinX() + "," + bbox.getMinY() + "," + bbox.getMaxX() + "," + bbox.getMaxY());
        CoordinateReferenceSystem crs = bbox.getCoordinateReferenceSystem();
        if (crs == null) {
            throw new WPSException("The BBOX parameter must have a coordinate reference system");
        } else {
            // handle possible axis flipping by changing the WMS version accordingly
            Integer code = CRS.lookupEpsgCode(crs, false);
            if (CRS.getAxisOrder(crs) == CRS.AxisOrder.EAST_NORTH) {
                template.put("version", "1.1.0");
                template.put("srs", "EPSG:" + code);
            } else {
                template.put("version", "1.3.0");
                template.put("crs", "EPSG:" + code);
            }
        }

        // loop over layers and accumulate
        RenderedImage result = null;
        progressListener.started();
        int i = 0;
        for (Layer layer : layers) {
            RenderedImage image;
            if (layer.getCapabilities() == null) {
                RenderedImageMap map = renderInternalLayer(layer, template);
                image = map.getImage();
            } else {
                throw new UnsupportedOperationException("Including cascaded layers is not yet implemented");
            }

            if (result == null) {
                result = image;
            } else {
                result = mergeImage(result, image);

            }

            // past the first layer switch transparency on to allow overlaying
            template.put("transparent", "true");
            
            // track progress and bail out if necessary
            progressListener.progress(95f * (++i) / layers.length);
        }

        // Decoration handling, we'll put together a empty GetMap for it
        GetMapRequest request = new GetMapRequest();
        if (time != null) { // allow text decoration timestamping
            request.getEnv().put("time", time);
        }
        request.setFormat(format);
        if (decorationName != null) {
            request.setFormatOptions(Collections.singletonMap("layout", decorationName));
            WMSMapContent content = new WMSMapContent(request);
            try {
                content.setMapWidth(width);
                content.setMapHeight(height);
                content.setTransparent(true);
                RenderedImageMapOutputFormat renderer = new RenderedImageMapOutputFormat(wms);
                RenderedImageMap map = renderer.produceMap(content);

                result = mergeImage(result, map.getImage());
            } finally {
                content.dispose();
            }
        }
        
        progressListener.progress(100);
        
        return result;
    }

    private RenderedImage mergeImage(RenderedImage result, RenderedImage image) {
        // make sure we can paint on it
        if (!(result instanceof BufferedImage)) {
            result = PlanarImage.wrapRenderedImage(result).getAsBufferedImage();
        }

        // could use mosaic here, but would require keeping all images in memory to build the op,
        // this way at most two at any time are around, so uses less memory overall
        BufferedImage bi = (BufferedImage) result;
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        graphics.drawRenderedImage(image, AffineTransform.getScaleInstance(1, 1));
        graphics.dispose();
        return result;
    }

    private RenderedImageMap renderInternalLayer(Layer layer, Map kvpTemplate) throws Exception {
        GetMapRequest request = getMapReader.createRequest();

        // prepare raw and parsed KVP maps to mimick a GetMap request
        CaseInsensitiveMap rawKvp = new CaseInsensitiveMap(new HashMap());
        rawKvp.putAll(kvpTemplate);
        rawKvp.put("format", "image/png"); // fake format, we are building a RenderedImage
        rawKvp.put("layers", layer.getName());
        for (Parameter parameter : layer.getParameters()) {
            rawKvp.putIfAbsent(parameter.key, parameter.value);
        }
        // for merging layers, unless the request stated otherwise
        rawKvp.putIfAbsent("transparent", "true");
        CaseInsensitiveMap kvp = new CaseInsensitiveMap(new HashMap());
        kvp.putAll(rawKvp);
        List<Throwable> exceptions = KvpUtils.parse(kvp);
        if (exceptions != null && exceptions.size() > 0) {
            throw new WPSException("Failed to build map for layer: " + layer.getName(), exceptions.get(0));
        }

        // parse
        getMapReader.read(request, kvp, rawKvp);

        // render
        GetMap mapBuilder = new GetMap(wms);
        return (RenderedImageMap) mapBuilder.run(request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.wms.setApplicationContext(applicationContext);
        List<Service> services = GeoServerExtensions.extensions(Service.class, applicationContext);
        this.service = services.stream().filter(s -> "WMS".equalsIgnoreCase(s.getId())).findFirst().orElse(null);
        if (service == null) {
            throw new RuntimeException("Could not find a WMS service");
        }
    }
}
