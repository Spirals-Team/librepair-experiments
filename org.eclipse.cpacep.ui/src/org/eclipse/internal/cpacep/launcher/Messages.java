package org.eclipse.internal.cpacep.launcher;
import java.text.MessageFormat;
import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.internal.cpacep.launcher.messages"; //$NON-NLS-1$
	public static String MainLaunchingTab_name;

	public static String MainLaunchingTab_labelCPAChecker;
	public static String MainLaunchingTab_labelFile;
	public static String MainLaunchingTab_labelBrowse;
	public static String MainLaunchingTab_projectsBrowse;
	public static String MainLaunchingTab_sourcesBrowse;
	public static String MainLaunchingTab_specificationCombo;
	public static String MainLaunchingTab_configurationCombo;
	public static String MainLaunchingTab_labelProgramArgs;
	public static String MainLaunchingTab_labelCommandLineArgs;
	public static String MainLaunchingTab_dialogCPA;
	
	public static String MainLaunchingTab_dialogSourcesTitle;
	public static String MainLaunchingTab_dialogSourcesMessage;
	
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
