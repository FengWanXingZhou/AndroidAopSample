package com.example.androidaopsample;


import android.os.SystemClock;
import android.util.Log;

import com.changhong.domain.aoptool.TimeTrace1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

//@Aspect
public class TimeAspectJ {

    public static final String TAG = "TimeAspectJ";

    //@Pointcut("execution(@com.example.androidaopsample.TimeTrace1 * *(..))")
    public void timeTrace(){
        Log.i(TAG,"printTime");
    }

    //@Around("timeTrace()")
    public void weaveJointPointAround(ProceedingJoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"Around joinPoint");
        long startTime = SystemClock.uptimeMillis();

            joinPoint.proceed();

        Log.i(TAG,"Around joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime));
    }

    /*@After("printTime()")
    public void weaveJointPointAfter(JoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"After joinPoint");
        long startTime = SystemClock.uptimeMillis();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String method = methodSignature.getMethod().getName();
        TimeTrace1 timeTrace =  methodSignature.getMethod().getAnnotation(TimeTrace1.class);

        Log.i(TAG,"After joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime)
                +" method = "+method+" timeTrace:"+timeTrace.tag());
    }

    @Before("printTime()")
    public void weaveJointPointBefore(JoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"Before joinPoint");
        long startTime = SystemClock.uptimeMillis();


        Log.i(TAG,"Before joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime));
    }*/

}
