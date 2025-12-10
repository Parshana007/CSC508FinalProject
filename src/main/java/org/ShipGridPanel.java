package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ShipGridPanel extends GridPanel implements PropertyChangeListener {

    private List<Ship> ships = new ArrayList<>();
    private Ship[][] boardShips;

    private boolean editMode;
    private Ship activeShip;

    public ShipGridPanel(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            initializeShips();
            setupKeyBindings();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShips(g);
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

    private void drawShip(Graphics g, Ship ship, boolean active) {
        List<Point> coords = ship.getCoordinates();

        int minX = coords.stream().mapToInt(p -> p.x).min().orElse(0);
        int maxX = coords.stream().mapToInt(p -> p.x).max().orElse(0);
        int minY = coords.stream().mapToInt(p -> p.y).min().orElse(0);
        int maxY = coords.stream().mapToInt(p -> p.y).max().orElse(0);

        int width = (maxX - minX + 1) * cellWidth;
        int height = (maxY - minY + 1) * cellHeight;

        g.setColor(active ? Color.RED : Color.LIGHT_GRAY);
        g.fillRect(minX * cellWidth, minY * cellHeight, width, height);

        g.setColor(active ? Color.RED : Color.DARK_GRAY);
        g.drawRect(minX * cellWidth, minY * cellHeight, width, height);
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

        Blackboard.getInstance().getPlayerState().setMyShips(ships);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!editMode) return;

        Point grid = toGrid(e.getX(), e.getY());

        // Bounds-check in case user clicks outside the grid
        if (grid.x < 0 || grid.x >= cols || grid.y < 0 || grid.y >= rows)
            return;

        int logicalX = grid.x;
        int logicalY = grid.y;

        activeShip = boardShips[logicalY][logicalX];
        repaint();
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