package edu.unq.desapp.grupo_a.backend.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import edu.unq.desapp.grupo_a.backend.dto.VehicleDataDto;
import edu.unq.desapp.grupo_a.backend.dto.VehicleDto;
import edu.unq.desapp.grupo_a.backend.dto.VehicleFilterDto;

@Path("/vechicle")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public interface VehicleResource {
	
	
	@POST
	@Path("/add-data-vehicle")
	public Response addVehicleData(VehicleDataDto vehicleDataDto);
	
	@POST
	@Path("/add-vehicle")
	public Response addVehicle(VehicleDto vehicleDto, Long userId );
	
	@GET
	@Path("/get-vehicle/{id}")
	public VehicleDto getVehicle(@PathParam("id")  Long id);
	
	@PUT
	@Path("/update-vehicle")
	public Response updateVehicle(VehicleDto vehicleDto);
	
	@DELETE
	@Path("/remove/{id}")
	public Response deleteVehicle(@PathParam("id")  Long id);
	
	@POST
	@Path("/publish-vehicle/{idUser}")
	public Response publishVehicle(VehicleDto vehicleDto, @PathParam("idUser")  Long idUser);
	
	@POST
	@Path("/rent-vehicle/{idUser}")
	public Response rentVehicle(VehicleDto vehicleDto, @PathParam("idUser")  Long idUser);
	
	@GET
	@Path("/search-vehicle")
	public List<VehicleDto> searchVehicle(VehicleFilterDto vehicleFilterDto);
	


}
