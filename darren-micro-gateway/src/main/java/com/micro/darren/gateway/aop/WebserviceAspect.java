package com.micro.darren.gateway.aop;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.common.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * webservice切面类
 */
@Aspect
@Component
@Order(1)  //标记定义了组件的加载顺序，值越小拥有越高的优先级。
@Slf4j
public class WebserviceAspect {

    /**
     * 定义切点
     */
    @Pointcut(value = "execution(* *..webservice..*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String firstArg = String.valueOf(args[0]);
        Method method = methodSignature.getMethod();
        String returnType = method.getReturnType().getSimpleName();
        try {
            if (Objects.isNull(method)){
                throw new IllegalArgumentException("目标方法为空");
            }
            if(StringUtils.isBlank(firstArg) || "?".equals(firstArg)){
                throw new IllegalArgumentException("请求参数为空");
            }
//            args[0] = XmlUtils.getJsonToXml(firstArg);
            Object object = proceedingJoinPoint.proceed(args); //处理结果
            if(null == object){
                throw new NullPointerException("返回结果为null");
            }
            return object.toString();
        }catch (IllegalArgumentException e){
            log.error("非法参数异常");
            JsonResult jsonResult = new JsonResult();
            jsonResult.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonResult.setResultMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return XmlUtils.getXmlToBean2XStream(jsonResult, "result", JsonResult.class);
        }catch (NullPointerException e){
            log.error("处理结果为null");
            JsonResult jsonResult = new JsonResult();
            jsonResult.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonResult.setResultMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return XmlUtils.getXmlToBean2XStream(jsonResult, "result", jsonResult.getClass());
        }catch (Exception e){
            log.error("身份验证失败:{}", e);
            JsonResult jsonResult = new JsonResult();
            jsonResult.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonResult.setResultMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return XmlUtils.getXmlToBean2XStream(jsonResult, "result", jsonResult.getClass());
        }
    }

}
