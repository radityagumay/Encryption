package raditya.labs.encryption;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.bind.DatatypeConverter;

import Decoder.BASE64Encoder;

public class RSAEncryption {

	private String STRING_PUBLIC_KEY = "";
	private String STRING_PRIVATE_KEY = """;

	private Cipher cipher;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public RSAEncryption() throws Exception {
		generateKey();
	}

	@PostConstruct
	private void generateKey() throws Exception {
		cipher = Cipher.getInstance("RSA");
		byte[] publicKeyBytes = DatatypeConverter.parseBase64Binary(STRING_PUBLIC_KEY);
		ByteArrayInputStream bais = new ByteArrayInputStream(publicKeyBytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		this.publicKey = (PublicKey) ois.readObject();
		
		byte[] privateKeyBytes = DatatypeConverter.parseBase64Binary(STRING_PRIVATE_KEY);
		bais = new ByteArrayInputStream(privateKeyBytes);
		ois = new ObjectInputStream(bais);
		this.privateKey = (PrivateKey) ois.readObject();
	}

	public String encrypt(String plaintext) {
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bytes = plaintext.getBytes("UTF-8");
			byte[] encrypted = blockCipher(bytes, Cipher.ENCRYPT_MODE);
			return new String(DatatypeConverter.printBase64Binary(encrypted));
		} catch (InvalidKeyException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalStateException("illegal");
		}
	}

	public String decrypt(String encrypted) {
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bts = DatatypeConverter.parseBase64Binary(encrypted);
			byte[] decrypted = blockCipher(bts, Cipher.DECRYPT_MODE);
			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
			throw new IllegalStateException("illegal");
		}
	}

	private byte[] blockCipher(byte[] bytes, int mode)
			throws IllegalBlockSizeException, BadPaddingException {
		byte[] scrambled = new byte[0];
		byte[] toReturn = new byte[0];
		int length = (mode == Cipher.ENCRYPT_MODE) ? 100 : 128;
		byte[] buffer = new byte[length];
		for (int i = 0; i < bytes.length; i++) {
			if ((i > 0) && (i % length == 0)) {
				scrambled = cipher.doFinal(buffer);
				toReturn = append(toReturn, scrambled);
				int newlength = length;
				if (i + length > bytes.length) {
					newlength = bytes.length - i;
				}
				buffer = new byte[newlength];
			}
			buffer[i % length] = bytes[i];
		}
		scrambled = cipher.doFinal(buffer);
		toReturn = append(toReturn, scrambled);
		return toReturn;
	}

	private byte[] append(byte[] prefix, byte[] suffix) {
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i = 0; i < prefix.length; i++) {
			toReturn[i] = prefix[i];
		}
		for (int i = 0; i < suffix.length; i++) {
			toReturn[i + prefix.length] = suffix[i];
		}
		return toReturn;
	}

	public static void AsymmetricCode(){
		RSAEncryption crypt = null;
		try {
			crypt = new RSAEncryption();
		} catch (Exception e) {
			throw new IllegalStateException("Something happened : " + e.getMessage());
		}
		long start = System.currentTimeMillis();
		String encrypt = crypt.encrypt(AppConstant.PLAIN_TEXT);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.printf("encrypt elapsedTime: %s\n", elapsedTime);
		System.out.println("encrypt ==> " + encrypt);
		
		start = System.currentTimeMillis(); 
		String decrypt = crypt.decrypt(encrypt);
		end = System.currentTimeMillis(); 
		elapsedTime = end - start;
		System.out.printf("decrypt elapsedTime: %s\n", elapsedTime);
		System.out.println("decrypt ==> " + decrypt);
	}
	
	//TODO Generate Encoded Public Key and Encoded Private Key
	private static void generate(){
		KeyPairGenerator keyPairGenerator = null;
		KeyPair keyPair = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			keyPair = keyPairGenerator.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		String encodedPublicKey = new String(new BASE64Encoder().encode(keyPair.getPublic().getEncoded()));
		System.out.printf("Encoded PublicKey\n%s\n%s\n\n", encodedPublicKey, encodedPublicKey.length());
		
		String encodedPrivatecKey = new String(new BASE64Encoder().encode(keyPair.getPrivate().getEncoded()));
		System.out.printf("Encoded PrivateKey\n%s\n%s\n\n", encodedPrivatecKey, encodedPrivatecKey.length());
	}
}
