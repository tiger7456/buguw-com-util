package com.buguw.common.util;

import com.buguw.common.multilang.MultiLanguageResourceBundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;

import sun.nio.ch.FileChannelImpl;

public class FileUtil
{
  public static String loadData(String paramString1, String paramString2)
  {
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString1);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.notExist", new String[] { paramString1 }));
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString1 }));
    char[] arrayOfChar = new char[(int)localFile.length()];
    FileInputStream localFileInputStream = null;
    InputStreamReader localInputStreamReader = null;
    try
    {
      localFileInputStream = new FileInputStream(localFile);
      localInputStreamReader = new InputStreamReader(localFileInputStream, paramString2);
      localInputStreamReader.read(arrayOfChar);
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    finally
    {
      if (null != localInputStreamReader)
        try
        {
          localInputStreamReader.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace(System.err);
        }
      if (null != localFileInputStream)
        try
        {
          localFileInputStream.close();
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace(System.err);
        }
    }
    return new String(arrayOfChar);
  }

  public static byte[] loadData(String paramString)
  {
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.notExist", new String[] { paramString }));
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString }));
    byte[] arrayOfByte = new byte[(int)localFile.length()];
    FileInputStream localFileInputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(localFile);
      localFileInputStream.read(arrayOfByte);
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    finally
    {
      if (null != localFileInputStream)
        try
        {
          localFileInputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace(System.err);
        }
    }
    return arrayOfByte;
  }

  public static void writeData(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if ((null == paramArrayOfByte) || (0 == paramArrayOfByte.length))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "data" }));
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.notExist", new String[] { paramString }));
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString }));
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileOutputStream = new FileOutputStream(localFile, paramBoolean);
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.flush();
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    finally
    {
      if (null != localFileOutputStream)
        try
        {
          localFileOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace(System.err);
        }
    }
  }
  public static FileChannel getOutputChannel(String s)
          throws IOException
      {
          FileOutputStream fileoutputstream = new FileOutputStream(s);
          FileChannel filechannel = FileChannelImpl.open(fileoutputstream.getFD(), true, true, fileoutputstream);
          return filechannel;
      }

      public static FileChannel getInputChannel(String s)
          throws IOException
      {
          FileInputStream fileinputstream = new FileInputStream(s);
          FileChannel filechannel = FileChannelImpl.open(fileinputstream.getFD(), true, true, fileinputstream);
          return filechannel;
      }
  private static File checkFileName(String paramString)
  {
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.notExist", new String[] { paramString }));
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString }));
    return localFile;
  }

  public static void saveAsData(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if ((null == paramArrayOfByte) || (0 == paramArrayOfByte.length))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "data" }));
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString);
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString }));
    if (localFile.getParentFile() != null)
      localFile.getParentFile().mkdirs();
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileOutputStream = new FileOutputStream(localFile, paramBoolean);
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.flush();
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    finally
    {
      if (null != localFileOutputStream)
        try
        {
          localFileOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace(System.err);
        }
    }
  }

  public static void saveAsData(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if ((null == paramString2) || (0 == paramString2.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "data" }));
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString1);
    if (localFile.isDirectory())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory", new String[] { paramString1 }));
    if (localFile.getParentFile() != null)
      localFile.getParentFile().mkdirs();
    FileOutputStream localFileOutputStream = null;
    OutputStreamWriter localOutputStreamWriter = null;
    try
    {
      localFileOutputStream = new FileOutputStream(localFile, paramBoolean);
      localOutputStreamWriter = new OutputStreamWriter(localFileOutputStream, paramString3);
      localOutputStreamWriter.write(paramString2);
      localOutputStreamWriter.flush();
    }
    catch (Exception localException1)
    {
      ExceptionUtil.throwActualException(localException1);
    }
    finally
    {
      if (null != localOutputStreamWriter)
        try
        {
          localOutputStreamWriter.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace(System.err);
        }
      if (null != localFileOutputStream)
        try
        {
          localFileOutputStream.close();
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace(System.err);
        }
    }
  }

  public static boolean isExist(String paramString)
  {
    if ((null == paramString) || (0 == paramString.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "fileName" }));
    File localFile = new File(paramString);
    return localFile.exists();
  }

  /** @deprecated */
  public static boolean isExits(String paramString)
  {
    return isExist(paramString);
  }

  public static byte[] getDigest(String paramString)
  {
    File localFile = checkFileName(paramString);
    MessageDigest localMessageDigest = null;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      ExceptionUtil.throwActualException(localNoSuchAlgorithmException);
    }
    long l1 = localFile.length();
    long l2 = 0L;
    int i = 0;
    FileInputStream localFileInputStream = null;
    byte[] arrayOfByte2=null;
    try
    {
      localFileInputStream = new FileInputStream(localFile);
      byte[] arrayOfByte1 = new byte[10240];
      while (l2 < l1)
      {
        i = localFileInputStream.read(arrayOfByte1);
        if (i <= 0)
          throw new RuntimeException("onceRead=" + i);
        localMessageDigest.update(arrayOfByte1, 0, i);
        l2 += i;
      }
      arrayOfByte2 = localMessageDigest.digest();
    }
    catch (Exception localException)
    {
      
      ExceptionUtil.throwActualException(localException);
    }
    finally
    {
      if (localFileInputStream != null)
        try
        {
          localFileInputStream.close();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
    }
    return arrayOfByte2;
  }

  public static String getDigestAsString(String paramString)
  {
    return new String(CodeUtil.BytetoHex(getDigest(paramString)));
  }

  public static boolean verifyDigest(String paramString, byte[] paramArrayOfByte)
  {
    if (null == paramArrayOfByte)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "digest" }));
    byte[] arrayOfByte = getDigest(paramString);
    if (arrayOfByte.length != paramArrayOfByte.length)
      return false;
    for (int i = 0; i < arrayOfByte.length; i++)
      if (arrayOfByte[i] != paramArrayOfByte[i])
        return false;
    return true;
  }

  public static boolean verifyDigest(String paramString1, String paramString2)
  {
    if ((null == paramString2) || (0 == paramString2.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "digestString" }));
    byte[] arrayOfByte = CodeUtil.HextoByte(paramString2);
    return verifyDigest(paramString1, arrayOfByte);
  }

  public static void move(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString1.equals(paramString2)))
      return;
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.sourceFile.notExist"));
    File localFile2 = new File(paramString2);
    if (localFile2.exists())
      localFile2.delete();
    File localFile3 = localFile2.getParentFile();
    localFile3.mkdirs();
    localFile1.renameTo(localFile2);
  }

  public static void copy(String paramString1, String paramString2)
  {
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.sourceFile.null"));
    if (paramString1.equals(paramString2))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.sourceFile.destFile.same"));
    File localFile1 = new File(paramString2);
    File localFile2 = localFile1.getParentFile();
    if (!localFile2.exists())
      localFile2.mkdirs();
    byte[] arrayOfByte = new byte[8192];
    int i = 0;
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(paramString1);
      localFileOutputStream = new FileOutputStream(paramString2);
      while ((i = localFileInputStream.read(arrayOfByte)) > 0)
        localFileOutputStream.write(arrayOfByte, 0, i);
    }
    catch (Exception localException)
    {
      ExceptionUtil.throwActualException(localException);
    }
    finally
    {
      close(localFileInputStream);
      close(localFileOutputStream);
    }
  }

  public static void waitForAccess(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()))
      return;
    Stack localStack = new Stack();
    int i = 0;
    while (true)
    {
      localStack.push(Long.valueOf(paramFile.length()));
      try
      {
        Thread.sleep(5000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      i++;
      if (i != 3)
        continue;
      Long localLong1 = (Long)localStack.pop();
      Long localLong2 = (Long)localStack.pop();
      Long localLong3 = (Long)localStack.pop();
      if ((localLong1.equals(localLong2)) && (localLong1.equals(localLong3)))
        break;
      i = 0;
      localStack.clear();
    }
  }

  public static boolean delete(String paramString)
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("file.notExist", new String[] { paramString }));
    return localFile.delete();
  }

  private static void close(InputStream paramInputStream)
  {
    if (paramInputStream != null)
      try
      {
        paramInputStream.close();
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
  }

  private static void close(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null)
      try
      {
        paramOutputStream.close();
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
  }

  

  public static String getFilePath(String paramString1, String paramString2)
  {
    if (paramString2 == null)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "file" }));
    if ((paramString1 == null) || (paramString1.length() < 1))
      return new File(paramString2).getAbsolutePath();
    File localFile = new File(paramString1);
    localFile.mkdirs();
    return new File(paramString1, paramString2).getAbsolutePath();
  }

  public static void copyAllFromDirectory(File paramFile1, File paramFile2)
  {
    if (null == paramFile1)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "srcFile" }));
    if (null == paramFile2)
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "srcFile" }));
    String str1 = null;
    try
    {
      str1 = paramFile1.getCanonicalPath();
    }
    catch (IOException localIOException1)
    {
      ExceptionUtil.throwActualException(localIOException1);
    }
    String str2 = null;
    try
    {
      str2 = paramFile2.getCanonicalPath();
    }
    catch (IOException localIOException2)
    {
      ExceptionUtil.throwActualException(localIOException2);
    }
    if (str1.equals(str2))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.sourcePath.destPath.same"));
    copyAllFromDirectory(str1, str2);
  }

  public static void copyAllFromDirectory(String paramString1, String paramString2)
  {
    if ((null == paramString1) || (0 == paramString1.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "srcPath" }));
    if ((null == paramString2) || (0 == paramString2.length()))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] { "destPath" }));
    if (paramString1.equals(paramString2))
      throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.sourcePath.destPath.same"));
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      return;
    File localFile2 = new File(paramString2);
    if (localFile2.isFile())
      throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("FileUtil.destPath.notDirectory", new String[] { paramString2 }));
    if (!localFile2.exists())
      localFile2.mkdirs();
    Project localProject = new Project();
    Copy localCopy = new Copy();
    localCopy.setProject(localProject);
    if (localFile1.isFile())
    {
      localCopy.setFile(localFile1);
    }
    else
    {
      FileSet localFileSet = new FileSet();
      localFileSet.setDir(localFile1);
      localCopy.addFileset(localFileSet);
    }
    localCopy.setTodir(localFile2);
    localCopy.execute();
  }

  public static void main(String[] paramArrayOfString)
  {
    copyAllFromDirectory("d:\\123\\12", "d:\\111");
    System.out.println();
  }
}

/* Location:           F:\360data\重要数据\桌面\giantstone-common-util.jar
 * Qualified Name:     com.giantstone.common.util.FileUtil
 * JD-Core Version:    0.6.0
 */