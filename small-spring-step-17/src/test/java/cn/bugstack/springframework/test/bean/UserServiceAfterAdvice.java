package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.aop.MethodAfterAdvice;
import cn.bugstack.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("afterAdvice")
public class UserServiceAfterAdvice implements MethodAfterAdvice {

    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("后置拦截方法：" + method.getName());

    }


}
