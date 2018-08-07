package guru.bonacci.oogway.doorway.clients;

import static feign.FeignException.errorStatus;

import feign.Response;
import feign.codec.ErrorDecoder;
import guru.bonacci.oogway.doorway.exceptions.NoGoException;
import guru.bonacci.oogway.doorway.exceptions.OurFaultException;

public class CredentialsErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() >= 400 && response.status() <= 499) {
			return new NoGoException();
		}
		if (response.status() >= 500 && response.status() <= 599) {
			return new OurFaultException();
		}
		return errorStatus(methodKey, response);
	}
}