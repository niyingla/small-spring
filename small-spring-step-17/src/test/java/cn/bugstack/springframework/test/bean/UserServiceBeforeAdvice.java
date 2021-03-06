package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import cn.bugstack.springframework.beans.factory.annotation.Order;
import cn.bugstack.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Order(1)
@Component("beforeAdvice")
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("前置拦截方法：" + method.getName());
    }

}
