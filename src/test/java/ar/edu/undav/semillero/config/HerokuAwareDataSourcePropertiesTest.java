package ar.edu.undav.semillero.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Alejandro Gomez
 */
public class HerokuAwareDataSourcePropertiesTest {

    private static final String USERNAME = "semillero";
    private static final String PASSWORD = "secreto";
    private static final String URL = "jdbc:mariadb://semillero-db/semillero";
    private static final String JAWSDB_MARIA_URL = "mysql://username:password@hostname:3306/default_schema";

    private HerokuAwareDataSourceProperties dsProps;

    @Test
    public void givenEmptyHerokuDsUrlShouldReturnUsername() {
        // given
        setUpDsProps("");
        // when
        String username = dsProps.determineUsername();
        // then
        Assertions.assertThat(username).isEqualTo(USERNAME);
    }

    @Test
    public void givenEmptyHerokuDsUrlShouldReturnPassword() {
        // given
        setUpDsProps("");
        // when
        String password = dsProps.determinePassword();
        // then
        Assertions.assertThat(password).isEqualTo(PASSWORD);
    }

    @Test
    public void givenEmptyHerokuDsUrlShouldReturnUrl() {
        // given
        setUpDsProps("");
        // when
        String url = dsProps.determineUrl();
        // then
        Assertions.assertThat(url).isEqualTo(URL);
    }

    @Test
    public void givenHerokuDsUrlShouldReturnUsername() {
        // given
        setUpDsProps(JAWSDB_MARIA_URL);
        // when
        String username = dsProps.determineUsername();
        // then
        Assertions.assertThat(username).isEqualTo("username");
    }

    @Test
    public void givenHerokuDsUrlShouldReturnPassword() {
        // given
        setUpDsProps(JAWSDB_MARIA_URL);
        // when
        String password = dsProps.determinePassword();
        // then
        Assertions.assertThat(password).isEqualTo("password");
    }

    @Test
    public void givenHerokuDsUrlShouldReturnUrl() {
        // given
        setUpDsProps(JAWSDB_MARIA_URL);
        // when
        String url = dsProps.determineUrl();
        // then
        Assertions.assertThat(url).isEqualTo("jdbc:mysql://hostname:3306/default_schema");
    }

    private void setUpDsProps(String herokuDsUrl) {
        dsProps = new HerokuAwareDataSourceProperties(herokuDsUrl);
        dsProps.setUsername(USERNAME);
        dsProps.setPassword(PASSWORD);
        dsProps.setUrl(URL);
    }
}
