package com.xiaoxiong;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    @Test
    public void test() throws Exception {
        String str ="{\"userName\":\"张三\",\n" +
                "                \"phone\":\"123456\",\n" +
                "                \"email\":\"devin_8_20@163.com\",\n" +
                "                \"password\":\"123456\"}";

        HttpHelper.httpPost("http://127.0.0.1:8762/member/regist", str, "[{\"key\":\"Content-Type\",\"value\":\"application/json;charset=UTF-8\"}]", 3);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
