package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_permission
 */
@Data
public class TbPermission implements Serializable {
    private Integer id;

    private String permissionName;

    private Integer moduleId;

    private Integer actionId;

    private static final long serialVersionUID = 1L;
}