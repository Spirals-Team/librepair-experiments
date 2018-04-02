package xades4j.providers;

/**
 * @author Artem R. Romanenko
 * @version 18/01/2018
 */
public interface ElementIdProvider {
    String generateSignatureId();

    String generateSignedPropsId(String signatureId);
}
