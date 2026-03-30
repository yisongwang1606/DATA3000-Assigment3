package BST;

/**
 * This class implements a binary search tree for variable name/value pairs.
 */
public class BinarySearchTree {
    /**
     * This node class stores a variable key, its integer value, and child references.
     */
    private static class Node {
        /** This field stores the variable name used as the BST key. */
        private String key;

        /** This field stores the integer value associated with the key. */
        private int value;

        /** This field points to the left child node in the BST. */
        private Node left;

        /** This field points to the right child node in the BST. */
        private Node right;

        /**
         * This constructor creates a node with a key and value pair.
         *
         * @param key variable name
         * @param value variable value
         */
        private Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /** This field stores the root node of the BST. */
    private Node root;

    /**
     * This method inserts a key/value pair into the BST or updates an existing key.
     *
     * @param key variable name
     * @param value variable value
     */
    public void insert(String key, int value) {
        String normalizedKey = validateAndNormalizeKey(key);
        root = insertRecursive(root, normalizedKey, value);
    }

    /**
     * This method searches the BST for a key and returns its value if found.
     *
     * @param key variable name to search
     * @return value if found, otherwise null
     */
    public Integer search(String key) {
        String normalizedKey = validateAndNormalizeKey(key);
        Node foundNode = searchRecursive(root, normalizedKey);
        if (foundNode == null) {
            return null;
        }
        return foundNode.value;
    }

    /**
     * This method deletes the given key from the BST when the key exists.
     *
     * @param key variable name to delete
     */
    public void delete(String key) {
        String normalizedKey = validateAndNormalizeKey(key);
        root = deleteRecursive(root, normalizedKey);
    }

    /**
     * This method removes every node from the BST.
     */
    public void deleteAll() {
        root = null;
    }

    /**
     * This method checks whether the BST currently has any nodes.
     *
     * @return true when no nodes exist, otherwise false
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * This method returns a hierarchical string representation of the BST.
     *
     * @return hierarchical tree view string
     */
    public String displayTree() {
        if (root == null) {
            return "(empty)";
        }
        StringBuilder builder = new StringBuilder();
        buildDisplayString(root, builder, 0);
        return builder.toString();
    }

    /**
     * This method recursively inserts or updates a node in the BST.
     *
     * @param current current node in traversal
     * @param key variable name
     * @param value variable value
     * @return updated subtree root
     */
    private Node insertRecursive(Node current, String key, int value) {
        if (current == null) {
            return new Node(key, value);
        }
        int comparison = key.compareTo(current.key);
        if (comparison < 0) {
            current.left = insertRecursive(current.left, key, value);
        } else if (comparison > 0) {
            current.right = insertRecursive(current.right, key, value);
        } else {
            current.value = value;
        }
        return current;
    }

    /**
     * This method recursively searches for a node by key.
     *
     * @param current current node in traversal
     * @param key key to find
     * @return matching node or null
     */
    private Node searchRecursive(Node current, String key) {
        if (current == null) {
            return null;
        }
        int comparison = key.compareTo(current.key);
        if (comparison == 0) {
            return current;
        }
        if (comparison < 0) {
            return searchRecursive(current.left, key);
        }
        return searchRecursive(current.right, key);
    }

    /**
     * This method recursively deletes a key from the BST and keeps BST order valid.
     *
     * @param current current node in traversal
     * @param key key to delete
     * @return updated subtree root
     */
    private Node deleteRecursive(Node current, String key) {
        if (current == null) {
            return null;
        }
        int comparison = key.compareTo(current.key);
        if (comparison < 0) {
            current.left = deleteRecursive(current.left, key);
            return current;
        }
        if (comparison > 0) {
            current.right = deleteRecursive(current.right, key);
            return current;
        }
        // Standard delete cases: leaf, one child, then two children.
        if (current.left == null && current.right == null) {
            return null;
        }
        if (current.left == null) {
            return current.right;
        }
        if (current.right == null) {
            return current.left;
        }
        // Two-child case: replace with in-order successor from the right subtree.
        Node successor = findMinimumNode(current.right);
        current.key = successor.key;
        current.value = successor.value;
        current.right = deleteRecursive(current.right, successor.key);
        return current;
    }

    /**
     * This method finds the minimum-key node in a subtree.
     *
     * @param current subtree root
     * @return minimum-key node
     */
    private Node findMinimumNode(Node current) {
        Node cursor = current;
        while (cursor.left != null) {
            cursor = cursor.left;
        }
        return cursor;
    }

    /**
     * This method appends a visual tree line using right-root-left order.
     * This matches the sample run style where larger keys appear above the root.
     *
     * @param current current node
     * @param builder output builder
     * @param depth depth level for indentation
     */
    private void buildDisplayString(Node current, StringBuilder builder, int depth) {
        if (current == null) {
            return;
        }
        buildDisplayString(current.right, builder, depth + 1);
        appendIndentation(builder, depth);
        builder.append("||==> ");
        builder.append(current.key);
        builder.append(":");
        builder.append(current.value);
        builder.append(System.lineSeparator());
        buildDisplayString(current.left, builder, depth + 1);
    }

    /**
     * This method appends two spaces per depth level to format tree indentation.
     *
     * @param builder output builder
     * @param depth current depth level
     */
    private void appendIndentation(StringBuilder builder, int depth) {
        for (int i = 0; i < depth; i++) {
            builder.append("  ");
        }
    }

    /**
     * This method validates that a variable key is non-null/non-blank and normalizes spaces.
     *
     * @param key key to validate
     * @return trimmed key
     */
    private String validateAndNormalizeKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Variable name cannot be null.");
        }
        String trimmedKey = key.trim();
        if (trimmedKey.isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be empty.");
        }
        return trimmedKey;
    }
}
