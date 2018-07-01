package org.eclipse.internal.cpacep.view;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.cpacep.util.StatisticsData;
import org.eclipse.internal.cpacep.dialog.Statistics;

public class StatisticAction extends ActionEvent {
    public StatisticAction(ViewToolBar toolbar) {
	super(toolbar);
    }

    @Override
    public IPath getImagePath() {
	return new Path("icons/statistics.gif"); //$NON-NLS-1$
    }

    @Override
    public IPath getDisabledImagePath() {
	return new Path("icons/statistics.gif"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
	return Messages.PropertiesViewForm_actionStatistics;
    }

    @Override
    public void updateEnablementState() {

    }

    @Override
    public void run() {
	CpacepView cpacepView = CpacepView.getViewInstance();
	List<StatisticsData> stats = null;
	try {
	    stats = cpacepView.getCPACEPConncetor().getStatistics();
	} catch (IOException ex) {
	    System.out.println("Error reading statistic file");
	}

	Statistics dialog = new Statistics(cpacepView.getComposite().getShell());
	dialog.setStats(stats);
	dialog.create();
	dialog.open();

	// if (dialog.open() == Window.OK) {
	//
	// }

    }
}
