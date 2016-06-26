package cz.paka.sachy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {

	/**
	 * oreze string od neviditelnych znaku + odstrani diakritiku a velka pismena
	 */
	public static String orez(String string) {
		string = string.trim();
		string = string.toLowerCase();
		string = StringUtils.stripAccents(string); // oreze diakritiku
		return string;
	}

	/**
	 * zahashuje heslo pomoci sha256
	 */
	public static String sha256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());
		byte byteData[] = md.digest();
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * vraci jmeno aktualne prihlaseneho uzivatele - viz http://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
	 */
	public static String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}
}
