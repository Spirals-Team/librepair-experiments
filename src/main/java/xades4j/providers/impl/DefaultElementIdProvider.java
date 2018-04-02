package xades4j.providers.impl;

import xades4j.providers.ElementIdProvider;

import java.util.UUID;

/**
 * @author Artem R. Romanenko
 * @version 18/01/2018
 */
public class DefaultElementIdProvider implements ElementIdProvider {
    @Override
    public String generateSignatureId() {
        return String.format("xmldsig-%s", UUID.randomUUID());
    }

    @Override
    public String generateSignedPropsId(String signatureId) {
        return String.format("%s-signedprops", signatureId);
    }
}
