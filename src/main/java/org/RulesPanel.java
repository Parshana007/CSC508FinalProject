package org;

import javax.swing.*;
import java.awt.*;

/**
 *  Displays the rules of the game during the Placement phase.
 */

public class RulesPanel extends JPanel {

    private final String[] setupRules = {
            "Click a ship on the grid to select it.",
            "Use the arrow keys to move the selected ship.",
            "Ships cannot overlap or go out of bounds.",
            "Arrange all ships on your board.",
            "When finished, click \"Submit\" to begin the game."
    };

    private final String[] gameRules = {
            "The object of the game is to guess where the opponent's ships are.",
            "During your turn, click a cell on the opponent's grid to select it.",
            "Click \"Submit\" to fire.",
            "Opponent takes their turn.",
            "The game continues until one player's ships have all been sunk."
    };

    public RulesPanel() {
        setBackground(Color.lightGray);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        writeRules(g);
    }

    protected void writeRules(Graphics g) {
        g.setColor(Color.BLACK);

        g.setFont(new Font("SansSerif", Font.BOLD, 14));

        int x = 20;
        int y = 40;
        int lineSpacing = 22;

        g.drawString("Setup Instructions:", x, y);
        y += lineSpacing;

        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (String rule : setupRules) {
            g.drawString(rule, x, y);
            y += lineSpacing;
        }

        y += 2 * lineSpacing;

        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Gameplay Rules:", x, y);
        y += lineSpacing;

        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (String rule : gameRules) {
            g.drawString(rule, x, y);
            y += lineSpacing;
        }
    }
}
