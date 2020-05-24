package com.example.sin.config;

import com.alibaba.fastjson.JSON;
import com.example.sin.constant.TimePattern;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Configuration
public class LoggerAopConfig {

    private static final Logger log = LoggerFactory.getLogger(LoggerAopConfig.class);
    private static final SimpleDateFormat format = new SimpleDateFormat(
        TimePattern.DEFAULT_DATETIME_FORMAT);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();//方法开始时间

    /**
     * 定义拦截规则：拦截cn.g2link.mls.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution( * com.example.sin.controller..*(..))&&@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void executeService() {
    }

    @Before("execution( * com.example.sin.controller..*(..))&&@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void invokeBefore(JoinPoint point) {
        Date date = new Date();
        startTime.set(date.getTime());
        log.info("start | " + format.format(date) + " | " + getRealClassName(point) + " | "
            + getMethodName(point) + " | " + getMethodArgs(point));
    }

    @After("execution( * com.example.sin.controller..*(..))&&@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void invokeAfter(JoinPoint point) {
        Date date = new Date();
        log.info("end | " + format.format(date) + " | " + getRealClassName(point) + " | "
            + getMethodName(point) + " | " + getMethodArgs(point) + " | " + (date.getTime()
            - startTime.get()) + "ms");
    }

    @AfterThrowing(value = "executeService()", throwing = "e")
    public void afterThrowing(JoinPoint point, Throwable e) {
        log.error(
            "Exception | " + format.format(System.currentTimeMillis()) + " | " + getRealClassName(
                point) + " | " + getMethodName(point) + " | " + getMethodArgs(point) + " | " + e);
    }

    /**
     * 获取被代理对象的真实类全名
     *
     * @param point 连接点对象
     * @return 类全名
     */
    private String getRealClassName(JoinPoint point) {
        return point.getTarget().getClass().getName();
    }

    /**
     * 获取代理执行的方法名
     *
     * @param point 连接点对象
     * @return 调用方法名
     */
    private String getMethodName(JoinPoint point) {
        return point.getSignature().getName();
    }

    /**
     * 获取代理执行的方法的参数
     *
     * @return 调用方法的参数
     */
    private String getMethodArgs(JoinPoint point) {
        try {
            Object[] args = point.getArgs();
            for (int i = 0; i < args.length; i++) {
                Object object = args[i];
                if (object instanceof MultipartFile) {
                    return "file";
                }
                if (object.toString().length() > 1000) {
                    args[i] = "bigdata";
                }
            }
            return JSON.toJSONString(args);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}