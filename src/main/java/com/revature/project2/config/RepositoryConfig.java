package com.revature.project2.config;

import com.revature.project2.comments.Comment;
import com.revature.project2.eventpics.EventPic;
import com.revature.project2.eventratings.EventRating;
import com.revature.project2.events.Event;
import com.revature.project2.users.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(User.class);
    config.exposeIdsFor(Event.class);
    config.exposeIdsFor(Comment.class);
    config.exposeIdsFor(EventPic.class);
    config.exposeIdsFor(EventRating.class);
  }
}
