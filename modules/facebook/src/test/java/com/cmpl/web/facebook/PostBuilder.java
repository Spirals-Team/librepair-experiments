package com.cmpl.web.facebook;

import java.util.Date;

import org.mockito.BDDMockito;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.Post.PostType;

import com.cmpl.web.core.common.builder.Builder;

public class PostBuilder extends Builder<Post> {

  private PostBuilder() {
  }

  private String message;
  private String description;
  private String name;
  private String caption;
  private PostType type;
  private String id;
  private String objectId;
  private Date createdTime;
  private String link;
  private Reference reference;
  private String picture;
  private String source;

  public PostBuilder message(String message) {
    this.message = message;
    return this;
  }

  public PostBuilder description(String description) {
    this.description = description;
    return this;
  }

  public PostBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PostBuilder caption(String caption) {
    this.caption = caption;
    return this;
  }

  public PostBuilder id(String id) {
    this.id = id;
    return this;
  }

  public PostBuilder objectId(String objectId) {
    this.objectId = objectId;
    return this;
  }

  public PostBuilder type(PostType type) {
    this.type = type;
    return this;
  }

  public PostBuilder createdTime(Date createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  public PostBuilder link(String link) {
    this.link = link;
    return this;
  }

  public PostBuilder reference(Reference reference) {
    this.reference = reference;
    return this;
  }

  public PostBuilder picture(String picture) {
    this.picture = picture;
    return this;
  }

  public PostBuilder source(String source) {
    this.source = source;
    return this;
  }

  @Override
  public Post build() {
    Post post = BDDMockito.mock(Post.class);

    BDDMockito.doReturn(message).when(post).getMessage();
    BDDMockito.doReturn(id).when(post).getId();
    BDDMockito.doReturn(description).when(post).getDescription();
    BDDMockito.doReturn(name).when(post).getName();
    BDDMockito.doReturn(caption).when(post).getCaption();
    BDDMockito.doReturn(type).when(post).getType();
    BDDMockito.doReturn(createdTime).when(post).getCreatedTime();
    BDDMockito.doReturn(objectId).when(post).getObjectId();
    BDDMockito.doReturn(link).when(post).getLink();
    BDDMockito.doReturn(reference).when(post).getFrom();
    BDDMockito.doReturn(picture).when(post).getPicture();
    BDDMockito.doReturn(source).when(post).getSource();

    return post;
  }

  public static PostBuilder create() {
    return new PostBuilder();
  }
}
