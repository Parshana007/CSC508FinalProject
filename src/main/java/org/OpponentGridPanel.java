package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *  Visualizes the elements on the opponent's grid, including my previous guesses (hits or misses) and my
 *  current unsubmitted guess.
 */

public class OpponentGridPanel extends GridPanel implements PropertyChangeListener {
    private Point currentGuess = null;

    public OpponentGridPanel() {}

    public Point getCurrentGuess() {
        return currentGuess;
    }

    public void clearGuess() {
        currentGuess = null;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTemporaryGuess(g);
        drawHitMiss(g);
    }

    private void drawTemporaryGuess(Graphics g) {
        if (currentGuess == null) return;

        int col = currentGuess.x;
        int row = currentGuess.y;

        int x = col * cellWidth;
        int y = row * cellHeight;

        g.setColor(Color.ORANGE);

        g.fillRect(x + 4, y + 4, cellWidth - 8, cellHeight - 8);
    }


    private void drawHitMiss(Graphics g) {
        List<Point> hits = Blackboard.getInstance().getOpponentState().getHits();
        List<Point> misses = Blackboard.getInstance().getOpponentState().getMisses();
        List<Ship> sunk = Blackboard.getInstance().getOpponentState().getSunkShips();
        System.out.println("HITS: " + hits);
        System.out.println("MISSES: " + misses);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;


                Point coordinate = new Point(col, row);

                if (hits.contains(coordinate)) {
                    g.setColor(Color.RED);
                    g.drawLine(x + 4, y + 4, x + cellWidth - 4, y + cellHeight - 4);
                    g.drawLine(x + cellWidth - 4, y + 4, x + 4, y + cellHeight - 4);
                } else if (misses.contains(coordinate)) {
                    g.setColor(Color.WHITE);
                    g.drawLine(x + 4, y + 4, x + cellWidth - 4, y + cellHeight - 4);
                    g.drawLine(x + cellWidth - 4, y + 4, x + 4, y + cellHeight - 4);
                }

                for(Ship ship : sunk) {
                    if (ship.getCoordinates().contains(coordinate)) {
                        g.setColor(Color.RED);
                        g.fillRect(x + 4, y + 4, cellWidth - 4, cellHeight - 4);
                    }
                }

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point grid = toGrid(e.getX(), e.getY());
        System.out.println("GRID x: " + grid.x + " y: " + grid.y);

        // Bounds-check in case user clicks outside the grid
        if (grid.x < 0 || grid.x >= cols || grid.y < 0 || grid.y >= rows)
            return;

        // draw your guess
        currentGuess = toCoordinate(grid.x, grid.y);
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("opponentStateUpdated")) {
            repaint();
        }

        if (SwingUtilities.isEventDispatchThread()) {
            repaint();
        } else {
            SwingUtilities.invokeLater(this::repaint);
        }
    }
}
