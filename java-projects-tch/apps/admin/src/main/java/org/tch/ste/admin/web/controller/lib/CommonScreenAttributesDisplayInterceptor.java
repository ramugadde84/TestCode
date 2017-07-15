/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.util.DateUtil;

/**
 * Maps the common attributes to every response.
 * 
 * @author Karthik.
 * 
 */
public class CommonScreenAttributesDisplayInterceptor implements HandlerInterceptor {

    @Autowired
    private VaultProperties vaultProperties;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
     * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
     * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            Map<String, Object> model = modelAndView.getModel();
            if (model != null && (viewName != null && !viewName.contains("redirect"))) {
                model.put(AdminControllerConstants.C_MODEL_ATTR_ENVIRONMENT,
                        vaultProperties.getKey(AdminControllerConstants.C_MODEL_ATTR_ENVIRONMENT));
                model.put(AdminControllerConstants.C_MODEL_ATTR_TIMESTAMP, DateUtil.getCurrentTime());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Empty.
    }

}
