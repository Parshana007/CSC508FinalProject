package org;

import javax.swing.*;
import java.awt.*;

public class GameResultScreen extends JPanel {

    public GameResultScreen(Phase phase) {
        setLayout(new BorderLayout());

        JLabel resultLabel;
        JButton replayButton;

        // Result label
        if (phase.equals(Phase.WONGAME)) {
            resultLabel = new JLabel("You Won!", SwingConstants.CENTER);
        } else {
            resultLabel = new JLabel("You Lost!", SwingConstants.CENTER);
        }

        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(resultLabel, BorderLayout.CENTER);

        // Replay button
        replayButton = new JButton("Replay");
        add(replayButton, BorderLayout.SOUTH);
    }
}
