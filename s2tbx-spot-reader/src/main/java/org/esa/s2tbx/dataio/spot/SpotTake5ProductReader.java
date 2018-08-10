/*
 * Copyright (C) 2014-2015 CS-SI (foss-contact@thor.si.c-s.fr)
 * Copyright (C) 2014-2015 CS-Romania (office@c-s.ro)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */

package org.esa.s2tbx.dataio.spot;

import com.bc.ceres.core.ProgressMonitor;
import org.esa.s2tbx.dataio.ColorPaletteBand;
import org.esa.s2tbx.dataio.VirtualDirEx;
import org.esa.s2tbx.dataio.metadata.XmlMetadata;
import org.esa.s2tbx.dataio.metadata.XmlMetadataParser;
import org.esa.s2tbx.dataio.metadata.XmlMetadataParserFactory;
import org.esa.s2tbx.dataio.readers.BaseProductReaderPlugIn;
import org.esa.s2tbx.dataio.spot.dimap.SpotConstants;
import org.esa.s2tbx.dataio.spot.dimap.SpotTake5Metadata;
import org.esa.snap.core.dataio.AbstractProductReader;
import org.esa.snap.core.dataio.ProductReaderPlugIn;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.FlagCoding;
import org.esa.snap.core.datamodel.Mask;
import org.esa.snap.core.datamodel.MetadataAttribute;
import org.esa.snap.core.datamodel.MetadataElement;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.datamodel.ProductData;
import org.esa.snap.core.util.TreeNode;
import org.esa.snap.dataio.geotiff.GeoTiffProductReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This product reader is used for products of SPOT4 TAKE5 experiment, products distributed by Theia.
 * The input format of the product is an archive with the ".tgz" extension or an xml file corresponding to the metadata.
 * If the input is an archive, the archive is expected to have a subfolder, containing the xml metadata file, that should have the same name as this subfolder.
 * If the input as a xml metadata file, the rest of the files (images and masks) are expected to be found in the location defined by the metadata values,
 * depending on the location of the metadata file.
 * In both cases, the input file (the archive or the metadata file) shoud start with "SPOT4_HRVIR1_XS_" (HVIR1 is the sensor of SPOT4 satellites).
 *
 * @author Ramona Manda
 */
public class SpotTake5ProductReader extends AbstractProductReader {
    private static final Logger logger = Logger.getLogger(SpotTake5ProductReader.class.getName());

    private SpotTake5Metadata imageMetadata;
    private VirtualDirEx input;
    private final Map<Band, GeoTiffProductReader> readerMap;
    private final Map<Band, Band> bandMap;
    private final Path colorPaletteFilePath;

    static {
        XmlMetadataParserFactory.registerParser(SpotTake5Metadata.class, new XmlMetadataParser<SpotTake5Metadata>(SpotTake5Metadata.class));
    }

    protected SpotTake5ProductReader(ProductReaderPlugIn readerPlugIn, Path colorPaletteFilePath) {
        super(readerPlugIn);

        this.colorPaletteFilePath = colorPaletteFilePath;
        this.readerMap = new HashMap<Band, GeoTiffProductReader>();
        this.bandMap = new HashMap<Band, Band>();
    }

    @Override
    public TreeNode<File> getProductComponents() {
        TreeNode<File> result = super.getProductComponents();
        if (input.isCompressed()) {
            return result;
        } else {
            for (String inputFile : imageMetadata.getTiffFiles().values()) {
                try {
                    TreeNode<File> productFile = new TreeNode<File>(inputFile);
                    productFile.setContent(input.getFile(inputFile));
                    result.addChild(productFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (String inputFile : imageMetadata.getMaskFiles().values()) {
                try {
                    TreeNode<File> productFile = new TreeNode<File>(inputFile);
                    productFile.setContent(input.getFile(inputFile));
                    result.addChild(productFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }

    @Override
    protected Product readProductNodesImpl() throws IOException {
        input = ((BaseProductReaderPlugIn)getReaderPlugIn()).getInput(getInput());
        File imageMetadataFile = null;
        String metaSubFolder = "";
        if (VirtualDirEx.isPackedFile(new File(input.getBasePath()))) {
            //if the input is an archive, check the metadata file as being the name of the archive, followed by ".xml", right under the unpacked archive folder
            String path = input.getBasePath();
            String metaFile = path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
            try {
                imageMetadataFile = input.getFile(metaFile + SpotConstants.SPOT4_TAKE5_METADATA_FILE_EXTENSION);
            } catch (Exception ex) {
                //if the metadata is not found as described above, the subfolders as checked, and for each subfolder, an xml file with the same name as the subfolder is searched
                //((TarVirtualDir) input).ensureUnpacked();
                for (String entrySubFolder : input.getTempDir().list()) {
                    if (new File(input.getTempDir(), entrySubFolder).isDirectory()) {
                        try {
                            imageMetadataFile = input.getFile(entrySubFolder + File.separator + entrySubFolder + SpotConstants.SPOT4_TAKE5_METADATA_FILE_EXTENSION);
                            //if the metadata is found under a subfolder of the archive, this subfolder must be used in order to compute the path for the rest of the files found in the metadata file
                            metaSubFolder = entrySubFolder + File.separator;
                            break;
                        } catch (Exception ex2) {
                            logger.warning(ex2.getMessage());
                        }
                    }
                }
            }
        } else {
            imageMetadataFile = new File(getInput().toString());
            if (!imageMetadataFile.isFile()) {
                imageMetadataFile = new File(input.getBasePath(), input.findFirst(SpotConstants.SPOT4_TAKE5_METADATA_FILE_EXTENSION));
            }
        }
        if (imageMetadataFile != null) {
            imageMetadata = XmlMetadata.create(SpotTake5Metadata.class, imageMetadataFile);
        }
        Product product = null;
        if (imageMetadata != null) {
            String productLevel = imageMetadata.getMetadataProfile();
            // for N2, the masks may not be present in metadata, but in a folder named MASK
            String masksFolderName = imageMetadata.getMasksFolder();
            if (productLevel.startsWith("N2") && masksFolderName != null) {
                File masksFolder = new File(imageMetadataFile.getParent(), masksFolderName);
                String[] files = masksFolder.list();
                Map<String, String> maskFiles = imageMetadata.getMaskFiles();
                if (files != null) {
                    for (String file : files) {
                        String path = masksFolderName + File.separator + file;
                        if (file.contains("SAT")) {
                            maskFiles.put(SpotConstants.SPOT4_TAKE5_TAG_SATURATION, path);
                        } else if (file.contains("NUA")) {
                            maskFiles.put(SpotConstants.SPOT4_TAKE5_TAG_CLOUDS, path);
                        } else if (file.contains("DIV")) {
                            maskFiles.put(SpotConstants.SPOT4_TAKE5_TAG_DIVERSE, path);
                        }
                    }
                }
                Map<String, String> rasterFiles = imageMetadata.getTiffFiles();
                if (!rasterFiles.containsKey(SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_AOT) ||
                        rasterFiles.get(SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_AOT) == null ||
                        rasterFiles.get(SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_AOT).isEmpty()) {
                    String[] rasterNames = imageMetadataFile.getParentFile().list((dir, name) -> name.contains("AOT"));
                    if (rasterNames != null && rasterNames.length > 0) {
                        rasterFiles.put(SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_AOT, rasterNames[0]);
                    }
                }
            }
            product = new Product(imageMetadata.getProductName(),
                                  SpotConstants.SPOT4_TAKE5_FORMAT_NAME[0],
                                  imageMetadata.getRasterWidth(),
                                  imageMetadata.getRasterHeight());
            product.setProductReader(this);
            //product.setFileLocation(imageMetadataFile);
            product.setFileLocation(new File(input.getBasePath()));
            product.getMetadataRoot().addElement(imageMetadata.getRootElement());
            ProductData.UTC startTime = imageMetadata.getDatePdv();
            product.setStartTime(startTime);
            product.setEndTime(startTime);
            product.setDescription(SpotConstants.SPOT4_TAKE5_FORMAT + " level:" + productLevel + " zone:" + imageMetadata.getGeographicZone());

            //all the bands of the tiff files are added to the product
            Map<String, String> tiffFiles = imageMetadata.getTiffFiles();
            List<String> sortedKeys = new ArrayList<>(tiffFiles.keySet());
            Collections.sort(sortedKeys);
            for (int i = sortedKeys.size() - 1; i >= 0; i--) {
                String key = sortedKeys.get(i);
                addBands(product, metaSubFolder + tiffFiles.get(key), key);
            }

            //for each mask found in the metadata, the first band of the mask is added to the product, in order to create the masks
            Map<String, Band> maskBands = new HashMap<String, Band>();
            for (Map.Entry<String, String> entry : imageMetadata.getMaskFiles().entrySet()) {
                Band maskBand = addMaskBand(product, metaSubFolder + entry.getValue(), entry.getKey());
                maskBands.put(entry.getKey(), maskBand);
            }

            //saturated flags&masks:
            if (imageMetadata.getMaskFiles().keySet().contains(SpotConstants.SPOT4_TAKE5_TAG_SATURATION)) {
                FlagCoding saturatedFlagCoding = createSaturatedFlagCoding(product);
                maskBands.get(SpotConstants.SPOT4_TAKE5_TAG_SATURATION).setSampleCoding(saturatedFlagCoding);

                List<Mask> saturatedMasks = createMasksFromFlagCodding(product, saturatedFlagCoding);
                for (Mask mask : saturatedMasks) {
                    product.getMaskGroup().add(mask);
                }
            }

            //clouds flags&masks:
            if (imageMetadata.getMaskFiles().keySet().contains(SpotConstants.SPOT4_TAKE5_TAG_CLOUDS)) {
                FlagCoding cloudsFlagCoding = createCloudsFlagCoding(product);
                maskBands.get(SpotConstants.SPOT4_TAKE5_TAG_CLOUDS).setSampleCoding(cloudsFlagCoding);

                List<Mask> cloudsMasks = createMasksFromFlagCodding(product, cloudsFlagCoding);
                for (Mask mask : cloudsMasks) {
                    product.getMaskGroup().add(mask);
                }
            }

            //diverse flags&masks:
            if (imageMetadata.getMaskFiles().keySet().contains(SpotConstants.SPOT4_TAKE5_TAG_DIVERSE)) {
                FlagCoding divFlagCoding = createDiverseFlagCoding(product);
                maskBands.get(SpotConstants.SPOT4_TAKE5_TAG_DIVERSE).setSampleCoding(divFlagCoding);

                List<Mask> divMasks = createMasksFromFlagCodding(product, divFlagCoding);
                for (Mask mask : divMasks) {
                    product.getMaskGroup().add(mask);
                }
            }

            product.setAutoGrouping(SpotConstants.SPOT4_TAKE5_TAG_GEOTIFF + SpotConstants.BAND_GROUP_SEPARATOR +
                                            SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_AOT + SpotConstants.BAND_GROUP_SEPARATOR +
                                            SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_CORR_ENV + SpotConstants.BAND_GROUP_SEPARATOR +
                                            SpotConstants.SPOT4_TAKE5_TAG_ORTHO_SURF_CORR_PENTE + SpotConstants.BAND_GROUP_SEPARATOR +
                                            SpotConstants.SPOT4_TAKE5_TAG_ORTHO_VAP_EAU + SpotConstants.BAND_GROUP_SEPARATOR +
                                            SpotConstants.SPOT4_TAKE5_GROUP_MASKS);
            product.setModified(false);
        }
        return product;
    }

    /**
     * Creates and adds masks to the product, by using the given flag coding. Each added mask uses the width and the height of the product.
     *
     * @param product    the product on which the masks are added
     * @param flagCoding for each flag of this flagCoding parameter, a new mask is added, with the same name as the flag
     * @return the list of all the masks added to the product
     */
    List<Mask> createMasksFromFlagCodding(Product product, FlagCoding flagCoding) {
        String flagCodingName = flagCoding.getName();
        ArrayList<Mask> masks = new ArrayList<Mask>();
        final int width = product.getSceneRasterWidth();
        final int height = product.getSceneRasterHeight();

        for (String flagName : flagCoding.getFlagNames()) {
            MetadataAttribute flag = flagCoding.getFlag(flagName);
            masks.add(Mask.BandMathsType.create(flagName,
                                                flag.getDescription(),
                                                width, height,
                                                flagCodingName + "." + flagName,
                                                ColorIterator.next(),
                                                0.5));
        }
        return masks;
    }

    @Override
    protected void readBandRasterDataImpl(int sourceOffsetX, int sourceOffsetY,
                                          int sourceWidth, int sourceHeight,
                                          int sourceStepX, int sourceStepY,
                                          Band destBand,
                                          int destOffsetX, int destOffsetY,
                                          int destWidth, int destHeight,
                                          ProductData destBuffer, ProgressMonitor pm) throws IOException {
        GeoTiffProductReader reader = readerMap.get(destBand);
        Band tiffBand = bandMap.get(destBand);
        reader.readBandRasterData(tiffBand, destOffsetX, destOffsetY, destWidth, destHeight, destBuffer, pm);
    }

    /**
     * This method adds the bands of the given tiff image to the product.
     * Also, the GeoCoding of the bands is transferred to the product.
     *
     * @param product        the product to which the bands are added
     * @param tiffFile       the file from which to take the bands
     * @param bandNamePrefix the name to start all the bands names
     */
    private void addBands(Product product, String tiffFile, String bandNamePrefix) {
        logger.info("Read product component: " + tiffFile);
        String[] bandNames = imageMetadata.getBandNames();
        try {
            File rasterFile = input.getFile(tiffFile);
            GeoTiffProductReader tiffReader = new GeoTiffProductReader(getReaderPlugIn());
            Product tiffProduct = tiffReader.readProductNodes(rasterFile, null);
            if (tiffProduct != null) {
                MetadataElement tiffMetadata = tiffProduct.getMetadataRoot();
                if (tiffMetadata != null) {
                    XmlMetadata.CopyChildElements(tiffMetadata, product.getMetadataRoot());
                }
                tiffProduct.transferGeoCodingTo(product, null);

                int numBands = tiffProduct.getNumBands();
                String bandPrefix = bandNamePrefix + "_";
                for (int idx = 0; idx < numBands; idx++) {
                    Band srcBand = tiffProduct.getBandAt(idx);
                    String bandName = (idx < bandNames.length ? bandNames[idx] : SpotConstants.DEFAULT_BAND_NAME_PREFIX + idx);
                    if (product.getBand(bandName) != null) {
                        bandName = bandPrefix + bandName;
                    }
                    ColorPaletteBand targetBand = new ColorPaletteBand(bandName, srcBand.getDataType(), product.getSceneRasterWidth(), product.getSceneRasterHeight(), this.colorPaletteFilePath);
                    product.addBand(targetBand);
                    readerMap.put(targetBand, tiffReader);
                    bandMap.put(targetBand, srcBand);
                    //targetBand.setRasterData(srcBand.getRasterData());
                    //targetBand.setSourceImage(srcBand.getSourceImage());
                    targetBand.setValidPixelExpression(srcBand.getValidPixelExpression());
                    targetBand.setNoDataValue(srcBand.getNoDataValue());
                    targetBand.setNoDataValueUsed(true);
                    targetBand.setUnit(getNotNullValueOrDefault(srcBand.getUnit()));
                    targetBand.setGeophysicalNoDataValue(srcBand.getGeophysicalNoDataValue());
                    targetBand.setSpectralWavelength(srcBand.getSpectralWavelength());
                    targetBand.setSpectralBandwidth(srcBand.getSpectralBandwidth());
                    targetBand.setScalingFactor(srcBand.getScalingFactor());
                    targetBand.setScalingOffset(srcBand.getScalingOffset());
                    targetBand.setSolarFlux(srcBand.getSolarFlux());
                    targetBand.setSampleCoding(srcBand.getSampleCoding());
                    targetBand.setImageInfo(srcBand.getImageInfo());
                    targetBand.setSpectralBandIndex(srcBand.getSpectralBandIndex());
                    targetBand.setDescription(bandName);
                }
            }
        } catch (IOException ioEx) {
            logger.severe("Error while reading component: " + ioEx.getMessage());
        }
    }

    private String getNotNullValueOrDefault(String value) {
        return (value == null ? SpotConstants.VALUE_NOT_AVAILABLE : value);
    }

    /**
     * This method adds the first band of the tiff image as a new band to the product. This band can be used to create some masks.
     * The GeoCoding of the band is not transferred to the product.
     *
     * @param product  the product to which the bands are added
     * @param tiffFile the file from which to take the bands
     * @param bandName the name of the band to be added
     * @return the newly added band
     */
    private Band addMaskBand(Product product, String tiffFile, String bandName) {
        logger.info("Read band for mask: " + tiffFile);
        try {
            File rasterFile = input.getFile(tiffFile);
            GeoTiffProductReader tiffReader = new GeoTiffProductReader(getReaderPlugIn());
            Product tiffProduct = tiffReader.readProductNodes(rasterFile, null);
            if (tiffProduct != null) {
                int numBands = tiffProduct.getNumBands();
                if (numBands > 0) {
                    Band srcBand = tiffProduct.getBandAt(0);
                    Band targetBand = product.addBand(bandName, srcBand.getDataType());
                    readerMap.put(targetBand, tiffReader);
                    bandMap.put(targetBand, srcBand);
                    //targetBand.setRasterData(srcBand.getRasterData());
                    //targetBand.setSourceImage(srcBand.getSourceImage());
                    targetBand.setNoDataValue(srcBand.getNoDataValue());
                    targetBand.setNoDataValueUsed(false);
                    targetBand.setSpectralWavelength(srcBand.getSpectralWavelength());
                    targetBand.setSpectralBandwidth(srcBand.getSpectralBandwidth());
                    targetBand.setScalingFactor(srcBand.getScalingFactor());
                    targetBand.setScalingOffset(srcBand.getScalingOffset());
                    targetBand.setSolarFlux(srcBand.getSolarFlux());
                    targetBand.setUnit(srcBand.getUnit());
                    targetBand.setSampleCoding(srcBand.getSampleCoding());
                    targetBand.setImageInfo(srcBand.getImageInfo());
                    targetBand.setSpectralBandIndex(srcBand.getSpectralBandIndex());
                    targetBand.setDescription(bandName);
                    return targetBand;
                }
            }
        } catch (IOException ioEx) {
            logger.severe("Error while reading band for mask: " + ioEx.getMessage());
        }
        return null;
    }

    /**
     * Creates the flags for the saturation mask; the flags are:
     * <ul><li>XS1 band is saturated</li></ul>
     * <ul><li>XS2 band is saturated</li></ul>
     * <ul><li>XS3 band is saturated</li></ul>
     * <ul><li>SWIR band is saturated</li></ul>
     *
     * @param outputProduct the products on which the flagCoding containing the flags is added
     * @return the flagCoding created with the saturated flags, and added to the product
     */
    FlagCoding createSaturatedFlagCoding(Product outputProduct) {
        String bandName = SpotConstants.SPOT4_TAKE5_TAG_SATURATION;
        FlagCoding flagCoding = new FlagCoding(bandName);
        flagCoding.addFlag("XS1_saturated", 1, "XS1 band is saturated");
        flagCoding.addFlag("XS2_saturated", 2, "XS2 band is saturated");
        flagCoding.addFlag("XS3_saturated", 4, "XS3 band is saturated");
        flagCoding.addFlag("SWIR_saturated", 8, "SWIR band is saturated");
        outputProduct.getFlagCodingGroup().add(flagCoding);
        return flagCoding;
    }

    /**
     * Creates the flags for the clouds mask; the flags are:
     * <ul><li>all clouds (except thin ones) or shadows</li></ul>
     * <ul><li>all clouds (except thin ones)</li></ul>
     * <ul><li>cloud detected through absolute threshold</li></ul>
     * <ul><li>cloud detected through multi-t threshold</li></ul>
     * <ul><li>very thin clouds</li></ul>
     * <ul><li>high clouds detected with 1.38 µm band (LANDSAT 8 only)</li></ul>
     * <ul><li>cloud shadows matched with a cloud</li></ul>
     * <ul><li>cloud shadows in the zone where clouds could be outside the image (less reliable)</li></ul>
     *
     * @param outputProduct the products on which the flagCoding containing the flags is added
     * @return the flagCoding created with the clouds flags, and added to the product
     */
    FlagCoding createCloudsFlagCoding(Product outputProduct) {
        String bandName = SpotConstants.SPOT4_TAKE5_TAG_CLOUDS;
        FlagCoding flagCoding = new FlagCoding(bandName);
        flagCoding.addFlag("clouds_or_shadows", 1, "all clouds (except thin ones) or shadows");
        flagCoding.addFlag("clouds", 2, "all clouds (except thin ones)");
        flagCoding.addFlag("cloud_absolute_threshold", 4, "cloud detected through absolute threshold");
        flagCoding.addFlag("cloud_multi_t_threshold", 8, "cloud detected through multi-t threshold");
        flagCoding.addFlag("thin_clouds", 16, "very thin clouds");
        flagCoding.addFlag("clouds_1.38band", 32, "high clouds detected with 1.38 µm band (LANDSAT 8 only)");
        flagCoding.addFlag("shadows_matched_clouds", 64, "cloud shadows matched with a cloud");
        flagCoding.addFlag("shadows_for_clouds_outside", 128, "cloud shadows in the zone where clouds could be outside the image (less reliable)");
        outputProduct.getFlagCodingGroup().add(flagCoding);
        return flagCoding;
    }

    /**
     * Creates the flags for the diverse masks (snow, water); the flags are:
     * <ul><li>no diverse data</li></ul>
     * <ul><li>water</li></ul>
     * <ul><li>snow</li></ul>
     * <ul><li>Sun too low for terrain correction (limitation of correction factor that tends to the infinity, correction is false)</li></ul>
     * <ul><li>Sun too low for terrain correction (correction might be inaccurate)</li></ul>
     *
     * @param outputProduct the products on which the flagCoding containing the flags is added
     * @return the flagCoding created with the saturated flags, and added to the
     */
    FlagCoding createDiverseFlagCoding(Product outputProduct) {
        String bandName = SpotConstants.SPOT4_TAKE5_TAG_DIVERSE;
        FlagCoding flagCoding = new FlagCoding(bandName);
        flagCoding.addFlag("no_div_data", 1, "no diverse data");
        flagCoding.addFlag("water", 2, "water");
        flagCoding.addFlag("snow", 4, "snow");
        flagCoding.addFlag("false_correction", 8, "Sun too low for terrain correction (limitation of correction factor that tends to the infinity, correction is false)");
        flagCoding.addFlag("inaccurate_correction", 16, "Sun too low for terrain correction (correction might be inaccurate)");
        outputProduct.getFlagCodingGroup().add(flagCoding);
        return flagCoding;
    }

    /**
     * Class used to define a list of colors for the masks to be coloured
     */
    private static class ColorIterator {

        @SuppressWarnings("CanBeFinal")
        static ArrayList<Color> colors;
        static Iterator<Color> colorIterator;

        static {
            colors = new ArrayList<Color>();
            colors.add(Color.red);
            colors.add(Color.red.darker());
            colors.add(Color.blue);
            colors.add(Color.blue.darker());
            colors.add(Color.green);
            colors.add(Color.green.darker());
            colors.add(Color.yellow);
            colors.add(Color.yellow.darker());
            colors.add(Color.magenta);
            colors.add(Color.magenta.darker());
            colors.add(Color.pink);
            colors.add(Color.pink.darker());
            colors.add(Color.cyan);
            colors.add(Color.cyan.darker());
            colors.add(Color.orange);
            colors.add(Color.orange.darker());
            colors.add(Color.blue.darker().darker());
            colors.add(Color.green.darker().darker());
            colors.add(Color.yellow.darker().darker());
            colors.add(Color.magenta.darker().darker());
            colors.add(Color.pink.darker().darker());
            colorIterator = colors.iterator();
        }

        static Color next() {
            if (!colorIterator.hasNext()) {
                colorIterator = colors.iterator();
            }
            return colorIterator.next();
        }
    }

}
