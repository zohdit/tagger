package exceptions;


import java.lang.Exception;

public class InvalidValueException extends Exception {

	private static final long serialVersionUID = -7665581268972110249L;

	public InvalidValueException() {
		super("Invalid value for the variable");
	}

	public InvalidValueException(String pMessage) {
		super(pMessage);
	}
}