package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.controller.form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //响应的数据都是json形式
@RequestMapping("/test")
@Api("测试web接口") //添加这个注解，swagger才会扫描这个类
public class TestController {

    @PostMapping("/sayHello")
    @ApiOperation("最简单的测试方法") //@Valid 数据校验  @RequestBody @RequestBody 注解用于将 HTTP 请求的请求体json数据序列化为对象类型
    public R sayHello(@Valid @RequestBody TestSayHelloForm form) {
        return R.ok().put("message", "hello world," + form.getName());
    }
}
