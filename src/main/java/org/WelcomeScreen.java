package org;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JPanel {
    private JTextField playerNameField;
    private JRadioButton onePlayerButton;
    private JRadioButton twoPlayerButton;
    private JLabel roomCodeLabel;
    private JTextField roomCodeField;
    private JPanel roomCodePanel;

    public WelcomeScreen() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        this.setFocusable(true);
        this.requestFocusInWindow();

        initializeComponents();
    }

    private void initializeComponents() {
        // Title
        JLabel titleLabel = new JLabel("Welcome to Battle..");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 30)));

        // Player name panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("PlayerName:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playerNameField = new JTextField(15);
        playerNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        namePanel.add(nameLabel);
        namePanel.add(playerNameField);
        add(namePanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Player mode panel
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup buttonGroup = new ButtonGroup();

        onePlayerButton = new JRadioButton("1 player", true);
        onePlayerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        twoPlayerButton = new JRadioButton("2 player");
        twoPlayerButton.setFont(new Font("Arial", Font.PLAIN, 16));

        buttonGroup.add(onePlayerButton);
        buttonGroup.add(twoPlayerButton);

        modePanel.add(onePlayerButton);
        modePanel.add(Box.createRigidArea(new Dimension(30, 0)));
        modePanel.add(twoPlayerButton);
        add(modePanel);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Room code panel (initially hidden)
        roomCodePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        roomCodeLabel = new JLabel("RoomCode:");
        roomCodeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roomCodeField = new JTextField(12);
        roomCodeField.setFont(new Font("Arial", Font.PLAIN, 14));
        roomCodePanel.add(roomCodeLabel);
        roomCodePanel.add(roomCodeField);
        roomCodePanel.setVisible(false); // Initially hidden
        add(roomCodePanel);
        add(Box.createRigidArea(new Dimension(0, 40)));

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> startGame());
        add(startButton);

        // Add action listeners for radio buttons
        twoPlayerButton.addActionListener(e -> roomCodePanel.setVisible(true));
        onePlayerButton.addActionListener(e -> roomCodePanel.setVisible(false));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void startGame() {
        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a player name!");
            return;
        }

        if (twoPlayerButton.isSelected()) {
            String roomCode = roomCodeField.getText().trim();
            if (roomCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a room code!");
                return;
            }
            System.out.println("Starting 2-player game: " + playerName + " in room " + roomCode);
        } else {
            System.out.println("Starting 1-player game: " + playerName);
        }

        // Here you would start the actual game
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Battle Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.add(new WelcomeScreen());
            frame.setVisible(true);
        });
    }
}