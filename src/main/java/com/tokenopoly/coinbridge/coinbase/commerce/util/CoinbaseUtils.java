/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.util;

import com.google.common.io.BaseEncoding;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 * Helper class that provides signature validation that works with Coinbase Commerce's
 * webhook callback functionality.
 */
@Slf4j
@SuppressWarnings("WeakerAccess")
public class CoinbaseUtils {

    private static final String SIGNING_ALGORITHM = "HmacSHA256";
    private static final Charset UTF8 = Charset.forName("UTF8");

    private final Mac sha256Hmac;
    private final BaseEncoding signatureEncoding;

    /**
     * Default constructor.
     * <p>This method takes a single parameter, the secret that is shared with the Coinbase Commerce
     * system and is used to sign all requests from that system, thereby guaranteeing the authenticity
     * of their source.</p>
     * <p>Never share your secret more broadly.  To that end, this method does not retain the value but
     * only uses it to initializd the {@link Mac} instance that performs the signature computation.
     * </p>
     *
     * @param sharedSecret the shared secret configured via your Coinbase Commerce console.
     * @throws NoSuchAlgorithmException if no Mac instance for the "HmacSHA256" algorithm exists.
     * @throws InvalidKeyException if the {@code sharedSecret} can't initilize the Mac algorithm instance.
     */
    public CoinbaseUtils(final String sharedSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        final SecretKeySpec secretKey = new SecretKeySpec(sharedSecret.getBytes(UTF8), SIGNING_ALGORITHM);
        this.sha256Hmac = Mac.getInstance(SIGNING_ALGORITHM);
        sha256Hmac.init(secretKey);
        this.signatureEncoding = BaseEncoding.base16().lowerCase();
    }

    /**
     * Compute the HmacSHA256 signature of the given message.
     * @param message a message string
     * @return the HmacSHA256 signature of the {@code message} string.
     */
    public String computeHmacSha256Signature(final String message) {
        final String nullSafeMessage = (message == null) ? "" : message;
        return signatureEncoding.encode(sha256Hmac.doFinal(nullSafeMessage.getBytes(UTF8)));
    }

    /**
     * Validate the signature that was provided for a message.
     *
     * @param signature the signature to check (validate)
     * @param message the message that purportedly has the provided signature
     * @param withLogging if {@code true} then logging messages will be produced along the way.
     * @return {@code true} of the provided {@code signature} is the HmacSHA256 signature of
     * the {@code message}, {@code false} otherwise.
     */
    public boolean isValidSignature(final String signature, final String message, boolean withLogging) {
        if (signature == null) {
            if (null == message) {
                if (withLogging) {
                    log.debug("Header signature and message are both null.");
                }
                return true;
            } else {
                if (withLogging) {
                    log.warn("Header signature is null but message is {}", message);
                }
                return false;
            }
        } else {
            final String computedSignature = computeHmacSha256Signature(message);

            if (computedSignature.equals(signature)) {
                if (withLogging) {
                    log.debug("  Header signature: {}", signature);
                    log.debug("Computed signature: {}", computedSignature);
                }
                return true;
            } else {
                if (withLogging) {
                    log.warn("  Header signature: {}", signature);
                    log.warn("Computed signature: {}", computedSignature);
                }
                return false;
            }
        }
    }

    /**
     * Convenience method.  Invokes {@link #isValidSignature(String, String, boolean)} passing
     * in {@code false} for the final parameter.
     *
     * @param signature the signature to check (validate)
     * @param message the message that purportedly has the provided signature
     * @return {@code true} of the provided {@code signature} is the HmacSHA256 signature of
     * the {@code message}, {@code false} otherwise.
     */
    public boolean isValidSignature(final String signature, final String message) {
        return this.isValidSignature(signature, message, false);
    }

}
