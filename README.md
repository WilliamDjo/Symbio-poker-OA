# Poker Hand Evaluator

A command-line Java program that evaluates poker hands for two players and determines the winner of each hand.

## Overview

This program reads a stream of poker hands from STDIN, where each line contains 10 cards (5 for each player). It evaluates each hand according to standard poker rules and outputs the total number of hands won by each player.

## Requirements

- Java Development Kit (JDK) 11 or higher (for direct source file execution)
- Command line terminal

## Running the Program

### Method 1: Direct Execution with Pipe (Java 11+)

```bash
echo "2H 3D 5S 9C KD 2C 3H 4S 8C AH" | java PokerHandEvaluator.java
```

```bash
cat poker-hands.txt | java PokerHandEvaluator.java
```

### Method 2: Using Input Redirection

1. Create an input file (e.g., `hands.txt`) with poker hands:

   ```
   2H 3D 5S 9C KD 2C 3H 4S 8C AH
   2H 4S 4C 2D 4H 2S 8S AS QS 3S
   2H 3D 5S 9C KD 2C 3H 4S 8C KH
   ```

2. Run the program with input redirection:
   ```bash
   java PokerHandEvaluator.java < hands.txt
   ```

### Method 3: Manual Input

1. Run the program:

   ```bash
   java PokerHandEvaluator.java
   ```

2. Type hands manually (one per line):

   ```
   2H 3D 5S 9C KD 2C 3H 4S 8C AH
   2H 4S 4C 2D 4H 2S 8S AS QS 3S
   ```

3. Press `Ctrl+D` (Linux/Mac) or `Ctrl+Z` followed by `Enter` (Windows) to signal EOF

## Input Format

- Each line must contain exactly 10 cards separated by spaces
- First 5 cards belong to Player 1, last 5 cards belong to Player 2
- Each card is represented by 2 characters:
  - **Value**: `2-9`, `T` (10), `J` (Jack), `Q` (Queen), `K` (King), `A` (Ace)
  - **Suit**: `D` (Diamonds), `H` (Hearts), `S` (Spades), `C` (Clubs)

### Example Input:

```
KH KC 3S 3H 3D AH AC AD 8H 8S
9C 9D 9H 9S 3D 3C 3S 3H AC AD
```

## Output Format

The program outputs the total number of hands won by each player:

```
Player 1: X hands
Player 2: Y hands
```

## Alternative: Traditional Compilation Method

If you're using Java 8-10 or prefer explicit compilation:

1. **Compile the program**:

   ```bash
   javac PokerHandEvaluator.java
   ```

2. **Run the compiled class**:
   ```bash
   echo "2H 3D 5S 9C KD 2C 3H 4S 8C AH" | java PokerHandEvaluator
   ```

## Notes

- The program continues reading until EOF is reached
- Invalid hands (wrong number of cards) are silently skipped
- Tied hands result in no points for either player
