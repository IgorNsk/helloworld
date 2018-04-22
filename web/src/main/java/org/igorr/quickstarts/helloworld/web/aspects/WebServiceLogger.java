package org.igorr.quickstarts.helloworld.web.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.igorr.quickstarts.helloworld.web.annotations.Trace;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class WebServiceLogger {
    private static final Logger LOG = Logger.getLogger(WebServiceLogger.class);

    private static AtomicInteger count = new AtomicInteger(0);

    @Pointcut("execution(* org.igorr.quickstarts.helloworld.web.restful.hello.HelloServiceS.*(..))")
    public void webServiceMethod() {
        // Do nothing because of aspect.
    }


    @Pointcut("@annotation(annotationTrace)")
    public void webServiceMethodAnnotation(Trace annotationTrace){
        // Do nothing because of aspect.
    }


    @Around("webServiceMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.debug("Call before method " + methodName + " with args " + methodArgs);

        Object result = thisJoinPoint.proceed();

        LOG.debug("Method " + methodName + " returns " + result);

        return result;
    }

    /*
    @Around("webServiceMethodAnnotation(annotationTrace)")
    public Object debugWebServiceCall(Trace annotationTrace, ProceedingJoinPoint thisJoinPoint) throws Throwable {

        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();



        LOG.debug("Call annotation method " + methodName + " with args " + methodArgs);
        LOG.debug("    Trace.level(): " + annotationTrace.level()) ;
        LOG.debug("    count: " + count.incrementAndGet());

        Object result = thisJoinPoint.proceed();
        return result;

    }
    */
    @Before("webServiceMethodAnnotation(annotationTrace)")
    public void annotationWebServiceCall(Trace annotationTrace){
        LOG.debug("annotation level: " + annotationTrace.level());
    }

}