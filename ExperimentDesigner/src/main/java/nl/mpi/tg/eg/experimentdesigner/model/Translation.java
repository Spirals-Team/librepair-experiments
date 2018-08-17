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

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;

/**
 * @since Jun 19, 2017 11:17:47 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class Translation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdDate = null;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date changedDate = null;

    private String description;
    @ElementCollection
    @CollectionTable(name = "locale_text_table")
    @MapKeyColumn(name = "locale_key")
    @Column(length = 6000)
    private final Map<String, String> translationMap = new HashMap<>();

    public Translation() {
        if (createdDate == null) {
            createdDate = new Date();
        }
        if (changedDate == null) {
            changedDate = new Date();
        }
    }

    public void setLocaleString(String locale, String translatedText) {
        translationMap.put(locale, translatedText);
    }

    public String getLocaleString(String locale) {
        return translationMap.get(locale);
    }

    public Set<String> getAvailableLocales() {
        return translationMap.keySet();
    }
}
