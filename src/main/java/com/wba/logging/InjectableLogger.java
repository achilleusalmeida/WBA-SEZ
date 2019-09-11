/**
 * 
 */
package com.wba.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 5, 2019
 */
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface InjectableLogger {

}
