package org.eclipse.internal.cpacep.view;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.cpacep.util.StringHandler;

public class RunAction extends ActionEvent {

    public RunAction(ViewToolBar toolbar) {
	super(toolbar);
    }

    @Override
    public IPath getImagePath() {
	return new Path("icons/run_enabled.gif"); //$NON-NLS-1$
    }

    @Override
    public IPath getDisabledImagePath() {
	return new Path("icons/run_disabled.gif"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
	return Messages.PropertiesViewForm_actionRun;
    }

    @Override
    public void updateEnablementState() {

    }

    @Override
    public void run() {
	CpacepView cpacepView = CpacepView.getViewInstance();
	cpacepView.reset();
	cpacepView.getCPACEPConncetor().fillInLaunch();
	String result=cpacepView.getCPACEPConncetor().getResult();
	String status=StringHandler.getResultFilter(result);
	cpacepView.getStatusBar().setStatus(status, cpacepView.getComposite().getDisplay());
	cpacepView.getText().setText(result);

    }

}
