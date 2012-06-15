package com.buguw.common.template;

import java.util.Map;

public class SimpleTemplateEngine
{
  public static String merge(String paramString, Map paramMap, boolean paramBoolean)
  {
    if (null == paramString)
      throw new IllegalArgumentException("template is null");
    String str = null;
    Object localObject = null;
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() > 16 ? paramString.length() : 16);
    int k = 0;
    while (k < paramString.length())
    {
      char c = paramString.charAt(k);
      if ((c == '$') && (k < paramString.length() - 1) && (paramString.charAt(k + 1) == '{'))
      {
        k++;
        k++;
        i = k;
        j = -1;
        while (k < paramString.length())
        {
          c = paramString.charAt(k);
          if (c == '}')
          {
            j = k++;
            break;
          }
          k++;
        }
        if ((k <= paramString.length()) && (-1 != j))
        {
          str = paramString.substring(i, j).trim();
          if (0 == str.length())
            throw new RuntimeException("Variable name is NULL");
          if (null == paramMap)
            throw new VariableMissException("Variable[" + str + "] is necessary! but Context is null!");
          localObject = paramMap.get(str);
          if (localObject != null)
          {
            localStringBuffer.append(localObject);
            continue;
          }
          if (!paramBoolean)
            continue;
          throw new VariableMissException("Variable[" + str + "] is missing!");
        }
        throw new RuntimeException("Variable must start with '${' and must be terminated by the matching '}'");
      }
      localStringBuffer.append(c);
      k++;
    }
    return localStringBuffer.toString();
  }
}
