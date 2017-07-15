/**
 * 
 */
package org.tch.ste.infra.batch.support;

import org.springframework.stereotype.Service;
import org.tch.ste.infra.util.DecimalUtil;

/**
 * Implements as a float.
 * 
 * @author Karthik.
 * 
 */
@Service("FloatFormatter")
public class FloatFormatterImpl extends AbstractFormatterImpl {

    private static final String type = "f";
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
        Float retVal = 0.0f;
        if (value != null) {
            retVal = Float.valueOf(value);
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
        return DecimalUtil.getPrecisionFormat(value);
    }

}