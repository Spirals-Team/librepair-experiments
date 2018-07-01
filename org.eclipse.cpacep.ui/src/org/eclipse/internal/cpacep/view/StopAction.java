package org.eclipse.internal.cpacep.view;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class StopAction extends ActionEvent {

    public StopAction(ViewToolBar toolbar) {
	super(toolbar);
    }

    @Override
    public IPath getImagePath() {
	return new Path("icons/stop_enabled.gif"); //$NON-NLS-1$
    }

    @Override
    public IPath getDisabledImagePath() {
	return new Path("icons/stop_disabled.gif"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
	return Messages.PropertiesViewForm_actionStop;
    }

    @Override
    public void updateEnablementState() {

    }

    @Override
    public void run() {
	CpacepView cpacepView = CpacepView.getViewInstance();
	cpacepView.reset();
	cpacepView.getCPACEPConncetor().killCommand();

    }
}
