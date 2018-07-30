/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experimentdesigner.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @since Jun 11, 2018 4:39:29 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class DataChannel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int channel;
    private String label;
    private boolean logToSdCard;

    public DataChannel() {
    }

    public DataChannel(int channel, String label, boolean logToSdCard) {
        this.channel = channel;
        this.label = label;
        this.logToSdCard = logToSdCard;
    }

    @XmlAttribute
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlAttribute
    public boolean isLogToSdCard() {
        return logToSdCard;
    }

    public void setLogToSdCard(boolean logToSdCard) {
        this.logToSdCard = logToSdCard;
    }
}
