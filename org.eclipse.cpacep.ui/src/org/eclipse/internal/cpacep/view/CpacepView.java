package org.eclipse.internal.cpacep.view;

import org.eclipse.cpacep.util.CPACEPConnector;
import org.eclipse.cpacep.util.StringHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CpacepView extends ViewPart {
    public static final String ID = "org.eclipse.cpacep.ui.view.properties"; //$NON-NLS-1$

    private Text text;
    private StringBuilder output;

    private Composite parent;
    private ViewToolBar toolbar;
    private StatusBar statusBar;

    private CPACEPConnector cpacepConnector;
    public static CpacepView cpacepView;

    public void createPartControl(Composite p) {
	this.parent = p;
	GridLayout gridLayout = new GridLayout();
	gridLayout.marginWidth = 4;
	gridLayout.marginHeight = 4;
	p.setLayout(gridLayout);
	statusBar = new StatusBar(p);
	statusBar.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

	toolbar = new ViewToolBar(getViewSite().getActionBars().getToolBarManager());

	text = new Text(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
	output = new StringBuilder();

	text.setSize(200, 200);
	GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
	gridData.horizontalSpan = 2;
	text.setLayoutData(gridData);
    }

    public static CpacepView getViewInstance() {
	return cpacepView;
    }

    public void startValidation(CPACEPConnector cpacepConnector, CpacepView cpacepView) {
	this.cpacepConnector = cpacepConnector;
	this.cpacepView = cpacepView;
	String result=cpacepConnector.getResult();
	text.setText(result);
	String status=StringHandler.getResultFilter(result);
	statusBar.setStatus(status, parent.getDisplay());
	enableActions();
    }

    public void enableActions() {
	toolbar.enableActions(true);
    }

    public void setFocus() {

    }

    public void reset() {
	text.setText("");
	statusBar.clear(parent.getDisplay());
    }

    public CPACEPConnector getCPACEPConncetor() {
	return cpacepConnector;
    }

    public void setCPACEPConnector(CPACEPConnector cpacepConnector) {
	this.cpacepConnector = cpacepConnector;
    }

    public Text getText() {
	return text;
    }
    
    public StatusBar getStatusBar() {
	return statusBar;
    }
    public Composite getComposite() {
	return parent;
    }
}