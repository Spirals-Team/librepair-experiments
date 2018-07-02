package br.com.igorventurelli.mercadolivre.domain;

import lombok.Value;

@Value
public final class AppInformation {

    private final Long clientId;
    private final String clientSecret;
}
