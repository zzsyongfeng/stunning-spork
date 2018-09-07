package com.yongfeng.service;

import java.util.Map;

import com.yongfeng.pojo.AccessToken;
import com.yongfeng.pojo.WechatUserUnionID;

/**
 * <p>Title: WechatScanLoginService</p>
 * <p>Description: 业务接口 </p>
 * @author fengyong
 * @date 2018年9月7日
 */
public interface WechatScanLoginService {
	
	Map<String,String> wechatLoginUrl();
	
	AccessToken getAccessToken(String code);
	
	WechatUserUnionID getUserUnionID();

}
