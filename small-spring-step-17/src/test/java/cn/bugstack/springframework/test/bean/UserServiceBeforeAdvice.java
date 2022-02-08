package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.aop.MethodBeforeAdvice;
import cn.bugstack.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("beforeAdvice")
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }

}
