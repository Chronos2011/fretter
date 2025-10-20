package com.github.chronos2011.fretter.domain;

/**
 * Class DomainException extends {@link Exception} for domain-specific exceptions.
 */
public class DomainException extends Exception {
	/**
	 * Creates a new DomainException.
	 *
	 * @param message the message of the DomainException
	 * @param cause   the cause of the DomainException
	 */
	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new DomainException.
	 *
	 * @param message the message of the DomainException
	 */
	public DomainException(String message) {
		super(message);
	}

	/**
	 * Creates a new DomainException.
	 *
	 * @param cause the cause of the DomainException
	 */
	public DomainException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new DomainException.
	 */
	public DomainException() {
		super();
	}
}
