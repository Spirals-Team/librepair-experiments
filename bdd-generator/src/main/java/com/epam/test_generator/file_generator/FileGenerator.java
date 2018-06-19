package com.epam.test_generator.file_generator;

import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This class is used to create information about suit with all test cases according to specified template.
 * For text generation FreeMaker API is used. {@link Configuration} object is used for configure information about
 * template that is used for text creation and {@link Template} object stores already parsed template that is ready
 * for work.
 *
 */
@Component
public class FileGenerator {

    @Autowired
    private Configuration configuration;

    public String generate(SuitDTO suit, List<CaseDTO> cases) throws IOException {
        Objects.requireNonNull(suit, "suit is null");
        Objects.requireNonNull(cases, "cases list is null");
        List<CaseDTO> casesWithSteps = cases.stream()
            .filter(c -> c.getSteps() != null)
            .filter(c -> !c.getSteps().isEmpty())
            .collect(Collectors.toList());
        suit.setCases(casesWithSteps);
        Map<String, Object> input = Collections.singletonMap("suit", suit);

        Template template = configuration.getTemplate("featureFileTemplate.ftl");

        try (StringWriter stringWriter = new StringWriter()) {
            template.process(input, stringWriter);
            stringWriter.flush();
            return stringWriter.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
