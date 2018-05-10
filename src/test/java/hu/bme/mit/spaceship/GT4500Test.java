package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore primary;
  TorpedoStore secondary;
  
  @Before
  public void init(){
	  primary = mock(TorpedoStore.class);
			  secondary = mock(TorpedoStore.class);
	  this.ship = new GT4500(primary, secondary);
    
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
	when(primary.isEmpty()).thenReturn(false);
	when(primary.fire(1)).thenReturn(true);

	  
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_Single_Twice_Success(){
	    // Arrange  

	    // Act
	    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	    result = ship.fireTorpedo(FiringMode.SINGLE);

	    // Assert
	    assertEquals(true, result);
  }
  
  //fire in all mode check return value
  // fire in single mode, primary fires, fire in single mode, secondarz fires
  // fire in single but primary ois emptz then secondary fires
  //fire in single, primary fires . fire in simple and secondary is empty, but fires primary


}
