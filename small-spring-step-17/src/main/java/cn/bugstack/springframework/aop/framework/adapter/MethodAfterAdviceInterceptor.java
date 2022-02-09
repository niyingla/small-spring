package cn.bugstack.springframework.aop.framework.adapter;

import cn.bugstack.springframework.aop.MethodAfterAdvice;
import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Interceptor to wrap am {@link MethodBeforeAdvice}.
 * Used internally by the AOP framework; application developers should not need
 * to use this class directly.
 */
public class MethodAfterAdviceInterceptor implements MethodInterceptor {

    private MethodAfterAdvice advice;

    public MethodAfterAdviceInterceptor() {
    }

    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object proceed = methodInvocation.proceed();
        this.advice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return proceed;
    }

    public MethodAfterAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAfterAdvice advice) {
        this.advice = advice;
    }
}