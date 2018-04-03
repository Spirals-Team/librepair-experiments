package com.d4dl.hellofib.placeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * A <a href="https://spring.io/understanding/HATEOAS">HATEOS</a> bean encapsulating a wrapped post element
 * retrieved from <a target="_blank" href="https://jsonplaceholder.typicode.com/posts">JSONPlaceholder posts</a>
 * extending {@link ResourceSupport} to facilitate construction HATEOS entities.
 *
 * The actual URI of each post is at
 * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder</a> so the selfrel link points
 * to it.  The Placeholder endpoint is, as its name implies, a placeholder for the entities at that address.
 * <p>
 * <b>
 *     Post objects should not be used to serialize object from a consumer client of this server.  They
 *     are merely wrapper objects for elements at
 *     <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
 * </b>
 * </p>
 */
public class Post extends ResourceSupport {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int wrappedId;
    private long userId;
    private String title;
    private String body;

    /**
     * Default constructor
     */
    public Post() {
    }

    /**
     * Setter used during deserialization to capture the id of the post elements from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>. This
     * allows the HATEOS object to be properly constructed since it HATEOS has a different notion of id, namely
     * that it be the identifying URI of the REST resource.
     * @param id the id that represents the element at
     *           <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>.
     *
     */
    @JsonSetter
    public void setId(int id) {
        this.wrappedId = id;
    }

    /**
     * Retrieves the id that is used at <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * for use during construction of identifying URIs of the actual post REST resource.  Since this object is
     * solely to be used to serialize and deserialize Post elements, this method is used to retrieved the actual
     * id of the element at <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>.
     * @return the id that represents the element at
     *           <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>.
     */
    public int getWrappedId() {
        return wrappedId;
    }

    /**
     * Retrieves the user id element that was retrieved from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * @return the user id that was set at
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Used during serialization, after data retreival from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * to set the userId
     * @param userId the user id as it is at
     *               <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the title element that was retrieved from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * presumably this represents some dummy title value of an imaginary post.
     * @return the title as it is at
     *               <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public String getTitle() {
        return title;
    }

    /**
     * Used during serialization, after data retreival from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * to set the title
     * @param title the title as it is at
     *               <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the body element that was retrieved from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * presumably this represents some dummy body value of an imaginary post.
     * @return the body as it is at
     *               <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public String getBody() {
        return body;
    }

    /**
     * Used during serialization, after data retreival from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     * to set the body
     * @param body the body as it is at
     *               <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
     */
    public void setBody(String body) {
        this.body = body;
    }
}
