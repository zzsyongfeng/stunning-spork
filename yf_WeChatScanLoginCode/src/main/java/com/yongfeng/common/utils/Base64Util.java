package com.yongfeng.common.utils;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * <p>Title: Base64Util</p>
 * <p>Description: Base64Util工具类 --- 加密和解密</p>
 * @author fengyong
 * @date 2018年9月7日
 */
public class Base64Util {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	/**
     * 解密
     * @param str
     * @return
     */
    public static String decodeStr(String str){
        if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return "";
		}
		return new String(Base64.decodeBase64(new String(str).getBytes(DEFAULT_CHARSET)),DEFAULT_CHARSET).trim();
    }

    /**
     * 加密
     * 
     * @param str
     * @return
     */
    public static String encodeStr(String str){
        if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return "";
		}
        return new String(Base64.encodeBase64Chunked(str.getBytes(DEFAULT_CHARSET)),DEFAULT_CHARSET).trim();
    }

}
