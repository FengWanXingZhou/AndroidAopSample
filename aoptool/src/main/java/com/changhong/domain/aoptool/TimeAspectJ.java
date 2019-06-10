package com.changhong.domain.aoptool;


import android.os.SystemClock;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TimeAspectJ {

    public static final String TAG = "TimeAspectJ";

    @Pointcut("execution(@com.changhong.domain.aoptool.TimeTrace1 * *(..))")
    public void timeTrace(){

    }

    @Around("timeTrace()")
    public void weaveJointPointAround(ProceedingJoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"Around joinPoint 1");
        long startTime = SystemClock.uptimeMillis();

        joinPoint.proceed();



        /*MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String method = methodSignature.getMethod().getName();
        TimeTrace1 timeTrace1 =  methodSignature.getMethod().getAnnotation(TimeTrace1.class);

        String tag = timeTrace1.tag();*/
        Log.i(TAG," cost time:"+(SystemClock.uptimeMillis()-startTime));
    }



}
