package ru.pomau.security.additional;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class Security {

    private Argon2 argon2 = Argon2Factory.create();

    private String alfabetLetter = "abcdefghijklmnopqrstuvwxyz", alfabetDigits = "1234567890";

    public String generatorToken(String token) {
        String hash = argon2.hash(22, 65536, 3, token.toCharArray());
        argon2.wipeArray(token.toCharArray());
        return hash;
    }

    public boolean verifyToken(String hash, String token) {
        return argon2.verify(hash,  token.toCharArray());
    }

    public String generateSession() {
        return getRandomString(alfabetDigits + alfabetLetter + alfabetLetter.toUpperCase(Locale.ROOT), 128);
    }

    public String generateP() {
        return getRandomString(alfabetDigits, 200);
    }

    public String generateG() {
        return getRandomString(alfabetDigits, 10);
    }

    private static String getRandomString(String alfabet, int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(alfabet.length());
            sb.append(alfabet.charAt(number));
        }
        // System.out.println(getRandomString(alfabetLetter + alfabetDigits + alfabetLetter.toUpperCase(), 128));
        return sb.toString();
    }

}
