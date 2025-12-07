package org;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        Panel panel = new Panel();
        this.add(panel);

        Blackboard.getInstance().addPropertyChangeListener(panel);
    }
}