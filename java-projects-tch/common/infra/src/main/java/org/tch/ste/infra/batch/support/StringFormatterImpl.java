/**
 * 
 */
package org.tch.ste.infra.batch.support;

import org.springframework.stereotype.Service;

/**
 * @author Karthik.
 * 
 */
@Service("StringFormatter")
public class StringFormatterImpl extends AbstractFormatterImpl {

    private static final String flags = "%-";
    private static final String type = "s";

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
        return (value != null) ? value : " ";
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
