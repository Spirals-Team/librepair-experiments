package apna.Maholla.controller;

import apna.Maholla.RequestModels.UpdateApartmentRequest;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.model.Apartment;
import apna.Maholla.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApartmentController {

    private ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @PostMapping(path = "/addApartment", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> addApartment(@RequestBody Apartment apartment) throws Exception {
        apartment.setApartmentUniqueId();
        apartmentRepository.save(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/updateApartmentKey", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound updateApartmentKey(@RequestBody UpdateApartmentRequest updateApartmentRequest) throws Exception {
        Apartment apartment = apartmentRepository.findByApartmentuniqueid(updateApartmentRequest.apartmentuniqueid);
        if(apartment == null)
            return new ResourceNotFoundException("Apartment", "Apartment key", updateApartmentRequest.apartmentuniqueid, "Not Found", "Apartment With given key not found");
        apartment.setApartmentUniqueId();
        String apartmentId = apartment.apartmentuniqueid;
        apartmentRepository.save(apartment);
        return new ResourceSavesSuccess("Apartment", "Apartment key", apartmentId, "Sucess", "Apartment key changed successfully");

    }
}
