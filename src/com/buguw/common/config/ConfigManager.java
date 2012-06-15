package com.buguw.common.config;

import com.buguw.common.multilang.MultiLanguageResourceBundle;
import com.buguw.common.util.ExceptionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * 配置文件管理
 * @author 方宗虎
 * @since 2012-5-11
 * @version 1.1.0
 */
public class ConfigManager {

    /**
     * 是否为web应用程序
     */
    private static boolean isWebApp = true;
    /**
     * 当前工作根目录
     */
    private static String rootPath = System.getProperty("user.dir");
    /**
     * web servlet上下文
     */
    private static ServletContext servletContext = null;

    /**
     * 设置web servlet上下文
     * @param tempServletContext void
     */
    public static void setServletContext(ServletContext tempServletContext) {
        if (null == tempServletContext)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "context" }));
        servletContext = tempServletContext;
        isWebApp = true;
    }

    /**
     * 设置用户根目录
     * @param paramString void
     */
    public static void setRootPath(String paramString) {
        if (null == paramString)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "newRootPath" }));
        rootPath = paramString;
        if (!rootPath.endsWith(System.getProperty("file.separator")))
            rootPath += System.getProperty("file.separator");
        isWebApp = false;
    }

    /**
     * 获取文件全路径
     * @param paramString
     * @return String
     */
    public static String getFullPathFileName(String paramString) {
        if (null == paramString)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileNameWithoutPath" }));
        if (isWebApp)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString(
                    "ConfigManager.getFullPathFileName.webApp"));
        return rootPath + paramString;
    }

    /**
     * 获取指定文件的流
     * @param paramString
     * @return InputStream
     */
    public static InputStream getInputStream(String paramString) {
        paramString = rootPath + paramString;
        Object localObject = new File(paramString);

        if ((null == paramString) || (0 == paramString.length()))
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileName" }));
        if (isWebApp) {
            if (null == servletContext)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString(
                        "ConfigManager.getInputStream.webApp"));
            localObject = servletContext.getResourceAsStream("/WEB-INF/" + paramString);
            if (null == localObject)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("file.notExist",
                        new String[] { "/WEB-INF/" + paramString }));
            FileInputStream localFileInputStream = null;
            try {
                localFileInputStream = new FileInputStream((File) localObject);
            } catch (Exception localException) {
                ExceptionUtil.throwActualException(localException);
            }
            return (InputStream) localFileInputStream;
        }

        if (!((File) localObject).exists())
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("file.notExist",
                    new String[] { paramString }));
        if (((File) localObject).isDirectory())
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory",
                    new String[] { paramString }));
        FileInputStream localFileInputStream = null;
        try {
            localFileInputStream = new FileInputStream((File) localObject);
        } catch (Exception localException) {
            ExceptionUtil.throwActualException(localException);
        }
        return (InputStream) localFileInputStream;
    }

    /**
     * 获取Properties配置文件
     * @param paramString
     * @return Properties
     */
    public static Properties loadProperties(String paramString) {
        if ((null == paramString) || (0 == paramString.length()))
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileName" }));
        InputStream localInputStream = getInputStream(paramString);
        Properties localProperties = new Properties();
        try {
            localProperties.load(localInputStream);
            if (null != localInputStream)
                try {
                    localInputStream.close();
                } catch (Exception localException1) {
                    localException1.printStackTrace(System.err);
                }
        } catch (Exception localException2) {
            System.out.println("load file " + paramString + " failed!" + localException2.getMessage());
            localException2.printStackTrace(System.err);
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("loadFile.failed",
                    new String[] { paramString, localException2.getMessage() }), localException2);
        } finally {
            if (null != localInputStream)
                try {
                    localInputStream.close();
                } catch (Exception localException3) {
                    localException3.printStackTrace(System.err);
                }
        }
        return localProperties;
    }
}
