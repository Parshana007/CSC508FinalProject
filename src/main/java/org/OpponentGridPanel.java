package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class OpponentGridPanel extends GridPanel implements PropertyChangeListener {
    private boolean editMode = false;

    public OpponentGridPanel() {

    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHitMiss(g);
    }

    private void drawHitMiss(Graphics g) {
        List<Point> hits = Blackboard.getInstance().getOpponentState().getHits();
        List<Point> misses = Blackboard.getInstance().getOpponentState().getMisses();
        List<Ship> sunk = Blackboard.getInstance().getOpponentState().getSunkShips();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = (col - 1) * cellWidth;
                int y = (row - 1) * cellHeight;


                Point coordinate = new Point(row, col);
                System.out.println("Coordinate x" + coordinate.getX() + "Coordinate y" + coordinate.getY());
                System.out.println("check" + hits.contains(coordinate));

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
        if (!editMode) return;

        Point grid = toGrid(e.getX(), e.getY());

        // Bounds-check in case user clicks outside the grid
        if (grid.x < 0 || grid.x >= cols || grid.y < 0 || grid.y >= rows)
            return;

        Point coordinate = toCoordinate(grid.x, grid.y);
        System.out.println("coordinate x" + coordinate.getX() + "Coordinate y" + coordinate.getY());

        // Triggers series of checking opponent's ship if hit/miss/sunk
        Blackboard.getInstance().addMyGuess(coordinate);
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
