/**
 * 
 */
package org.tch.ste.test.base;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Karthik.
 * 
 */
public abstract class WebAppIntegrationTest extends BaseTest implements MessageSourceAware {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    protected MessageSource messageSource;

    protected ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();

    protected ResultMatcher redirectedStatus = MockMvcResultMatchers.status().isMovedTemporarily();

    protected ViewResultMatchers viewResultMatcher = MockMvcResultMatchers.view();

    protected Locale defaultLocale = Locale.getDefault();

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    /**
     * Integration test for web app.
     */
    public WebAppIntegrationTest() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain, "/*").build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
     * springframework.context.MessageSource)
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
