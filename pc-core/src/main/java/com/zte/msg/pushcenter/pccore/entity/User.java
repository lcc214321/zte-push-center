package com.zte.msg.pushcenter.pccore.entity;

import lombok.Data;

@Data
public class User extends BaseEntity{

    private String userName;

    private String password;

    private String userRealName;
}
