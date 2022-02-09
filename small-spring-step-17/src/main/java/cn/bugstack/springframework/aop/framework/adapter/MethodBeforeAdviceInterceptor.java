package cn.bugstack.springframework.aop.framework.adapter;

import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import cn.bugstack.springframework.aop.Order;
import cn.bugstack.springframework.util.AnnontationUtil;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Interceptor to wrap am {@link cn.bugstack.springframework.aop.MethodBeforeAdvice}.
 * Used internally by the AOP framework; application developers should not need
 * to use this class directly.
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, Order {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        ((MethodBeforeAdvice) this.advice).before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Integer getOrder() {
        return AnnontationUtil.getOrderValue(advice.getClass());
    }
}
