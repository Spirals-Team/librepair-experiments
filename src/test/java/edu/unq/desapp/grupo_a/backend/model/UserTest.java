package edu.unq.desapp.grupo_a.backend.model;

import org.junit.Test;
import static org.mockito.Mockito.*;

import edu.unq.desapp.grupo_a.backend.model.builders.UserBuilder;
import edu.unq.desapp.grupo_a.backend.model.exceptions.UserInitException;
import static org.junit.Assert.*;

public class UserTest {
	
    @Test
    public void testUserName() {
        User user = UserBuilder.anUser()
        		.withName("Charlie")
        		.build();
        assertEquals(user.getName(), "Charlie");
    }
    
    @Test (expected = UserInitException.class)
    public void testUserNameNull() {
    	UserBuilder.anUser()
    		.withName(null)
    		.build();
    }

    @Test (expected = UserInitException.class)
    public void testUserNameEmpty() {
    	UserBuilder.anUser()
    		.withName("")
    		.build();
    }
    
    @Test (expected = UserInitException.class)
    public void testUserNameWithSpaces() {
    	UserBuilder.anUser()
    		.withName("   ")
    		.build();
    }
    
    @Test
    public void testUserCuil() {
    	User user = UserBuilder.anUser()
    			.withCuil("20-11111111-3")
    			.build();
    	assertEquals(user.getCuil(), "20-11111111-3");
    }
    
    @Test
    public void testUserAddress() {
    	Address mockAddress = mock(Address.class);
    	User user = UserBuilder.anUser()
    			.withAddress(mockAddress)
    			.build();
    	assertEquals(user.getAddress(), mockAddress);
    }
    
    @Test
    public void testUserEmail() {
    	User user = UserBuilder.anUser()
    			.withEmail("charlie@zolezzi.com")
    			.build();
    	assertEquals(user.getEmail(), "charlie@zolezzi.com");
    }
    
    @Test
    public void testUserInitialReputation() {
    	//when a User is initialized
    	//then its reputation is 0 (zero)
    	User user = UserBuilder.anUser()
    			.build();
    	assertEquals(user.getReputation(), Double.valueOf(0));
    }
    
    @Test
    public void testUserInitialVehicles() {
    	//when a User is initialized
    	//then it'oes not have vehicles
    	User user = UserBuilder.anUser()
    			.build();
    	assertTrue(user.getVehicles().isEmpty());
    }
    
    @Test
    public void testUserInitialCredits() {
    	//when a User is initialized
    	//then its credits are 0 (zero);
    	User user = UserBuilder.anUser()
    			.build();
    	assertEquals(user.getCreditAccount().getCurrentAmount(), Double.valueOf(0));
    }

    @Test
    public void testUserAddVehicle() {
    	//when a User is initialized and adds a vehicle
    	//then it has 1 (one) vehicle
    	Vehicle mockVehicle = mock(Vehicle.class);
    	User user = UserBuilder.anUser()
    			.build();
    	user.addVehicle(mockVehicle);
    	assertFalse(user.getVehicles().isEmpty());
    }
    
    @Test
    public void testUserRemoveVehicle() {
    	//when a User has 1 (one) vehicle and removes it
    	//then the User has 0 (zero) vehicles
    	Vehicle mockVehicle = mock(Vehicle.class);
    	User user = UserBuilder.anUser()
    			.build();
    	user.addVehicle(mockVehicle);
    	user.removeVehicle(mockVehicle);
    	assertTrue(user.getVehicles().isEmpty());
    }
    
    @Test
    public void testUserAddCredits() {
    	//when a User is initialized and adds 100 (one hundred) credits
    	//then its credits are 100
    	User user = UserBuilder.anUser()
    			.build();
    	user.addCredit(100.0);
    	assertEquals(user.getCreditAccount().getCurrentAmount(), Double.valueOf(100));
    }
    
    @Test
    public void testUserLoseCredits() {
    	//when a User has 100 (one hundred) credits and lose 35 credits
    	//then its credits are 65
    	User user = UserBuilder.anUser()
    			.build();
    	user.addCredit(100.0);
    	user.loseCredit(35.0);
    	assertEquals(user.getCreditAccount().getCurrentAmount(), Double.valueOf(65));
    }
}
