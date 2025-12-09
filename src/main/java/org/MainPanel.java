package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel implements PropertyChangeListener {

    public MainPanel() {

        // Set layout first
        setLayout(new BorderLayout());

        // Top label
        JLabel topLabel = new JLabel("Select a ship to place", SwingConstants.CENTER);
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(topLabel, BorderLayout.NORTH);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        ShipBankPanel shipBank = new ShipBankPanel();
        GridPanel grid = new GridPanel(true);

        // --- CREATE COLUMN LABELS (A–J) ---
        JPanel columnLabels = new JPanel(new GridLayout(1, 10));
        for (char c = 'A'; c <= 'J'; c++) {
            JLabel lbl = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            columnLabels.add(lbl);
        }

        // --- CREATE ROW LABELS (1–10) ---
        JPanel rowLabels = new JPanel(new GridLayout(10, 1));
        for (int i = 1; i <= 10; i++) {
            JLabel lbl = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            rowLabels.add(lbl);
        }

        // --- WRAP GRID + LABELS ---
        JPanel labeledGrid = new JPanel(new BorderLayout());
        labeledGrid.add(columnLabels, BorderLayout.NORTH);
        labeledGrid.add(rowLabels, BorderLayout.WEST);
        labeledGrid.add(grid, BorderLayout.CENTER);

        // Border and wrapper
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        gridWrapper.add(labeledGrid, BorderLayout.CENTER);

        // Ship panel wrapper
        JPanel shipBankWrapper = new JPanel(new BorderLayout());
        shipBankWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        shipBankWrapper.add(shipBank, BorderLayout.CENTER);

        // Add spacing between components
        container.add(Box.createRigidArea(new Dimension(20, 0)));
        container.add(shipBankWrapper);
        container.add(Box.createRigidArea(new Dimension(40, 0)));
        container.add(gridWrapper);
        container.add(Box.createRigidArea(new Dimension(20, 0)));

        outer.add(container, BorderLayout.CENTER);
        add(outer, BorderLayout.CENTER);

        Blackboard.getInstance().addPropertyChangeListener(grid);
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
