package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.aop.MethodAroundAdvice;
import cn.bugstack.springframework.beans.factory.annotation.Order;
import cn.bugstack.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Order(3)
@Component("aroundAdvice")
public class UserServiceAroundAdvice implements MethodAroundAdvice {

    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("环绕后置拦截方法：" + method.getName());

    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("环绕前置拦截方法：" + method.getName());
    }


}
