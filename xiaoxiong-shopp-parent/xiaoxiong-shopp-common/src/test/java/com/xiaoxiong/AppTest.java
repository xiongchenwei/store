package com.xiaoxiong;

import static org.junit.Assert.assertTrue;

import com.xiaoxiong.common.utils.CommonIdUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void test() {

        String time = DateFormatUtils.format(System.currentTimeMillis(), "yyMMddHHmmssSSS");
        System.out.println(time);
        System.out.println(System.currentTimeMillis());
        String t = CommonIdUtils.generate();
        System.out.println(t);

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
