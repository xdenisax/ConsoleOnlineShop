package exception;

public class InvalidNameException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	public InvalidNameException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public String getMessage() {
		return this.errorMessage;
	}
}
