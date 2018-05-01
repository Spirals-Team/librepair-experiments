/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stanislav
 */
public class CarRemoteFetchFacadeTest {
    
    public CarRemoteFetchFacadeTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testGetAll() {
        System.out.println("getAll");
        CarRemoteFetchFacade instance = new CarRemoteFetchFacade();
        String result = instance.getAll();
        System.out.println(result.toString());
        assertTrue(result.length() > 0);
        
    }

   // @Test
    public void testGetByRegNo() {
        System.out.println("getByRegNo");

    }
    
}
