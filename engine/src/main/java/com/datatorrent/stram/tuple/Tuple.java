/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.datatorrent.stram.tuple;

import com.datatorrent.bufferserver.packet.MessageType;
import com.datatorrent.bufferserver.util.Codec;

/**
 *
 * Basic object that is streamed<p>
 * <br>
 * Tuples are of the following type<br>
 * Data:<br>
 * Control: begin window, end window, reset window, end stream<br>
 * heartbeat: To be done, not a high priority<br>
 * <br>
 *
 * @since 0.3.2
 */
public class Tuple
{
  protected long windowId;
  private final MessageType type;

  public Tuple(MessageType t, long windowId)
  {
    type = t;
    this.windowId = windowId;
  }

  /**
   * @return the windowId
   */
  public long getWindowId()
  {
    return windowId;
  }

  public void setWindowId(long windowId)
  {
    this.windowId = windowId;
  }

  public final int getBaseSeconds()
  {
    return (int)(windowId >> 32);
  }

  /**
   * @return the type
   */
  public MessageType getType()
  {
    return type;
  }

  @Override
  public String toString()
  {
    return "type = " + type + " " + Codec.getStringWindowId(windowId);
  }

}
