package net.yxy.chukonu.spring.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class EncryptUtil {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder() ;

    public static String encryptPassword(String rawPwd) {
        return encoder.encode(rawPwd);
    }
    
    public static void main(String[] args) {
        System.out.println(encryptPassword("admin@123"));
        System.out.println(encryptPassword("editor@123"));
        System.out.println(encryptPassword("view@123"));
    }
}
