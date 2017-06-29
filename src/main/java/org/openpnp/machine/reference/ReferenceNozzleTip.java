package org.openpnp.machine.reference;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.opencv.core.RotatedRect;
import org.opencv.features2d.KeyPoint;
import org.openpnp.ConfigurationListener;
import org.openpnp.gui.MainFrame;
import org.openpnp.gui.support.Icons;
import org.openpnp.gui.support.PropertySheetWizardAdapter;
import org.openpnp.gui.support.Wizard;
import org.openpnp.machine.reference.wizards.ReferenceNozzleTipConfigurationWizard;
import org.openpnp.model.Configuration;
import org.openpnp.model.LengthUnit;
import org.openpnp.model.Location;
import org.openpnp.model.Part;
import org.openpnp.spi.Camera;
import org.openpnp.spi.Head;
import org.openpnp.spi.Nozzle;
import org.openpnp.spi.NozzleTip;
import org.openpnp.spi.PropertySheetHolder;
import org.openpnp.spi.base.AbstractNozzleTip;
import org.openpnp.util.MovableUtils;
import org.openpnp.util.OpenCvUtils;
import org.openpnp.util.UiUtils;
import org.openpnp.util.VisionUtils;
import org.openpnp.vision.pipeline.CvPipeline;
import org.openpnp.vision.pipeline.CvStage.Result;
import org.pmw.tinylog.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

public class ReferenceNozzleTip extends AbstractNozzleTip {


    @ElementList(required = false, entry = "id")
    private Set<String> compatiblePackageIds = new HashSet<>();

    @Attribute(required = false)
    private boolean allowIncompatiblePackages;

    @Element(required = false)
    private Location changerStartLocation = new Location(LengthUnit.Millimeters);

    @Element(required = false)
    private double changerStartSpeed = 1.0D;
    
    @Element(required = false)
    private Location changerMidLocation = new Location(LengthUnit.Millimeters);
    
    @Element(required = false)
    private double changerMidSpeed = 1.0D;
    
    @Element(required = false)
    private Location changerMidLocation2;
    
    @Element(required = false)
    private double changerMidSpeed2 = 1.0D;
    
    @Element(required = false)
    private Location changerEndLocation = new Location(LengthUnit.Millimeters);
    
    @Element(required = false)
    private double changerEndSpeed = 1.0D;
    
    @Element(required = false)
    private Calibration calibration = new Calibration();


    @Element(required = false)
    private double vacuumLevelPartOn;

    @Element(required = false)
    private double vacuumLevelPartOff;
    
    private Set<org.openpnp.model.Package> compatiblePackages = new HashSet<>();

    public ReferenceNozzleTip() {
        Configuration.get().addListener(new ConfigurationListener.Adapter() {
            @Override
            public void configurationLoaded(Configuration configuration) throws Exception {
                for (String id : compatiblePackageIds) {
                    org.openpnp.model.Package pkg = configuration.getPackage(id);
                    if (pkg == null) {
                        continue;
                    }
                    compatiblePackages.add(pkg);
                }
                /*
                 * Backwards compatibility. Since this field is being added after the fact, if
                 * the field is not specified in the config then we just make a copy of the
                 * other mid location. The result is that if a user already has a changer
                 * configured they will not suddenly have a move to 0,0,0,0 which would break
                 * everything.
                 */
                if (changerMidLocation2 == null) {
                    changerMidLocation2 = changerMidLocation.derive(null, null, null, null);
                }
            }
        });
    }

    @Override
    public boolean canHandle(Part part) {
        boolean result =
                allowIncompatiblePackages || compatiblePackages.contains(part.getPackage());
        // Logger.debug("{}.canHandle({}) => {}", getName(), part.getId(), result);
        return result;
    }

    public Set<org.openpnp.model.Package> getCompatiblePackages() {
        return new HashSet<>(compatiblePackages);
    }

    public void setCompatiblePackages(Set<org.openpnp.model.Package> compatiblePackages) {
        this.compatiblePackages.clear();
        this.compatiblePackages.addAll(compatiblePackages);
        compatiblePackageIds.clear();
        for (org.openpnp.model.Package pkg : compatiblePackages) {
            compatiblePackageIds.add(pkg.getId());
        }
    }

    @Override
    public String toString() {
        return getName() + " " + getId();
    }

    @Override
    public Wizard getConfigurationWizard() {
        return new ReferenceNozzleTipConfigurationWizard(this);
    }

    @Override
    public String getPropertySheetHolderTitle() {
        return getClass().getSimpleName() + " " + getName();
    }

    @Override
    public PropertySheetHolder[] getChildPropertySheetHolders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Action[] getPropertySheetHolderActions() {
        return new Action[] {unloadAction, loadAction, deleteAction};
    }

    @Override
    public PropertySheet[] getPropertySheets() {
        return new PropertySheet[] {new PropertySheetWizardAdapter(getConfigurationWizard())};
    }

    public boolean isAllowIncompatiblePackages() {
        return allowIncompatiblePackages;
    }

    public void setAllowIncompatiblePackages(boolean allowIncompatiblePackages) {
        this.allowIncompatiblePackages = allowIncompatiblePackages;
    }

    public Location getChangerStartLocation() {
        return changerStartLocation;
    }

    public void setChangerStartLocation(Location changerStartLocation) {
        this.changerStartLocation = changerStartLocation;
    }

    public Location getChangerMidLocation() {
        return changerMidLocation;
    }

    public void setChangerMidLocation(Location changerMidLocation) {
        this.changerMidLocation = changerMidLocation;
    }

    public Location getChangerMidLocation2() {
        return changerMidLocation2;
    }

    public void setChangerMidLocation2(Location changerMidLocation2) {
        this.changerMidLocation2 = changerMidLocation2;
    }

    public Location getChangerEndLocation() {
        return changerEndLocation;
    }

    public void setChangerEndLocation(Location changerEndLocation) {
        this.changerEndLocation = changerEndLocation;
    }
    
    public double getChangerStartSpeed() {
        return changerStartSpeed;
    }

    public void setChangerStartSpeed(double changerStartSpeed) {
        this.changerStartSpeed = changerStartSpeed;
    }

    public double getChangerMidSpeed() {
        return changerMidSpeed;
    }

    public void setChangerMidSpeed(double changerMidSpeed) {
        this.changerMidSpeed = changerMidSpeed;
    }

    public double getChangerMidSpeed2() {
        return changerMidSpeed2;
    }

    public void setChangerMidSpeed2(double changerMidSpeed2) {
        this.changerMidSpeed2 = changerMidSpeed2;
    }

    public double getChangerEndSpeed() {
        return changerEndSpeed;
    }

    public void setChangerEndSpeed(double changerEndSpeed) {
        this.changerEndSpeed = changerEndSpeed;
    }

    private Nozzle getParentNozzle() {
        for (Head head : Configuration.get().getMachine().getHeads()) {
            for (Nozzle nozzle : head.getNozzles()) {
                for (NozzleTip nozzleTip : nozzle.getNozzleTips()) {
                    if (nozzleTip == this) {
                        return nozzle;
                    }
                }
            }
        }
        return null;
    }
	
    public double getVacuumLevelPartOn() {
        return vacuumLevelPartOn;
    }

    public void setVacuumLevelPartOn(double vacuumLevelPartOn) {
        this.vacuumLevelPartOn = vacuumLevelPartOn;
    }

    public double getVacuumLevelPartOff() {
        return vacuumLevelPartOff;
    }

    public void setVacuumLevelPartOff(double vacuumLevelPartOff) {
        this.vacuumLevelPartOff = vacuumLevelPartOff;
    }

    public Calibration getCalibration() {
        return calibration;
    }

    public Action loadAction = new AbstractAction("Load") {
        {
            putValue(SMALL_ICON, Icons.nozzleTipLoad);
            putValue(NAME, "Load");
            putValue(SHORT_DESCRIPTION, "Load the currently selected nozzle tip.");
        }

        @Override
        public void actionPerformed(final ActionEvent arg0) {
            UiUtils.submitUiMachineTask(() -> {
                getParentNozzle().loadNozzleTip(ReferenceNozzleTip.this);
            });
        }
    };

    public Action unloadAction = new AbstractAction("Unload") {
        {
            putValue(SMALL_ICON, Icons.nozzleTipUnload);
            putValue(NAME, "Unload");
            putValue(SHORT_DESCRIPTION, "Unload the currently loaded nozzle tip.");
        }

        @Override
        public void actionPerformed(final ActionEvent arg0) {
            UiUtils.submitUiMachineTask(() -> {
                getParentNozzle().unloadNozzleTip();
            });
        }
    };
    
    public Action deleteAction = new AbstractAction("Delete Nozzle Tip") {
        {
            putValue(SMALL_ICON, Icons.nozzleTipRemove);
            putValue(NAME, "Delete Nozzle Tip");
            putValue(SHORT_DESCRIPTION, "Delete the currently selected nozzle tip.");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int ret = JOptionPane.showConfirmDialog(MainFrame.get(),
                    "Are you sure you want to delete " + getName() + "?",
                    "Delete " + getName() + "?", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                getParentNozzle().removeNozzleTip(ReferenceNozzleTip.this);
            }
        }
    };

    @Root
    public static class Calibration {
        public static class CalibrationOffset {
            final Location offset;
            final double angle;

            public CalibrationOffset(Location offset, double angle) {
                this.offset = offset;
                this.angle = angle;
            }

            @Override
            public String toString() {
                return angle + " " + offset;
            }
        }

        @Element(required = false)
        private CvPipeline pipeline = createDefaultPipeline();

        @Attribute(required = false)
        private double angleIncrement = 15;
        
        @Attribute(required = false)
        private boolean enabled;
        
        private boolean calibrating;

        List<CalibrationOffset> offsets;

        public void calibrate(ReferenceNozzleTip nozzleTip) throws Exception {
            if (!isEnabled()) {
                return;
            }
            try {
                calibrating = true;
                
                reset();

                Nozzle nozzle = nozzleTip.getParentNozzle();
                Camera camera = VisionUtils.getBottomVisionCamera();

                // Move to the camera with an angle of 0.
                Location location = camera.getLocation();
                location = location.derive(null, null, null, 0d);
                MovableUtils.moveToLocationAtSafeZ(nozzle, location);
                for (int i = 0; i < 3; i++) {
                    // Locate the nozzle offsets.
                    Location offset = findCircle();

                    // Subtract the offsets and move to that position to center the nozzle.
                    location = location.subtract(offset);
                    nozzle.moveTo(location);
                }
                // This is our baseline location and should have the nozzle well centered over the
                // camera.
                Location startLocation = location;

                // Now we rotate the nozzle 360 degrees at calibration.angleIncrement steps, find the
                // nozzle using the camera and record the offsets.
                List<CalibrationOffset> offsets = new ArrayList<>();
                for (double i = 0; i < 360; i += angleIncrement) {
                    location = startLocation.derive(null, null, null, i);
                    nozzle.moveTo(location);
                    Location offset = findCircle();
                    offsets.add(new CalibrationOffset(offset, i));
                }

                // The nozzle tip is now calibrated and calibration.getCalibratedOffset() can be
                // used.
                this.offsets = offsets;
                
                nozzle.moveToSafeZ();
            }
            finally {
                calibrating = false;
            }
        }

        public Location getCalibratedOffset(double angle) {
            if (!isEnabled() || !isCalibrated()) {
                return new Location(LengthUnit.Millimeters, 0, 0, 0, 0);
            }

            // Make sure the angle is between 0 and 360.
            while (angle < 0) {
                angle += 360;
            }
            while (angle > 360) {
                angle -= 360;
            }
            List<CalibrationOffset> offsets = getOffsetPairForAngle(angle);
            CalibrationOffset a = offsets.get(0);
            CalibrationOffset b = offsets.get(1);
            Location offsetA = a.offset;
            Location offsetB = b.offset.convertToUnits(a.offset.getUnits());

            double ratio = (angle - a.angle) / (b.angle - a.angle);
            double deltaX = offsetB.getX() - offsetA.getX();
            double deltaY = offsetB.getY() - offsetA.getY();
            double offsetX = offsetA.getX() + (deltaX * ratio);
            double offsetY = offsetA.getY() + (deltaY * ratio);

            return new Location(offsetA.getUnits(), offsetX, offsetY, 0, 0);
        }

        private Location findCircle() throws Exception {
            Camera camera = VisionUtils.getBottomVisionCamera();
            pipeline.setCamera(camera);
            pipeline.process();
            Location location;
            Object result = pipeline.getResult("result").model;
            if (result instanceof List) {
                if (((List) result).get(0) instanceof Result.Circle) {
                    List<Result.Circle> circles = (List<Result.Circle>) result;
                    List<Location> locations = circles.stream().map(circle -> {
                        return VisionUtils.getPixelCenterOffsets(camera, circle.x, circle.y);
                    }).sorted((a, b) -> {
                        double a1 =
                                a.getLinearDistanceTo(new Location(LengthUnit.Millimeters, 0, 0, 0, 0));
                        double b1 =
                                b.getLinearDistanceTo(new Location(LengthUnit.Millimeters, 0, 0, 0, 0));
                        return Double.compare(a1, b1);
                    }).collect(Collectors.toList());
                    location = locations.get(0);
                }
                else if (((List) result).get(0) instanceof KeyPoint) {
                    KeyPoint keyPoint = ((List<KeyPoint>) result).get(0);
                    location = VisionUtils.getPixelCenterOffsets(camera, keyPoint.pt.x, keyPoint.pt.y);
                }
                else {
                    throw new Exception("Unrecognized result " + result);
                }
            }
            else if (result instanceof RotatedRect) {
                RotatedRect rect = (RotatedRect) result;
                location = VisionUtils.getPixelCenterOffsets(camera, rect.center.x, rect.center.y);
            }
            else {
                throw new Exception("Unrecognized result " + result);
            }
            MainFrame.get().get().getCameraViews().getCameraView(camera).showFilteredImage(
                    OpenCvUtils.toBufferedImage(pipeline.getWorkingImage()), 250);
            return location;
        }

        /**
         * Find the two closest offsets to the angle being requested. The offsets start at angle 0
         * and go to angle 360 - angleIncrement in angleIncrement steps.
         */
        private List<CalibrationOffset> getOffsetPairForAngle(double angle) {
            CalibrationOffset a = null, b = null;
            if (angle >= offsets.get(offsets.size() - 1).angle) {
                return Arrays.asList(offsets.get(offsets.size() - 1), offsets.get(0));
            }
            for (int i = 0; i < offsets.size(); i++) {
                if (angle < offsets.get(i + 1).angle) {
                    a = offsets.get(i);
                    b = offsets.get(i + 1);
                    break;
                }
            }
            return Arrays.asList(a, b);
        }

        public static CvPipeline createDefaultPipeline() {
            try {
                String xml = IOUtils.toString(ReferenceNozzleTip.class
                        .getResource("ReferenceNozzleTip-Calibration-DefaultPipeline.xml"));
                return new CvPipeline(xml);
            }
            catch (Exception e) {
                throw new Error(e);
            }
        }

        public void reset() {
            offsets = null;
        }

        public boolean isCalibrated() {
            return offsets != null && !offsets.isEmpty();
        }
        
        public boolean isCalibrating() {
            return calibrating;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public boolean isCalibrationNeeded() {
            return isEnabled() && !isCalibrated() && !isCalibrating();
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public CvPipeline getPipeline() throws Exception {
            pipeline.setCamera(VisionUtils.getBottomVisionCamera());
            return pipeline;
        }

        public void setPipeline(CvPipeline calibrationPipeline) {
            this.pipeline = calibrationPipeline;
        }
    }
}
