package net.thumbtack.buscompany.security;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static net.thumbtack.buscompany.security.PBKDF2.obtainHash;

@Component
public class PasswordEncoder {

    public byte[] getSalt() {
        return PBKDF2.obtainSalt();
    }

    public String encode(String password, byte[] salt) {
        try {
            return PBKDF2.toHexStr(obtainHash(password, salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
