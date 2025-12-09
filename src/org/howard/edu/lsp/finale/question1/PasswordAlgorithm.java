package org.howard.edu.lsp.finale.question1;

/**
 * Strategy interface for password generation algorithms.
 * Each algorithm implementation is responsible for producing
 * a password string of the requested length according to its
 * own rules.
 */
public interface PasswordAlgorithm {

    /**
     * Generate a password of the given length.
     *
     * @param length the desired length of the password; must be positive
     * @return a generated password string
     * @throws IllegalArgumentException if length is less than or equal to zero
     */
    String generate(int length);
}
