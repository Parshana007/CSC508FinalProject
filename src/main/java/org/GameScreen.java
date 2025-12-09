package org;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameScreen extends JPanel implements PropertyChangeListener {
    private ShipGridPanel myGrid;       // Your ship placement grid
    private OpponentGridPanel oppGrid;  // Grid where you guess

    public GameScreen() {
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


        // Opponent Player Grid
        oppGrid = new OpponentGridPanel();
        JPanel oppLabeled = wrapGridWithLabels(oppGrid);

        JPanel oppWrapper = new JPanel(new BorderLayout());
        oppWrapper.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));
        oppWrapper.add(oppLabeled, BorderLayout.CENTER);


        // My Player Grid
        myGrid = new ShipGridPanel(true); // editMode = true → movable ships
        JPanel myLabeled = wrapGridWithLabels(myGrid);

        JPanel myWrapper = new JPanel(new BorderLayout());
        myWrapper.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));
        myWrapper.add(myLabeled, BorderLayout.CENTER);


        // Add spacing between components
        container.add(Box.createRigidArea(new Dimension(20, 0)));
        container.add(oppWrapper);
        container.add(Box.createRigidArea(new Dimension(40, 0)));
        container.add(myWrapper);
        container.add(Box.createRigidArea(new Dimension(20, 0)));

        outer.add(container, BorderLayout.CENTER);
        add(outer, BorderLayout.CENTER);

        Blackboard.getInstance().addPropertyChangeListener(this);
        Blackboard.getInstance().addPropertyChangeListener(myGrid);
        Blackboard.getInstance().addPropertyChangeListener(oppGrid);
    }

    private JPanel wrapGridWithLabels(JPanel grid) {
        JPanel labeled = new JPanel(new BorderLayout());
        labeled.add(createColumnLabels(), BorderLayout.NORTH);
        labeled.add(createRowLabels(), BorderLayout.WEST);
        labeled.add(grid, BorderLayout.CENTER);
        return labeled;
    }

    private JPanel createColumnLabels() {
        // --- CREATE COLUMN LABELS (A–J) ---
        JPanel panel = new JPanel(new GridLayout(1, 10));
        for (char c = 'A'; c <= 'J'; c++) {
            JLabel lbl = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            panel.add(lbl);
        }
        return panel;
    }

    private JPanel createRowLabels() {
        // --- CREATE ROW LABELS (1–10) ---
        JPanel panel = new JPanel(new GridLayout(10, 1));
        for (int i = 1; i <= 10; i++) {
            JLabel lbl = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            panel.add(lbl);
        }
        return panel;
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
