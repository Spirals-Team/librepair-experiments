package pl.hycom.ip2018.searchengine.localsearch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.localsearch.exception.LocalSearchException;
import pl.hycom.ip2018.searchengine.localsearch.exception.LocalSearchIOException;
import pl.hycom.ip2018.searchengine.localsearch.model.LocalSearchResponse;
import pl.hycom.ip2018.searchengine.localsearch.model.Result;
import pl.hycom.ip2018.searchengine.localsearch.util.ZonedDateTimeStringConverter;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Implementation of {@link LocalSearch} to get data by query
 */
@Slf4j
public class DefaultLocalSearch implements LocalSearch {

    @Autowired
    private Environment environment;

    @Autowired
    private FileChecker fileChecker;

    @Autowired
    private ZonedDateTimeStringConverter converter;

    @Autowired
    private ResourceBundle resourceBundle;

    /**
     * Returns response wrapped in our type
     *
     * @param query we are searching for
     * @return object representation of response
     */
    @Override
    public LocalSearchResponse getResponse(String query) throws LocalSearchException {

        if (log.isInfoEnabled()) {
            log.info("Requesting searching results for {}", query);
        }
        try {
            // Get recursively all files in root
            List<Path> paths = Files.walk(Paths.get(environment.getProperty("rest.api.localPath"))).collect(Collectors.toList());

            // Get regular files from paths (not directory)
            List<Path> regulars = paths.stream().filter(Files::isRegularFile).collect(Collectors.toList());

            // Split files, readable and non readable
            List<Path> readableFiles = new ArrayList<>();
            List<Path> notReadableFiles = new ArrayList<>();
            regulars.forEach(reg -> {
                if (reg.toFile().canRead()) {
                    readableFiles.add(reg);
                } else {
                    notReadableFiles.add(reg);
                }
            });

            // Split readable files, binaries and plain texts
            List<Path> readableBinariesFiles = new ArrayList<>();
            List<Path> readablePlainTextsFiles = new ArrayList<>();

            // intensive operation but parallel
            readableFiles.parallelStream().forEach(read -> {
                if (fileChecker.isBinaryFile(read.toFile())) {
                    readableBinariesFiles.add(read);
                } else {
                    readablePlainTextsFiles.add(read);
                }
            });

            LocalSearchResponse response = new LocalSearchResponse();
            response.setResults(getFileResultsAsync(query,
                    paths, notReadableFiles, readableBinariesFiles, readablePlainTextsFiles));
            return response;
        } catch (IOException | InterruptedException | ExecutionException | LocalSearchIOException e) {
            if (log.isErrorEnabled()) {
                log.error("Searching results for {} are not available from Local", query);
            }
            throw new LocalSearchException();
        }
    }

    private List<SimpleResult> getFileResultsAsync(String query,
                                                   List<Path> paths,
                                                   List<Path> notReadableFiles,
                                                   List<Path> readableBinariesFiles,
                                                   List<Path> readablePlainTextsFiles)
            throws InterruptedException, ExecutionException {

        // Get results from directories
        CompletableFuture<List<Result>> directories
                = CompletableFuture.supplyAsync(() -> getDirectoriesResults(paths, query));
        // Get results from readable binaries
        CompletableFuture<List<Result>> readableBinaries
                = CompletableFuture.supplyAsync(() -> getBinariesResults(readableBinariesFiles, query));
        // Get results from readable plain text files
        CompletableFuture<List<Result>> readablePlainTexts
                = CompletableFuture.supplyAsync(() -> getPlainTextResults(readablePlainTextsFiles, query));
        // Get results from not readable files
        CompletableFuture<List<Result>> notReadable
                = CompletableFuture.supplyAsync(() -> getNotReadableResults(notReadableFiles, query));

        CompletableFuture.allOf(directories, readableBinaries, readablePlainTexts, notReadable).get();

        List<SimpleResult> items = new ArrayList<>();
        items.addAll(directories.get());
        items.addAll(readableBinaries.get());
        items.addAll(readablePlainTexts.get());
        items.addAll(notReadable.get());

        return items;
    }

    /**
     * Get not readable results which contains query
     *
     * @param notReadableFiles files we can not read but still can contains query in name
     * @param query            we are searching for
     * @return Results List
     */
    private List<Result> getNotReadableResults(List<Path> notReadableFiles, String query) {
        List<Result> result = new ArrayList<>();
        getFilesByQuery(notReadableFiles, query)
                .forEach(path -> result.add(getResultByPath(path, resourceBundle.getString("prop.local.canNotRead"))));
        return result;
    }

    /**
     * Get results for readable text files
     *
     * @param readableFiles files we can read
     * @param query         we are searching for
     * @return Results List
     */
    private List<Result> getPlainTextResults(List<Path> readableFiles, String query) {
        List<Result> result = new ArrayList<>();

        // parallel to be faster skrrr pop pop
        readableFiles.parallelStream().forEach(text -> {
            String snippet = getSnippet(text, query);
            if (!snippet.isEmpty()) {
                result.add(getResultByPath(text, snippet));
            } else if (containsIgnoreCase(getNameFromPath(text), query)) { // TODO refactor
                result.add(getResultByPath(text, resourceBundle.getString("prop.local.noQueryIn")));
            }
        });
        return result;
    }

    /**
     * Get results for binaries which can contain query in name
     *
     * @param readableFiles files we can read
     * @param query         we are searching for
     * @return Results List
     */
    private List<Result> getBinariesResults(List<Path> readableFiles, String query) {
        List<Result> result = new ArrayList<>();
        getFilesByQuery(readableFiles, query).
                forEach(bin -> result.add(getResultByPath(bin, resourceBundle.getString("prop.local.binFile"))));
        return result;
    }

    /**
     * Get results for directories which can contain query in name
     *
     * @param paths all files from root
     * @param query we are searching for
     * @return Results List
     */
    private List<Result> getDirectoriesResults(List<Path> paths, String query) {
        List<Result> result = new ArrayList<>();
        getDirectoriesByQuery(paths, query).
                forEach(dir -> result.add(getResultByPath(dir, resourceBundle.getString("prop.local.dirFile"))));
        return result;
    }

    /**
     * Convert information from File and snippet to Result
     *
     * @param path    path to File
     * @param snippet description for File
     * @return Result
     */
    private Result getResultByPath(Path path, String snippet) {
        Result res = new Result();
        res.setHeader(getNameFromPath(path));
        res.setSnippet(snippet);
        res.setTimestamp(converter.toStringFromLong(path.toFile().lastModified()));
        res.setUrl(path.toAbsolutePath().toString());
        return res;
    }

    /**
     * Get simple name from File
     *
     * @param path path to File
     * @return String
     */
    private String getNameFromPath(Path path) {
        String[] array = toShortPath(path).split("\\u005c");
        return array[array.length - 1];
    }

    /**
     * Get snippet according to Confluence Documentation
     *
     * @param path  path to file
     * @param query we are searching for
     * @return String
     */
    private String getSnippet(Path path, String query) {
        try {
            List<String> lines = Files.readAllLines(path);
            int all = lines.size();
            int counter = 0;
            boolean found = false;
            for (String line : lines) {
                if (containsIgnoreCase(line, query)) {
                    found = true;
                    break;
                }
                counter++;
            }
            if (!found) return "";
            if (counter == 0 && all == 1) {
                return lines.get(0);
            }
            if (counter > 0 && counter < all - 1) {
                return new StringBuilder()
                        .append(lines.get(counter - 1)).append("\n")
                        .append(lines.get(counter)).append("\n")
                        .append(lines.get(counter + 1)).toString();
            }
            if (all == 2) {
                return new StringBuilder()
                        .append(lines.get(0)).append("\n")
                        .append(lines.get(1)).toString();
            }
            if (counter == 0) {
                return new StringBuilder()
                        .append(lines.get(0)).append("\n")
                        .append(lines.get(1)).append("\n")
                        .append(lines.get(2)).toString();
            }
            if (counter == all - 1) {
                return new StringBuilder()
                        .append(lines.get(all - 3)).append("\n")
                        .append(lines.get(all - 2)).append("\n")
                        .append(lines.get(all - 1)).toString();
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * Get files which contain query
     *
     * @param regulars files
     * @param query    we are searching for
     * @return Paths List
     */
    private List<Path> getFilesByQuery(List<Path> regulars, String query) {
        List<Path> result = new ArrayList<>();
        regulars.forEach(reg -> {
            if (containsIgnoreCase(getNameFromPath(reg), query)) {
                result.add(reg);
            }
        });
        return result;
    }

    /**
     * Get directories which contain query
     *
     * @param paths files
     * @param query we are searching for
     * @return Paths List
     */
    private List<Path> getDirectoriesByQuery(List<Path> paths, String query) {
        List<Path> result = new ArrayList<>();
        List<Path> earlier = new ArrayList<>();
        paths.stream()
                .filter(path -> isDirectory(path)
                        && containsIgnoreCase(path, query)
                        && !itWasEarlier(earlier, path))
                .forEach(path -> {
                    earlier.add(path);
                    result.add(path);
                });
        return result;
    }

    /**
     * Simplify file path
     *
     * @param path path to files
     * @return String
     */
    private String toShortPath(Path path) {
        int beginning = environment.getProperty("rest.api.localPath").length();
        return path.toString().substring(path.toString().length() == beginning - 1
                ? beginning - 1
                : beginning);
    }

    private boolean containsIgnoreCase(Path main, String inside) {
        return toShortPath(main).toLowerCase().contains(inside.toLowerCase());
    }

    private boolean containsIgnoreCase(String main, String inside) {
        return main.toLowerCase().contains(inside.toLowerCase());
    }

    private boolean containsIgnoreCase(Path main, Path inside) {
        return toShortPath(main).toLowerCase().contains(toShortPath(inside).toLowerCase());
    }

    private boolean isDirectory(Path path) {
        return path.toFile().isDirectory();
    }

    private boolean itWasEarlier(List<Path> main, Path param) {
        return !main.stream()
                .filter(o -> containsIgnoreCase(param, o))
                .collect(Collectors.toList()).isEmpty();
    }
}
