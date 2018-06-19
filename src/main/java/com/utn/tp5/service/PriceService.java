package com.utn.tp5.service;

import com.utn.tp5.model.RouteXCabin;
import com.utn.tp5.persistence.PricePersistence;
import com.utn.tp5.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PriceService {

    @Autowired
    private PricePersistence pricePersistence;

    public List<Price> getAll() {
        return pricePersistence.findAll();
    }

    public Price getById(Long id) {
        return pricePersistence.getOne(id);
    }

    public boolean savePrice(Price p) {
        Price saved = pricePersistence.save(p);
        boolean rtn = false;
        if (saved.equals(p)) {
            rtn = true;
        }
        return rtn;
    }

    public List<Price> getByRouteXCabin(RouteXCabin rxc){
        return pricePersistence.getByRouteXCabin(rxc);
    }

    public boolean deletePrice(Price price) {
        boolean rtn = false;
        Optional<Price> optionalPrice = Optional.of(this.pricePersistence.getOne(price.getId()));
        if (optionalPrice.isPresent()) {
            pricePersistence.delete(price);
        }
        return rtn;
    }
}
