package ar.edu.undav.semillero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Alejandro Gomez
 */
public abstract class WebUtils {

    public static <T> ResponseEntity<T> emptyToNotFound(Optional<T> body) {
        return body.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<Collection<T>> emptyToNotFound(Collection<T> body) {
        return CollectionUtils.isEmpty(body) ? ResponseEntity.notFound().build() : ResponseEntity.ok(body);
    }
}
