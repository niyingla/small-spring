package cn.bugstack.springframework.aop.aspectj;

import cn.bugstack.springframework.aop.Pointcut;
import cn.bugstack.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 *

 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面
    private AspectJExpressionPointcut pointcut;
    // 执行具体拦截的拦截器
    private Advice advice;
    // 表达式
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

}
