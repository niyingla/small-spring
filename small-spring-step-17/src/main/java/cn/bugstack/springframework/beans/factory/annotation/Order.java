package cn.bugstack.springframework.beans.factory.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Order {
  /**
   * 排序值 越小越靠前
   * @return
   */
  int value() default 0;
}
