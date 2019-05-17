package com.xiaoxiong.controller;

import com.xiaoxiong.base.BaseController;
import com.xiaoxiong.common.constants.BaseApiConstants;
import com.xiaoxiong.common.constants.Constants;
import com.xiaoxiong.common.web.CookieUtil;
import com.xiaoxiong.entity.UserEntity;
import com.xiaoxiong.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Slf4j
public class LoginController extends BaseController {

    private static final String LGOIN = "login";
    private static final String INDEX = "index";

    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/locaLogin")
    public String locaLogin() {
        return LGOIN;
    }

    @PostMapping("/login")
    public String login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response){

        try {
            Map<String, Object> loginMap = userFeign.login(userEntity);
            Integer code = (Integer) loginMap.get(BaseApiConstants.HTTP_CODE_NAME);
            if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {

                return setError(request, (String) loginMap.get(BaseApiConstants.HTTP_200_NAME), LGOIN);
            }
            // 登录成功之后,获取token.将这个token存放在cookie
            String token = (String) loginMap.get("data");
            CookieUtil.addCookie(response,"token",token, Constants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
            return INDEX;

        } catch (Exception e) {
            return setError(request, "登陆失败!", LGOIN);
        }
    }
}
