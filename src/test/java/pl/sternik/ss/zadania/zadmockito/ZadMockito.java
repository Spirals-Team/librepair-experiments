package pl.sternik.ss.zadania.zadmockito;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;


public class ZadMockito {

    UserService userService = mock(UserService.class);
    AuthService authService = new AuthService(userService);

    @Test
    public void testBadLogin() {

        when(userService.findByName("xyz")).thenReturn(null);

        when(userService.findByName("xyz")).thenReturn("lipa");

        boolean authOk = authService.checkCredentials("xyz", "password");

        assertThat(authOk).isFalse();

        verify(userService).findByName("xyz");

        verify(userService, never()).findByName("root");

        verify(userService, only()).findByName("xyz");

    }

    @Test(expected = NullPointerException.class)
    public void testBadLoginException() {
        //   when(userService.findByName("xyz")).thenThrow(new Exception());  //NAPRAWIC

        when(userService.findByName(anyString())).thenThrow(new Exception());

        authService.checkCredentials("ABC", "Password");

        boolean authOk = authService.checkCredentials("xyz", "password");

    }

}
