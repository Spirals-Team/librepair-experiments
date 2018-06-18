package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NamesDto {

    @JsonProperty(value = "Name")
    public NameDto name;
}
