package pw.spn.crawler.livelib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import pw.spn.crawler.livelib.entity.Book;
import pw.spn.crawler.livelib.entity.BookStatus;
import pw.spn.crawler.livelib.exception.LivelibCrawlerException;
import pw.spn.crawler.livelib.http.LivelibHttpService;

public class LivelibCrawler {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String WISH_URL_TEMPLATE = "/userbooks?page=wishonly&login=%s&count=" + Integer.MAX_VALUE;
    private static final String READING_URL_TEMPLATE = "/userbooks?page=reading&login=%s&count=" + Integer.MAX_VALUE;
    private static final String UNREAD_URL_TEMPLATE = "/userbooks?page=unread&login=%s&count=" + Integer.MAX_VALUE;
    private static final String READ_URL_TEMPLATE = "/userbooks?page=read&login=%s&count=" + Integer.MAX_VALUE;
    private static final String SEARCH_URL_TEMPLATE = "/booksearch?sort_order=rating&searchstring=%s&start=%d";
    private final LivelibHttpService httpService;

    public LivelibCrawler() {
        this(new LivelibHttpService());
    }

    public LivelibCrawler(LivelibHttpService httpService) {
        this.httpService = httpService;
    }

    public List<Book> getByStatus(String userName, BookStatus status) {
        switch (status) {
        case WISH_TO_READ:
            return getWish(userName);
        case ALREADY_READ:
            return getRead(userName);
        case READING:
            return getReading(userName);
        case UNREAD:
            return getUnread(userName);
        default:
            return Collections.emptyList();
        }
    }

    public List<Book> getWish(String userName) {
        Optional<List<Book>> result = httpService.doGet(String.format(WISH_URL_TEMPLATE, userName),
                new TypeReference<List<Book>>() {
        });
        return result.orElse(Collections.emptyList());
    }

    public List<Book> getReading(String userName) {
        Optional<List<Book>> result = httpService.doGet(String.format(READING_URL_TEMPLATE, userName),
                new TypeReference<List<Book>>() {
        });
        return result.orElse(Collections.emptyList());
    }

    public List<Book> getRead(String userName) {
        Optional<List<Book>> result = httpService.doGet(String.format(READ_URL_TEMPLATE, userName),
                new TypeReference<List<Book>>() {
        });
        return result.orElse(Collections.emptyList());
    }

    public List<Book> getUnread(String userName) {
        Optional<List<Book>> result = httpService.doGet(String.format(UNREAD_URL_TEMPLATE, userName),
                new TypeReference<List<Book>>() {
        });
        return result.orElse(Collections.emptyList());
    }

    public List<Book> searchForBook(String query) {
        String encodedSearchString;
        try {
            encodedSearchString = URLEncoder.encode(query, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new LivelibCrawlerException(e);
        }
        Optional<List<Book>> result = httpService.doGet(String.format(SEARCH_URL_TEMPLATE, encodedSearchString, 0),
                new TypeReference<List<Book>>() {
        });
        return result.orElse(Collections.emptyList());
    }
}
