package utils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class Crypto {

    public static final String UNICODE_FORMAT = "UTF-8";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 256;


    /**
     * generates a unique encryption key according to the 3 parameter specifications
     * @param encryptionType determines encryption type
     * @param password users input
     * @param salt decoded salt from file
     * @return
     */
    public SecretKey generateKey(String encryptionType, char[] password, byte[] salt){

        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance(encryptionType);
            KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            return secretKey;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * generates salt
     * @param length length of the salt
     * @return salt
     */
    public byte[] generateSalt (String length) {
        SecureRandom RAND = new SecureRandom();

        if (length.length() < 1) {
            System.out.println("generateSalt: length must be > 0");
            return null;
        }

        byte[] salt = new byte[SALT_LENGTH];
        RAND.nextBytes(salt);

        return salt;
    }


    /**
     * encrypts string
     * @param dataToEncrypt clear text
     * @param secretKey unique key that is used to encrypt the data
     * @param cipher object Cipher is used to do the encryption
     * @return encrypted string
     */
    public byte[] encryptString(String dataToEncrypt, SecretKey secretKey, Cipher cipher) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
            byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] textEncrypted = cipher.doFinal(text);

            return textEncrypted;
    }


    /**
     * decrypts string
     * @param dataToDecrypt encrypted string
     * @param secretKey unique key that is used to decrypt the data
     * @param cipher object Cipher is used to do the decryption
     * @return decrypted string
     * @exception InvalidKeyException gets thrown if wrong key is provided
     */
    public String decryptString(byte[] dataToDecrypt, SecretKey secretKey, Cipher cipher) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
        String result = new String(textDecrypted);

        return result;
    }

    public static void main(String[] args) {

    }
}