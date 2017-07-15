/**
 * 
 */
package org.tch.ste.auth.web.controller.support;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.tch.ste.infra.repository.support.EntityManagerInjector;

/**
 * Initializes the issuer specific images - To be used only for local development.
 * 
 * @author Karthik.
 *
 */
public class InitIssuerLogoUtil {
    
    private static String[] iisns=new String[]{"123456","123556","123656"};

    private List<Resource> resources;
    

    
    @Autowired
    private EntityManagerInjector entityManagerInjector;
    
    @Autowired
    private IssuerLogoSetterService issuerLogoSetterService;
    
    /**
     * Constructor.
     * 
     * @param resources List<Resource> - The images.
     */
    public InitIssuerLogoUtil(List<Resource> resources) {
        this.resources=resources;
    }
    
    /**
     * Initializes the issuer logos.
     * @throws IOException Thrown.
     */
    public void initIssuerLogos() throws IOException {
        for (int i=0; i < iisns.length;++i) {
            // To the common DB.
            issuerLogoSetterService(i);
            entityManagerInjector.inject(iisns[i]);
            // To the iisn specific DB.
            issuerLogoSetterService(i);
            entityManagerInjector.remove(iisns[i]);
        }
    }
    /**
     * 
     * @param i
     * @throws IOException
     */
    private void issuerLogoSetterService(int i) throws IOException{
        issuerLogoSetterService.setIssuerLogo(iisns[i], resources.get(i).getFile());
    }
}
