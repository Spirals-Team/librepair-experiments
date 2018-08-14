package edu.boisestate.cs471.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import edu.boisestate.cs471.util.EventType;
import edu.boisestate.cs471.util.interfaces.IEventReceiver;

public class GuiListener implements ActionListener, ItemListener, PopupMenuListener {

    private final IEventReceiver mReceiver;
    private final HashMap<Object, EventType> mEventTypes = new HashMap<>();
    private final HashMap<Object, Object[]> mEventArgs = new HashMap<>();

    public GuiListener(IEventReceiver receiver) {
        if (null == receiver) {
            throw new IllegalArgumentException("Receiver cannot be null");
        }
        mReceiver = receiver;
    }
    
    
    public void listenToComboBoxIndex(JComboBox<?> item, EventType eventType) {
        item.setActionCommand(eventType.toString());
        item.addPopupMenuListener(this);
    }
    
    public void listenTo(AbstractButton item, EventType eventType, Object ...eventArgs) {
        if (eventArgs.length > 0) {
            mEventTypes.put(item, eventType);
            mEventArgs.put(item, eventArgs);
        }
        else {
            item.setActionCommand(eventType.toString());
        }
        item.addActionListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the event comes from a source with mapped arguments.
        Object source = e.getSource();
        if (mEventTypes.containsKey(source)) {
            EventType eventType = mEventTypes.get(source);
            Object[] args = mEventArgs.get(source);
            mReceiver.onEvent(eventType, args);
            return;
        }
        
        // If no arguments exist for this event, convert the action command to an event type
        String command = e.getActionCommand();
        try {
            EventType asEnum = EventType.valueOf(command);
            mReceiver.onEvent(asEnum);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Warning: Ignoring unexpected command value " + e.getActionCommand());
            return;
        }
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        // Ignore this event. The user canceled the drop down menu of the sorting algorithm selection box.
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        // The user may have selected a different algorithm from the drop down menu
        if (e.getSource() instanceof JComboBox<?>) {
            JComboBox<?> box = (JComboBox<?>) e.getSource();
            String command = box.getActionCommand();
            try {
                EventType asEnum = EventType.valueOf(command);
                mReceiver.onEvent(asEnum, box.getSelectedIndex());
            }
            catch (IllegalArgumentException ex) {
                System.out.println("Warning: Ignoring unexpected command value " + box.getActionCommand());
                return;
            }
        }
        else {
            System.err.println("Event popupMenuWillBecomeInvisible() must come from a JComboBox");
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        // Ignore this event. The user opened the drop down menu of the sorting algorithm selection box.
    }

}
