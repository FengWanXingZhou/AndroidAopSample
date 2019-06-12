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

    @Pointcut("execution(@com.changhong.domain.aoptool.TimeTrace * *(..))")
    public void timeTrace(){

    }

    @Around("timeTrace()")
    public void weaveJointPointAround(ProceedingJoinPoint joinPoint) throws Throwable{

        //Log.i(TAG,"Around joinPoint");
        long startTime = SystemClock.uptimeMillis();

        joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String method = methodSignature.getMethod().getName();
        TimeTrace timeTrace =  methodSignature.getMethod().getAnnotation(TimeTrace.class);

        String tag = timeTrace.tag();
        Log.i(tag,methodSignature.toShortString()+ " cost time:"+(SystemClock.uptimeMillis()-startTime)+" mills");
    }



}
