package academia.security;

import academia.business.account.AccountRepository;
import academia.domain.AccountType;
import academia.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 3/17/18.
 */
@Service
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // noop
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(presentedPassword, account.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (account.getType() == AccountType.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (account.getType() == AccountType.TRAINER) {
            authorities.add(new SimpleGrantedAuthority("TRAINER"));
        } else if (account.getType() == AccountType.CUSTOMER) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        } else {
            throw new RuntimeException("Tipo de conta inválida");
        }

        return new LoggedUser(account.getName(), account.getUsername(), account.getPassword(), authorities);
    }
}
