package m2dl.ivvq.sortircesoir.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public final class AuthentHelper {

    private static final String secret = "H1z_ez5~d-fe!8efef2";

    public static String createJWT(Long userId) {
        if (userId == null)
            return null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withClaim("userId", userId)
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException | UnsupportedEncodingException  exception){
            return null;
        }
    }

    public static DecodedJWT verifyJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (UnsupportedEncodingException | JWTVerificationException | NullPointerException e) {
            return null;
        }
    }
}
