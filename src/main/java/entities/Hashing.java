package entities;

import org.mindrot.jbcrypt.BCrypt;
import java.security.SecureRandom;

public class Hashing {

    /**
     * generate initial password, generate salt and return hashed+salted initial pw
     * */

    public static String initPW(String email) {

        // Generate salt
        String salt = BCrypt.gensalt();

        // Write salt in DB

        // Generate initial password
        String initPW = genPassword();

        // print initPW on screen

        String hash = BCrypt.hashpw(initPW, salt);

        return hash;

    }


    /**
     * Generate secure random initial passwordstring
     */

    public static String genPassword() {

        SecureRandom random = new SecureRandom();

        byte[] initPW = new byte[16];

        random.nextBytes(initPW);

        String pw = new String(initPW);

        return pw;

    }

}