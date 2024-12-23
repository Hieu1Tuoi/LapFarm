package LapFarm.Utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SecureUrlUtil {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "0912200315112001"; // Thay bằng khóa bí mật mạnh hơn (32 ký tự)

    // Tạo khóa bí mật (Optional nếu không muốn sử dụng key cố định)
    public static String generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // Hoặc 192, 256 tùy vào độ dài khóa AES
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Mã hóa ID
 // Mã hóa ID an toàn cho URL
    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        
        // Sử dụng URL-safe Base64
        return Base64.getUrlEncoder().encodeToString(encrypted);
    }


    // Giải mã ID
 // Giải mã ID an toàn cho URL
    public static String decrypt(String cipherText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        // Giải mã URL-safe Base64
        byte[] decodedBytes = Base64.getUrlDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }


}
