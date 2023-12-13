package com.example.emos.wx.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component // 把这个类加入spring,让spring进行管理
@Scope("prototype") //表示spring在使用这个对象的时候是一个多例对象，不再是一个单单例对象
public class OAuth2Filter extends AuthenticatingFilter {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    // 拦截请求后，用于令牌字符串封装成令牌对象
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = getRequestToken(req);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    // 拦截请求，判断请求是否需要被shiro处理
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true; //是option请求直接放行，不需要被shiro处理
        }
        return false;
    }

    //shiro验证token进行认证
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // 设置响应头，响应字符集
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        // 设置跨域参数
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        // 清空threadLocalToken里面的令牌数据，重新刷新
        threadLocalToken.clear();

        String token = getRequestToken(req);
        if (StrUtil.isBlank(token)) { //请求头的token有问题，也就是没有token
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的令牌");
            return false;
        }
        try {
            jwtUtil.verifierToken(token);
        } catch (TokenExpiredException e) { //如果令牌验证有问题，捕获异常，通过这个异常，我们就可以知道是令牌过期了，还是令牌内容有问题
            if (redisTemplate.hasKey(token)) { // 如果服务器的令牌还在缓存里，客户端的令牌已经过期，这种情况，我们需要刷新令牌
                redisTemplate.delete(token); // 刷新令牌：删除服务器缓存的令牌
                int userId = jwtUtil.getUserId(token); // 重新根据userId生成令牌
                token = jwtUtil.createToken(userId);
                redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS); //redis 保存下数据
                threadLocalToken.setToken(token); // 除了redis里面，媒介类threadLocal里面也需要保存一份
            } else {
                resp.setStatus(HttpStatus.SC_UNAUTHORIZED); // 客户端的令牌验证时间有问题，服务端的令牌也没有，那就需要重新生成，也就是重新登录
                resp.getWriter().print("令牌已过期");
                return false;
            }
        } catch (Exception e) {
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED); // 客户端的令牌内容验证有问题，我们告知是无效令牌
            resp.getWriter().print("无效的令牌");
            return false;
        }
        boolean bool = executeLogin(request, response); // 如果上面都执行通过，调用executeLogin进行认证授权，调用这个方法认证失败，授权失败都可能返回false,因为我们还需要重写onLoginFailure，对认证失败重写，才能分清是认证失败，还是授权失败
        return bool;
    }

    //重写onLoginFailure 认证失败的处理
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            resp.getWriter().print(e.getMessage()); // 响应认证失败的消息
        } catch (Exception exception) {

        }

        return false;
    }

    // 拦截请求和返回响应的方法
    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        super.doFilterInternal(request, response, chain);

    }

    // 从请求头里面去获取token令牌，如果为null,尝试从请求体里面获取token
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
