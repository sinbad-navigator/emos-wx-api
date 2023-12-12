package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 签到表
 * tb_checkin
 */
@Data
public class TbCheckin implements Serializable {
    private Integer id;

    private Integer userId;

    private String address;

    private String country;

    private String province;

    private String city;

    private String district;

    private Byte status;

    private Integer risk;

    private Date date;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}