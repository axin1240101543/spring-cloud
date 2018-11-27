package com.micro.darren.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**  */
    private Integer uid;

    /** 用户账号 */
    private String username;

    /** 密码 */
    private String password;

    /** 性别 0 女,1男 */
    private Short gender;

    /** 注册日期 */
    private Date regdate;

    /** 最后访问时间 */
    private Date lastvisit;

    /** 城市 */
    private String city;

    /** 用户电话 */
    private String tel;

    /** 电子邮箱 */
    private String email;

    /** 生出年月 */
    private Date bday;

    /** 址地 */
    private String addr;

    /** 备用地址 */
    private String standAddr;

    /** 备用地址2 */
    private String standAddr2;

    /** 备用地址3 */
    private String standAddr3;

    /** 找回密码token */
    private String identifying;

    /**  */
    private Short status;

    /** 姓名 */
    private String realName;

    /** 用户头像 */
    private String headimg;

}