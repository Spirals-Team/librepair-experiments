package net.contargo.iris.transport.service;

import net.contargo.iris.connection.MainRunConnection;
import net.contargo.iris.connection.service.MainRunConnectionService;
import net.contargo.iris.connection.service.SeaportTerminalConnectionService;
import net.contargo.iris.seaport.Seaport;
import net.contargo.iris.terminal.Terminal;
import net.contargo.iris.terminal.service.TerminalService;
import net.contargo.iris.transport.api.ModeOfTransport;
import net.contargo.iris.transport.api.TransportDescriptionDto;
import net.contargo.iris.transport.api.TransportTemplateDto;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static net.contargo.iris.transport.api.ModeOfTransport.RAIL;
import static net.contargo.iris.transport.api.ModeOfTransport.ROAD;
import static net.contargo.iris.transport.api.ModeOfTransport.WATER;
import static net.contargo.iris.transport.api.SiteType.SEAPORT;
import static net.contargo.iris.transport.api.SiteType.TERMINAL;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


/**
 * @author  Ben Antony - antony@synyx.de
 * @author  Sandra Thieme - thieme@synyx.de
 */
public class DescriptionGenerator {

    private static final List<ModeOfTransport> ALL_MAIN_RUN_MODE_OF_TRANSPORTS = asList(WATER, RAIL);

    private final SeaportTerminalConnectionService seaportTerminalConnectionService;
    private final TerminalService terminalService;
    private final MainRunConnectionService mainRunConnectionService;

    public DescriptionGenerator(SeaportTerminalConnectionService seaportTerminalConnectionService,
        TerminalService terminalService, MainRunConnectionService mainRunConnectionService) {

        this.seaportTerminalConnectionService = seaportTerminalConnectionService;
        this.terminalService = terminalService;
        this.mainRunConnectionService = mainRunConnectionService;
    }

    /**
     * Generates all possible {@link net.contargo.iris.transport.api.TransportDescriptionDto}s for a given
     * {@link net.contargo.iris.transport.api.TransportTemplateDto}, where the terminal is unknown.
     *
     * @param  template  the template
     *
     * @return  a list of {@link net.contargo.iris.transport.api.TransportDescriptionDto}
     */
    public List<TransportDescriptionDto> from(TransportTemplateDto template) {

        List<TransportDescriptionDto> descriptions = new ArrayList<>();

        List<Seaport> seaports = template.transportDescription.stream()
                .filter(this::containsAnySeaport)
                .map(this::getSeaport)
                .collect(toList());
        List<Terminal> terminals = terminalService.getAllActive();

        Map<Terminal, List<MainRunConnection>> terminalListMap = terminals.stream()
                .collect(toMap(Function.identity(), t -> terminalHasConnectionToEveryGivenSeaports(t, seaports)));

        terminalListMap.forEach((terminal, connections) -> {
            Map<BigInteger, List<ModeOfTransport>> seaportToRouteType = connections.stream()
                .collect(groupingBy(mainRunConnection -> mainRunConnection.getSeaport().getUniqueId(),
                        mapping(mainRunConnection -> ModeOfTransport.fromRouteType(mainRunConnection.getRouteType()),
                            toList())));

            template.transportDescription.stream().filter(DescriptionGenerator::isMainRunSegment).forEach(s -> {
                String seaportUuid = getSeaportUuid(s);
                List<ModeOfTransport> modeOfTransports = seaportToRouteType.get(new BigInteger(seaportUuid));
                modeOfTransports.forEach(m -> {
                    TransportDescriptionDto description = generateResult(template, terminal, seaportUuid, m);
                    descriptions.add(description);
                });
            });
        });

        return descriptions;
    }


    private String getSeaportUuid(TransportTemplateDto.TransportSegment s) {

        if (s.fromSite.type == SEAPORT) {
            return s.fromSite.uuid;
        } else {
            return s.toSite.uuid;
        }
    }


    private TransportDescriptionDto generateResult(TransportTemplateDto template, Terminal terminal,
        String seaportUuid, ModeOfTransport mot) {

        TransportDescriptionDto descriptionDto = new TransportDescriptionDto(template);
        descriptionDto.transportDescription.forEach(s -> {
            if (s.fromSite.type == TERMINAL) {
                s.fromSite.uuid = terminal.getUniqueId().toString();
            }

            if (s.toSite.type == TERMINAL) {
                s.toSite.uuid = terminal.getUniqueId().toString();
            }

            if (isMainRunSegment(s)) {
                s.modeOfTransport = mot;
            } else {
                s.modeOfTransport = ROAD;
            }
        });

        return descriptionDto;
    }


    private List<MainRunConnection> terminalHasConnectionToEveryGivenSeaports(Terminal terminal,
        List<Seaport> seaports) {

        return mainRunConnectionService.getConnectionsForTerminal(terminal.getUniqueId())
            .stream()
            .filter(MainRunConnection::getEnabled)
            .filter(this::matchingMot)
            .filter(matchingSeaports(seaports))
            .collect(toList());
    }


    private Predicate<MainRunConnection> matchingSeaports(List<Seaport> seaports) {

        return c -> seaports.stream().anyMatch(s -> s.getUniqueId().equals(c.getSeaport().getUniqueId()));
    }


    private boolean matchingMot(MainRunConnection c) {

        return ALL_MAIN_RUN_MODE_OF_TRANSPORTS.stream().map(ModeOfTransport::getRouteType).anyMatch(routeType ->
                    routeType == c.getRouteType());
    }


    private Seaport getSeaport(TransportTemplateDto.TransportSegment segment) {

        Seaport result = new Seaport();

        if (segment.toSite.type == SEAPORT) {
            result.setUniqueId(new BigInteger(segment.toSite.uuid));

            return result;
        }

        if (segment.fromSite.type == SEAPORT) {
            result.setUniqueId(new BigInteger(segment.fromSite.uuid));

            return result;
        }

        return null;
    }


    private boolean containsAnySeaport(TransportTemplateDto.TransportSegment segment) {

        return segment.fromSite.type == SEAPORT || segment.toSite.type == SEAPORT;
    }


    private static boolean isMainRunSegment(TransportTemplateDto.TransportSegment segment) {

        return (segment.fromSite.type == SEAPORT && segment.toSite.type == TERMINAL)
            || (segment.fromSite.type == TERMINAL && segment.toSite.type == SEAPORT);
    }


    private static boolean isMainRunSegment(TransportDescriptionDto.TransportSegment segment) {

        return (segment.fromSite.type == SEAPORT && segment.toSite.type == TERMINAL)
            || (segment.fromSite.type == TERMINAL && segment.toSite.type == SEAPORT);
    }
}
