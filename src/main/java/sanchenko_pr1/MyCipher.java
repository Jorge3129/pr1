package sanchenko_pr1;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyCipher {
    private final Cipher cipher;
    private final Key cipherKey;
    private final IvParameterSpec ivspec;

    public MyCipher(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(algorithm);
        this.cipherKey = MyCipher.generateKey();
        this.ivspec = MyCipher.generateIv();
    }

    public byte[] encrypt(byte[] input)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.cipherKey, this.ivspec);
        return cipher.doFinal(input);
    }

    public byte[] decrypt(byte[] input)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.cipherKey, this.ivspec);
        return cipher.doFinal(input);
    }

    private static Key generateKey()
            throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void main(String[] args) {
        try {
            String algorithm = "AES";
            MyCipher cipher = new MyCipher(algorithm);
            Message message = new Message(CommandType.CREATE, 1, new byte[]{1, 2, 3});
            Packet packet = new Packet((byte) 1, 1, message);
            byte[] data = new ObjectMapper().writeValueAsBytes(packet);
            byte[] encrypted = cipher.encrypt(data);
            byte[] decrypted = cipher.decrypt(encrypted);
            System.out.println(Arrays.toString(decrypted));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}