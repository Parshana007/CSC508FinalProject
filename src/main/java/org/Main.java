package org;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(boolean isAI) {

        ScreenManager screens = new ScreenManager(isAI);
        add(screens);
        Blackboard.getInstance().addPropertyChangeListener(screens);
    }

    public static void main(String[] args) {

        boolean isAI = false;
        if(args.length > 0 && args[0].equals("AI")){
            isAI=true;
            Blackboard.getInstance().setRoomID("AIROOMCODE");
            Blackboard.getInstance().initializeShips();
            Blackboard.getInstance().submitShipsPlacement();
            System.out.println("Ships Placed for AI" + Blackboard.getInstance().getPlayerState().getMyShips());
            Blackboard.getInstance().getGameFlow().setPhase(Phase.GUESSING);
        }

        final boolean aiFlag=isAI;



        SwingUtilities.invokeLater(() -> {
            Main m = new Main(aiFlag);
            m.setTitle("Battleship");
            m.setSize(1200, 600);
            m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m.setResizable(false);
            m.setVisible(true);
        });

        MQTTPublisher publisher = new MQTTPublisher();
        Blackboard.getInstance().addPropertyChangeListener(publisher);
        MQTTSubscriber subscriber = new MQTTSubscriber();
        Blackboard.getInstance().addPropertyChangeListener(subscriber);

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