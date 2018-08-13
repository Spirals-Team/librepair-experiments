package io.jsonwebtoken.security;

import io.jsonwebtoken.JweHeader;

public interface EncryptionAlgorithmLocator {

    EncryptionAlgorithm getEncryptionAlgorithm(JweHeader jweHeader);
}
