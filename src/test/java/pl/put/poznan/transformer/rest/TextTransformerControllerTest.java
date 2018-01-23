package pl.put.poznan.transformer.rest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.put.poznan.transformer.logic.transform.TransformTypes;
import pl.put.poznan.transformer.rest.dto.ListResponse;
import pl.put.poznan.transformer.rest.dto.StringResponse;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TextTransformerControllerTest {

    private final TextTransformerController controller = new TextTransformerController();

    @Test
    public void shouldReturnValidResponseWithTransformationList() {
        ListResponse stringResponse = controller.getTransforms();
        assertEquals(stringResponse.getCode(), HttpStatus.OK.value());
        assertEquals(controller.getTransforms().getData(), Arrays.toString(TransformTypes.values()));
    }
}