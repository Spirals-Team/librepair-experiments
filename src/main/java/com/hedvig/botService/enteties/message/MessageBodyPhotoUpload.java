package com.hedvig.botService.enteties.message;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("photo_upload")
@ToString
public class MessageBodyPhotoUpload extends MessageBody {

  public String url;
  private static Logger log = LoggerFactory.getLogger(MessageBodyPhotoUpload.class);

  public MessageBodyPhotoUpload(String content, String url) {
    super(content);
    this.url = url;
  }

  MessageBodyPhotoUpload() {
    log.info("Instansiating MessageBodyPhotoUpload");
  }
}
