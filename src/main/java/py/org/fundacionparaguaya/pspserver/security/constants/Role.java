package py.org.fundacionparaguaya.pspserver.security.constants;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {
	/**
	 * Can do anything
	 */
	ROLE_ROOT,

	/**
	 * Can manage everything related to an application that is a Hub
	 */
	ROLE_HUB_ADMIN,

	/**
	 * Can manage everything related to an application that is an organization or a partner
	 */
	ROLE_APP_ADMIN,

	/**
	 * Can fill surveys and visualize its state.
	 *
	 * TODO: Perhaps this won't be necessary.
	 */
	ROLE_SURVEY_USER,

	/**
	 * Can manage taks.
	 */
	ROLE_SOCIAL_ASSISTANT,

	/**
	 * This is the most bare bone standard type of
	 * user the application can have.
	 * <br />
	 * The behaviour should be such as if an user is created without any role,
	 * this should be the default role for that user.
	 *
	 * TODO: Perhaps this won't be necessary.
	 */
	ROLE_USER;

	private static final String SEPARATOR = "_";
	private static final String PREFIX = "ROLE";

	/**
	 * Returns the name of this enum value without the {@link #PREFIX}.
	 * Spring security works with role names without this prefix.
	 *
	 * @return
	 */
	public String getSecurityName() {
		return Arrays.stream(this.name().split(SEPARATOR))
				.filter(part -> !part.equals(PREFIX))
				.collect(Collectors.joining(SEPARATOR));
	}
}