package com.buguw.common.template;

public class VariableMissException extends RuntimeException
{
  public VariableMissException()
  {
  }

  public VariableMissException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public VariableMissException(String paramString)
  {
    super(paramString);
  }

  public VariableMissException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}
