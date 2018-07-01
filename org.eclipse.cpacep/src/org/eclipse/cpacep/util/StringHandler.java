package org.eclipse.cpacep.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StringHandler {

	public static final String VERIFICATION = "Verification result:";
	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";
	public static final String UNKNOWN = "UNKNOWN";

	public static Path getHomePath(String pExecutable) {
		return Paths.get(pExecutable).getParent().getParent();
	}

	public static String getResultFilter(String result) {
		String lines[] = result.split("\\r?\\n");
		for (String line : lines) {
			if (line.contains(VERIFICATION)) {
				if (line.contains(TRUE)) {
					return TRUE;
				} else if (line.contains(FALSE)) {
					return FALSE;
				}
			}
		}
		return UNKNOWN;
	}
}
