package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = DlmovieApplication.class)
class CommentServiceIntegrationTest extends Specification{

    @Autowired
    private CommentService commentService

    void setup() {

    }

    def "test"() {
        given:
            commentService = new CommentService();
        when:
            System.out.println(commentService.commentRepository);
        then:
            commentService != null
    }
}
