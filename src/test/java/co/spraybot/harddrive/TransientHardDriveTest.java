package co.spraybot.harddrive;

import co.spraybot.HardDriveSector;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransientHardDriveTest {

    private TransientHardDrive subject;

    @Before
    public void setUp() {
        subject = new TransientHardDrive(mock(EventBus.class));
    }

    @Test
    public void writingHardDriveSector() {
        HardDriveSector hardDriveSector = mock(HardDriveSector.class);
        when(hardDriveSector.identifier()).thenReturn("id");
        Future<Boolean> done = subject.write(hardDriveSector);

        assertNotNull(done);
        assertTrue(done.succeeded());
        assertTrue(done.result());
    }

    @Test
    public void rememberingSomething() {
        HardDriveSector hardDriveSector = mock(HardDriveSector.class);
        when(hardDriveSector.identifier()).thenReturn("id");
        subject.write(hardDriveSector);
        Future<HardDriveSector> remembered = subject.read("id");

        assertNotNull(remembered);
        assertTrue(remembered.succeeded());
        assertSame(hardDriveSector, remembered.result());
    }

    @Test
    public void forgettingSomething() {
        HardDriveSector hardDriveSector = mock(HardDriveSector.class);
        when(hardDriveSector.identifier()).thenReturn("id");
        subject.write(hardDriveSector);
        Future<Void> forgot = subject.erase("id");

        assertNotNull(forgot);
        assertTrue(forgot.succeeded());

        Future<HardDriveSector> remembered = subject.read("id");

        assertNotNull(remembered);
        assertTrue(remembered.succeeded());
        assertNull(remembered.result());
    }

    @Test
    public void forgettingEverything() {
        HardDriveSector hardDriveSector = mock(HardDriveSector.class);
        when(hardDriveSector.identifier()).thenReturn("id");

        HardDriveSector secondOne = mock(HardDriveSector.class);
        when(secondOne.identifier()).thenReturn("second");
        subject.write(hardDriveSector);
        subject.write(secondOne);
        Future<Void> forgot = subject.eraseEverything();

        assertNotNull(forgot);
        assertTrue(forgot.succeeded());

        Future<HardDriveSector> remembered = subject.read("id");
        Future<HardDriveSector> secondRemembered = subject.read("second");

        assertNotNull(remembered);
        assertTrue(remembered.succeeded());
        assertNull(remembered.result());

        assertNotNull(secondRemembered);
        assertTrue(secondRemembered.succeeded());
        assertNull(secondRemembered.result());
    }

}
