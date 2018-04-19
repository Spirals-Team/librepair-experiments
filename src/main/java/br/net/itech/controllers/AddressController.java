package br.net.itech.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.itech.controllers.crud.Read;
import br.net.itech.dtos.AddressDto;
import br.net.itech.entities.Address;
import br.net.itech.factories.dtos.AddressDtoFactory;
import br.net.itech.response.Response;
import br.net.itech.services.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController implements Read<AddressDto> {
	@Autowired
	AddressService addresService;

	@Autowired
	AddressDtoFactory addressDtoFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

	@Override
	public ResponseEntity<Response<AddressDto>> read(@PathVariable("id") String id) {
		LOGGER.info("Pesquisando endereço por id: " + id);
		Response<AddressDto> response = new Response<AddressDto>();
		Optional<Address> address = addresService.findById(Integer.parseInt(id));
		AddressDto addressDto = null;
		if (!address.isPresent()) {
			LOGGER.error("Não possivel localizar o endereço Id " + id);
			response.getErrors().add("Não foi possivel encontrar o endereço de Id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		LOGGER.info("Endereço: {}", address.get().toString());
		addressDto = addressDtoFactory.getAddressDto(address.get());
		response.setData(addressDto);
		return ResponseEntity.ok().body(response);
	}
}