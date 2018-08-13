package io.jsonwebtoken.security;

import javax.crypto.SecretKey;

public interface SymmetricEncryptionAlgorithm extends EncryptionAlgorithm<SecretKey, SecretKey> {
}
