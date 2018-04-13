package groovy.m2dl.ivvq.sortircesoir.helpers

import com.auth0.jwt.interfaces.DecodedJWT
import m2dl.ivvq.sortircesoir.helpers.AuthentHelper
import spock.lang.Specification

class AuthentHelperTest extends Specification {

    AuthentHelper authentHelper;
    Long userId = 1;

    def setup() {
         authentHelper = new AuthentHelper();
    }

    def "create a token and verify it"() {
        when: "generating a new token"
        String token = authentHelper.createJWT(userId)

        then: "token is valid"
        assert authentHelper.verifyJWT(token) != null
    }


    def "create a token with user id and then get user id from token"() {
        when: "generating a new token"
        String token = authentHelper.createJWT(userId)

        then: "token and userId are valids"
        DecodedJWT decodedJWT = authentHelper.verifyJWT(token)
        assert decodedJWT != null
        assert userId == decodedJWT.getClaim("userId").asLong()
    }

    def "create a token with null user id fails"() {
        when: "generating a new token with null user id"
        String token = authentHelper.createJWT(null)

        then: "token is not generated, it is null"
        assert token == null

        expect: "decodedJWT returns null"
        assert authentHelper.verifyJWT(token) == null
    }
}
