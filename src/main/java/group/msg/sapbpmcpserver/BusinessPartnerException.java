package group.msg.sapbpmcpserver;

/**
 * Simple exception class
 */
public class BusinessPartnerException extends Exception {

	/** UUID for serialization */
	private static final long serialVersionUID = 2381174599400642508L;

	public BusinessPartnerException() {
		super();
	}

	public BusinessPartnerException(String message) {
		super(message);
	}

	public BusinessPartnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessPartnerException(Throwable cause) {
		super(cause);
	}
}