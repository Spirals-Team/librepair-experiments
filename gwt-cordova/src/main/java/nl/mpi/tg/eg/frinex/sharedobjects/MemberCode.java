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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @since May 10, 2017 2:31:56 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MemberCode implements Comparable<MemberCode> {

    private final String memberCodeString;

    public MemberCode(String memberCodeString) {
        this.memberCodeString = memberCodeString;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.memberCodeString);
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
        final MemberCode other = (MemberCode) obj;
        if (!Objects.equals(this.memberCodeString, other.memberCodeString)) {
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return memberCodeString != null && !memberCodeString.isEmpty() && memberCodeString.length() == 1;
    }

    static public List<MemberCode> fromAllMemberCodes(String allMemberCodes) {
        String[] meberCodeStrings = allMemberCodes.split(",");
        List<MemberCode> memberCodes = new ArrayList<>();
        for (String memberCodeString : meberCodeStrings) {
            memberCodes.add(new MemberCode(memberCodeString));
        }
        return memberCodes;
    }

    public boolean memberOfChannel(String channel) {
        return channel.contains(memberCodeString);
    }

    @Override
    public int compareTo(MemberCode o) {
        return memberCodeString.compareTo(o.memberCodeString);
    }

    @JsonValue
    @Override
    public String toString() {
        return memberCodeString;
    }
}
