/*
 * ObserverInterface.java
 *
 * Created on 06 May 2006, 13:07
 *
 * To change this template, choose Tools | Template Manager
 * editor.
 */

package framework.mvc;

import java.util.Observable;

/**
 * Observer Interface.
 */
public interface ObserverInterface {

    public void update(Observable observable, Object object);
}
