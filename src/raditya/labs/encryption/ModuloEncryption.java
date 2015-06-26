package raditya.labs.encryption;

public class ModuloEncryption {
	
	
	public static void ModuloEncryptionCode(){
		System.out.println("PLAIN TEXT : " + AppConstant.PLAIN_TEXT);
		
		long start = System.currentTimeMillis();
		String enc = encrypt(AppConstant.PLAIN_TEXT);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.printf("encrypt elapsedTime: %s\n", elapsedTime);
		System.out.println("encrypt : " + encrypt(AppConstant.PLAIN_TEXT));
		
		start = System.currentTimeMillis();
		String dec = decrypt(enc);
		end = System.currentTimeMillis();
		elapsedTime = end - start;	

		System.out.printf("decrypt elapsedTime: %s\n", elapsedTime);	
		System.out.println("decrypt : " + dec);
	}
	
	private static String encrypt(String value){
		StringBuffer buffer = new StringBuffer();
		int encryptInt;
		for (int i = 0; i < AppConstant.PLAIN_TEXT.length(); i++){
			int plainTextInt = (int)(AppConstant.PLAIN_TEXT.charAt(i) - 65);
			int secretTextInt = (int)(AppConstant.KEY.charAt(i) - 65);
			encryptInt = (plainTextInt + secretTextInt) % 26;
			buffer.append((char)((encryptInt) + (int) 'A'));
		}
		return buffer.toString();
	}
	
	private static String decrypt(String value){
		StringBuffer decryptedString = new StringBuffer();
		int decryptedInt;
		for (int i = 0; i < value.length(); i++) {
			int decryptedTextInt = (int) (value.charAt(i) - 'A');
			int secretKeyInt = (int) (AppConstant.KEY.charAt(i) - 'A');
			decryptedInt = decryptedTextInt - secretKeyInt;
			if (decryptedInt < 1)
				decryptedInt += 26;
			decryptedString.append((char) ((decryptedInt) + (int) 'A'));
		}
		return decryptedString.toString();
	}
}
