package io.testrex.assembler;

import io.testrex.controller.TestSuiteController;
import io.testrex.model.TestSuite;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TestSuiteResourceAssembler implements ResourceAssembler<TestSuite, Resource<TestSuite>> {
    @Override
    public Resource<TestSuite> toResource(TestSuite testSuite) {
        return new Resource<>(testSuite,
                linkTo(methodOn(TestSuiteController.class).findById(testSuite.getId())).withSelfRel(),
                linkTo(methodOn(TestSuiteController.class).findAll()).withRel("testsuites")
        );
    }
}
