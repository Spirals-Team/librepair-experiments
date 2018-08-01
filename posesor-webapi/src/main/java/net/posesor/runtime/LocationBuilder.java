package net.posesor.runtime;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.concurrent.ThreadSafe;
import java.net.URI;
import java.util.function.Function;

/**
 * Allows to obtain URI locations for controllers.
 *
 * It is separated because needs integration test. Exposing it as simple
 * functional interface allows express this functionality as mockable logic.
 *
 * @param <T> the class of controller to be used as root URL part in created links.
 */
@ThreadSafe
public final class LocationBuilder<T> implements Function<String, URI> {

    private final UriComponentsBuilder uriBuilder;

    /**
     * Creates a new instance of {@code LocationBuilder}.
     *
     * @param controller type of controller used as the base of resources' location.
     */
    public LocationBuilder(@NotNull Class<T> controller) {
        uriBuilder = ControllerLinkBuilder.linkTo(controller).toUriComponentsBuilder().path("/{id}");
    }

    /**
     * Produces location for {@code resourceId} based on {@code T}.
     *
     * @param resourceId unique identifier of resource.
     * @return full URI equivalent of the identifier.
     */
    @Override
    public URI apply(String resourceId) {
        return uriBuilder.buildAndExpand(resourceId).toUri();
    }
}
