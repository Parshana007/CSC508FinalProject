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
//    private static final String PLACE = "PLACE";
    private static final String WON = "WON";
    private static final String LOST = "LOST";
    private static final String WELCOMESCREEN = "WELCOMESCREEN";


    public ScreenManager() {
        setLayout(layout);

        // Add screens
        add(new WelcomeScreen(), WELCOMESCREEN);
        add(new GameResultScreen(Phase.WONGAME), WON);
        add(new GameResultScreen(Phase.LOSTGAME), LOST);

//        add(new RoomScreen(), ROOM);
//        add(new WaitingScreen("Waiting for other player to join..."), WAIT);
//        add(new PlacementScreen(), PLACE);
//        add(new GameScreen(), GAME);

        // which screen to start with
        layout.show(this, WELCOMESCREEN);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"phase".equals(evt.getPropertyName())) return;

        Phase newPhase = (Phase) evt.getNewValue();

        switch (newPhase) {
            case WONGAME -> layout.show(this, WON);
            case LOSTGAME -> layout.show(this, LOST);

//            case WAITFOROPPONENTROOMID -> layout.show(this, WAIT);
//            case PLACEMENT -> layout.show(this, PLACE);
//            case WAITFOROPPONENTPLACEMENT ->
//                    layout.show(this, WAIT); // reuse waiting screen
//            case GUESSING -> layout.show(this, GAME);
        }
    }
}
