package com.example.stefan.exception;

public class AccessForbiddenException extends RuntimeException
{
  public AccessForbiddenException(String message)
  {
    super(message);
  }

  public AccessForbiddenException(String message, Throwable cause)
  {
    super(message, cause);
  }
}

