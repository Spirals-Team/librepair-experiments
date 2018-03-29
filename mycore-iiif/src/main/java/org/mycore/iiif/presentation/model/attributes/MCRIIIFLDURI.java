/*
 * This file is part of ***  M y C o R e  ***
 * See http://www.mycore.de/ for details.
 *
 * MyCoRe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCoRe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCoRe.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mycore.iiif.presentation.model.attributes;

import org.mycore.iiif.model.MCRIIIFBase;

public class MCRIIIFLDURI extends MCRIIIFBase {

    private String format = null;

    public MCRIIIFLDURI(String uri, String type, String format) {
        super(uri, type, MCRIIIFBase.API_PRESENTATION_2);
        this.format = format;
    }

    public MCRIIIFLDURI(String uri, String type) {
        super(uri, type, MCRIIIFBase.API_PRESENTATION_2);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
