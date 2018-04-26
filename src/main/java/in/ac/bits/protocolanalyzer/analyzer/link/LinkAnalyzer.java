/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.analyzer.link;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import in.ac.bits.protocolanalyzer.analyzer.GenericAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.PacketWrapper;
import in.ac.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
public class LinkAnalyzer implements GenericAnalyzer {

    private EventBus linkLayerEventBus;

    public void setEventBus(EventBus eventBus) {
        this.linkLayerEventBus = eventBus;
    }

    private void publishToEventBus(PacketWrapper packetWrapper) {
        /* post the event to corresponding event-bus */
        linkLayerEventBus.post(packetWrapper);
    }

    public void analyzePacket(PacketWrapper packetWrapper) {

        LinkAnalyzerEntity lae = new LinkAnalyzerEntity();
        lae.setPacketId(packetWrapper.getPacketId());
        lae.setTimestamp(packetWrapper.getPacketTimestamp());

        publishToEventBus(packetWrapper);
    }

}
