package com.micro.darren.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志切面类
 */
@Aspect
@Component
@Order(0)  //标记定义了组件的加载顺序，值越小拥有越高的优先级。
@Slf4j
public class LogAspect {

    private static long startTime = 0L;

    private static final String WEBSERVICE_ORDER_PACKAGE = "webservice.order";

    /**
     * 定义切点
     */
    @Pointcut(value = "execution(* *..webservice..*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        startTime = System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String targetClazz = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = new StringBuilder(targetClazz).append(".").append(method.getName()).toString();
        Object[] args = proceedingJoinPoint.getArgs();
        if (targetClazz.contains(WEBSERVICE_ORDER_PACKAGE)){//darren-micro-order
            log.warn("请求方法:{}, 访问IP:{}, 访问目标方法:{}, 请求方法参数:{}",
                    httpServletRequest.getMethod(), httpServletRequest.getRemoteAddr(), methodName, Arrays.toString(args));
        } else {
            log.info("请求方法:{}, 访问IP:{}, 访问目标方法:{}, 请求方法参数:{}",
                    httpServletRequest.getMethod(), httpServletRequest.getRemoteAddr(), methodName, Arrays.toString(args));
        }
        return proceedingJoinPoint.proceed();
    }

    @AfterReturning(pointcut = "pointcut()", returning = "object")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object object){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String targetClazz = method.getDeclaringClass().getName();
//        String returnType = method.getReturnType().getSimpleName();//返回类型
        long processTime = System.currentTimeMillis() - startTime;
        if (targetClazz.contains(WEBSERVICE_ORDER_PACKAGE)){//darren-micro-order
            log.warn("响应时间:{},响应数据:{}", processTime, object.toString());
        } else {
            log.info("响应时间:{},响应数据:{}", processTime, object.toString());
        }
    }



}
