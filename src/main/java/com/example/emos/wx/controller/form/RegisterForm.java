package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class RegisterForm {

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$",message = "注册码必须是6为数字")
    private String registerCode;

    @NotBlank(message = "微信临时授权不能为空")
    private String code;

    @NotBlank
    private String nickName;

    @NotBlank
    private String photo;


}
