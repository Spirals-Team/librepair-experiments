//package poe.spring.delegate;
//
//public class VoyageCreationDelegate {
//
//	public static final int MIN_VILLEDEPART_LENGTH = 4;
//	public static final int MAX_VILLEDEPART_LENGTH = 10;
//	public static final String[] FORBIDDEN_VILLEDEPART = { "toto", "tata", "titi" };
//
//	public static Boolean isSizeValid(String villeDepart) {
//		Boolean isValid;
//		isValid = villeDepart.length() >= MIN_VILLEDEPART_LENGTH && villeDepart.length() <= MAX_VILLEDEPART_LENGTH;
//		return isValid;
//	}
//
//	public static Boolean testForbiddenString(String villeDepart) {
//		Boolean isAuthorized = true;
//		for (int i = 0; (i < FORBIDDEN_VILLEDEPART.length) && isAuthorized; i++) {
//			if (FORBIDDEN_VILLEDEPART[i].equals(villeDepart)) {
//				isAuthorized = false;
//			}
//		}
//		return isAuthorized;
//	}
//
//}
