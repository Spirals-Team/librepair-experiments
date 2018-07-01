package org.eclipse.internal.cpacep.launcher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.cpacep.util.CPACEPConnector;
import org.eclipse.cpacep.util.FileHandler;
import org.eclipse.cpacep.util.StringHandler;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;


public class MainLaunchingTab extends AbstractLaunchConfigurationTab {

    private Text executableText;
    private Button executableButton;
    private Text sourceText;
    private Button sourceFileButton;
    private Combo specificationCombo;
    private Combo configurationCombo;
    private Text commandLineText;
    private boolean isComboEnable;
    private boolean isUpdateComboData;
    private String dummySpace = "                                                          ";

    private ArrayList<String> specificationData;
    private ArrayList<String> configurationData;

    ModifyListener modifyListener = new ModifyListener() {
	@Override
	public void modifyText(ModifyEvent e) {
	    if (executableText != null) {
		updateComboBox();
	    }
	    scheduleUpdateJob();
	}
    };

    @Override
    public boolean canSave() {
	return true;
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
	setMessage(null);
	setErrorMessage(null);
	return true;
    }

    @Override
    public void createControl(Composite parent) {
	// TODO Auto-generated method stub
	updateLaunchConfigurationDialog();
	Font font = parent.getFont();
	Composite comp = new Composite(parent, SWT.NONE);
	setControl(comp);
	GridLayout topLayout = new GridLayout();
	topLayout.verticalSpacing = 7;
	topLayout.horizontalSpacing = 7;
	topLayout.numColumns = 3;
	comp.setLayout(topLayout);
	comp.setFont(font);

	createVerticalSpacer(comp, 3);

	new Label(comp, SWT.NONE).setText(Messages.MainLaunchingTab_labelCPAChecker);
	executableText = new Text(comp, SWT.SINGLE | SWT.BORDER);
	GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
	gridData.horizontalSpan = 2;
	executableText.setLayoutData(gridData);
	executableText.addModifyListener(modifyListener);

	Composite tempBrowseLayout = new Composite(comp, SWT.NONE);
	gridData = new GridData(GridData.FILL_HORIZONTAL);
	gridData.horizontalSpan = 3;
	tempBrowseLayout.setLayoutData(gridData);

	GridLayout browseButtonLayout = new GridLayout(4, false);
	browseButtonLayout.marginHeight = browseButtonLayout.marginWidth = 0;
	tempBrowseLayout.setLayout(browseButtonLayout);

	Label tempLbl = new Label(tempBrowseLayout, SWT.NONE);
	tempLbl.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));

	executableButton = createPushButton(tempBrowseLayout, Messages.MainLaunchingTab_labelBrowse, null);
	executableButton.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		CPACExecutableButtonSelected();
	    }
	});

	new Label(comp, SWT.NONE).setText(Messages.MainLaunchingTab_labelFile);
	sourceText = new Text(comp, SWT.SINGLE | SWT.BORDER);
	gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
	gridData.horizontalSpan = 2;
	sourceText.setLayoutData(gridData);
	sourceText.addModifyListener(modifyListener);
	sourceText.setFont(font);

	Composite tempButtonLayout = new Composite(comp, SWT.NONE);
	gridData = new GridData(GridData.FILL_HORIZONTAL);
	gridData.horizontalSpan = 3;
	tempButtonLayout.setLayoutData(gridData);

	GridLayout buttonLayout = new GridLayout(4, false);
	buttonLayout.marginHeight = buttonLayout.marginWidth = 0;
	tempButtonLayout.setLayout(buttonLayout);

	Label tempLbl2 = new Label(tempButtonLayout, SWT.NONE);
	tempLbl2.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));

	sourceFileButton = createPushButton(tempButtonLayout, Messages.MainLaunchingTab_sourcesBrowse, null);
	sourceFileButton.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		sourcesButtonSelected();
	    }
	});

	Composite specLabelLayout = new Composite(comp, SWT.NONE);
	gridData = new GridData(GridData.FILL_HORIZONTAL);
	gridData.horizontalSpan = 4;
	specLabelLayout.setLayoutData(gridData);

	GridLayout spcLableGrid = new GridLayout(3, true);
	spcLableGrid.numColumns = 2;
	spcLableGrid.marginHeight = spcLableGrid.marginWidth = 0;
	specLabelLayout.setLayout(spcLableGrid);

	// new Label(specLabelLayout, SWT.NONE).setLayoutData(new GridData(SWT.END,
	// SWT.CENTER, false, false));
	Label spcLabel = new Label(specLabelLayout, SWT.NONE);
	spcLabel.setText("Specification");
	GridData specGridData = new GridData();
	specGridData.horizontalIndent = 194;
	spcLabel.setLayoutData(specGridData);

	Label confLabel = new Label(specLabelLayout, SWT.NONE);
	confLabel.setText("Configuration");
	GridData confGridData = new GridData();
	confGridData.horizontalIndent = 187;
	confLabel.setLayoutData(confGridData);

	new Label(comp, SWT.NONE).setText(Messages.MainLaunchingTab_labelProgramArgs);

	specificationCombo = SWTFactory.createCombo(comp, SWT.DROP_DOWN, 1, null);
	configurationCombo = SWTFactory.createCombo(comp, SWT.DROP_DOWN, 1, null);
	specificationCombo.setEnabled(false);
	configurationCombo.setEnabled(false);
	configurationCombo.addModifyListener(modifyListener);
	specificationCombo.addModifyListener(modifyListener);

	new Label(comp, SWT.NONE).setText(Messages.MainLaunchingTab_labelCommandLineArgs);
	commandLineText = new Text(comp, SWT.SINGLE | SWT.BORDER);
	gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
	gridData.horizontalSpan = 2;
	commandLineText.setLayoutData(gridData);
	commandLineText.addModifyListener(modifyListener);
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	// TODO Auto-generated method stub
	System.out.println("Inside defaults");

    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
	// TODO Auto-generated method stub
	try {
	    executableText.setText(
		    configuration.getAttribute(CPACEPConnector.LC_CPACEP_EXECUTABLE, CPACEPConnector.NO_VALUE));
	    sourceText.setText(configuration.getAttribute(CPACEPConnector.LC_CPACEP_SOURCE, CPACEPConnector.NO_VALUE));
	    commandLineText
		    .setText(configuration.getAttribute(CPACEPConnector.LC_CPACEP_CMD, CPACEPConnector.NO_VALUE));
	    isComboEnable = configuration.getAttribute(CPACEPConnector.LC_CPACEP_ENABLE_COMBO, false);
	    specificationCombo.setText(configuration.getAttribute(CPACEPConnector.LC_CPACEP_SPECIFICATION,
		    Messages.MainLaunchingTab_specificationCombo));
	    configurationCombo.setText(configuration.getAttribute(CPACEPConnector.LC_CPACEP_CONFIGURATION,
		    Messages.MainLaunchingTab_configurationCombo));
	    if (isComboEnable) {
		updateComboBox();
	    }
	} catch (CoreException e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
	// TODO Auto-generated method stub

	if (isDirty()) {
	    String exec = executableText.getText().trim();
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_EXECUTABLE,
		    exec.toString().length() == 0 ? null : exec);
	    String src = sourceText.getText().trim();
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_SOURCE, src.toString().length() == 0 ? null : src);
	    String spec = specificationCombo.getText().trim();
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_SPECIFICATION,
		    spec.toString().length() == 0 ? null : spec);
	    String conf = configurationCombo.getText().trim();
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_CONFIGURATION,
		    conf.toString().length() == 0 ? null : conf);
	    String cmd = commandLineText.getText().trim();
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_CMD, cmd.toString().length() == 0 ? null : cmd);
	    configuration.setAttribute(CPACEPConnector.LC_CPACEP_ENABLE_COMBO, true);
	}

    }

    @Override
    public String getName() {
	// TODO Auto-generated method stub
	return Messages.MainLaunchingTab_name;
    }

    private void CPACExecutableButtonSelected() {
	FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
	dialog.setText(Messages.MainLaunchingTab_dialogCPA);
	dialog.setFilterNames(new String[] { "cpa*" }); //$NON-NLS-1$
	String file = dialog.open();
	if (file != null) {
	    executableText.setText(file);
	}
    }

    private void updateComboBox() {
	if (!isUpdateComboData && !executableText.getText().isEmpty()) {
	    Path homeDir = StringHandler.getHomePath(executableText.getText().trim());
	    Path configDir = homeDir.resolve("config");
	    try {
		specificationData = FileHandler.fileMatcher("glob:*.spc",
			configDir.resolve("specification"));
		configurationData = FileHandler.fileMatcher("glob:*.properties",
			configDir);
		if (specificationData != null) {
		    specificationCombo.setEnabled(true);

		    for (String spec : specificationData) {
			specificationCombo.add(spec);
		    }
		}
		if (configurationData != null) {
		    configurationCombo.setEnabled(true);

		    for (String conf : configurationData) {
			configurationCombo.add(conf);
		    }
		}

		if (configurationCombo.getEnabled() && specificationCombo.getEnabled()) {
		    isComboEnable = true;
		} else {
		    isComboEnable = false;
		}

		addAutoCompleteFeature(specificationCombo);
		addAutoCompleteFeature(configurationCombo);
	    } catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	    }
	    isUpdateComboData = true;
	}
    }

    void sourcesButtonSelected() {
	FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
	dialog.setText(Messages.MainLaunchingTab_dialogSourcesMessage);
	dialog.setFilterNames(new String[] { "*.c" }); //$NON-NLS-1$
	String file = dialog.open();
	if (file != null) {
	    sourceText.setText(file);
	}

    }

    /**
     * 
     * @param combo
     * Function to auto complete the combo box
     */
    public static void addAutoCompleteFeature(Combo combo) {
	// Add a key listener
	combo.addKeyListener(new KeyAdapter() {
	    public void keyReleased(KeyEvent keyEvent) {
		Combo cmb = ((Combo) keyEvent.getSource());
		setClosestMatch(cmb);
	    }

	    // Move the highlight back by one character for backspace
	    public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.keyCode == SWT.BS) {
		    Combo cmb = ((Combo) keyEvent.getSource());
		    Point pt = cmb.getSelection();
		    cmb.setSelection(new Point(Math.max(0, pt.x - 1), pt.y));
		}
	    }

	    private void setClosestMatch(Combo combo) {
		String str = combo.getText();
		String[] cItems = combo.getItems();
		// Find Item in Combo Items. If full match returns index
		int index = -1;
		for (int i = 0; i < cItems.length; i++) {
		    if (cItems[i].toLowerCase().startsWith(str.toLowerCase())) {
			index = i;
			break;
		    }
		}

		if (index != -1) {
		    Point pt = combo.getSelection();
		    combo.select(index);
		    combo.setText(cItems[index]);
		    combo.setSelection(new Point(pt.x, cItems[index].length()));
		} else {
		    combo.setText("");
		}
	    }
	});
    }

}
