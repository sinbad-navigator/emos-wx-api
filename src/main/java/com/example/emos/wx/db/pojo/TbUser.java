package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * tb_user
 */
@Data
public class TbUser implements Serializable {
    private Integer id;

    private String openId;

    private String nickname;

    private String photo;

    private String name;

    private Object sex;

    private String tel;

    private String email;

    private Date hiredate;

    private Object role;

    private Boolean root;

    private Integer deptId;

    private Byte status;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}