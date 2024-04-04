package io.github.deanen1.reqresclient.service.reqres;

public class UserServiceException extends RuntimeException {

  public UserServiceException(String message) {
    super(message);
  }

  public UserServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
