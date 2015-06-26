package raditya.labs.encryption;

public class XOREncryption {

	public static void XOREncryptionCode(){
		System.out.println("PLAIN TEXT : " + AppConstant.PLAIN_TEXT);
		
		long start = System.currentTimeMillis();
		String enc = encrypt(AppConstant.PLAIN_TEXT);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.printf("encrypt elapsedTime: %s\n", elapsedTime);
		System.out.println("encrypt : " + enc);
		
		start = System.currentTimeMillis();
		String dec = decrypt(enc);
		end = System.currentTimeMillis();
		elapsedTime = end - start;	

		System.out.printf("decrypt elapsedTime: %s\n", elapsedTime);	
		System.out.println("decrypt : " + dec);
	}
	
	private static String encrypt(String str){
		StringBuffer sb = new StringBuffer(str);
		int lenStr = str.length();
		int lenKey = AppConstant.KEY.length();
		
		for(int i = 0, j = 0; i < lenStr; i++, j++){
			if(j >= lenKey) j = 0;
			
			sb.setCharAt(i, (char)(str.charAt(i) ^ AppConstant.KEY.charAt(j)));
		}
		return sb.toString();
	}
	
	private static String decrypt(String str){
		return encrypt(str);
	}
}
