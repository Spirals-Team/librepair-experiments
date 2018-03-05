package com.oxygenxml.git.service;

import com.oxygenxml.git.view.event.ChangeEvent;

/**
 * An adapter.
 *  
 * @author alex_jitianu
 */
public class GitEventAdapter implements GitEventListener {

  @Override
  public void repositoryChanged() {
    // Adapter. Empty implementation.
  }

  @Override
  public void stateChanged(ChangeEvent changeEvent) {
    // Adapter. Empty implementation.
  }

}
