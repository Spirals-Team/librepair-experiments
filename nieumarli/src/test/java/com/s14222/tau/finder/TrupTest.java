package com.s14222.tau.finder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.*;
import com.s14222.tau.finder.*;
public class TrupTest {

  @Test
    public void testTrupa() {
      Trup trup = new Trup();
      trup.sila = 3;
      trup.aktywujSuperMoc();
      assertNotNull(trup);
    }

    @Test
    public void KiedySuperMocAktywowana_ToZwiekszSileO3() {
      Trup trup = new Trup();
      trup.sila = 3;
      trup.aktywujSuperMoc();
      assertEquals(trup.sila, 6);
    }
}
