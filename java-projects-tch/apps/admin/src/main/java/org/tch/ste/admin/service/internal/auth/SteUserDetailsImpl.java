/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

/**
 * Custom implementation.
 * 
 * @author Karthik.
 * 
 */
public class SteUserDetailsImpl implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = -7228137421507724683L;

    private String iisn;

    private LdapUserDetailsImpl ldapUserDetailsImpl;

    /**
     * Overloade constructor.
     * 
     * @param ldapUserDetailsImpl
     *            LdapUserDetailsImpl - The actual implementation.
     */
    public SteUserDetailsImpl(LdapUserDetailsImpl ldapUserDetailsImpl) {
        this.ldapUserDetailsImpl = ldapUserDetailsImpl;
    }

    /**
     * Returns the field iisn.
     * 
     * @return iisn String - Get the field.
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * Sets the field iisn.
     * 
     * @param iisn
     *            String - Set the field iisn.
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#getAuthorities
     * ()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ldapUserDetailsImpl.getAuthorities();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return ldapUserDetailsImpl.getPassword();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return ldapUserDetailsImpl.getUsername();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
     * ()
     */
    @Override
    public boolean isAccountNonExpired() {
        return ldapUserDetailsImpl.isAccountNonExpired();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked
     * ()
     */
    @Override
    public boolean isAccountNonLocked() {
        return ldapUserDetailsImpl.isAccountNonLocked();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#
     * isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return ldapUserDetailsImpl.isCredentialsNonExpired();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return ldapUserDetailsImpl.isEnabled();
    }

}
