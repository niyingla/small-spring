package cn.bugstack.springframework.aop.framework.adapter;

import cn.bugstack.springframework.aop.MethodAroundAdvice;
import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import cn.bugstack.springframework.aop.Order;
import cn.bugstack.springframework.util.AnnontationUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Interceptor to wrap am {@link MethodBeforeAdvice}.
 * Used internally by the AOP framework; application developers should not need
 * to use this class directly.
 */
public class MethodAroundInterceptor implements MethodInterceptor, Order {

    private MethodAroundAdvice advice;

    public MethodAroundInterceptor() {
    }

    public MethodAroundInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        Object proceed = methodInvocation.proceed();
        this.advice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return proceed;
    }

    public MethodAroundAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Integer getOrder() {
        return AnnontationUtil.getOrderValue(advice.getClass());
    }
}
