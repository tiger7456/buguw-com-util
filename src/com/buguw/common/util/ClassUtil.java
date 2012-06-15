package com.buguw.common.util;

import com.buguw.common.multilang.MultiLanguageResourceBundle;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil
{
  private static ClassLoader classLoader;

  public static void setClassLoader(ClassLoader paramClassLoader)
  {
    classLoader = paramClassLoader;
  }

  public static Object newInstance(String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    Class localClass = null;
    if (null == classLoader)
      localClass = Class.forName(paramString);
    else
      localClass = classLoader.loadClass(paramString);
    return newInstance(localClass, paramArrayOfObject);
  }

  public static Object newInstance(Class paramClass, Object[] paramArrayOfObject)
    throws Exception
  {
    Class[] arrayOfClass = getClassArrayByArgumentsArray(paramArrayOfObject);
    Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
    return localConstructor.newInstance(paramArrayOfObject);
  }

  private static Class[] getClassArrayByArgumentsArray(Object[] paramArrayOfObject)
  {
    Class[] arrayOfClass = new Class[paramArrayOfObject.length];
    int i = 0;
    int j = paramArrayOfObject.length;
    while (i < j)
    {
      arrayOfClass[i] = paramArrayOfObject[i].getClass();
      i++;
    }
    return arrayOfClass;
  }

  public static Object createClassInstance(String paramString)
  {
    Class localClass = null;
    try
    {
      if (null == classLoader)
        localClass = Class.forName(paramString);
      else
        localClass = classLoader.loadClass(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      ExceptionUtil.throwActualException(localException);
    }
    return createClassInstance(localClass);
  }

  public static Object createClassInstance(Class paramClass)
  {
    if (null == paramClass)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "_class" }));
    Object localObject = null;
    try
    {
      localObject = paramClass.newInstance();
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      ExceptionUtil.throwActualException(localException);
    }
    return localObject;
  }

  public static Object invoke(Object paramObject, String paramString, Class[] paramArrayOfClass, Object[] paramArrayOfObject)
  {
    if (null == paramObject)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "obj" }));
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "methodName" }));
    Class localClass = paramObject.getClass();
    Method localMethod = null;
    try
    {
      localMethod = localClass.getMethod(paramString, paramArrayOfClass);
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    Object localObject = null;
    try
    {
      localObject = localMethod.invoke(paramObject, paramArrayOfObject);
    }
    catch (Exception localException2)
    {
      ExceptionUtil.throwActualException(localException2);
    }
    return localObject;
  }

  public static Object getBeanAttributeValue(Object paramObject, String paramString)
  {
    return getBeanAttributeValue(paramObject, paramString, null);
  }

  public static Object getBeanAttributeValue(Object paramObject, String paramString1, String paramString2)
  {
    if (null == paramObject)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "bean" }));
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "attributeName" }));
    String str = "get" + paramString1.substring(0, 1).toUpperCase();
    if (paramString1.length() > 1)
      str = str + paramString1.substring(1);
    if (paramString2 != null)
      str = str + paramString2;
    return invoke(paramObject, str, null, null);
  }

  public static void setBeanAttributeValue(Object paramObject1, String paramString, Object paramObject2)
  {
    setBeanAttributeValue(paramObject1, paramString, null, paramObject2);
  }

  public static void setBeanAttributeValue(Object paramObject1, String paramString, Object paramObject2, Class paramClass)
  {
    setBeanAttributeValue(paramObject1, paramString, null, paramObject2, paramClass);
  }

  public static void setBeanAttributeValue(Object paramObject1, String paramString1, String paramString2, Object paramObject2)
  {
    setBeanAttributeValue(paramObject1, paramString1, paramString2, paramObject2, null);
  }

  public static void setBeanAttributeValue(Object paramObject1, String paramString1, String paramString2, Object paramObject2, Class paramClass)
  {
    if (null == paramObject1)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "bean" }));
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "attributeName" }));
    String str = "set" + paramString1.substring(0, 1).toUpperCase();
    if (paramString1.length() > 1)
      str = str + paramString1.substring(1);
    if (paramString2 != null)
      str = str + paramString2;
    if (paramClass == null)
      paramClass = paramObject2.getClass();
    invoke(paramObject1, str, new Class[] { paramClass }, new Object[] { paramObject2 });
  }

  public static void setBeanValueByPath(Object paramObject1, String paramString, Object paramObject2, Class paramClass)
  {
    int i = paramString.indexOf('.');
    if (-1 == i)
    {
      setBeanAttributeValue(paramObject1, paramString, paramObject2, paramClass);
    }
    else
    {
      Object localObject = getBeanAttributeValue(paramObject1, paramString.substring(0, i));
      if (null == localObject)
        throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.attribute.null", new String[] { paramString.substring(0, i) }));
      setBeanValueByPath(localObject, paramString.substring(i + 1), paramObject2, paramClass);
    }
  }

  public static void setBeanValueByPath(Object paramObject1, String paramString, Object paramObject2)
  {
    setBeanValueByPath(paramObject1, paramString, paramObject2, null);
  }

  public static Object getBeanValueByPath(Object paramObject, String paramString)
  {
    Object localObject1 = null;
    int i = paramString.indexOf('.');
    if (-1 == i)
      return getBeanAttributeValue(paramObject, paramString);
    Object localObject2 = getBeanAttributeValue(paramObject, paramString.substring(0, i));
    if (null == localObject2)
      throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.attribute.null", new String[] { paramString.substring(0, i) }));
    return getBeanValueByPath(localObject2, paramString.substring(i + 1));
  }

  public static Class getBeanFieldClass(Object paramObject, String paramString)
  {
    if (null == paramObject)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "bean" }));
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "attributeName" }));
    paramString = StringUtil.toLowerCaseFirstOne(paramString);
    Field localField = null;
    try
    {
      localField = paramObject.getClass().getDeclaredField(paramString);
    }
    catch (Exception localException)
    {
      ExceptionUtil.throwActualException(localException);
    }
    return localField.getType();
  }

  public static ClassLoader createClassLoader(ClassLoader paramClassLoader, String paramString)
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.createClassLoader.libPath.notExist", new String[] { "libPath" }));
    if (!localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.createClassLoader.libPath.notDirectory", new String[] { paramString }));
    if (!localFile.canRead())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.createClassLoader.libPath.canNotRead", new String[] { paramString }));
    ArrayList localArrayList = new ArrayList(64);
    getAllJarUrl(localArrayList, localFile);
    URL[] arrayOfURL = new URL[localArrayList.size()];
    localArrayList.toArray(arrayOfURL);
    for (int i = 0; i < arrayOfURL.length; i++)
      System.out.println(arrayOfURL[i]);
    URLClassLoader localURLClassLoader = new URLClassLoader(arrayOfURL, paramClassLoader);
    return localURLClassLoader;
  }

  private static void getAllJarUrl(List paramList, File paramFile)
  {
    String[] arrayOfString = paramFile.list(new FilenameFilter()
    {
      public boolean accept(File paramFile, String paramString)
      {
        return paramString.endsWith(".jar");
      }
    });
    File localFile = null;
    int i = 0;
    try
    {
      for (i = 0; i < arrayOfString.length; i++)
      {
        localFile = new File(paramFile, arrayOfString[i]);
        localFile = localFile.getCanonicalFile();
        paramList.add(localFile.toURL());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("ClassUtil.getAllJarUrl.listJar.error", new String[] { arrayOfString[i], localException.getMessage() }), localException);
    }
  }
}

