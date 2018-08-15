/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.traveldata;

import java.io.File;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Population;

import ch.ethz.idsc.amodeus.virtualnetwork.VirtualNetwork;
import ch.ethz.idsc.tensor.sca.Chop;

public class TravelDataTestHelper {

    public static TravelDataTestHelper prepare(VirtualNetwork<Link> vNCreated, VirtualNetwork<Link> vNSaved) throws Exception {
        return new TravelDataTestHelper(vNCreated, vNSaved);
    }

    private TravelData tDCreated;
    private TravelData tDSaved;
    private VirtualNetwork<Link> virtualNetworkCreated;

    private TravelDataTestHelper(VirtualNetwork<Link> vNCreated, VirtualNetwork<Link> vNSaved) throws Exception {
        tDCreated = TravelDataGet.readDefault(vNCreated);
        tDSaved = TravelDataIO.read(new File("resources/testComparisonFiles/travelData"), vNSaved);
    }

    public boolean timeIntervalCheck() {
        return (tDSaved.getTimeInterval() == tDCreated.getTimeInterval());
    }

    public boolean timeStepsCheck() {
        return (tDSaved.getTimeSteps() == tDCreated.getTimeSteps());
    }

    public boolean lambdaAbsoluteCheck() {
        return Chop._06.close(tDSaved.getLambdaAbsolute(), tDCreated.getLambdaAbsolute());
    }

    public boolean lambdaAbsoluteAtTimeCheck() {
        return Chop._06.close(tDSaved.getLambdaAbsoluteAtTime(1000), tDCreated.getLambdaAbsoluteAtTime(1000));
    }

    public boolean lambdaInvalidAbsoluteAtTimeCheck() {
        try {
            tDCreated.getLambdaRateAtTime(24 * 3600 + 1);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean lambdaRateCheck() {
        return Chop._06.close(tDSaved.getLambdaRate(), tDCreated.getLambdaRate());
    }

    public boolean lambdaRateAtTimeCheck() {
        return Chop._06.close(tDSaved.getLambdaRateAtTime(1000), tDCreated.getLambdaRateAtTime(1000));
    }

    public boolean lambdaInvalidRateAtTimeCheck() {
        try {
            tDCreated.getLambdaRateAtTime(-1);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean checkInvalidTimeInterval(Population population, Network network) {
        try {
            new TravelData(virtualNetworkCreated, network, population, 24 * 3600 - 1);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean checkValidTimeInterval(Population population, Network network) {
        try {
            new TravelData(virtualNetworkCreated, network, population, 1);
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}
