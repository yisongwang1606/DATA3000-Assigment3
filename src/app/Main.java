package app;

import javax.swing.JOptionPane;

import calculator.PostfixCalculator;

/**
 * Entry point that runs the assignment sample postfix expressions.
 */
public final class Main {
    /**
     * Private constructor to prevent utility-style instantiation.
     */
    private Main() {
    }

    /**
     * Runs all sample expressions required by the assignment.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        showStartupDialog();

        PostfixCalculator calculator = new PostfixCalculator();

        runSample(calculator, "x y * z +", new String[] { "x", "y", "z" }, new int[] { 5, 3, 4 });
        runSample(calculator, "a b + c *", new String[] { "a", "b", "c" }, new int[] { 2, 3, 4 });
        runSample(calculator, "m n * p -", new String[] { "m", "n", "p" }, new int[] { 8, 2, 3 });
        runSample(calculator, "q r s * +", new String[] { "q", "r", "s" }, new int[] { 7, 3, 2 });
        runSample(calculator, "d e f * + 4 +", new String[] { "d", "e", "f" }, new int[] { 1, 2, 3 });
        runSample(calculator, "g h i + * j /", new String[] { "g", "h", "i", "j" }, new int[] { 2, 3, 4, 5 });
        runSample(calculator, "k l + m n + *", new String[] { "k", "l", "m", "n" }, new int[] { 2, 3, 4, 5 });
        runSample(calculator, "o p / q r + * s +", new String[] { "o", "p", "q", "r", "s" }, new int[] { 9, 3, 5, 2, 7 });
        runErrorHandlingShowcase();
    }

    /**
     * Displays the startup prompt requested in the sample run section.
     */
    private static void showStartupDialog() {
        String message = "A text-based calculator that can evaluate arithmetic expressions\n"
                + "in postfix notation using a stack data structure and handle variables\n"
                + "using a binary search tree (BST).\n"
                + "You will design and implement an array-based stack, and then develop and\n"
                + "implement a provided algorithm to evaluate postfix expressions, handle\n"
                + "integer operands and basic arithmetic operations, integrate a binary search\n"
                + "tree to store variables and their values, visually display the binary search\n"
                + "tree, and handle deletion of all variables.\n"
                + "The correctness of your implementation can be verified by following the\n"
                + "provided postfix expressions as test cases in the sample runs.\n\n"
                + "Press OK to Start";
        try {
            JOptionPane.showMessageDialog(null, message, "Welcome to", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException exception) {
            // Fallback for headless environments where a GUI dialog is unavailable.
            System.out.println(message);
            System.out.println();
        }
    }

    /**
     * Sets variables, evaluates one postfix expression, and prints required output.
     *
     * @param calculator shared calculator instance
     * @param expression postfix expression to evaluate
     * @param variableNames variable names for this case
     * @param variableValues variable values for this case
     */
    private static void runSample(PostfixCalculator calculator, String expression, String[] variableNames, int[] variableValues) {
        validateInputLengths(variableNames, variableValues);

        for (int i = 0; i < variableNames.length; i++) {
            calculator.setVariable(variableNames[i], variableValues[i]);
        }

        System.out.println("Postfix expression: " + expression);
        String treeBeforeEvaluation = calculator.displayVariableTree();
        System.out.print(treeBeforeEvaluation);

        int result = calculator.evaluatePostfixExpression(expression);
        System.out.println("Result: " + result);

        calculator.deleteAllVariables();
        System.out.println();
        System.out.println("All variables have been deleted.");
        System.out.println(calculator.displayVariableTree());
        System.out.println();
    }

    /**
     * Runs additional error-focused tests for presentation and grading rubric coverage.
     */
    private static void runErrorHandlingShowcase() {
        System.out.println("Additional error-handling test cases:");
        runExpectedErrorCase(
                "Undefined variable",
                "u 2 +",
                new String[] {},
                new int[] {});
        runExpectedErrorCase(
                "Insufficient operands",
                "5 +",
                new String[] {},
                new int[] {});
        runExpectedErrorCase(
                "Division by zero",
                "8 0 /",
                new String[] {},
                new int[] {});
    }

    /**
     * Runs one test case expected to throw a readable calculator error.
     *
     * @param testName label displayed for this test
     * @param expression postfix expression
     * @param variableNames variable names used in this test
     * @param variableValues variable values used in this test
     */
    private static void runExpectedErrorCase(
            String testName,
            String expression,
            String[] variableNames,
            int[] variableValues) {
        validateInputLengths(variableNames, variableValues);
        PostfixCalculator calculator = new PostfixCalculator();
        for (int i = 0; i < variableNames.length; i++) {
            calculator.setVariable(variableNames[i], variableValues[i]);
        }
        System.out.println("- " + testName + ": " + expression);
        try {
            int value = calculator.evaluatePostfixExpression(expression);
            System.out.println("  Unexpected success: " + value);
        } catch (IllegalArgumentException exception) {
            System.out.println("  Expected error: " + exception.getMessage());
        }
    }

    /**
     * Validates that variable names and values arrays represent matching pairs.
     *
     * @param variableNames variable names array
     * @param variableValues variable values array
     */
    private static void validateInputLengths(String[] variableNames, int[] variableValues) {
        if (variableNames.length != variableValues.length) {
            throw new IllegalArgumentException("Variable names and values must have the same length.");
        }
    }
}
