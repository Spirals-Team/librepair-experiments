package br.com.igorventurelli.mercadolivre.api.parser;

import br.com.igorventurelli.mercadolivre.domain.UserInformation;
import br.com.igorventurelli.mercadolivre.domain.enumeration.AppScope;
import br.com.igorventurelli.mercadolivre.domain.enumeration.TokenType;
import br.com.igorventurelli.mercadolivre.exception.CodeNotValidException;
import com.google.gson.JsonObject;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationResponseParser extends JsonParser<UserInformation> {

    @Override
    protected UserInformation parseSingleImpl(final JsonObject jsonObject, final int httpStatus) {

        if(httpStatus == HttpStatus.SC_BAD_REQUEST) {
            final String message = jsonObject.get("message").getAsString();
            final String error = jsonObject.get("error").getAsString();

            throw new CodeNotValidException(String.format("Error: %s - Message from API: %s", error, message));
        }

        final String accessToken = jsonObject.get("access_token").getAsString();
        final String refreshToken = jsonObject.get("refresh_token").getAsString();
        final String tokenTypeString = jsonObject.get("token_type").getAsString();
        final TokenType tokenType = TokenType.getFromString(tokenTypeString);
        final Long expiresIn = jsonObject.get("expires_in").getAsLong();
        final String[] scopesString = jsonObject.get("scope").getAsString().split(" ");
        List<AppScope> scopes = new ArrayList<>();
        for(final String scope : scopesString) {
            scopes.add(AppScope.getFromString(scope));
        }

        return new UserInformation(refreshToken, scopes, tokenType, accessToken, expiresIn);
    }
}
