package cn.bugstack.springframework.beans.factory.xml;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.PropertyValue;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanReference;
import cn.bugstack.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import cn.bugstack.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.bugstack.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import cn.bugstack.springframework.core.io.Resource;
import cn.bugstack.springframework.core.io.ResourceLoader;
import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Bean definition reader for XML bean definitions.
 * <p>
 * 读取xml中的BeanDefinition
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 加载流中的BeanDefinition
     * @param resource
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                //解析文件流，注入benDefinition
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    /**
     * 加载路径下的BeanDefinition
     * @param location
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * 循环加载路径下的配置，组装beanDefinition
     * @param locations
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 加载Definitions包含注解和xml
     * @param inputStream
     * @throws ClassNotFoundException
     * @throws DocumentException
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element componentScan = root.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            //扫描路径下的@Componet注解对象
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {
            //根据xml 配置加载BeanDefinition并注册
            createBeanDefinitionFromElement(bean);
        }
    }

    /**
     * 根据xml 配置加载BeanDefinition并注册
     * @param bean
     * @throws ClassNotFoundException
     */
    private void createBeanDefinitionFromElement(Element bean) throws ClassNotFoundException {
        String id = bean.attributeValue("id");
        String name = bean.attributeValue("name");
        String className = bean.attributeValue("class");
        String initMethod = bean.attributeValue("init-method");
        String destroyMethodName = bean.attributeValue("destroy-method");
        String beanScope = bean.attributeValue("scope");

        // 获取 Class，方便获取类中的名称
        Class<?> clazz = Class.forName(className);
        // 优先级 id > name
        String beanName = StrUtil.isNotEmpty(id) ? id : name;
        if (StrUtil.isEmpty(beanName)) {
            beanName = StrUtil.lowerFirst(clazz.getSimpleName());
        }

        // 定义Bean
        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        beanDefinition.setInitMethodName(initMethod);
        beanDefinition.setDestroyMethodName(destroyMethodName);

        if (StrUtil.isNotEmpty(beanScope)) {
            beanDefinition.setScope(beanScope);
        }

        List<Element> propertyList = bean.elements("property");
        // 读取属性并填充
        for (Element property : propertyList) {
            // 解析标签：property
            String attrName = property.attributeValue("name");
            String attrValue = property.attributeValue("value");
            String attrRef = property.attributeValue("ref");
            // 获取属性值：引入对象、值对象
            Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
            // 创建属性信息
            PropertyValue propertyValue = new PropertyValue(attrName, value);
            beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
        }
        if (getRegistry().containsBeanDefinition(beanName)) {
            throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
        }
        // 注册 BeanDefinition
        getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 扫描路径
     * @param scanPath
     */
    private void scanPackage(String scanPath) {
        //切分出具体路径
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        //创建扫描类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        //扫描路径
        scanner.doScan(basePackages);
    }

}
