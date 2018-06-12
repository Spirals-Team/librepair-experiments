/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.storage;

import io.github.fasset.fasset.kernel.subscriptions.SubscriptionService;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Provides storage service to clients for files uploads
 */
public interface StorageService extends SubscriptionService {

    /**
     * To initialize storage and create storage directory
     */
    void init();

    /**
     * To store the file into storage
     *
     * @param file Multipart file to be stored
     */
    void store(MultipartFile file);

    /**
     * Loads all files into storage
     *
     * @return Stream containing the paths of all the files saved
     */
    Stream<Path> loadAll();

    /**
     * Load the fileName given into storage
     *
     * @param fileName of the file being loaded
     * @return Path of the file after being loaded
     */
    Path load(String fileName);

    /**
     * Loads the file given as a {@link Resource} object
     *
     * @param fileName of the file being loaded as resource
     * @return Path of the file after being loaded as resource
     */
    Resource loadAsResource(String fileName);

    /**
     * Deletes all files in the storage
     */
    void deleteAll();
}
