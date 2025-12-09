package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GamePanel extends JPanel implements PropertyChangeListener {

    public GamePanel() {

        // Set layout first
        setLayout(new BorderLayout());

        // Top label
        JLabel topLabel = new JLabel("Select a ship to place", SwingConstants.CENTER);
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // top/bottom padding
        topLabel.setOpaque(true); // optional if you want background color
        add(topLabel, BorderLayout.NORTH);

        // Outer container with top and bottom padding
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Inner horizontal container
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

//        ShipBankPanel shipBank = new ShipBankPanel();
        GridPanel grid = new GridPanel(false);
//
//        // Wrap panels with brown borders
//        JPanel shipBankWrapper = new JPanel(new BorderLayout());
//        shipBankWrapper.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
//                BorderFactory.createEmptyBorder(0, 0, 0, 0)
//        ));
//        shipBankWrapper.add(shipBank, BorderLayout.CENTER);

//        OpponentPanel opponentPanel = new OpponentPanel();

        GridPanel gridOpponent = new GridPanel(true);
        JPanel oppGridWrapper = new JPanel(new BorderLayout());
        oppGridWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        oppGridWrapper.add(gridOpponent, BorderLayout.CENTER);

        Blackboard.getInstance().addPropertyChangeListener(gridOpponent);

        //TODO: this needs to come from the previous screen to populate so maybe instead of new class it just repaint this portion
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        gridWrapper.add(grid, BorderLayout.CENTER);

        // Add spacing between and around panels
        container.add(Box.createRigidArea(new Dimension(20, 0)));
        container.add(oppGridWrapper);
        container.add(Box.createRigidArea(new Dimension(40, 0))); // brown gap could go here instead
        container.add(gridWrapper);
        container.add(Box.createRigidArea(new Dimension(20, 0)));

        outer.add(container, BorderLayout.CENTER);

        add(outer, BorderLayout.CENTER);
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
