/*
 * Copyright (C) 2015 Pivotal Software, Inc.
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

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @since Sep 7, 2015 3:40:28 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String postName;
    private String registrationField;
    private String controlledRegex;
    private String controlledMessage;
    private boolean preventServerDuplicates;
    private String duplicatesControlledMessage;

    public Metadata() {
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Metadata(String postName, String registrationField, String controlledRegex, String controlledMessage, boolean preventServerDuplicates, String duplicatesControlledMessage) {
        this.postName = postName;
        this.registrationField = registrationField;
        this.controlledRegex = controlledRegex;
        this.controlledMessage = controlledMessage;
        this.preventServerDuplicates = preventServerDuplicates;
        this.duplicatesControlledMessage = duplicatesControlledMessage;
    }

    @XmlAttribute
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @XmlAttribute
    public String getRegistrationField() {
        return registrationField;
    }

    public void setRegistrationField(String registrationField) {
        this.registrationField = registrationField;
    }

    @XmlAttribute
    public String getControlledRegex() {
        return controlledRegex;
    }

    public void setControlledRegex(String controlledRegex) {
        this.controlledRegex = controlledRegex;
    }

    @XmlAttribute
    public String getControlledMessage() {
        return controlledMessage;
    }

    public void setControlledMessage(String controlledMessage) {
        this.controlledMessage = controlledMessage;
    }

    @XmlAttribute
    public boolean isPreventServerDuplicates() {
        return preventServerDuplicates;
    }

    public void setPreventServerDuplicates(boolean preventServerDuplicates) {
        this.preventServerDuplicates = preventServerDuplicates;
    }

    @XmlAttribute
    public String getDuplicatesControlledMessage() {
        return duplicatesControlledMessage;
    }

    public void setDuplicatesControlledMessage(String duplicatesControlledMessage) {
        this.duplicatesControlledMessage = duplicatesControlledMessage;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.postName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Metadata other = (Metadata) obj;
        if (!Objects.equals(this.postName, other.postName)) {
            return false;
        }
        return true;
    }
}
