package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameResultScreen extends JFrame {
    public GameResultScreen(Phase phase) {
        JLabel resultLabel;
        JButton replayButton;
        setTitle("Game Over");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen

        // Layout
        setLayout(new BorderLayout());

        // Result label
        if (phase.equals(Phase.WONGAME)) {
            resultLabel = new JLabel("You Won!");
        }else {resultLabel = new JLabel("You Lost!");}
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(resultLabel, BorderLayout.CENTER);

        // Replay button
        replayButton = new JButton("Replay");
        replayButton.addActionListener(onReplay);
        add(replayButton, BorderLayout.SOUTH);
    }

//    public static void main(String[] args) {
//        // Standalone test:
//        GameResultScreen winScreen = new GameResultScreen("me", e -> {
//            System.out.println("Replay button clicked!");
//        });
//
//        winScreen.setVisible(true);
//    }
}
