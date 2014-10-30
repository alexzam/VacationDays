package alexzam.vacation.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.util.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

@Service
public class GoogleAuthService {

    private final SecureRandom random = new SecureRandom();

    @Autowired
    private GoogleIdTokenVerifier idTokenVerifier;

    @Value("${environment.getProperty('auth.google.client_id')}")
    private String clientId;

    @SuppressWarnings("FieldCanBeLocal")
    private final String ISSUER = "accounts.google.com";

    public String generateRandomToken() {
        return new BigInteger(130, random).toString(32);
    }

    public String getIdFromToken(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdToken token = idTokenVerifier.verify(idToken);
        GoogleIdToken.Payload tokenPayload = token.getPayload();

        if (!clientId.equals(tokenPayload.getAudience()))
            throw new GeneralSecurityException("ID token for wrong client ID");
        if (!ISSUER.equals(tokenPayload.getIssuer())) throw new GeneralSecurityException("ID token from wrong issuer");

        return tokenPayload.getSubject();
    }
}
