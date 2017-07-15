/**
 * 
 */
package org.tch.ste.admin.service.core.issuer;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.AuthenticationMechanism;

/**
 * @author ramug
 * 
 */
@Service
public class AuthenticationComparatorImpl implements AuthenticationComparator, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6296470013658383943L;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {
        AuthenticationMechanism authenticationMechanism = (AuthenticationMechanism) o1;
        AuthenticationMechanism authenticationMechanism2 = (AuthenticationMechanism) o2;
        return (authenticationMechanism.getId() - authenticationMechanism2.getId());

    }
}
