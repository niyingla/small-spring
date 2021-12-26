package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Support base class for singleton registries which need to handle
 * {@link cn.bugstack.springframework.beans.factory.FactoryBean} instances,
 * integrated with {@link DefaultSingletonBeanRegistry}'s singleton management.
 * <p>

 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 共工厂获取
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        //是否是单例工厂
        if (factory.isSingleton()) {
            //尝试从缓存中互殴去
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                //通过工厂方法获取对象
                object = doGetObjectFromFactoryBean(factory, beanName);
                //生成后写入工厂bean缓存
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            //不是单例 直接从工厂方法生成
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 通过工厂方法获取
     * @param factory
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
