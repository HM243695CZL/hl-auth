package com.hl.admin.log;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
@Aspect
public class RequestLogAspect {

    private static final Logger log = LoggerFactory.getLogger(RequestLogAspect.class);

    @Pointcut("@annotation(com.hl.admin.log.LogAnnotation)")
    public void entryPoint() {
        // 无需内容
    }

    @Before("entryPoint()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] parameters = joinPoint.getArgs();
            log.info("\n==================接口请求==================\n" +
                    "请求时间：" + DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss.sss") + "\n" +
                    "请求地址："+ request.getRequestURL().toString() +"\n" +
                    "请求IP："+ request.getRemoteAddr() +"\n" +
                    "请求方法："+ request.getMethod() +"\n" +
                    "类名：" + className +"\n" +
                    "方法名：" + methodName +"\n" +
                    "请求参数：" + JSON.toJSONString(parameters) +"\n");
        } catch (Throwable e) {
            log.info("around " + joinPoint + " with exception : " + e.getMessage());
        }
    }

    @Around("entryPoint()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("\n==================接口响应==================\n" +
                "类名：" + className +"\n"
                + "方法名：" + methodName +"\n"
                + "返回结果：" + JSON.toJSONString(result) +"\n"
                + "方法执行耗时："+ time + "ms\n"
        );

        return result;
    }

    @AfterThrowing(pointcut = "entryPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] parameters = joinPoint.getArgs();

            log.info("\n异常方法:" + className + "." + methodName + "();\n" +
                    "参数:" + JSON.toJSONString(parameters) + "\n" +
                    "异常信息:" + e.getMessage());
        } catch (Exception ex) {
            log.error("异常信息:{}", ex.getMessage());
        }
    }
}
