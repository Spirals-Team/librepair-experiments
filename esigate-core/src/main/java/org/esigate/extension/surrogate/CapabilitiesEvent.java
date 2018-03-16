/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.esigate.extension.surrogate;

import java.util.ArrayList;
import java.util.List;

import org.esigate.events.Event;

/**
 * This event is fired on startup to register all installed capabilities.
 * 
 * @author Nicolas Richeton
 * 
 */
public class CapabilitiesEvent extends Event {
    /**
     * Current capability list. An extension can update this list to declare additional capabilities.
     */
    private final List<String> capabilities = new ArrayList<>();

    public List<String> getCapabilities() {
        return capabilities;
    }
}
