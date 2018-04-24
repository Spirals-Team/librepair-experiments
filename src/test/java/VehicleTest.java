import model.Vehicle;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 27/03/18.
 */
public class VehicleTest {

    @Test
    public void vehicleBuilderTest(){
      Vehicle vehicle=  new VehicleBuilder(Vehicle.VehicleType.VAN,4,"Bernal","Av San Martin 3","Av San Martin 212",
                "Lula Livre","4444444",1).build();

      Assert.assertEquals(vehicle.getClass(),Vehicle.class);
    }
    @Test
    public void vehicleConstructorTest(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).setCost(1).setCapacity(4).setDescription("Lula livre").setPhone("44444444")
                .setLocation("Bernal").setRetirementAddress("Av San Martin 3").setReturnAddress("Av San Martin 212").build();

        Assert.assertTrue(vehicle.getTypeName().equals("VAN") && vehicle.getCost().equals(1) && vehicle.getCapacity().equals(4) &&
        vehicle.getDescription().equals("Lula livre") && vehicle.getRetirementAddress().equals("Av San Martin 3")
                && vehicle.getReturnAddress().equals("Av San Martin 212"));
    }

    @Test
    public void vehicleIsAVanType(){
        //Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        Vehicle vehicle = new Vehicle(Vehicle.VehicleType.VAN);
        Assert.assertTrue(vehicle.getTypeName().equals("VAN"));
    }



    @Test
    public void vehicleDescriptionIsValidWith67Chars(){
        String description = "La moto esta en un excelente estado, y es muy comoda, es un fierro!";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE)
                .setDescription(description).build();
        Assert.assertTrue(vehicle.isValidDescription(description));
    }


    @Test
    public void vehicleDescriptionIsNotValidWithAShortDescription(){
        String description = "La moto esta muy buena";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE)
                .setDescription(description).build();
        Assert.assertFalse(vehicle.isValidDescription(description));
    }


    @Test
    public void vehiclePhoneNumberIsValid(){
        String number = "666444555";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Assert.assertTrue(vehicle.isValidPhoneNumber(number));
    }


    @Test
    public void vehiclePhoneNumberIsNotValidBecauseIsTooLong(){
        String number = "11112222233333444445555555";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Assert.assertFalse(vehicle.isValidPhoneNumber(number));
    }


    @Test
    public void vehicleChangeCostTest(){
        Vehicle vehicle = new VehicleBuilder().setCost(2).build();
        Assert.assertTrue(vehicle.getCost().equals(2));
        vehicle.revalueateCost(4);
        Assert.assertTrue(vehicle.getCost().equals(4));
    }


    @Test
    public void vehicleChangeLocation(){
        Vehicle vehicle = new VehicleBuilder().setLocation("Bernal").build();
        Assert.assertTrue(vehicle.getLocation().equals("Bernal"));
        vehicle.setLocation("Quilmes");
        Assert.assertTrue(vehicle.getLocation().equals("Quilmes"));
    }
    @Test
    public void vehicleTypeTest(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Assert.assertTrue(vehicle.getType().equals(Vehicle.VehicleType.MOTORCYCLE));

    }
    @Test
    public void vehicleChangePhoneTest(){
        Vehicle vehicle = new VehicleBuilder().setPhone("44444444").build();
        Assert.assertTrue(vehicle.getPhone().equals("44444444"));
        vehicle.setPhone("22222222");
        Assert.assertTrue(vehicle.getPhone().equals("22222222"));

    }

    @Test
    public void vehicleChangeRetirementAddressTest(){
        String retirementAdress="Av San Martin 2";
        String newRetirementAddress= "Av Siempre Viva 1000";
        Vehicle vehicle = new VehicleBuilder().setRetirementAddress(retirementAdress).build();
        Assert.assertEquals(vehicle.getRetirementAddress(),retirementAdress);
        vehicle.setRetirementAddress(newRetirementAddress);
        Assert.assertEquals(vehicle.getRetirementAddress(),newRetirementAddress);

    }
    @Test
    public void vehicleChangeReturnAddressTest(){
        String returnAddress="Av San Martin 2";
        String newReturnAddress= "Av Siempre Viva 1000";
        Vehicle vehicle = new VehicleBuilder().setReturnAddress(returnAddress).build();
        Assert.assertEquals(vehicle.getReturnAddress(),returnAddress);
        vehicle.setReturnAddress(newReturnAddress);
        Assert.assertEquals(vehicle.getReturnAddress(),newReturnAddress);

    }
    @Test
    public void vehicleChangeCapacityTest(){
        Vehicle vehicle = new VehicleBuilder().setCapacity(2).build();
        Assert.assertTrue(vehicle.getCapacity().equals(2));
        vehicle.setCapacity(3);
        Assert.assertTrue(vehicle.getCapacity().equals(3));
    }
    @Test
    public void vehicleChangeDescriptionTest(){
        String description="Mas que un auto";
        String newDescription= "Batimovil ";
        Vehicle vehicle = new VehicleBuilder().setDescription(description).build();
        Assert.assertEquals(vehicle.getDescription(),description);
        vehicle.setDescription(newDescription);
        Assert.assertEquals(vehicle.getDescription(),newDescription);

    }

}
