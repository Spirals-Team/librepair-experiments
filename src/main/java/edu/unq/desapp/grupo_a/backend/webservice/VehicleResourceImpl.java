package edu.unq.desapp.grupo_a.backend.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import edu.unq.desapp.grupo_a.backend.api.VehicleResource;
import edu.unq.desapp.grupo_a.backend.dto.VehicleDataDto;
import edu.unq.desapp.grupo_a.backend.dto.VehicleDto;
import edu.unq.desapp.grupo_a.backend.dto.VehicleFilterDto;

@Service
@Produces("application/json")
@Consumes("application/json")
@Path("/vehicles")
public class VehicleResourceImpl implements VehicleResource{

	@Override
	public Response addVehicleData(VehicleDataDto vehicleDataDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response addVehicle(VehicleDto vehicleDto, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleDto getVehicle(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateVehicle(VehicleDto vehicleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteVehicle(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response publishVehicle(VehicleDto vehicleDto, Long idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response rentVehicle(VehicleDto vehicleDto, Long idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleDto> searchVehicle(VehicleFilterDto vehicleFilterDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
