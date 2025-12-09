package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridPanel extends JPanel implements PropertyChangeListener, MouseListener {
    List<Ship> ships;
//    List<HitMiss> guesses;

    // 2D matrix to represent the board - each cell will either reference a ship or null if no ship is placed there
    Ship[][] boardShips;

    int cellWidth, cellHeight;
    int rows, cols;
    // if in edit mode, moving the mouse will move the ship being placed (activeShip)
    boolean editMode;
    Ship activeShip;

    public GridPanel(boolean editMode) {
        setBackground(Color.blue);

        rows = 10;
        cols = 10;

        if (editMode) {
            initializeShips();
        }

//        guesses = new ArrayList<>();
        this.editMode = editMode;
        setupKeyBindings();
        this.addMouseListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void addGuess(HitMiss guess) {
        // draw the guess by coloring in the cell
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        cellWidth = getWidth() / cols;
        cellHeight = getHeight() / rows;

        drawGrid(g);
        drawShips(g);
        // drawGuesses(g) -- add this later to layer the guesses on top of the ships
    }

    private void drawGrid(Graphics g) {

        g.setColor(Color.DARK_GRAY);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    private void initializeShips() {
        ships = new ArrayList<>();

        List<Point> ship1Points = new ArrayList<>();
        ship1Points.add(new Point(1, 1));
        ship1Points.add(new Point(2, 1));
        ship1Points.add(new Point(3, 1));
        ship1Points.add(new Point(4, 1));
        ship1Points.add(new Point(5, 1));
        ships.add(new Ship(ship1Points));

        List<Point> ship2Points = new ArrayList<>();
        ship2Points.add(new Point(2, 3));
        ship2Points.add(new Point(2, 4));
        ship2Points.add(new Point(2, 5));
        ship2Points.add(new Point(2, 6));
        ships.add(new Ship(ship2Points));

        List<Point> ship3Points = new ArrayList<>();
        ship3Points.add(new Point(4, 3));
        ship3Points.add(new Point(5, 3));
        ship3Points.add(new Point(6, 3));
        ships.add(new Ship(ship3Points));

        List<Point> ship4Points = new ArrayList<>();
        ship4Points.add(new Point(7, 5));
        ship4Points.add(new Point(7, 6));
        ships.add(new Ship(ship4Points));

        List<Point> ship5Points = new ArrayList<>();
        ship5Points.add(new Point(5, 5));
        ships.add(new Ship(ship5Points));
    }

    private void drawShips(Graphics g) {
        boardShips = new Ship[rows][cols];
        for (Ship ship : ships) {
            for (Point cell : ship.getCoordinates()) {
                boardShips[cell.y][cell.x] = ship;
            }
            drawShip(g, ship, ship == activeShip);

        }
    }

    private void drawShip(Graphics g, Ship ship, boolean selectingShip) {
        int shipWidth = ship.getCoordinates().get(ship.getCoordinates().size() - 1).x
                - ship.getCoordinates().get(0).x + 1;

        int shipHeight = ship.getCoordinates().get(ship.getCoordinates().size() - 1).y
                - ship.getCoordinates().get(0).y + 1;

        Color fillColor = selectingShip ? Color.red : Color.lightGray;
        g.setColor(fillColor);
        g.fillRect((ship.getCoordinates().get(0).x) * cellWidth,
                (ship.getCoordinates().get(0).y) * cellHeight,
                shipWidth * cellWidth,
                shipHeight * cellHeight);

        Color outlineColor = selectingShip ? Color.red : Color.darkGray;
        g.setColor(outlineColor);
        g.drawRect((ship.getCoordinates().get(0).x) * cellWidth,
                (ship.getCoordinates().get(0).y) * cellHeight,
                shipWidth * cellWidth,
                shipHeight * cellHeight);
    }

    private void setupKeyBindings() {
        InputMap input = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actions = getActionMap();

        input.put(KeyStroke.getKeyStroke("LEFT"),  "moveLeft");
        input.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        input.put(KeyStroke.getKeyStroke("UP"),    "moveUp");
        input.put(KeyStroke.getKeyStroke("DOWN"),  "moveDown");

        actions.put("moveLeft",  new MoveShipAction(-1, 0));
        actions.put("moveRight", new MoveShipAction(1, 0));
        actions.put("moveUp",    new MoveShipAction(0, -1));
        actions.put("moveDown",  new MoveShipAction(0, 1));
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
    public void mouseReleased(MouseEvent e) {
        if (!editMode) {
            return;
        }
        int x = e.getX();
        int y = e.getY();

        // Determine which cell was clicked
        int col = x / cellWidth;
        int row = y / cellHeight;

        // Bounds-check in case user clicks outside the grid
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return; // clicked outside grid
        }

        activeShip = boardShips[row][col];

        repaint();
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

    private class MoveShipAction extends AbstractAction {
        int dx, dy;

        MoveShipAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!editMode || activeShip == null)
                return;  // ignore keys unless in edit mode AND a ship is selected

            moveActiveShip(dx, dy);
        }

        private void moveActiveShip(int dx, int dy) {
            if (activeShip == null) return;

            List<Point> newCoords = new ArrayList<>();
            for (Point coord : activeShip.getCoordinates()) {
                int newX = coord.x + dx;
                int newY = coord.y + dy;

                if (!canPlaceShip(newX, newY)) {
                    return; // if any cell is invalid, cancel move
                }

                newCoords.add(new Point(newX, newY));
            }

            // move is valid: update coordinates
            activeShip.setCoordinates(newCoords);

            repaint();
        }

        private boolean canPlaceShip(int newX, int newY) {
            return newY >= 0 && newY < rows && newX >= 0 && newX < cols
                    && (boardShips[newY][newX] == activeShip || boardShips[newY][newX] == null);
        }
    }

}
