package com.cmpl.web.core.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

/**
 * Implementation de l'interface de validation des modifications de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryRequestValidatorImpl extends BaseValidator implements NewsEntryRequestValidator {

  public NewsEntryRequestValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(NewsEntryRequest request, Locale locale) {

    List<ErrorCause> causes = isNewsEntryValid(request, locale);
    causes.addAll(isContentValid(request.getContent(), locale));
    causes.addAll(isNewsImageValid(request.getImage(), locale));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(NewsEntryRequest request, String newsEntryId, Locale locale) {

    List<ErrorCause> causes = isIdValid(newsEntryId, locale);
    causes.addAll(isNewsEntryValid(request, locale));
    causes.addAll(isContentValid(request.getContent(), locale));
    causes.addAll(isNewsImageValid(request.getImage(), locale));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String newsEntryId, Locale locale) {
    List<ErrorCause> causes = isIdValid(newsEntryId, locale);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateGet(String newsEntryId, Locale locale) {
    List<ErrorCause> causes = isIdValid(newsEntryId, locale);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  List<ErrorCause> isIdValid(String id, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(id)) {

      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_ID, locale);
      causes.add(cause);
    }
    return causes;
  }

  List<ErrorCause> isContentValid(NewsContentRequest content, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (content == null) {
      return causes;
    }
    if (!isNewsContentValid(content)) {
      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_CONTENT, locale);
      causes.add(cause);
    }
    return causes;
  }

  List<ErrorCause> isNewsEntryValid(NewsEntryRequest entry, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (entry == null) {
      return causes;
    }

    if (!isStringValid(entry.getTitle())) {
      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_TITLE, locale);
      causes.add(cause);

    }
    if (!isStringValid(entry.getAuthor())) {
      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_AUTHOR, locale);
      causes.add(cause);
    }
    return causes;
  }

  List<ErrorCause> isNewsImageValid(NewsImageRequest imageRequest, Locale locale) {

    List<ErrorCause> causes = new ArrayList<>();
    if (imageRequest == null) {
      return causes;
    }

    if (!isStringValid(imageRequest.getAlt())) {
      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_ALT, locale);
      causes.add(cause);

    }
    if (!isStringValid(imageRequest.getLegend())) {
      ErrorCause cause = computeCause(ERROR_CAUSE.EMPTY_NEWS_LEGEND, locale);
      causes.add(cause);
    }

    return causes;
  }

  boolean isNewsContentValid(NewsContentRequest content) {
    return isStringValid(content.getContent());
  }

}
