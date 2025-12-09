package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Password generation algorithm that produces a password containing
 * letters only (A–Z, a–z).
 */
public class LettersPasswordAlgorithm implements PasswordAlgorithm {

    /**
     * Allowed characters: A–Z, a–z only.
     */
    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz";

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be positive.");
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(LETTERS.length());
            builder.append(LETTERS.charAt(index));
        }
        return builder.toString();
    }
}
