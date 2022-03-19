package com.xiaobai.aspect;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.xiaobai.annotation.SystemLog;
import com.xiaobai.entity.SysLog;
import com.xiaobai.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Pointcut("@annotation(com.xiaobai.annotation.SystemLog)")
    public void pt() {

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
            addLog(joinPoint,ret);
        } finally {
            log.info("==========End==========" + System.lineSeparator());
        }
        return ret;
    }

    private void addLog(ProceedingJoinPoint joinPoint, Object ret) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取被增强方法上的注解对象
        SysLog sysLog = new SysLog();
        sysLog.setUrl(requestAttributes.getRequest().getRequestURL().toString());
        sysLog.setBusinessName(getSystemLog(joinPoint).businessName());
        sysLog.setHttpMethod(requestAttributes.getRequest().getMethod());
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + ((MethodSignature) joinPoint.getSignature()).getName());
        sysLog.setIp(requestAttributes.getRequest().getRemoteHost());
        sysLog.setRequestArgs(JSON.toJSONString(joinPoint.getArgs()));
        sysLog.setResponse(JSON.toJSONString(ret));
        sysLog.setCreateTime(DateUtil.date());
        sysLogMapper.insert(sysLog);
    }

    private void handleAfter(Object ret) {
        // 打印响应信息
        log.info("Response          ：{}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("==========Start==========");
        // 打印请求url
        log.info("URL               ：{}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName      ：{}", systemLog.businessName());
        //打印请求方法
        log.info("Http Method       ：{}", request.getMethod());
        // 打印方法全路径
        log.info("Class Method      ：{}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求ip
        log.info("IP                ：{}", request.getRemoteHost());
        // 打印请求参数
        log.info("Request Args      ：{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }
}
