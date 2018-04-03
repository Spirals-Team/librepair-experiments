package net.mirwaldt.jcomparison.core.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.mirwaldt.jcomparison.core.exception.CycleDetectedException.CyclicTracesInfo.CyclePosition.*;

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
public class CycleDetectedException extends ComparisonFailedException {

    private final CyclicTracesInfo cyclicTracesInfo;

    public CycleDetectedException(Object leftObject, Object rightObject, CyclicTracesInfo cyclicTracesInfo) {
        super(leftObject, rightObject);
        this.cyclicTracesInfo = cyclicTracesInfo;
    }

    public CycleDetectedException(String message, Object leftObject, Object rightObject, CyclicTracesInfo cyclicTracesInfo) {
        super(message, leftObject, rightObject);
        this.cyclicTracesInfo = cyclicTracesInfo;
    }

    public CycleDetectedException(String message, Throwable cause, Object leftObject, Object rightObject, CyclicTracesInfo cyclicTracesInfo) {
        super(message, cause, leftObject, rightObject);
        this.cyclicTracesInfo = cyclicTracesInfo;
    }

    public CycleDetectedException(Throwable cause, Object leftObject, Object rightObject, CyclicTracesInfo cyclicTracesInfo) {
        super(cause, leftObject, rightObject);
        this.cyclicTracesInfo = cyclicTracesInfo;
    }

    public CycleDetectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object leftObject, Object rightObject, CyclicTracesInfo cyclicTracesInfo) {
        super(message, cause, enableSuppression, writableStackTrace, leftObject, rightObject);
        this.cyclicTracesInfo = cyclicTracesInfo;
    }

    public CyclicTracesInfo getCyclicTracesInfo() {
        return cyclicTracesInfo;
    }

    /**
     *
     * @return the cyclic trace between the first and the second occurrence of the left object in the graph
     * @throws IllegalStateException if left graph has no cycle
     */
    public List<Object> findLeftSubTraceOfCycle() {
        if(cyclicTracesInfo.getCyclePosition().equals(LEFT_GRAPH) || cyclicTracesInfo.getCyclePosition().equals(BOTH_GRAPHS)) {
            return CyclicTracesInfo.findCyclicSequence(getLeftObject(), cyclicTracesInfo.getTraceOfLeftObjects());
        } else {
            throw new IllegalStateException("Left object graph has no cycle.");
        }
    }

    /**
     *
     * @return the cyclic trace between the first and the second occurrence of the right object in the graph
     * @throws IllegalStateException if right graph has no cycle
     */
    public List<Object> findRightSubTraceOfCycle() {
        if(cyclicTracesInfo.getCyclePosition().equals(RIGHT_GRAPH) || cyclicTracesInfo.getCyclePosition().equals(BOTH_GRAPHS)) {
            return CyclicTracesInfo.findCyclicSequence(getRightObject(), cyclicTracesInfo.getTraceOfRightObject());
        } else {
            throw new IllegalStateException("Right object graph has no cycle.");
        }
    }

    public static class CyclicTracesInfo {
        public enum CyclePosition {
            LEFT_GRAPH, RIGHT_GRAPH, BOTH_GRAPHS
        }

        private final CyclePosition cyclePosition;

        private final List<Object> traceOfLeftObjects;
        private final List<Object> traceOfRightObject;

        public CyclicTracesInfo(CyclePosition cyclePosition, List<Object> traceOfLeftObjects, List<Object> traceOfRightObject) {
            this.cyclePosition = cyclePosition;
            this.traceOfLeftObjects = traceOfLeftObjects;
            this.traceOfRightObject = traceOfRightObject;
        }

        public CyclePosition getCyclePosition() {
            return cyclePosition;
        }

        public List<Object> getTraceOfLeftObjects() {
            return traceOfLeftObjects;
        }

        public List<Object> getTraceOfRightObject() {
            return traceOfRightObject;
        }

        public static List<Object> findCyclicSequence(Object cyclicObject, List<Object> track) {
            final int positionOfCyclicObject = track.indexOf(cyclicObject);
            if(0 <= positionOfCyclicObject) {
                final List<Object> cyclicSequence = new ArrayList<>(track.subList(positionOfCyclicObject, track.size()));
                cyclicSequence.add(cyclicObject);
                return cyclicSequence;
            } else {
                return Collections.emptyList();
            }
        }
    }
}
