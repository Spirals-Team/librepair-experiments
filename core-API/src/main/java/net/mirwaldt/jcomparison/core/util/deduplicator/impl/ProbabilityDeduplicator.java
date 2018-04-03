package net.mirwaldt.jcomparison.core.util.deduplicator.impl;

import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.concurrent.ThreadLocalRandom;

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
 * inspired by (NOT COPIED!)
 * https://shipilev.net/talks/joker-Oct2014-string-catechism.pdf
 * slide 53
 *
 * Created by Michael on 22.07.2017.
 */
public class ProbabilityDeduplicator implements Deduplicator {
    private final int probabilityInPercent;
    private final Deduplicator deduplicator;

    public ProbabilityDeduplicator(int probabilityInPercent, Deduplicator deduplicator) {
        if(probabilityInPercent < 0 || 100 < probabilityInPercent) {
            throw new IllegalArgumentException("Constructor parameter probabilityInPercent must lie between 0 and 100 (both inclusive) but it is '" + probabilityInPercent + "'!");
        }
        this.probabilityInPercent = probabilityInPercent;
        this.deduplicator = deduplicator;
    }

    @Override
    public Object deduplicate(Object value) {
        if(probabilityInPercent == 0) {
            return value;
        } else if(probabilityInPercent == 100) {
            return deduplicator.deduplicate(value);
        } else if(1 + ThreadLocalRandom.current().nextInt(99) <= probabilityInPercent) {
            return deduplicator.deduplicate(value);
        } else {
            return value;
        }
    }

    @Override
    public void clean() {
        deduplicator.clean();
    }
}
