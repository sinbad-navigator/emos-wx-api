package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 节假日表
 * tb_holidays
 */
@Data
public class TbHolidays implements Serializable {
    private Integer id;

    private Date date;

    private static final long serialVersionUID = 1L;
}