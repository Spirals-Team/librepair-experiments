package com.cmpl.web.facebook;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.news.entry.NewsEntryService;

/**
 * Implementation de l'interface qui va recuperer les posts facebook d'unutilisateur
 * 
 * @author Louis
 *
 */
public class FacebookServiceImpl implements FacebookService {

  private final Facebook facebookConnector;
  private final ConnectionRepository connectionRepository;
  private final ContextHolder contextHolder;
  private final NewsEntryService newsEntryService;

  public FacebookServiceImpl(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    this.facebookConnector = Objects.requireNonNull(facebookConnector);

    this.connectionRepository = Objects.requireNonNull(connectionRepository);

    this.contextHolder = Objects.requireNonNull(contextHolder);

    this.newsEntryService = Objects.requireNonNull(newsEntryService);

  }

  @Override
  public List<ImportablePost> getRecentFeed() throws BaseException {

    if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
      throw new BaseException();
    }

    PagedList<Post> recentPosts = getFeedOperations().getPosts();

    return computeImportablePosts(recentPosts);
  }

  FeedOperations getFeedOperations() {
    return facebookConnector.feedOperations();
  }

  List<ImportablePost> computeImportablePosts(PagedList<Post> recentPosts) {
    return recentPosts.stream().map(recentPost -> computeImportablePost(recentPost, contextHolder.getDateFormat()))
        .filter(this::canImportPost).collect(Collectors.toList());
  }

  boolean canImportPost(ImportablePost post) {
    if (PostType.STATUS.equals(post.getType()) && !StringUtils.hasText(post.getDescription())) {
      return false;
    }
    if (newsEntryService.isAlreadyImportedFromFacebook(post.getFacebookId())) {
      return false;
    }
    return true;
  }

  ImportablePost computeImportablePost(Post feed, DateTimeFormatter formatter) {

    return new ImportablePostBuilder().author(computeAuthor(feed)).description(computeDescription(feed))
        .photoUrl(computePhotoUrl(feed)).linkUrl(computeLink(feed)).videoUrl(computeVideoUrl(feed))
        .title(computeTitle(feed)).type(computeType(feed)).facebookId(computeId(feed)).onclick(computeOnclick(feed))
        .creationDate(computeCreatedTime(feed)).objectId(computeObjectId(feed))
        .formattedDate(computeFormattedDate(feed, formatter)).build();
  }

  String computeFormattedDate(Post feed, DateTimeFormatter formatter) {
    return formatter.format(computeCreatedTime(feed));
  }

  String computeObjectId(Post feed) {
    return feed.getObjectId();
  }

  LocalDate computeCreatedTime(Post feed) {
    return feed.getCreatedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  String computeId(Post feed) {
    return feed.getId();
  }

  PostType computeType(Post feed) {
    return feed.getType();
  }

  String computeLink(Post feed) {
    return feed.getLink();
  }

  String computeAuthor(Post feed) {
    return feed.getFrom().getName();
  }

  String computePhotoUrl(Post feed) {
    if (!PostType.PHOTO.equals(computeType(feed))) {
      return "";
    }
    return feed.getPicture();
  }

  String computeVideoUrl(Post feed) {
    String videoUrl = feed.getSource();
    if (!StringUtils.hasText(videoUrl)) {
      return videoUrl;
    }
    videoUrl = makeVideoNotAutoplay(videoUrl);
    return videoUrl;
  }

  String makeVideoNotAutoplay(String videoUrl) {
    return videoUrl.replace("autoplay=1", "autoplay=0");
  }

  String computeOnclick(Post feed) {
    return "toggleImport('" + computeId(feed) + "')";
  }

  String computeTitle(Post feed) {
    String title = feed.getName();
    if (!StringUtils.hasText(title)) {
      title = feed.getCaption();
    }
    if (!StringUtils.hasText(title)) {
      title = "Type " + computeType(feed).name();
    }
    return title;
  }

  String computeDescription(Post feed) {
    String message = feed.getMessage();
    if (!StringUtils.hasText(message)) {
      return feed.getDescription();
    }
    return message;
  }

}
