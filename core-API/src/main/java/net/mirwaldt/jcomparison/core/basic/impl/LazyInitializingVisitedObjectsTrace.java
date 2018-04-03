package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * default implementation for VisitedObjectsTrace
 */
public class LazyInitializingVisitedObjectsTrace implements VisitedObjectsTrace {
    //TODO: Collection instead of Set? Collection also has contains() and a list instead of a set would also be possible for small traces		
    private Set<IdentityComparingItem> visitedLeftObjects = Collections.emptySet();
    private Set<IdentityComparingItem> visitedRightObjects = Collections.emptySet();

    private Set<IdentityComparingItem> unmodifiableVisitedLeftObjects = Collections.emptySet();
    private Set<IdentityComparingItem> unmodifiableVisitedRightObjects = Collections.emptySet();

    public Set<IdentityComparingItem> readVisitedLeftObjects() {
        if (unmodifiableVisitedLeftObjects == null) {
            unmodifiableVisitedLeftObjects = Collections.unmodifiableSet(visitedLeftObjects);
        }
        return visitedLeftObjects;
    }

    public Set<IdentityComparingItem> readVisitedRightObjects() {
        if (unmodifiableVisitedRightObjects == null) {
            unmodifiableVisitedRightObjects = Collections.unmodifiableSet(visitedRightObjects);
        }
        return unmodifiableVisitedRightObjects;
    }

    public Set<IdentityComparingItem> writeVisitedLeftObjects() {
        if (visitedLeftObjects == Collections.<IdentityComparingItem>emptySet()) {
             /*
             * LinkedHashSet because we want to save insertion order for iteration
             */
            visitedLeftObjects = new LinkedHashSet<>();
            unmodifiableVisitedLeftObjects = null;
        }
        return visitedLeftObjects;
    }

    public Set<IdentityComparingItem> writeVisitedRightObjects() {
        if (visitedRightObjects == Collections.<IdentityComparingItem>emptySet()) {
             /*
             * LinkedHashSet because we want to save insertion order for iteration
             */
            visitedRightObjects = new LinkedHashSet<>();
            unmodifiableVisitedRightObjects = null;
        }
        return visitedRightObjects;
    }
}

