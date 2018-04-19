package pl.rmitula.restfullshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Status status;

}
