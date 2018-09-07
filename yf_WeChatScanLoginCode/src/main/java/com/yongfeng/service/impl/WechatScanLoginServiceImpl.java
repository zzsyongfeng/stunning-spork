package com.yongfeng.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yongfeng.common.constanst.Constanst;
import com.yongfeng.common.utils.AesUtil;
import com.yongfeng.common.utils.DateUtils;
import com.yongfeng.common.utils.HttpClientUtils;
import com.yongfeng.common.utils.PropertiesUtil;
import com.yongfeng.pojo.AccessToken;
import com.yongfeng.pojo.HttpParame;
import com.yongfeng.pojo.WechatUserUnionID;
import com.yongfeng.service.WechatScanLoginService;

/**
 * <p>Title: WechatScanLoginServiceImpl</p>
 * <p>Description: 业务接口实现 </p>
 * @author fengyong
 * @date 2018年9月7日
 */
@Service
public class WechatScanLoginServiceImpl implements WechatScanLoginService {

	/**
	 * <p>Title: wechatLoginUrl</p>
	 * <p>Description: 网页授权回调地址处理</p>
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public Map<String, String> wechatLoginUrl() {
		String content = Constanst.PWD_MD5+DateUtils.getYYYYMMdd();
		byte[] encrypt = AesUtil.encrypt(content, AesUtil.PASSWORD_SECRET_KEY, 16);
		String parseByte2HexStr = AesUtil.parseByte2HexStr(encrypt);
		Map<String,String> map = new HashMap<String,String>();
		String url = HttpParame.AUTHORIZATION_URL;
		url = url.replaceAll("APPID", PropertiesUtil.getValue(HttpParame.APPID));
		try {
			url = url.replaceAll("REDIRECT_URI", URLEncoder.encode(
					PropertiesUtil.getValue(HttpParame.REDIRECT_URI),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url = url.replaceAll("SCOPE", "snsapi_login");
		url = url.replace("STATE", parseByte2HexStr);	//加密state进行验证 回调地址当天有效 防止恶意攻击
		map.put("url", url);
		return map;
	}
	
	/**
	 * <p>Title: getAccessToken</p>
	 * <p>Description: 用户授权后获取用户唯一标识 </p>
	 * @param code
	 * @return
	 */
	@Override
	public AccessToken getAccessToken(String code) {
		String accessTokenUrl = HttpParame.ACCESS_TOKEN_URL;
		accessTokenUrl = accessTokenUrl.replaceAll("APPID", PropertiesUtil.getValue(HttpParame.APPID));
		accessTokenUrl = accessTokenUrl.replaceAll("SECRET", PropertiesUtil.getValue(HttpParame.SECRET));
		accessTokenUrl = accessTokenUrl.replaceAll("CODE", code);
		String responseContent = HttpClientUtils.getInstance().sendHttpGet(accessTokenUrl);
		if (responseContent == null || responseContent == "") {
			return null;
		}
		JSONObject parseObject = JSONObject.parseObject(responseContent);
		AccessToken accessToken = JSONObject.toJavaObject(parseObject, AccessToken.class);
		return accessToken;
	}

	/**
	 * <p>Title: getUserUnionID</p>
	 * <p>Description: 获取用户统一标识。针对一个微信开放平台帐号下的应用，
	 * 同一用户的unionid在多个应用中是唯一的。
	 * 此方法不牵扯到多个应用时候可以不用。
	 * 
	 * 此处用到只是为了获取微信扫码用户的省份城市(此信息获取的只是微信用户所填的城市省份，
	 * 并不是用户的实时位置信息，如果用户未填写是获取不到的。)
	 * </p>
	 * @return
	 */
	@Override
	public WechatUserUnionID getUserUnionID() {
		String unionIDUrl = HttpParame.GET_UNIONID_URL;
		unionIDUrl = unionIDUrl.replace("ACCESS_TOKEN", PropertiesUtil.getValue(HttpParame.ACCESS_TOKEN));
		unionIDUrl = unionIDUrl.replace("OPENID", PropertiesUtil.getValue(HttpParame.OPENID));
		String responseContent = HttpClientUtils.getInstance().sendHttpGet(unionIDUrl);
		if (responseContent == null || responseContent == "") {
			return null;
		}
		JSONObject parseObject = JSONObject.parseObject(responseContent);
		WechatUserUnionID userUnionID = JSONObject.toJavaObject(parseObject, WechatUserUnionID.class);
		return userUnionID;
	}

	

}
