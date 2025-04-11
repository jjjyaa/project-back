package com.example.hjtest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Around("execution(* com.example.hjtest.controller..*(..))")
    public Object logpoint(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메소드 이름
        String methodName = joinPoint.getSignature().getName();

        // 메소드 파라미터
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();

        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null) {
                    // 파라미터 타입 이름 (클래스명만 출력)
                    String typeName = arg.getClass().getSimpleName();
                    params.append(typeName).append(": ").append(arg.toString()).append(" ");
                } else {
                    // 파라미터가 null인 경우 처리
                    params.append("null ");
                }
            }
        }

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        // 로그 출력

        log.info("\n============================================================================================================\n" +
                "메소드 호출: {}.{}() 파라미터: {}", className, methodName, params.toString().trim() +
                "\n============================================================================================================\n");

        return joinPoint.proceed();
    }
}
