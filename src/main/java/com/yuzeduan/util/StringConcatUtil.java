package com.yuzeduan.util;


public class StringConcatUtil {
    private static StringBuilder mBuilder = new StringBuilder();

    /**
     * 用于拼接图片的父路径和名字生成图片路径
     * @param head 父路径
     * @param tail 图片名字
     * @return 返回生成的完整图片路径
     */
    public static String concatString(String head, String tail){
        mBuilder.delete(0, mBuilder.length());
        return mBuilder.append(head).append("/").append(tail).toString();
    }

}
