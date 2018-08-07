package guru.bonacci.intercapere;

public class SurroundService implements InterCapereService {

	private final String prefix;

	private final String suffix;

	public SurroundService(String prefix, String suffix) {
		this.prefix = (prefix != null ? prefix : "");
		this.suffix = (suffix != null ? suffix : "");
	}

	public SurroundService() {
		this(null, null);
	}

	@Override
	public String take(String in) {
		String out = String.format("%s%s%s", this.prefix, in, this.suffix);
		System.out.println(out);
		return out;
	}

}
