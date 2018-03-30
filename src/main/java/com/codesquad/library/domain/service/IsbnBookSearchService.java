package com.codesquad.library.domain.service;

import com.codesquad.library.dtos.model.book.BookSearchDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class IsbnBookSearchService {

    private static final Logger log = LoggerFactory.getLogger(IsbnBookSearchService.class);

    @Value("${naver.book.endpoint}")
    private String uri;

    @Value("${naver.book.accesskey}")
    private String accessKey;

    @Value("${naver.book.secretkey}")
    private String secretKey;

    public BookSearchDocument getBookInfo(long isbn) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", accessKey);
        headers.set("X-Naver-Client-Secret", secretKey);

        log.debug(headers.toString());

        RestTemplate template = new RestTemplate();

        return template.exchange(generateUri(isbn), HttpMethod.GET, new HttpEntity(headers), BookSearchDocument.class).getBody();
    }

    private URI generateUri(long isbn){
        log.debug("injected endpoint : {}", this.uri);
        URI uri= UriComponentsBuilder.newInstance().scheme("https").host(this.uri).path("/v1/search/book.json").queryParam("query", isbn).build().encode().toUri();
        log.debug("generated uri : {}", uri.toString());

        return uri;
    }

}
