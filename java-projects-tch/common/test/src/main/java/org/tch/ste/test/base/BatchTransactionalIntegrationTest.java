/**
 * 
 */
package org.tch.ste.test.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.constant.MivConstants;

/**
 * @author Karthik.
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring_app_context.xml")
@WebAppConfiguration
@TransactionConfiguration(transactionManager = MivConstants.BATCH_TRANSACTION_MANAGER)
@Transactional(value = MivConstants.BATCH_TRANSACTION_MANAGER)
public @interface BatchTransactionalIntegrationTest {
    // Empty.
}
