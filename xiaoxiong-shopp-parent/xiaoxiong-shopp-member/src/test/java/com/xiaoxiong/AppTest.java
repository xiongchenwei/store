package com.xiaoxiong;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void login() throws Exception {
        String str ="{\"phone\":\"123456\",\n" +
                "                \"password\":\"123456\"}";

        HttpHelper.httpPost("http://127.0.0.1:8762/member/login", str, "[{\"key\":\"Content-Type\",\"value\":\"application/json;charset=UTF-8\"}]", 3);
    }


    @Test
    public void getUser() throws Exception {
        String token = "";


        HttpHelper.httpPost("http://127.0.0.1:8762/member/getUser",token, "[{\"key\":\"Content-Type\",\"value\":\"application/json;charset=UTF-8\"}]", 3);
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
