/**
 * 
 */
package org.tch.ste.infra.batch.support;

import org.springframework.stereotype.Service;

/**
 * Formatting for Integers.
 * 
 * @author Karthik.
 * 
 */
@Service("IntegerFormatter")
public class IntegerFormatterImpl extends AbstractFormatterImpl {
    private static final String type = "d";
    private static final String flags = "%0";

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.infra.batch.support.AbstractFormatterImpl#getType()
     */
    @Override
    protected String getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.infra.batch.support.AbstractFormatterImpl#getFlags()
     */
    @Override
    protected String getFlags() {
        return flags;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.AbstractFormatterImpl#convertValue(java
     * .lang.String)
     */
    @Override
    protected Object convertValue(String value) {
        Integer retVal = 0;
        if (value != null) {
            retVal = Integer.valueOf(value);
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.AbstractFormatterImpl#getPrecision(java
     * .lang.Object)
     */
    @Override
    protected String getPrecision(Object value) {
        return null;
    }

}
