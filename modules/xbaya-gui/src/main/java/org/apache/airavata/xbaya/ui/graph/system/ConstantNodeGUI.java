/**
 *
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
package org.apache.airavata.xbaya.ui.graph.system;

import org.apache.airavata.workflow.model.graph.system.ConstantNode;
import org.apache.airavata.xbaya.ui.XBayaGUI;
import org.apache.airavata.xbaya.ui.dialogs.graph.system.ConstantConfigurationDialog;
import org.apache.airavata.xbaya.ui.utils.ErrorMessages;

public class ConstantNodeGUI extends ConfigurableNodeGUI {

    private static final String CONFIG_AREA_STRING = "Value";

    private ConstantNode node;

    private ConstantConfigurationDialog configurationWindow;

    /**
     * @param node
     */
    public ConstantNodeGUI(ConstantNode node) {
        super(node);
        this.node = node;
        setConfigurationText(CONFIG_AREA_STRING);
    }

    /**
     * Shows a configuration window when a user click the configuration area.
     * 
     * @param engine
     */
    @Override
    protected void showConfigurationDialog(XBayaGUI xbayaGUI) {
        if (this.node.isConnected()) {
            if (this.configurationWindow == null) {
                this.configurationWindow = new ConstantConfigurationDialog(this.node, xbayaGUI);
            }
            this.configurationWindow.show();

        } else {
        	xbayaGUI.getErrorWindow().info(ErrorMessages.CONSTANT_NOT_CONNECTED_WARNING);
        }
    }
}