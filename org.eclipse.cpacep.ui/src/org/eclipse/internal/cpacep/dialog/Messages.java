package org.eclipse.internal.cpacep.dialog;

import java.text.MessageFormat;
import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.internal.cpacep.dialog.messages"; //$NON-NLS-1$


	public static String DialogViewForm_StatisticsTitle;
	public static String DialogViewForm_StatisticsMessage;
	public static String DialogViewForm_RightPanelTitle;
	public static String DialogViewForm_LeftPanelTitle;
	
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

