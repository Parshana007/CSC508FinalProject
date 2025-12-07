package org;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);

        Blackboard.getInstance().addPropertyChangeListener(mainPanel);
    }

    public static void main(String[] args) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        // ask for room code

        // "waiting for other player..."

        // both players have joined - advance to ship placement screen

        // players place ships and press submit

        // "waiting for other player to submit..."

        // both players have submitted ship placements - advance to main game screen
    }
}