package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.FactoryBean;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.bugstack.springframework.core.convert.ConversionService;
import cn.bugstack.springframework.util.ClassUtils;
import cn.bugstack.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**

 * <p>
 * BeanDefinition注册表接口
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * ClassLoader to resolve bean class names with, if necessary
     */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * BeanPostProcessors to apply in createBean
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * String resolvers to apply e.g. to annotation attribute values
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    private ConversionService conversionService;

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    @Override
    public boolean containsBean(String name) {
        return containsBeanDefinition(name);
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    /**
     * 获取对象
     * @param name
     * @param args
     * @param <T>
     * @return
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        //获取对象缓存中的单例对象
        //从三、二、一级缓存中获取对象
        Object sharedInstance = getSingleton(name);
        //存在缓存对象
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }
        //获取对象BeanDefinition
        BeanDefinition beanDefinition = getBeanDefinition(name);
        //创建对象
        Object bean = createBean(name, beanDefinition, args);
        //判断是否是需要工厂获取实例
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 从工厂类获取对象
     * @param beanInstance
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        //判断是否是工厂对象
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        //尝试从FactoryBean缓存中获取
        //尝试从缓存工厂对象中获取对象
        Object object = getCachedObjectForFactoryBean(beanName);

        //不存在 直接生成
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            //从工厂获取对象
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

}
