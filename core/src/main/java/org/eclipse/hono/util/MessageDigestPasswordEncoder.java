/*******************************************************************************
 * Copyright (c) 2018 Red Hat Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat - initial creation
 *******************************************************************************/
package org.eclipse.hono.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Implementation of {@link PasswordEncoder} that properly uses salt.
 */
public class MessageDigestPasswordEncoder implements PasswordEncoder {

    private final MessageDigest messageDigest;

    /**
     * Create message digest password encoder with specified hash function.
     *
     * @param hashFunction - hash function to be used
     */
    public MessageDigestPasswordEncoder(final String hashFunction) {
        try {
            messageDigest = MessageDigest.getInstance(hashFunction);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such hash function: " + e);
        }
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        final EncodedPassword password = new EncodedPassword(rawPassword.toString());

        if (password.salt != null) {
            messageDigest.update(password.salt.getBytes());
        }

        return Base64.getEncoder().encodeToString(messageDigest.digest(password.password.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        final EncodedPassword password = new EncodedPassword(encodedPassword);

        if (password.salt != null) {
            messageDigest.update(password.salt.getBytes());
        }
        final byte[] hashedPassword = messageDigest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        return Arrays.equals(hashedPassword, Base64.getDecoder().decode(password.password));
    }

    /**
     * Value object class for parsing passwords in {salt}password format.
     */
    final class EncodedPassword {
        private static final String PREFIX = "{";
        private static final String SUFFIX = "}";

        String salt;
        String password;

        EncodedPassword(final String encodedPassword) {
            final int start = encodedPassword.indexOf(PREFIX);
            password = encodedPassword;
            if (start == 0) {
                final int end = encodedPassword.indexOf(SUFFIX, start);
                if (end > 0) {
                    salt = encodedPassword.substring(start + 1, end);
                    password = encodedPassword.substring(end + 1);
                }
            }
        }
    }

}
