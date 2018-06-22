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
package org.apache.airavata.xbaya.ui.dialogs.component;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.airavata.workflow.model.component.web.WebComponentRegistry;
import org.apache.airavata.xbaya.XBayaConstants;
import org.apache.airavata.xbaya.XBayaEngine;
import org.apache.airavata.xbaya.component.registry.ComponentRegistryLoader;
import org.apache.airavata.xbaya.ui.dialogs.XBayaDialog;
import org.apache.airavata.xbaya.ui.utils.ErrorMessages;
import org.apache.airavata.xbaya.ui.widgets.GridPanel;
import org.apache.airavata.xbaya.ui.widgets.XBayaLabel;
import org.apache.airavata.xbaya.ui.widgets.XBayaTextField;
import org.apache.airavata.xbaya.util.RegistryConstants;

public class WebResigtryWindow {

    private XBayaEngine engine;

    private ComponentRegistryLoader loader;

    private XBayaDialog dialog;

    private XBayaTextField urlTextField;

    /**
     * @param engine
     */
    public WebResigtryWindow(XBayaEngine engine) {
        this.engine = engine;
        this.loader = ComponentRegistryLoader.getLoader(this.engine, RegistryConstants.REGISTRY_TYPE_WEB);
        initGUI();
    }

    /**
     * Displays the dialog.
     */
    public void show() {
        if (this.urlTextField.getText().length() == 0) {
            this.urlTextField.setText(XBayaConstants.DEFAULT_WEB_REGISTRY.toString());
        }
        this.dialog.show();
    }

    private void hide() {
        this.dialog.hide();
    }

    private void ok() {
        String urlString = this.urlTextField.getText();

        if (urlString.length() == 0) {
            this.engine.getGUI().getErrorWindow().error(ErrorMessages.URL_EMPTY);
            return;
        }
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            this.engine.getGUI().getErrorWindow().error(ErrorMessages.URL_WRONG, e);
            return;
        }

        WebComponentRegistry registry = new WebComponentRegistry(url);

        hide();

        this.loader.load(registry);
    }

    /**
     * Initializes the GUI.
     */
    private void initGUI() {
        this.urlTextField = new XBayaTextField();
        XBayaLabel urlLabel = new XBayaLabel("URL", this.urlTextField);

        GridPanel infoPanel = new GridPanel();
        infoPanel.add(urlLabel);
        infoPanel.add(this.urlTextField);
        infoPanel.layout(1, 2, GridPanel.WEIGHT_NONE, 1);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        this.dialog = new XBayaDialog(this.engine.getGUI(), "Web Registry", infoPanel, buttonPanel);
        this.dialog.setDefaultButton(okButton);
    }
}