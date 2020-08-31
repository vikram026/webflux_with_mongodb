package com.nisum.webflux.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logging {

   @Pointcut("execution(* com.nisum.*.*(..))")
   private void selectAll(){}

   @Before("selectAll()")
   public void beforeAdvice(){
      System.out.println("Going to Call desired Method");
   }  
}
