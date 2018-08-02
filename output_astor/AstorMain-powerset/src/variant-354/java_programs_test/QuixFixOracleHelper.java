package java_programs_test;


public class QuixFixOracleHelper {






	public static java.lang.String format(java.lang.Object out, boolean cutDecimal) {
		java.lang.Object jsonOutObtained = java_programs_test.QuixFixOracleHelper.transformToString(out, cutDecimal);
		java.lang.String obtained = java_programs_test.QuixFixOracleHelper.removeSymbols(jsonOutObtained.toString());
		return obtained;
	}

	public static java.lang.String removeSymbols(java.lang.String r) {

		r = r.replaceAll("\\(", "[").replaceAll("\\)", "]").replace(" ", "").replaceAll("\"", "");
		return r;
	}

	public static java.lang.String transformToString(java.lang.Object out, boolean mustRemoveDecimal) {
		if (out instanceof java.lang.Iterable) {
			java.lang.String r = "[";
			for (java.lang.Object o : ((java.lang.Iterable) (out))) {
				r += (java_programs_test.QuixFixOracleHelper.transformToString(o, mustRemoveDecimal)) + ",";
			}
			if ((r.length()) >= 2) {
				r = r.trim().substring(0, ((r.length()) - 1));
			}

			return r + "]";
		}else {
			java.lang.String s = "";
			if ((out instanceof java.lang.String) && (!(java_programs_test.QuixFixOracleHelper.isInteger(out.toString()))))
				s += out.toString();else 
			{
				s = (mustRemoveDecimal && (out.toString().endsWith(".0"))) ? 
				out.toString().substring(0, ((out.toString().length()) - 2)) : out.toString();
			}
			return s;
		}

	}

	public static boolean isInteger(java.lang.String s) {
		boolean isValidInteger = false;
		try {
			java.lang.Integer.parseInt(s);
			isValidInteger = true;
		} catch (java.lang.NumberFormatException ex) {
		}

		return isValidInteger;
	}}