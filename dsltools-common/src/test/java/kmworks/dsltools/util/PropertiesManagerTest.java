/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmworks.dsltools.util;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Christian P. Lerch
 */
public class PropertiesManagerTest {
    
    public PropertiesManagerTest() {
    }

    @Test
    public void testPropertiesLoader() {
        Properties props = PropertiesManager.getOptions();
        assertNotNull(props);
    }
    
}
