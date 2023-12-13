package com.example.emos.wx.aop;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect // 声明切面的注解
@Component //用于将一个类标记为一个组件，并将其纳入 Spring 的管理范围
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.example.emos.wx.controller.*.*(..))") //所有controller包下的所有类都要被这个aop拦截
    public void aspect(){

    }

    @Around("aspect()") //声明AOP的环绕事件
    public Object around(ProceedingJoinPoint point) throws Throwable{
        R r=(R)point.proceed();
        String token=threadLocalToken.getToken();
        if(token!=null){
            r.put("token",token);
            threadLocalToken.clear();
        }
        return r;
    }
}
