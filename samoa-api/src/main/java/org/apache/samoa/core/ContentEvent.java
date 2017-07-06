package org.apache.samoa.core;

/*
 * #%L
 * SAMOA
 * %%
 * Copyright (C) 2014 - 2015 Apache Software Foundation
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * The Interface ContentEvent.
 */
public interface ContentEvent extends java.io.Serializable {

  /**
   * Gets the content event key.
   * 
   * @return the key
   */
  public String getKey();

  /**
   * Sets the content event key.
   * 
   * @param key
   *          string
   */
  public void setKey(String key);

  public boolean isLastEvent();
}
