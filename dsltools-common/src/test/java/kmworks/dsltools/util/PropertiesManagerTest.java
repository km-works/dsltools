/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmworks.dsltools.util;

import kmworks.util.config.PropertyMap;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Christian P. Lerch
 */
public class PropertiesManagerTest {
    
    public PropertiesManagerTest() {
    }

    @Test
    public void testPropertiesLoader() {
        PropertyMap options = PropertiesManager.getOptions();
        assertNotNull(options);
        assertFalse(options.isEmpty());
    }
    
}
