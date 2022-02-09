package cn.bugstack.springframework.aop.framework.adapter;

import cn.bugstack.springframework.util.AnnontationUtil;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * <p> AbstractMethodInterceptor </p>
 *
 * @author xiaoye
 * @version 1.0
 * @date 2022/2/9 18:45
 */
public abstract class AbstractMethodInterceptor implements MethodInterceptor {

  protected Advice advice;

  public Integer getOrder() {
    return AnnontationUtil.getOrderValue(advice.getClass());
  }

}
