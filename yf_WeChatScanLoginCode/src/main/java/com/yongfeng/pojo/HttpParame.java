package com.yongfeng.pojo;

/**
 * Title: HttpParame Description: 请求参数
 * @author fengyong
 * @date 2018年9月3日
 */
public class HttpParame {

	// 应用唯一标识
	public static final String APPID = "appid";

	// 密匙
	public static final String SECRET = "secret";

	// 微信用户唯一标识
	public static final String OPENID = "openid";

	// 接口调用凭证
	public static final String ACCESS_TOKEN = "access_token";

	// 回调地址
	public static final String REDIRECT_URI = "redirect_uri";

	// 网页授权回调地址
	public static final String AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	// 通过code获取access_token
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 此接口用于获取用户个人信息 UnionID机制
	public static final String GET_UNIONID_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

}
