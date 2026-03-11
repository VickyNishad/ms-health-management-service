/**
 * 
 */
package com.health.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.health.models.GenericModel;

/**
 * 
 */
public class HealthUtils {
	
	private static final String _ALGORITHM = "AES";
    private static final String _DOT = ".";
    private static final String _TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String secretKey = "8@)*#&#8@)*)(&#@)(#4Q3&^%&^%#0987654321";
    
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
	public static String generateOtp() {
		Random random = new Random();
		int otp = random.nextInt(900000) + 100000;
		return String.valueOf(otp);
	}
	public static String getRandomPassword(String s1, String s2) {
		String prefix = "";
		String sufix = "";
		if (s1.length() == 1 || s1.length() == 2 || s1.length() == 3) {
			prefix = s1;
		}

		if (s1.length() > 3) {
			prefix = s1.substring(0, 3);
		}

		if (s2.length() == 1 || s2.length() == 2 || s2.length() == 3 || s2.length() == 4) {
			sufix = s2;
		}

		if (s2.length() > 4) {
			sufix = s2.substring(s2.length()-4);
		}
		
		return prefix.toUpperCase()+"@"+sufix.toUpperCase();

	}

    // ✅ Method 1: Encrypt password
    public static String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    // ✅ Method 2: Verify raw password with hashed password
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
    
    
	public static void removeKeyFromMap(Map<String, Object> map,String key) {
		map.remove(key);
		System.out.println("Successfully remove "+key+" from map");
	}

	
	public static int getIntValue(Object value) {
        if (value == null) {
            return 0; 
        }

        try {
            if (value instanceof Integer) {
                return (int) value;
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else if (value instanceof Number) {
                return ((Number) value).intValue(); 
            } else {
                return Integer.parseInt(value.toString());
            }
        } catch (NumberFormatException e) {
            System.err.println("Unable to convert to int: " + value);
            return 0; 
        }
    }
    
    public static String getStringValue(Object value) {
        if (value == null) {
            return ""; // 
        }

        return String.valueOf(value);
    }
    
      
    // AES Encryption method
    public static String encrypt(String data, String secretKey) {
        try {
            // Define the cipher algorithm, explicitly specifying padding and mode (CBC with PKCS5 padding)
            Cipher cipher = Cipher.getInstance(_TRANSFORMATION);

            // Generate a secret key specification based on the secret key provided
            SecretKeySpec keySpec = new SecretKeySpec(getAESKey(secretKey), _ALGORITHM);

            // Generate an IV (Initialization Vector)
            byte[] iv = new byte[16]; // AES uses a 16-byte IV
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParams = new IvParameterSpec(iv);

            // Initialize cipher in ENCRYPT_MODE with the key and IV
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParams);

            // Encrypt the data
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Return Base64 encoded IV followed by the encrypted data
            return Base64.getEncoder().encodeToString(iv) + _DOT + Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) {
        	 return null;
        }
    }

    // AES Decryption method
    public static String decrypt(String encryptedData, String secretKey) {
        try {
            // Split the encrypted data to extract the IV and actual ciphertext
            String[] parts = encryptedData.split("\\"+_DOT);
            byte[] iv = Base64.getDecoder().decode(parts[0]); // Extract the IV from the first part
            byte[] cipherText = Base64.getDecoder().decode(parts[1]); // Extract the encrypted data (ciphertext)

            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance(_TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(getAESKey(secretKey), _ALGORITHM);

            // Use the extracted IV for decryption
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParams);

            // Decrypt the data
            byte[] decryptedData = cipher.doFinal(cipherText);

            // Return the original string
            return new String(decryptedData, StandardCharsets.UTF_8);

        } catch (Exception e) {
            return null;
        }
    }
    
    
    public static byte[] getAESKey(String secretKey) throws Exception {
        // Use SHA-256 to hash the key and then truncate to the appropriate length
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        // Truncate or expand to 128, 192, or 256 bits (16, 24, or 32 bytes)
        return Arrays.copyOf(key, 16); // For AES-128, use 16 bytes (128 bits). For AES-256, use 32 bytes.
    }
    
    
    public static String generate_token(GenericModel<?> genericModel) {
        try {
            // Combine device id and mobileNumber and emailId and deviceType a unique UUID with timestamp
            Object object = genericModel.getRequest();
            String token_data = Convertors.convertObjectToString(object);
            // Encode token using Base64 encoding
            String accessToken = encrypt(token_data, secretKey);
            if (accessToken.length()>495){
                System.out.println("token_data " +"too long");
            }
            return accessToken;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getAuthorizationToken(String authorization){
    	if(Validators.isNullOrEmpty(authorization)) {
    		return "";
    	}
        String stringPrefix = authorization.substring(0,7);
        return stringPrefix.equalsIgnoreCase("Bearer ") ? authorization.substring(7) : authorization;
    }
    
    public static String getFileName(String fileName, MultipartFile file) {
    	String newFileName = "";
    	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    	String originalFilename = file.getOriginalFilename();
    	String ext = getFileExtension(file);
    	
    	if(Validators.isNullOrEmpty(fileName)) {
    		newFileName = timestamp + "_" + originalFilename;
    	}else {
    		newFileName = timestamp + "_" + fileName+"."+ext;
    	}
    	return newFileName;
    }
    
    
    public static String getFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();

        if (filename == null || !filename.contains(".")) {
            return "unknown";
        }

        // Extract everything after the last dot (.)
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

}
