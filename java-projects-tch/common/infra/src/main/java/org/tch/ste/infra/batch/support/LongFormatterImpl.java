/**
 * 
 */
package org.tch.ste.infra.batch.support;

import org.springframework.stereotype.Service;

/**
 * @author Karthik.
 * 
 */
@Service("LongFormatter")
public class LongFormatterImpl extends IntegerFormatterImpl {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.IntegerFormatterImpl#convertValue(java
     * .lang.String)
     */
    @Override
    protected Object convertValue(String value) {
        return Long.valueOf(value);
    }

}
