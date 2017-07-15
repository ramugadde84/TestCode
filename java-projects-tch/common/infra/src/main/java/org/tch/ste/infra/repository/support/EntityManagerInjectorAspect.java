/**
 * 
 */
package org.tch.ste.infra.repository.support;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * An injector which is generic and works on a specified bean.
 * 
 * @author Karthik.
 * 
 */
public class EntityManagerInjectorAspect {

    private static Logger logger = LoggerFactory.getLogger(EntityManagerInjectorAspect.class);

    @Autowired
    @Qualifier("defaultInjectorImpl")
    private EntityManagerInjector entityManagerInjector;

    private String argumentName;

    private String propertyExpression;

    /**
     * Overloaded Constructor.
     * 
     * @param argumentName
     *            String - The argument name which maps to the IISN.
     * @param propertyExpression
     *            String - The property expression - Optional in case the
     *            argument itself matches the IISN.
     */
    public EntityManagerInjectorAspect(String argumentName, String propertyExpression) {
        this.argumentName = argumentName;
        this.propertyExpression = propertyExpression;
    }

    /**
     * Injects the entity manager.
     * 
     * @param p
     *            ProceeedingJoinPoint - The proceeding join point.
     * @return Object - The value returned by the called method.
     * @throws Throwable
     *             - The thrown exception.
     */
    public Object injectEntityManager(ProceedingJoinPoint p) throws Throwable {
        Object retVal = null;
        String issuerId = getIssuerId(p);
        logger.debug("Injecting Entity Manager for issuer with id: {}", issuerId);
        boolean hasInjected = entityManagerInjector.inject(issuerId);
        try {
            retVal = p.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (hasInjected) {
                entityManagerInjector.remove(issuerId);
            }
        }
        return retVal;
    }

    /**
     * Returns the issuer id based on the constructor argument value.
     * 
     * @param p
     *            ProceedingJoinPoint - The join point.
     * 
     * @return String - The IISN.
     */
    private String getIssuerId(ProceedingJoinPoint p) {
        String retVal = null;
        Object arg = findArg(p);
        if (arg != null) {
            if (this.propertyExpression != null) {
                try {
                    retVal = BeanUtils.getNestedProperty(arg, this.propertyExpression);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.warn("Error while accessing property {}", this.propertyExpression, e);
                }
            } else {
                retVal = arg.toString();
            }
        }
        return retVal;
    }

    /**
     * Fetches the given argument name.
     * 
     * @param p
     *            ProceedingJoinPoint - The join point.
     * @return Object - The argument which matches the name.
     */
    private Object findArg(ProceedingJoinPoint p) {
        Object retVal = null;
        Object[] args = p.getArgs();
        MethodSignature sig = (MethodSignature) p.getSignature();
        String[] parameterNames = sig.getParameterNames();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; ++i) {
                if (this.argumentName.equals(parameterNames[i])) {
                    retVal = args[i];
                    break;
                }
            }
        } else {
            // FIXME - HACK ALERT! Return the first argument.
            if (args != null && args.length > 0) {
                retVal = args[0];
            }
        }
        return retVal;
    }

}
