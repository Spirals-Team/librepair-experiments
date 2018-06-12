package nl.stil4m.mollie.concepts;

import java.io.IOException;
import nl.stil4m.mollie.ResponseOrError;
import org.apache.http.client.methods.HttpDelete;

/**
 *
 * @author stevensnoeijen
 */
public interface DeleteWithResponse<T> extends Concept<T> {

    default ResponseOrError<T> delete(String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url(id));
        return requestSingle(httpDelete);
    }
}
