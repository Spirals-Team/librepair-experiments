package pl.rmitula.restfullshop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto implements Serializable {

    private Long id;
    @NotNull
    private Long category;
    @NotNull
    private String name;
    @NotNull
    private Integer quanityInStock;
    @NotNull
    private Double price;

}
