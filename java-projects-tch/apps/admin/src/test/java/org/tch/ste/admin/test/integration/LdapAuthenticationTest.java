package org.tch.ste.admin.test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LdapAuthenticationTest extends BaseTest implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Test
	public void testLdap() {
		LdapAuthenticationProvider authenticationProvider = applicationContext
				.getBean("ldapAuthProvider", LdapAuthenticationProvider.class);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"tch-configuration", "password");

		authenticationProvider.authenticate(authentication);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

}
