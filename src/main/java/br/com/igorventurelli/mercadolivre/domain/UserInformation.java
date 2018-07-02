package br.com.igorventurelli.mercadolivre.domain;

import br.com.igorventurelli.mercadolivre.domain.enumeration.AppScope;
import br.com.igorventurelli.mercadolivre.domain.enumeration.TokenType;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Getter
public final class UserInformation {

    @Setter
    private String accessToken;
    private final String refreshToken;
    @Setter
    private Long expiresIn;
    private final List<AppScope> scopes;
    private final TokenType tokenType;

    public UserInformation(final String refreshToken, final List<AppScope> scopes, final TokenType tokenType) {
        this.refreshToken = refreshToken;
        this.scopes = scopes;
        this.tokenType = tokenType;
    }

    public UserInformation(final String refreshToken, final List<AppScope> scopes, final TokenType tokenType,
                           final String accessToken, final Long expiresIn) {
        this(refreshToken, scopes, tokenType);
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

}
