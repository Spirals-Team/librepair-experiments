/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package io.ballerina.messaging.broker.amqp.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Representation of an AMQP connection.
 */
public class ConnectionMetadata   {

    private @Valid Integer connectionId = null;
    private @Valid String connectedIp = null;
    private @Valid Integer channelCount = null;

    /**
     * The connection id. This is unique for each connection.
     **/
    public ConnectionMetadata connectionId(Integer connectionId) {
        this.connectionId = connectionId;
        return this;
    }


    @ApiModelProperty(required = true, value = "The connection id. This is unique for each connection.")
    @JsonProperty("connectionId")
    @NotNull
    public Integer getConnectionId() {
        return connectionId;
    }
    public void setConnectionId(Integer connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * The ip address of the connected client. This could be the same for multiple connections.
     **/
    public ConnectionMetadata connectedIp(String connectedIp) {
        this.connectedIp = connectedIp;
        return this;
    }


    @ApiModelProperty(required = true, value = "The ip address of the connected client. This could be the same for multiple connections.")
    @JsonProperty("connectedIp")
    @NotNull
    public String getConnectedIp() {
        return connectedIp;
    }
    public void setConnectedIp(String connectedIp) {
        this.connectedIp = connectedIp;
    }

    /**
     * Number of active channels registered for each connection
     **/
    public ConnectionMetadata channelCount(Integer channelCount) {
        this.channelCount = channelCount;
        return this;
    }


    @ApiModelProperty(required = true, value = "Number of active channels registered for each connection")
    @JsonProperty("channelCount")
    @NotNull
    public Integer getChannelCount() {
        return channelCount;
    }
    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConnectionMetadata connectionMetadata = (ConnectionMetadata) o;
        return Objects.equals(connectionId, connectionMetadata.connectionId) &&
               Objects.equals(connectedIp, connectionMetadata.connectedIp) &&
               Objects.equals(channelCount, connectionMetadata.channelCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionId, connectedIp, channelCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ConnectionMetadata {\n");

        sb.append("    connectionId: ").append(toIndentedString(connectionId)).append("\n");
        sb.append("    connectedIp: ").append(toIndentedString(connectedIp)).append("\n");
        sb.append("    channelCount: ").append(toIndentedString(channelCount)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
