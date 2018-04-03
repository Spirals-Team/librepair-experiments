package com.d4dl.hellofib.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * A simple <a href="https://spring.io/understanding/HATEOAS">HATEOS</a>
 * REST controller the retrieves localized hello world messages.  The messages can be found
 * in the resources directory with the key d4dl.helloworld.  In order to retrieve localized versions of
 * the message the <a target="_blank"
 *    href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language">Accept-Language</a> header
 *    should be set.  It defaults to English.
 */
@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Default constructor
     */
    public HelloWorldController() {
    }

    /**
     * Publishes a simple REST endpoing that returns the message HelloWorld in a language specified in the
     * request header (As long as its English, Spanish or French)
     * @param request the request from which the <a target="_blank"
     *    href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language">Accept-Language</a> header
     * @return a <a href="https://spring.io/understanding/HATEOAS">HATEOS</a> compatible message that will be
     * serialized into a HATEOS JSON object.
     */
    @GetMapping("/helloWorld")
    public HttpEntity<Message> helloWorld(HttpServletRequest request) {
        String hello = messageSource.getMessage("d4dl.helloworld", new Object[]{}, localeResolver.resolveLocale(request));
        Message message = new Message(hello);
        message.add(linkTo(HelloWorldController.class).withSelfRel());

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
