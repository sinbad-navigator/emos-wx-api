package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_workday
 */
@Data
public class TbWorkday implements Serializable {
    private Integer id;

    private Date date;

    private static final long serialVersionUID = 1L;
}