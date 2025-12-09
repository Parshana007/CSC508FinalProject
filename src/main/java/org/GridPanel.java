package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel implements PropertyChangeListener, MouseMotionListener, MouseListener {
    List<Ship> ships;
//    List<HitMiss> guesses;
    // if in edit mode, moving the mouse will move the ship being placed (activeShip)
    boolean editMode;
    Ship activeShip;

    public GridPanel(boolean editMode) {
        ships = new ArrayList<>();
//        guesses = new ArrayList<>();
        this.editMode = editMode;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        setBackground(Color.blue);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

//    public void addGuess(HitMiss guess) {
//        this.guesses.add(guess);
//        // draw the guess
//    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
//        drawHitMiss(g);
    }

    private void drawHitMiss(Graphics g) {
        int rows = 10;
        int cols = 10;

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        g.setColor(Color.DARK_GRAY);

        List<Point> hits = Blackboard.getInstance().getOpponentState().getHits();
        List<Point> misses = Blackboard.getInstance().getOpponentState().getMiss();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;

                if (hits.contains(new Point(row, col))) {
                    g.setColor(Color.RED);
                    g.drawLine(x + 4, y + 4, x + cellWidth - 4, y + cellHeight - 4);
                    g.drawLine(x + cellWidth - 4, y + 4, x + 4, y + cellHeight - 4);
                } else if (misses.contains(new Point(row, col))) {
                    g.setColor(Color.WHITE);
                    g.drawLine(x + 4, y + 4, x + cellWidth - 4, y + cellHeight - 4);
                    g.drawLine(x + cellWidth - 4, y + 4, x + 4, y + cellHeight - 4);
                }
            }
        }
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
                g.setColor(Color.RED);
                g.drawLine(x + 4, y + 4, x + cellWidth - 4, y + cellHeight - 4);
                g.drawLine(x + cellWidth - 4, y + 4, x + 4, y + cellHeight - 4);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int rows = 10;
        int cols = 10;

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        System.out.println("Cell Width: " + cellWidth + " Cell Height: " + cellHeight);

        int x = e.getX();
        int y = e.getY();

        System.out.println("x: " + x + " y: " + y);

        int col = x / cellWidth;  // 0–9
        int row = y / cellHeight; // 0–9

        // Convert to 1–10 for your game logic
        int gridCol = col + 1;
        int gridRow = row + 1;

//        Blackboard.getInstance().addMyGuess(new Point(gridCol, gridRow));

        System.out.println("Clicked grid location: row = " + gridRow + ", col = " + gridCol);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
