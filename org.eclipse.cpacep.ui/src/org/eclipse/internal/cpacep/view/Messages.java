package org.eclipse.internal.cpacep.view;

import java.text.MessageFormat;
import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.internal.cpacep.view.messages"; //$NON-NLS-1$


	public static String PropertiesViewForm_actionStop;
	public static String PropertiesViewForm_actionRun;
	public static String PropertiesViewForm_actionStatistics;
	public static String PropertiesViewForm_actionResult;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String format(String message, Object[] objects) {
		return MessageFormat.format(message, objects);
	}
}

