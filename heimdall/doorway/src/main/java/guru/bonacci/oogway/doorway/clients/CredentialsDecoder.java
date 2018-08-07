package guru.bonacci.oogway.doorway.clients;

import static java.lang.String.format;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.doorway.security.Decryptor;

public class CredentialsDecoder implements Decoder {

	private final Decoder decoder = new JacksonDecoder();

	@Autowired
	private Decryptor decryptor;

	@Override
	public Object decode(Response response, Type type) throws IOException {
		Object o = decoder.decode(response, type);
		if (Credentials.class.equals(type)) {
			Credentials c = (Credentials) o;
			c.setPassword(decryptor.decrypt(c.getEncryptedPassword()));
			c.setEncryptedPassword(null);
			return c;
		}
		throw new DecodeException(format("%s is not a type supported by this decoder.", type));
	}
}