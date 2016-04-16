package com.hzih.mmcs.utils;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-7
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    /**
     * 字符串str不是空值或者str长度大于0
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        if(str!=null&&str.length()>0){
            return true;
        }
        return false;
    }

    /**
     *  字符串str是空值或者str长度等于0
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if(str!=null&&str.length()>0){
            return false;
        }
        return true;
    }

    /**
     * 去掉json数据{success:true,total:1,rows[{time:'2013-01-08'},]}中",]}"的逗号,
     * 变成 {success:true,total:1,rows[{time:'2013-01-08'}]}
     * @param str
     * @return
     */
    public static String trim(String str){
        if(str.indexOf(",]}")> -1){
            return str.split(",]}")[0]+"]}";
        }
        return str;
    }

    /**
     *  str = start + result + end
     * @param str
     * @param start
     * @param end
     * @return result
     */
    public static String splitBetween2Str(String str, String start, String end) {
        return str.split(start)[1].split(end)[0];
    }
    public static void main(String[] args) {
        splitBetween2Str("CN=qxp 13867168447, OU=yf, O=cx, L=00, L=00, ST=01, C=cn","L=",",");
    }
}
