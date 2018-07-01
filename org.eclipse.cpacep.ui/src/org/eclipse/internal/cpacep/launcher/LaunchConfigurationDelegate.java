package org.eclipse.internal.cpacep.launcher;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.cpacep.util.CPACEPConnector;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.internal.cpacep.view.CpacepView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class LaunchConfigurationDelegate extends org.eclipse.debug.core.model.LaunchConfigurationDelegate {

    public void launch(final ILaunchConfiguration configuration, String mode, final ILaunch launch,
	    IProgressMonitor monitor) {

	Display.getDefault().asyncExec(new Runnable() {
	    @Override
	    public void run() {
		IWorkbenchPage page = null;
		CpacepView view = null;
		try {
		    page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		    view = (CpacepView) page.findView(CpacepView.ID);
		    if (view == null) {
			view = (CpacepView) page.showView(CpacepView.ID, null, IWorkbenchPage.VIEW_ACTIVATE);
		    } else {
			view.reset();
		    }
		    view.startValidation(CPACEPConnector.create(configuration), view);

		} catch (PartInitException e) {
		    e.printStackTrace();
		}
	    }
	});
    }
}