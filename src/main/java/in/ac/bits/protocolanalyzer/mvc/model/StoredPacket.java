/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.mvc.model;

import in.ac.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;

/**
 *
 * @author amit
 */
public class StoredPacket {

    private long packetId;
    private LinkAnalyzerEntity linkAnalyzerEntity;
    private NetworkAnalyzerEntity networkAnalyzerEntity;
    private TransportAnalyzerEntity transportAnalyzerEntity;

    public long getPacketId() {
        return packetId;
    }

    public void setPacketId(long packetId) {
        this.packetId = packetId;
    }

    public LinkAnalyzerEntity getLinkAnalyzerEntity() {
        return linkAnalyzerEntity;
    }

    public void setLinkAnalyzerEntity(LinkAnalyzerEntity linkAnalyzerEntity) {
        this.linkAnalyzerEntity = linkAnalyzerEntity;
    }

    public NetworkAnalyzerEntity getNetworkAnalyzerEntity() {
        return networkAnalyzerEntity;
    }

    public void setNetworkAnalyzerEntity(
            NetworkAnalyzerEntity networkAnalyzerEntity) {
        this.networkAnalyzerEntity = networkAnalyzerEntity;
    }

    public TransportAnalyzerEntity getTransportAnalyzerEntity() {
        return transportAnalyzerEntity;
    }

    public void setTransportAnalyzerEntity(
            TransportAnalyzerEntity transportAnalyzerEntity) {
        this.transportAnalyzerEntity = transportAnalyzerEntity;
    }

}
