package com.xiaoxiong.controller;

import com.xiaoxiong.base.BaseController;
import com.xiaoxiong.common.constants.BaseApiConstants;
import com.xiaoxiong.entity.UserEntity;
import com.xiaoxiong.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class RegistController extends BaseController  {

    private static final String LOCAREGIST = "locaRegist";

    private static final String LGOIN = "login";

    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/locaRegist")
    public String locaRegist() {
        return LOCAREGIST;
    }

    @PostMapping("/regist")
    public String regist(UserEntity userEntity, HttpServletRequest request) {
        try {
            log.info("###传入参数：{}",userEntity.toString());
            Map<String, Object> regist = userFeign.regist(userEntity);
            Integer code = (Integer) regist.get(BaseApiConstants.HTTP_CODE_NAME);
            if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
                String msg = (String) regist.get("msg");
                return setError(request, msg, LOCAREGIST);
            }

            //注册成功
            return LGOIN;
        } catch (Exception e) {

            System.out.println("修改上传");
            return setError(request, "注册失败！",LOCAREGIST);
        }
    }
}
