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
package nl.mpi.tg.eg.experimentdesigner.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @since Feb 23, 2016 3:00:09 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class StimuliSubAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String percentOfPage;
    private String label;
    private String[] buttonArray;

    public StimuliSubAction() {
    }

    public StimuliSubAction(String percentOfPage, String label, String button) {
        this.percentOfPage = percentOfPage;
        this.label = label;
        this.buttonArray = new String[]{button};
    }

    public StimuliSubAction(String percentOfPage, String[] buttonArray) {
        this.percentOfPage = percentOfPage;
        this.label = null;
        this.buttonArray = buttonArray;
    }

    public String getPercentOfPage() {
        return percentOfPage;
    }

    public String getLabel() {
        return label;
    }

    public String[] getButtons() {
        return buttonArray;
    }
}
