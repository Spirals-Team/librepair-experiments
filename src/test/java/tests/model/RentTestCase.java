package tests.model;


import builders.PostBuilder;
import builders.UserBuilder;
import model.Post;
import model.Rental;
import model.Reservation;
import model.User;
import model.exceptions.InvalidStatusChangeException;
import model.exceptions.UserBlockedException;
import model.states.rental.*;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RentTestCase {

    @Test(expected=UserBlockedException.class)
    public void shouldNotRentIfIsAUserDisabled(){

        Post post = PostBuilder.
                aPost().
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().buildUserDisabled();

        tenantUser.rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
    }

    @Test
    public void shouldCreateARental(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        assertNotNull(reservation.beConfirm());
    }

    @Test
    public void theRentalShouldStartInPendingState(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental = reservation.beConfirm();

        assertEquals(rental.getState().getClass(), PendingRentalST.class);

    }

    @Test(expected = InvalidStatusChangeException.class)
    public void theRentalShouldNotGoFromConfirmedStateByTheOwnerToConfirmedByTheOwner(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental1 = reservation.beConfirm();

        rental1.ownerConfirmation();
        rental1.ownerConfirmation();

    }

    @Test(expected = InvalidStatusChangeException.class)
    public void theRentalShouldNotGoFromConfirmedStateByTheTenantToConfirmedByTheTenant(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental2 = reservation.beConfirm();

        rental2.tenantConfirmation();
        rental2.tenantConfirmation();

    }

    @Test
    public void theRentalShouldGoFromConfirmedByTheTenantSTToPendingReturnRentalST() {

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental3 = reservation.beConfirm();

        //PendingRental
        rental3.tenantConfirmation();
        //ConfirmedByTheOwnerST

        rental3.ownerConfirmation();

        //PendingReturnRentalST

        assertEquals(rental3.getState().getClass(), PendingReturnRentalST.class);
    }

    @Test
    public void theRentalShouldGoFromConfirmedByTheOwnerSTToPendingReturnRentalST() {

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental4 = reservation.beConfirm();

        //PendingRental
        rental4.ownerConfirmation();
        //ConfirmedByTheOwnerST

        rental4.tenantConfirmation();

        //PendingReturnRentalST

        assertEquals(rental4.getState().getClass(), PendingReturnRentalST.class);
    }

    @Test
    public void theRentalShouldGoFromPendingReturnRentalSTToReturnCOnfirmedByTheOwner(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental5 = reservation.beConfirm();

        //PendingRental
        rental5.ownerConfirmation();
        //ConfirmedByTheOwnerST

        rental5.tenantConfirmation();

        //PendingReturnRentalST

        rental5.ownerConfirmation();
        //ReturnConfirmedByTheOwner

        assertEquals(rental5.getState().getClass(), ReturnConfirmedByTheOwner.class);

    }

    @Test
    public void theRentalShouldGoFromPendingReturnRentalSTToReturnCOnfirmedByTheTenant(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental6 = reservation.beConfirm();

        //PendingRental
        rental6.ownerConfirmation();
        //ConfirmedByTheOwnerST

        rental6.tenantConfirmation();
        //PendingReturnRentalST

        rental6.tenantConfirmation();
        //ReturnCOnfirmedByTheTenant

        assertEquals(rental6.getState().getClass(), ReturnConfirmedByTheTenant.class);

    }

    @Test
    public void theRentalShouldGoFromReturnConfirmedByTheTenantToFinalizedRentalST(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental7 = reservation.beConfirm();

        //PendingRental
        rental7.ownerConfirmation();
        //ConfirmedByTheOwnerST

        rental7.tenantConfirmation();

        //PendingReturnRentalST

        rental7.ownerConfirmation();
        //ReturnConfirmedByTheOwner

       rental7.tenantUserConfirmatedReturn( 5,
               "El vehiculo estaba en perfectas condiciones");

        assertEquals(rental7.getState().getClass(), FinalizedRentalST.class);

    }

    @Test
    public void theRentalShouldGoFromReturnCOnfirmedByTheOwnerToFinalizedRentalST(){

        Post post = PostBuilder.
                aPost().
                whitCostPerHour(2).
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.now().plusDays(3L)).build();

        User tenantUser = UserBuilder.anUser().withCredit(100).build();

        Reservation reservation = tenantUser.
                rent(post, LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Rental rental8 = reservation.beConfirm();
        //PendingRental

        rental8.ownerConfirmation();
        //ConfirmedByTheOwnerST

        rental8.tenantConfirmation();
        //PendingReturnRentalST

        rental8.tenantConfirmation();
        //ReturnConfirmedByTheOwner

        rental8.ownerUserConfirmatedReturn(3, "Salió todo ok, 100% recomendable");

        assertEquals(rental8.getState().getClass(), FinalizedRentalST.class);

    }

}
