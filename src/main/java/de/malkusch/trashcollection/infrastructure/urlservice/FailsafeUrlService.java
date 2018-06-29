package de.malkusch.trashcollection.infrastructure.urlservice;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FailsafeUrlService implements OpenUrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailsafeUrlService.class);

    FailsafeUrlService(Path bufferDirectory, OpenUrlService openUrlService) {
        this.bufferDirectory = bufferDirectory;
        this.openUrlService = openUrlService;
    }

    private final OpenUrlService openUrlService;

    @Override
    public InputStream open(URL url) throws IOException {
        final Path buffer = bufferFile(url);

        try (InputStream stream = openUrlService.open(url)) {
            Path tmp = Files.createTempFile("trashcollection-", ".tmp");
            LOGGER.debug("Downloading {} into {}", url, tmp);
            Files.copy(stream, tmp, REPLACE_EXISTING);
            LOGGER.debug("Replacing {} with {}", buffer, tmp);
            Files.move(tmp, buffer, REPLACE_EXISTING);

        } catch (IOException e) {
            LOGGER.warn("Couldn't read {}, falling back to {}", url, buffer, e);
        }
        return Files.newInputStream(buffer);
    }

    private final Path bufferDirectory;

    private Path bufferFile(URL url) {
        final String hash = DigestUtils.md5Hex(url.toString());
        return Paths.get(bufferDirectory.toString(), "trashcollection-" + hash);
    }

}
