package com.micro.darren.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationContextUtils implements ApplicationContextAware{

     private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取IOC容器的Bean
     *
     * @param name IOC容器bean名称
     * @return 返回Bean
     */
    public static Object getBean(String name) {
        Assert.notNull(applicationContext, "applicationContext is must not be none");
        return applicationContext.getBean(name);
    }

    /**
     * 根据class类型直接获取ioc容器中的bean
     * note：bean必须的单例
     *
     * @param clazz 需要获取bean 类名称
     * @return 返回bean
     */
    public static <T> T getBeanByClass(Class<T> clazz) {
        Assert.notNull(applicationContext, "applicationContext is must not be none");
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据类型获取bean的名称
     *
     * @param clazz 类型
     * @return
     */
    public static String[] getBeanNamesForType(Class<?> clazz) {
        Assert.notNull(applicationContext, "applicationContext is must not be none");
        return applicationContext.getBeanNamesForType(clazz);
    }

}
