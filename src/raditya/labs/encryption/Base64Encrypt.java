package raditya.labs.encryption;

public class Base64Encrypt {
	
	public static void Base64EncryptCode(){
		long start = System.currentTimeMillis();
		String enc = new String(org.apache.commons.codec.binary.Base64.encodeBase64(AppConstant.PLAIN_TEXT.getBytes()));
		long end = System.currentTimeMillis();
		long elapsedtime = end - start;
		System.out.printf("encyrpt elapsedtime : %s\n", elapsedtime);
		System.out.println("encrypt : " + enc);
		
		start = System.currentTimeMillis();
		String dec = new String(org.apache.commons.codec.binary.Base64.decodeBase64(enc.getBytes()));
		end = System.currentTimeMillis();
		elapsedtime = end - start;
		System.out.printf("decrypt elapsedtime : %s\n", elapsedtime);
		System.out.println("decrypt : " + dec);
	}
}
