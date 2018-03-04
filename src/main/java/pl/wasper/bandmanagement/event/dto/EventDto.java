package pl.wasper.bandmanagement.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import pl.wasper.bandmanagement.serializer.BigDecimalSerializer;
import pl.wasper.bandmanagement.serializer.LocalDateTimeDeserializer;
import pl.wasper.bandmanagement.serializer.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {
    private Long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private String place;
    private String description;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal price;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal advance;
    private boolean isAdvancePaid;
    private boolean isPaid;

}
