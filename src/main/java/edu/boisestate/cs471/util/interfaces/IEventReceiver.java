package edu.boisestate.cs471.util.interfaces;

import edu.boisestate.cs471.util.EventType;

public interface IEventReceiver {

    void onEvent(EventType type, Object ...args);
}
