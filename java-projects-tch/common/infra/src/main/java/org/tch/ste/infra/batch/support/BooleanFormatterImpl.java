/**
 * 
 */
package org.tch.ste.infra.batch.support;

import org.springframework.stereotype.Service;

/**
 * For a boolean value.
 * 
 * @author Karthik.
 * 
 */
@Service("BooleanFormatter")
public class BooleanFormatterImpl extends AbstractFormatterImpl {

    private static final String flags = "%-";
    private static final String type = "b";

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
        return Boolean.valueOf(value);
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
