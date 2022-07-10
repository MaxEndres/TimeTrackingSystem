package utility;
import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Check if input string is valid
     */
    public static boolean pwInvalid(String pw){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);

        if(pw == null){
            return true;
        }
        Matcher m = p.matcher(pw);
        if(m.matches()){
            return false;
        }
        return true;
    }
}

