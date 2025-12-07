package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel implements PropertyChangeListener {

    public MainPanel() {
        setBackground(Color.white);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SwingUtilities.isEventDispatchThread()) {
            repaint();
        } else {
            SwingUtilities.invokeLater(this::repaint);
        }
    }

}
