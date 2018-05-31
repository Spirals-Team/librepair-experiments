package com.cmpl.web.core.news;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewsImageServiceImplTest {

  @Mock
  private NewsImageRepository repository;

  @InjectMocks
  @Spy
  private NewsImageServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsImageDTO dto = new NewsImageDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsImage.class));
    NewsImage result = service.toEntity(dto);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsImage entity = new NewsImage();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(entity), BDDMockito.any(NewsImageDTO.class));
    NewsImageDTO result = service.toDTO(entity);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

}
