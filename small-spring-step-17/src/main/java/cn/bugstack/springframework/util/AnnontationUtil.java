package cn.bugstack.springframework.util;

import cn.bugstack.springframework.beans.factory.annotation.Order;

import java.util.Optional;

public class AnnontationUtil {
  /**
   * 获取注解排序值 空为 0
   *
   * @param c
   * @return
   */
  public static Integer getOrderValue(Class<?> c) {
    Order annotation = c.getAnnotation(Order.class);
    return Optional.ofNullable(annotation).map(Order::value).orElse(0);
  }

}
