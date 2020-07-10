package com.sgtcadet.timesheetws.api.util;

import com.sgtcadet.timesheetws.config.encryption.Encoders;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

public final class CustomUtil {
    private CustomUtil() { // private constructor
    }
    public static String hashString(String stringToHash) {
        if(stringToHash != null){
            Encoders encoder = new Encoders();
            PasswordEncoder passwordEncoder = encoder.userPasswordEncoder();
            return passwordEncoder.encode(stringToHash);
        }
        return null;
    }

    public static String formatDate(String format, Date dateToFormat){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(dateToFormat);
    }

    public static String encrypt(final String data, String secret) {

        byte[] decodedKey = Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while encrypting data", e);
        }

    }

    public static String decrypt(final String encryptedString, String secret) {

        byte[] decodedKey = Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while decrypting data", e);
        }
    }

    public static boolean validPhoneNumber(String number){
        return Pattern.compile("^\\d{10}$").matcher(number.trim()).find();
    }

    public static boolean validEmail(String email){
        return Pattern.compile(".+@.+\\..+").matcher(email.trim()).find();
    }
}
