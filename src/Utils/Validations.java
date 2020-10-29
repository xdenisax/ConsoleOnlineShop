package Utils;

public class Validations {
	public static boolean isNameValid(String input) {
		if(input.length() < 3) {
			System.out.println("Introduceti un nume valid. Lungimea minima trebuie sa fie 3.");
			return false;
		}
		if(!isAlpha(input)) {
			System.out.println("Numele trebuie sa contina doar litere.");
			return false;
		}
		return true;
	}
	
	public static boolean isAddressValid(String input) {		
		if(input.length() < 7) {
			System.out.println("Introduceti un nume valid. Lungimea minima trebuie sa fie 7.");
			return false;
		}
		return true;
	}
	
	public static boolean isNumberValid(String input) {
		if(!isNumeric(input)) {
			System.out.println("Numarul de telefon trebuie sa contina doar cifre.");
			return false;
		}
		
		if(input.length() != 10) {
			System.out.println("Introduceti un numar de telefon cu 10 cifre.");
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

}
