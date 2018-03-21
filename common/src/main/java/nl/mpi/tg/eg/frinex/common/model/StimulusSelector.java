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
package nl.mpi.tg.eg.frinex.common.model;

import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;

/**
 * @since Aug 24, 2017 2:42:33 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimulusSelector {

    final String alias;
    final Tag tag;
    final Integer minCount;
    final Integer maxCount;

    public StimulusSelector(Tag tag) {
        this.alias = tag.name();
        this.tag = tag;
        this.minCount = null;
        this.maxCount = null;
    }

    public StimulusSelector(String alias, Tag tag) {
        this.alias = alias;
        this.tag = tag;
        this.minCount = null;
        this.maxCount = null;
    }

    public StimulusSelector(String alias, Tag tag, int minCount, int maxCount) {
        this.alias = alias;
        this.tag = tag;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public String getAlias() {
        return alias;
    }

    public Tag getTag() {
        return tag;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }
}
