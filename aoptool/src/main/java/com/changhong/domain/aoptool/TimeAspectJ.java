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
    public Object weaveJointPointAround(ProceedingJoinPoint joinPoint) throws Throwable{

        //Log.i(TAG,"Around joinPoint");
        long startTime = SystemClock.uptimeMillis();

        Object result = joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();



        String methodName = methodSignature.getMethod().getName();
        TimeTrace timeTrace =  methodSignature.getMethod().getAnnotation(TimeTrace.class);

        String tag = timeTrace.tag();


        Object[] args = joinPoint.getArgs();
        String arg = null;
        if(args != null) {

            for (int i = 0; i < args.length; i++) {
                Log.i(tag, " args = " + args[i].toString());
                if(i == 0){
                    arg = args[i].toString();
                }else{
                    arg = arg + ","+args[i].toString();
                }

            }
        }
        Log.i(tag,"method "+methodSignature.toShortString()
                +" args:"+arg

                + " cost time:"
                +(SystemClock.uptimeMillis()-startTime)+" mills");

        return result;
    }

    /*@Around("timeTrace()")
    public void weaveJointPointAround(JoinPoint joinPoint) throws Throwable{

        //Log.i(TAG,"Around joinPoint");
        long startTime = SystemClock.uptimeMillis();

        joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String method = methodSignature.getMethod().getName();
        TimeTrace timeTrace =  methodSignature.getMethod().getAnnotation(TimeTrace.class);

        String tag = timeTrace.tag();
        Log.i(tag,methodSignature.toShortString()+ " cost time:"+(SystemClock.uptimeMillis()-startTime)+" mills");
    }
*/


}
