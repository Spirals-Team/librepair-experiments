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
package nl.mpi.tg.eg.experimentdesigner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @since Aug 24, 2017 4:09:52 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class RandomTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @XmlAttribute
    private String alias;
    @XmlValue
    private String tag;
    @XmlAttribute
    private Integer minCount;
    @XmlAttribute
    private Integer maxCount;

    public RandomTag() {
    }

    public RandomTag(String alias, String tag, Integer minCount, Integer maxCount) {
        this.alias = alias;
        this.tag = tag;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }
}
