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

        initialize();
    }

    private void initialize() {
        // Title
        JLabel titleLabel = new JLabel("Welcome to Battleship!");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
//        add(Box.createRigidArea(new Dimension(0, 30)));

        // Player name panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("PlayerName:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerNameField = new JTextField(15);
        playerNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        namePanel.add(nameLabel);
        namePanel.add(playerNameField);
        add(namePanel);
//        add(Box.createRigidArea(new Dimension(0, 20)));

        // Player mode panel
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup buttonGroup = new ButtonGroup();

        onePlayerButton = new JRadioButton("1 player", true);
        onePlayerButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        twoPlayerButton = new JRadioButton("2 player");
        twoPlayerButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

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
        roomCodeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomCodeField = new JTextField(12);
        roomCodeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomCodePanel.add(roomCodeLabel);
        roomCodePanel.add(roomCodeField);
        roomCodePanel.setVisible(false); // Initially hidden
        add(roomCodePanel);
        add(Box.createRigidArea(new Dimension(0, 40)));

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // startGame is the panel when clicking the button start game
        startButton.addActionListener(e -> startGame());
        add(startButton);

        // allows user to pick one player or two player mode
        // if they pick two player mode, only then will the room code panel be visible
        twoPlayerButton.addActionListener(e -> roomCodePanel.setVisible(true));
        onePlayerButton.addActionListener(e -> roomCodePanel.setVisible(false));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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