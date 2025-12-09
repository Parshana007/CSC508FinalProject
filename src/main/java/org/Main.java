package org;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        ScreenManager screens = new ScreenManager();
        add(screens);

        Blackboard.getInstance().addPropertyChangeListener(screens);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main m = new Main();
            m.setTitle("Battleship");
            m.setSize(1200, 600);
            m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m.setResizable(false);
            m.setVisible(true);
        });
    }
}
//
//
//public class Main extends JFrame {
//    public Main() {
//        MainPanel mainPanel = new MainPanel();
//        this.add(mainPanel);
//
//        Blackboard.getInstance().addPropertyChangeListener(mainPanel);
//    }
//
//    public static void main(String[] args) {
//        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
//
//        // ask for room code
//
//        // "waiting for other player..."
//
//        // both players have joined - advance to ship placement screen
//
//        // players place ships and press submit
//
//        // "waiting for other player to submit..."
//
//        // both players have submitted ship placements - advance to main game screen
//
//
////        Blackboard.getInstance().addPropertyChangeListener("gameOver", evt -> {
////            String winner = (String) evt.getNewValue(); // "me" or "opponent"
////
////            SwingUtilities.invokeLater(() -> {
////                GameResultScreen screen = new GameResultScreen(
////                        winner,
////                        e -> restartGame() // restart
////                );
////                screen.setVisible(true);
////            });
////        });
//
//        SwingUtilities.invokeLater(() -> {
//            Main main = new Main();
//            main.setTitle("Battleship");
//            main.setSize(1200, 600);
//            main.setResizable(false);
//            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            main.setVisible(true);
//        });
//
////        MyPublisher publisher = new MyPublisher();
////        Blackboard.getInstance().addPropertyChangeListener(publisher);
//    }
//}