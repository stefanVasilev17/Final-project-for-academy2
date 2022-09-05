package com.example.stefan.exception;

public class IllegalDataException extends RuntimeException
{
  public IllegalDataException(String message)
  {
    super(message);
  }

  public IllegalDataException(String message, Throwable cause)
  {
    super(message, cause);
  }
}

