/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.buguw.common.util;

import com.buguw.common.multilang.MultiLanguageResourceBundle;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

// Referenced classes of package com.giantstone.common.util:
//            CodeUtil

public class MapUtil
{

    public MapUtil()
    {
    }

    public static Object getValue(Map map, String s)
    {
        if(null == map)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "map"
            }));
        if(null == s)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "pathKey"
            }));
        int i = s.indexOf('.');
        if(-1 == i)
        {
            int j = s.indexOf('[');
            if(-1 == j)
                return map.get(s);
            int k = s.indexOf(']', j + 1);
            if(-1 == k)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.1", new String[] {
                    s
                }));
            String s2 = s.substring(0, j);
            Object obj1 = map.get(s2);
            if(null == obj1)
                return obj1;
            if(!(obj1 instanceof List))
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notList.1", new String[] {
                    s, (new StringBuilder()).append("").append(obj1).toString()
                }));
            List list = (List)obj1;
            if(k == j + 1)
                return list;
            int l = -1;
            try
            {
                l = Integer.parseInt(s.substring(j + 1, k));
            }
            catch(Exception exception)
            {
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.2", new String[] {
                    s, exception.getMessage()
                }), exception);
            }
            if(l < 0)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.3", new String[] {
                    s, (new StringBuilder()).append("").append(l).toString()
                }));
            if(l > list.size() - 1)
                return null;
            else
                return list.get(l);
        }
        if(i == s.length() - 1)
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.noLowerKey", new String[] {
                s
            }));
        String s1 = s.substring(0, i);
        Object obj = getValue(map, s1);
        if(null == obj)
            return null;
        if(obj instanceof Map)
        {
            String s3 = s.substring(i + 1);
            return getValue((Map)obj, s3);
        } else
        {
            throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notMap", new String[] {
                s, (new StringBuilder()).append("").append(obj).toString()
            }));
        }
    }

    public static void setValue(Map map, String s, Object obj)
    {
        if(null == map)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "map"
            }));
        if(null == s)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "pathKey"
            }));
        int i = s.indexOf('.');
        if(-1 == i)
        {
            int j = s.indexOf('[');
            if(-1 == j)
            {
                map.put(s, obj);
                return;
            }
            int k = s.indexOf(']', j + 1);
            if(-1 == k)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.1", new String[] {
                    "pathKey"
                }));
            boolean flag = false;
            if(k == j + 1)
            {
                flag = true;
                if(obj != null && !(obj instanceof List))
                    throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notList.2", new String[] {
                        s, (new StringBuilder()).append("").append(obj).toString()
                    }));
            }
            String s2 = s.substring(0, j);
            Object obj3 = map.get(s2);
            if(null == obj3)
            {
                if(flag)
                {
                    map.put(s2, obj);
                    return;
                }
                obj3 = new ArrayList();
                map.put(s2, obj3);
            } else
            if(!(obj3 instanceof List))
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notList.3", new String[] {
                    s, (new StringBuilder()).append("").append(obj).toString()
                }));
            List list = (List)obj3;
            if(flag)
            {
                map.put(s2, obj);
                return;
            }
            int l = -1;
            try
            {
                l = Integer.parseInt(s.substring(j + 1, k));
            }
            catch(Exception exception)
            {
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.2", new String[] {
                    s, exception.getMessage()
                }), exception);
            }
            if(l < 0)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notLegalArray.3", new String[] {
                    s, (new StringBuilder()).append("").append(l).toString()
                }));
            if(l >= list.size())
            {
                for(int i1 = list.size(); i1 < l + 1; i1++)
                    list.add(null);

            }
            list.set(l, obj);
        } else
        {
            if(i == s.length() - 1)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.noLowerKey", new String[] {
                    s
                }));
            String s1 = s.substring(0, i);
            if(s1.indexOf("[]") != -1)
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.canNotHaveLowerKey", new String[] {
                    s
                }));
            Object obj1 = getValue(map, s1);
            Object obj2 = null;
            if(null == obj1)
            {
                obj2 = new HashMap();
                setValue(map, s1, obj2);
            } else
            if(obj1 instanceof Map)
                obj2 = (Map)obj1;
            else
                throw new RuntimeException(MultiLanguageResourceBundle.getInstance().getString("MapUtil.getValue.wrongPath.notMap", new String[] {
                    s, (new StringBuilder()).append("").append(obj1).toString()
                }));
            String s3 = s.substring(i + 1);
            setValue(((Map) (obj2)), s3, obj);
        }
    }

    public static Object[] toArray(Map map)
    {
        if(null == map)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "map"
            }));
        Object aobj[] = map.keySet().toArray();
        Arrays.sort(aobj);
        Object aobj1[] = new Object[aobj.length];
        for(int i = 0; i < aobj.length; i++)
            aobj1[i] = map.get(aobj[i]);

        return aobj1;
    }

    public static List toList(Map map)
    {
        return Arrays.asList(toArray(map));
    }

    public static void printMap(Map map)
    {
        if(null == map)
        {
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "map"
            }));
        } else
        {
            printMap(map, ((OutputStream) (System.out)));
            return;
        }
    }

    public static void printMap(Map map, OutputStream outputstream)
    {
        if(null == map)
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "map"
            }));
        if(null == outputstream)
        {
            throw new IllegalArgumentException(MultiLanguageResourceBundle.getInstance().getString("parameter.null", new String[] {
                "out"
            }));
        } else
        {
            PrintStream printstream = new PrintStream(outputstream);
            printstream.println(MultiLanguageResourceBundle.getInstance().getString("MapUtil.printMap.mapStruct"));
            printMap(map, printstream, 1);
            return;
        }
    }

    private static void printMap(Map map, PrintStream printstream, int i)
    {
        Iterator iterator = map.entrySet().iterator();
        Object obj = null;
        Object obj1 = null;
        Object obj3 = null;
        while(iterator.hasNext()) 
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            Object obj2 = entry.getKey();
            Object obj4 = entry.getValue();
            printLayer(i, printstream);
            printstream.print((new StringBuilder()).append("\"").append(obj2).append("\" = ").toString());
            if(obj4 == null)
                printstream.println();
            else
            if(obj4 instanceof Map)
            {
                printstream.println("[Map]");
                printMap((Map)obj4, printstream, i + 1);
            } else
            if(obj4 instanceof List)
            {
                printstream.println("[List]");
                printList((List)obj4, printstream, i + 1);
            } else
            if(obj4 instanceof byte[])
                printstream.println(new String(CodeUtil.BytetoHex((byte[])(byte[])obj4)));
            else
                printstream.println(obj4.toString());
        }
    }

    private static void printList(List list, PrintStream printstream, int i)
    {
        Iterator iterator = list.iterator();
        int j = 0;
        while(iterator.hasNext()) 
        {
            Object obj = iterator.next();
            printLayer(i, printstream);
            printstream.print((new StringBuilder()).append("[").append(j++).append("] = ").toString());
            if(obj == null)
                printstream.println();
            else
            if(obj instanceof Map)
            {
                printstream.println("[Map]");
                printMap((Map)obj, printstream, i + 1);
            } else
            if(obj instanceof List)
            {
                printstream.println("[List]");
                printList((List)obj, printstream, i + 1);
            } else
            if(obj instanceof byte[])
                printstream.println(new String(CodeUtil.BytetoHex((byte[])(byte[])obj)));
            else
                printstream.println(obj.toString());
        }
    }

    private static void printLayer(int i, PrintStream printstream)
    {
        if(0 == i)
            return;
        for(int j = 0; j < i; j++)
            printstream.print("    ");

        printstream.println("|");
        for(int k = 0; k < i; k++)
            printstream.print("    ");

        printstream.print("+----");
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\ReportSVN\report_library\giantstone\giantstone-common-util.jar
	Total time: 103 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/