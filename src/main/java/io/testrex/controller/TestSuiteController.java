package io.testrex.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.testrex.assembler.TestSuiteResourceAssembler;
import io.testrex.repository.TestSuiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.testrex.model.TestSuite;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/testsuites")
public class TestSuiteController {
  private final TestSuiteRepository testSuiteRepository;
  private final TestSuiteResourceAssembler suiteAssembler= new TestSuiteResourceAssembler();

  public TestSuiteController(TestSuiteRepository testSuiteRepository) {
    this.testSuiteRepository = testSuiteRepository;
  }

  Logger LOG = LoggerFactory.getLogger(TestSuiteController.class);

  @GetMapping(path = "/{id}")
  public Resource<TestSuite> findById(@PathVariable Long id) {
    return suiteAssembler.toResource(testSuiteRepository.getOne(id));
  }

  @GetMapping(path = "/")
  public Resources<Resource<TestSuite>> findAll() {
    List<Resource<TestSuite>> resources = testSuiteRepository.findAll().stream().map(
        suiteAssembler::toResource).collect(Collectors.toList());
    return new Resources<>(resources,
            linkTo(methodOn(TestSuiteController.class).findAll()).withSelfRel());
  }

  @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<?> create(@RequestBody TestSuite testSuite) {
    LOG.info("Creating TestSuite: ", testSuite.toString());
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(testSuite.getId()).toUri();
    return ResponseEntity.created(location).body(testSuiteRepository.save(testSuite));
  }

  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id) {
    testSuiteRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> put(@PathVariable Long id, @RequestBody TestSuite newTestSuite) {
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    TestSuite requested = testSuiteRepository.getOne(id);
    if (requested != null) {
      requested = newTestSuite;
      return ResponseEntity.created(location).body(testSuiteRepository.save(requested));
    } else {
      newTestSuite.setId(id);
      return ResponseEntity.created(location).body(testSuiteRepository.save(newTestSuite));
    }
  }
}
