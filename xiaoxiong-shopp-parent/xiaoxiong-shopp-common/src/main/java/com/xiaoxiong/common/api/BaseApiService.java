package com.xiaoxiong.common.api;

import com.xiaoxiong.common.constants.BaseApiConstants;

import java.util.HashMap;
import java.util.Map;

public class BaseApiService {

    /**
     *
     * @methodDesc: 功能描述:(返回错误)
     * @param: @param
     *             msg
     * @param: @return
     */
    public Map<String, Object> setResutError(String msg) {
        return setResut(BaseApiConstants.HTTP_500_CODE, msg, null);
    }
    /**
     *
     * @methodDesc: 功能描述:(返回成功)
     * @param: @param
     *             msg
     * @param: @return
     */
    public Map<String, Object> setResutSuccessData(Object data) {
        return setResut(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, data);
    }
    /**
     *
     * @methodDesc: 功能描述:(返回成功)
     * @param: @param
     *             msg
     * @param: @return
     */
    public Map<String, Object> setResutSuccess() {
        return setResut(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, null);
    }


    /**
     *
     * @methodDesc: 功能描述:(参数错误)
     * @param: @param
     *             msg
     * @param: @return
     */
    public Map<String, Object> setResutParameterError(String msg) {
        return setResut(BaseApiConstants.HTTP_400_CODE, msg, null);
    }
    /**
     *
     * @methodDesc: 功能描述:(返回成功)
     * @param: @param
     *             msg
     * @param: @return
     */
    public Map<String, Object> setResutSuccess(String msg) {
        return setResut(BaseApiConstants.HTTP_200_CODE, msg, null);
    }

    /**
     *
     * @methodDesc: 功能描述:(自定义返回)
     * @param: @param
     *             code
     * @param: @param
     *             msg
     * @param: @param
     *             data
     * @param: @return
     */
    public Map<String, Object> setResut(Integer code, String msg, Object data) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(BaseApiConstants.HTTP_CODE_NAME, code);
        result.put(BaseApiConstants.HTTP_200_NAME, msg);
        if (data != null)
            result.put(BaseApiConstants.HTTP_DATA_NAME, data);
        return result;
    }
}
