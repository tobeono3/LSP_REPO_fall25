package org.howard.edu.lsp.finale.question1;

/**
 * Service responsible for generating passwords using different algorithms.
 * <p>
 * This class is implemented as a singleton and exposes methods for
 * selecting the password-generation algorithm at run time and for
 * generating passwords of a requested length.
 */
public class PasswordGeneratorService {

    /**
     * The single shared instance of the service.
     */
    private static final PasswordGeneratorService INSTANCE = new PasswordGeneratorService();

    /**
     * Current password-generation strategy.
     */
    private PasswordAlgorithm algorithm;

    /**
     * Private constructor to enforce singleton behavior.
     */
    private PasswordGeneratorService() {
        // Intentionally empty; algorithm is selected via setAlgorithm(String).
    }

    /**
     * ===================== DESIGN PATTERN DOCUMENTATION =====================
     *
     * Patterns used:
     * 1. Singleton
     *    - The class provides a single global access point via getInstance().
     *    - The constructor is private, and a static final INSTANCE is created.
     *    - All callers share the same PasswordGeneratorService object, which
     *      satisfies the requirement for "a single shared access point" and
     *      ensures only one instance of the service exists.
     *
     * 2. Strategy
     *    - The password-generation behavior is represented by the
     *      PasswordAlgorithm interface and its concrete implementations
     *      (BasicPasswordAlgorithm, EnhancedPasswordAlgorithm,
     *      LettersPasswordAlgorithm).
     *    - The service holds a reference to a PasswordAlgorithm and delegates
     *      generation to it, allowing the behavior to be swapped at run time
     *      using setAlgorithm(String name).
     *    - New password-generation approaches can be added in the future by
     *      creating new classes that implement PasswordAlgorithm, without
     *      changing client code that calls generatePassword(int length).
     *
     * These patterns are appropriate because:
     * - Singleton guarantees one shared service instance throughout the
     *   application, matching the requirement for one access point and
     *   coordinated configuration.
     * - Strategy cleanly decouples "what the service does" (password
     *   generation behavior) from "how clients use the service", enabling
     *   easy expansion and swapping of algorithms without modifying clients.
     * =======================================================================
     */

    /**
     * Returns the single instance of the {@code PasswordGeneratorService}.
     *
     * @return the singleton instance of the service
     */
    public static PasswordGeneratorService getInstance() {
        return INSTANCE;
    }

    /**
     * Selects the password-generation algorithm by name.
     * <p>
     * Supported names:
     * <ul>
     *     <li>"basic"    — digits only (0–9), using {@link java.util.Random}</li>
     *     <li>"enhanced" — letters and digits, using {@link java.security.SecureRandom}</li>
     *     <li>"letters"  — letters only (A–Z, a–z)</li>
     * </ul>
     *
     * @param name the algorithm name (case-insensitive)
     * @throws IllegalArgumentException if the name is null or unsupported
     */
    public void setAlgorithm(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Algorithm name must not be null.");
        }

        String normalized = name.toLowerCase().trim();
        switch (normalized) {
            case "basic":
                this.algorithm = new BasicPasswordAlgorithm();
                break;
            case "enhanced":
                this.algorithm = new EnhancedPasswordAlgorithm();
                break;
            case "letters":
                this.algorithm = new LettersPasswordAlgorithm();
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + name);
        }
    }

    /**
     * Generates a password string of the requested length using the currently
     * selected algorithm.
     *
     * @param length the desired password length; must be positive
     * @return a generated password string
     * @throws IllegalStateException    if no algorithm has been selected yet
     * @throws IllegalArgumentException if {@code length} is less than or equal to zero
     */
    public String generatePassword(int length) {
        if (algorithm == null) {
            throw new IllegalStateException("Password algorithm has not been selected.");
        }
        return algorithm.generate(length);
    }

    /**
     * Package-private helper to reset the internal state for testing.
     * <p>
     * This method is not part of the public API and is used only by the
     * JUnit tests to ensure a clean state between test cases.
     */
    static void resetForTesting() {
        INSTANCE.algorithm = null;
    }
}
