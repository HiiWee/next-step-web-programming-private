package core.di.context.annotation;

import core.annotation.Bean;
import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.DefaultBeanDefinition;
import java.lang.reflect.Method;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotatedBeanDefinitionReader implements BeanDefinitionReader {

    private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(final BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(final Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    public void registerBean(final Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new DefaultBeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for (Method beanMethod : beanMethods) {
            log.debug("@Bean method : {}", beanMethod);
            AnnotatedBeanDefinition beanDefinition = new AnnotatedBeanDefinition(beanMethod.getReturnType(),
                    beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), beanDefinition);
        }
    }
}
