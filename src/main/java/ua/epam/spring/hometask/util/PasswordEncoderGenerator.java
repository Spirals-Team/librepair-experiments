package ua.epam.spring.hometask.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Vladimir_Vysokomorny on 23-Jan-18.
 */
public class PasswordEncoderGenerator {

    public static void main(String[] args){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String hashedPassword = passwordEncoder.encode("password");
        System.out.println(hashedPassword);
        System.out.println(passwordEncoder.matches("password", "$2a$12$KiAaFnYXRWjvp8ScuN2/WuYL.yIwQbSyRg1wFmvAwrhrThzqXFsri"));
    }

}
