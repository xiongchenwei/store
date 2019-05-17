package com.xiaoxiong.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BaseEntity {

    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Timestamp created;
    /**
     * 修改时间
     */
    private Timestamp updated;
//    public static void main(String[] args) {
//		log.info("我在使用lomBok  自动生成 get 和set 方法 还有自动日志");
//	}

}
