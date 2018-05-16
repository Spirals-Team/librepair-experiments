/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @since Nov 4, 2016 2:15:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class ScreenText implements Serializable {

    @Id
    @XmlTransient
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 6000)
    protected String screenText = null;

    public long getId() {
        return id;
    }

    @XmlAttribute(name = "value")
    public String getScreenText() {
        return screenText;
    }

    public void setScreenText(String screenText) {
        this.screenText = screenText;
    }
}
