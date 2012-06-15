package com.buguw.common.util;

import com.buguw.common.multilang.MultiLanguageResourceBundle;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class StringUtil
{
  public static String formatXmlString(String paramString)
  {
    SAXReader localSAXReader = new SAXReader();
    StringReader localStringReader = new StringReader(paramString);
    Document localDocument = null;
    try
    {
      localDocument = localSAXReader.read(localStringReader);
    }
    catch (DocumentException localDocumentException)
    {
      ExceptionUtil.throwActualException(localDocumentException);
    }
    OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
    localOutputFormat.setSuppressDeclaration(true);
    StringWriter localStringWriter = new StringWriter();
    XMLWriter localXMLWriter = new XMLWriter(localStringWriter, localOutputFormat);
    try
    {
      localXMLWriter.write(localDocument);
    }
    catch (IOException localIOException)
    {
      ExceptionUtil.throwActualException(localIOException);
    }
    return localStringWriter.toString();
  }

  public static String formatXmlValue(String paramString)
  {
    if (paramString == null)
      return paramString;
    if (!isValidXmlString(paramString))
      throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("StringUtil.formatXmlValue.isInvalidXmlString", new String[] { paramString }));
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() > 16 ? paramString.length() * 2 : 32);
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ('&' == c)
        localStringBuffer.append("&amp;");
      else if ('<' == c)
        localStringBuffer.append("&lt;");
      else if ('>' == c)
        localStringBuffer.append("&gt;");
      else if ('\'' == c)
        localStringBuffer.append("&apos;");
      else if ('"' == c)
        localStringBuffer.append("&quot;");
      else
        localStringBuffer.append(c);
    }
    return localStringBuffer.toString();
  }

  public static String getDateString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i = localGregorianCalendar.get(1);
    int j = localGregorianCalendar.get(2) + 1;
    int k = localGregorianCalendar.get(5);
    localStringBuffer.append(Integer.toString(i));
    if (j < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(j));
    if (k < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(k));
    return localStringBuffer.toString();
  }

  public static String getDateTimeMillisString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i = localGregorianCalendar.get(1);
    int j = localGregorianCalendar.get(2) + 1;
    int k = localGregorianCalendar.get(5);
    int m = localGregorianCalendar.get(11);
    int n = localGregorianCalendar.get(12);
    int i1 = localGregorianCalendar.get(13);
    int i2 = localGregorianCalendar.get(14);
    localStringBuffer.append(Integer.toString(i));
    if (j < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(j));
    if (k < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(k));
    if (m < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(m));
    if (n < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(n));
    if (i1 < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(i1));
    localStringBuffer.append(Integer.toString(i2));
    return localStringBuffer.toString();
  }

  public static String getDateTimeString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i = localGregorianCalendar.get(1);
    int j = localGregorianCalendar.get(2) + 1;
    int k = localGregorianCalendar.get(5);
    int m = localGregorianCalendar.get(11);
    int n = localGregorianCalendar.get(12);
    int i1 = localGregorianCalendar.get(13);
    localStringBuffer.append(Integer.toString(i));
    if (j < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(j));
    if (k < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(k));
    if (m < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(m));
    if (n < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(n));
    if (i1 < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(i1));
    return localStringBuffer.toString();
  }

  public static String getTimeString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i = localGregorianCalendar.get(11);
    int j = localGregorianCalendar.get(12);
    int k = localGregorianCalendar.get(13);
    if (i < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(i));
    if (j < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(j));
    if (k < 10)
      localStringBuffer.append("0");
    localStringBuffer.append(Integer.toString(k));
    return localStringBuffer.toString();
  }

  public static String toLowerCaseFirstOne(String paramString)
  {
    if (Character.isLowerCase(paramString.charAt(0)))
      return paramString;
    return Character.toLowerCase(paramString.charAt(0)) + paramString.substring(1);
  }

  public static String toUpperCaseFirstOne(String paramString)
  {
    if (Character.isUpperCase(paramString.charAt(0)))
      return paramString;
    return Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
  }

  public static String leftString(String paramString, int paramInt)
  {
    byte[] arrayOfByte = paramString.getBytes();
    if (arrayOfByte.length <= paramInt)
      return paramString;
    int i = 0;
    for (int j = 0; j < paramInt; j++)
      if ((arrayOfByte[j] < 0) || (arrayOfByte[j] > 255))
      {
        i += 2;
        j++;
      }
      else
      {
        i++;
      }
    if (i > paramInt)
      i = paramInt - 1;
    return new String(arrayOfByte, 0, i);
  }

  public static boolean isValidXmlString(String paramString)
  {
    if (null == paramString)
      throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "string" }));
    for (int i = 0; i < paramString.length(); i++)
      if (!isWatchableChar(paramString.charAt(i)))
        return false;
    return true;
  }

  public static boolean isWatchableChar(char paramChar)
  {
    return (paramChar < 0) || (paramChar >= ' ');
  }
}

/* Location:           F:\360data\重要数据\桌面\giantstone-common-util.jar
 * Qualified Name:     com.giantstone.common.util.StringUtil
 * JD-Core Version:    0.6.0
 */