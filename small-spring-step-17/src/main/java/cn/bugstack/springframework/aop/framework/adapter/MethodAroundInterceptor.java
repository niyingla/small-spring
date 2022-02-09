package cn.bugstack.springframework.aop.framework.adapter;

import cn.bugstack.springframework.aop.MethodAroundAdvice;
import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Interceptor to wrap am {@link MethodBeforeAdvice}.
 * Used internally by the AOP framework; application developers should not need
 * to use this class directly.
 */
public class MethodAroundInterceptor extends AbstractMethodInterceptor {


    public MethodAroundInterceptor() {
    }

    public MethodAroundInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        ((MethodAroundAdvice) this.advice).before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        Object proceed = methodInvocation.proceed();
        ((MethodAroundAdvice) this.advice).after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return proceed;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAroundAdvice advice) {
        this.advice = advice;
    }
}
