package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridPanel extends JPanel implements MouseListener {
    protected int rows = 10;
    protected int cols = 10;

    protected int cellWidth;
    protected int cellHeight;

    public GridPanel() {
        setBackground(Color.BLUE);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        cellWidth = getWidth() / cols;
        cellHeight = getHeight() / rows;

        drawGrid(g);
    }

    protected void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = c * cellWidth;
                int y = r * cellHeight;
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    protected Point toGrid(int x, int y) {
        return new Point(x / cellWidth, y / cellHeight);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
