package calculator;

import ADTStack.ArrayStack;
import ADTStack.StackInterface;
import BST.BinarySearchTree;
import java.util.EmptyStackException;

/**
 * This class evaluates postfix expressions by using an array-based stack and a BST for variables.
 */
public class PostfixCalculator {
    /** This field stores variable key/value pairs in a binary search tree. */
    private final BinarySearchTree variableTree;

    /** This field stores operands and intermediate results during expression evaluation. */
    private final StackInterface<Integer> stack;

    /**
     * This constructor initializes the variable tree and the operand stack.
     */
    public PostfixCalculator() {
        this.variableTree = new BinarySearchTree();
        this.stack = new ArrayStack<>();
    }

    /**
     * This method evaluates a postfix expression and returns the integer result.
     *
     * @param expression postfix expression with tokens separated by spaces
     * @return evaluated integer result
     */
    public int evaluatePostfixExpression(String expression) {
        validateExpression(expression);
        stack.clear();
        String[] tokens = expression.trim().split("\\s+");
        // Process each token as either an operator or an operand.
        for (String token : tokens) {
            if (isOperator(token)) {
                // Postfix order requires popping right operand before left operand.
                int rightOperand = popOperandFromStack();
                int leftOperand = popOperandFromStack();
                int operationResult = applyOperator(token, leftOperand, rightOperand);
                stack.push(operationResult);
            } else {
                int operandValue = resolveOperandValue(token);
                stack.push(operandValue);
            }
        }
        int finalResult = popOperandFromStack();
        // A valid postfix expression must reduce to exactly one remaining value.
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("The postfix expression is invalid because extra operands remain.");
        }
        return finalResult;
    }

    /**
     * This method stores or updates a variable in the BST.
     *
     * @param key variable name
     * @param value variable value
     */
    public void setVariable(String key, int value) {
        variableTree.insert(key, value);
    }

    /**
     * This method deletes a variable by key from the BST.
     *
     * @param key variable name
     */
    public void deleteVariable(String key) {
        variableTree.delete(key);
    }

    /**
     * This method removes all variables from the BST.
     */
    public void deleteAllVariables() {
        variableTree.deleteAll();
    }

    /**
     * This method returns the tree display used for visualization output.
     *
     * @return hierarchical string of variable BST
     */
    public String displayVariableTree() {
        return variableTree.displayTree();
    }

    /**
     * This method resolves a token to an integer by checking variables first, then numeric parsing.
     *
     * @param token operand token
     * @return integer value represented by the token
     */
    private int resolveOperandValue(String token) {
        // Variable lookup has priority; if absent, parse as integer literal.
        Integer variableValue = variableTree.search(token);
        if (variableValue != null) {
            // Reinsert/update confirms the variable table role during evaluation.
            variableTree.insert(token, variableValue);
            return variableValue;
        }
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Token '" + token + "' is not a defined variable or integer.", exception);
        }
    }

    /**
     * This method pops one operand from the stack and converts stack underflow to a readable error.
     *
     * @return popped operand value
     */
    private int popOperandFromStack() {
        try {
            return stack.pop();
        } catch (EmptyStackException exception) {
            throw new IllegalArgumentException("The postfix expression has insufficient operands.", exception);
        }
    }

    /**
     * This method applies one arithmetic operator to two operands.
     *
     * @param operator arithmetic operator token
     * @param leftOperand left operand
     * @param rightOperand right operand
     * @return arithmetic result
     */
    private int applyOperator(String operator, int leftOperand, int rightOperand) {
        if ("+".equals(operator)) {
            return leftOperand + rightOperand;
        }
        if ("-".equals(operator)) {
            return leftOperand - rightOperand;
        }
        if ("*".equals(operator)) {
            return leftOperand * rightOperand;
        }
        if (rightOperand == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return leftOperand / rightOperand;
    }

    /**
     * This method checks whether a token is one of the supported arithmetic operators.
     *
     * @param token token to check
     * @return true when token is an operator, otherwise false
     */
    private boolean isOperator(String token) {
        return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
    }

    /**
     * This method validates that the expression is non-null and non-blank.
     *
     * @param expression expression to validate
     */
    private void validateExpression(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null.");
        }
        if (expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty.");
        }
    }
}
