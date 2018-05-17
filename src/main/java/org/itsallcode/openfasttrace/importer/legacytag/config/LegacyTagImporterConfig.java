package org.itsallcode.openfasttrace.importer.legacytag.config;

/*-
 * #%L
 * OpenFastTrace
 * %%
 * Copyright (C) 2016 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import static java.util.Collections.emptyList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Configuration for the {@link LegacyTagImporter}.
 */
public class LegacyTagImporterConfig
{
    private final List<PathConfig> pathConfigs;
    private final Optional<Path> basePath;

    /**
     * Create a new configuration object.
     * 
     * @param basePath
     *            the root directory of the project to import. All paths will be
     *            relative to this path.
     * @param pathConfigs
     *            a list of {@link PathConfig} objects.
     */
    public LegacyTagImporterConfig(final Optional<Path> basePath,
            final List<PathConfig> pathConfigs)
    {
        this.basePath = basePath;
        this.pathConfigs = pathConfigs;
    }

    /**
     * Creates a new, empty configuration.
     */
    public static LegacyTagImporterConfig empty()
    {
        return new LegacyTagImporterConfig(Optional.empty(), emptyList());
    }

    public List<PathConfig> getPathConfigs()
    {
        return this.pathConfigs;
    }

    public Optional<Path> getBasePath()
    {
        return this.basePath;
    }
}
