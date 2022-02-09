package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.beans.factory.annotation.Value;
import cn.bugstack.springframework.stereotype.Component;

import java.util.Random;

/**

 */
@Component
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行查询方法中...");
        return "小傅哥，100001，深圳，" + token;
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
