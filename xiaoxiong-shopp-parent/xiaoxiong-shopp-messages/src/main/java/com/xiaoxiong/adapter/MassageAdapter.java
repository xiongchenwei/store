package com.xiaoxiong.adapter;


import com.alibaba.fastjson.JSONObject;

/**
 * (所有的消息都会交给这个接口进行转发)
 */
public interface MassageAdapter  {

    //接收消息
    public void distribute(JSONObject jsonObject);

}
