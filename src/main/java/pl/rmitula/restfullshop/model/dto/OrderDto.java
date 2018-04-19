package pl.rmitula.restfullshop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rmitula.restfullshop.model.Status;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto implements Serializable {

    private Long id;
    private Long productId;
    private Long userId;
    private Integer quanity;
    private Status status;

}
