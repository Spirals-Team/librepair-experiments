package org.matsim.pt.connectionScan.conversion.transitNetworkConversion;

import edu.kit.ifv.mobitopp.publictransport.connectionscan.TransitNetwork;
import edu.kit.ifv.mobitopp.publictransport.model.*;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.pt.connectionScan.plausibilityCheck.LoopFinder;
import org.matsim.pt.router.TransitRouterConfig;
import org.matsim.pt.router.TransitTravelDisutility;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetworkConverter {
    private static final Logger log = Logger.getLogger(NetworkConverter.class);

    //matsim
    private TransitSchedule transitSchedule;

    //transformation
    private MappingHandler idAndMappingHandler;
    //TODO
    private CoordinateTransformation coordinateTransformation;

    //connection-scan
    private List<Stop> stops = new ArrayList<>();
    private Connections connections = new Connections();
    private Time day;
    private TransitRouterConfig config;
    private TransitTravelDisutility costFunction;


    public NetworkConverter(TransitSchedule transitSchedule, TransitRouterConfig config, TransitTravelDisutility costFunction) {

        this.transitSchedule = transitSchedule;
        this.idAndMappingHandler = new MappingHandler();
        this.config = config;
        this.costFunction = costFunction;
        createDay();
    }

    public TransitNetwork convert() {
        log.info("Start converting TransitNetwork");

        StopConverter stopConverter = new StopConverter(transitSchedule.getFacilities(), idAndMappingHandler,
                config.getBeelineWalkConnectionDistance(), costFunction, config);
//        stopConverter.convert();

        ConnectionConverter connectionConverter = new ConnectionConverter(stopConverter,
                transitSchedule.getTransitLines(),
                idAndMappingHandler, getDay());
        connectionConverter.convert();
        this.stops = stopConverter.getConnectionScanStops();
        this.connections = connectionConverter.getConnections();

        //TODO on or off?
//        LoopFinder.hasLoop(this.connections);

        TransitNetwork transitNetwork = TransitNetwork.createOf(stops, connections);
        log.info("Finished converting TransitNetwork");
        return transitNetwork;
    }

    private void createDay() { this.day = new Time(LocalDateTime.of(2017, 3, 14, 0, 0));} //TODO make constant

    public Time getDay() {
        return day;
    }

    public MappingHandler getMappingHandler() {
        return idAndMappingHandler;
    }
}
