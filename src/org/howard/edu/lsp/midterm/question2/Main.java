package org.howard.edu.lsp.midterm.question2;

/**
 * Demonstrates the use of method overloading with the AreaCalculator class.
 *
 * Explanation:
 * Method overloading is better here because all methods perform a similar
 * conceptual operation (calculating an area), just for different shapes.
 * Using one method name makes the API consistent and easier to use
 * compared to having many separate methods like circleArea(), rectangleArea(), etc.
 */
public class Main {
    public static void main(String[] args) {
        // Normal cases
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        // Exception demonstration
        try {
            System.out.println("Attempting invalid area: " + AreaCalculator.area(-3.0));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
