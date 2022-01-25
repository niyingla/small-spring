package cn.bugstack.springframework.context.support;

import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bugstack.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Convenient base class for {@link cn.bugstack.springframework.context.ApplicationContext}
 * implementations, drawing configuration from XML documents containing bean definitions
 * understood by an {@link cn.bugstack.springframework.beans.factory.xml.XmlBeanDefinitionReader}.
 *

 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载xml中的对象Definition
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        //获取本地配置路径
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            //循环加载路径下的配置，组装beanDefinition
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取本地配置路径
     * @return
     */
    protected abstract String[] getConfigLocations();

}
