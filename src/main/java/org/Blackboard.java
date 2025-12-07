package org;

import java.beans.PropertyChangeSupport;

public class Blackboard extends PropertyChangeSupport {

    private static volatile Blackboard instance;

    private Blackboard() {
        super(new Object());
    }

    public static Blackboard getInstance() {
        if (instance == null) {
            synchronized (Blackboard.class) {
                if (instance == null) {
                    instance = new Blackboard();
                }
            }
        }
        return instance;
    }
}
