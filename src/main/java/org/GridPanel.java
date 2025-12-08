package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel implements PropertyChangeListener, MouseMotionListener {
    List<Ship> ships;
    List<HitMiss> guesses;
    // if in edit mode, moving the mouse will move the ship being placed (activeShip)
    boolean editMode;
    Ship activeShip;

    public GridPanel(boolean editMode) {
        ships = new ArrayList<>();
        guesses = new ArrayList<>();
        this.editMode = editMode;
        this.addMouseMotionListener(this);
        setBackground(Color.blue);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void addGuess(HitMiss guess) {
        this.guesses.add(guess);
        // draw the guess
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        int rows = 10;
        int cols = 10;

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        g.setColor(Color.DARK_GRAY);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
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
