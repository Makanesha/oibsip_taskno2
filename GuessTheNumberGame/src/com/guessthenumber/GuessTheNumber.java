package com.guessthenumber;

import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumber extends JFrame implements ActionListener {

    // Define UI components
    private JLabel instructionLabel, resultLabel, attemptsLabel, scoreLabel;
    private JTextField guessInput;
    private JButton guessButton, restartButton;
    private int numberToGuess;
    private int attemptsLeft;
    private int score;

    public GuessTheNumber() {
        // Set JFrame properties
        setTitle("Guess The Number");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set background color
        getContentPane().setBackground(Color.BLACK);

        // Initialize score and attempts
        score = 100; // Start with maximum score
        attemptsLeft = 10;

        // Create and set up UI components with new theme colors
        instructionLabel = new JLabel("Guess the number between 1 and 100!");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        guessInput = new JTextField(5);
        guessInput.setBackground(Color.WHITE);
        guessInput.setForeground(Color.BLACK);
        guessInput.setFont(new Font("Arial", Font.PLAIN, 14));

        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(34, 139, 34)); // Green color
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.addActionListener(this);

        restartButton = new JButton("Restart");
        restartButton.setBackground(new Color(34, 139, 34)); // Green color
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Arial", Font.BOLD, 14));
        restartButton.addActionListener(e -> restartGame());

        resultLabel = new JLabel("You have 10 attempts left.");
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        attemptsLabel = new JLabel("Attempts Left: " + attemptsLeft);
        attemptsLabel.setForeground(Color.WHITE);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Layout settings (GridBagLayout for better spacing control)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Add components to the JFrame using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(instructionLabel, gbc);

        gbc.gridy = 1;
        add(guessInput, gbc);

        gbc.gridy = 2;
        add(guessButton, gbc);

        gbc.gridy = 3;
        add(resultLabel, gbc);

        gbc.gridy = 4;
        add(attemptsLabel, gbc);

        gbc.gridy = 5;
        add(scoreLabel, gbc);

        gbc.gridy = 6;
        add(restartButton, gbc);

        // Start the game by generating the number to guess
        generateNewNumber();
    }

    private void generateNewNumber() {
        Random rand = new Random();
        numberToGuess = rand.nextInt(100) + 1; // Random number between 1 and 100
    }

    private void restartGame() {
        generateNewNumber();
        attemptsLeft = 10; // Reset attempts
        score = 100; // Reset score
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        scoreLabel.setText("Score: " + score);
        resultLabel.setText("Guess the number between 1 and 100!");
        guessInput.setText("");
        guessButton.setEnabled(true); // Enable guess button for a new round
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userGuessText = guessInput.getText();
        if (userGuessText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userGuess;
        try {
            userGuess = Integer.parseInt(userGuessText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check the guess
        if (userGuess == numberToGuess) {
            resultLabel.setText("Correct! You guessed the number!");
            resultLabel.setForeground(new Color(34, 139, 34)); // Green for correct
            scoreLabel.setText("Score: " + score);
            generateNewNumber(); // Generate a new number for the next round

            guessButton.setEnabled(false); // Disable the guess button for now
        } else {
            attemptsLeft--;
            if (attemptsLeft == 0) {
                resultLabel.setText("Game Over! The number was: " + numberToGuess);
                resultLabel.setForeground(Color.RED);
                score = 0; // Set score to 0 for game over
                guessButton.setEnabled(false); // Disable the guess button
            } else {
                if (score > 10) {
                    score -= 10; // Decrease score by 10 for each incorrect guess
                } else if (attemptsLeft == 1) {
                    score = 10; // Ensure score is 10 on the last guess
                }

                int difference = Math.abs(userGuess - numberToGuess);

                // Provide feedback based on the difference
                if (difference > 20) {
                    if (userGuess > numberToGuess) {
                        resultLabel.setText("Your guess is TOO HIGH! Try again.");
                    } else {
                        resultLabel.setText("Your guess is TOO LOW! Try again.");
                    }
                } else if (difference > 10) {
                    if (userGuess > numberToGuess) {
                        resultLabel.setText("Your guess is HIGH! Try again.");
                    } else {
                        resultLabel.setText("Your guess is LOW! Try again.");
                    }
                } else if (difference > 5) {
                    resultLabel.setText("You're somewhere there! Try again.");
                } else if (difference > 2) {
                    resultLabel.setText("You're Close! Try again.");
                } else if (difference <= 2) {
                    resultLabel.setText("You're TOO CLOSE! Try again.");
                }
                resultLabel.setForeground(Color.RED);  // Keep feedback in red for incorrect guesses
            }



            attemptsLabel.setText("Attempts Left: " + attemptsLeft);
            scoreLabel.setText("Score: " + score);
        }
    }

    public static void main(String[] args) {
        // Swing GUI must be created and updated in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GuessTheNumber game = new GuessTheNumber();
            game.setVisible(true);
        });
    }
}
