
package com.xiaoxiong.common.constants;

import org.springframework.stereotype.Component;

public interface Constants {
	//用户会话保存90天
	//缓存是秒
	Long USER_TOKEN_TERMVALIDITY = 60 * 60 * 24 * 90l;
	//cookie是毫秒
	int WEBUSER_COOKIE_TOKEN_TERMVALIDITY = 1000*60 * 60 * 24 * 90;
	String USER_TOKEN = "token";
}
