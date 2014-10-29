package alexzam.vacation.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class GoogleAuthService {

    private final SecureRandom random = new SecureRandom();

    public String generateRandomToken() {
        return new BigInteger(130, random).toString(32);
    }
}
