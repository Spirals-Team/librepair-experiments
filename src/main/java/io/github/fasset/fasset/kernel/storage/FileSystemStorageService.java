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

import io.github.fasset.fasset.config.StorageProperties;
import io.github.fasset.fasset.kernel.notifications.FileUploadNotification;
import io.github.fasset.fasset.kernel.subscriptions.SimpleSubscription;
import io.github.fasset.fasset.kernel.subscriptions.SubscriptionService;
import io.github.fasset.fasset.kernel.util.StorageException;
import io.github.fasset.fasset.kernel.util.StorageFileNotFoundException;
import io.github.fasset.fasset.model.files.FileUpload;
import io.github.fasset.fasset.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Implements storageService interface while at the same time implementing the subscriptionService. The later allows
 * a {@link io.github.fasset.fasset.kernel.subscriptions.Subscriber} implementation to observe changes and act
 * accordingly, and this this case upload data stored in the the file recently uploaded in the file system
 */
@Service("fileSystemStorageService")
public class FileSystemStorageService extends SimpleSubscription implements SubscriptionService, StorageService {

    private static final Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);


    private final Path rootLocation;

    private final FileUploadService fileUploadService;


    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties, @Qualifier("fileUploadService") FileUploadService fileUploadService) {
        rootLocation = Paths.get(storageProperties.getLocation());
        this.fileUploadService = fileUploadService;
    }

    /**
     * To store the file into storage
     *
     * @param file Multipart file being uploaded to the file system from the front end
     */
    @Override
    public void store(MultipartFile file) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Storing file into the directory : {}", fileName);

        if (file.isEmpty()) {

            throw new StorageException("Failed to store empty file : " + fileName);

        } else {

            FileUpload fileUpload = configureFileUploadAttributes(this.rootLocation.resolve(fileName).toString(), YearMonth.of(2017, 12));

            if (fileUploadService.theFileIsAlreadyUploaded(fileUpload)) {

                log.info("The file : {} has already been uploaded", fileUpload.getFileName());

            } else {

                if (fileName.contains("..")) {
                    // This is a security check
                    throw new StorageException("Cannot store file with relative path outside current directory " + fileName);
                } else {

                    try {
                        Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new StorageException("Failed to store file " + fileName, e);
                    }

                    // Replace with subscriber for database and another for the batch
                    fileUploadService.recordFileUpload(fileUpload);

                    postUpdate(() -> new FileUploadNotification(fileUpload.getFileName(), fileUpload.getMonth().toString(), fileUpload.getTimeUploaded().toString()));
                }
            }
        }

    }

    private FileUpload configureFileUploadAttributes(String fileName, YearMonth month) {

        LocalDateTime uploadTime = LocalDateTime.now();

        log.info("Configuring notification to server of the file uploaded : {} for the month : {} at time :{}" + "", fileName, month, uploadTime);

        return new FileUpload(fileName, month, uploadTime);
    }


    /**
     * Loads all files into storage
     *
     * @return Stream containing file names of files currently in the file system
     */
    @Override
    public Stream<Path> loadAll() {

        log.info("Checking for files in the directory...");

        Stream<Path> filePathStream;

        try {
            filePathStream = Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

        return filePathStream;
    }

    /**
     * Load the fileName given into storage
     *
     * @param fileName String name of the file being loaded
     * @return Path of the file after it has been loaded
     */
    @Override
    public Path load(String fileName) {

        log.info("Loading file : {} from storage", fileName);

        return rootLocation.resolve(fileName);
    }

    /**
     * Loads the file given as a {@link Resource} object
     *
     * @param fileName String name of the file being loaded as a resource
     * @return Resource containing the file path
     */
    @Override
    public Resource loadAsResource(String fileName) {

        log.debug("Loading fileName : {} as resource", fileName);

        Resource resource = null;

        Path file = load(fileName);

        try {
            Resource temp = new UrlResource(file.toUri());

            if (!temp.exists() || !temp.isReadable()) {

                throw new StorageFileNotFoundException("Could not read file: " + fileName);
            } else {

                resource = temp;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return resource;
    }

    /**
     * Deletes all files in the storage
     */
    @Override
    public void deleteAll() {

        log.info("Deleting all files from the file system storage");

        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /**
     * To initialize storage and create storage directory
     */
    @Override
    public void init() {

        log.info("Initializing file storage system...");

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
