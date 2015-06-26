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

/*import org.springframework.stereotype.Component;
 import com.srin.ggi.server.ggi.api.exception.EncryptionException;
 import com.srin.ggi.server.ggi.api.util.Cryptography.Asymmetric;*//**
 * * * @author
 * aris *
 */
/*
 * @Componentpublic class AsymmetricCryptographyImpl
 */
public class RSAEncryption {

	private String STRING_PUBLIC_KEY = "rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANSU0F1cgACW0Ks8xf4BghU4AIAAHhwAAAAojCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAmFBhhP+j1k3p0j9j7j1jEXQD9M/fjmdsJ/ZzdcN4Rd0d7F/FaiR/4F6FI8Vcd7CebOuGvbStbUKEUx3813a2BCeqsNYqmAhzDbgvGnGlKxmPb7XZF9+Ws82vi3Gxo/+6b5BMsygt7TdSc3Rjf6uXeMxAGdr862L2FHRDeVeXajcCAwEAAXQABVguNTA5fnIAGWphdmEuc2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAAEgAAeHB0AAZQVUJMSUM=";
	private String STRING_PRIVATE_KEY = "rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANSU0F1cgACW0Ks8xf4BghU4AIAAHhwAAACejCCAnYCAQAwDQYJKoZIhvcNAQEBBQAEggJgMIICXAIBAAKBgQCYUGGE/6PWTenSP2PuPWMRdAP0z9+OZ2wn9nN1w3hF3R3sX8VqJH/gXoUjxVx3sJ5s64a9tK1tQoRTHfzXdrYEJ6qw1iqYCHMNuC8acaUrGY9vtdkX35azza+LcbGj/7pvkEyzKC3tN1JzdGN/q5d4zEAZ2vzrYvYUdEN5V5dqNwIDAQABAoGATgR2Pyq2oBHen9vqcuCsuwbi8+X6S7X792hMcdjC5X5CUo4nz5uMiL/BkLYKDCDCtWXa6Zb/ckStBOoKVc8voZkep1JUJHkljxXaosOzPOyN+aqzjCL87RnXnUY9Hj61c6C8z6fuYJHOf2yPKe26fOcoAloY18Qz21EaG24foPECQQDvkrlY3ICooYZ2dtDwdBlxnoKZY+0kMs2uMD11VNMWvJw/lUIC9CXfrBSwb09V+oJYVshIGNg6fNqaE4YPOvm/AkEAosH6sz+Ye09s84JGRMUrneX5NWal6XtXQAhGnMwPydtE451N7CjSc8JVUPiOCXS1GZa3dw/Vl6ZC/RimCUr9iQJAPz2oF8D9ZYZeDF+JADBllwyUxREPxIJ0sQqi7poZruLTDCXGaykv5j9yprB7bOLYDaG69O06BxZ2Tj3hIZwb2QJBAJQMW119tdhFEVgX4o9fwyMoNB5NNxJBLcaDQKgUPzBgkjNxvZXaNEU0OqMi1SDo1mtmW5CkCKifhkPvjejaG/ECQDdz/IhJZxykEsz37mJevyELcSFOm1uuPz2UY6t8PTlZ08OYOjgEevGBQYsPqCLCXKj5c9bmmZeGnmTDTImbZul0AAZQS0NTIzh+cgAZamF2YS5zZWN1cml0eS5LZXlSZXAkVHlwZQAAAAAAAAAAEgAAeHIADmphdmEubGFuZy5FbnVtAAAAAAAAAAASAAB4cHQAB1BSSVZBVEU=";

	/*
	 * public static void main(String[] args) throws Exception{
	 * 
	 * }
	 */

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
