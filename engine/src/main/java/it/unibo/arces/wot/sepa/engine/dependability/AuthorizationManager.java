/* This class implements the OAuth 2.0 Authorization Manager (AM) of the SEPA
 * 
 * Author: Luca Roffia (luca.roffia@unibo.it)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package it.unibo.arces.wot.sepa.engine.dependability;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;

//import org.apache.http.Header;
//import org.apache.http.HttpRequest;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import it.unibo.arces.wot.sepa.commons.exceptions.SEPASecurityException;
import it.unibo.arces.wot.sepa.commons.response.ErrorResponse;
import it.unibo.arces.wot.sepa.commons.response.JWTResponse;
import it.unibo.arces.wot.sepa.commons.response.RegistrationResponse;
import it.unibo.arces.wot.sepa.commons.response.Response;
import it.unibo.arces.wot.sepa.commons.security.SEPASecurityManager;
import it.unibo.arces.wot.sepa.engine.bean.AuthorizationManagerBeans;
import it.unibo.arces.wot.sepa.engine.bean.SEPABeans;

public class AuthorizationManager implements AuthorizationManagerMBean {
	private static final Logger logger = LogManager.getLogger("AuthorizationManager");
	
	//TODO: CLIENTS DB to be made persistent
	//IDENTITY ==> ID
	private HashMap<String,String> clients = new HashMap<String,String>();
	
	//TODO: CREDENTIALS DB to be made persistent
	//ID ==> Secret
	private HashMap<String,String> credentials = new HashMap<String,String>();
	
	//TODO: TOKENS DB to be made persistent
	//ID ==> JWTClaimsSet
	private HashMap<String,JWTClaimsSet> clientClaims = new HashMap<String,JWTClaimsSet>();
	
	//*************************
	//JWT signing and verifying
	//*************************
	private JWSSigner signer;
	private RSASSAVerifier verifier;
	private JsonElement jwkPublicKey;
	private ConfigurableJWTProcessor<SEPASecurityContext> jwtProcessor;
	private SEPASecurityContext context = new SEPASecurityContext();
	private SEPASecurityManager sManager;
	
	/**
	Security context. Provides additional information necessary for processing a JOSE object.
	Example context information:

	Identifier of the message producer (e.g. OpenID Connect issuer) to retrieve its public key to verify the JWS signature.
	Indicator whether the message was received over a secure channel (e.g. TLS/SSL) which is essential for processing unsecured (plain) JOSE objects.
	*/
	private class SEPASecurityContext implements SecurityContext {
		
	}
	
	private void securityCheck(String identity) {
		logger.debug("*** Security check ***");
		//Add identity
		addAuthorizedIdentity(identity);
		
		//Register
		logger.debug("Register: "+identity);
		Response response = register(identity);
		if (response.getClass().equals(RegistrationResponse.class)) {
			RegistrationResponse ret = (RegistrationResponse) response;
			String auth = ret.getClientId()+":"+ret.getClientSecret();	
			logger.debug("ID:SECRET="+auth);
			
			//Get token
			String encodedCredentials = Base64.getEncoder().encodeToString(auth.getBytes());
			logger.debug("Authorization Basic "+encodedCredentials);
			response = getToken(encodedCredentials);
			
			if (response.getClass().equals(JWTResponse.class)) {
				logger.debug("Access token: "+((JWTResponse) response).getAccessToken());
				
				//Validate token
				Response valid = validateToken(((JWTResponse) response).getAccessToken());
				if(!valid.getClass().equals(ErrorResponse.class)) logger.debug("PASSED");
				else {
					ErrorResponse error = (ErrorResponse) valid;
					logger.error(error);
				}
			}
			else logger.debug("FAILED");
		}
		else logger.debug("FAILED");
		logger.debug("**********************");
		System.out.println("");	
		
		//Add identity
		removeAuthorizedIdentity(identity);
	}

//	/**
//	 * Gets the RSA Key from the keystore.
//	 *
//	 * @param keyAlias
//	 *            the key alias
//	 * @param keyPwd
//	 *            the key password
//	 * @return the RSAKey
//	 * @throws JOSEException 
//	 * @throws KeyStoreException 
//	 *
//	 * @see RSAKey
//	 */
//	private RSAKey getJWK(String keyAlias, String keyPwd) throws KeyStoreException, JOSEException {
//		RSAKey jwk = null;
//
//		jwk = RSAKey.load(sManager.getKeyStore(), keyAlias, keyPwd.toCharArray());
//
//		return jwk;
//	}
	
	private boolean init(KeyStore keyStore,String keyAlias,String keyPwd) throws KeyStoreException, JOSEException{		
		// Load the key from the key store
		RSAKey jwk = RSAKey.load(keyStore, keyAlias, keyPwd.toCharArray());
						
		//Get the private and public keys to sign and verify
		RSAPrivateKey privateKey;
		RSAPublicKey publicKey;
		
		privateKey = jwk.toRSAPrivateKey();
		publicKey = jwk.toRSAPublicKey();
		
		// Create RSA-signer with the private key
		signer = new RSASSASigner(privateKey);
		
		// Create RSA-verifier with the public key
		verifier = new RSASSAVerifier(publicKey);
				
		//Serialize the public key to be deliverer during registration
		jwkPublicKey = new JsonParser().parse(jwk.toPublicJWK().toJSONString());
		
		// Set up a JWT processor to parse the tokens and then check their signature
		// and validity time window (bounded by the "iat", "nbf" and "exp" claims)
		jwtProcessor = new DefaultJWTProcessor<SEPASecurityContext>();
		JWKSet jws = new JWKSet(jwk);
		JWKSource<SEPASecurityContext> keySource = new ImmutableJWKSet<SEPASecurityContext>(jws);
		JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
		JWSKeySelector<SEPASecurityContext> keySelector = new JWSVerificationKeySelector<SEPASecurityContext>(expectedJWSAlg, keySource);
		jwtProcessor.setJWSKeySelector(keySelector);
		
		return true;
	}
	
	public AuthorizationManager(String keystoreFileName,String keystorePwd,String keyAlias,String keyPwd,String certificate) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, JOSEException, SEPASecurityException {	
		SEPABeans.registerMBean("SEPA:type=AuthorizationManager",this);	
		
		sManager = new SEPASecurityManager(keystoreFileName, keystorePwd,keyPwd);
		init(sManager.getKeyStore(),keyAlias, keyPwd);
		
		securityCheck(UUID.randomUUID().toString());
	}
	
	/**
	 * Operation when receiving a HTTP request at a protected endpoint
	 * 
<pre>
1. Check if the request contains an Authorization header. 

2. Check if the request contains an Authorization: Bearer-header with non-null/empty contents 

3. Check if the value of the Authorization: Bearer-header is a JWT object 

4. Check if the JWT object is signed 

5. Check if the signature of the JWT object is valid. This is to be checked with AS public signature verification key 

6. Check the contents of the JWT object 

7. Check if the value of "iss" is https://wot.arces.unibo.it:8443/oauth/token 

8. Check if the value of "aud" contains https://wot.arces.unibo.it:8443/sparql 

9. Accept the request as well as "sub" as the originator of the request and process it as usual
 
Respond with 401 if not
 
</pre>
	 */
//	private synchronized Response authorizeRequest(HttpRequest request) {
//		// Extract Bearer authorization
//		Header[] bearer = request.getHeaders("Authorization");
//
//		if (bearer.length != 1) {
//			logger.error("Authorization header is missing or multiple");
//			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_request","Authorization header must be a single one");
//		}
//		if (!bearer[0].getValue().startsWith("Bearer ")) {
//			logger.error("Authorization must be \"Bearer JWT\"");
//			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_request","Authorization header must be \"Bearer JWT\"");
//		}
//
//		// ******************
//		// JWT validation
//		// ******************
//		String jwt = bearer[0].getValue().split(" ")[1];
//
//		return validateToken(jwt);
//	}
	
	private boolean authorizeIdentity(String id) {
		logger.debug("Authorize identity:"+id);
		
		//TODO: WARNING! TO BE REMOVED IN PRODUCTION. ONLY FOR TESTING.
		if (id.equals("SEPATest")) {
			logger.warn("SEPATest authorized! Setting expiring token period to 5 seconds");
			AuthorizationManagerBeans.addAuthorizedIdentity(id);
			AuthorizationManagerBeans.setTokenExpiringPeriod(5);
			return true;
		}
		
		if(!AuthorizationManagerBeans.getAuthorizedIdentities().containsKey(id)) return false;
		
		if(!AuthorizationManagerBeans.getAuthorizedIdentities().get(id)) return false;
		
		AuthorizationManagerBeans.getAuthorizedIdentities().put(id, false);
		return true;
	}
	
	/**
	 * <pre>
	 * POST https://wot.arces.unibo.it:8443/oauth/token
	 * 
	 * Accept: application/json
	 * Content-Type: application/json
	 * 
	 * { 
	 *  "client_identity": ”<ClientIdentity>", 
	 *  "grant_types": ["client_credentials"] 
	 * }
	 * 
	 * Response example:
	 *
	 * {
	 *  "clientId": "889d02cf-16dd-4934-9341-a754088faxyz",
	 *  "clientSecret": "ahd5MU42J0hIxPXzhUhjJHt2d0Oc5M6B644CtuwUlE9zpSuF14-kXYZ",
	 *  "signature" : JWK RSA public key (can be used to verify the signature),
	 *  "authorized" : Boolean
	 * }
	 * 
	 * In case of error, the following applies:
 {
   "error":"Unless specified otherwise see RFC6749. Otherwise, this is specific of the SPARQL 1.1 SE Protocol",
   "error_description":"Unless specified otherwise, see RFC6749. Otherwise, this is specific of the SPARQL 1.1 SE Protocol", (OPTIONAL)
   "status_code" : the HTTP status code (would be 400 for Oauth 2.0 errors).
 }
	 * </pre>
	 * 
	 * @param identity the client identity to be registered
	 * */
	public synchronized Response register(String identity) {
		logger.info("REGISTER: "+identity);
		
		//Check if entity is authorized to request credentials
		if (!authorizeIdentity(identity)) {
			logger.error("Not authorized identity "+identity);
			return new ErrorResponse(HttpStatus.SC_UNAUTHORIZED,"not_authorized_identity","Client "+identity+" is not authorized");
		}
		
		String client_id = null;
		String client_secret = null;
		
		//Check if identity has been already registered
		if (clients.containsKey(identity)) {
			logger.warn("Giving credentials to a registered identity "+identity);
			client_id = clients.get(identity);
			client_secret = credentials.get(client_id);
		}
		else {
			//Create credentials
			client_id = UUID.randomUUID().toString();
			client_secret = UUID.randomUUID().toString();
		
			//Store credentials
			while(credentials.containsKey(client_id)) client_id = UUID.randomUUID().toString();
			credentials.put(client_id,client_secret);
		
			//Register client
			clients.put(identity, client_id);
		}
		return new RegistrationResponse(client_id,client_secret,jwkPublicKey);
	}
	
/**
 It requests a token to the Authorization Server. A token request should be made when the current token is expired or it is the first token. 
 If the token is not expired, the "invalid_grant" error is returned.
 
 @param encodedCredentials the client credentials encoded using Base64
 @return JWTResponse in case of success, ErrorResponse otherwise
 @see JWTResponse
 @see ErrorResponse
 
<pre>
POST https://wot.arces.unibo.it:8443/oauth/token
 
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Authorization: Basic Basic64(id:secret)
 
Response example:
{
"access_token": "eyJraWQiOiIyN.........",
"token_type": "bearer",
"expires_in": 3600 
}

 Error response example:
 {
   "error":"Unless specified otherwise see RFC6749. Otherwise, this is specific of the SPARQL 1.1 SE Protocol",
   "error_description":"Unless specified otherwise, see RFC6749. Otherwise, this is specific to the SPARQL 1.1 SE Protocol", (OPTIONAL)
   "status_code" : the HTTP status code (should be 400 for all Oauth 2.0 errors).
 }

According to RFC6749, the error member can assume the following values: invalid_request, invalid_client, invalid_grant, unauthorized_client, unsupported_grant_type, invalid_scope.
 </pre>
*/
	
	public synchronized Response getToken(String encodedCredentials) {
		logger.debug("Get token");
		
		//Decode credentials
		byte[] decoded = null;
		try{
			decoded = Base64.getDecoder().decode(encodedCredentials);
		}
		catch (IllegalArgumentException e) {
			logger.error("Not authorized");
			return new ErrorResponse(HttpStatus.SC_UNAUTHORIZED,"invalid_client",e.getMessage());
		}
		
		// Parse credentials
		String decodedCredentials = new String(decoded);
		String[] clientID = decodedCredentials.split(":");
		if (clientID==null){
			logger.error("Wrong Basic authorization");
			return new ErrorResponse(HttpStatus.SC_UNAUTHORIZED,"invalid_client","Client id not found: "+decodedCredentials);
		}
		if (clientID.length != 2) {
			logger.error("Wrong Basic authorization");
			return new ErrorResponse(HttpStatus.SC_UNAUTHORIZED,"invalid_client","Wrong credentials: "+decodedCredentials);
		}
		
		String id = decodedCredentials.split(":")[0];
		String secret = decodedCredentials.split(":")[1];
		logger.debug("Credentials: "+id+" "+secret);
		
		//Verify credentials
		if (!credentials.containsKey(id)) {
			logger.error("Client id: "+id+" is not registered");
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","Client "+id+" not found");
		}
		
		if (!credentials.get(id).equals(secret)) {
			logger.error("Wrong secret: "+secret+ " for client id: "+id);
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","Client not authorized");
		}
		
		// Prepare JWT with claims set
		 JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
		 Date now = new Date();
				 
		 // If not yet expired return an error
		if (clientClaims.containsKey(id)) {
			Date expiring = clientClaims.get(id).getExpirationTime();
			
			long delta = expiring.getTime()-now.getTime();
			// Expires if major than current time
			logger.debug("ID: "+id+" ==> Token will expire in: "+delta+" ms");
			if(delta > 0) {
				logger.warn("Token is NOT EXPIRED");
				return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant",now+" token will expire on "+expiring);
			}
			logger.debug("Token is EXPIRED. Release a fresh token.");
		}
		
		// Define validity period
		 //Date before = new Date(now.getTime()-1000);
		 Date expires = new Date(now.getTime()+(AuthorizationManagerBeans.getTokenExpiringPeriod()*1000));
		
		/*
		 * 4.1.1.  "iss" (Issuer) Claim

	   The "iss" (issuer) claim identifies the principal that issued the
	   JWT.  The processing of this claim is generally application specific.
	   The "iss" value is a case-sensitive string containing a StringOrURI
	   value.  Use of this claim is OPTIONAL.*/
		 
		 claimsSetBuilder.issuer(AuthorizationManagerBeans.getIssuer());
		 
	 /* 4.1.2.  "sub" (Subject) Claim

	   The "sub" (subject) claim identifies the principal that is the
	   subject of the JWT.  The Claims in a JWT are normally statements
	   about the subject.  The subject value MUST either be scoped to be
	   locally unique in the context of the issuer or be globally unique.
	   The processing of this claim is generally application specific.  The
	   "sub" value is a case-sensitive string containing a StringOrURI
	   value.  Use of this claim is OPTIONAL.*/
		 
		 claimsSetBuilder.subject(AuthorizationManagerBeans.getSubject());
		
	 /* 4.1.3.  "aud" (Audience) Claim

	   The "aud" (audience) claim identifies the recipients that the JWT is
	   intended for.  Each principal intended to process the JWT MUST
	   identify itself with a value in the audience claim.  If the principal
	   processing the claim does not identify itself with a value in the
	   "aud" claim when this claim is present, then the JWT MUST be
	   rejected.  In the general case, the "aud" value is an array of case-
	   sensitive strings, each containing a StringOrURI value.  In the
	   special case when the JWT has one audience, the "aud" value MAY be a
	   single case-sensitive string containing a StringOrURI value.  The
	   interpretation of audience values is generally application specific.
	   Use of this claim is OPTIONAL.*/
		 
		 ArrayList<String> audience = new ArrayList<String>();
		 audience.add(AuthorizationManagerBeans.getHttpsAudience());
		 audience.add(AuthorizationManagerBeans.getWssAudience());
		 claimsSetBuilder.audience(audience);
		
		/* 4.1.4.  "exp" (Expiration Time) Claim

	   The "exp" (expiration time) claim identifies the expiration time on
	   or after which the JWT MUST NOT be accepted for processing.  The
	   processing of the "exp" claim requires that the current date/time
	   MUST be before the expiration date/time listed in the "exp" claim.
	   Implementers MAY provide for some small leeway, usually no more than
	   a few minutes, to account for clock skew.  Its value MUST be a number
	   containing a NumericDate value.  Use of this claim is OPTIONAL.*/
		
		 claimsSetBuilder.expirationTime(expires);
		
		/*4.1.5.  "nbf" (Not Before) Claim

	   The "nbf" (not before) claim identifies the time before which the JWT
	   MUST NOT be accepted for processing.  The processing of the "nbf"
	   claim requires that the current date/time MUST be after or equal to
	   the not-before date/time listed in the "nbf" claim.  Implementers MAY
	   provide for some small leeway, usually no more than a few minutes, to
	   account for clock skew.  Its value MUST be a number containing a
	   NumericDate value.  Use of this claim is OPTIONAL.*/
		
		// claimsSetBuilder.notBeforeTime(before);
		
		/* 4.1.6.  "iat" (Issued At) Claim

	   The "iat" (issued at) claim identifies the time at which the JWT was
	   issued.  This claim can be used to determine the age of the JWT.  Its
	   value MUST be a number containing a NumericDate value.  Use of this
	   claim is OPTIONAL.*/

		claimsSetBuilder.issueTime(now);
		
		/*4.1.7.  "jti" (JWT ID) Claim

	   The "jti" (JWT ID) claim provides a unique identifier for the JWT.
	   The identifier value MUST be assigned in a manner that ensures that
	   there is a negligible probability that the same value will be
	   accidentally assigned to a different data object; if the application
	   uses multiple issuers, collisions MUST be prevented among values
	   produced by different issuers as well.  The "jti" claim can be used
	   to prevent the JWT from being replayed.  The "jti" value is a case-
	   sensitive string.  Use of this claim is OPTIONAL.*/
		
		claimsSetBuilder.jwtID(id+":"+secret);

		JWTClaimsSet jwtClaims = claimsSetBuilder.build();
		
		//******************************
		// Sign JWT with private RSA key
		//******************************
		SignedJWT signedJWT;
		try {
			signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), JWTClaimsSet.parse(jwtClaims.toString()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","ParseException: "+e.getMessage());
		}
		try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","JOSEException: "+e.getMessage());
		}
						
		//Add the token to the released tokens
		clientClaims.put(id, jwtClaims);
		
		JWTResponse jwt = new JWTResponse(signedJWT.serialize(),"bearer",AuthorizationManagerBeans.getTokenExpiringPeriod());
		logger.debug("Released token: "+jwt);
		
		return jwt;
	}
	
	/**
	 * Operation when receiving a request at a protected endpoint
	 * 
<pre>
Specific to HTTP request:
1. Check if the request contains an Authorization header. 
2. Check if the request contains an Authorization: Bearer-header with non-null/empty contents 
3. Check if the value of the Authorization: Bearer-header is a JWT object 

Token validation:
4. Check if the JWT object is signed 
5. Check if the signature of the JWT object is valid. This is to be checked with AS public signature verification key 
6. Check the contents of the JWT object 
7. Check if the value of "iss" is https://wot.arces.unibo.it:8443/oauth/token 
8. Check if the value of "aud" contains https://wot.arces.unibo.it:8443/sparql 
9. Accept the request as well as "sub" as the originator of the request and process it as usual
 
Respond with 401 if not
 
</pre>

@param accessToken the JWT token to be validate according to points 4-9
	 */
	public synchronized Response validateToken(String accessToken) {
		logger.debug("Validate token");
		
		//Parse and verify the token
		SignedJWT signedJWT = null;
		try {
			signedJWT = SignedJWT.parse(accessToken);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","ParseException: "+e.getMessage());
		}

		try {
			 if(!signedJWT.verify(verifier)) {
				 logger.error("Signed JWT not verified");
				 return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","Signed JWT not verified");
			 }
			 
		} catch (JOSEException e) {
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","JOSEException: "+e.getMessage());
		}
		
		// Process the token (validate)
		JWTClaimsSet claimsSet = null;
		try {
			claimsSet = jwtProcessor.process(accessToken, context);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","ParseException: "+e.getMessage());
		} catch (BadJOSEException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","BadJOSEException: "+e.getMessage());
		} catch (JOSEException e) {
			logger.error(e.getMessage());
			return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","JOSEException: "+e.getMessage());
		}
		
//		//Check token expiration
//		Date now = new Date();
//		Date expiring = claimsSet.getExpirationTime();
//		Date notBefore = claimsSet.getNotBeforeTime();
//		if (expiring.getTime()-now.getTime() <= 0) return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","Token is expired "+claimsSet.getExpirationTime());		
//		if (now.getTime() < notBefore.getTime()) return new ErrorResponse(HttpStatus.SC_BAD_REQUEST,"invalid_grant","Token can not be used before: "+claimsSet.getNotBeforeTime());	
				
		return new JWTResponse(accessToken,"bearer",claimsSet.getExpirationTime().getTime()-new Date().getTime());
	}
	
	@Override
	public long getTokenExpiringPeriod() {
		return AuthorizationManagerBeans.getTokenExpiringPeriod();
	}
	

	@Override
	public void setTokenExpiringPeriod(long period) {
		AuthorizationManagerBeans.setTokenExpiringPeriod(period);
	}

	@Override
	public void addAuthorizedIdentity(String id) {
		AuthorizationManagerBeans.getAuthorizedIdentities().put(id, true);
	}

	@Override
	public void removeAuthorizedIdentity(String id) {
		AuthorizationManagerBeans.getAuthorizedIdentities().remove(id);
	}

	@Override
	public HashMap<String, Boolean> getAuthorizedIdentities() {
		return AuthorizationManagerBeans.getAuthorizedIdentities();
	}

	@Override
	public String getIssuer() {
		return AuthorizationManagerBeans.getIssuer();
	}

	@Override
	public void setIssuer(String issuer) {
		AuthorizationManagerBeans.setIssuer(issuer);
	}

	@Override
	public String getHttpsAudience() {
		return AuthorizationManagerBeans.getHttpsAudience();
	}

	@Override
	public void setHttpsAudience(String audience) {
		AuthorizationManagerBeans.setHttpsAudience(audience);
	}

	@Override
	public String getWssAudience() {
		return AuthorizationManagerBeans.getWssAudience();
	}

	@Override
	public void setWssAudience(String audience) {
		AuthorizationManagerBeans.setWssAudience(audience);
	}

	@Override
	public String getSubject() {
		return AuthorizationManagerBeans.getSubject();
	}

	@Override
	public void setSubject(String sub) {
		AuthorizationManagerBeans.setSubject(sub);
	}

	public SSLContext getSSLContext() throws SEPASecurityException {
		return sManager.getSSLContext();
	}	
}
