package hanfak.shopofhan.infrastructure.web;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Unmarshaller<T> {
    T unmarshall(HttpServletRequest request) throws IOException;
}
