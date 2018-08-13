package io.jsonwebtoken.security;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface AsymmetricEncryptionAlgorithm extends EncryptionAlgorithm<PrivateKey, PublicKey> {
}
