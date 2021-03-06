package framework.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import framework.util.LoggerUtil;

/**
 * Created by Willow on 12/13/16.
 */
@Component
@Aspect
public class ExceptionAspect {
    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* controller..*.*(..))")
    public void exception() {
    }

    @Pointcut("execution(* service..*.*(..)) || @annotation(framework.Aspect.Check)")
    public void aspect() {
    }


    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
    @Before("aspect()")
    public void before(JoinPoint joinPoint) {
        LoggerUtil.info("before " + joinPoint);
    }

    //配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspect()")
    public void after(JoinPoint joinPoint) {
        LoggerUtil.info("after " + joinPoint);
    }

    //配置环绕通知,使用在方法aspect()上注册的切入点
//    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        try {
            Object result= joinPoint.proceed();
            long end = System.currentTimeMillis();
            LoggerUtil.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            LoggerUtil.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
        return null;
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning(value = "aspect()", returning = "retVal")
    public void afterReturn(JoinPoint joinPoint, Object retVal) {
        LoggerUtil.info("afterReturn " + joinPoint);
    }

    //配置抛出异常后通知,使用在方法aspect()上注册的切入点
    @AfterThrowing(pointcut = "exception()", throwing = "ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex) {
        LoggerUtil.error(joinPoint.toShortString() + " : " + ex);
    }
}
