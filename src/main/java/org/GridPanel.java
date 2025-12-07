package org;

import javax.swing.*;
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
    }

    public void addGuess(HitMiss guess) {
        this.guesses.add(guess);
        // draw the guess
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
