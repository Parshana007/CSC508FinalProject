package org;

import javax.swing.*;
import java.awt.*;

/**
 *  Final screen of the game. Displays text depending on whether player won or lost, and allows player
 *  to replay.
 */

public class GameResultScreen extends JPanel {

    public GameResultScreen(Phase phase) {
        setLayout(new BorderLayout());

        JLabel resultLabel;

        // Result label
        if (phase.equals(Phase.WONGAME)) {
            resultLabel = new JLabel("You Won!", SwingConstants.CENTER);
        } else {
            resultLabel = new JLabel("You Lost!", SwingConstants.CENTER);
        }

        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(resultLabel, BorderLayout.CENTER);
    }
}
