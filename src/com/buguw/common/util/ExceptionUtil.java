package com.buguw.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

public class ExceptionUtil
{
  public static void throwActualException(Throwable paramThrowable)
  {
    paramThrowable = getActualException(paramThrowable);
    if ((paramThrowable instanceof RuntimeException))
      throw ((RuntimeException)paramThrowable);
    if ((paramThrowable instanceof Error))
      throw ((Error)paramThrowable);
    throw new RuntimeException(paramThrowable.getMessage(), paramThrowable);
  }

  public static Throwable getActualException(Throwable paramThrowable)
  {
    int i = 0;
    while (i == 0)
    {
      if ((paramThrowable instanceof RemoteException))
      {
        paramThrowable = ((RemoteException)paramThrowable).detail;
        continue;
      }
      if ((paramThrowable instanceof InvocationTargetException))
      {
        paramThrowable = ((InvocationTargetException)paramThrowable).getCause();
        continue;
      }
      i = 1;
    }
    return paramThrowable;
  }

  public static String getExceptionDetail(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    return localStringWriter.toString();
  }
}

/* Location:           F:\360data\重要数据\桌面\giantstone-common-util.jar
 * Qualified Name:     com.giantstone.common.util.ExceptionUtil
 * JD-Core Version:    0.6.0
 */