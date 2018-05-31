package com.cmpl.web.core.page;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.cmpl.web.core.file.FileService;

@RunWith(MockitoJUnitRunner.class)
public class PageServiceImplTest {

  @Mock
  private PageRepository pageRepository;

  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private PageServiceImpl pageService;

  @Test
  public void testCreateEntity() throws Exception {
    PageDTO dtoToCreate = PageDTOBuilder.create().body("someBody").footer("someFooter").header("someHeader")
        .name("someName").build();
    Page entityToCreate = PageBuilder.create().build();

    BDDMockito.doReturn(dtoToCreate).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.doReturn(entityToCreate).when(pageService).toEntity(BDDMockito.any(PageDTO.class));
    BDDMockito.given(pageRepository.save(BDDMockito.any(Page.class))).willReturn(entityToCreate);
    BDDMockito.doNothing().when(fileService).saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    pageService.createEntity(dtoToCreate, Locale.FRANCE.getLanguage());

    BDDMockito.verify(fileService, BDDMockito.times(3))
        .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

  }

  @Test
  public void testUpdateEntity() throws Exception {

    PageDTO dtoToUpdate = PageDTOBuilder.create().body("someBody").footer("someFooter").header("someHeader")
        .name("someName").build();
    Page entityToUpdate = PageBuilder.create().build();

    BDDMockito.doReturn(dtoToUpdate).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.doReturn(entityToUpdate).when(pageService).toEntity(BDDMockito.any(PageDTO.class));
    BDDMockito.given(pageRepository.save(BDDMockito.any(Page.class))).willReturn(entityToUpdate);
    BDDMockito.doNothing().when(fileService).saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    pageService.updateEntity(dtoToUpdate, Locale.FRANCE.getLanguage());

    BDDMockito.verify(fileService, BDDMockito.times(3))
        .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());
  }

  @Test
  public void testGetEntity() throws Exception {

    PageDTO dtoToFind = PageDTOBuilder.create().name("someName").build();
    Optional<Page> entityToFind = Optional.of(PageBuilder.create().build());
    BDDMockito.doReturn(dtoToFind).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.given(pageRepository.findById(BDDMockito.anyLong())).willReturn(entityToFind);

    String content = "someContent";
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn(content);

    PageDTO result = pageService.getEntity(123456789l, Locale.FRANCE.getLanguage());
    Assert.assertEquals(content, result.getBody());
    Assert.assertEquals(content, result.getHeader());
    Assert.assertEquals(content, result.getFooter());

    BDDMockito.verify(fileService, BDDMockito.times(4)).readFileContentFromSystem(BDDMockito.anyString());

  }

  @Test
  public void testToDTO() throws Exception {
    Page entity = PageBuilder.create().build();

    BDDMockito.doNothing().when(pageService).fillObject(BDDMockito.any(Page.class), BDDMockito.any(PageDTO.class));
    pageService.toDTO(entity);

    BDDMockito.verify(pageService, BDDMockito.times(1)).fillObject(BDDMockito.any(Page.class),
        BDDMockito.any(PageDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    PageDTO dto = PageDTOBuilder.create().build();

    BDDMockito.doNothing().when(pageService).fillObject(BDDMockito.any(PageDTO.class), BDDMockito.any(Page.class));
    pageService.toEntity(dto);

    BDDMockito.verify(pageService, BDDMockito.times(1)).fillObject(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Page.class));
  }

  @Test
  public void testGetPageByName_Found() throws Exception {
    PageDTO result = PageDTOBuilder.create().id(123456789l).build();

    Page page = PageBuilder.create().build();

    BDDMockito.doReturn(result).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.given(pageRepository.findByName(BDDMockito.anyString())).willReturn(page);

    Assert.assertEquals(result, pageService.getPageByName("someName", Locale.FRANCE.getLanguage()));
  }

  @Test
  public void testGetPageByName_Not_Found() throws Exception {

    BDDMockito.given(pageRepository.findByName(BDDMockito.anyString())).willReturn(null);

    Assert.assertNull(pageService.getPageByName("someName", Locale.FRANCE.getLanguage()).getId());
  }

  @Test
  public void testGetPages() throws Exception {
    PageDTO result = PageDTOBuilder.create().id(123456789l).build();
    Page page = PageBuilder.create().build();

    BDDMockito.given(pageRepository.findAll(BDDMockito.any(Sort.class))).willReturn(Arrays.asList(page));
    BDDMockito.doReturn(Arrays.asList(result)).when(pageService).toListDTO(BDDMockito.anyList());

    Assert.assertEquals(result, pageService.getPages().get(0));

  }

  @Test
  public void testToListDTO() throws Exception {
    PageDTO result = PageDTOBuilder.create().id(123456789l).build();
    BDDMockito.doReturn(result).when(pageService).toDTO(BDDMockito.any(Page.class));

    Page page = PageBuilder.create().build();
    Assert.assertEquals(result, pageService.toListDTO(Arrays.asList(page)).get(0));

  }

}
