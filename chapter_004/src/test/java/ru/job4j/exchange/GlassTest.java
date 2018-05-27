package ru.job4j.exchange;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class GlassTest {
    Glass glass = new Glass();

    @Test
    public void whenAddAndDelClaimInGlass() {
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 145, 150));
        glass.insertClaim(new Claim("GAZ", Claim.Type.DEL, Claim.Action.BID, 145, 150));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 140, 200));
        glass.insertClaim(new Claim("GAZ", Claim.Type.DEL, Claim.Action.ASK, 140, 200));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 150, 2));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 145, 2));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 150, 1));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 155, 1));
        glass.printGlass();
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 150, 10));
        glass.printGlass();
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 145, 5));
        glass.printGlass();
    }
}
