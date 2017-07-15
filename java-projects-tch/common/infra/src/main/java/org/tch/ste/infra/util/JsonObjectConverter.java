/**
 * 
 */
package org.tch.ste.infra.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

/**
 * Implements the interface for JSON using Jackson.
 * 
 * @author Karthik.
 * @param <T>
 *            The generic type.
 * 
 */
@Service("jsonConverter")
public class JsonObjectConverter<T> implements ObjectConverter<T> {

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.web.lib.ObjectConverter#stringify(java.lang.Object)
     */
    @Override
    public String stringify(T obj) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // FIXME to Throw the exception after wrapping into Runtime Exception.
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.web.lib.ObjectConverter#objectify(java.lang.String,
     * java.lang.Class)
     */
    @Override
    public T objectify(String str, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        T retVal = null;
        try {
            retVal = mapper.readValue(str, clazz);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return retVal;
    }

}
