package com.meditation.util;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/27 12:33
 */

@Aspect()
@Component
public class aspect {




    public void begin(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();

        System.out.println("(普通通知)" + signature.getName() + "程序开始了" + Arrays.asList(args));
    }

    public void retur(JoinPoint joinPoint, Object result) {
        System.out.println("(slitting)普通通知" + joinPoint.getSignature().getName() + "程序返回了" + "参数为:" + result);
    }


    public void abnomality(JoinPoint joinPoint, Exception e) {
        System.out.println("(slitting)普通通知" + joinPoint.getSignature().getName() + "程序异常了" + "异常是:" + e);
    }

    public void finish(JoinPoint joinPoint) {
        System.out.println("(slitting)普通通知" + joinPoint.getSignature().getName() + "程序结束了");
    }



    //@Around("execution(* com.meditation.controller.*(..)))")
    public Object haha (ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();//获取当前连接点执行的方法
        Object[] args = joinPoint.getArgs();//获取当前连接点的参数

        Object proceed = null;

        ServletRequestAttributes request =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); //Requst对象
        HttpServletRequest request1 = request.getRequest();
        String requestURI = request.getRequest().getRequestURI();

        try {
            /*前置通知*/
            System.out.println("（前置通知）请求链接："+requestURI+"已执行，参数："+ Arrays.toString(args));
            proceed = joinPoint.proceed();
            /*返回通知*/
            System.out.println("（返回通知）结果:"+proceed);
        }catch (Throwable  throwable){
            /*异常通知*/
            System.out.println("（异常通知）异常：" + signature.getName() + "方法出现异常:" + throwable);
            //出现异常通知, 环绕就会优先捕捉异常, 为了 让其他通知捕捉到异常 一定要抛出去
            throw new RuntimeException();
        }finally {
            /*结束通知*/
            System.out.println("---当前请求结束---"+"\n");
        }

        return proceed;
    }
}
