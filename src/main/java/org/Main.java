package org;

import javax.swing.*;

/**
 *  Main application class for Battleship
 */

public class Main extends JFrame {

    public Main() {
        ScreenManager screens = new ScreenManager();
        add(screens);
        Blackboard.getInstance().addPropertyChangeListener(screens);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main m = new Main();
            m.setTitle("Battleship");
            m.setSize(1200, 600);
            m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m.setResizable(false);
            m.setVisible(true);
        });

        MQTTPublisher publisher = new MQTTPublisher();
        Blackboard.getInstance().addPropertyChangeListener(publisher);
        MQTTSubscriber subscriber = new MQTTSubscriber();
        Blackboard.getInstance().addPropertyChangeListener(subscriber);

    }
}