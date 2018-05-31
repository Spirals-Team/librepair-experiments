package com.cmpl.web.front.ui.media;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

@Controller
@RequestMapping(value = "/public/medias")
public class MediaController {

  private final MediaService mediaService;

  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @GetMapping("/{mediaName:.+}")
  public void serve(@PathVariable("mediaName") String mediaName, HttpServletResponse res)
      throws SQLException, IOException {
    MediaDTO mediaDTO = mediaService.findByName(mediaName);
    if (mediaDTO != null) {
      readMediaContent(mediaName, mediaDTO, res);
      return;
    }

    res.setStatus(HttpStatus.NOT_FOUND.value());
  }

  private void readMediaContent(String mediaName, MediaDTO mediaDTO, HttpServletResponse res) throws IOException {
    if (mediaDTO != null) {
      res.setHeader(HttpHeaders.CONTENT_TYPE, mediaDTO.getContentType());
      res.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "Content-Disposition: inline; filename=\"" + mediaDTO.getName() + "\"");
      StreamUtils.copy(mediaService.download(mediaName), res.getOutputStream());
      return;
    }
  }
}
