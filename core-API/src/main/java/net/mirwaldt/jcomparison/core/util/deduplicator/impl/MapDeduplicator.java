package net.mirwaldt.jcomparison.core.util.deduplicator.impl;

import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.Map;
import java.util.function.Supplier;

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
public class MapDeduplicator implements Deduplicator {
    private final Map<Object, Object> deduplicateObjects;

    public MapDeduplicator(Supplier<Map<Object, Object>> deduplicateObjectsSupplier) {
        this.deduplicateObjects = deduplicateObjectsSupplier.get();
    }

    @Override
    public Object deduplicate(Object potentialDuplicate) {
        if (potentialDuplicate == null) {
            return null;
        } else {
            return deduplicateObjects.computeIfAbsent(potentialDuplicate, (key) -> potentialDuplicate);
        }
    }

    @Override
    public void clean() {
        deduplicateObjects.clear();
    }
}
