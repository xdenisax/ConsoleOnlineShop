package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.InvalidNameException;

import exception.InvalidAddressException;

public class Validations {
	public static boolean isNameValid(String input) {
		try {
			if(input.length() < 3) {
				throw new InvalidNameException("Introduceti un nume valid. Lungimea minima trebuie sa fie 3.");
			}
			if(!isAlpha(input)) {				
				throw new InvalidNameException("Numele trebuie sa contina doar litere.");
			}		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}
	
	public static boolean isAddressValid(String input) {	
		try {
			if(input.length() < 7) {
				throw new InvalidAddressException("Introduceti un nume valid. Lungimea minima trebuie sa fie 7.");
			}		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}
	
	public static boolean isNumberValid(String input) {
		try {
			if(!isNumeric(input)) {
				throw new IllegalArgumentException("Numarul de telefon trebuie sa contina doar cifre.");
			}
			if(input.length() != 10) {
				throw new IllegalArgumentException("Introduceti un numar de telefon cu 10 cifre.");
			}	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;		
	}
	
	public static boolean isAlpha(String text) {
	    return text.matches("[a-zA-Z]+");
	}
	
	public static boolean isAlphaNumeric(String text) {
	    return text.matches("[A-Za-z0-9]+");
	}
	
	public static boolean isNumeric(String text) {
	    return text.matches("-?\\d+(.\\d+)?");
	}
	
	public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
	
	public static boolean isValidPrice(float price ) {
		if(price < 0) {
			return false;
		}
		return true;
	}

}
