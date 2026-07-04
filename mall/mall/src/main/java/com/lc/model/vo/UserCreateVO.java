package com.lc.model.vo;


import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class UserCreateVO {

    private String name;

    private int gender;

    private String phone;

    private String username;

    private String password;
}
