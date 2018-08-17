/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.sharedobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/**
 * @since May 10, 2017 2:29:32 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupUUID {

    final private GroupId groupId;
    final private String groupUUID;

    public GroupUUID(GroupId groupId, String groupUUID) {
        this.groupId = groupId;
        this.groupUUID = groupUUID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.groupUUID);
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
        final GroupUUID other = (GroupUUID) obj;
        if (!Objects.equals(this.groupUUID, other.groupUUID)) {
            return false;
        }
        return true;
    }

    @JsonValue
    @Override
    public String toString() {
        return groupUUID;
    }
}
