package com.cmpl.web.core.widget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.Widget;

@RunWith(MockitoJUnitRunner.class)
public class WidgetMapperTest {

  @Spy
  private WidgetMapper mapper;

  @Test
  public void testToEntity() {

    WidgetDTO dto = WidgetDTOBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(WidgetDTO.class), BDDMockito.any(Widget.class));
    mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(WidgetDTO.class),
        BDDMockito.any(Widget.class));
  }

  @Test
  public void testToDTO() {
    Widget entity = WidgetBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Widget.class), BDDMockito.any(WidgetDTO.class));
    mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(Widget.class),
        BDDMockito.any(WidgetDTO.class));
  }
}
