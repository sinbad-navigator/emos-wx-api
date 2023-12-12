package com.example.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_face_model
 */
@Data
public class TbFaceModel implements Serializable {
    private Integer id;

    private Integer userId;

    private String faceModel;

    private static final long serialVersionUID = 1L;
}