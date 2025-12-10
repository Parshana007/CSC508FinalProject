package org;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *  Represents movement of a ship when in the placement screen. Moves the ship based on which arrow key is pressed,
 *  using the key bindings in MyGridPanel.
 */
public class MoveShipAction extends AbstractAction {
    int dx, dy;
    MyGridPanel myGridPanel;

    MoveShipAction(int dx, int dy, MyGridPanel myGridPanel) {
        this.dx = dx;
        this.dy = dy;
        this.myGridPanel = myGridPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myGridPanel.moveActiveShip(dx, dy);
    }


}