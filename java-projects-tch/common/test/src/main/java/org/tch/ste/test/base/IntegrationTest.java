/**
 * 
 */
package org.tch.ste.test.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Annotation to capture the common annotations used for Testing.
 * 
 * @author Karthik.
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/spring_app_context_junit.xml",
        "file:src/main/webapp/WEB-INF/spring/spring_mvc_config_junit.xml" })
@WebAppConfiguration
public @interface IntegrationTest {
    // Empty.
}
