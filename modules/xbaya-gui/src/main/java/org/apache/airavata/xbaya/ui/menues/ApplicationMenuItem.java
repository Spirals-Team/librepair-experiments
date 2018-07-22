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
package org.apache.airavata.xbaya.ui.menues;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.apache.airavata.xbaya.XBayaEngine;
import org.apache.airavata.xbaya.ui.experiment.LaunchApplicationWindow;

public class ApplicationMenuItem {
	
	private JMenu applicationMenu;

    private JMenuItem executeApplicationItem;
    
	private XBayaEngine engine;

	public ApplicationMenuItem(XBayaEngine engine) {
		this.engine = engine;

        createApplicationMenu();
	}

	private void createApplicationMenu() {
		createExecuteApplicationItem();
		this.applicationMenu = new JMenu("Run Applications");
		this.applicationMenu.add(this.executeApplicationItem);
		this.applicationMenu.addSeparator();
	}

	private void createExecuteApplicationItem() {
		this.executeApplicationItem = new JMenuItem("Execute Application");
		this.executeApplicationItem.addActionListener(new AbstractAction() {
			private LaunchApplicationWindow window;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (this.window == null) {
                    this.window = new LaunchApplicationWindow(ApplicationMenuItem.this.engine);
                }
                try {
                    this.window.show();
                } catch (Exception e1) {
                    ApplicationMenuItem.this.engine.getGUI().getErrorWindow().error(e1);
                }
            }
        });
		
	}

	public JMenu getMenu() {
		return this.applicationMenu;
	}

	

}
