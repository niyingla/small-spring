package cn.bugstack.springframework.test.bean;

import cn.bugstack.springframework.beans.factory.annotation.Autowired;
import cn.bugstack.springframework.beans.factory.annotation.Value;
import cn.bugstack.springframework.stereotype.Component;

/**
 * <p> MyBean </p>
 *
 * @author xiaoye
 * @version 1.0
 * @date 2022/2/7 16:58
 */

@Component
public class MyBean {
  @Value("${info.test}")
  private String info;

  @Autowired
  private Wife wife;

  @Override
  public String toString() {
    return "MyBean{" +
        "info='" + info + '\'' +
        ", wife=" + wife +
        '}';
  }
}
