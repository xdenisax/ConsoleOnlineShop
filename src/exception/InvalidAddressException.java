package exception;

public class InvalidAddressException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	public InvalidAddressException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return this.errorMessage;
	}
}
