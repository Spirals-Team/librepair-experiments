package org.eclipse.cpacep.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.debug.core.ILaunchConfiguration;

public class CPACEPConnector {

	public static final String NO_VALUE = ""; //$NON-NLS-1$
	public static final String LC_CPACEP_SOURCE = "cpacep.source"; //$NON-NLS-1$
	public static final String LC_CPACEP_CMD = "cpacep.cmd"; //$NON-NLS-1$
	public static final String LC_CPACEP_SPECIFICATION = "cpacep.spec"; //$NON-NLS-1$
	public static final String LC_CPACEP_CONFIGURATION = "cpacep.config"; //$NON-NLS-1$
	public static final String LC_CPACEP_EXECUTABLE = "cpacep.executable"; //$NON-NLS-1$

	public static final String LC_CPACEP_MKDIR = "cpacep.mkdir";
	public static final String LC_CPACEP_ENABLE_COMBO = "cpacep.enablecombo";
	public static final String SPEC_FILE_TYPE = ".spc";
	public static final String CONFIG_FILE_TYPE = ".properties";

	public static final int LC_SOURCES_TO_CHECK = 1;
	public static final int CPA_HOME_PATH = 2;

	public static File outputDirectory;
	private List<String> baseCli = new ArrayList<String>();
	//	private static final String PLUGIN_ID = "org.eclipse.cpacep"; //$NON-NLS-1$
	//
	//	private ILog logger = Platform.getPlugin(PLUGIN_ID).getLog();
	private String lcCPACEPExecutable;
	private String lcSourceFile;
	private String lcCommandLine;
	private String lcSpecification;
	private String lcConfiguration;

	private String CPACheckerHome;
	private String result;

	private Process p;

	public CPACEPConnector() {

	}

	public CPACEPConnector(ILaunchConfiguration config) {

		try {
			lcCPACEPExecutable = config.getAttribute(LC_CPACEP_EXECUTABLE, NO_VALUE);
			lcSourceFile = config.getAttribute(LC_CPACEP_SOURCE, NO_VALUE);
			lcCommandLine = config.getAttribute(LC_CPACEP_CMD, NO_VALUE);
			lcSpecification = config.getAttribute(LC_CPACEP_SPECIFICATION, NO_VALUE);
			lcConfiguration = config.getAttribute(LC_CPACEP_CONFIGURATION, NO_VALUE);
			setCPACheckerHome();
			setOutputDirectory();

		} catch (Exception e) {
			e.printStackTrace();
			//logger.log(new Status(IStatus.ERROR, PLUGIN_ID, "Error retrieving attributes from the launch configuration", e)); //$NON-NLS-1$
		}
	}

	public void setCPACheckerHome() {
		CPACheckerHome = StringHandler.getHomePath(lcCPACEPExecutable).toString();
	}

	public void setOutputDirectory() throws IOException {
		outputDirectory = FileHandler.createTempDirectory();
	}

	private void initializeBaseCommandLine() {
		baseCli.add(lcCPACEPExecutable);
		baseCli.add(" -spec " + CPACheckerHome + File.separator + "config" + File.separator + "specification" + File.separator + lcSpecification + SPEC_FILE_TYPE);
		baseCli.add(" -config " + CPACheckerHome + File.separator + "config" + File.separator + lcConfiguration + CONFIG_FILE_TYPE);
		baseCli.add(lcCommandLine);
		baseCli.add(lcSourceFile);
		baseCli.add(" -outputpath " + outputDirectory);
	}

	public String executeCommand(String command) {

		StringBuilder output = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec(command);
			//p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return output.toString();
	}

	public void killCommand() {
		p.destroy();
	}

	public void fillInLaunch() {
		StringBuilder sb = new StringBuilder();
		for (String command : baseCli) {
			if (!command.equals("")) {
				sb.append(command + " "); //$NON-NLS-1$
			}
		}
		result = executeCommand(sb.toString());

	}

	public static CPACEPConnector create(ILaunchConfiguration config) {
		CPACEPConnector bridge = new CPACEPConnector(config);
		bridge.initializeBaseCommandLine();
		bridge.fillInLaunch();
		return bridge;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<StatisticsData> getStatistics() throws IOException {
		List<String> lines = null;
		List<StatisticsData> stats = new ArrayList<>();
		lines = FileHandler.readFile(new File(outputDirectory + File.separator + "Statistics.txt"));

		if (lines != null) {
			StatisticsData statistics = new StatisticsData();
			for (String line : lines) {
				if (line.contains("-----") || line.contains("======") || line.contains("Verification result")) {
					if (statistics != null) {
						if (statistics.getHeader() == null) {
							statistics.setHeader(statistics.getLastAddedLine());
						} else {
							String tempHeader = statistics.getLastAddedLine();
							statistics.removeLastLine();
							statistics.removeHeaderFromBody();
							stats.add(statistics);
							statistics = new StatisticsData();
							statistics.setHeader(tempHeader);
						}
					}
				} else {
					statistics.addLine(line);
				}
			}

		}
		return stats;
	}
}
