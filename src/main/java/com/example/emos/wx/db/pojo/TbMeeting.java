package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会议表
 * tb_meeting
 */
@Data
public class TbMeeting implements Serializable {
    private Long id;

    private String uuid;

    private String title;

    private Long creatorId;

    private Date date;

    private String place;

    private Date start;

    private Date end;

    private Short type;

    private Object members;

    private String desc;

    private String instanceId;

    private Short status;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}