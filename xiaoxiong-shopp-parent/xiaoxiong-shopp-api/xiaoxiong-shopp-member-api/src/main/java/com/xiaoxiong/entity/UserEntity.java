package com.xiaoxiong.entity;

import com.xiaoxiong.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends BaseEntity {

    private String userName;

    private String phone;

    private String email;

    private String password;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
