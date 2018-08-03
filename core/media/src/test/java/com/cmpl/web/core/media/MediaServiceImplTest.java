package com.cmpl.web.core.media;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Media;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceImplTest {

  @Mock
  private MediaMapper mapper;

  @Spy
  @InjectMocks
  private MediaServiceImpl mediaService;

  @Mock
  private ContextHolder contextHolder;
  @Mock
  private FileService fileService;
  @Mock
  private MediaDAO mediaDAO;

  @Test
  public void testFindByName() throws Exception {
    MediaDTO result = MediaDTOBuilder.create().build();

    BDDMockito.doReturn(result).when(mapper).toDTO(BDDMockito.any(Media.class));
    BDDMockito.given(mediaDAO.findByName(BDDMockito.anyString())).willReturn(MediaBuilder.create().build());

    Assert.assertEquals(result, mediaService.findByName("someName"));
  }

  @Test
  public void testDownload() throws Exception {

    String mediaName = "someMediaName";
    String path = "src/test/resources/img/base64Image.txt";
    FileInputStream is = new FileInputStream(new File(path));
    BDDMockito.given(fileService.read(BDDMockito.anyString())).willReturn(is);

    Assert.assertEquals(is, mediaService.download(mediaName));
  }

  @Test
  public void testUpload() throws Exception {

    MultipartFile multiPartFile = BDDMockito.mock(MultipartFile.class);
    BDDMockito.given(multiPartFile.getOriginalFilename()).willReturn("test.pdf");
    BDDMockito.given(multiPartFile.getContentType()).willReturn("application/pdf");
    BDDMockito.given(multiPartFile.getSize()).willReturn(1l);
    BDDMockito.given(multiPartFile.getBytes()).willReturn(new byte[]{});

    BDDMockito.doNothing().when(fileService).saveMediaOnSystem(BDDMockito.anyString(), BDDMockito.any(byte[].class));

    MediaDTO mediaToCreate = MediaDTOBuilder.create().build();
    BDDMockito.doReturn(mediaToCreate).when(mediaService).createEntity(BDDMockito.any(MediaDTO.class));

    Assert.assertEquals(mediaToCreate, mediaService.upload(multiPartFile));
  }

}
