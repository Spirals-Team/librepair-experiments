package net.mirwaldt.jcomparison.core.util.deduplicator.impl;

import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

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
public class WeakHashMapDeduplicator implements Deduplicator {
    private final WeakHashMap<Object, WeakReference<Object>> weakHashMap = new WeakHashMap<>();

    public Object deduplicate(Object potentialDuplicate) {
        if(potentialDuplicate == null) {
            return null;
        } else {
            final WeakReference<Object> oldReference = weakHashMap.get(potentialDuplicate);

            if (oldReference != null) {
                final Object oldValue = oldReference.get();

                if (oldValue != null) {
                    return potentialDuplicate;
                }
            }

            weakHashMap.put(potentialDuplicate, new WeakReference<>(potentialDuplicate));

            return potentialDuplicate;
        }
    }

    @Override
    public void clean() {
        weakHashMap.clear();
    }
}
