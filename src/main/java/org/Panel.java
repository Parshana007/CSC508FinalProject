package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Panel extends JPanel implements PropertyChangeListener, MouseMotionListener {

    public Panel() {
        setBackground(new Color(172, 248, 199));
        this.addMouseMotionListener(this);
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

    @Override
    public void mouseDragged (MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved (MouseEvent mouseEvent) {

    }
}
