package unidy.springstarter.common.exception;

public class RestfulException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RestfulException() {
		super();
	}

	public RestfulException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RestfulException(final String message) {
		super(message);
	}

	public RestfulException(final Throwable cause) {
		super(cause);
	}

}
