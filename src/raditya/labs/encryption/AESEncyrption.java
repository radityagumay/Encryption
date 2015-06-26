package raditya.labs.encryption;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class AESEncyrption {
	
	private static final String ALGORITHM = "AES";
	
	private static long start = 0l;
	private static long end = 0l;
	private static long elapsed = 0l;
	
	public static void AESEncyrptionCode(){
		System.out.println("PLAIN TEXT : " + AppConstant.PLAIN_TEXT);
		
		start = System.currentTimeMillis();
		end = System.currentTimeMillis();
		String enc = encrypt(AppConstant.PLAIN_TEXT);
		end = System.currentTimeMillis();
		elapsed = end - start;
		System.out.printf("Encrypt elapsed time : %s\n", elapsed);
		System.out.println("Encrypt : " + enc);
		
		start = System.currentTimeMillis();
		String dec = decrypt(enc);
		end = System.currentTimeMillis();
		elapsed = end - start;
		System.out.printf("Decrypt elapsed time : %s\n", elapsed);
		System.out.println("Decrypt : " + dec);		
	}
	
	private static String encrypt(String value) {
		String enc = "";
		Cipher cipher;
		byte[] ency = null;
		Key key = null;
		try {
			key = generateKey(AppConstant.KEY);
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			ency = cipher.doFinal(value.getBytes());
			enc = new BASE64Encoder().encode(ency);
		} catch (Exception ex){
			throw new IllegalArgumentException("illegal " + ex.getMessage());	
		}
		return enc;
	}
	
	private static String decrypt(String value){
		String dec = "";
		Cipher cipher;
		byte[] decvalue = null;
		byte[] decodevalue = null;
		Key key = null;
		try {
			key = generateKey(AppConstant.KEY);
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decodevalue = new BASE64Decoder().decodeBuffer(value);
			decvalue = cipher.doFinal(decodevalue);
			dec = new String(decvalue);
		} catch (Exception ex){
			throw new IllegalArgumentException("illegal " + ex.getMessage());	
		}
		return dec;
	}

	private static Key generateKey(String valKey) throws Exception {
		byte[] sKey = valKey.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		sKey = sha.digest(sKey);
		sKey = Arrays.copyOf(sKey, 16);
		Key key = new SecretKeySpec(sKey, ALGORITHM);
		return key;
	}
}
