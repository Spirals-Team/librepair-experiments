package apna.Maholla.ResponceModel;

import org.springframework.http.ResponseEntity;

public class UpdateApartment {
    public String apartmentId;
    public ResponseEntity<Object> status;

    public UpdateApartment(ResponseEntity<Object> build) {
        status = build;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }
}
