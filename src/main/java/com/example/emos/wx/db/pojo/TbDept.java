package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_dept
 */
@Data
public class TbDept implements Serializable {
    private Integer id;

    private String deptName;

    private static final long serialVersionUID = 1L;
}