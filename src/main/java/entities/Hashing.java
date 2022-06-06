package entities;
import java.util.Random;

public class Hashing {

    /**
     * Generate secure random initial passwordstring with 8 characters.
     */

    public static String genPassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[8];

        for(int i = 0; i < 8 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        String passwordStr = new String(password);
        return passwordStr;
    }
}

