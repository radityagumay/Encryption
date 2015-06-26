package raditya.labs.encryption;

import java.util.Scanner;

public class MainProject {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int value = sc.nextInt();
		switch (value) {
		case 1: //RSA
			RSAEncryption.AsymmetricCode();
			break;
		case 2: //XOREncyrption
			XOREncryption.XOREncryptionCode();
			break;
		case 3: //Modulo
			ModuloEncryption.ModuloEncryptionCode();
			break;
		case 4: //Base64
			Base64Encrypt.Base64EncryptCode();
			break;
		case 5: //AES
			AESEncyrption.AESEncyrptionCode();
			break;	
		case 6: //TEA
			TEA.TEACode();
			break;	
		}
	}
}
