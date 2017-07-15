/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ERRORS;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * A class which converts the errors from a binding result into a map.
 * 
 * @author Karthik.
 * 
 */
public class ErrorHandler {

    /**
     * Private constructor since this is a utility class.
     */
    private ErrorHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Adds the errors from the binding result to the model as a Map.
     * 
     * @param result
     *            BindingResult - The result.
     * @param model
     *            Model - The model.
     */
    public static void addErrorsToModel(BindingResult result, Model model) {
        Map<String, Object> errors = new HashMap<String, Object>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getCode());
        }
        model.addAttribute(C_MODEL_ATTR_ERRORS, errors);
    }

}
