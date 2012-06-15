package com.buguw.common.template;

public class MessageTemplateUtil
{
  public static String assempleMessage(String paramString, Object[] paramArrayOfObject)
  {
    if (null == paramString)
      throw new IllegalArgumentException("messageTemplate is null");
    if (0 == paramArrayOfObject.length)
      return paramString;
    String str = null;
    int i = 0;
    Object localObject = null;
    int j = 0;
    int k = 0;
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() > 16 ? paramString.length() * 2 : 16);
    int m = 0;
    while (m < paramString.length())
    {
      char c = paramString.charAt(m);
      if ((c == '{') && (m < paramString.length() - 1))
      {
        m++;
        j = m;
        k = -1;
        while (m < paramString.length())
        {
          c = paramString.charAt(m);
          if (c == '}')
          {
            k = m++;
            break;
          }
          m++;
        }
        if ((m <= paramString.length()) && (-1 != k))
        {
          str = paramString.substring(j, k).trim();
          if (0 == str.length())
            throw new RuntimeException("argument is NULL");
          i = Integer.parseInt(str);
          if ((i >= paramArrayOfObject.length) || (i < 0))
            continue;
          localStringBuffer.append(paramArrayOfObject[i] == null ? "null" : paramArrayOfObject[i]);
          continue;
        }
        throw new RuntimeException("argument must start with '{' and must be terminated by the matching '}'");
      }
      localStringBuffer.append(c);
      m++;
    }
    return localStringBuffer.toString();
  }
}

