/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This Exception is used to handle all the exceptions which are raised in
 * controller,and forward that exception to error view page.
 * 
 * @author ramug
 * 
 */
@ControllerAdvice
public class ErrorRedirectionHandler {
    
    private Logger logger=LoggerFactory.getLogger(ErrorRedirectionHandler.class);
    /**
     * If controller method throws exception this method renders special view
     * file and pass to it exception message.
     * 
     * @param e
     *            Throwable - The exception handled.
     * 
     * @return ModelAndView.
     */
    @ExceptionHandler(Throwable.class)
    public ModelAndView exception(Throwable e) {
        logger.warn("Exception occured while processing",e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("name", e.getClass().getSimpleName());
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }

}
