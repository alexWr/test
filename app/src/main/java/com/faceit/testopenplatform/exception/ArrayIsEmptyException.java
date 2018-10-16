package com.faceit.testopenplatform.exception;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class ArrayIsEmptyException extends Exception {

  public ArrayIsEmptyException() {
    super();
  }

  public ArrayIsEmptyException(final Throwable cause) {
    super(cause);
  }
}
