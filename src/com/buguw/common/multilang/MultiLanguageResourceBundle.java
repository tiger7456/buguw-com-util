package com.buguw.common.multilang;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.PropertyResourceBundle;


/**
 * 多语言设置
 * @author 方宗虎
 * @since 2012-5-11
 * @version 1.1.0
 */
public class MultiLanguageResourceBundle {
    /**
     * 单例句柄
     */
    private static MultiLanguageResourceBundle instance = null;
    /**
     * 使用属性文件中的静态字符串集合来管理语言环境资源
     */
    private PropertyResourceBundle resourceBundle = null;

    /**
     * 私有构造函数
     */
    private MultiLanguageResourceBundle() {
        if (null == resourceBundle) {
            resourceBundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Common_resource",
                    Locale.getDefault());
        }

    }

    /**
     * 获取该类
     * @return MultiLanguageResourceBundle
     */
    public static MultiLanguageResourceBundle getInstance() {
        if (null == instance)
            instance = new MultiLanguageResourceBundle();
        return instance;
    }

    /**
     * 获取信息
     * @param messageDefine
     * @return String
     */
    public String getString(String messageDefine) {
        return getString(messageDefine, null);
    }

    /**
     * 获取信息
     * @param messageDefine
     * @param messageDefineArrayOfString
     * @return String
     */
    public String getString(String messageDefine, String[] messageDefineArrayOfString) {
        if (null == messageDefine)
            throw new IllegalArgumentException(this.resourceBundle.getString("MultiLanguageResourceBundle.name.null"));
        messageDefine = messageDefine.trim();
        if (0 == messageDefine.length())
            throw new IllegalArgumentException(this.resourceBundle.getString("MultiLanguageResourceBundle.name.empty"));
        String str1 = this.resourceBundle.getString(messageDefine);
        if (Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage()))
            try {
                str1 = new String(str1.getBytes("ISO_8859_1"), "UTF-8");
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
                localUnsupportedEncodingException.printStackTrace();
            }
        if (null != messageDefineArrayOfString)
            for (int i = 0; i < messageDefineArrayOfString.length; i++) {
                String str2 = "{" + i + "}";
                int j = str1.indexOf(str2);
                while (-1 < j) {
                    if (null == messageDefineArrayOfString[i]) {
                        str1 = str1.substring(0, j) + str1.substring(j + str2.length());
                        j = str1.indexOf(str2);
                        continue;
                    }
                    str1 = str1.substring(0, j) + messageDefineArrayOfString[i] + str1.substring(j + str2.length());
                    j = str1.indexOf(str2, j + messageDefineArrayOfString[i].length());
                }
            }
//        if(null != messageDefineArrayOfString)
//        {
//            for(int i = 0; i < messageDefineArrayOfString.length; i++)
//            {
//                String s2 = (new StringBuilder()).append("{").append(i).append("}").toString();
//                for(int j = str1.indexOf(s2); -1 < j;)
//                    if(null == messageDefineArrayOfString[i])
//                    {
//                        str1 = (new StringBuilder()).append(str1.substring(0, j)).append(str1.substring(j + s2.length())).toString();
//                        j = str1.indexOf(s2);
//                    } else
//                    {
//                        str1 = (new StringBuilder()).append(str1.substring(0, j)).append(messageDefineArrayOfString[i]).append(str1.substring(j + s2.length())).toString();
//                        j = str1.indexOf(s2, j + messageDefineArrayOfString[i].length());
//                    }
//
//            }
//
//        }
        return str1;
    }

    public static void main(String[] args) {
        String[] strings = new String[] { "newRootPath","dsasgsg","weesss","fssssss"};
        System.out.println(strings.length);
        System.out.println(MultiLanguageResourceBundle.getInstance().getString("BeanShell.eval.TargetError",strings));
        System.out.println(MultiLanguageResourceBundle.getInstance().getString(null));
    }
}
