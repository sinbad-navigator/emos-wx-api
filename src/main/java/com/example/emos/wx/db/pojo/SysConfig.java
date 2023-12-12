package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_config
 */
@Data
public class SysConfig implements Serializable {
    private Integer id;

    private String paramKey;

    private String paramValue;

    private Boolean status;

    private String remark;

    private static final long serialVersionUID = 1L;
}