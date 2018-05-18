package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

/*
Tesztek:

1, SINGLE, első hely üres, a második nem = siker

2, SINGLE, mindkettő üres =sikertelen

3, ALL, első hely-en van töltény, másodikban nincs =siker

4, ALL, első helyen nincs töltény, a másodikban van = siker

5, ALL, mindkét hely üres = sikertelen
*/


public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore mockFirstTorpedoStore;
  private TorpedoStore mockSecondTorpedoStore;

  @Before
  public void init(){
    mockFirstTorpedoStore = mock(TorpedoStore.class);
    mockSecondTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockFirstTorpedoStore, mockSecondTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(false);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(false);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

    // 1 tesztem
  @Test
  public void fireTorpedo_Single_FirstStoreIsEmptySecondIsNot(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(true);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verification
    verify(mockFirstTorpedoStore, times(1)).isEmpty();
    verify(mockFirstTorpedoStore, times(0)).fire(1);
    verify(mockSecondTorpedoStore, times(1)).isEmpty();
    verify(mockSecondTorpedoStore, times(1)).fire(1);
  }

  // 2-es tesztem
  @Test
  public void fireTorpedo_Single_BothStoresAreEmpty(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(true);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  // 3-as tesztem
  @Test
  public void fireTorpedo_All_SecondStoreIsEmptyFirstIsNot(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(false);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  // 4-es tesztem
  @Test
  public void fireTorpedo_All_FirstStoreIsEmptySecondIsNot(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(true);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  // 5-ös tesztem
  @Test
  public void fireTorpedo_All_BothStoresAreEmpty(){
    // Arrange
    when(mockFirstTorpedoStore.isEmpty()).thenReturn(true);
    when(mockFirstTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

}
