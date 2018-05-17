package org.itsallcode.openfasttrace.importer.legacytag;

/*-
 * #%L
 \* OpenFastTrace
 * %%
 * Copyright (C) 2016 - 2018 hamstercommunity
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

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.itsallcode.openfasttrace.importer.*;
import org.itsallcode.openfasttrace.importer.input.InputFile;
import org.itsallcode.openfasttrace.importer.legacytag.config.LegacyTagImporterConfig;
import org.itsallcode.openfasttrace.importer.legacytag.config.PathConfig;

// [impl->dsn~import.short-coverage-tag~1]
public class LegacyTagImporterFactory extends ImporterFactory
{
    private static final Logger LOG = Logger.getLogger(LegacyTagImporterFactory.class.getName());

    private static LegacyTagImporterConfig defaultConfig = LegacyTagImporterConfig.empty();;

    private final Supplier<LegacyTagImporterConfig> config;

    public LegacyTagImporterFactory()
    {
        this(() -> defaultConfig);
    }

    public LegacyTagImporterFactory(final Supplier<LegacyTagImporterConfig> config)
    {
        this.config = config;
    }

    @Override
    public boolean supportsFile(final InputFile path)
    {
        return findConfig(path).isPresent();
    }

    private Optional<PathConfig> findConfig(final InputFile file)
    {
        return this.config.get().getPathConfigs().stream() //
                .filter(config -> config.matches(file)) //
                .findFirst();
    }

    @Override
    public Importer createImporter(final InputFile path, final ImportEventListener listener)
    {
        final Optional<PathConfig> config = findConfig(path);
        if (!config.isPresent())
        {
            final List<String> descriptions = this.config.get().getPathConfigs().stream() //
                    .map(PathConfig::getDescription) //
                    .collect(toList());
            throw new ImporterException("File '" + path
                    + "' not supported for import, supported paths: " + descriptions);
        }
        return () -> runImporter(path, config.get(), listener);
    }

    private void runImporter(final InputFile file, final PathConfig config,
            final ImportEventListener listener)
    {
        LOG.finest(() -> "Creating importer for file " + file);
        final LegacyTagImporter importer = new LegacyTagImporter(config, file, listener);
        importer.runImport();
    }

    public static void setPathConfig(final LegacyTagImporterConfig config)
    {
        defaultConfig = config;
    }
}
