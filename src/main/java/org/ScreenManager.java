package org;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ScreenManager extends JPanel implements PropertyChangeListener {

    private CardLayout layout = new CardLayout();

    // identifiers
//    private static final String ROOM = "ROOM";
//    private static final String WAIT = "WAIT";
    private static final String PLACE = "PLACE";
    private static final String WON = "WON";
    private static final String LOST = "LOST";
    private static final String WELCOMESCREEN = "WELCOMESCREEN";
    private static final String GAME = "GAME";


    public ScreenManager(boolean isAI) {
        Blackboard.getInstance().addPropertyChangeListener(this);
        setLayout(layout);

        // Add screens
        add(new WelcomeScreen(), WELCOMESCREEN);
        add(new GameResultScreen(Phase.WONGAME), WON);
        add(new GameResultScreen(Phase.LOSTGAME), LOST);

//        add(new RoomScreen(), ROOM);
//        add(new WaitingScreen("Waiting for other player to join..."), WAIT);
        add(new PlacementScreen(), PLACE);
        add(new GameScreen(), GAME);
        if (isAI) {
            layout.show(this, GAME);
        }
        else {
        // which screen to start with
        layout.show(this, WELCOMESCREEN);}
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"phase".equals(evt.getPropertyName())) return;

        Phase newPhase = (Phase) evt.getNewValue();
        
        switch (newPhase) {
            case WONGAME -> layout.show(this, WON);
            case LOSTGAME -> layout.show(this, LOST);

            case WAITFOROPPONENTROOMID, WAITFOROPPONENTPLACEMENT -> showWaitingPopup();
            case PLACEMENT -> {
                layout.show(this, PLACE);
                if (waitingDialog != null) {
                    waitingDialog.dispose();
                    waitingDialog = null;
                }
            }
            case GUESSING -> {
                layout.show(this, GAME);
                if (waitingDialog != null) {
                    waitingDialog.dispose();
                    waitingDialog = null;
                }
            }
            case WAITFOROPPONENTGUESS -> {
                showWaitingPopup();
                layout.show(this, GAME);
            }
        }
    }

    private JDialog waitingDialog;

    private void showWaitingPopup() {
        // Only create if not already open
        if (waitingDialog != null && waitingDialog.isShowing()) return;

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        waitingDialog = new JDialog(mainFrame, "Waiting", true);
        waitingDialog.add(new JLabel("Waiting for opponent...", SwingConstants.CENTER));
        waitingDialog.setSize(300, 150);
        waitingDialog.setLocationRelativeTo(mainFrame);

        // Show popup (on EDT)
        SwingUtilities.invokeLater(() -> waitingDialog.setVisible(true));
    }

}
