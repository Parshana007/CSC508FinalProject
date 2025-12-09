package org;

import java.awt.*;
import java.util.List;

public class OpponentGridPanel extends GridPanel {
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
}
