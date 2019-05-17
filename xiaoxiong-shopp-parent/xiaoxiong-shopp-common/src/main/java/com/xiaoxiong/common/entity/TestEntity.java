package com.xiaoxiong.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TestEntity extends BaseEntity {

    private String userName;

    private String phone;

    private String email;

    private String Password;


}
