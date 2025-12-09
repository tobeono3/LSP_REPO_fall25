package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Enhanced password generation algorithm.
 * Uses {@link java.security.SecureRandom} and produces a password that
 * may contain uppercase letters, lowercase letters, and digits.
 */
public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {

    /**
     * Allowed characters: A–Z, a–z, 0–9.
     */
    private static final String ALLOWED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789";

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be positive.");
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ALLOWED.length());
            builder.append(ALLOWED.charAt(index));
        }
        return builder.toString();
    }
}
