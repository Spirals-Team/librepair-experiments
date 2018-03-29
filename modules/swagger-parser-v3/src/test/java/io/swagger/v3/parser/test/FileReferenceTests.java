package io.swagger.v3.parser.test;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import java.util.Arrays;
import java.util.Map;


public class FileReferenceTests {
    @Test
    public void testIssue306() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-306.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();

        assertTrue(swagger.getComponents().getSchemas().size() == 3);
        // resolved from `$ref: './book.yaml'`
        assertNotNull(swagger.getComponents().getSchemas().get("Inventory"));
        // resolved from `$ref: 'book.yaml'`
        assertNotNull(swagger.getComponents().getSchemas().get("Orders"));

        // copied from `./book.yaml`
        assertNotNull(swagger.getComponents().getSchemas().get("book"));
    }

    @Test
    public void testIssue308() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-308.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();

        assertTrue(swagger.getComponents().getSchemas().size() == 2);
        assertTrue(swagger.getComponents().getSchemas().get("Paging").getProperties().size() == 1);
    }

    @Test
    public void testIssue310() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-310.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();

        assertTrue(swagger.getComponents().getSchemas().size() == 2);
        assertTrue(swagger.getComponents().getSchemas().get("Paging").getProperties().size() == 1);
    }

    @Test
    public void testIssue312() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-312.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/events"));
        PathItem path = swagger.getPaths().get("/events");
        assertNotNull(path.getGet());

        Operation get = path.getGet();
        assertEquals(get.getOperationId(), "getEvents");
        assertTrue(swagger.getComponents().getSchemas().size() == 2);
        assertTrue(swagger.getComponents().getSchemas().get("Paging").getProperties().size() == 1);
    }

    @Test
    public void testIssue314() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-314.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/events"));
        PathItem path = swagger.getPaths().get("/events");
        assertNotNull(path.getGet());

        Operation get = path.getGet();
        assertEquals(get.getOperationId(), "getEvents");
        assertTrue(swagger.getComponents().getSchemas().size() == 3);
        assertTrue(swagger.getComponents().getSchemas().get("Foobar").getProperties().size() == 1);
        assertTrue(swagger.getComponents().getSchemas().get("StatusResponse").getProperties().size() == 1);
        assertTrue(swagger.getComponents().getSchemas().get("Paging").getProperties().size() == 1);
    }

    @Test
    public void testIssue316() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-316.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/events"));
        PathItem path = swagger.getPaths().get("/events");
        assertNotNull(path.getGet());
        Operation get = path.getGet();
        assertEquals(get.getOperationId(), "getEvents");
        assertTrue(swagger.getComponents().getSchemas().size() == 3);
        assertTrue(swagger.getComponents().getSchemas().get("Foobar").getProperties().size() == 1);
        assertTrue(swagger.getComponents().getSchemas().get("StatusResponse").getProperties().size() == 1);
        assertTrue(swagger.getComponents().getSchemas().get("Paging2").getProperties().size() == 2);
        Schema model = swagger.getComponents().getSchemas().get("Paging2");

        Schema property = (Schema) model.getProperties().get("foobar");
        assertTrue(property.get$ref() != null);
        assertEquals(property.get$ref(), "#/components/schemas/Foobar");
    }

    @Test
    public void testIssue323() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-323.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/events"));

        assertNotNull(swagger.getComponents().getSchemas().get("StatusResponse"));
        assertNotNull(swagger.getComponents().getSchemas().get("Paging"));
        assertNotNull(swagger.getComponents().getSchemas().get("Foobar"));
    }

    @Test
    public void testIssue289() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/issue-289.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/foo").getGet());
    }

    @Test
    public void testIssue336() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-336.json", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths());
    }

    @Test
    public void testIssue340() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-340.json", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertFalse(swagger.getComponents().getSchemas().get("BarData").get$ref() != null);
    }

    @Test
    public void testIssue304() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-304.json", null, options);
        assertNotNull(result.getOpenAPI().getComponents().getSchemas());
    }

    @Test
    public void testAllOfFlatAndNested() {
        for (String path : Arrays.asList("./src/test/resources/allOf-properties-ext-ref/models/swagger2.yaml",
                "./src/test/resources/allOf-properties-ext-ref/swagger1.yaml")) {
            ParseOptions options = new ParseOptions();
            options.setResolve(true);
            OpenAPI swagger = new OpenAPIV3Parser().readLocation(path,null, options).getOpenAPI();
            assertEquals(3, swagger.getComponents().getSchemas().size());
            ComposedSchema composedModel = (ComposedSchema)swagger.getComponents().getSchemas().get("record");
            assertEquals(composedModel.getAllOf().get(0), "pet");
            Map<String, Schema> props = composedModel.getAllOf().get(0).getProperties();
            assertEquals( props.get("mother").get$ref(), "pet");
            assertEquals( ((ArraySchema)props.get("siblings")).getItems().get$ref(), "pet");
        }
    }

    @Test
    public void testIssue421() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/nested-file-references/issue-421.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger.getPaths().get("/pet/{petId}"));
        assertNotNull(swagger.getPaths().get("/pet/{petId}").getGet());
        assertNotNull(swagger.getPaths().get("/pet/{petId}").getGet().getParameters());
        assertTrue(swagger.getPaths().get("/pet/{petId}").getGet().getParameters().size() == 1);
        assertTrue(swagger.getPaths().get("/pet/{petId}").getGet().getParameters().get(0).getName().equals("petId"));
        assertTrue(swagger.getComponents().getSchemas().get("Pet") instanceof Schema);
        assertTrue(swagger.getComponents().getSchemas().get("Pet").getProperties().size() == 6);

        assertNotNull(swagger.getPaths().get("/pet/{petId}").getPost());
        assertNotNull(swagger.getPaths().get("/pet/{petId}").getPost().getParameters());
        assertTrue(swagger.getPaths().get("/pet/{petId}").getPost().getParameters().size() == 3);
        assertTrue(swagger.getPaths().get("/pet/{petId}").getPost().getParameters().get(1).get$ref() != null);
        //assertTrue((swagger.getPaths().get("/pet/{petId}").getPost().getParameters().get(1).get$ref(). == RefFormat.INTERNAL);
        assertTrue(swagger.getPaths().get("/pet/{petId}").getPost().getParameters().get(1).get$ref().equals("name"));

        assertNotNull(swagger.getPaths().get("/store/order"));
        assertNotNull(swagger.getPaths().get("/store/order").getPost());
        assertNotNull(swagger.getPaths().get("/store/order").getPost().getParameters());
        assertTrue(swagger.getPaths().get("/store/order").getPost().getParameters().size() == 1);
        assertTrue(swagger.getPaths().get("/store/order").getPost().getRequestBody()instanceof RequestBody);
        assertNotNull(swagger.getPaths().get("/store/order").getPost().getParameters().get(0).getSchema());
        assertTrue(swagger.getPaths().get("/store/order").getPost().getParameters().get(0).getSchema().get$ref() != null);
        assertTrue(swagger.getPaths().get("/store/order").getPost().getParameters().get(0).getSchema().get$ref().equals("Order"));

        assertTrue(swagger.getComponents().getSchemas().get("Order") instanceof Schema);
        assertTrue(swagger.getComponents().getSchemas().get("Order").getProperties().size() == 6);
    }

    @Test
    public void testRelativeRefIssue421() {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("./src/test/resources/main.yaml", null, options);
        assertNotNull(result.getOpenAPI());

        OpenAPI swagger = result.getOpenAPI();
        assertNotNull(swagger);
        assertNotNull(swagger.getPaths().get("pets"));
        assertNotNull(swagger.getPaths().get("pets").getGet());
        assertNotNull(swagger.getPaths().get("pets").getGet().getResponses());
        assertNotNull(swagger.getPaths().get("pets").getGet().getResponses().get("200"));
        assertNotNull(swagger.getPaths().get("pets").getGet().getResponses().get("200").getContent().get("").getSchema());
        assertTrue(swagger.getPaths().get("pets").getGet().getResponses().get("200").getContent().get("").getSchema().get$ref() != null);

        assertEquals(swagger.getPaths().get("pets").getGet().getResponses().get("200").getContent().get("").getSchema().get$ref(), "#/components/schemas/Pet");

        assertTrue(swagger.getComponents().getSchemas().get("Pet") instanceof Schema);
        assertTrue(swagger.getComponents().getSchemas().get("Pet").getProperties().size() == 2);
    }
}
