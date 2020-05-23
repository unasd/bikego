package kr.co.bikego.test;

import kr.co.bikego.util.AES256Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;

@SpringBootTest(classes = { kr.co.bikego.SecurityConfig.class })
public class BootTest {
    @Autowired
    AES256Util aes;

    @Test
    public void cryptoTest() throws GeneralSecurityException, UnsupportedEncodingException {
        String encString = aes.encrypt("01031660256");
        System.out.println("encString :: " + encString);

        String decString = aes.decrypt(encString);
        System.out.println("decString :: " + decString);
    }
}
