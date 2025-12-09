package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OpponentPanel extends JPanel implements MouseListener {
    public OpponentPanel() {
        setBackground(Color.blue);
        this.setFocusable(true);
        this.requestFocusInWindow();
        // opponent grid
//        GridPanel gridOpponent = new GridPanel(true);
//        JPanel oppGridWrapper = new JPanel(new BorderLayout());
//        oppGridWrapper.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
//                BorderFactory.createEmptyBorder(0, 0, 0, 0)
//        ));
//        oppGridWrapper.add(gridOpponent, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
}