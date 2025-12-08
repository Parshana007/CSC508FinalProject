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


        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setTitle("Battleship");
            main.setSize(1200, 600);
            main.setResizable(false);
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setVisible(true);
        });

//        MyPublisher publisher = new MyPublisher();
//        Blackboard.getInstance().addPropertyChangeListener(publisher);
    }
}