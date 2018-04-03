package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.CycleDetectedException;

import java.util.List;
import java.util.stream.Collectors;

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
 */
public class ExceptionAtCycleComparator implements ItemComparator<Object, ComparisonResult<?,?,?>> {

    @Override
    public ComparisonResult<?,?,?> compare(Object leftItem, 
                                           Object rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        final IdentityComparingItem leftIdentityComparingItem = new IdentityComparingItem(leftItem);
        final IdentityComparingItem rightIdentityComparingItem = new IdentityComparingItem(rightItem);

        final boolean hasCycleInLeftObjectGraph = visitedObjectsTrace.readVisitedLeftObjects().contains(leftIdentityComparingItem);
        final boolean hasCycleInRightObjectGraph = visitedObjectsTrace.readVisitedRightObjects().contains(rightIdentityComparingItem);
        if(hasCycleInLeftObjectGraph && visitedObjectsTrace.readVisitedRightObjects().contains(rightIdentityComparingItem)) {
            final CycleDetectedException.CyclicTracesInfo cyclicTracesInfo = createTrackingInfo(CycleDetectedException.CyclicTracesInfo.CyclePosition.BOTH_GRAPHS, visitedObjectsTrace);
            throw new CycleDetectedException("Both objects graphs have cycles.", leftItem, rightItem, cyclicTracesInfo);
        } else if(hasCycleInLeftObjectGraph) {
            final CycleDetectedException.CyclicTracesInfo cyclicTracesInfo = createTrackingInfo(CycleDetectedException.CyclicTracesInfo.CyclePosition.LEFT_GRAPH, visitedObjectsTrace);
            throw new CycleDetectedException("Left object graph has a cycle.", leftItem, rightItem, cyclicTracesInfo);
        } else if(hasCycleInRightObjectGraph){
            final CycleDetectedException.CyclicTracesInfo cyclicTracesInfo = createTrackingInfo(CycleDetectedException.CyclicTracesInfo.CyclePosition.RIGHT_GRAPH, visitedObjectsTrace);
            throw new CycleDetectedException("Right object graph has a cycle.", leftItem, rightItem, cyclicTracesInfo);
        } else {
            throw new ComparisonFailedException("Cannot find any cycle.", leftItem, rightItem);
        }
    }

    private CycleDetectedException.CyclicTracesInfo createTrackingInfo(CycleDetectedException.CyclicTracesInfo.CyclePosition bothGraphs, VisitedObjectsTrace visitedObjectsTrace) {
        final List<Object> trackOfLeftObjects = visitedObjectsTrace.readVisitedLeftObjects().stream().map(IdentityComparingItem::getObject).collect(Collectors.toList());
        final List<Object> trackOfRightObjects = visitedObjectsTrace.readVisitedRightObjects().stream().map(IdentityComparingItem::getObject).collect(Collectors.toList());

        return new CycleDetectedException.CyclicTracesInfo(bothGraphs, trackOfLeftObjects, trackOfRightObjects);
    }
}
