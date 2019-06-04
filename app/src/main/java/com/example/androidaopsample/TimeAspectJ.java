package com.example.androidaopsample;


import android.os.SystemClock;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TimeAspectJ {

    public static final String TAG = "TimeAspectJ";

    @Pointcut("execution(@com.example.androidaopsample.TimeTrace * *(..))")
    public void printTime(){
        Log.i(TAG,"printTime");
    }

    /*@Around("printTime()")
    public void weaveJointPointAround(ProceedingJoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"Around joinPoint");
        long startTime = SystemClock.uptimeMillis();

            joinPoint.proceed();

        Log.i(TAG,"Around joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime));
    }*/

    @After("printTime()")
    public void weaveJointPointAfter(JoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"After joinPoint");
        long startTime = SystemClock.uptimeMillis();


        Log.i(TAG,"After joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime));
    }

    @Before("printTime()")
    public void weaveJointPointBefore(JoinPoint joinPoint) throws Throwable{

        Log.i(TAG,"Before joinPoint");
        long startTime = SystemClock.uptimeMillis();


        Log.i(TAG,"Before joinPoint cost time:"+(SystemClock.uptimeMillis()-startTime));
    }

}
