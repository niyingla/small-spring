package cn.bugstack.springframework.beans.factory;

/**
 * Interface to be implemented by objects used within a {@link BeanFactory}
 * which are themselves factories. If a bean implements this interface,
 * it is used as a factory for an object to expose, not directly as a bean
 * instance that will be exposed itself.
 *
 * 由BeanFactory中使用的对象实现的接口，这些对象本身就是工厂。
 * 如果一个 bean 实现了这个接口，它就被用作一个对象暴露的工厂，
 * 而不是直接作为一个将暴露自己的 bean 实例。
 * @param <T>
 *

 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();

}
