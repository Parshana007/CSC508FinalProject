package org;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the initial screen. Prompts users to enter their name, select # of players, and if 2 players, enter a room code.
 */

public class WelcomeScreen extends JPanel {

    public WelcomeScreen() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        this.setFocusable(true);
        this.requestFocusInWindow();

        initialize();
    }

    private void initialize() {
        JLabel roomCodeLabel;
        JTextField playerNameField;
        JTextField roomCodeField;

        // Title
        JLabel titleLabel = new JLabel("Welcome to Battleship!");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        // Player name panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("PlayerName:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerNameField = new JTextField(15);
        playerNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        namePanel.add(nameLabel);
        namePanel.add(playerNameField);

        // Room code panel
        JPanel roomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roomCodeLabel = new JLabel("RoomCode:");
        roomCodeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomCodeField = new JTextField(12);
        roomCodeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomPanel.add(roomCodeLabel);
        roomPanel.add(roomCodeField);

        // Add panels
        add(namePanel);
        add(roomPanel);

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // when start button is clicked
        startButton.addActionListener(e -> {
            String playerName = playerNameField.getText();
            Blackboard.getInstance().setMyPlayer(playerName);
            System.out.println(playerName);

            String roomCode = roomCodeField.getText();
            Blackboard.getInstance().setRoomID(roomCode);
            System.out.println(roomCode);

            if (Blackboard.getInstance().getGameFlow().isOpponentReadyRoom()) {
                Blackboard.getInstance().getGameFlow().setPhase(Phase.PLACEMENT);
            } else {
                Blackboard.getInstance().getGameFlow().setPhase(Phase.WAITFOROPPONENTROOMID);
            }
        });

        add(startButton);

    }
}