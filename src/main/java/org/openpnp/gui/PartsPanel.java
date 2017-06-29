/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import org.openpnp.gui.components.AutoSelectTextTable;
import org.openpnp.gui.support.ActionGroup;
import org.openpnp.gui.support.Helpers;
import org.openpnp.gui.support.Icons;
import org.openpnp.gui.support.IdentifiableListCellRenderer;
import org.openpnp.gui.support.IdentifiableTableCellRenderer;
import org.openpnp.gui.support.MessageBoxes;
import org.openpnp.gui.support.PackagesComboBoxModel;
import org.openpnp.gui.support.Wizard;
import org.openpnp.gui.support.WizardContainer;
import org.openpnp.gui.tablemodel.PartsTableModel;
import org.openpnp.model.Configuration;
import org.openpnp.model.Location;
import org.openpnp.model.Part;
import org.openpnp.spi.Feeder;
import org.openpnp.spi.FiducialLocator;
import org.openpnp.spi.Nozzle;
import org.openpnp.spi.PartAlignment;
import org.openpnp.util.MovableUtils;
import org.openpnp.util.UiUtils;
import org.pmw.tinylog.Logger;

@SuppressWarnings("serial")
public class PartsPanel extends JPanel implements WizardContainer {


    private static final String PREF_DIVIDER_POSITION = "PartsPanel.dividerPosition";
    private static final int PREF_DIVIDER_POSITION_DEF = -1;
    private Preferences prefs = Preferences.userNodeForPackage(PartsPanel.class);

    final private Configuration configuration;
    final private Frame frame;

    private PartsTableModel tableModel;
    private TableRowSorter<PartsTableModel> tableSorter;
    private JTextField searchTextField;
    private JTable table;
    private ActionGroup singleSelectionActionGroup;
    private ActionGroup multiSelectionActionGroup;

    public PartsPanel(Configuration configuration, Frame frame) {
        this.configuration = configuration;
        this.frame = frame;

        singleSelectionActionGroup = new ActionGroup(deletePartAction, pickPartAction);
        singleSelectionActionGroup.setEnabled(false);
        multiSelectionActionGroup = new ActionGroup(deletePartAction);
        multiSelectionActionGroup.setEnabled(false);

        setLayout(new BorderLayout(0, 0));
        tableModel = new PartsTableModel();
        tableSorter = new TableRowSorter<>(tableModel);

        JPanel toolbarAndSearch = new JPanel();
        add(toolbarAndSearch, BorderLayout.NORTH);
        toolbarAndSearch.setLayout(new BorderLayout(0, 0));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolbarAndSearch.add(toolBar);

        JPanel panel_1 = new JPanel();
        toolbarAndSearch.add(panel_1, BorderLayout.EAST);

        JLabel lblSearch = new JLabel("Search");
        panel_1.add(lblSearch);

        searchTextField = new JTextField();
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
        });
        panel_1.add(searchTextField);
        searchTextField.setColumns(15);

        JComboBox packagesCombo = new JComboBox(new PackagesComboBoxModel());
        packagesCombo.setRenderer(new IdentifiableListCellRenderer<org.openpnp.model.Package>());

        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setContinuousLayout(true);
        splitPane
                .setDividerLocation(prefs.getInt(PREF_DIVIDER_POSITION, PREF_DIVIDER_POSITION_DEF));
        splitPane.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                prefs.putInt(PREF_DIVIDER_POSITION, splitPane.getDividerLocation());
            }
        });
        add(splitPane, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        table = new AutoSelectTextTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultEditor(org.openpnp.model.Package.class,
                new DefaultCellEditor(packagesCombo));
        table.setDefaultRenderer(org.openpnp.model.Package.class,
                new IdentifiableTableCellRenderer<org.openpnp.model.Package>());

        table.setRowSorter(tableSorter);
        table.getTableHeader().setDefaultRenderer(new MultisortTableHeaderCellRenderer());
        splitPane.setLeftComponent(new JScrollPane(table));
        splitPane.setRightComponent(tabbedPane);
        
        JButton btnNewPart = toolBar.add(newPartAction);
        btnNewPart.setToolTipText("");
        JButton btnDeletePart = toolBar.add(deletePartAction);
        btnDeletePart.setToolTipText("");
        toolBar.addSeparator();
        toolBar.add(pickPartAction);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                List<Part> selections = getSelections();

                if (selections.size() > 1) {
                    singleSelectionActionGroup.setEnabled(false);
                    multiSelectionActionGroup.setEnabled(true);
                }
                else {
                    multiSelectionActionGroup.setEnabled(false);
                    singleSelectionActionGroup.setEnabled(!selections.isEmpty());
                }

                Part part = getSelection();

                tabbedPane.removeAll();

                if (part != null) {
                    for (PartAlignment partAlignment : Configuration.get().getMachine().getPartAlignments()) {
                        Wizard wizard=partAlignment.getPartConfigurationWizard(part);
                        if (wizard != null) {
                            JPanel panel = new JPanel();
                            panel.setLayout(new BorderLayout());
                            panel.add(wizard.getWizardPanel());
                            tabbedPane.add(wizard.getWizardName(), new JScrollPane(panel));
                            wizard.setWizardContainer(PartsPanel.this);
                        }
                    }
                    
                    FiducialLocator fiducialLocator =
                            Configuration.get().getMachine().getFiducialLocator();
                    Wizard wizard = fiducialLocator.getPartConfigurationWizard(part);
                    if (wizard != null) {
                        JPanel panel = new JPanel();
                        panel.setLayout(new BorderLayout());
                        panel.add(wizard.getWizardPanel());
                        tabbedPane.add(wizard.getWizardName(), new JScrollPane(panel));
                        wizard.setWizardContainer(PartsPanel.this);
                    }
                }

                revalidate();
                repaint();
            }
        });
    }

    private Part getSelection() {
        List<Part> selections = getSelections();
        if (selections.size() != 1) {
            return null;
        }
        return selections.get(0);
    }

    private List<Part> getSelections() {
        List<Part> selections = new ArrayList<>();
        for (int selectedRow : table.getSelectedRows()) {
            selectedRow = table.convertRowIndexToModel(selectedRow);
            selections.add(tableModel.getPart(selectedRow));
        }
        return selections;
    }

    private void search() {
        RowFilter<PartsTableModel, Object> rf = null;
        // If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter("(?i)" + searchTextField.getText().trim());
        }
        catch (PatternSyntaxException e) {
            Logger.warn("Search failed", e);
            return;
        }
        tableSorter.setRowFilter(rf);
    }

    public final Action newPartAction = new AbstractAction() {
        {
            putValue(SMALL_ICON, Icons.add);
            putValue(NAME, "New Part...");
            putValue(SHORT_DESCRIPTION, "Create a new part, specifying it's ID.");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (Configuration.get().getPackages().size() == 0) {
                MessageBoxes.errorBox(getTopLevelAncestor(), "Error",
                        "There are currently no packages defined in the system. Please create at least one package before creating a part.");
                return;
            }

            String id;
            while ((id = JOptionPane.showInputDialog(frame,
                    "Please enter an ID for the new part.")) != null) {
                if (configuration.getPart(id) != null) {
                    MessageBoxes.errorBox(frame, "Error", "Part ID " + id + " already exists.");
                    continue;
                }
                Part part = new Part(id);

                part.setPackage(Configuration.get().getPackages().get(0));

                configuration.addPart(part);
                tableModel.fireTableDataChanged();
                Helpers.selectLastTableRow(table);
                break;
            }
        }
    };

    public final Action deletePartAction = new AbstractAction() {
        {
            putValue(SMALL_ICON, Icons.delete);
            putValue(NAME, "Delete Part");
            putValue(SHORT_DESCRIPTION, "Delete the currently selected part.");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            List<Part> selections = getSelections();
            List<String> ids = selections.stream().map(Part::getId).collect(Collectors.toList());
            String formattedIds;
            if (ids.size() <= 3) {
                formattedIds = String.join(", ", ids);
            }
            else {
                formattedIds = String.join(", ", ids.subList(0, 3)) + ", and " + (ids.size() - 3) + " others";
            }
            
            int ret = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                    "Are you sure you want to delete " + formattedIds + "?",
                    "Delete " + selections.size() + " parts?", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                for (Part part : selections) {
                    Configuration.get().removePart(part);
                }
            }
        }
    };

    public final Action pickPartAction = new AbstractAction() {
        {
            putValue(SMALL_ICON, Icons.pick);
            putValue(NAME, "Pick Part");
            putValue(SHORT_DESCRIPTION, "Pick the selected part from the first available feeder.");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            UiUtils.submitUiMachineTask(() -> {
                Nozzle nozzle = MainFrame.get().getMachineControls().getSelectedNozzle();
                Part part = getSelection();
                Feeder feeder = null;
                // find a feeder to feed
                for (Feeder f : Configuration.get().getMachine().getFeeders()) {
                    if (f.isEnabled() && f.getPart().equals(part)) {
                        feeder = f;
                    }
                }
                if (feeder == null) {
                    throw new Exception("No valid feeder found for " + part.getId());
                }
                // feed the chosen feeder
                feeder.feed(nozzle);
                // pick the part
                Location pickLocation = feeder.getPickLocation();
                MovableUtils.moveToLocationAtSafeZ(nozzle, pickLocation);
                nozzle.pick(part);
                nozzle.moveToSafeZ();
            });
        }
    };

    @Override
    public void wizardCompleted(Wizard wizard) {}

    @Override
    public void wizardCancelled(Wizard wizard) {}
}
