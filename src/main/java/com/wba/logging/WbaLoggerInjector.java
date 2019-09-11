/**
 * 
 */
package com.wba.logging;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 5, 2019
 */
@Component
public class WbaLoggerInjector implements BeanPostProcessor
{
	/**
     * Injecting the logger into bean class. postProcessBeforeInitialization
     * method calls with an arbitrary target instance and bean name of the
     * instance, the fields Mark with the {@link InjectTableLogger} annotation
     * will be set with the Logger Instance created using {@link LoggerFactory}
     * 's method getLogger passing the name of the class.
     * 
     * @param pBean
     *            Object the target instance the logger is used.
     * @param pBeanName
     *            String name of the bean having the {@link InjectableLogger}
     *            Annotation.
     * @return bean
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
     *      java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(final Object pBean, String pBeanName) 
    {
        ReflectionUtils.doWithFields(pBean.getClass(), new FieldCallback() {
  			@Override
			public void doWith(Field field) throws IllegalAccessException {
                // make the field accessible if defined private
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(InjectableLogger.class) != null) {
                    Logger log = LoggerFactory.getLogger(pBean.getClass());
                    field.set(pBean, log);
                }				
			}
        });
        return pBean;
    }

}
