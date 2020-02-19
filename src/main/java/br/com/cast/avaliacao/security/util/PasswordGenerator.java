package br.com.cast.avaliacao.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordGenerator {
	public static void main(String[] args) {
		String rawPassword = "123456";
		//String encodedPassword = "$2a$10$APFWWAOTREvEpmHm1upVw.LESH0MQ3/p24NuCbVzQ6HJNOzdMMreS";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
		System.out.println("Equal: " +BCrypt.checkpw(rawPassword.toString(), encodedPassword));
	}
}
