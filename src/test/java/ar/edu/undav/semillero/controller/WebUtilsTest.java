package ar.edu.undav.semillero.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Alejandro Gomez
 */
public class WebUtilsTest {

    @Test
    public void givenEmptyShouldReturnNotFound() {
        // given
        Optional<Object> body = Optional.empty();
        // when
        ResponseEntity<Object> entity = WebUtils.emptyToNotFound(body);
        // then
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenNonEmptyShouldReturnOk() {
        // given
        Optional<Object> body = Optional.of("hello world!");
        // when
        ResponseEntity<Object> entity = WebUtils.emptyToNotFound(body);
        // then
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenEmptyCollectionShouldReturnNotFound() {
        // given
        Collection<Object> body = Collections.emptyList();
        // when
        ResponseEntity<Collection<Object>> entity = WebUtils.emptyToNotFound(body);
        // then
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenNonEmptyCollectionShouldReturnOk() {
        // given
        Collection<Object> body = Collections.singleton("hello world!");
        // when
        ResponseEntity<Collection<Object>> entity = WebUtils.emptyToNotFound(body);
        // then
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
