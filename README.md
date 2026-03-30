# DATA3000 Assignment 3

This project implements a postfix calculator using:

- an array-based stack (`ADTStack`)
- a binary search tree for variables (`BST`)
- a main driver that runs the required sample expressions (`app.Main`)

## Project Structure

```text
src/
  ADTStack/
    ArrayStack.java
    StackInterface.java
  BST/
    BinarySearchTree.java
  calculator/
    PostfixCalculator.java
  app/
    Main.java
```

## Requirements

- JDK 17+ (tested with JDK 21)
- PowerShell (for commands below)

## Compile

From the project root:

```powershell
javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
```

## Run

Normal run (shows `JOptionPane` welcome dialog):

```powershell
java -cp bin app.Main
```

Headless run (useful in terminal-only environments):

```powershell
java "-Djava.awt.headless=true" -cp bin app.Main
```

## What the Program Does

1. Shows the assignment welcome message.
2. Loads variable sets for 8 postfix test expressions.
3. Evaluates each expression.
4. Prints:
   - postfix expression
   - BST visualization
   - result
   - confirmation after deleting all variables

## Notes

- `bin/` contains compiled output and is ignored by Git.
- Only source code and essential project files are tracked in this repository.
