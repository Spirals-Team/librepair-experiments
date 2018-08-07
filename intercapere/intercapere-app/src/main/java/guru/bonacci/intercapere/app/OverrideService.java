package guru.bonacci.intercapere.app;

import guru.bonacci.intercapere.InterCapereService;

public class OverrideService implements InterCapereService {

	@Override
	public String take(String in) {
		String out = "Always the same s...";
		System.out.println(out);
		return out;
	}
}
