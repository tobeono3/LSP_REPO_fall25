package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Basic password generation algorithm.
 * Uses {@link java.util.Random} and produces a password that
 * consists of digits only (0–9).
 */
public class BasicPasswordAlgorithm implements PasswordAlgorithm {

    /**
     * Source characters for this algorithm: digits 0–9.
     */
    private static final String DIGITS = "0123456789";

    private final Random random = new Random();

    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be positive.");
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(DIGITS.length());
            builder.append(DIGITS.charAt(index));
        }
        return builder.toString();
    }
}
