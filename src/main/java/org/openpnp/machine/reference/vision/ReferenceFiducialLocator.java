package org.openpnp.machine.reference.vision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.Icon;

import org.apache.commons.io.IOUtils;
import org.opencv.features2d.KeyPoint;
import org.openpnp.gui.MainFrame;
import org.openpnp.gui.support.PropertySheetWizardAdapter;
import org.openpnp.gui.support.Wizard;
import org.openpnp.machine.reference.vision.wizards.ReferenceFiducialLocatorConfigurationWizard;
import org.openpnp.machine.reference.vision.wizards.ReferenceFiducialLocatorPartConfigurationWizard;
import org.openpnp.model.Board;
import org.openpnp.model.BoardLocation;
import org.openpnp.model.Configuration;
import org.openpnp.model.Footprint;
import org.openpnp.model.Length;
import org.openpnp.model.Location;
import org.openpnp.model.Panel;
import org.openpnp.model.Part;
import org.openpnp.model.Placement;
import org.openpnp.model.Placement.Type;
import org.openpnp.spi.Camera;
import org.openpnp.spi.FiducialLocator;
import org.openpnp.spi.PropertySheetHolder;
import org.openpnp.spi.VisionProvider;
import org.openpnp.spi.VisionProvider.TemplateMatch;
import org.openpnp.util.IdentifiableList;
import org.openpnp.util.MovableUtils;
import org.openpnp.util.OpenCvUtils;
import org.openpnp.util.Utils2D;
import org.openpnp.util.VisionUtils;
import org.openpnp.vision.pipeline.CvPipeline;
import org.openpnp.vision.pipeline.CvStage;
import org.openpnp.vision.pipeline.stages.SetResult;
import org.pmw.tinylog.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 * Implements an algorithm for finding a set of fiducials on a board and returning the correct
 * orientation for the board.
 */
@Root
public class ReferenceFiducialLocator implements FiducialLocator {
    @Element(required = false)
    protected CvPipeline pipeline = createDefaultPipeline();

    @ElementMap(required = false)
    protected Map<String, PartSettings> partSettingsByPartId = new HashMap<>();

    public Location locateBoard(BoardLocation boardLocation) throws Exception {
        return locateBoard(boardLocation, false);
    }

    public Location locateBoard(BoardLocation boardLocation, boolean checkPanel) throws Exception {
        IdentifiableList<Placement> fiducials;

        if (checkPanel) {
            Panel panel = MainFrame.get().getJobTab().getJob().getPanels()
                    .get(boardLocation.getPanelId());
            fiducials = panel.getFiducials();
        }
        else {
            fiducials = getFiducials(boardLocation);
        }


        if (fiducials.size() < 2) {
            throw new Exception(String.format(
                    "The board side contains only %d placements marked as fiducials, but at least 2 are required.",
                    fiducials.size()));
        }

        // Find the two that are most distant from each other
        List<Placement> mostDistant = getMostDistantPlacements(fiducials);

        Placement placementA = mostDistant.get(0);
        Placement placementB = mostDistant.get(1);

        Logger.debug("Chose {} and {}", placementA.getId(), placementB.getId());

        // Run the fiducial check on each and get their actual locations
        Location actualLocationA = getFiducialLocation(boardLocation, placementA);
        if (actualLocationA == null) {
            throw new Exception("Unable to locate first fiducial.");
        }
        Location actualLocationB = getFiducialLocation(boardLocation, placementB);
        if (actualLocationB == null) {
            throw new Exception("Unable to locate second fiducial.");
        }

        // Calculate the linear distance between the ideal points and the
        // located points. If they differ by more than a few percent we
        // probably made a mistake.
        double fidDistance =
                Math.abs(placementA.getLocation().getLinearDistanceTo(placementB.getLocation()));
        double visionDistance = Math.abs(actualLocationA.getLinearDistanceTo(actualLocationB));
        if (Math.abs(fidDistance - visionDistance) > fidDistance * 0.01) {
            throw new Exception("Located fiducials are more than 1% away from expected.");
        }

        Location location = Utils2D.calculateBoardLocation(boardLocation, placementA, placementB,
                actualLocationA, actualLocationB);

        location = location.derive(null, null,
                boardLocation.getLocation().convertToUnits(location.getUnits()).getZ(), null);

        return location;
    }

    public static Location getFiducialLocation(Footprint footprint, Camera camera)
            throws Exception {
        // Create the template
        BufferedImage template = createTemplate(camera.getUnitsPerPixel(), footprint);

        // Wait for camera to settle
        Thread.sleep(camera.getSettleTimeMs());
        // Perform vision operation
        return getBestTemplateMatch(camera, template);
    }

    /**
     * Given a placement containing a fiducial, attempt to find the fiducial using the vision
     * system. The function first moves the camera to the ideal location of the fiducial based on
     * the board location. It then performs a template match against a template generated from the
     * fiducial's footprint. These steps are performed thrice to "home in" on the fiducial. Finally,
     * the location is returned. If the fiducial was not able to be located with any degree of
     * certainty the function returns null.
     *
     * @param location, part
     * @return
     * @throws Exception
     */
    public Location getHomeFiducialLocation(Location location, Part part) throws Exception {
        Camera camera = Configuration.get().getMachine().getDefaultHead().getDefaultCamera();

        org.openpnp.model.Package pkg = part.getPackage();
        if (pkg == null) {
            throw new Exception(
                    String.format("Part %s does not have a valid package assigned.", part.getId()));
        }

        Footprint footprint = pkg.getFootprint();
        if (footprint == null) {
            throw new Exception(String.format(
                    "Package %s does not have a valid footprint. See https://github.com/openpnp/openpnp/wiki/Fiducials.",
                    pkg.getId()));
        }

        if (footprint.getShape() == null) {
            throw new Exception(String.format(
                    "Package %s has an invalid or empty footprint.  See https://github.com/openpnp/openpnp/wiki/Fiducials.",
                    pkg.getId()));
        }

        // Create the template
        BufferedImage template =
                createTemplate(camera.getUnitsPerPixel(), part.getPackage().getFootprint());


        // Move to where we expect to find the fid, if user has not specified then we treat 0,0,0,0
        // as the place for this to be
        if (location != null) {
            MovableUtils.moveToLocationAtSafeZ(camera, location);
        }

        for (int i = 0; i < 3; i++) {
            // Wait for camera to settle
            Thread.sleep(camera.getSettleTimeMs());
            // Perform vision operation
            location = getBestTemplateMatch(camera, template);
            if (location == null) {
                Logger.debug("No matches found!");
                return null;
            }
            Logger.debug("home fid. located at {}", location);
            // Move to where we actually found the fid
            camera.moveTo(location);
        }

        return location;

    }

    /**
     * Given a placement containing a fiducial, attempt to find the fiducial using the vision
     * system. The function first moves the camera to the ideal location of the fiducial based on
     * the board location. It then performs a template match against a template generated from the
     * fiducial's footprint. These steps are performed thrice to "home in" on the fiducial. Finally,
     * the location is returned. If the fiducial was not able to be located with any degree of
     * certainty the function returns null.
     * 
     * @param fid
     * @return
     * @throws Exception
     */
    private Location getFiducialLocation(BoardLocation boardLocation, Placement fid)
            throws Exception {
        Camera camera = Configuration.get().getMachine().getDefaultHead().getDefaultCamera();

        Logger.debug("Locating {}", fid.getId());

        Part part = fid.getPart();
        if (part == null) {
            throw new Exception(
                    String.format("Fiducial %s does not have a valid part assigned.", fid.getId()));
        }

        org.openpnp.model.Package pkg = part.getPackage();
        if (pkg == null) {
            throw new Exception(
                    String.format("Part %s does not have a valid package assigned.", part.getId()));
        }

        Footprint footprint = pkg.getFootprint();
        if (footprint == null) {
            throw new Exception(String.format(
                    "Package %s does not have a valid footprint. See https://github.com/openpnp/openpnp/wiki/Fiducials.",
                    pkg.getId()));
        }

        if (footprint.getShape() == null) {
            throw new Exception(String.format(
                    "Package %s has an invalid or empty footprint.  See https://github.com/openpnp/openpnp/wiki/Fiducials.",
                    pkg.getId()));
        }

        // Create the template
        BufferedImage template = createTemplate(camera, part);

        // Move to where we expect to find the fid
        Location location =
                Utils2D.calculateBoardPlacementLocation(boardLocation, fid.getLocation());
        Logger.debug("Looking for {} at {}", fid.getId(), location);
        MovableUtils.moveToLocationAtSafeZ(camera, location);

        PartSettings partSettings = getPartSettings(part);
        CvPipeline pipeline = partSettings.getPipeline();
        
        pipeline.setCamera(camera);
        if (pipeline.getStage("template") instanceof SetResult) {
            SetResult setResult = (SetResult) pipeline.getStage("template");
            setResult.setImage(OpenCvUtils.toMat(template));
        }
        for (int i = 0; i < 3; i++) {
            // Perform vision operation
            pipeline.process();
            
            // Process the results
            location = getBestResult(camera, (List<KeyPoint>) pipeline.getResult("results").model);
            
            // TODO STOPSHIP feel like it would be better to do the conversions explicitly, i.e.
            // I need a List<KeyPoint> so call a function that will convert whatever to that,
            // including a singleton, rather than having the user trying to do conversions.
            // And sorting remains a concern, cause what if they want a different hueristic?
            
            
            if (location == null) {
                Logger.debug("No matches found!");
                return null;
            }
            Logger.debug("{} located at {}", fid.getId(), location);
            // Move to where we actually found the fid
            camera.moveTo(location);
        }

        return location;
    }
    
    private static Location getBestResult(Camera camera, List<KeyPoint> keyPoints) {
        if (keyPoints == null || keyPoints.isEmpty()) {
            return null;
        }
        
        // getTemplateMatches returns results in order of score, but we're
        // more interested in the result closest to the expected location
//        Collections.sort(keyPoints, new Comparator<KeyPoint>() {
//            @Override
//            public int compare(KeyPoint o1, KeyPoint o2) {
//                double d1 = o1.location.getLinearDistanceTo(camera.getLocation());
//                double d2 = o2.location.getLinearDistanceTo(camera.getLocation());
//                return Double.compare(d1, d2);
//            }
//        });
//
//        return matches.get(0).location;

        return null;
    }

    private static Location getBestTemplateMatch(final Camera camera, BufferedImage template)
            throws Exception {
        VisionProvider visionProvider = camera.getVisionProvider();

        List<TemplateMatch> matches = visionProvider.getTemplateMatches(template);

        if (matches.isEmpty()) {
            return null;
        }

        // getTemplateMatches returns results in order of score, but we're
        // more interested in the result closest to the expected location
        Collections.sort(matches, new Comparator<TemplateMatch>() {
            @Override
            public int compare(TemplateMatch o1, TemplateMatch o2) {
                double d1 = o1.location.getLinearDistanceTo(camera.getLocation());
                double d2 = o2.location.getLinearDistanceTo(camera.getLocation());
                return Double.compare(d1, d2);
            }
        });

        return matches.get(0).location;
    }
    
    public static BufferedImage createTemplate(Camera camera, Part part) throws Exception {
        return createTemplate(camera.getUnitsPerPixel(), part.getPackage().getFootprint());
    }

    /**
     * Create a template image based on a Placement's footprint. The image will be scaled to match
     * the dimensions of the current camera.
     * 
     * @param unitsPerPixel, footprint
     * @return
     */
    private static BufferedImage createTemplate(Location unitsPerPixel, Footprint footprint)
            throws Exception {
        Shape shape = footprint.getShape();

        if (shape == null) {
            throw new Exception(
                    "Invalid footprint found, unable to create template for fiducial match. See https://github.com/openpnp/openpnp/wiki/Fiducials.");
        }

        // Determine the scaling factor to go from Outline units to
        // Camera units.
        Length l = new Length(1, footprint.getUnits());
        l = l.convertToUnits(unitsPerPixel.getUnits());
        double unitScale = l.getValue();

        // Create a transform to scale the Shape by
        AffineTransform tx = new AffineTransform();

        // First we scale by units to convert the units and then we scale
        // by the camera X and Y units per pixels to get pixel locations.
        tx.scale(unitScale, unitScale);
        tx.scale(1.0 / unitsPerPixel.getX(), 1.0 / unitsPerPixel.getY());

        // Transform the Shape and draw it out.
        shape = tx.createTransformedShape(shape);

        Rectangle2D bounds = shape.getBounds2D();

        if (bounds.getWidth() == 0 || bounds.getHeight() == 0) {
            throw new Exception(
                    "Invalid footprint found, unable to create template for fiducial match. Width and height of pads must be greater than 0. See https://github.com/openpnp/openpnp/wiki/Fiducials.");
        }

        // Make the image 50% bigger than the shape. This gives better
        // recognition performance because it allows some border around the edges.
        double width = bounds.getWidth() * 1.5;
        double height = bounds.getHeight() * 1.5;
        BufferedImage template =
                new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) template.getGraphics();

        g2d.setStroke(new BasicStroke(1f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        // center the drawing
        g2d.translate(width / 2, height / 2);
        g2d.fill(shape);

        g2d.dispose();

        return template;
    }

    /**
     * Given a List of Placements, find the two that are the most distant from each other.
     * 
     * @param fiducials
     * @return
     */
    private static List<Placement> getMostDistantPlacements(List<Placement> fiducials) {
        if (fiducials.size() < 2) {
            return null;
        }
        Placement maxA = null, maxB = null;
        double max = 0;
        for (Placement a : fiducials) {
            for (Placement b : fiducials) {
                if (a == b) {
                    continue;
                }
                double d = Math.abs(a.getLocation().getLinearDistanceTo(b.getLocation()));
                if (d > max) {
                    maxA = a;
                    maxB = b;
                    max = d;
                }
            }
        }
        ArrayList<Placement> results = new ArrayList<>();
        results.add(maxA);
        results.add(maxB);
        return results;
    }

    private static IdentifiableList<Placement> getFiducials(BoardLocation boardLocation) {
        Board board = boardLocation.getBoard();
        IdentifiableList<Placement> fiducials = new IdentifiableList<>();
        for (Placement placement : board.getPlacements()) {
            if (placement.getType() == Type.Fiducial
                    && placement.getSide() == boardLocation.getSide()) {
                fiducials.add(placement);
            }
        }
        return fiducials;
    }
    
    public CvPipeline getPipeline() {
        if (pipeline.getStage("template") == null) {
            SetResult setResult = new SetResult(null, null);
            setResult.setName("template");
            pipeline.insert(setResult, 0);
        }
        return pipeline;
    }

    public void setPipeline(CvPipeline pipeline) {
        this.pipeline = pipeline;
    }
    
    public static CvPipeline createDefaultPipeline() {
        try {
            String xml = IOUtils.toString(ReferenceBottomVision.class
                    .getResource("ReferenceFiducialLocator-DefaultPipeline.xml"));
            return new CvPipeline(xml);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public String getPropertySheetHolderTitle() {
        return "Fiducal Locator";
    }

    @Override
    public PropertySheetHolder[] getChildPropertySheetHolders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PropertySheet[] getPropertySheets() {
        return new PropertySheet[] {
                new PropertySheetWizardAdapter(new ReferenceFiducialLocatorConfigurationWizard(this))};
    }

    @Override
    public Action[] getPropertySheetHolderActions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Icon getPropertySheetHolderIcon() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public PartSettings getPartSettings(Part part) {
        PartSettings partSettings = this.partSettingsByPartId.get(part.getId());
        if (partSettings == null) {
            partSettings = new PartSettings(this);
            this.partSettingsByPartId.put(part.getId(), partSettings);
        }
        return partSettings;
    }

    public Map<String, PartSettings> getPartSettingsByPartId() {
        return partSettingsByPartId;
    }

    @Override
    public Wizard getPartConfigurationWizard(Part part) {
        PartSettings partSettings = getPartSettings(part);
        try {
            partSettings.getPipeline().setCamera(VisionUtils.getBottomVisionCamera());
        }
        catch (Exception e) {
        }
        return new ReferenceFiducialLocatorPartConfigurationWizard(this, part);
    }

    @Root
    public static class PartSettings {
        @Attribute
        protected boolean enabled;

        @Element
        protected CvPipeline pipeline;

        public PartSettings() {

        }

        public PartSettings(ReferenceFiducialLocator fiducialLocator) {
            try {
                setPipeline(fiducialLocator.getPipeline().clone());
            }
            catch (Exception e) {
                throw new Error(e);
            }
        }

        public CvPipeline getPipeline() {
            if (pipeline.getStage("template") == null) {
                SetResult setResult = new SetResult(null, null);
                setResult.setName("template");
                pipeline.insert(setResult, 0);
            }
            return pipeline;
        }

        public void setPipeline(CvPipeline pipeline) {
            this.pipeline = pipeline;
        }
    }
}
