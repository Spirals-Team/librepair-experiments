/*
 * Copyright 2016 JBoss Inc
 *
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
 */

package io.apiman.common.net.hawkular.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eric.wittmann@gmail.com
 */
public class DataPointLongBean {
    
    private Date timestamp;
    private long value;
    private Map<String, String> tags;
    
    /**
     * Constructor.
     */
    public DataPointLongBean() {
    }

    /**
     * Constructor.
     * @param timestamp
     * @param value
     */
    public DataPointLongBean(Date timestamp, long value) {
        setTimestamp(timestamp);
        setValue(value);
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the value
     */
    public long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(long value) {
        this.value = value;
    }
    
    /**
     * @param name
     * @param value
     */
    public void addTag(String name, String value) {
        if (this.tags == null) {
            this.tags = new HashMap<>();
        }
        this.tags.put(name, value);
    }
    
    /**
     * Removes a single tag.
     * @param name
     */
    public void removeTag(String name) {
        if (this.tags != null) {
            this.tags.remove(name);
        }
    }
    
    /**
     * Clear the tags for the data point.
     */
    public void clearTags() {
        if (this.tags != null) {
            this.tags.clear();
        }
    }

    /**
     * @return the tags
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
    
}
