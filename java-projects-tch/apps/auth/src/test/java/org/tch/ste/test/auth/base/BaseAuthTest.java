/**
 * 
 */
package org.tch.ste.test.auth.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.infra.service.core.password.PasswordGenerationService;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * Base auth test.
 * 
 * @author Karthik.
 * 
 */
public abstract class BaseAuthTest extends WebAppIntegrationTest {
	@Autowired
	@Qualifier("customerDao")
	private JpaDao<Customer, Integer> customerDao;
	
	@Autowired
	@Qualifier("issuerConfigurationDao")
	private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

	@Autowired
	private PasswordGenerationService passwordGenerationService;

	@Autowired
	@Qualifier("defaultInjectorImpl")
	protected EntityManagerInjector emInjector;
	
	@Value("classpath:hdfc.jpg")
	private Resource logoFile;

	protected String LOGIN_URL = "/login";

	protected String LOGIN_ID = "userId";

	protected String PASSWORD = "password";

	protected String IISN = "iisn";

	protected String TR_ID = "trId";

	protected String iisn = "123456";

	protected String trId = "123456";

	protected List<String> passwords = new ArrayList<String>();

	protected List<String> userNames = new ArrayList<String>();

	protected String EMPTY_PI_ERROR_MSG = "There are no cards available for selection. Please contact customer support.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tch.ste.test.base.WebAppIntegrationTest#doOtherSetup()
	 */
	@Override
	protected void doOtherSetup() {
		super.doOtherSetup();
		createUserNames();
		try {
			createCustomers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates the user names.
	 */
	private void createUserNames() {
		userNames.add("test1234");
		userNames.add("test4321");
		userNames.add("test4444");
	}

	/**
	 * Creates the customers needed to test.
	 * @throws IOException Thrown.
	 */
	private void createCustomers() throws IOException {
		for (String userName : userNames) {
			Customer customer = new Customer();
			customer.setUserName(userName);
			Password password = passwordGenerationService.generatePassword();
			customer.setHashedPassword(password.getHashedPassword());
			customer.setPasswordSalt(password.getPasswordSalt());
			passwords.add(password.getPlainTextPassword());
			customerDao.save(customer);
		}
		IssuerConfiguration issuerConfiguration=issuerConfigurationDao.get(1);
		issuerConfiguration.setLogoImage(FileUtils.readFileToByteArray(logoFile.getFile()));
		issuerConfigurationDao.save(issuerConfiguration);
	}

	/**
	 * Deletes the customers after completion of test case.
	 */
	@Override
	protected void doOtherTeardown() {
		super.doOtherTeardown();
		deleteCustomers();
	}

	/**
	 * Deletes all customers.
	 */
	private void deleteCustomers() {
		for (Customer customer : customerDao.getAll()) {
			customerDao.delete(customer);
		}
	}

	/**
	 * @return the iisn
	 */
	public String getIisn() {
		return iisn;
	}

	/**
	 * @param iisn
	 *            the iisn to set
	 */
	public void setIisn(String iisn) {
		this.iisn = iisn;
	}

	/**
	 * @return the trId
	 */
	public String getTrId() {
		return trId;
	}

	/**
	 * @param trId
	 *            the trId to set
	 */
	public void setTrId(String trId) {
		this.trId = trId;
	}

}
