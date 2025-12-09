package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for {@link PasswordGeneratorService}.
 */
public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        // Ensure clean state before each test
        PasswordGeneratorService.resetForTesting();
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        // verify that 'service' is not null
        assertNotNull(service, "getInstance() should never return null.");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        // Verify that both 'service' and 'second' refer to the exact same object
        assertSame(service, second,
                "getInstance() must always return the same singleton instance.");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();

        // No algorithm selected yet — calling generatePassword should throw IllegalStateException
        assertThrows(IllegalStateException.class,
                () -> s.generatePassword(8),
                "generatePassword should throw IllegalStateException when no algorithm is selected.");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);

        assertNotNull(p, "Generated password should not be null.");
        assertEquals(10, p.length(), "Password length should match requested length for basic algorithm.");

        // verify that all characters are digits
        for (char c : p.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only, but found: " + c);
        }
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);

        assertNotNull(p, "Generated password should not be null.");
        assertEquals(12, p.length(), "Password length should match requested length for enhanced algorithm.");

        // verify that all characters are letters or digits
        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c),
                    "Enhanced algorithm must generate only letters or digits, but found: " + c);
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);

        assertNotNull(p, "Generated password should not be null.");
        assertEquals(8, p.length(), "Password length should match requested length for letters algorithm.");

        // verify that all characters are letters
        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only, but found: " + c);
        }
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);

        // lengths must be correct
        assertEquals(10, p1.length());
        assertEquals(10, p2.length());
        assertEquals(10, p3.length());

        // basic: digits only
        for (char c : p1.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only.");
        }

        // letters: letters only
        for (char c : p2.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only.");
        }

        // enhanced: letters or digits
        for (char c : p3.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c),
                    "Enhanced algorithm must generate letters or digits only.");
        }

        // A digits-only password can never be equal to a letters-only password
        assertNotEquals(p1, p2,
                "Switching algorithms should result in different kinds of passwords (digits vs letters).");
    }
}
