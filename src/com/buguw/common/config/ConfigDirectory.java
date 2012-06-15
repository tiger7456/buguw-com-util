package com.buguw.common.config;

import com.buguw.common.multilang.MultiLanguageResourceBundle;
import com.buguw.common.util.ExceptionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 目录配置
 * @author 方宗虎
 * @since 2012-5-11
 * @version 1.1.0
 */
public class ConfigDirectory {
    /**
     * 用户的当前工作目录
     */
    private String rootPath = System.getProperty("user.dir");

    /**
     * 构造函数
     * @param paramString 用户根目录
     */
    public ConfigDirectory(String path) {
        setRootPath(path);
    }

    /**
     * 设置用户根目录
     * @param path void
     */
    public void setRootPath(String path) {
        if (null == path)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "newRootPath" }));
        this.rootPath = path;
        if (!this.rootPath.endsWith(System.getProperty("file.separator")))
            this.rootPath += System.getProperty("file.separator");
    }
    /**
     * 获取文件全路径
     * @param fileName 文件名
     * @return String
     */
    public String getFullPathFileName(String fileName) {
        if (null == fileName)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileNameWithoutPath" }));
        return this.rootPath + fileName;
    }

    /**
     * 获取指定文件的流
     * @param fileName
     * @return InputStream
     */
    public InputStream getInputStream(String fileName) {
        if ((null == fileName) || (0 == fileName.length()))
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileName" }));
        fileName = this.rootPath + fileName;
        File localFile = new File(fileName);
        if (!localFile.exists())
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("file.notExist",
                    new String[] { fileName }));
        if (localFile.isDirectory())
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("file.isDirectory",
                    new String[] { fileName }));
        FileInputStream localFileInputStream = null;
        try {
            localFileInputStream = new FileInputStream(localFile);
        } catch (Exception e) {
            ExceptionUtil.throwActualException(e);
        }
        return localFileInputStream;
    }

    /**
     * 获取Properties配置文件
     * @param fileName
     * @return Properties
     */
    public Properties loadProperties(String fileName) {
        if ((null == fileName) || (0 == fileName.length()))
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null",
                    new String[] { "fileName" }));
        InputStream localInputStream = getInputStream(fileName);
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
            System.out.println("load file " + fileName + " failed!" + localException2.getMessage());
            localException2.printStackTrace(System.err);
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("loadFile.failed",
                    new String[] { fileName, localException2.getMessage() }), localException2);
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

    public static void main(String[] args) {

        System.out.println("Java运行时环境版本:\n" + System.getProperty("java.version"));

        System.out.println("Java 运行时环境供应商:\n" + System.getProperty("java.vendor"));

        System.out.println("Java 供应商的URL:\n" + System.getProperty("java.vendor.url"));

        System.out.println("Java安装目录:\n" + System.getProperty("java.home"));

        System.out.println("Java 虚拟机规范版本:\n" + System.getProperty("java.vm.specification.version"));

        System.out.println("Java 类格式版本号:\n" + System.getProperty("java.class.version"));

        System.out.println("Java类路径：\n" + System.getProperty("java.class.path"));

        System.out.println("加载库时搜索的路径列表:\n" + System.getProperty("java.library.path"));

        System.out.println("默认的临时文件路径:\n" + System.getProperty("java.io.tmpdir"));

        System.out.println("要使用的 JIT 编译器的名称:\n" + System.getProperty("java.compiler"));

        System.out.println("一个或多个扩展目录的路径:\n" + System.getProperty("java.ext.dirs"));

        System.out.println("操作系统的名称:\n" + System.getProperty("os.name"));

        System.out.println("操作系统的架构:\n" + System.getProperty("os.arch"));

        System.out.println("操作系统的版本:\n" + System.getProperty("os.version"));

        System.out.println("文件分隔符（在 UNIX 系统中是“/”）:\n" + System.getProperty("file.separator"));

        System.out.println("路径分隔符（在 UNIX 系统中是“:”）:\n" + System.getProperty("path.separator"));

        System.out.println("行分隔符（在 UNIX 系统中是“/n”）:\n" + System.getProperty("line.separator"));

        System.out.println("用户的账户名称:\n" + System.getProperty("user.name"));

        System.out.println("用户的主目录:\n" + System.getProperty("user.home"));

        System.out.println("用户的当前工作目录:\n" + System.getProperty("user.dir"));
    }
}
