package org.geektimes.project.week10.bulkhead;

import java.lang.annotation.*;

/**
 * @author FanJiang
 * @since
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
public @interface Asynchronous {

}
