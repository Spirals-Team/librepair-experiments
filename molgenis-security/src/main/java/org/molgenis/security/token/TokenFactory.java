package org.molgenis.security.token;

import org.molgenis.data.MolgenisDataAccessException;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static java.lang.String.format;

public class TokenFactory
{
	private TokenFactory()
	{
	}

	public static RunAsUserToken getRunAsUserToken(String key, UserDetails userDetails,
			Class<? extends Authentication> originalAuthentication)
	{
		checkUserEnabled(userDetails);
		return new RunAsUserToken(key, userDetails.getUsername(), userDetails.getPassword(),
				userDetails.getAuthorities(), originalAuthentication);
	}

	public static RestAuthenticationToken getRestAuthenticationToken(UserDetails userDetails, String token)
	{
		checkUserEnabled(userDetails);
		return new RestAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities(), token);
	}

	private static void checkUserEnabled(UserDetails userDetails)
	{
		if (!userDetails.isEnabled())
		{
			throw new MolgenisDataAccessException(format("User %s is disabled.", userDetails.getUsername()));
		}
	}

}
